package com.rk.uberApp.cofiguration;

import com.rk.uberApp.dtos.PointDto;
import com.rk.uberApp.utils.GeometryUtils;
import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper getModelMapper(){

        ModelMapper modelMapper = new ModelMapper();

        modelMapper.typeMap(PointDto.class, Point.class).setConverter(context -> {
            PointDto pointDto = context.getSource();
            return GeometryUtils.createPoint(pointDto);
        });

        modelMapper.typeMap(Point.class, PointDto.class).setConverter( context -> {
            Point point = context.getSource();
            double[] coordinates = {point.getX(),point.getY()};
            return new PointDto(coordinates);
        });

        return modelMapper;
    }

}
