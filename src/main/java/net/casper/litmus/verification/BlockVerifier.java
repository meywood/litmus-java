package net.casper.litmus.verification;

import com.casper.sdk.model.block.JsonBlock;
import com.casper.sdk.model.block.JsonBlockBody;
import com.casper.sdk.model.common.Digest;
import com.syntifi.crypto.key.hash.Blake2b;
import net.casper.litmus.exception.BlockVerificationException;
import net.casper.litmus.serde.JsonBlockBodyByteSerializer;
import net.casper.litmus.serde.JsonBlockHeaderByteSerializer;

/**
 * Verifies a block by hashing the block header and comparing the hash to the block hash.
 *
 * @author ian@meywood.com
 */
public class BlockVerifier {

    private final JsonBlockHeaderByteSerializer jsonBlockHeaderByteSerializer = new JsonBlockHeaderByteSerializer();
    private final JsonBlockBodyByteSerializer jsonBlockBodyByteSerializer = new JsonBlockBodyByteSerializer();

    /**
     * Verifies the block by byte serializing the block and comparing the hashed bytes to the provided block hash.
     *
     * @param block The block to verify
     * @throws BlockVerificationException If the block hash does not match the expected hash or the body hash does not
     *                                    match the expected hash
     */
    public void verifyBlock(final JsonBlock block) {
        assert block != null : "Block cannot be null";

        verifyBlockBody(block.getHeader().getBodyHash(), block.getBody());

        var blockHash = toDigest(jsonBlockHeaderByteSerializer.toBytes(block.getHeader()));

        if (!blockHash.equals(block.getHash())) {
            throw new BlockVerificationException(
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

        var bodyHash = toDigest(jsonBlockBodyByteSerializer.toBytes(blockBody));

        if (!bodyHash.equals(expectedBodyHash)) {
            throw new BlockVerificationException(
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
