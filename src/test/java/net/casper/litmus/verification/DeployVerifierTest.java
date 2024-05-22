package net.casper.litmus.verification;

import com.casper.sdk.exception.NoSuchTypeException;
import com.casper.sdk.model.deploy.Deploy;
import dev.oak3.sbs4j.exception.ValueSerializationException;
import net.casper.litmus.DeployCache;
import net.casper.litmus.exception.DeployVerificationException;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

public class DeployVerifierTest {
    private final DeployVerifier verifier = new DeployVerifier();
    private final DeployCache deployCache = new DeployCache();
    @Test
    void verifyDeploySuccess() throws Exception {
        for (Deploy deploy : deployCache.getDeploysSuccess()){
            verifier.verifyDeploy(deploy);
        }
    }
    @Test
    void verifyDeployFail() throws NoSuchTypeException, ValueSerializationException, NoSuchAlgorithmException {

        for (Deploy deploy : deployCache.getDeploysFail()) {
            try {
                verifier.verifyDeploy(deploy);
                assert false;
            } catch (DeployVerificationException ex) {
                assert true;
            }
        }
    }

}
