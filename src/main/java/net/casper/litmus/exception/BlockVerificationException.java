package net.casper.litmus.exception;

/**
 * Exception thrown when a block fails verification. That is it's hash or body hard are not valid.
 *
 * @author ian@meywood.com
 */
public class BlockVerificationException extends RuntimeException {
    public BlockVerificationException(String message) {
        super(message);
    }
}
