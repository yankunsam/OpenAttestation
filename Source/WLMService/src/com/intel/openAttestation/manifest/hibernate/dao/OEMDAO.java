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
import com.intel.openAttestation.manifest.hibernate.domain.OEM;
import com.intel.openAttestation.manifest.hibernate.util.HibernateUtilHis;

/**
 * This class serves as a central location for updates and queries against 
 * the OEM table
 * @author intel
 * @version OpenAttestation
 *
 */
public class OEMDAO {

	/**
	 * Constructor to start a hibernate transaction in case one has not
	 * already been started 
	 */
	public OEMDAO() {
	}
	
	public OEM addOEMEntry(OEM OEMEntry){
		try {
			HibernateUtilHis.beginTransaction();
			//OEM.setCreateTime(new Date());
			HibernateUtilHis.getSession().save(OEMEntry);
			HibernateUtilHis.commitTransaction();
			return OEMEntry;
		} catch (Exception e) {
			HibernateUtilHis.rollbackTransaction();
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			HibernateUtilHis.closeSession();
		}

	}
	
//	public OEM updateOEMEntry (OEM OEM){
//		try {
//			HibernateUtilHis.beginTransaction();
//			Session session = HibernateUtilHis.getSession();
//			OEM.setLastUpdateTime(new Date());
//			
//			Query query = session.createQuery("from OEM a where a.index = :index");
//			query.setLong("index", OEM.getIndex());
//			List list = query.list();
//			if (list.size() < 1){
//				throw new Exception ("Object not found");
//			}
//			OEM OEMOld = (OEM)list.get(0);
//			OEMOld.setLastUpdateTime(OEM.getLastUpdateTime());
//			OEMOld.setLastUpdateRequestHost(OEM.getLastUpdateRequestHost());
//			OEMOld.setOEMDesc(OEM.getOEMDesc());
//			OEMOld.setOEMNumber(OEM.getOEMNumber());
//			OEMOld.setOEMValue(OEM.getOEMValue());
//			HibernateUtilHis.commitTransaction();
//			return OEM;
//		} catch (Exception e) {
//			HibernateUtilHis.rollbackTransaction();
//			e.printStackTrace();
//			throw new RuntimeException(e);
//		}finally{
//			HibernateUtilHis.closeSession();
//		}
//		
//	}
//
//	public void deleteOEMEntry (OEM OEM){
//		try {
//			HibernateUtilHis.beginTransaction();
//			Session session = HibernateUtilHis.getSession();
//			OEM = (OEM)session.load(OEM.class, OEM.getIndex());
//			session.delete(OEM);
//			HibernateUtilHis.commitTransaction();
//		} catch (Exception e) {
//			HibernateUtilHis.rollbackTransaction();
//			e.printStackTrace();
//			throw new RuntimeException(e);
//		}finally{
//			HibernateUtilHis.closeSession();
//		}
		
//	}
	public void DeleteOEMEntry (String OEMName){
		try {
			HibernateUtilHis.beginTransaction();
			Session session = HibernateUtilHis.getSession();
			Query query = session.createQuery("from OEM a where a.OEMName = :NAME");
			query.setString("NAME", OEMName);
			List list = query.list();
			if (list.size() < 1){
				throw new Exception ("Object not found");
			}
			OEM OEMEntry = (OEM)list.get(0);
			session.delete(OEMEntry);
			HibernateUtilHis.commitTransaction();
		} catch (Exception e) {
			HibernateUtilHis.rollbackTransaction();
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			HibernateUtilHis.closeSession();
		}
		
	}

//	public List<OEM> queryOEMEntryByIndex (long index){
//		List<OEM> OEMs = null;
//		try {
//			HibernateUtilHis.beginTransaction();
//			Query query = HibernateUtilHis.getSession().createQuery("from OEM a where a.index = :index");
//			query.setLong("index", index);
//			List list = query.list();
//		
//			if (list.size() < 1) {
//				OEMs = new ArrayList<OEM>();
//			} else {
//				OEMs = (List<OEM>)list;
//			}
//			HibernateUtilHis.commitTransaction();
//			return OEMs;
//		} catch (Exception e) {
//			HibernateUtilHis.rollbackTransaction();
//			e.printStackTrace();
//			throw new RuntimeException(e);
//		}finally{
//			HibernateUtilHis.closeSession();
//		}
//		
//	}
//
//	public List<OEM> queryOEMEntry (int OEMNumber){
//		List<OEM> OEMs = null;
//		try {
//			HibernateUtilHis.beginTransaction();
//			Query query = HibernateUtilHis.getSession().createQuery("from OEM a where a.OEMNumber = :number");
//			query.setLong("number", OEMNumber);
//			List list = query.list();
//			if (list.size() < 1) {
//				OEMs = new ArrayList<OEM>();
//			} else {
//				OEMs = (List<OEM>)list;
//			}
//			HibernateUtilHis.commitTransaction();
//			return OEMs;
//		} catch (Exception e) {
//			HibernateUtilHis.rollbackTransaction();
//			e.printStackTrace();
//			throw new RuntimeException(e);
//		}finally{
//			HibernateUtilHis.closeSession();
//		}
//		
//	}
//
//	public List<OEM> queryOEMEntry (String OEMDesc){
//		List<OEM> OEMs = null;
//		try {
//			HibernateUtilHis.beginTransaction();
//			Query query = HibernateUtilHis.getSession().
//					createQuery("from OEM a where a.OEMDesc like '%"+OEMDesc+"%'");
//			List list = query.list();
//			if (list.size() < 1) {
//				OEMs = new ArrayList<OEM>();
//			} else {
//				OEMs = (List<OEM>)list;
//			}
//			HibernateUtilHis.commitTransaction();
//			return OEMs;
//		} catch (Exception e) {
//			HibernateUtilHis.rollbackTransaction();
//			e.printStackTrace();
//			throw new RuntimeException(e);
//		}finally{
//			HibernateUtilHis.closeSession();
//		}
//		
//	}
//
//	public List<OEM> queryOEMEntry (int OEMNumber, String OEMDesc){
//		List<OEM> OEMs = null;
//		try {
//			HibernateUtilHis.beginTransaction();
//			Query query = HibernateUtilHis.getSession().
//					createQuery("from OEM a where a.OEMNumber=:number and a.OEMDesc like '%"+OEMDesc+"%'");
//			query.setLong("number", OEMNumber);
//			List list = query.list();
//			if (list.size() < 1) {
//				OEMs = new ArrayList<OEM>();
//			} else {
//				OEMs = (List<OEM>)list;
//			}
//			HibernateUtilHis.commitTransaction();
//			return OEMs;
//		} catch (Exception e) {
//			HibernateUtilHis.rollbackTransaction();
//			e.printStackTrace();
//			throw new RuntimeException(e);
//		}finally{
//			HibernateUtilHis.closeSession();
//		}
//		
//	}
//	
//	public boolean validateOEM (int OEMNumber, String OEMValue){
//		boolean flag =false;
//		try {
//			HibernateUtilHis.beginTransaction();
//			Query query = HibernateUtilHis.getSession().
//					createQuery("from OEM a where a.OEMNumber=:number and a.OEMValue=:value");
//			query.setLong("number", OEMNumber);
//			query.setString("value", OEMValue);
//			List list = query.list();
//			if (list.size() < 1) {
//				flag =  false;
//			} else {
//				flag = true;
//			}
//			HibernateUtilHis.commitTransaction();
//			return flag;
//		} catch (Exception e) {
//			HibernateUtilHis.rollbackTransaction();
//			e.printStackTrace();
//			throw new RuntimeException(e);
//		}finally{
//			HibernateUtilHis.closeSession();
//		}
//		
//	}
//
//	public boolean isOEMExisted(long index){
//		boolean flag =false;
//		try {
//			HibernateUtilHis.beginTransaction();
//			Query query = HibernateUtilHis.getSession().createQuery("from OEM a where a.index = :index");
//			query.setLong("index", index);
//			List list = query.list();
//		
//			if (list.size() < 1) {
//				flag =  false;
//			} else {
//				flag = true;
//			}
//			HibernateUtilHis.commitTransaction();
//			return flag;
//		} catch (Exception e) {
//			HibernateUtilHis.rollbackTransaction();
//			e.printStackTrace();
//			throw new RuntimeException(e);
//		}finally{
//			HibernateUtilHis.closeSession();
//		}
//		
//
//	}
//	
	public boolean isOEMExisted(String Name){
		boolean flag =false;
		try {
			HibernateUtilHis.beginTransaction();
			Query query = HibernateUtilHis.getSession().createQuery("from OEM a where a.OEMName = :value");
			query.setString("value", Name);
			List list = query.list();
		
			if (list.size() < 1) {
				flag =  false;
			} else {
				flag = true;
			}
			HibernateUtilHis.commitTransaction();
			return flag;
		} catch (Exception e) {
			HibernateUtilHis.rollbackTransaction();
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			HibernateUtilHis.closeSession();
		}
	}

//	public boolean isOEMExisted(int OEMNumber, String OEMValue, long exceptIndex){
//		boolean flag = false;
//		try {
//			HibernateUtilHis.beginTransaction();
//			Query query = HibernateUtilHis.getSession().createQuery("from OEM a " +
//					"where a.OEMNumber = :number and a.OEMValue = :value and a.index <> :exceptIndex");
//			query.setInteger("number", OEMNumber);
//			query.setString("value", OEMValue);
//			query.setLong("exceptIndex", exceptIndex);
//			List list = query.list();
//		
//			if (list.size() < 1) {
//				flag =  false;
//			} else {
//				flag = true;
//			}
//			HibernateUtilHis.commitTransaction();
//			return flag;
//		} catch (Exception e) {
//			HibernateUtilHis.rollbackTransaction();
//			e.printStackTrace();
//			throw new RuntimeException(e);
//		}finally{
//			HibernateUtilHis.closeSession();
//		}
//	}

}
