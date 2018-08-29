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

import com.nec.baas.core.NbService;
import org.slf4j.Logger;

/**
 * コンテキスト
 */
public interface Context {
    /** ロガー */
    Logger logger();

    /** クライアントコンテキスト */
    ClientContext clientContext();

    /**
     * 成功応答を返す。Content-Type は "text/plain"
     * @param str メッセージ
     */
    default void succeed(String str) {
        succeed(ApigwResponse.ok(str, "text/plain").build());
    }

    /**
     * 成功応答を返す。Content-Type は "application/json"
     * @param pojoToJson
     */
    default void succeed(Object pojoToJson) {
        succeed(ApigwResponse.ok(pojoToJson, "application/json").build());
    }

    /**
     * 成功応答を返す。Content-Type は ApigwResponse に従う
     * @param response ApigwResponse
     */
    void succeed(ApigwResponse response);

    /**
     * エラー応答を返す。status code は 500、Content-Type は "text/plain"
     * @param message メッセージ
     */
    default void fail(String message) {
        fail(httpError(500, message));
    }

    /**
     * HTTP エラー応答を返す。Content-Type は "text/plain"
     * @param httpError HttpError
     */
    default void fail(HttpError httpError) {
        // #4704 httpErrorがnullの場合は、ApigwResponseがnullのときと同様の処理
        ApigwResponse response;
        if (httpError == null) {
            response = null;
        } else {
            response = ApigwResponse
                        .status(httpError.getStatus())
                        .entity(httpError.getMessage())
                        .contentType("text/plain")
                        .build();
        }
        fail(response);
    }

    /**
     * HTTPエラー応答を返す
     * @param response ApigwResponse
     */
    void fail(ApigwResponse response);

    /**
     * Nebula Service インスタンスを返す
     * @return NbService
     */
    NbService nebula();

    /**
     * HTTP エラーを生成する。
     * @param status ステータスコード
     * @param message メッセージ
     * @return HttpError
     */
    default HttpError httpError(int status, String message) {
        return new HttpError(status, message);
    }
}
