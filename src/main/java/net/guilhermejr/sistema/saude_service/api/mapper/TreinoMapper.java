package net.guilhermejr.sistema.saude_service.api.mapper;

import net.guilhermejr.sistema.saude_service.api.response.TreinoResponse;
import net.guilhermejr.sistema.saude_service.config.ModelMapperConfig;
import net.guilhermejr.sistema.saude_service.domain.entity.Treino;
import org.springframework.stereotype.Component;

@Component
public class TreinoMapper extends ModelMapperConfig {

    public TreinoResponse mapObject(Treino treino) {
        return this.mapObject(treino, TreinoResponse.class);
    }

}
