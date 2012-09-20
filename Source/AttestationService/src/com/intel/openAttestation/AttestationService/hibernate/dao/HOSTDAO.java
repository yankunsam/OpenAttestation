/*
Copyright (c) 2012, Intel Corporation
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
    * Neither the name of Intel Corporation nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package com.intel.openAttestation.AttestationService.hibernate.dao;

import gov.niarl.hisAppraiser.hibernate.domain.MachineCert;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

import gov.niarl.hisAppraiser.hibernate.domain.HOST;
import com.intel.openAttestation.AttestationService.hibernate.util.HibernateUtilHis;
//import com.intel.openAttestation.hibernate.domain.AttestRequest;
import gov.niarl.hisAppraiser.hibernate.domain.AttestRequest;

/**
 * This class serves as a central location for updates and queries against 
 * the host table
 * @author intel
 * @version OpenAttestation
 *
 */
public class HOSTDAO {

	/**
	 * Constructor to start a hibernate transaction in case one has not
	 * already been started 
	 */
	public HOSTDAO() {
	}
	
	public HOST addHOSTEntry (HOST HostEntry){
		try {
			HibernateUtilHis.beginTransaction();
			HibernateUtilHis.getSession().save(HostEntry);
			HibernateUtilHis.commitTransaction();
			return HostEntry;
		} catch (Exception e) {
			HibernateUtilHis.rollbackTransaction();
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			HibernateUtilHis.closeSession();
		}

	}
	
	public HOST updatehostEntry (HOST host){
		try {
			HibernateUtilHis.beginTransaction();
			Session session = HibernateUtilHis.getSession();
			Query query = session.createQuery("from HOST a where a.HostName = :HostName");
			query.setString("HostName", host.getHostName());
			List list = query.list();			
			if (list.size() < 1){
				throw new Exception ("Object not found");
			}		
			HOST hostOld = (HOST)list.get(0);
			hostOld.setAddOn_Connection_String(host.getAddOn_Connection_String());
			hostOld.setDescription(host.getDescription());
			hostOld.setEmail(host.getEmail());
			//hostOld.setHostName(host.getHostName());
			hostOld.setIPAddress(host.getIPAddress());
			hostOld.setPort(host.getPort());
			HibernateUtilHis.commitTransaction();
			return host;
		} catch (Exception e) {
			HibernateUtilHis.rollbackTransaction();
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			HibernateUtilHis.closeSession();
		}
		
	}
	
	public void DeleteHOSTEntry (String HostName){
		try {
			HibernateUtilHis.beginTransaction();
			Session session = HibernateUtilHis.getSession();
			Query query = session.createQuery("from HOST a where a.HostName = :NAME");
			query.setString("NAME", HostName);
			List list = query.list();
			if (list.size() < 1){
				throw new Exception ("Object not found");
			}
			HOST hostEntry = (HOST)list.get(0);
			session.delete(hostEntry);
			HibernateUtilHis.commitTransaction();
		} catch (Exception e) {
			HibernateUtilHis.rollbackTransaction();
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			HibernateUtilHis.closeSession();
		}
	}
		
	public boolean isHOSTExisted(String Name){
		boolean flag =false;
		try {
			HibernateUtilHis.beginTransaction();
			Query query = HibernateUtilHis.getSession().createQuery("from HOST a where a.HostName = :value");
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
	
	
	
	/**
	 * get hostID by HostName
	 * @return
	 */
	public long getHostId(String HostName){
		long hostId;
		List<HOST> reqs = null;
		try{
			HibernateUtilHis.beginTransaction();
			Query query = HibernateUtilHis.getSession().createQuery("from HOST a where a.HostName=:HostName");
			query.setString("HostName", HostName);
			List list = query.list();
			if (list.size() < 1) {
				hostId = 0L;
			} else {
				reqs = (List<HOST>) list;
				hostId = reqs.get(0).getID();
			}
			HibernateUtilHis.commitTransaction();
			return hostId;
		} catch (Exception e) {
			HibernateUtilHis.rollbackTransaction();
			throw new RuntimeException(e);
		}finally{
			HibernateUtilHis.closeSession();
		}
	}
	
	/**
	 * get requests by requestId
	 * @param requestId
	 * @return
	 */
	public List<AttestRequest> getRequestsByRequestId(String requestId){
		System.out.println("dao requestId:"+requestId);
		List<AttestRequest> reqs = null;
		try{
			HibernateUtilHis.beginTransaction();
			Query query = HibernateUtilHis.getSession().createQuery("from AttestRequest a where  a.requestId= :requestId");
			query.setString("requestId", requestId);
			List list = query.list();
			if (list.size() < 1) {
				reqs =  new ArrayList<AttestRequest>();
			} else {
				reqs =  (List<AttestRequest>) list;
			}
			HibernateUtilHis.commitTransaction();
			return reqs;
		} catch (Exception e) {
			HibernateUtilHis.rollbackTransaction();
			throw new RuntimeException(e);
		}finally{
			HibernateUtilHis.closeSession();
		}
			
	}
	
	/**
	 * Obtain the active MachineCert row for a given machine name.
	 * @param machineName Name of the machine of interest.
	 * @return The MachineCert entry or null if the machine name has no
	 * active registrations
	 */
	public MachineCert getMachineCert(String machineName) {
		machineName = machineName.toLowerCase();
		MachineCert cert = null;
		try {
			HibernateUtilHis.beginTransaction();
			Query query = HibernateUtilHis.getSession().createQuery("from MachineCert m where m.machineName = :machineName and m.active = :active");
			query.setString("machineName", machineName);
			query.setBoolean("active", true);
			List list = query.list();
			if (list.size() < 1) {
				cert = null;
			} else {
				cert = (MachineCert) list.iterator().next();
			}
			HibernateUtilHis.commitTransaction();
			return cert;
		} catch (Exception e) {
			HibernateUtilHis.rollbackTransaction();
			throw new RuntimeException(e);
		}finally{
			HibernateUtilHis.closeSession();
		}		
	}
	
	/**
	 * save a request for given request
	 * @param req
	 */
	public  void saveRequest(AttestRequest req){
		try {
			HibernateUtilHis.beginTransaction();
			HibernateUtilHis.getSession().save(req);
			HibernateUtilHis.commitTransaction();
		} catch (Exception e) {
			HibernateUtilHis.rollbackTransaction();
			throw new RuntimeException(e);
		}finally{
			HibernateUtilHis.closeSession();
		}
	}

	/*
	 * obtain the last attested attestRequest row for a given host name.
	 * @Param hostName Name of the machine of interest.
	 * @Return The AttestRequest entry
	 */
	public AttestRequest getLastAttestedRequest(String hostName){
		AttestRequest req = null;
		try {
			HibernateUtilHis.beginTransaction();
			hostName = hostName.toLowerCase();
			Query query = HibernateUtilHis.getSession().createQuery("from AttestRequest a where a.hostName = :hostName and" +
					                " a.result is not null order by a.validateTime desc");
			query.setString("hostName", hostName);
			List list = query.list();
			if (list.size() < 1) {
				req = new AttestRequest();
			} else {
				req = (AttestRequest) list.iterator().next();
			}
			HibernateUtilHis.commitTransaction();
			return req;
		} catch (Exception e) {
			HibernateUtilHis.rollbackTransaction();
			throw new RuntimeException(e);
		}finally{
			HibernateUtilHis.closeSession();
		}
	}
	
	/*
	 * update the request row for a given request
	 * @Param req of the request of interest.
	 * 
	 */
	public AttestRequest updateRequest(AttestRequest req){
		try {
			HibernateUtilHis.beginTransaction();
			Session session = HibernateUtilHis.getSession();
			session.update(req);
			HibernateUtilHis.commitTransaction();
			return  (AttestRequest)session.get(AttestRequest.class, req.getId());
		} catch (Exception e) {
			HibernateUtilHis.rollbackTransaction();
			throw new RuntimeException(e);
		}finally{
			HibernateUtilHis.closeSession();
		}
	}
	
	/**
	 * get request by id
	 * @param id
	 * @return
	 */
	public AttestRequest getRequestById(Long id){
		AttestRequest req = null;
		try {
			HibernateUtilHis.beginTransaction();
			Query query = HibernateUtilHis.getSession().createQuery("from AttestRequest a where a.id = :id");
			query.setLong("id", id);
			List list = query.list();
			if (list.size() < 1) {
				req = new AttestRequest();
			} else {
				req =  (AttestRequest) list.iterator().next();
			}
			HibernateUtilHis.commitTransaction();
			return req;
		} catch (Exception e) {
			HibernateUtilHis.rollbackTransaction();
			throw new RuntimeException(e);
		}finally{
			HibernateUtilHis.closeSession();
		}
			
	}
}
