package net.guilhermejr.sistema.saude_service.domain.repository;

import net.guilhermejr.sistema.saude_service.domain.entity.Treino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TreinoRepository extends JpaRepository<Treino, Long> {

    @Query(value = "SELECT id, criado FROM treinos ORDER BY inicio DESC LIMIT 1", nativeQuery = true)
    Treino mais_recente();

    @Query(value = "SELECT TO_CHAR(semana, 'DD/MM') AS semana, distancia_total FROM vw_distancia_total_por_semana", nativeQuery = true)
    List<Object[]> distancia_total_por_semana();

    @Query(value = "SELECT TO_CHAR(semana, 'DD/MM') AS semana, energia_ativa_total FROM vw_energia_ativa_por_semana", nativeQuery = true)
    List<Object[]> energia_ativa_por_semana();

    @Query(value = "SELECT TO_CHAR(semana, 'DD/MM') AS semana, total_minutos FROM vw_tempo_total_treino_por_semana", nativeQuery = true)
    List<Object[]> tempo_total_treino_por_semana();

    @Query(value = "SELECT TO_CHAR(semana, 'DD/MM') AS semana, qtd_treinos FROM vw_treino_por_semana", nativeQuery = true)
    List<Object[]> treino_por_semana();

    @Query(value = "SELECT COUNT(*) AS quantidade_treinos, SUM(distancia) AS distancia, SUM(energia_ativa) AS energia_ativa, SUM(EXTRACT(EPOCH FROM duracao) / 60) AS duracao FROM treinos WHERE inicio >= CURRENT_DATE - INTERVAL '6 days'", nativeQuery = true)
    List<Object[]> resumo_treinos();

}
