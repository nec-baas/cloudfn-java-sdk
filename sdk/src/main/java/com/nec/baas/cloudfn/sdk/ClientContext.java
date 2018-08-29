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
 * クライアントコンテキスト
 */
public interface ClientContext {
    /** Content-Type */
    String contentType();

    /** HTTP Method */
    String method();

    /** Request URI */
    String uri();

    /** クエリパラメータ */
    Map<String,List<String>> queryParams();

    /** パスパラメータ */
    Map<String,String> pathParams();

    /** ヘッダ */
    Map<String,List<String>> headers();

    /**
     * Nebula コンテキスト
     */
    NebulaContext nebula();

    /**
     * Function 定義
     */
    Map<String, Object> function();
}
