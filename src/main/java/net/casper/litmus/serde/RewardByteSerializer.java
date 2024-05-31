package net.casper.litmus.serde;

import com.casper.sdk.model.clvalue.serde.Target;
import com.casper.sdk.model.era.Reward;
import dev.oak3.sbs4j.SerializerBuffer;
import net.casper.litmus.exception.ByteSerializeException;

import java.math.BigInteger;

/**
 * @author ian@meywood.com
 */
public class RewardByteSerializer implements ByteSerializer<Reward> {

    @Override
    public byte[] toBytes(final Reward reward) {
        var ser = new SerializerBuffer();
        try {
            reward.getValidator().serialize(ser, Target.JSON);
            ser.writeU64(BigInteger.valueOf(reward.getAmount()));
        } catch (Exception e) {
            throw new ByteSerializeException(e);
        }
        return ser.toByteArray();
    }
}
