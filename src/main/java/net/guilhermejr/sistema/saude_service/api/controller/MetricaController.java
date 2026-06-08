package net.guilhermejr.sistema.saude_service.api.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.guilhermejr.sistema.saude_service.api.response.MetricaResponse;
import net.guilhermejr.sistema.saude_service.api.response.RetornoResponse;
import net.guilhermejr.sistema.saude_service.api.response.RetornoSonoResponse;
import net.guilhermejr.sistema.saude_service.service.MetricaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@AllArgsConstructor
@RestController
@PreAuthorize("hasAnyRole('SAUDE')")
@RequestMapping("/metricas")
public class MetricaController {

    private final MetricaService metricaService;

    // --- tempo_de_pe --------------------------------------------------------
    @GetMapping("/tempo_de_pe")
    public ResponseEntity<List<RetornoResponse>> tempo_de_pe() {

        log.info("Retornando tempo_de_pe");
        List<RetornoResponse> retornoResponse = metricaService.tempo_de_pe();
        return ResponseEntity.status(HttpStatus.OK).body(retornoResponse);

    }

    // --- exposicao_ao_sol ---------------------------------------------------
    @GetMapping("/exposicao_ao_sol")
    public ResponseEntity<List<RetornoResponse>> exposicao_ao_sol() {

        log.info("Retornando exposicao_ao_sol");
        List<RetornoResponse> retornoResponse = metricaService.exposicao_ao_sol();
        return ResponseEntity.status(HttpStatus.OK).body(retornoResponse);

    }

    // --- frequencia_cardiaca_em_repouso_media_movel_8_dias ------------------
    @GetMapping("/frequencia_cardiaca_em_repouso_media_movel_8_dias")
    public ResponseEntity<List<RetornoResponse>> frequencia_cardiaca_em_repouso_media_movel_8_dias() {

        log.info("Retornando frequencia_cardiaca_em_repouso_media_movel_8_dias");
        List<RetornoResponse> retornoResponse = metricaService.frequencia_cardiaca_em_repouso_media_movel_8_dias();
        return ResponseEntity.status(HttpStatus.OK).body(retornoResponse);

    }

    // --- passos_medios_diarios_media_movel_8_dias ---------------------------
    @GetMapping("/passos_medios_diarios_media_movel_8_dias")
    public ResponseEntity<List<RetornoResponse>> passos_medios_diarios_media_movel_8_dias() {

        log.info("Retornando passos_medios_diarios_media_movel_8_dias");
        List<RetornoResponse> retornoResponse = metricaService.passos_medios_diarios_media_movel_8_dias();
        return ResponseEntity.status(HttpStatus.OK).body(retornoResponse);

    }

    // --- sono_profundo_medio_media_movel_8_dias -----------------------------
    @GetMapping("/sono_profundo_medio_media_movel_8_dias")
    public ResponseEntity<List<RetornoSonoResponse>> sono_profundo_medio_media_movel_8_dias() {

        log.info("Retornando sono_profundo_medio_media_movel_8_dias");
        List<RetornoSonoResponse> retornoSonoResponse = metricaService.sono_profundo_medio_media_movel_8_dias();
        return ResponseEntity.status(HttpStatus.OK).body(retornoSonoResponse);

    }

    // --- sono_total_medio_media_movel_8_dias --------------------------------
    @GetMapping("/sono_total_medio_media_movel_8_dias")
    public ResponseEntity<List<RetornoSonoResponse>> sono_total_medio_media_movel_8_dias() {

        log.info("Retornando sono_total_medio_media_movel_8_dias");
        List<RetornoSonoResponse> retornoSonoResponse = metricaService.sono_total_medio_media_movel_8_dias();
        return ResponseEntity.status(HttpStatus.OK).body(retornoSonoResponse);

    }

    // --- maisRecente --------------------------------------------------------
    @GetMapping("/maisRecente")
    public ResponseEntity<MetricaResponse> maisRecente() {

        log.info("Retornando maisRecente");
        MetricaResponse metricaResponse = metricaService.maisRecente();
        return ResponseEntity.status(HttpStatus.OK).body(metricaResponse);

    }

}
