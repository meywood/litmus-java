package net.casper.litmus.verification;

import com.casper.sdk.model.common.Digest;
import com.casper.sdk.model.deploy.Deploy;
import com.casper.sdk.model.deploy.DeployHeader;
import com.syntifi.crypto.key.hash.Blake2b;
import net.casper.litmus.exception.DeployVerificationException;
import net.casper.litmus.serde.DeployBodyByteSerializer;
import net.casper.litmus.verification.helper.DeployHeaderHelper;

import java.security.GeneralSecurityException;

/**
 * Serialises a Deploy and compares the resulting hash with the supplied deploy hash
 *
 * @author Carl Norburn
 */
public class DeployVerifier {

    private final DeployBodyByteSerializer deployBodyByteSerializer = new DeployBodyByteSerializer();

    /**
     * Verifies the Deploy by serialising the given deploy and comparing the hash
     *
     * @param jsonDeploy the deploy to verify
     */
    public void verifyDeploy(final Deploy jsonDeploy) throws GeneralSecurityException {
        assert jsonDeploy != null: "Deploy cannot be null";

        var deploy = new DeployHeaderHelper(jsonDeploy);

        verifyDeployHeader(jsonDeploy.getHeader().getBodyHash(), deploy.getDeployHeader());
        verifyDeploySignature(jsonDeploy);

        var deployHash = toDigest(deployBodyByteSerializer.toBytes(deploy.getDeployHeader()));

        if (!deployHash.equals(jsonDeploy.getHash())) {
            throw new DeployVerificationException(
                    "Deploy hash does not match expected hash \nExpected: "
                            + deployHash
                            + "\nActual: "
                            +  jsonDeploy.getHash()
            );
        }
    }

    private void verifyDeployHeader(final Digest expectedBodyHash, final DeployHeader deployHeader) {

        if (!deployHeader.getBodyHash().equals(expectedBodyHash)) {
            throw new DeployVerificationException(
                    "Deploy body hash does not match expected body hash \nExpected: "
                            + expectedBodyHash
                            + "\nActual: "
                            +  deployHeader.getBodyHash()
            );
        }
    }

    private void verifyDeploySignature(final Deploy deploy) throws GeneralSecurityException {

        if (!deploy.getHeader().getAccount().getPubKey().verify(deploy.getHash().getDigest(),
                deploy.getApprovals().get(0).getSignature().getKey())) {
            throw new DeployVerificationException(
                    "Deploy signature does not verify against the deploy public key \nDeploy hash: " + deploy.getHash()
            );
        }
    }

    private Digest toDigest(final byte[] bytes) {
        return Digest.digestFromBytes(Blake2b.digest(bytes, 32));
    }
}
