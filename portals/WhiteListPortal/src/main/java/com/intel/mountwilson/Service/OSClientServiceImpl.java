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
 * This class contains methods use to get or update Rest Services for OS Component.
 */
package com.intel.mountwilson.Service;

import com.intel.mountwilson.common.WLMPortalException;
import com.intel.mountwilson.datamodel.OSDataVO;
import com.intel.mountwilson.util.ConnectionUtil;
import com.intel.mountwilson.util.ConverterUtil;
import com.intel.mtwilson.WhitelistService;
import com.intel.mtwilson.datatypes.OsData;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
/**
 * @author Yuvraj Singh
 *
 */
@Service
public class OSClientServiceImpl implements IOSClientService {

        Logger log = LoggerFactory.getLogger(getClass().getName());
	
	public OSClientServiceImpl(){
		
	}
	
	/**
	 * Method to get List of all OS.
	 * 
	 * @param apiClientServices
	 * @return List of OSDataVO Objects.
	 * @throws WLMPortalException
	 */
	@Override
	public List<OSDataVO> getAllOS(WhitelistService apiClientServices) throws WLMPortalException {
		log.info("OSClientServiceImpl.getAllOS >>");
		List<OSDataVO> list = null;
                                try {
                                        list =ConverterUtil.getListToOSDataVO(apiClientServices.listAllOS());
                                }catch (Exception e) {
                                                        throw ConnectionUtil.handleException(e);
                                }
                                log.info("OSClientServiceImpl.getAllOS <<");
	return list;
	}

	
	/**
	 * Method to add OS Type into a rest services
	 * 
	 * @param dataVO
	 * @param apiClientServices
	 * @return Boolean variable e.g true if OS is added successfully 
	 * @throws WLMPortalException
	 */
	@Override
	public boolean addOSInfo(OSDataVO dataVO,WhitelistService apiClientServices) throws WLMPortalException {
		log.info("OSClientServiceImpl.addOSInfo >>");
		boolean result = false;
		try {
			result = apiClientServices.addOS(new OsData(dataVO.getOsName(), dataVO.getOsVersion(), dataVO.getOsDescription()));
		} catch (Exception e) {
			log.error(e.getMessage());
			throw ConnectionUtil.handleException(e);
		}
		log.info("OSClientServiceImpl.addOSInfo <<");
       	return result;
	}

	/**
	 * Method to update OS Type into a rest services
	 * 
	 * @param dataVO
	 * @param apiClientServices
	 * @return Boolean variable e.g true if OS is updated successfully 
	 * @throws WLMPortalException
	 */
	@Override
	public boolean updateOSInfo(OSDataVO dataVO,WhitelistService apiClientServices) throws WLMPortalException {
		log.info("OSClientServiceImpl.updateOSInfo >>");
		
		boolean result = false;
		try {
			result = apiClientServices.updateOS(new OsData(dataVO.getOsName(), dataVO.getOsVersion(), dataVO.getOsDescription()));
		} catch (Exception e) {
			log.error(e.getMessage());
			throw ConnectionUtil.handleException(e);
		}
		log.info("OSClientServiceImpl.updateOSInfo <<");
       	return result;
	}

	/**
	 * Method to delete OS Type from a rest services
	 * 
	 * @param dataMap
	 * @param apiClientServices
	 * @return Boolean variable e.g true if OS is deleted successfully 
	 * @throws WLMPortalException
	 */
	@Override
	public boolean deleteOS(OSDataVO dataVO,WhitelistService apiClientServices) throws WLMPortalException {
		log.info("OSClientServiceImpl.deleteOS >>");
		boolean result = false;
		try {
			result = apiClientServices.deleteOS(new OsData(dataVO.getOsName(), dataVO.getOsVersion(), dataVO.getOsDescription()));
			System.out.println(result);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw ConnectionUtil.handleException(e);
		}
		log.info("OSClientServiceImpl.deleteOS <<");
       	return result;
	}
	
}
