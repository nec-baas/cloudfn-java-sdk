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

import java.util.List;
import java.util.Map;

/**
 * Nebula Context
 */
public interface NebulaContext {
    /** テナントID */
    String tenant();
    /** アプリケーションID */
    String appId();
    /** アプリケーションキー */
    String appKey();
    /** マスターキー */
    String masterKey();
    /** マスターキー使用有無 */
    boolean isMaster();
    /** APIサーバ Base URI */
    String baseUri();
    /** ログインユーザ情報 */
    User user();

    /**
     * ユーザ
     */
    interface User {
        /** ユーザID */
        String id();
        /** セッショントークン */
        String sessionToken();
        /** セッショントークン有効期限(unix epoch, 秒) */
        long expire();
        /** ユーザ名 */
        String username();
        /** E-mail */
        String email();
        /** オプション */
        Map<String, Object> options();
        /** 所属グループ名一覧 */
        List<String> groups();
    }
}
