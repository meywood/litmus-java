package net.casper.litmus.serde;

import com.casper.sdk.model.common.Digest;
import dev.oak3.sbs4j.SerializerBuffer;

import java.util.Collection;
import java.util.stream.Collectors;

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
        return collectionByteSerializer.toBytes(
                digests.stream().map(Digest::new).collect(Collectors.toList()),
                this
        );
    }
}
