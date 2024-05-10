package net.casper.litmus.serde;

import com.casper.sdk.model.common.Digest;
import dev.oak3.sbs4j.SerializerBuffer;

import java.util.Collection;

/**
 * @author ian@meywood.com
 */
public class DigestByteSerializer implements ByteSerializer<Digest> {

    private final CollectionByteSerializer collectionByteSerializer = new CollectionByteSerializer();

    @Override
    public byte[] toBytes(final Digest digest) {
        var ser = new SerializerBuffer();
        ser.writeByteArray(digest.getDigest());
        return ser.toByteArray();
    }

    public byte[] toBytes(final Collection<String> digests) {
        var ser = new SerializerBuffer();
        collectionByteSerializer.toBytes(
                ser,
                digests,
                digest -> ser.writeByteArray(toBytes(new Digest(digest)))
        );
        return ser.toByteArray();
    }
}
