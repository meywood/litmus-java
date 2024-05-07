package net.casper.litmus.serder;

import com.casper.sdk.model.block.JsonBlock;

/**
 * @author ian@meywood.com
 */
public class BlockByteSerializer implements ByteSerializer<JsonBlock> {

    public byte[] toBytes(final JsonBlock block) {
        return new byte[0];
    }
}
