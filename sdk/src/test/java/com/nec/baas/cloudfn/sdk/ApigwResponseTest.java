package com.nec.baas.cloudfn.sdk;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * ApigwResponse テスト
 */
public class ApigwResponseTest {
    @Test
    public void testBuilder() {
        ApigwResponse.Builder builder = new ApigwResponse.Builder();
        ApigwResponse response = builder.status(200).contentType("text/plain").entity("data")
                .header("Set-Cookie", "12345").build();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentType()).isEqualTo("text/plain");
        assertThat(response.getEntity()).isEqualTo("data");
        assertThat(response.getHeaders().get("Set-Cookie")).isEqualTo(Collections.singletonList("12345"));
    }

    /**
     * ContentTypeと同じヘッダを設定
     */
    @Test
    public void testBuilderSameHeader() {
        ApigwResponse.Builder builder = new ApigwResponse.Builder();
        ApigwResponse response = builder.status(500).contentType("text/plain").header("Content-Type", "application/json").entity("data")
                .header("Set-Cookie", "12345").build();

        assertThat(response.getStatus()).isEqualTo(500);
        assertThat(response.getContentType()).isEqualTo("text/plain"); // 1番目の値を取得
        assertThat(response.getEntity()).isEqualTo("data");
        assertThat(response.getHeaders().get("Set-Cookie")).isEqualTo(Collections.singletonList("12345"));
        List<String> expected = new ArrayList(1) {{ add("text/plain"); add("application/json");}};
        assertThat(response.getHeaders().get("Content-Type")).isEqualTo(expected);
    }

    /**
     * ContentTypeを未設定, null
     */
    @Test
    public void testContentTypeNull() {
        ApigwResponse.Builder builder = new ApigwResponse.Builder();
        ApigwResponse response = builder.status(400).build();

        assertThat(response.getContentType()).isNull();

        builder = new ApigwResponse.Builder();
        response = builder.status(404).contentType(null).build();

        assertThat(response.getContentType()).isNull();
    }

    /**
     * entityをnull
     */
    @Test
    public void testEntityNull() {
        ApigwResponse.Builder builder = new ApigwResponse.Builder();
        ApigwResponse response = builder.status(400).entity(null).build();

        assertThat(response.getEntity()).isNull();
    }

    /**
     * Builder ok
     */
    @Test
    public void testBuilderOk() {
        ApigwResponse.Builder builder = ApigwResponse.ok();
        ApigwResponse response = builder.build();
        assertThat(response.getStatus()).isEqualTo(200);

        builder = ApigwResponse.ok("data");
        response = builder.build();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getEntity()).isEqualTo("data");

        builder = ApigwResponse.ok("data", "text/plain");
        response = builder.build();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentType()).isEqualTo("text/plain");
        assertThat(response.getEntity()).isEqualTo("data");
    }

    /**
     * Builder status
     */
    @Test
    public void testBuilderStatus() {
        ApigwResponse.Builder builder = ApigwResponse.status(500);
        ApigwResponse response = builder.build();
        assertThat(response.getStatus()).isEqualTo(500);
    }

    @Test
    public void testEqualsHashcode() {
        ApigwResponse r1 = ApigwResponse.status(200).build();
        ApigwResponse r2 = ApigwResponse.status(200).build();
        ApigwResponse r3 = ApigwResponse.status(500).build();
        ApigwResponse r4 = ApigwResponse.status(200).contentType("text/plain").build();
        ApigwResponse r5 = ApigwResponse.status(200).entity("body").build();

        assertThat(r1).isEqualTo(r2);
        assertThat(r1.hashCode()).isEqualTo(r2.hashCode());

        assertThat(r1).isNotEqualTo(r3);
        assertThat(r1.hashCode()).isNotEqualTo(r3.hashCode());

        assertThat(r1).isNotEqualTo(r4);
        assertThat(r1.hashCode()).isNotEqualTo(r4.hashCode());

        assertThat(r1).isNotEqualTo(r5);
        assertThat(r1.hashCode()).isNotEqualTo(r5.hashCode());
    }
}
