package com.nec.baas.cloudfn.sdk.test;

import com.nec.baas.cloudfn.sdk.Context;
import com.nec.baas.cloudfn.sdk.NebulaContext;
import com.nec.baas.core.NbService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.Buffer;
import java.util.Map;

/**
 * Cloud Functions Test Framework
 */
@Slf4j
@Data
public class CloudfnMock {
    private NbService nbService;
    private NebulaContext nebulaContext = new TestNebulaContext();

    /**
     * Function を呼び出す(text/plain)
     * @param clazz Function クラス
     * @param handlerName メソッド名
     * @param event リクエストボディ(String)
     */
    public TestContext call(Class clazz, String handlerName, String event) {
        return callSub(clazz, handlerName, String.class, event);
    }

    /**
     * Function を呼び出す(JSON)
     * @param clazz Function クラス
     * @param handlerName メソッド名
     * @param event リクエストボディ(JSON)
     */
    public TestContext call(Class clazz, String handlerName, Map<String,Object> event) {
        return callSub(clazz, handlerName, Map.class, event);
    }

    /**
     * Function を呼び出す(octet-stream, byte[])
     * @param clazz Function クラス
     * @param handlerName メソッド名
     * @param event リクエストボディ(byte[])
     */
    public TestContext call(Class clazz, String handlerName, byte[] event) {
        return callSub(clazz, handlerName, Map.class, event);
    }

    /**
     * Function を呼び出す(octet-stream, Buffer)
     * @param clazz Function クラス
     * @param handlerName メソッド名
     * @param event リクエストボディ(Buffer)
     */
    public TestContext call(Class clazz, String handlerName, Buffer event) {
        return callSub(clazz, handlerName, Map.class, event);
    }

    /**
     * Function を呼び出す(octet-stream, ByteArrayInputStream)
     * @param clazz Function クラス
     * @param handlerName メソッド名
     * @param event リクエストボディ(ByteArrayInputStream)
     */
    public TestContext call(Class clazz, String handlerName, ByteArrayInputStream event) {
        return callSub(clazz, handlerName, Map.class, event);
    }

    private TestContext callSub(Class clazz, String handlerName, Class eventClass, Object event) {
        TestContext context = new TestContext(nebulaContext);
        if (nbService != null) {
            // override
            context.setNbService(nbService);
        }

        Method method;
        try {
            method = clazz.getMethod(handlerName, eventClass, Context.class);
        } catch (NoSuchMethodException e) {
            log.error("No such handler: {}", handlerName);
            throw new AssertionError("No such handler: " + handlerName);
        }
        try {
            method.invoke(null, event, context);
        } catch (IllegalAccessException | InvocationTargetException e) {
            //log.error("Invocation error: {}", e);
            e.printStackTrace();
            throw new AssertionError("Invocation Error: " + e);
        }
        return context;
    }
}
