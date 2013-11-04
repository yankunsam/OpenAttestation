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
package com.intel.mountwilson.trustagent.commands;

import java.io.File;

import com.intel.mountwilson.common.Config;
import com.intel.mountwilson.common.ErrorCode;
import com.intel.mountwilson.common.HisConfig;
import com.intel.mountwilson.common.ICommand;
import com.intel.mountwilson.common.TAException;
import com.intel.mountwilson.his.helper.ProvisionTPM;
import com.intel.mountwilson.trustagent.data.TADataContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author dsmagadX
 */
public class TakeOwnershipCmd implements ICommand {

    Logger log = LoggerFactory.getLogger(getClass().getName());
    private TADataContext context;

    public TakeOwnershipCmd(TADataContext context) {
        this.context = context;
    }

    // precondition:  run the pcakey command to obtain the EK signing key
    public static void main(String[] args) throws TAException {
        TakeOwnershipCmd cmd = new TakeOwnershipCmd(null); // this command doesn't use the TADataContext
        cmd.execute();
    }
    
    @Override
    public void execute() throws TAException{

        try {
        	
//			String commandLine = String.format("takeownership");
//			CommandUtil.runCommand(commandLine); // safe; no arguments involved in this command line
//			log.log(Level.INFO, " Take ownership command executed successfully");
        	
			if(endorsementKeyExists()){	
				ProvisionTPM.takeOwnership();
				deleteEndorsmentKey();
			}else
				log.info("No Endorsement key. Assuming ownership is already taken.");
			
		} catch (Exception e) {
			throw new TAException(ErrorCode.ERROR, "Error while taking ownership",e);
		}

    }

	private void deleteEndorsmentKey() {
		
		String endorsementKey = (String) HisConfig.getConfiguration().getString("TpmEndorsmentP12");
		
		File file = new File(Config.getHomeFolder() + "/" + endorsementKey);
		
		if(file.isFile()){
			file.delete();
			log.info("Deleted endoresment key after taking ownership.");
		}
		
	}

	private boolean endorsementKeyExists() {
		
		String endorsementKey = (String) HisConfig.getConfiguration().getString("TpmEndorsmentP12");
		
		log.info(" File to check " + Config.getHomeFolder() + "/" + endorsementKey);
		
		File file = new File(Config.getHomeFolder() + "/" + endorsementKey);
		
		if(file.isFile()){
			log.info("Endorsement key Found.");
			return true;
		}
		
		return false;
	}


}
