create or replace view vw_treinos_enriquecidos as
select
    t.id,
    t.atividade_id,
    a.descricao as atividade,
    t.inicio,
    t.fim,
    t.inicio::date as data_treino,
    coalesce(
            extract(epoch from (t.fim - t.inicio)) / 60.0,
            extract(epoch from t.duracao) / 60.0
    ) as duracao_min,
    coalesce(
            extract(epoch from (t.fim - t.inicio)) / 3600.0,
            extract(epoch from t.duracao) / 3600.0
    ) as duracao_h,
    t.energia_ativa,
    t.frequencia_cardiaca_maxima,
    t.frequencia_cardiaca_media,
    t.distancia,
    t.velocidade_media,
    t.passos,
    t.frequencia_passos,
    t.bracadas,

    t.frequencia_bracadas,
    t.criado
from treinos t
         join atividades a on a.id = t.atividade_id;

-------------------------------------------------------------------------------

create or replace view vw_treino_por_semana as
select
    date_trunc('week', inicio)::date as semana,
    count(*) as qtd_treinos
from vw_treinos_enriquecidos
where inicio is not null
group by 1
order by 1 desc
limit 8;

-------------------------------------------------------------------------------

create or replace view vw_tempo_total_treino_por_semana as
select
    date_trunc('week', inicio)::date as semana,
    round(sum(duracao_min)::numeric, 2) as total_minutos
from vw_treinos_enriquecidos
where inicio is not null
group by 1
order by 1 desc
limit 8;

-------------------------------------------------------------------------------

create or replace view vw_distancia_total_por_semana as
select
    date_trunc('week', inicio)::date as semana,
    round(sum(distancia)::numeric, 2) as distancia_total
from vw_treinos_enriquecidos
where inicio is not null
  and distancia is not null
group by 1
order by 1 desc
limit 8;

-------------------------------------------------------------------------------

create or replace view vw_energia_ativa_por_semana as
select
    date_trunc('week', inicio)::date as semana,
    round(sum(energia_ativa)::numeric, 2) as energia_ativa_total
from vw_treinos_enriquecidos
where inicio is not null
  and energia_ativa is not null
group by 1
order by 1 desc
limit 8;

-------------------------------------------------------------------------------

create or replace view vw_sono_total_medio_media_movel_8_dias as
select
    data,
    sono_total,
    round((extract(epoch from sono_total) / 3600.0)::numeric, 2) as sono_total_horas,
    round(
            avg(extract(epoch from sono_total) / 3600.0)
                over (order by data rows between 7 preceding and current row)::numeric,
            2
    ) as media_movel_8d_sono_total_horas
from metricas
where data is not null
  and sono_total is not null
order by data desc
limit 8;

-------------------------------------------------------------------------------

create or replace view vw_sono_profundo_medio_media_movel_8_dias as
select
    data,
    sono_profundo,
    round((extract(epoch from sono_profundo) / 3600.0)::numeric, 2) as sono_profundo_horas,
    round(
            avg(extract(epoch from sono_profundo) / 3600.0)
                over (order by data rows between 7 preceding and current row)::numeric,
            2
    ) as media_movel_8d_sono_profundo_horas
from metricas
where data is not null
  and sono_profundo is not null
order by data desc
limit 8;

-------------------------------------------------------------------------------

create or replace view vw_frequencia_cardiaca_em_repouso_media_movel_8_dias as
select
    data,
    frequencia_cardiaca_em_repouso,
    round(
            avg(frequencia_cardiaca_em_repouso)
                over (order by data rows between 7 preceding and current row)::numeric,
            2
    ) as media_movel_8d_fc_repouso
from metricas
where data is not null
  and frequencia_cardiaca_em_repouso is not null
order by data desc
limit 8;

-------------------------------------------------------------------------------

create or replace view vw_passos_medios_diarios_media_movel_8_dias as
select
    data,
    passos,
    round(
            avg(passos) over (order by data rows between 7 preceding and current row)::numeric,
            2
    ) as media_movel_8d_passos
from metricas
where data is not null
  and passos is not null
order by data desc
limit 8;

-------------------------------------------------------------------------------
-- Aqui vai uma versão simples, prática e interpretável, com score de 0 a 100 baseado em:

-- sono total
-- sono profundo
-- FC em repouso contra sua média recente
-- saturação de oxigênio

-- Regra usada

-- Sono total: até 40 pontos
-- Sono profundo: até 20 pontos
-- FC repouso: até 25 pontos
-- Saturação: até 15 pontos

create or replace view vw_score_de_prontidao_diaria as
with base as (
    select
        data,
        extract(epoch from sono_total) / 3600.0 as sono_total_h,
        extract(epoch from sono_profundo) / 3600.0 as sono_profundo_h,
        frequencia_cardiaca_em_repouso as fc_repouso,
        saturacao_de_oxigenio as spo2,
        avg(frequencia_cardiaca_em_repouso)
            over (order by data rows between 7 preceding and current row) as fc_repouso_media_8d
    from metricas
    where data is not null
),
score as (
    select
        data,
        sono_total_h,
        sono_profundo_h,
        fc_repouso,
        fc_repouso_media_8d,
        spo2,

        (
            -- Sono total: até 40 pontos (8h = pontuação máxima)
            least(40.0, greatest(0.0, (coalesce(sono_total_h, 0) / 8.0) * 40.0))

            +

            -- Sono profundo: até 20 pontos (1.5h = pontuação máxima)
            least(20.0, greatest(0.0, (coalesce(sono_profundo_h, 0) / 1.5) * 20.0))

            +

            -- FC em repouso: até 25 pontos
            case
                when fc_repouso is null or fc_repouso_media_8d is null then 0.0
                when fc_repouso <= fc_repouso_media_8d then 25.0
                when fc_repouso <= fc_repouso_media_8d * 1.05 then 18.0
                when fc_repouso <= fc_repouso_media_8d * 1.10 then 10.0
                else 3.0
            end

            +

            -- Saturação: até 15 pontos
            case
                when spo2 is null then 0.0
                when spo2 >= 97 then 15.0
                when spo2 >= 95 then 10.0
                when spo2 >= 93 then 5.0
                else 0.0
            end
        ) as prontidao_score
    from base
)
select
    data,
    round(prontidao_score::numeric, 1) as prontidao_score,
    case
        when prontidao_score >= 85 then 'excelente'
        when prontidao_score >= 70 then 'boa'
        when prontidao_score >= 50 then 'moderada'
        else 'baixa'
        end as classificacao
from score
order by data desc
limit 8;
-------------------------------------------------------------------------------

create or replace view vw_tempo_de_pe_e_exposicao_ao_sol as
select
    data,
    tempo_em_pe,
    exposicao_ao_sol
from metricas
order by data desc
limit 8;