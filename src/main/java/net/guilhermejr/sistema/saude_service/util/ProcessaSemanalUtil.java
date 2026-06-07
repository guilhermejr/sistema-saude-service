package net.guilhermejr.sistema.saude_service.util;

import lombok.extern.log4j.Log4j2;
import net.guilhermejr.sistema.saude_service.api.response.RetornoResponse;
import net.guilhermejr.sistema.saude_service.api.response.RetornoSonoResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Component
public class ProcessaSemanalUtil {

    public List<RetornoResponse> processar(List<Object[]> dados) {

        List<RetornoResponse> retorno = new ArrayList<>();
        dados.forEach(res -> {
            RetornoResponse retornoResponse = new RetornoResponse();

            retornoResponse.setPeriodo(res[0].toString());
            retornoResponse.setValor(new BigDecimal(String.valueOf(res[1])));

            // Verifica se existe res[2]
            if (res.length > 2 && res[2] != null) {
                retornoResponse.setValor2(new BigDecimal(String.valueOf(res[2])));
            }

            retorno.add(retornoResponse);
        });

        return retorno;
    }

    public List<RetornoSonoResponse> processarSono(List<Object[]> dados) {

        List<RetornoSonoResponse> retorno = new ArrayList<>();
        dados.forEach(res -> {
            RetornoSonoResponse retornoSonoResponse = new RetornoSonoResponse();

            retornoSonoResponse.setPeriodo(res[0].toString());
            retornoSonoResponse.setTempo(res[1].toString());
            retornoSonoResponse.setHorasSono(new BigDecimal(String.valueOf(res[2])));
            retornoSonoResponse.setHorasSonoUltimos8Dias(new BigDecimal(String.valueOf(res[3])));
            retorno.add(retornoSonoResponse);
        });

        return retorno;
    }

}
