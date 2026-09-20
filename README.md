# saude-service

API para os **dados de saúde coletados pelo Apple Watch** — treinos e métricas, expostos como séries prontas para gráficos.

## Stack

| Item | Versão |
|---|---|
| Java | 21 |
| Spring Boot | 4.1.1 |
| Spring Cloud | 2025.1.3 |

| Porta | Context path | Perfil exigido |
|---|---|---|
| 9010 | `/saude-service/` | `ROLE_SAUDE` |

## Endpoints

### `/treinos`

| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/treinos/treino_por_semana` | quantidade de treinos por semana |
| `GET` | `/treinos/distancia_total_por_semana` | distância total por semana |
| `GET` | `/treinos/energia_ativa_por_semana` | energia ativa por semana |
| `GET` | `/treinos/tempo_total_treino_por_semana` | tempo total por semana |
| `GET` | `/treinos/resumo_treinos` | resumo consolidado |
| `GET` | `/treinos/mais_recente` | treino mais recente |

### `/metricas`

| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/metricas/tempo_de_pe` | tempo em pé |
| `GET` | `/metricas/exposicao_ao_sol` | exposição ao sol |
| `GET` | `/metricas/frequencia_cardiaca_em_repouso_media_movel_8_dias` | FC em repouso, média móvel de 8 dias |
| `GET` | `/metricas/passos_medios_diarios_media_movel_8_dias` | passos médios, média móvel de 8 dias |
| `GET` | `/metricas/sono_profundo_medio_media_movel_8_dias` | sono profundo, média móvel de 8 dias |
| `GET` | `/metricas/sono_total_medio_media_movel_8_dias` | sono total, média móvel de 8 dias |
| `GET` | `/metricas/mais_recente` | métrica mais recente |

## Autenticação

As requisições precisam do token JWT emitido pelo `autenticacao-service`, no cabeçalho:

```
Authorization: Bearer <token>
```

O serviço apenas **valida** o token — ele não emite nenhum. A chave de validação vem de `JWTSecret`, em `secret/application` no Vault, e precisa ser a mesma usada pelo emissor.

> **Atenção ao segredo:** desde a migração para o jjwt 0.13, `JWTSecret` precisa ser uma string **Base64** que decodifique para **no mínimo 64 bytes** — exigência do HS512. Gere um com `openssl rand -base64 64`. Se o valor não atender, a aplicação falha no startup com mensagem explícita, em vez de aceitar uma chave fraca em silêncio.

## Banco de dados

PostgreSQL, com schema versionado por **Flyway** (migrations em `src/main/resources/db/migration`):

- `V001__Inicial.sql`
- `V002__Criacao_de_views.sql`
- `V003__Modifica_views_para_retornar_semama_com_0.sql`

As migrations rodam automaticamente no startup.

> No Spring Boot 4 a autoconfiguração do Flyway passou a viver no módulo `spring-boot-flyway`. Sem essa dependência o Flyway é ignorado **em silêncio** — a aplicação sobe normalmente e nenhuma migration é aplicada. Ela está declarada no `pom.xml`; não remova.

As agregações por semana e as médias móveis são calculadas em **views** no PostgreSQL, criadas pelas migrations `V002` e `V003`.

**Entidades:** `Treino`, `Metrica`.

## Cache

O serviço usa `spring-boot-starter-cache` para evitar recalcular as séries a cada requisição.

## Configuração

A aplicação não guarda configuração própria: ela busca tudo no arranque, via `spring.config.import`.

| Origem | O que vem de lá |
|---|---|
| **Vault** (`secret/application`) | segredos compartilhados: `JWTSecret`, credenciais de e-mail, AWS, Eureka |
| **Vault** (`secret/<nome-do-serviço>`) | segredos próprios, como as credenciais do banco |
| **Config Server** | `server.port`, `context-path`, datasource e demais propriedades |

### Variável de ambiente obrigatória

| Variável | Para que serve |
|---|---|
| `VAULT_TOKEN` | token de acesso ao Vault |

`VAULT_TOKEN` **não tem valor padrão**. Sem ela, o Spring envia a string literal `${VAULT_TOKEN}` ao Vault, recebe `403` e — como `spring.cloud.vault.fail-fast` vem desligado — o erro só aparece bem depois, disfarçado de placeholder não resolvido (`${...} is malformed`). Se quiser que a falha apareça na hora, ligue `spring.cloud.vault.fail-fast: true`.

Também são necessários `VAULT_HOST`, `VAULT_PORT` e `VAULT_SCHEME` quando o Vault não está em `localhost:8200` via `http`, e `CONFIG_SERVER_USER` / `CONFIG_SERVER_PASS` nos serviços que leem do Config Server.

## Como executar

```bash
# build
./mvnw clean package

# execução
VAULT_TOKEN=<seu-token> java -jar target/saude-service-*.jar --spring.profiles.active=dev
```

> **Dependências no ar:** este serviço só sobe com o **Vault**, o **Config Server** e o **Eureka** disponíveis, além do seu banco PostgreSQL.

A aplicação sobe em `http://localhost:9010/saude-service/`.

### Docker

O `Dockerfile` espera o jar já na raiz do projeto, com o nome `sistema-saude-service.jar`:

```bash
./mvnw clean package
cp target/saude-service-*.jar sistema-saude-service.jar

docker build \
  --build-arg VAULT_HOST=<host> \
  --build-arg VAULT_TOKEN=<token> \
  --build-arg CONFIG_SERVER_USER=<usuario> \
  --build-arg CONFIG_SERVER_PASS=<senha> \
  -t saude-service .
```
