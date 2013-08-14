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

package com.intel.mountwilson.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecurityFilter implements Filter {
        Logger log = LoggerFactory.getLogger(getClass().getName());
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		log.info("SecurityFilter >>");
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String url = request.getServletPath();
		
		HttpSession httpSession = request.getSession(false);
		System.err.println(httpSession +"----"+url);
		if (httpSession != null && !httpSession.isNew()) {
			Object obj = httpSession.getAttribute("logged-in");
			if ( obj != null) {
				boolean logged =  (Boolean) obj;
				System.err.println(logged);
				if (logged) {
					log.info("User is logged in, forwarding request to "+url);
					chain.doFilter(request, response);
				}else {
					log.info("User is not logged in or session expired, forwarding request to Login page.");
					goToLogin(request,response);
				}
			}else {
				log.info("User is not logged in or session expired, forwarding request to Login page.");
				goToLogin(request,response);
			}
			
			
		}else {
			log.info("User's Session expired, forwarding request to Login page.");
			redirectToLogin(request,response);
		}
				
	}

	private void goToLogin(HttpServletRequest req,HttpServletResponse res) {
		try {
			res.addHeader("message", "Your Login Session is Expired. Please Login again.");
			res.sendRedirect(req.getContextPath()+"/login.htm");
		} catch (IOException e) {
			log.error("IOException Exception, Error While forwarding request to Login page.");
			e.printStackTrace();
		} 
		
	}
	
	private void redirectToLogin(HttpServletRequest req,HttpServletResponse res) {
		try {
			RequestDispatcher dispatcher = req.getRequestDispatcher("/login.htm");
			req.setAttribute("message", "Your Login Session is Expired. Please Login again.");
			dispatcher.forward(req, res);
		} catch (Exception e) {
			log.error("IOException Exception, Error While forwarding request to Login page.");
			e.printStackTrace();
		} 
	}

	@Override
	public void destroy() {
				
	}

}
