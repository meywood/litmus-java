package net.casper.litmus.serde;

import dev.oak3.sbs4j.SerializerBuffer;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * @author ian@meywood.com
 */
public class CollectionByteSerializer {

    public <T> void toBytes(final SerializerBuffer ser, final Collection<T> collection, final Consumer<T> consumer) {

        if (collection == null) {
            ser.writeU32(0L);
        } else {
            ser.writeU32((long) collection.size());
            for (T t : collection) {
                consumer.accept(t);
            }
        }
    }
}
