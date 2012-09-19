/*
Copyright (c) 2012, Intel Corporation
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
    * Neither the name of Intel Corporation nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package com.intel.openAttestation.manifest.hibernate.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import com.intel.openAttestation.manifest.hibernate.domain.MLE;
import com.intel.openAttestation.manifest.hibernate.domain.OEM;
import com.intel.openAttestation.manifest.hibernate.util.HibernateUtilHis;


public class MLEDAO {

	/**
	 * Constructor to start a hibernate transaction in case one has not
	 * already been started 
	 */
	public MLEDAO() {
	}
	
	public MLE queryMLEidByNameAndVersionAndOEMid (String Name, String Version, String OEMname){
		List<MLE> mleList = null;
		try {
			HibernateUtilHis.beginTransaction();
			Query query = HibernateUtilHis.getSession().createQuery("select a from MLE a, OEM b where a.Name = :name and a.Version = :version and a.oem.OEMID = b.OEMID and b.Name = :oem_name");
			query.setString("name", Name);
			query.setString("version", Version);
			query.setString("oem_name", OEMname);
			List list = query.list();
			mleList = (List<MLE>)list;
			if (list.size() < 1) 
			{
				return null;
			} else {
				HibernateUtilHis.commitTransaction();
				return (MLE)mleList.get(0);
			}
		} catch (Exception e) {
			HibernateUtilHis.rollbackTransaction();
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			HibernateUtilHis.closeSession();
		}
		
	}
	
	public MLE queryMLEidByNameAndVersionAndOSid (String Name, String Version, String OSname, String OSversion){
		List<MLE> mleList = null;
		try {
			HibernateUtilHis.beginTransaction();
			Query query = HibernateUtilHis.getSession().createQuery("select a from MLE a, OS b where a.Name = :name and a.Version = :version and a.os.ID = b.ID and b.Name = :os_name and b.Version = :os_version");
			query.setString("name", Name);
			query.setString("version", Version);
			query.setString("os_name", OSname);
			query.setString("os_version", OSversion);
			List list = query.list();
			mleList = (List<MLE>)list;
			if (list.size() < 1) 
			{
				return null;
			} else {
				HibernateUtilHis.commitTransaction();
				return (MLE)mleList.get(0);
			}
		} catch (Exception e) {
			HibernateUtilHis.rollbackTransaction();
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			HibernateUtilHis.closeSession();
		}
		
	}
//	
//	public Long queryMLEidByNameAndVersionAndOemid (String Name, String Version, ){
//		OEM oem = new OEM();
//		List<OEM> oemList=null;
//		try {
//			HibernateUtilHis.beginTransaction();
//			Query query = HibernateUtilHis.getSession().createQuery("from OEM a where a.Name = :name");
//			query.setString("name", Name);
//			List list = query.list();
//			oemList = (List<OEM>)list;
//			if (list.size() < 1) {
//				return 0L;
//			} else {
//				HibernateUtilHis.commitTransaction();
//				return oemList.get(0).getOEMID();
//			}
//		} catch (Exception e) {
//			HibernateUtilHis.rollbackTransaction();
//			e.printStackTrace();
//			throw new RuntimeException(e);
//		}finally{
//			HibernateUtilHis.closeSession();
//		}
//		
//	}
	
}