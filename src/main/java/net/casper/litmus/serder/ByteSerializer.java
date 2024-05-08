package net.casper.litmus.serder;

/**
 * @author ian@meywood.com
 */
public interface  ByteSerializer<T> {

    byte[] toBytes(final T t);
}
