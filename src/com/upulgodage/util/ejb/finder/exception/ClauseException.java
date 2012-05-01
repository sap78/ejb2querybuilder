/*
 * Created on Aug 3, 2005
 */
package com.upulgodage.util.ejb.finder.exception;

/**
 * @author Upul Godage <uigodage@yahoo.com>
 */
public class ClauseException extends RuntimeException {
    public ClauseException() {
        super();
    }
    public ClauseException(String message) {
        super(message);
    }
    public ClauseException(String message, Throwable nestedThrowable) {
        super(message, nestedThrowable);
    }
    public ClauseException(Throwable nestedThrowable) {
        super(nestedThrowable);
    }
}

//!end
