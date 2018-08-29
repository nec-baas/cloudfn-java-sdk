package com.nec.baas.cloudfn.sdk.test;

import com.nec.baas.cloudfn.sdk.NebulaContext;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * NebulaContext 実装
 */
@Data
@Accessors(fluent = true)
public class TestNebulaContext implements NebulaContext {
    private String tenant = "tenant1";
    private String appId = "app1";
    private String appKey = "appkey1";
    private String masterKey = "masterkey1";
    private boolean isMaster = false;
    private String baseUri = "https://baas.example.com/api";
    private User user;
}
