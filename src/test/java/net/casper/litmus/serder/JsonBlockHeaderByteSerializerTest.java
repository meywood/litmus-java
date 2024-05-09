package net.casper.litmus.serder;

import com.syntifi.crypto.key.hash.Blake2b;
import net.casper.litmus.BlockCache;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author ian@meywood.com
 */
class JsonBlockHeaderByteSerializerTest {

    @Test
    void jsonBlockHeaderByteSerializer() {

        var serializer = new JsonBlockHeaderByteSerializer();
        for (int i = 0; i < 100; i++) {
            var blockByHeight = new BlockCache().getBlockByHeight((long) i);
            byte[] bytes = serializer.toBytes(blockByHeight.getHeader());
            assertThat(bytes.length).isGreaterThan(0);
        }
    }
}
