package net.casper.litmus.serder;

import com.casper.sdk.model.block.JsonBlockBody;
import com.casper.sdk.model.clvalue.serde.Target;
import com.casper.sdk.model.common.Digest;
import dev.oak3.sbs4j.SerializerBuffer;

import java.util.Collection;

/**
 * @author ian@meywood.com
 */
public class JsonBlockBodyByteSerializer implements ByteSerializer<JsonBlockBody> {

    public byte[] toBytes(final JsonBlockBody blockBody) {
        var ser = new SerializerBuffer();

        if (blockBody.getProposer() == null) {
            ser.writeU8((byte) 0);
        } else {
            blockBody.getProposer().serialize(ser, Target.JSON);
        }

        writeDigests(ser, blockBody.getDeployHashes());
        writeDigests(ser, blockBody.getTransferHashes());
        return ser.toByteArray();
    }

    private void writeDigests(final SerializerBuffer ser, final Collection<String> digests) {
        if (digests != null) {
            ser.writeU32((long) digests.size());
            digests.forEach(digest -> writeDigest(ser, digest));
        } else {
            ser.writeU32(0L);
        }
    }

    private void writeDigest(SerializerBuffer ser, final String digest) {
        ser.writeByteArray(new Digest(digest).getDigest());
    }
}
