package com.nec.baas.cloudfn.sdk.test;

import com.nec.baas.cloudfn.sdk.ApigwResponse;
import com.nec.baas.cloudfn.sdk.Context;
import com.nec.baas.core.NbService;
import com.nec.baas.object.NbObjectBucket;

/**
 * Test Function under test
 */
public class TestFunction {
    public static void sayHello(String event, Context context) {
        String json = "{\"message\": \"Hello world!!1\"}";
        ApigwResponse response = ApigwResponse.ok(json, "application/json").build();
        context.succeed(response);
    }

    public static void useNbService(String event, Context context) {
        NbService service = context.nebula();
        NbObjectBucket bucket = service.objectBucketManager().getBucket(event);
        context.succeed("OK");
    }
}
