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
package com.intel.mountwilson.trustagent;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;


import com.intel.mountwilson.common.CommandUtil;
import com.intel.mountwilson.common.Config;
import com.intel.mountwilson.common.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author dsmagadx
 */
public abstract class BaseServer {

	boolean isTakeOwnershipDone = false;

	static {
		try {
			String loggingPropertiesFilename = Config.getAppPath()
					+ File.separator + "config" + File.separator
					+ "logging.properties";
			File file = new File(loggingPropertiesFilename);
			System.out.println("looking for " + loggingPropertiesFilename);
			if (file.exists()) {
				System.setProperty("java.util.logging.config.file",
						loggingPropertiesFilename);
				LoggerFactory.getLogger(BaseServer.class.getName()).info(
						"Start logging .....");
			} else {
				System.err
						.println("Could not initiate the logging. Log will be output to console.");
			}
		} catch (Exception e) {
			System.err
					.println("Could not initiate the logging. Log will be output to console."
							+ e.getMessage());
		}
	}
	static Logger log = LoggerFactory.getLogger(BaseServer.class.getName());

	public void handleConnection(InputStream sockInput, OutputStream sockOutput) {

		byte[] buf = new byte[1024];
		
		try {
			int bytes_read ;
			// This call to read() will wait forever, until the
			// program on the other side either sends some data,
			// or closes the socket.
			bytes_read = sockInput.read(buf, 0, buf.length);


			// If the socket is closed, sockInput.read() will return -1.
			if (bytes_read < 0) {
				log.info("Tried to read from socket read() returned < 0,  Closing socket.");
				writeResponse(sockOutput, CommandUtil
						.generateErrorResponse(ErrorCode.BAD_REQUEST));
				return;

			}
			
			log.info("Received data from socket.");

			
			TrustAgent agent = new TrustAgent();
			if (isTakeOwnershipDone) {
				writeResponse(sockOutput,new TrustAgent().processRequest(getString(buf, bytes_read)));
			} else {
				writeResponse(sockOutput,agent
						.generateErrorResponse(ErrorCode.TPM_OWNERSHIP_ERROR));
			}
			
		} catch (Throwable e) { // Make sure all the exceptions are caught here
								// and return and response to client
			log.error("Exception reading from/writing to socket, e = {0}", e);
			writeResponse(sockOutput, CommandUtil.generateErrorResponse(ErrorCode.FATAL_ERROR));
			
		} 

	}

	private String getString(byte[] buf, int bytes_read) {
		if(buf != null )
			return new String(buf, 0, bytes_read);
		else
			return "";
	}

	private void writeResponse(OutputStream sockOutput, String response){
		try {
			if( response != null){
				sockOutput
					.write(response.getBytes(), 0, response.getBytes().length);
			}else{
				sockOutput
					.write("".getBytes(), 0, "".getBytes().length);
			}
			sockOutput.flush();

		} catch (Exception e) {
			log.error("Error while writing back to sock output",e);
		}
	}

	protected void takeOwnerShip() {
		if (!isTakeOwnershipDone)
			isTakeOwnershipDone = new TrustAgent().takeOwnerShip();
	}
}
