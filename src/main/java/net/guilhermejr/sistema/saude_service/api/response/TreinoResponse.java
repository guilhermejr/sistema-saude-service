package net.guilhermejr.sistema.saude_service.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TreinoResponse {

    private Long id;
    private LocalDateTime criado;

}
