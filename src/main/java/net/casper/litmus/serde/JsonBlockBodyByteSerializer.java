package net.casper.litmus.serde;

import com.casper.sdk.model.block.JsonBlockBody;
import com.casper.sdk.model.clvalue.serde.Target;
import dev.oak3.sbs4j.SerializerBuffer;

/**
 * @author ian@meywood.com
 */
public class JsonBlockBodyByteSerializer implements ByteSerializer<JsonBlockBody> {

    private final DigestByteSerializer digestByteSerializer = new DigestByteSerializer();

    @Override
    public byte[] toBytes(final JsonBlockBody blockBody) {
        var ser = new SerializerBuffer();

        if (blockBody.getProposer() == null) {
            ser.writeU8((byte) 0);
        } else {
            blockBody.getProposer().serialize(ser, Target.JSON);
        }

        ser.writeByteArray(digestByteSerializer.toBytes(blockBody.getDeployHashes()));
        ser.writeByteArray(digestByteSerializer.toBytes(blockBody.getTransferHashes()));

        return ser.toByteArray();
    }
}
