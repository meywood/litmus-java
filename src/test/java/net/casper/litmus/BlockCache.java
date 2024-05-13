package net.casper.litmus;

import com.casper.sdk.model.block.JsonBlock;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author ian@meywood.com
 */
public class BlockCache {

    /** Map of block heights to JsonBlocks */
    private final Map<Long, JsonBlock> heightBlockMap = new LinkedHashMap<>();

    public BlockCache() {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            for (int i = 0; i < 100; i++) {
                final InputStream in = BlockCache.class.getResourceAsStream("/assets/blocks/block-" + i + ".json");
                JsonBlock jsonBlock = objectMapper.readValue(in, JsonBlock.class);
                heightBlockMap.put(jsonBlock.getHeader().getHeight(), jsonBlock);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public JsonBlock getBlockByHeight(final long height) {
        return heightBlockMap.get(height);
    }

}
