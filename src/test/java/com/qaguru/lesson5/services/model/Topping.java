package com.qaguru.lesson5.services.model;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("unused")
@Setter
@Getter
public class Topping {

    @Expose
    private String id;
    @Expose
    private String type;

}
