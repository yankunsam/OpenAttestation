package com.intel.openAttestation.AttestationService.util;


import java.util.HashMap;
import java.util.Iterator;

import gov.niarl.hisAppraiser.util.HisUtil;

import com.intel.openAttestation.AttestationService.util.ActionDelay.Action;

public class CommonUtil {
	
	private static HashMap<Action, Integer> actionIntegerHashMap = new HashMap<Action, Integer>();
	
	public static synchronized String generateRequestId(String label){
		byte[] nonce = HisUtil.generateSecureRandom(16);
		return label+ HisUtil.hexString(nonce);
	}
	
	/**
	 * Converts an Action enumeration into the related integer.
	 * @param action Enumeration value.
	 * @return Integer related to the enumeration.
	 */
	public static int getIntFromAction(Action action) {
		return actionIntegerHashMap.get(action);
	}

    public static boolean validLength(HashMap parameters) {
    	for (Iterator iter = parameters.keySet().iterator(); iter.hasNext();){
    		String key = (String)iter.next();
    		Integer value = (Integer)parameters.get(key);
    		if (key.length() > value.intValue()){
    			return false;
    		}
    	}
    	return true;
    }
}
