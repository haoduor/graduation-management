package com.haoduor.graduation.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 重复id 错误
 */

@Data
@AllArgsConstructor
public class DuplicateIdException extends Exception{
    Object id;
}
