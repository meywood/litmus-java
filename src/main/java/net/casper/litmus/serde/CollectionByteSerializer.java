package net.casper.litmus.serde;

import dev.oak3.sbs4j.SerializerBuffer;

import java.util.Collection;

/**
 * @author ian@meywood.com
 */
public class CollectionByteSerializer {


    public <T> byte[] toBytes(final Collection<T> collection, final ByteSerializer<T> itemSerializer) {
        var ser = new SerializerBuffer();

        if (collection == null) {
            ser.writeU32(0L);
        } else {
            ser.writeU32((long) collection.size());
            for (T t : collection) {
                ser.writeByteArray(itemSerializer.toBytes(t));
            }
        }
        return ser.toByteArray();
    }
}
