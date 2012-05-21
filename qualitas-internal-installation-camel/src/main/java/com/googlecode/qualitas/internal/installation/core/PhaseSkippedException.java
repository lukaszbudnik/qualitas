package com.googlecode.qualitas.internal.installation.core;

public class PhaseSkippedException extends Exception {

    private static final long serialVersionUID = -7313177593978190212L;

    public PhaseSkippedException() {
        super();
    }

    public PhaseSkippedException(String message, Throwable cause) {
        super(message, cause);
    }

    public PhaseSkippedException(String message) {
        super(message);
    }

    public PhaseSkippedException(Throwable cause) {
        super(cause);
    }

}
