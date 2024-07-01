package net.casper.litmus.serde;

import com.casper.sdk.model.clvalue.serde.Target;
import com.casper.sdk.model.era.ValidatorWeight;
import dev.oak3.sbs4j.SerializerBuffer;
import dev.oak3.sbs4j.exception.ValueSerializationException;
import net.casper.litmus.exception.ByteSerializeException;

/**
 * @author ian@meywood.com
 */
public class ValidatorWeightByteSerializer implements ByteSerializer<ValidatorWeight> {

    @Override
    public byte[] toBytes(final ValidatorWeight validatorWeight) {
        var ser = new SerializerBuffer();
        try {
            validatorWeight.getValidator().serialize(ser, Target.JSON);
            ser.writeU512(validatorWeight.getWeight());
        } catch (ValueSerializationException e) {
            throw new ByteSerializeException(e);
        }
        return ser.toByteArray();
    }
}
