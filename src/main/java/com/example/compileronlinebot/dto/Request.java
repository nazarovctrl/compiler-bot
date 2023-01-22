package com.example.compileronlinebot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
public class Request {
    private Long userId;
    private String code;
    private Integer messageId;
}
