package com.nec.baas.cloudfn.sdk;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * HttpError テスト
 */
public class HttpErrorTest {

    /**
     * コンストラクタ
     */
    @Test
    public void test() {
        HttpError httpError = new HttpError(200, "test");
        assertThat(httpError.getStatus()).isEqualTo(200);
        assertThat(httpError.getMessage()).isEqualTo("test");

        httpError = new HttpError(0, null);
        assertThat(httpError.getMessage()).isNull();
    }

    /**
     * Builder生成
     */
    @Test
    public void testCreateBuilder() {
        HttpError.Builder builder = HttpError.status(500);
        assertThat(builder).isNotNull();

        HttpError httpError = builder.build();
        assertThat(httpError.getStatus()).isEqualTo(500);
        assertThat(httpError.getMessage()).isNull();
    }

    /**
     * Builder
     */
    @Test
    public void testBuilder() {
        HttpError httpError = HttpError.status(400).message("abc").build();

        assertThat(httpError.getStatus()).isEqualTo(400);
        assertThat(httpError.getMessage()).isEqualTo("abc");
    }
}
