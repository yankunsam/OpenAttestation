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
 * This Class contains all Method for OEM Component (Add, Edit, View and Delete)
 */
package com.intel.mountwilson.Service;

import com.intel.mountwilson.common.WLMPortalException;
import com.intel.mountwilson.datamodel.OEMDataVO;
import com.intel.mountwilson.util.ConnectionUtil;
import com.intel.mountwilson.util.ConverterUtil;
import com.intel.mtwilson.WhitelistService;
import com.intel.mtwilson.datatypes.OemData;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yuvraj Singh
 *
 */
public class OEMClientServiceImpl implements IOEMClientService {
    
        Logger log = LoggerFactory.getLogger(getClass().getName());
	
	/**
	 * Method to get List of all OEMs .
	 * 
	 * @param apiClientServices : object of WhitelistService Interface.
	 * @return List of OEMDataVO Objects.
	 * @throws WLMPortalException
	 */
	@Override
	public List<OEMDataVO> getAllOEM(WhitelistService apiClientServices) throws WLMPortalException {
		log.info("OEMClientServiceImpl.getAllOEM >>");
		List<OEMDataVO> list = null;
                                try {
                                        list =ConverterUtil.getListToOEMDataVO(apiClientServices.listAllOEM());
                                }catch (Exception e) {
                                        log.error(e.getMessage());
                                        throw ConnectionUtil.handleException(e);
                                        }
                                log.info("OEMClientServiceImpl.getAllOEM <<");
                                return list;
	}

	/**
	 * Method to add OEM Type into a Rest Services
	 * 
	 * @param dataVO
	 * @param apiClientServices
	 * @return Boolean variable e.g true if OEM is added successfully 
	 * @throws WLMPortalException
	 */
	@Override
	public boolean addOEMInfo(OEMDataVO dataVO, WhitelistService apiClientServices) throws WLMPortalException {
		log.info("OEMClientServiceImpl.addOEMInfo >>");
		boolean result = false;
		try {
			apiClientServices.addOEM(new OemData(dataVO.getOemName(), dataVO.getOemDescription()));
			result = true;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw ConnectionUtil.handleException(e);
		}
                                log.info("OEMClientServiceImpl.addOEMInfo <<");
       	
       	return result;
	}

	
	/**
	 * Method to update OEM into a Rest Services
	 * 
	 * @param dataVO
	 * @param apiClientServices
	 * @return Boolean variable e.g true if OEM is updated successfully 
	 * @throws WLMPortalException
	 */
	@Override
	public boolean updateOEMInfo(OEMDataVO dataVO,WhitelistService apiClientServices) throws WLMPortalException {
		log.info("OEMClientServiceImpl.updateOEMInfo >>");
		boolean result = false;
		try {
			apiClientServices.updateOEM(new OemData(dataVO.getOemName(), dataVO.getOemDescription()));
			result = true;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw ConnectionUtil.handleException(e);
		}
		log.info("OEMClientServiceImpl.updateOEMInfo <<");
		return result;
	}

	/**
	 * Method to delete OEM into a Rest Services
	 * 
	 * @param dataMap
	 * @param apiClientServices
	 * @return Boolean variable e.g true if OEM is deleted successfully 
	 * @throws WLMPortalException
	 */
	@Override
	public boolean deleteOEM(OEMDataVO dataVO,WhitelistService apiClientServices) throws WLMPortalException {
		log.info("OEMClientServiceImpl.deleteOEM >>");
		boolean result = false;
		try {
			System.out.println(apiClientServices+"----"+dataVO.getOemName());
			result = apiClientServices.deleteOEM(dataVO.getOemName());
		} catch (Exception e) {
			log.error(e.getMessage());
			throw ConnectionUtil.handleException(e);
		}
                         log.info("OEMClientServiceImpl.deleteOEM <<");
       	return result;
	}
}
