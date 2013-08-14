/*
 * Copyright (c) 2013, Intel Corporation. 
 * All rights reserved.
 * 
 * The contents of this file are released under the BSD license, you may not use this file except in compliance with the License.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * Neither the name of Intel Corporation nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.intel.mtwilson;

import com.intel.mtwilson.datatypes.ErrorCode;

/**
 *
 * @since 0.5.2
 * @author jabuhacx
 */
public class ApiException extends Exception {
    private int errorCode;
    private ApiResponse response = null; // may be null; not all ApiExceptions are associated with a server response
//    private String errorMessage;
    
    public ApiException(ApiResponse response, String message) {
        super(message+" ["+ErrorCode.UNKNOWN_ERROR.toString()+"] ");
        errorCode = ErrorCode.UNKNOWN_ERROR.getErrorCode();
//        errorMessage = message;
        this.response = response;
    }
    public ApiException(ApiResponse response, String message, Exception e) {
        super(message+" ["+ErrorCode.UNKNOWN_ERROR.toString()+"] ", e);
        errorCode = ErrorCode.UNKNOWN_ERROR.getErrorCode();
//        errorMessage = message;
        this.response = response;
    }
    public ApiException(ApiResponse response, String message, int errorCode) {
        //super(message+" ["+ErrorCode.valueOf(String.valueOf(errorCode)).toString()+"] ");
        super(message+" ["+String.valueOf(errorCode)+"] ");
        this.errorCode = errorCode;
//        errorMessage = message;
        this.response = response;
    }

    public ApiException(ApiResponse response, String message, ErrorCode errorCode) {
        super(message+" ["+errorCode.toString()+"] ");
        this.errorCode = errorCode.getErrorCode();
//        errorMessage = message;
        this.response = response;
    }
    
    public ApiException(String message) {
        super(message+" ["+ErrorCode.UNKNOWN_ERROR.toString()+"] ");
        errorCode = ErrorCode.UNKNOWN_ERROR.getErrorCode();
//        errorMessage = message;
    }
    public ApiException(String message, Exception e) {
        super(message+" ["+ErrorCode.UNKNOWN_ERROR.toString()+"] ", e);
        errorCode = ErrorCode.UNKNOWN_ERROR.getErrorCode();
//        errorMessage = message;
    }
    public ApiException(String message, int errorCode) {
        //super(message+" ["+ErrorCode.valueOf(String.valueOf(errorCode)).toString()+"] ");
        super(message+" ["+String.valueOf(errorCode)+"] ");
        this.errorCode = errorCode;
//        errorMessage = message;
    }

    public ApiException(String message, ErrorCode errorCode) {
        super(message+" ["+errorCode.toString()+"] ");
        this.errorCode = errorCode.getErrorCode();
//        errorMessage = message;
    }
    
    public int getErrorCode() {
        return errorCode;
    }
    /*
    @Override
    public String getMessage() {
        return super.getMessage()+": "+getErrorContent();
    }*/
    
    public String getHttpReasonPhrase() {
        if( response != null ) {
            return response.httpReasonPhrase;
        }
        else {
            return null;
        }
    }
    
    public Integer getHttpStatusCode() {
        if( response != null ) {
            return response.httpStatusCode;
        }
        else {
            return null;
        }
    }

}
