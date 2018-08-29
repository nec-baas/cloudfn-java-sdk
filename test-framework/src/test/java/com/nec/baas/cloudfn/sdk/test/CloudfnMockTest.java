package com.nec.baas.cloudfn.sdk.test;

import com.nec.baas.cloudfn.sdk.ApigwResponse;
import com.nec.baas.core.NbService;
import com.nec.baas.object.NbObjectBucketManager;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * CloudFnMock test。
 *
 * 本テストは CloudFunction test framework のサンプルでもある。
 */
public class CloudfnMockTest {
    private CloudfnMock cloudFnMock;

    @Before
    public void before() {
        cloudFnMock = new CloudfnMock();

        // 実APIサーバに接続する場合は、以下のように NebulaContext を設定する。
        TestNebulaContext nebulaContext = new TestNebulaContext()
                .tenant("tenant1")
                .appId("app1")
                .appKey("appkey1")
                .baseUri("https://baas.example.com/api");
        cloudFnMock.setNebulaContext(nebulaContext);
    }

    /**
     * 単純 function 呼び出し
     */
    @Test
    public void testHello() {
        TestContext context = cloudFnMock.call(TestFunction.class, "sayHello", "dummy");

        assertThat(context.isSucceeded());
        ApigwResponse response = context.getSuccessfulResponse();
        assertThat(response.getContentType()).isEqualTo("application/json");
    }

    /**
     * Nebula API 呼び出し
     */
    @Test
    public void testCallNebulaApi() {
        // NbService の mock を生成
        NbObjectBucketManager obm = mock(NbObjectBucketManager.class);
        NbService service = mock(NbService.class);
        when(service.objectBucketManager()).thenReturn(obm);
        cloudFnMock.setNbService(service);

        // Function 実行
        TestContext context = cloudFnMock.call(TestFunction.class, "useNbService", "bucket1");
        assertThat(context.isSucceeded());

        // Verify
        verify(obm).getBucket(eq("bucket1"));
    }
}
