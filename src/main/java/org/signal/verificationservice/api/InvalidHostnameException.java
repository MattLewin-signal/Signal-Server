package org.signal.verificationservice.api;

public class InvalidHostnameException extends Exception {

    public InvalidHostnameException(String message) { super(message); }

    public InvalidHostnameException(Throwable cause) { super(cause); }
}
