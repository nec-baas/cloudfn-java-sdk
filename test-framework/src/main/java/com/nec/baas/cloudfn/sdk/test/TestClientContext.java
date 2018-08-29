package com.nec.baas.cloudfn.sdk.test;

import com.nec.baas.cloudfn.sdk.ClientContext;
import com.nec.baas.cloudfn.sdk.NebulaContext;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

/**
 * TestClient Context
 */
@Getter
@Setter
@Accessors(fluent = true)
public class TestClientContext implements ClientContext {
    private String contentType;
    private String method;
    private String uri;
    private Map<String, List<String>> queryParams;
    private Map<String, String> pathParams;
    private Map<String, List<String>> headers;
    private Map<String, Object> function;
    private NebulaContext nebula;

    public TestClientContext(NebulaContext nebulaContext) {
        this.nebula = nebulaContext;
    }
}
