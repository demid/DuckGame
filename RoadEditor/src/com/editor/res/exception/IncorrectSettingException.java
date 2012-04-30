package com.editor.res.exception;

/**
 * Date: 25.04.12
 * Time: 15:28
 *
 * @author: Alexey
 */
public class IncorrectSettingException extends RuntimeException{
    public IncorrectSettingException() {
    }

    public IncorrectSettingException(String message) {
        super(message);
    }

    public IncorrectSettingException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectSettingException(Throwable cause) {
        super(cause);
    }
}
