package net.guilhermejr.sistema.saude_service.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResumoTreinosResponse {

    public Long quantidade_treinos;
    public Long distancia;
    public Long energia_ativa;
    public Long duracao;

}
