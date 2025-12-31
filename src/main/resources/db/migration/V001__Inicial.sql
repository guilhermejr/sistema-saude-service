create table if not exists atividades
(
    id bigserial not null,
    descricao varchar(255) not null,
    criado timestamp without time zone,
    primary key (id)
);

create table if not exists treinos
(
    id bigserial not null,
    atividade_id bigint not null,
    inicio timestamp without time zone,
    fim timestamp without time zone,
    duracao time,
    energia_ativa numeric(19,2),
    frequencia_cardiaca_maxima bigint,
    frequencia_cardiaca_media numeric(19,2),
    distancia numeric(19,2),
    velocidade_media numeric(19,2),
    criado timestamp without time zone,
    primary key (id)
);

alter table if exists treinos
    add constraint fk_atividades
    foreign key (atividade_id)
    references atividades;

create table if not exists metricas
(
    id bigserial not null,
    data date,
    sono time,
    passos bigint,
    distancia numeric(19,2),
    energia_ativa numeric(19,2),
    frequencia_cardiaca_maxima bigint,
    frequencia_cardiaca_minima bigint,
    frequencia_cardiaca_media numeric(19,2),
    frequencia_cardiaca_em_repouso bigint,
    hora_de_ficar_em_pe bigint,
    frequencia_cardiaca_ao_caminhar_media numeric(19,2),
    peso numeric(19,2),
    saturacao_de_oxigenio numeric(19,2),
    tempo_em_pe bigint,
    exposicao_ao_sol bigint,
    criado timestamp without time zone,
    primary key (id)
);