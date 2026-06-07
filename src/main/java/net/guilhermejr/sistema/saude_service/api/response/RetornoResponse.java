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
public class RetornoResponse {

    private String periodo;
    private BigDecimal valor;
    private BigDecimal valor2;

}
