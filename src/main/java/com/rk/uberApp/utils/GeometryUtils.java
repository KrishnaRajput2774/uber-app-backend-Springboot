package com.rk.uberApp.utils;


import ch.qos.logback.core.CoreConstants;
import com.rk.uberApp.dtos.PointDto;
import org.locationtech.jts.geom.*;

public class GeometryUtils {

    public static Point createPoint(PointDto pointDto) {

        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
        Coordinate coordinate = new Coordinate(
                pointDto.getCoordinates()[0],
                pointDto.getCoordinates()[1]);

        return geometryFactory.createPoint(coordinate);
    }


}
