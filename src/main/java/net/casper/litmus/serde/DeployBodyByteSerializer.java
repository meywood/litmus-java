package net.casper.litmus.serde;

import com.casper.sdk.exception.NoSuchTypeException;
import com.casper.sdk.model.clvalue.serde.Target;
import com.casper.sdk.model.deploy.DeployHeader;
import dev.oak3.sbs4j.SerializerBuffer;
import dev.oak3.sbs4j.exception.ValueSerializationException;
import net.casper.litmus.exception.ByteSerializeException;

public class DeployBodyByteSerializer implements ByteSerializer<DeployHeader> {
    @Override
    public byte[] toBytes(final DeployHeader deployHeader) {

        var ser = new SerializerBuffer();

        try {

            deployHeader.serialize(ser, Target.BYTE);

        } catch (NoSuchTypeException | ValueSerializationException e) {
            throw new ByteSerializeException(e);
        }

        return ser.toByteArray();

    }
}
