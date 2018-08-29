package com.nec.baas.cloudfn.sdk.test;

import com.nec.baas.cloudfn.sdk.ApigwResponse;
import com.nec.baas.cloudfn.sdk.ClientContext;
import com.nec.baas.cloudfn.sdk.Context;
import com.nec.baas.cloudfn.sdk.NebulaContext;
import com.nec.baas.core.NbService;
import com.nec.baas.generic.NbGenericServiceBuilder;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

import java.util.concurrent.CountDownLatch;

/**
 * Context 実装 (test用)
 */
@Slf4j
@Data
public class TestContext implements Context {
    private TestClientContext clientContext;
    private NbService nbService;

    /**
     * 成功レスポンス
     */
    @Getter
    private ApigwResponse successfulResponse;

    /**
     * 失敗レスポンス
     */
    @Getter
    private ApigwResponse failedResponse;

    private CountDownLatch latch;

    public TestContext(NebulaContext nebulaContext) {
        this.nbService = createNbService(nebulaContext);
        this.clientContext = new TestClientContext(nebulaContext);
        this.latch = new CountDownLatch(1);
    }

    NbService createNbService(NebulaContext nc) {
        if (nc == null) return null;

        NbService.enableMultiTenant(true);

        return new NbGenericServiceBuilder()
                .tenantId(nc.tenant())
                .appId(nc.appId())
                .appKey(nc.isMaster() ? nc.masterKey() : nc.appKey())
                .endPointUri(nc.baseUri())
                .build();
    }

    @Override
    public Logger logger() {
        return log;
    }

    @Override
    public ClientContext clientContext() {
        return this.clientContext;
    }

    @Override
    public void succeed(ApigwResponse response) {
        this.successfulResponse = response;
        finish();
    }

    @Override
    public void fail(ApigwResponse response) {
        this.failedResponse = response;
        finish();
    }

    /**
     * Function が終了したかどうかを返す (succeed or fail)
     * @return 終了していれば true
     */
    public boolean isFinished() {
        return isSucceeded() || isFailed();
    }

    /**
     * Function が成功終了したかどうかを返す
     * @return 成功していれば true
     */
    public boolean isSucceeded() {
        return this.successfulResponse != null;
    }

    /**
     * Function が失敗終了したかどうかを返す
     * @return 失敗していれば true
     */
    public boolean isFailed() {
        return this.failedResponse != null;
    }

    /**
     * Function が終了するのを待つ
     * @throws InterruptedException
     */
    public void waitFor() throws InterruptedException {
        latch.wait();
    }


    private void finish() {
        latch.countDown();
    }

    @Override
    public NbService nebula() {
        return nbService;
    }
}
