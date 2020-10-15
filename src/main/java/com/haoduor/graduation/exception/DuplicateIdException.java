package com.haoduor.graduation.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DuplicateIdException extends Exception{
    long id;
}
