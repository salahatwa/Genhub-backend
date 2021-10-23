package com.genhub.blogapi.files;

/**
 * repeat type exception
 *
 * @author ssatwa
 * @date 3/13/20 5:03 PM
 */
public class RepeatTypeException extends RuntimeException {
    public RepeatTypeException(String message) {
        super(message);
    }

    public RepeatTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
