package net.guilhermejr.sistema.saude_service.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RetornoSonoResponse {

    private String periodo;
    private String tempo;
    private BigDecimal horasSono;
    private BigDecimal horasSonoUltimos8Dias;

}
