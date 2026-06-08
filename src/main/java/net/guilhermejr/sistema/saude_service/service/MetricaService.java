package net.guilhermejr.sistema.saude_service.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.guilhermejr.sistema.saude_service.api.mapper.MetricaMapper;
import net.guilhermejr.sistema.saude_service.api.response.MetricaResponse;
import net.guilhermejr.sistema.saude_service.api.response.RetornoResponse;
import net.guilhermejr.sistema.saude_service.api.response.RetornoSonoResponse;
import net.guilhermejr.sistema.saude_service.domain.repository.MetricaRepository;
import net.guilhermejr.sistema.saude_service.util.ProcessaSemanalUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@AllArgsConstructor
public class MetricaService {

    private final MetricaRepository metricaRepository;
    private final ProcessaSemanalUtil processaSemanalUtil;
    private final MetricaMapper metricaMapper;

    // --- tempo_de_pe --------------------------------------------------------
    public List<RetornoResponse> tempo_de_pe() {

        List<Object[]> dados = metricaRepository.tempo_de_pe();
        return processaSemanalUtil.processar(dados);

    }

    // --- exposicao_ao_sol ---------------------------------------------------
    public List<RetornoResponse> exposicao_ao_sol() {

        List<Object[]> dados = metricaRepository.exposicao_ao_sol();
        return processaSemanalUtil.processar(dados);

    }

    // --- frequencia_cardiaca_em_repouso_media_movel_8_dias ------------------
    public List<RetornoResponse> frequencia_cardiaca_em_repouso_media_movel_8_dias() {

        List<Object[]> dados = metricaRepository.frequencia_cardiaca_em_repouso_media_movel_8_dias();
        return processaSemanalUtil.processar(dados);

    }

    // --- passos_medios_diarios_media_movel_8_dias ---------------------------
    public List<RetornoResponse> passos_medios_diarios_media_movel_8_dias() {

        List<Object[]> dados = metricaRepository.passos_medios_diarios_media_movel_8_dias();
        return processaSemanalUtil.processar(dados);

    }

    // --- sono_profundo_medio_media_movel_8_dias ---------------------------
    public List<RetornoSonoResponse> sono_profundo_medio_media_movel_8_dias() {

        List<Object[]> dados = metricaRepository.sono_profundo_medio_media_movel_8_dias();
        return processaSemanalUtil.processarSono(dados);

    }

    // --- sono_total_medio_media_movel_8_dias --------------------------------
    public List<RetornoSonoResponse> sono_total_medio_media_movel_8_dias() {

        List<Object[]> dados = metricaRepository.sono_total_medio_media_movel_8_dias();
        return processaSemanalUtil.processarSono(dados);

    }

    // --- maisRecente --------------------------------------------------------
    public MetricaResponse maisRecente() {

        return metricaMapper.mapObject(metricaRepository.maisRecente());

    }

}
