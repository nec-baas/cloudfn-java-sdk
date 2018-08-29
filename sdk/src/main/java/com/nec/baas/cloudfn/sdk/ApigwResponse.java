/*
 * Copyright (C) 2016-2018, NEC Corporation.
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nec.baas.cloudfn.sdk;

import java.util.*;

/**
 * API-GW レスポンス.
 *
 * <p>
 * サポートしているContent-Typeと、entityの型は、以下の通り。
 * <table>
 *     <tr>
 *         <th>Content-Type</th>
 *         <th>entityの型</th>
 *         <th>備考</th>
 *     </tr>
 *     <tr>
 *         <td>text/plain</td>
 *         <td>任意のオブジェクト</td>
 *         <td>toString()で文字列化される</td>
 *     </tr>
 *     <tr>
 *         <td>application/json</td>
 *         <td>任意のPojo</td>
 *         <td>JacksonでJSONに変換可能であること</td>
 *     </tr>
 *     <tr>
 *         <td>application/octet-stream</td>
 *         <td>byte[], ByteBuffer, String</td>
 *         <td>String は UTF-8エンコードされる</td>
 *     </tr>
 * </table>
 * </p>
 */
public class ApigwResponse {
    private static final String HEADER_CONTENT_TYPE = "Content-Type";

    private final int status;
    private final Map<String, List<String>> headers;
    private final Object entity;

    /**
     * コンストラクタ
     * @param status スターテスコード
     * @param headers ヘッダ
     * @param entity エンティティ
     */
    private ApigwResponse(int status, Map<String, List<String>> headers, Object entity) {
        this.status = status;
        this.headers = headers;
        this.entity = entity;
    }

    /**
     * ステータスコードを返す
     * @return ステータスコード
     */
    public int getStatus() {
        return status;
    }

    /**
     * Content-Type を返す
     * @return Content-Type
     */
    public String getContentType() {
        List<String> types = headers.get(HEADER_CONTENT_TYPE);
        return types != null ? types.get(0) : null;
    }

    /**
     * ヘッダを返す
     * @return Headers
     */
    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    /**
     * Entity を返す
     * @return Entity
     */
    public Object getEntity() {
        return entity;
    }

    /**
     * ApigwResponse ビルダ
     */
    public static class Builder {
        private int status;
        private HashMap<String, List<String>> headers = new HashMap<>();
        private Object entity;

        /**
         * コンストラクタ
         */
        public Builder() {}

        /**
         * HTTPステータスコードを設定する
         * @param status ステータスコード
         * @return this
         */
        public Builder status(int status) {
            this.status = status;
            return this;
        }

        /**
         * HTTPヘッダを追加する
         * @param name ヘッダ名
         * @param value 値
         * @return this
         */
        public Builder header(String name, String value) {
            headers.compute(name, (k, v) -> {
                if (v == null) {
                    return new ArrayList(1) {{ add(value); }};
                } else {
                    v.add(value);
                    return v;
                }
            });
            return this;
        }

        /**
         * Content-Type を指定する
         * @param contentType Content-Type
         * @return this
         */
        public Builder contentType(String contentType) {
            return header(HEADER_CONTENT_TYPE, contentType);
        }

        /**
         * ボディの entity を指定する
         * @param entity Entity
         * @return this
         */
        public Builder entity(Object entity) {
            this.entity = entity;
            return this;
        }

        /**
         * ApigwResponse をビルドする
         * @return ApigwResponse
         */
        public ApigwResponse build() {
            return new ApigwResponse(status, headers, entity);
        }
    }

    /**
     * Builder を生成する。ステータスコードは 200。
     * @return Builder
     */
    public static Builder ok() {
        return new Builder().status(200);
    }

    /**
     * Builder を生成する。ステータスコードは 200。
     * @param entity Entity
     * @return Builder
     */
    public static Builder ok(Object entity) {
        return new Builder().status(200).entity(entity);
    }

    /**
     * Builder を生成する。ステータスコードは 200。
     * @param entity Entity
     * @param contentType Content-Type
     * @return BUilder
     */
    public static Builder ok(Object entity, String contentType) {
        return new Builder().status(200).entity(entity).contentType(contentType);
    }

    /**
     * Builder を生成する
     * @param status ステータスコード
     * @return Builder
     */
    public static Builder status(int status) {
        return new Builder().status(status);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || !(obj instanceof ApigwResponse)) return false;

        ApigwResponse that = (ApigwResponse)obj;
        return this.status == that.status &&
                Objects.equals(this.headers, that.headers) &&
                Objects.equals(this.entity, that.entity);
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = this.status;
        result = (result * PRIME) + (this.headers == null ? 43 : this.headers.hashCode());
        result = (result * PRIME) + (this.entity == null ? 43 : this.entity.hashCode());
        return result;
    }
}
