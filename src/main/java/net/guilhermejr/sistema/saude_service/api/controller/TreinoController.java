package net.guilhermejr.sistema.saude_service.api.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.guilhermejr.sistema.saude_service.api.response.RetornoResponse;
import net.guilhermejr.sistema.saude_service.api.response.TreinoResponse;
import net.guilhermejr.sistema.saude_service.service.TreinoService;
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
@RequestMapping("/treinos")
public class TreinoController {

    private final TreinoService treinoService;

    // --- distancia_total_por_semana ----------------------------------------
    @GetMapping("/distancia_total_por_semana")
    public ResponseEntity<List<RetornoResponse>> distancia_total_por_semana() {

        log.info("Retornando distancia_total_por_semana");
        List<RetornoResponse> retornoResponse = treinoService.distancia_total_por_semana();
        return ResponseEntity.status(HttpStatus.OK).body(retornoResponse);

    }

    // --- energia_ativa_por_semana -------------------------------------------
    @GetMapping("/energia_ativa_por_semana")
    public ResponseEntity<List<RetornoResponse>> energia_ativa_por_semana() {

        log.info("Retornando energia_ativa_por_semana");
        List<RetornoResponse> retornoResponse = treinoService.energia_ativa_por_semana();
        return ResponseEntity.status(HttpStatus.OK).body(retornoResponse);

    }

    // --- tempo_total_treino_por_semana --------------------------------------
    @GetMapping("/tempo_total_treino_por_semana")
    public ResponseEntity<List<RetornoResponse>> tempo_total_treino_por_semana() {

        log.info("Retornando tempo_total_treino_por_semana");
        List<RetornoResponse> retornoResponse = treinoService.tempo_total_treino_por_semana();
        return ResponseEntity.status(HttpStatus.OK).body(retornoResponse);

    }

    // --- treino_por_semana --------------------------------------------------
    @GetMapping("/treino_por_semana")
    public ResponseEntity<List<RetornoResponse>> treino_por_semana() {

        log.info("Retornando treino_por_semana");
        List<RetornoResponse> retornoResponse = treinoService.treino_por_semana();
        return ResponseEntity.status(HttpStatus.OK).body(retornoResponse);

    }

    // --- maisRecente --------------------------------------------------------
    @GetMapping("/maisRecente")
    public ResponseEntity<TreinoResponse> maisRecente() {

        log.info("Retornando maisRecente");
        TreinoResponse treinoResponse = treinoService.maisRecente();
        return ResponseEntity.status(HttpStatus.OK).body(treinoResponse);

    }

}
