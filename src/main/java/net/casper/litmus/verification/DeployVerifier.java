package net.casper.litmus.verification;

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
 * Verifies a Deploy by building the Deploy Header and comparing the resulting body hash with the supplied body hash
 *
 * @author Carl Norburn
 */
public class DeployVerifier {

    /**
     * Verifies the Deploy by serialising the given deploy's header and comparing the hashed body
     *
     * @param deploy the deploy to verify
     * @throws NoSuchAlgorithmException non-supported supplied public key
     * @throws NoSuchTypeException invalid cl_type supplied
     * @throws ValueSerializationException can not serialise a given type
     * @throws DeployVerificationException failed comparison of the body hashes
     */
    public void verifyDeploy(final Deploy deploy) throws NoSuchAlgorithmException, NoSuchTypeException,
            ValueSerializationException {

        assert deploy != null;

        final DeployHeader deployHeader = buildDeployHeader(
                PublicKey.fromAbstractPublicKey(deploy.getHeader().getAccount().getPubKey()),
                deploy.getHeader().getChainName(),
                deploy.getHeader().getGasPrice(),
                deploy.getHeader().getTtl(),
                deploy.getHeader().getTimeStamp(),
                deploy.getHeader().getDependencies(),
                CasperDeployHelper.getDeployItemAndModuleBytesHash(deploy.getSession(), (ModuleBytes) deploy.getPayment())
        );

        if (!deployHeader.getBodyHash().equals(deploy.getHeader().getBodyHash())) {
            throw new DeployVerificationException(
                    "Deploy body hash does not match expected body hash \nExpected: "
                            + deployHeader.getBodyHash()
                            + "\nActual: "
                            +  deploy.getHeader().getBodyHash()
            );
        }
    }
}
