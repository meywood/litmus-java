package net.casper.litmus.serde;

/**
 * Interface for all byte serializers.
 *
 * @author ian@meywood.com
 */
public interface ByteSerializer<T> {

    byte[] toBytes(final T t);
}
