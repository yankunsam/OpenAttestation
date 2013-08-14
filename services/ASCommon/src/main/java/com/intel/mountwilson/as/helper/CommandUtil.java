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

package com.intel.mountwilson.as.helper;

import com.intel.mountwilson.as.common.ASException;
import com.intel.mtwilson.datatypes.ErrorCode;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.commons.lang.StringUtils;

/**
 *
 * @author dsmagadX
 */
public class CommandUtil {

    private static final Logger log = LoggerFactory.getLogger(CommandUtil.class);

    public static List<String> runCommand(String commandLine, boolean readResult, String commandAlias) {
        List<String> result = new ArrayList<String> ();

        try {
            int returnCode;

            log.trace("Running command {}", commandLine);

            Process p = Runtime.getRuntime().exec(commandLine);

            if (readResult) {
            	InputStream in = p.getInputStream();
            	try {
                BufferedReader input = new BufferedReader(new InputStreamReader(in));



                String newLine;

                while ((newLine = input.readLine()) != null) {
                    result.add(newLine);
                }

                input.close();
            	}
            	finally {
            		if(in != null ) {
            			in.close();
            		}
            	}
            }
            String resultForLog = result.size()+" items:\n"+StringUtils.join(result, "\n");
            log.trace("Result Output \n{}", resultForLog);
            //do a loop to wait for an exit value
            boolean isRunning;
            int timeout = 5000;
            int countToTimeout = 0;

            do {
                countToTimeout++;
                isRunning = false;
                try {
                    /*returnCode = */ p.exitValue();
                } catch (IllegalThreadStateException e1) {
                    isRunning = true;
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e2) {
                        isRunning = false;
                    }
                }
            } while (isRunning
                    && (countToTimeout < timeout));


            if (countToTimeout
                    == timeout) {
                log.trace("Command is not responding.");
                p.destroy();

            }

            returnCode = p.exitValue();


            log.trace("Return code {}", String.valueOf(returnCode));

            if (returnCode != 0) {
                throw new ASException(ErrorCode.AS_QUOTE_VERIFY_COMMAND_FAILED, returnCode);
            }

        } catch (ASException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ASException(ex);
        }
        return result;
    }

    // not being uesd.  also, use IOUtils to read contents of entire file into string 
    /*
    public static byte[] readfile(String fileName) throws Exception {

        byte[] fileContents = null;

        try {
            InputStream fStream = new FileInputStream(fileName);
            fileContents = new byte[fStream.available()];
            fStream.read(fileContents);

            fStream.close();
        } catch (Exception ex) {
            log.log(Level.SEVERE, null, ex);
            throw ex;
        }
        return fileContents;
    }
    */
}
