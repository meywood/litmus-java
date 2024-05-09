package net.casper.litmus;

import com.casper.sdk.model.block.JsonBlock;
import com.casper.sdk.model.block.JsonBlockBody;
import com.casper.sdk.model.common.Digest;
import com.syntifi.crypto.key.hash.Blake2b;
import net.casper.litmus.serder.JsonBlockBodyByteSerializer;
import net.casper.litmus.serder.JsonBlockHeaderByteSerializer;

/**
 * @author ian@meywood.com
 */
public class Verifier {

    private final JsonBlockHeaderByteSerializer jsonBlockHeaderByteSerializer = new JsonBlockHeaderByteSerializer();
    private final JsonBlockBodyByteSerializer jsonBlockBodyByteSerializer = new JsonBlockBodyByteSerializer();

    public void verifyBlock(final JsonBlock block) {
        assert block != null : "Block cannot be null";

        var headerBytes = jsonBlockHeaderByteSerializer.toBytes(block.getHeader());

        verifyBlockBody(block.getHeader().getBodyHash(), block.getBody());

        var blockHash = toDigest(headerBytes);

        if (!blockHash.equals(block.getHash())) {
            throw new UpdateException(
                    "Block  hash does not match expected hash \nExpected: "
                            + block.getHash()
                            + "\nActual: "
                            + blockHash
            );
        }
    }

    void verifyBlockBody(final Digest expectedBodyHash, final JsonBlockBody blockBody) {
        assert expectedBodyHash != null : "Expected body hash  cannot be null";
        assert blockBody != null : "Block body cannot be null";

        var bytes = jsonBlockBodyByteSerializer.toBytes(blockBody);
        var bodyHash = toDigest(bytes);

        if (!bodyHash.equals(expectedBodyHash)) {
            throw new UpdateException(
                    "Block body hash does not match expected hash \nExpected: "
                            + expectedBodyHash
                            + "\nActual: "
                            + bodyHash
            );
        }
    }

    private Digest toDigest(final byte[] bytes) {
        return Digest.digestFromBytes(Blake2b.digest(bytes, 32));
    }
}
