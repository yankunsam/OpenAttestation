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

/**
 * This class is used while creating ModelAndView object in controllers. To crate a view for JSON Data type.
 * If you want to send any data in JSON format to client, create a object of this class and pass to ModelAndView constructor while making ModelAndView Object. 
 */
package com.intel.mountwilson.util;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.View;

import com.google.gson.Gson;

public class JSONView implements View {
	private boolean isKeyRequired;
	
	public JSONView() {
		this(true);
	}
	
	//Pass false if you dont want the keys to be part of json
	public JSONView(boolean isKeyRequired) {
		this.isKeyRequired = isKeyRequired;
	}
	
	//Method to set Content type for response to JSON Type.
	@Override
	public String getContentType() {
		return "application/json";
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void render(Map jsonDetailsMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType(getContentType());
		if(isKeyRequired){
			response.getWriter().write(new Gson().toJson(jsonDetailsMap));
		}else{
			if(jsonDetailsMap.size() == 1){
				Iterator iterator = jsonDetailsMap.values().iterator();
				if (iterator.hasNext()) {
					response.getWriter().write(new Gson().toJson(iterator.next()));
				}
			}else{
				response.getWriter().write(new Gson().toJson(jsonDetailsMap.values()));
			}
			
		}
		
	}
}
