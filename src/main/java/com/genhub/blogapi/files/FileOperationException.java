package com.genhub.blogapi.files;

/**
 * File operation exception.
 *
 * @author ssatwa
 * @date 3/27/19
 */
public class FileOperationException  extends RuntimeException {
    public FileOperationException(String message) {
        super(message);
    }

    public FileOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
