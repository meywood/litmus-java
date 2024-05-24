package net.casper.litmus.serde;

import com.casper.sdk.exception.NoSuchTypeException;
import com.casper.sdk.helper.CasperDeployHelper;
import com.casper.sdk.model.clvalue.serde.Target;
import com.casper.sdk.model.deploy.Deploy;
import com.casper.sdk.model.deploy.DeployHeader;
import com.casper.sdk.model.deploy.executabledeploy.ModuleBytes;
import com.casper.sdk.model.key.PublicKey;
import dev.oak3.sbs4j.SerializerBuffer;
import dev.oak3.sbs4j.exception.ValueSerializationException;

import java.security.NoSuchAlgorithmException;

import static com.casper.sdk.helper.CasperDeployHelper.buildDeployHeader;

/**
 * @author Carl Norburn
 */
public class DeployByteSerializer implements ByteSerializer<Deploy> {
    @Override
    public byte[] toBytes(final Deploy deploy) {
        var ser = new SerializerBuffer();

        try {

            final DeployHeader deployHeader = buildDeployHeader(
                    PublicKey.fromAbstractPublicKey(deploy.getHeader().getAccount().getPubKey()),
                    deploy.getHeader().getChainName(),
                    deploy.getHeader().getGasPrice(),
                    deploy.getHeader().getTtl(),
                    deploy.getHeader().getTimeStamp(),
                    deploy.getHeader().getDependencies(),
                    CasperDeployHelper.getDeployItemAndModuleBytesHash(deploy.getSession(), (ModuleBytes) deploy.getPayment())
            );

            deployHeader.serialize(ser, Target.BYTE);

        } catch (NoSuchAlgorithmException | NoSuchTypeException | ValueSerializationException e) {
            throw new ByteSerializeException(e);
        }

        return ser.toByteArray();
    }
}
