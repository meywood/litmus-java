package net.casper.litmus;

import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * @author ian@meywood.com
 */
class VerifierTest {


    @Test
    void verifyBlock() {

        var blockByHeight = new BlockCache().getBlockByHeight(0L);
        new Verifier().verifyBlock(blockByHeight);
    }

    @Test
    void verifyBlockBody() throws IOException {

        var blockByHeight = new BlockCache().getBlockByHeight(0L);
        new Verifier().verifyBlockBody(blockByHeight.getHeader().getBodyHash(), blockByHeight.getBody());
    }
}
