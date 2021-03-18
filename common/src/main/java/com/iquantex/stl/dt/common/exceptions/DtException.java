package com.iquantex.stl.dt.common.exceptions;

public class DtException extends RuntimeException {

    public DtException(String message) {
        super(message);
    }

    public DtException(Throwable cause) {
        super(cause);
    }
}
