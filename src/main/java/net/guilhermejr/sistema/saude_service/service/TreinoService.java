package net.guilhermejr.sistema.saude_service.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.guilhermejr.sistema.saude_service.api.mapper.TreinoMapper;
import net.guilhermejr.sistema.saude_service.api.response.RetornoResponse;
import net.guilhermejr.sistema.saude_service.api.response.TreinoResponse;
import net.guilhermejr.sistema.saude_service.domain.entity.Treino;
import net.guilhermejr.sistema.saude_service.domain.repository.TreinoRepository;
import net.guilhermejr.sistema.saude_service.util.ProcessaSemanalUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@AllArgsConstructor
public class TreinoService {

    private final TreinoRepository treinoRepository;
    private final ProcessaSemanalUtil processaSemanalUtil;
    private final TreinoMapper treinoMapper;

    // --- distancia_total_por_semana -----------------------------------------
    public List<RetornoResponse> distancia_total_por_semana() {

        List<Object[]> dados = treinoRepository.distancia_total_por_semana();
        return processaSemanalUtil.processar(dados);

    }

    // --- energia_ativa_por_semana -------------------------------------------
    public List<RetornoResponse> energia_ativa_por_semana() {

        List<Object[]> dados = treinoRepository.energia_ativa_por_semana();
        return processaSemanalUtil.processar(dados);

    }

    // --- tempo_total_treino_por_semana --------------------------------------
    public List<RetornoResponse> tempo_total_treino_por_semana() {

        List<Object[]> dados = treinoRepository.tempo_total_treino_por_semana();
        return processaSemanalUtil.processar(dados);

    }

    // --- treino_por_semana --------------------------------------------------
    public List<RetornoResponse> treino_por_semana() {

        List<Object[]> dados = treinoRepository.treino_por_semana();
        return processaSemanalUtil.processar(dados);

    }

    // --- mais_recente -------------------------------------------------------
    public TreinoResponse mais_recente() {

        return treinoMapper.mapObject(treinoRepository.mais_recente());

    }

}
