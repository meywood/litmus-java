package net.casper.litmus.exception;

/**
 * Exception thrown when a Deploys body hash fails verification
 * @author Carl Norburn
 */
public class DeployVerificationException extends RuntimeException {
    public DeployVerificationException(String message) {
        super(message);
    }

}
