package net.casper.litmus.verification;

import net.casper.litmus.BlockCache;
import org.junit.jupiter.api.Test;

/**
 * @author ian@meywood.com
 */
class BlockVerifierTest {

    @Test
    void verifyBlock() {

        var verifier = new BlockVerifier();

        for (long i = 0; i < 100; i++) {
            var blockByHeight = new BlockCache().getBlockByHeight(i);
            verifier.verifyBlock(blockByHeight);
        }
    }

    @Test
    void verifyBlockBody() {

        var verifier = new BlockVerifier();

        for (long i = 0; i < 100; i++) {
            var blockByHeight = new BlockCache().getBlockByHeight(i);
            verifier.verifyBlockBody(blockByHeight.getHeader().getBodyHash(), blockByHeight.getBody());
        }
    }
}
