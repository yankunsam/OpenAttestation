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
 * Class use to return Web Pages for each MLE,OEM and OS Component.
 */
package com.intel.mountwilson.controller;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 * @author yuvrajsx
 *
 */
@Controller
public class WLMViewController extends MultiActionController{
	
	private static final Logger logger = Logger.getLogger(WLMViewController.class.getName()); 

	/**
	 * Method to for OS Component
	 */
	public ModelAndView getEditOSPage(HttpServletRequest req,HttpServletResponse res) {
		logger.info("WLMViewController.getEditOSPage >>");
		return new ModelAndView("EditOS");
	}
	
	public ModelAndView getViewOSPage(HttpServletRequest req,HttpServletResponse res) {
		logger.info("WLMViewController.getViewOSPage >>");
		return new ModelAndView("ViewOS");
	}
	
	public ModelAndView getAddOSPage(HttpServletRequest req,HttpServletResponse res) {
		logger.info("WLMViewController.getAddOSPage >>");
		return new ModelAndView("AddOS");
	}
	
	/**
	 * Method to for MLE Component
	 */
	public ModelAndView getAddMLEPage(HttpServletRequest req,HttpServletResponse res) {
		logger.info("WLMViewController.getAddMLEPage >>");
		return new ModelAndView("AddMle");
	}
	
	public ModelAndView getViewMle(HttpServletRequest req,HttpServletResponse res) {
		logger.info("WLMViewController.getViewMle >>");
		return new ModelAndView("ViewMle");
	}
	
	public ModelAndView getEditMle(HttpServletRequest req,HttpServletResponse res) {
		logger.info("WLMViewController.getEditMle >>");
		return new ModelAndView("EditMle");
	}
	
	
	/**
	 * Method to for OEM Component
	 */
	public ModelAndView getViewOEMPage(HttpServletRequest req,HttpServletResponse res) {
		logger.info("WLMViewController.getViewOEMPage >>");
		return new ModelAndView("ViewOEM");
	}
	
	//Method to get Edit page For OEM
	public ModelAndView getEditOEMPage(HttpServletRequest req,HttpServletResponse res) {
		logger.info("WLMViewController.getEditOEMPage >>");
		return new ModelAndView("EditOEM");
	}
	
	public ModelAndView getAddOEMPage(HttpServletRequest req,HttpServletResponse res) {
		logger.info("WLMViewController.getAddOEMPage >>");
		return new ModelAndView("AddOEM");
	}
	
	public ModelAndView getAboutPage(HttpServletRequest req,HttpServletResponse res) {
		logger.info("WLMViewController.getAddOEMPage >>");
		return new ModelAndView("AboutWLM");
	}
	public ModelAndView getRegisterPage(HttpServletRequest req,HttpServletResponse res) {
		logger.info("WLMViewController.getRegisterPage >>");
		return new ModelAndView("Register");
	}
	
}
