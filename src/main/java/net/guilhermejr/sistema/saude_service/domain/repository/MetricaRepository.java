package net.guilhermejr.sistema.saude_service.domain.repository;

import net.guilhermejr.sistema.saude_service.domain.entity.Metrica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MetricaRepository extends JpaRepository<Metrica, Long> {

    @Query(value = "SELECT id, criado FROM metricas ORDER BY data DESC LIMIT 1", nativeQuery = true)
    Metrica mais_recente();

    @Query(value = "SELECT TO_CHAR(data, 'DD/MM') AS data, tempo_em_pe FROM vw_tempo_de_pe", nativeQuery = true)
    List<Object[]> tempo_de_pe();

    @Query(value = "SELECT TO_CHAR(data, 'DD/MM') AS data, exposicao_ao_sol FROM vw_exposicao_ao_sol", nativeQuery = true)
    List<Object[]> exposicao_ao_sol();

    @Query(value = "SELECT TO_CHAR(data, 'DD/MM') AS data, frequencia_cardiaca_em_repouso, media_movel_8d_fc_repouso FROM vw_frequencia_cardiaca_em_repouso_media_movel_8_dias", nativeQuery = true)
    List<Object[]> frequencia_cardiaca_em_repouso_media_movel_8_dias();

    @Query(value = "SELECT TO_CHAR(data, 'DD/MM') AS data, passos, media_movel_8d_passos FROM vw_passos_medios_diarios_media_movel_8_dias", nativeQuery = true)
    List<Object[]> passos_medios_diarios_media_movel_8_dias();

    @Query(value = "SELECT TO_CHAR(data, 'DD/MM') AS data, sono_profundo, sono_profundo_horas, media_movel_8d_sono_profundo_horas FROM vw_sono_profundo_medio_media_movel_8_dias", nativeQuery = true)
    List<Object[]> sono_profundo_medio_media_movel_8_dias();

    @Query(value = "SELECT TO_CHAR(data, 'DD/MM') AS data, sono_total, sono_total_horas, media_movel_8d_sono_total_horas FROM vw_sono_total_medio_media_movel_8_dias", nativeQuery = true)
    List<Object[]> sono_total_medio_media_movel_8_dias();


}
