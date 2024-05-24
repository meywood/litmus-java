package net.casper.litmus.verification;

import com.casper.sdk.model.common.Digest;
import com.casper.sdk.model.deploy.Deploy;
import com.syntifi.crypto.key.hash.Blake2b;
import net.casper.litmus.exception.DeployVerificationException;
import net.casper.litmus.serde.DeployByteSerializer;

/**
 * Serialises a Deploy and compares the resulting hash with the supplied deploy hash
 *
 * @author Carl Norburn
 */
public class DeployVerifier {

    private final DeployByteSerializer deployByteSerializer = new DeployByteSerializer();

    /**
     * Verifies the Deploy by serialising the given deploy and comparing the hash
     *
     * @param deploy the deploy to verify
     */
    public void verifyDeploy(final Deploy deploy) {

        assert deploy != null;

        var deployHash = toDigest(deployByteSerializer.toBytes(deploy));

        if (!deployHash.equals(deploy.getHash())) {
            throw new DeployVerificationException(
                    "Deploy body hash does not match expected body hash \nExpected: "
                            + deployHash
                            + "\nActual: "
                            +  deploy.getHeader().getBodyHash()
            );
        }
    }

    private Digest toDigest(final byte[] bytes) {
        return Digest.digestFromBytes(Blake2b.digest(bytes, 32));
    }

}
