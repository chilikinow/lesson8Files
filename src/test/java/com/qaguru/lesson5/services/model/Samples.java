package com.qaguru.lesson5.services.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("unused")
@Setter
@Getter
public class Samples {

    @Expose
    private List<Sample> samples;

}
