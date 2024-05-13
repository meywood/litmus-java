package net.casper.litmus.verification;

import com.casper.sdk.model.block.JsonBlock;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.casper.litmus.BlockCache;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author ian@meywood.com
 */
class BlockVerifierTest {

    @Test
    void verifyBlock() throws IOException {

        var verifier = new BlockVerifier();

        for (long i = 0; i < 100; i++) {
            var blockByHeight = new BlockCache().getBlockByHeight(i);
            verifier.verifyBlock(blockByHeight);
        }

        final InputStream in = BlockVerifierTest.class.getResourceAsStream("/mainnet/blocks/block-0.json");
        JsonBlock mainNetBlock = new ObjectMapper().readValue(in, JsonBlock.class);
        verifier.verifyBlock(mainNetBlock);

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
