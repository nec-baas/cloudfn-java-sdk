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

/**
 * HTTP エラー
 */
public class HttpError {
    /**
     * ステータスコード
     */
    private final int status;

    /**
     * メッセージ
     */
    private final String message;

    /**
     * コンストラクタ
     * @param status ステータスコード
     * @param message エラーメッセージ
     */
    public HttpError(int status, String message) {
        this.status = status;
        this.message = message;
    }

    /**
     * Builder を生成する
     * @param status ステータスコード
     * @return Builder
     */
    public static Builder status(int status) {
        return new Builder().status(status);
    }

    /**
     * ステータスコードを返す
     * @return ステータスコード
     */
    public int getStatus() {
        return status;
    }

    /**
     * エラーメッセージを返す
     * @return エラーメッセージ
     */
    public String getMessage() {
        return message;
    }

    /**
     * HttpError ビルダ
     */
    public static class Builder {
        int status;
        String message;

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
         * エラーメッセージを指定する
         * @param message エラーメッセージ
         * @return this
         */
        public Builder message(String message) {
            this.message = message;
            return this;
        }

        /**
         * HttpError をビルドする
         * @return HttpError
         */
        public HttpError build() {
            return new HttpError(status, message);
        }
    }
}
