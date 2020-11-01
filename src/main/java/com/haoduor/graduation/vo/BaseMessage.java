package com.haoduor.graduation.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseMessage implements Message{
    private int id;
    private String message;

    @Override
    public Object invoke(int id, String message) {
        return new BaseMessage(id, message);
    }
}
