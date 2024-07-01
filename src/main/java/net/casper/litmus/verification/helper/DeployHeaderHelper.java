package net.casper.litmus.verification.helper;

import com.casper.sdk.exception.NoSuchTypeException;
import com.casper.sdk.helper.CasperDeployHelper;
import com.casper.sdk.model.deploy.Deploy;
import com.casper.sdk.model.deploy.DeployHeader;
import com.casper.sdk.model.deploy.executabledeploy.ModuleBytes;
import com.casper.sdk.model.key.PublicKey;
import dev.oak3.sbs4j.exception.ValueSerializationException;
import net.casper.litmus.exception.DeployVerificationException;

import java.security.NoSuchAlgorithmException;

import static com.casper.sdk.helper.CasperDeployHelper.buildDeployHeader;

/**
 * Builds the DeployHeader from a given JSON derived Deploy using the SDK methods
 */
public class DeployHeaderHelper {

    private final DeployHeader deployHeader;

    public DeployHeaderHelper(final Deploy deploy) {

        try {
            deployHeader = buildDeployHeader(
                    PublicKey.fromAbstractPublicKey(deploy.getHeader().getAccount().getPubKey()),
                    deploy.getHeader().getChainName(),
                    deploy.getHeader().getGasPrice(),
                    deploy.getHeader().getTtl(),
                    deploy.getHeader().getTimeStamp(),
                    deploy.getHeader().getDependencies(),
                    CasperDeployHelper.getDeployItemAndModuleBytesHash(deploy.getSession(), (ModuleBytes) deploy.getPayment())
            );
        } catch (ValueSerializationException | RuntimeException | NoSuchAlgorithmException | NoSuchTypeException e) {
            throw new DeployVerificationException(e);
        }
    }

    public DeployHeader getDeployHeader() {
        return deployHeader;
    }
}
