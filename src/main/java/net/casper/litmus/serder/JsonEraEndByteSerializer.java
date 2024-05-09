package net.casper.litmus.serder;

import com.casper.sdk.model.era.JsonEraEnd;
import dev.oak3.sbs4j.SerializerBuffer;

/**
 * @author ian@meywood.com
 */
public class JsonEraEndByteSerializer implements ByteSerializer<JsonEraEnd> {

    private final JsonEraReportByteSerializer jsonEraReportByteSerializer = new JsonEraReportByteSerializer();
    private final ValidatorWeightByteSerializer validatorWeightByteSerializer = new ValidatorWeightByteSerializer();
    private final CollectionByteSerializer collectionByteSerializer = new CollectionByteSerializer();

    @Override
    public byte[] toBytes(final JsonEraEnd jsonEraEnd) {

        var ser = new SerializerBuffer();

        if (jsonEraEnd != null) {
            // Write as option some
            ser.writeU8((byte) 1);
            ser.writeByteArray(this.jsonEraReportByteSerializer.toBytes(jsonEraEnd.getEraReport()));
            collectionByteSerializer.toBytes(
                    ser,
                    jsonEraEnd.getNextEraValidatorWeights(),
                    validatorWeight -> ser.writeByteArray(validatorWeightByteSerializer.toBytes(validatorWeight))
            );
        } else {
            // write as option none
            ser.writeU8((byte) 0);
        }

        return ser.toByteArray();
    }
}
