package net.casper.litmus;

import com.casper.sdk.model.block.JsonBlock;
import com.casper.sdk.model.block.JsonBlockBody;
import com.casper.sdk.model.common.Digest;
import com.syntifi.crypto.key.hash.Blake2b;
import net.casper.litmus.serder.JsonBlockBodyByteSerializer;

/**
 * @author ian@meywood.com
 */
public class Verifier {

    void verifyBlock(final JsonBlock block) {
        assert block != null : "Block cannot be null";

        throw new RuntimeException("Not implemented");
    }

    void verifyBlockBody(final Digest expectedBodyHash, final JsonBlockBody blockBody) {
        assert expectedBodyHash != null : "Expected body hash  cannot be null";
        assert blockBody != null : "Block body cannot be null";

        var bytes = new JsonBlockBodyByteSerializer().toBytes(blockBody);
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