package com.ericson.tiendasmartech.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ControllerResponse {
    private int status;
    private String message;
    private Object data;
}
