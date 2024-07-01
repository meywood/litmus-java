package net.casper.litmus.exception;

/**
 * Exception thrown on byte serialization error.
 *
 * @author ian@meywood.com
 */
public class ByteSerializeException extends RuntimeException {
    public ByteSerializeException(Throwable cause) {
        super(cause);
    }
}
