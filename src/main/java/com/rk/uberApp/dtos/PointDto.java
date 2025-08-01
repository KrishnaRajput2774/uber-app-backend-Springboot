package com.rk.uberApp.dtos;

import lombok.Data;


@Data
public class PointDto {
    double[] coordinates;
    String type = "Point";

    public PointDto(double[] coordinates) {
        this.coordinates = coordinates;
    }
}