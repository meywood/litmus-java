package net.casper.litmus.serde;

import com.casper.sdk.model.clvalue.serde.Target;
import com.casper.sdk.model.era.JsonEraReport;
import dev.oak3.sbs4j.SerializerBuffer;

/**
 * @author ian@meywood.com
 */
public class JsonEraReportByteSerializer implements ByteSerializer<JsonEraReport> {

    private final RewardByteSerializer rewardByteSerializer = new RewardByteSerializer();
    private final CollectionByteSerializer collectionByteSerializer = new CollectionByteSerializer();

    @Override
    public byte[] toBytes(final JsonEraReport jsonEraReport) {

        var ser = new SerializerBuffer();

        collectionByteSerializer.toBytes(
                ser,
                jsonEraReport.getEquivocators(),
                equivocator -> equivocator.serialize(ser, Target.JSON)
        );

        collectionByteSerializer.toBytes(
                ser,
                jsonEraReport.getRewards(),
                reward -> ser.writeByteArray(rewardByteSerializer.toBytes(reward))
        );

        collectionByteSerializer.toBytes(
                ser,
                jsonEraReport.getInactiveValidators(),
                validator -> validator.serialize(ser, Target.JSON)
        );

        return ser.toByteArray();
    }
}
