/*
 * This source file was generated by the Gradle 'init' task
 */
package net.casper.litmus;

import com.casper.sdk.model.block.JsonBlock;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class KernelTest {


    @BeforeAll
    public static void beforeAll() throws IOException {


    }

    @Test
    void kernelExists() {

        final JsonBlock blockByHeight = new BlockCache().getBlockByHeight(0L);
        final Kernel classUnderTest = new Kernel(blockByHeight.getHash());
        assertNotNull(classUnderTest.getLatestBlockHash(), "getLatestBlockHash should return 'not null value'");
    }

    @Test
    void firstEntryInBlocksMapIsCorrect() {

        final JsonBlock blockByHeight = new BlockCache().getBlockByHeight(0L);
        assertNotNull(blockByHeight, "First entry in BLOCKS_MAP should not be null");

        Kernel kernel = new Kernel(blockByHeight.getHash());


    }


    @Test
    void blockBodyBytes() {

    }
}
