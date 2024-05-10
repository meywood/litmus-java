package net.casper.litmus.serde;

import dev.oak3.sbs4j.SerializerBuffer;

import java.util.regex.Pattern;

/**
 * @author ian@meywood.com
 */
public class VersionByteSerializer implements ByteSerializer<String> {

    @Override
    public byte[] toBytes(final String version) {
        var ser = new SerializerBuffer();
        var parts = version.split(Pattern.quote("."));
        for (var part : parts) {
            ser.writeU32(Long.valueOf(part));
        }
        return ser.toByteArray();
    }
}
