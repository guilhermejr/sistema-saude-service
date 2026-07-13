CREATE OR REPLACE VIEW public.vw_distancia_total_por_semana AS
WITH semanas AS (
    SELECT generate_series(
        date_trunc('week', CURRENT_DATE) - interval '7 weeks',
        date_trunc('week', CURRENT_DATE),
        interval '1 week'
    )::date AS semana
),
distancias AS (
    SELECT
        date_trunc('week', inicio)::date AS semana,
        round(sum(distancia), 2) AS distancia_total
    FROM vw_treinos_enriquecidos
    WHERE inicio IS NOT NULL
      AND distancia IS NOT NULL
    GROUP BY 1
)
SELECT
    s.semana,
    COALESCE(d.distancia_total, 0) AS distancia_total
FROM semanas s
         LEFT JOIN distancias d
                   ON d.semana = s.semana
ORDER BY s.semana DESC;


CREATE OR REPLACE VIEW public.vw_energia_ativa_por_semana AS
WITH semanas AS (
    SELECT generate_series(
        date_trunc('week', CURRENT_DATE) - interval '7 weeks',
        date_trunc('week', CURRENT_DATE),
        interval '1 week'
    )::date AS semana
),
energias AS (
    SELECT
        date_trunc('week', inicio)::date AS semana,
        round(sum(energia_ativa), 2) AS energia_ativa_total
    FROM vw_treinos_enriquecidos
    WHERE inicio IS NOT NULL
      AND energia_ativa IS NOT NULL
    GROUP BY 1
)
SELECT
    s.semana,
    COALESCE(e.energia_ativa_total, 0) AS energia_ativa_total
FROM semanas s
         LEFT JOIN energias e
                   ON e.semana = s.semana
ORDER BY s.semana DESC;

CREATE OR REPLACE VIEW public.vw_tempo_total_treino_por_semana AS
WITH semanas AS (
    SELECT generate_series(
        date_trunc('week', CURRENT_DATE) - interval '7 weeks',
        date_trunc('week', CURRENT_DATE),
        interval '1 week'
    )::date AS semana
),
tempos AS (
    SELECT
        date_trunc('week', inicio)::date AS semana,
        round(sum(duracao_min), 2) AS total_minutos
    FROM vw_treinos_enriquecidos
    WHERE inicio IS NOT NULL
    GROUP BY 1
)
SELECT
    s.semana,
    COALESCE(t.total_minutos, 0) AS total_minutos
FROM semanas s
         LEFT JOIN tempos t
                   ON t.semana = s.semana
ORDER BY s.semana DESC;

CREATE OR REPLACE VIEW public.vw_treino_por_semana AS
WITH semanas AS (
    SELECT generate_series(
        date_trunc('week', CURRENT_DATE) - interval '7 weeks',
        date_trunc('week', CURRENT_DATE),
        interval '1 week'
    )::date AS semana
),
treinos AS (
    SELECT
        date_trunc('week', inicio)::date AS semana,
        count(*) AS qtd_treinos
    FROM vw_treinos_enriquecidos
    WHERE inicio IS NOT NULL
    GROUP BY 1
)
SELECT
    s.semana,
    COALESCE(t.qtd_treinos, 0) AS qtd_treinos
FROM semanas s
         LEFT JOIN treinos t
                   ON t.semana = s.semana
ORDER BY s.semana DESC;
