package com.haoduor.graduation.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DataMessage implements Message{
    private int id;
    private String message;
    private Object data;

    public DataMessage(int id, String message) {
        this.id = id;
        this.message = message;
    }

    @Override
    public Object invoke(int id, String message) {
        return new DataMessage(id, message);
    }
}
