package net.guilhermejr.sistema.saude_service.api.mapper;

import net.guilhermejr.sistema.saude_service.api.response.MetricaResponse;
import net.guilhermejr.sistema.saude_service.config.ModelMapperConfig;
import net.guilhermejr.sistema.saude_service.domain.entity.Metrica;
import org.springframework.stereotype.Component;

@Component
public class MetricaMapper extends ModelMapperConfig {

    public MetricaResponse mapObject(Metrica metrica) {
        return this.mapObject(metrica, MetricaResponse.class);
    }

}
