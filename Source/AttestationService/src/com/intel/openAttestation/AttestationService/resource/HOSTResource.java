/*
Copyright (c) 2012, Intel Corporation
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
    * Neither the name of Intel Corporation nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package com.intel.openAttestation.AttestationService.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.hibernate.Query;

import com.intel.openAttestation.AttestationService.bean.OpenAttestationResponseFault;
import com.intel.openAttestation.AttestationService.hibernate.dao.HOSTDAO;
import com.intel.openAttestation.AttestationService.hibernate.domain.HOST;
import com.intel.openAttestation.AttestationService.hibernate.util.HibernateUtilHis;
import com.intel.openAttestation.AttestationService.resource.HOSTResource;
import com.intel.openAttestation.AttestationService.bean.HostBean;
//import com.intel.openAttestation.bean.ReqAttestationBean;

/**
 * RESTful web service interface to work with HOST DB.
 * @author xmei1
 *
 */

@Path("resources/host")
public class HOSTResource {
		
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response addHOST(@Context UriInfo uriInfo, HostBean hostFullObj, @Context javax.servlet.http.HttpServletRequest request){
        UriBuilder b = uriInfo.getBaseUriBuilder();
        b = b.path(HOSTResource.class);
		Response.Status status = Response.Status.OK;
		String hostName = "";
        try{
			HOSTDAO dao = new HOSTDAO();
		
			System.out.println("Check if the HOST Name exists:" + hostName);
			if (dao.isHOSTExisted(hostFullObj.getHostName())){
				status = Response.Status.BAD_REQUEST;
				OpenAttestationResponseFault fault = new OpenAttestationResponseFault(2001);
				fault.setError_message("Data Error - HOST " + hostFullObj.getHostName() +" already exists in the database");
				return Response.status(status).header("Location", b.build()).entity(fault).build();
			}
			
			HOST host = new HOST();
			host.setAddOn_Connection_String(hostFullObj.getAddOn_Connection_String());
			host.setDescription(hostFullObj.getDescription());
			host.setEmail(hostFullObj.getEmail());
			host.setHostName(hostFullObj.getHostName());
			host.setIPAddress(hostFullObj.getIPAddress());
			host.setPort(hostFullObj.getPort());
			
			dao.addHOSTEntry(host);
			
			//MLE table check, we need the foreign key to query from the table of "MLE"
			long hostId = dao.getHostId(hostName);
			//////check the entry from the MLE table///////////////
			try {
				HibernateUtilHis.beginTransaction();
				Query query = HibernateUtilHis.getSession().createQuery("from HOST a where a.ID=:hostID");
				query.setLong("hostID", hostId);
				List list = query.list();
				if (list.size() < 1) {
					//call API which will insert an entry into MLE table with MLE attribute
				} else {
					//TBD
					//logic: 1. resolution the MLE type from input 
					//		 2. query MLE table via the input parameter
					//		 3. if the entry doesn't existed, insert a new entry into MLE
					//		 4. what's the return result if there is an exception accompany with MLE table operation
				}
				HibernateUtilHis.commitTransaction();
			} catch (Exception e) {
				HibernateUtilHis.rollbackTransaction();
				throw new RuntimeException(e);
			}finally{
				HibernateUtilHis.closeSession();
			}
					
	        return Response.status(status).header("Location", b.build()).type(MediaType.TEXT_PLAIN).entity("True").build();
		}catch (Exception e){
			status = Response.Status.INTERNAL_SERVER_ERROR;
			OpenAttestationResponseFault fault = new OpenAttestationResponseFault(
					OpenAttestationResponseFault.FaultCode.FAULT_2001);
			fault.setError_message("Add HOST entry failed." + "Exception:" + e.getMessage());
			return Response.status(status).header("Location", b.build()).entity(fault).build();
		}
	}
	
//	public Response addHOST(@Context UriInfo uriInfo, HOST host, @Context javax.servlet.http.HttpServletRequest request){
//        UriBuilder b = uriInfo.getBaseUriBuilder();
//        b = b.path(HOSTResource.class);
//		Response.Status status = Response.Status.OK;
//        try{
//			HOSTDAO dao = new HOSTDAO();
//			System.out.println("Check if the HOST Name exists:" + host.getHostName());
//			if (dao.isHOSTExisted(host.getHostName())){
//				status = Response.Status.BAD_REQUEST;
//				OpenAttestationResponseFault fault = new OpenAttestationResponseFault(2001);
//				fault.setError_message("Data Error - HOST " + host.getHostName() +" already exists in the database");
//				return Response.status(status).header("Location", b.build()).entity(fault).build();
//			}
//			dao.addHOSTEntry(host);
//	        return Response.status(status).header("Location", b.build()).type(MediaType.TEXT_PLAIN).entity("True").build();
//		}catch (Exception e){
//			status = Response.Status.INTERNAL_SERVER_ERROR;
//			OpenAttestationResponseFault fault = new OpenAttestationResponseFault(
//					OpenAttestationResponseFault.FaultCode.FAULT_2001);
//			fault.setError_message("Add HOST entry failed." + "Exception:" + e.getMessage());
//			return Response.status(status).header("Location", b.build()).entity(fault).build();
//		}
//
//	}

//	@PUT
//	@Consumes("application/json")
//	@Produces("application/json")
//	public Response updatehostEntry(@Context UriInfo uriInfo, HOST host, @Context javax.servlet.http.HttpServletRequest request){
//        UriBuilder b = uriInfo.getBaseUriBuilder();
//        b = b.path(HOSTResource.class);
//		Response.Status status = Response.Status.ACCEPTED;
//        try{
//			HOSTDAO dao = new HOSTDAO();
//			if (! dao.isHOSTExisted(host.getHostName())){
//				status = Response.Status.NOT_FOUND;
//				OpenAttestationResponseFault fault = new OpenAttestationResponseFault(OpenAttestationResponseFault.FaultCode.FAULT_404);
//				fault.setError_message("Update host entry failed.");
//				fault.setDetail("host Index:" + host.getHostName() + " doesn't exist in DB.");
//				return Response.status(status).header("Location", b.build()).entity(fault).build();
//			}
//			
//			if (host.getIPAddress() == null || host.getPort() == null){
//				status = Response.Status.BAD_REQUEST;
//				OpenAttestationResponseFault fault = new OpenAttestationResponseFault(OpenAttestationResponseFault.FaultCode.FAULT_412);
//				fault.setError_message("Update host entry failed.");
//				fault.setDetail("hostNumber:" + host.getHostName() +", IP addresss and port are required.");
//				return Response.status(status).header("Location", b.build()).entity(fault).build();
//			}
//			dao.updatehostEntry(host);
//			return Response.status(status).header("Location", b.build()).type(MediaType.TEXT_PLAIN).entity("True").build();
//		}catch (Exception e){
//			e.printStackTrace();
//			status = Response.Status.INTERNAL_SERVER_ERROR;
//			OpenAttestationResponseFault fault = new OpenAttestationResponseFault(OpenAttestationResponseFault.FaultCode.FAULT_500);
//			fault.setError_message("Update host entry failed.");
//			fault.setDetail("Exception:" + e.getMessage()); 
//			return Response.status(status).entity(fault).build();
//		}
//	}
	
	@PUT
	@Consumes("application/json")
	@Produces("application/json")
	public Response updatehostEntry(@Context UriInfo uriInfo, HostBean hostFullObj, @Context javax.servlet.http.HttpServletRequest request){
        UriBuilder b = uriInfo.getBaseUriBuilder();
        b = b.path(HOSTResource.class);
		Response.Status status = Response.Status.ACCEPTED;
        try{
			HOSTDAO dao = new HOSTDAO();
			if (!dao.isHOSTExisted(hostFullObj.getHostName())){
				status = Response.Status.NOT_FOUND;
				OpenAttestationResponseFault fault = new OpenAttestationResponseFault(OpenAttestationResponseFault.FaultCode.FAULT_404);
				fault.setError_message("Update host entry failed.");
				fault.setDetail("host Index:" + hostFullObj.getHostName() + " doesn't exist in DB.");
				return Response.status(status).header("Location", b.build()).entity(fault).build();
			}
			
			//IP addresss and port are required
			if (hostFullObj.getIPAddress() == null || hostFullObj.getPort() == null){
				status = Response.Status.BAD_REQUEST;
				OpenAttestationResponseFault fault = new OpenAttestationResponseFault(OpenAttestationResponseFault.FaultCode.FAULT_412);
				fault.setError_message("Update host entry failed.");
				fault.setDetail("hostNumber:" + hostFullObj.getHostName() +", IP addresss and port are required.");
				return Response.status(status).header("Location", b.build()).entity(fault).build();
			}
			
			//update HOST table
			HOST host = new HOST();
			host.setAddOn_Connection_String(hostFullObj.getAddOn_Connection_String());
			host.setDescription(hostFullObj.getDescription());
			host.setEmail(hostFullObj.getEmail());
			host.setHostName(hostFullObj.getHostName());
			host.setIPAddress(hostFullObj.getIPAddress());
			host.setPort(hostFullObj.getPort());
			
			dao.updatehostEntry(host);
			
			//update related MLE table
			//TBD
			//logic: 1. resolution the MLE type from input 
			//		 2. query MLE table via the input parameter
			//		 3. if the one attribute is not matched with the attribute from MLE table, update MLE table
			//		 4. what's the return result if there is an exception accompany with MLE table operation
			return Response.status(status).header("Location", b.build()).type(MediaType.TEXT_PLAIN).entity("True").build();
		}catch (Exception e){
			e.printStackTrace();
			status = Response.Status.INTERNAL_SERVER_ERROR;
			OpenAttestationResponseFault fault = new OpenAttestationResponseFault(OpenAttestationResponseFault.FaultCode.FAULT_500);
			fault.setError_message("Update host entry failed.");
			fault.setDetail("Exception:" + e.getMessage()); 
			return Response.status(status).entity(fault).build();
		}
	}	
	
	@DELETE
	@Produces("application/json")
	public Response delhostEntry(@QueryParam("hostName") String Name, @Context UriInfo uriInfo){
        UriBuilder b = uriInfo.getBaseUriBuilder();
        b = b.path(HOSTResource.class);
		Response.Status status = Response.Status.OK;
        try{
			HOSTDAO dao = new HOSTDAO();
			System.out.println("Check if the HOST Name exists:" + Name);
			if (dao.isHOSTExisted(Name)){
				dao.DeleteHOSTEntry(Name);
				return Response.status(status).type(MediaType.TEXT_PLAIN).entity("True").build();
			}
			status = Response.Status.BAD_REQUEST;
			OpenAttestationResponseFault fault = new OpenAttestationResponseFault(OpenAttestationResponseFault.FaultCode.FAULT_2000);
			fault.setError_message("Host not found - Host - " + Name + "that is being deleted does not exist.");
			fault.setError_message("Data Error - HOST " + Name +" does not exist in the database");		
			return Response.status(status).entity(fault).build();
			
			//update related MLE table
			//TBD
			//logic: 1. get the hostID from the HOST table
			//		 2. query MLE table via the hostID
			//		 3. delete all of the entry in MLE table which hostID is matched with HOST table's hostID 
			//		 4. what's the return result if there is an exception accompany with MLE table operation

		}catch (Exception e){
			status = Response.Status.INTERNAL_SERVER_ERROR;
			OpenAttestationResponseFault fault = new OpenAttestationResponseFault(
					OpenAttestationResponseFault.FaultCode.FAULT_500);
			fault.setError_message("Delete HOST entry failed." + "Exception:" + e.getMessage()); 
			return Response.status(status).entity(fault).build();
		}
	}
	
}
