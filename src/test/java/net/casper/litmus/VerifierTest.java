package net.casper.litmus;

import org.junit.jupiter.api.Test;

/**
 * @author ian@meywood.com
 */
class VerifierTest {

    @Test
    void verifyBlock() {

        var verifier = new Verifier();

        for (int i = 0; i < 100; i++) {
            var blockByHeight = new BlockCache().getBlockByHeight((long) i);
            verifier.verifyBlock(blockByHeight);
        }
    }

    @Test
    void verifyBlockBody() {

        var verifier = new Verifier();

        for (int i = 0; i < 100; i++) {
            var blockByHeight = new BlockCache().getBlockByHeight((long) i);
            verifier.verifyBlockBody(blockByHeight.getHeader().getBodyHash(), blockByHeight.getBody());
        }
    }
}
