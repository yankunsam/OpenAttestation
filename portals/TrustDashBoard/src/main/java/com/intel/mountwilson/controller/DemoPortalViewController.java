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
 * This Class is use to return JSP's for each view present in TrustDashBoard.
 */
package com.intel.mountwilson.controller;

import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 * @author yuvrajsx
 *
 */
public class DemoPortalViewController extends MultiActionController {
	
	// variable declaration used for Logging.  
	private static final Logger logger = Logger.getLogger(DemoPortalViewController.class.getName());
	
	//This method will return home page for TrustDashBoard.
	public ModelAndView getDashBoardPage(HttpServletRequest req,HttpServletResponse res) {
		logger.info("DemoPortalViewController.getDashBoardPage");
		ModelAndView responseView = new ModelAndView("HostTrustStatus");
		return responseView;
	}
	
	//This method will return Add Host Page.
	public ModelAndView getAddHostPage(HttpServletRequest req , HttpServletResponse res){
		logger.info("DemoPortalViewController.getAddHostPage");
		return new ModelAndView("AddHost");
	}
	
	public ModelAndView getViewHostPage(HttpServletRequest req , HttpServletResponse res){
		logger.info("DemoPortalViewController.getViewHostPage");
		ModelAndView responseView = new ModelAndView("ViewHost");
		return responseView;
	}
	
	public ModelAndView getEditHostPage(HttpServletRequest req , HttpServletResponse res){
		logger.info("DemoPortalViewController.getEditHostPage");
		ModelAndView responseView = new ModelAndView("EditHost");
		return responseView;
	}
        
	
	public ModelAndView showReportsPage(HttpServletRequest req , HttpServletResponse res){
		logger.info("DemoPortalViewController.showReportsPage");
		ModelAndView responseView = new ModelAndView("ShowReports");
		return responseView;
	}
	
	
	/**
	 * This method will return Page to show SAML Details for a host.
	 * 
	 * @param req
	 * @param res
	 * @return
	 */
	public ModelAndView trustVerificationDetails(HttpServletRequest req,HttpServletResponse res) {
		logger.info("DemoPortalDataController.trustVerificationDetails");
		ModelAndView responseView = new ModelAndView("TrustSamlDetails");
		String hostName = req.getParameter("hostName");
		responseView.addObject("hostName", hostName);
		return responseView;
	}
	
    //this method will return Page to register new user.
	public ModelAndView getRegisterPage(HttpServletRequest req,HttpServletResponse res) {
		logger.info("WLMViewController.getRegisterPage");
		return new ModelAndView("Register");
	}
}
