package com.qaguru.lesson5.services.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("unused")
@Setter
@Getter
public class Sample {

    @Expose
    private Batters batters;
    @Expose
    private String id;
    @Expose
    private String name;
    @Expose
    private Double ppu;
    @Expose
    private List<Topping> topping;
    @Expose
    private String type;

}
