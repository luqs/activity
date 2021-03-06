/*
 * 微信公众平台(JAVA) SDK
 *
 * Copyright (c) 2014, Ansitech Network Technology Co.,Ltd All rights reserved.
 * 

 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cuize.activity.web.util.oauth;

/**
 *
 * 微信操作全局异常
 *

 */
public class WeixinException extends Exception {

    public WeixinException(String msg) {
        super(msg);
    }

    public WeixinException(Exception cause) {
        super(cause);
    }

    public WeixinException(String msg, Exception cause) {
        super(msg, cause);
    }
}
