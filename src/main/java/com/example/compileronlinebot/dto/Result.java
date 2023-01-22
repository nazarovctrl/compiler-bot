package com.example.compileronlinebot.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result {
    private Double cpuTime;
    private Long memory;
    private String output;
    private Integer statusCode;
}
