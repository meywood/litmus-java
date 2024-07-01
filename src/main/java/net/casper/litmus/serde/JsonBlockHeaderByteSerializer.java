package net.casper.litmus.serde;

import com.casper.sdk.model.block.JsonBlockHeader;
import dev.oak3.sbs4j.SerializerBuffer;
import dev.oak3.sbs4j.exception.ValueSerializationException;
import net.casper.litmus.exception.ByteSerializeException;

import java.math.BigInteger;

/**
 * @author ian@meywood.com
 */
public class JsonBlockHeaderByteSerializer implements ByteSerializer<JsonBlockHeader> {

    private final DigestByteSerializer digestByteSerializer = new DigestByteSerializer();
    private final JsonEraEndByteSerializer eraEndByteSerializer = new JsonEraEndByteSerializer();
    private final VersionByteSerializer versionByteSerializer = new VersionByteSerializer();

    @Override
    public byte[] toBytes(final JsonBlockHeader blockHeader) {
        var ser = new SerializerBuffer();

        try {
            ser.writeByteArray(digestByteSerializer.toBytes(blockHeader.getParentHash()));
            ser.writeByteArray(digestByteSerializer.toBytes(blockHeader.getStateRootHash()));
            ser.writeByteArray(digestByteSerializer.toBytes(blockHeader.getBodyHash()));
            ser.writeBool(blockHeader.isRandomBit());
            ser.writeByteArray(digestByteSerializer.toBytes(blockHeader.getAccumulatedSeed()));
            ser.writeByteArray(eraEndByteSerializer.toBytes(blockHeader.getEraEnd()));
            ser.writeU64(BigInteger.valueOf(blockHeader.getTimeStamp().getTime()));
            ser.writeU64(BigInteger.valueOf(blockHeader.getEraId()));
            ser.writeU64(BigInteger.valueOf(blockHeader.getHeight()));
            ser.writeByteArray(versionByteSerializer.toBytes(blockHeader.getProtocolVersion()));
        } catch (ValueSerializationException e) {
            throw new ByteSerializeException(e);
        }

        return ser.toByteArray();
    }
}
