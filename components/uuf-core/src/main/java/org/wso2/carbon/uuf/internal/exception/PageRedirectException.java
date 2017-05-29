/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.uuf.internal.exception;

import org.wso2.carbon.uuf.spi.HttpResponse;

public class PageRedirectException extends HttpErrorException {

    private final String redirectUrl;

    public PageRedirectException(String redirectUrl) {
        super(HttpResponse.STATUS_FOUND, "Redirecting to '" + redirectUrl + "'.");
        this.redirectUrl = redirectUrl;
    }

    public PageRedirectException(String redirectUrl, Throwable cause) {
        super(HttpResponse.STATUS_FOUND, "Redirecting to '" + redirectUrl + "'.", cause);
        this.redirectUrl = redirectUrl;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }
}
