package net.casper.litmus;

import com.casper.sdk.model.deploy.Deploy;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DeployCache {

    private final List<Deploy> deploysSuccess = new ArrayList<>();
    private final List<Deploy> deploysFail = new ArrayList<>();

    public DeployCache() {

        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            for (int i = 0; i <= 11; i++) {
                final InputStream in = DeployCache.class.getResourceAsStream("/assets/deploys/success/deploy-" + i + ".json");
                final Deploy deploy = objectMapper.readValue(in, Deploy.class);
                deploysSuccess.add(deploy);
            }

            for (int i = 0; i < 2; i++) {
                final InputStream in = DeployCache.class.getResourceAsStream("/assets/deploys/fail/deploy-" + i + ".json");
                final Deploy deploy = objectMapper.readValue(in, Deploy.class);
                deploysFail.add(deploy);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public List<Deploy> getDeploysSuccess() {
        return deploysSuccess;
    }

    public List<Deploy> getDeploysFail() {
        return deploysFail;
    }
}
