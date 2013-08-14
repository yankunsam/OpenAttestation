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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intel.mtwilson.datatypes;


import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.slf4j.LoggerFactory;

/**
 *
 * @author dsmagadx
 */
public class AuthResponse {

    private ErrorCode errorCode = ErrorCode.OK;
    private String errorMessage = null;
   

    public AuthResponse() {
        this.errorCode = ErrorCode.OK;
        this.errorMessage = ErrorCode.OK.getMessage();
    }

    public AuthResponse(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getMessage();
    }

//    public AuthResponse(ErrorCode errorCode, String extraInfo) {
//        this.errorCode = errorCode;
//        this.errorMessage = String.format(errorCode.getMessage(), extraInfo);
//    }
    public AuthResponse(ErrorCode errorCode, String errorMessage, Throwable rootCause) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        //this.extraInfo = rootCause.getMessage();
    }
    public AuthResponse(ErrorCode errorCode, Object... extraInfo) {
        this.errorCode = errorCode;
        try{
            this.errorMessage = String.format(errorCode.getMessage(), extraInfo); 
        }catch(Throwable e){
            this.errorMessage = errorCode.getMessage();
            LoggerFactory.getLogger(getClass().getName()).error("Error while formatting error message for " + errorCode.toString() ,e );
        }   
    }
    
    public AuthResponse(AuthResponse response) {
        this.errorMessage = response.getErrorMessage();
        this.errorCode = response.getErrorCodeEnum();
    }

    @JsonProperty("error_code")
    public String getErrorCode() {
        return errorCode.toString(); // so we see "VALIDATION_ERROR" instead of "1006"
    }

    @JsonProperty("error_message")
    public String getErrorMessage() {
        return errorMessage;
    }

    @JsonProperty("error_code")
    public void setErrorCode(String errorCode) {
        this.errorCode = ErrorCode.valueOf(errorCode);
    }

    @JsonProperty("error_message")
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
  
    
    @JsonIgnore(true)
    public ErrorCode getErrorCodeEnum(){
        return errorCode;
    }

//    public void setAuthResponse(AuthResponse response) {
//        this.errorCode = response.errorCode;
//    }
}
