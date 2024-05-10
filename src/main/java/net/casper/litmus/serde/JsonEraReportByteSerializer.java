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

        ser.writeByteArray(collectionByteSerializer.toBytes(
                jsonEraReport.getEquivocators(),
                equivocator -> {
                    var serEqu = new SerializerBuffer();
                    equivocator.serialize(serEqu, Target.JSON);
                    return serEqu.toByteArray();
                }
        ));

        ser.writeByteArray(collectionByteSerializer.toBytes(
                jsonEraReport.getRewards(),
                rewardByteSerializer
        ));

        ser.writeByteArray(collectionByteSerializer.toBytes(
                jsonEraReport.getInactiveValidators(),
                publicKey -> {
                    var serKey = new SerializerBuffer();
                    publicKey.serialize(serKey, Target.JSON);
                    return serKey.toByteArray();
                }

        ));

        return ser.toByteArray();
    }
}
