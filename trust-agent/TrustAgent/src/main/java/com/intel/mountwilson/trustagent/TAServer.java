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

/**
 *
 * @author dsmagadX
 */
import com.intel.mountwilson.common.Config;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TAServer extends BaseServer {

    static Logger log = LoggerFactory.getLogger(TAServer.class.getName());
    private ServerSocket serverSock = null;

    public TAServer(int serverPort) throws IOException {

        try {
            serverSock = new ServerSocket(serverPort);
        } catch (IOException e) {
            log.error("Error while creating socket.", e);
            throw e;
        }
    }

    public void waitForConnections() {
        Socket sock = null;
        InputStream sockInput;
        OutputStream sockOutput;
        /*
         Take ownership of the TPM
         */
        takeOwnerShip();
        
        while (true) {
            try {
                sock = serverSock.accept();
                log.info("Have accepted new socket.");
                /*
                 Take ownership of the TPM. This time if already ownership is done 
                 * then this method will return. This is fix the bug where sometimes 
                 * tcsd is not up. 
                 */
                takeOwnerShip();
                
                sockInput = sock.getInputStream();
                sockOutput = sock.getOutputStream();


                handleConnection(sockInput, sockOutput);

            } catch (Exception e) {
                log.error( null, e);
            }finally{
              
                try {
                		log.info("Closing socket.");
                		sock.close();
                } catch (IOException ex) {
                    log.error( null, ex);
                }
            }

            log.info("Finished with socket, waiting for next connection.");
        }
        
    }
    
    public void close() {
    	try {
    		serverSock.close();
    	}
    	catch(IOException e) {
    		log.warn("Failed to close server socket");
    	}
    }

    public static void main(String argv[]) throws IOException {
		TAServer server = new TAServer(getPort());
		server.waitForConnections();
		server.close();
    }

    private static int getPort() {
        return Integer.parseInt(Config.getInstance().getProperty("nonsecure.port"));
    }
}
