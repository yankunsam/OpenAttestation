/*
Copyright (c) 2012, Intel Corporation
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
    * Neither the name of Intel Corporation nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package com.intel.openAttestation.manifest.resource;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
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
import com.intel.openAttestation.manifest.bean.OpenAttestationResponseFault;
import com.intel.openAttestation.manifest.hibernate.dao.OEMDAO;
import com.intel.openAttestation.manifest.hibernate.domain.OEM;
import com.intel.openAttestation.manifest.resource.OEMResource;



/**
 * RESTful web service interface to work with OEM DB.
 * @author xmei1
 *
 */

@Path("resources/oem")
public class OEMResource {
	
//	@GET
//	@Produces("application/json")
//	public List<OEM> getoemEntry(@QueryParam("index") String index,
//			@QueryParam("oemNumber") String number,@QueryParam("oemDesc") String desc){
//		OEMDAO dao = new OEMDAO();
//		if (index == null && number == null && desc == null)
//			return dao.getAlloemEntries();
//		else if ( index != null)
//			return dao.queryoemEntryByIndex(Long.valueOf(index).longValue());
//		else if (number != null && desc == null)
//			return dao.queryoemEntry(Integer.valueOf(number).intValue());
//		else if (number == null && desc != null)
//			return dao.queryoemEntry(desc);
//		else
//			return dao.queryoemEntry(Integer.valueOf(number).intValue(), desc);
//	}
//	
	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response addOEM(@Context UriInfo uriInfo, OEM oem,
			@Context javax.servlet.http.HttpServletRequest request){
        UriBuilder b = uriInfo.getBaseUriBuilder();
        b = b.path(OEMResource.class);
		Response.Status status = Response.Status.OK;
        try{
			OEMDAO dao = new OEMDAO();
			System.out.println("Check if the OEM Name exists:" + oem.getOEMName());
			if (dao.isOEMExisted(oem.getOEMName())){
				status = Response.Status.BAD_REQUEST;
				OpenAttestationResponseFault fault = new OpenAttestationResponseFault(1006);
				fault.setError_message("Data Error - OEM " + oem.getOEMName()+" already exists in the database");
				return Response.status(status).header("Location", b.build()).entity(fault)
						.build();
			}
			dao.addOEMEntry(oem);
	        return Response.status(status).header("Location", b.build()).type(MediaType.TEXT_PLAIN).entity("True")
	        		.build();
		}catch (Exception e){
			status = Response.Status.INTERNAL_SERVER_ERROR;
			OpenAttestationResponseFault fault = new OpenAttestationResponseFault(
					OpenAttestationResponseFault.FaultCode.FAULT_500);
			fault.setError_message("Add OEM entry failed." + "Exception:" + e.getMessage());
			return Response.status(status).header("Location", b.build()).entity(fault)
					.build();
		}

	}

	@DELETE
	@Produces("application/json")
	public Response deloemEntry(@QueryParam("Name") String Name, @Context UriInfo uriInfo){
        UriBuilder b = uriInfo.getBaseUriBuilder();
        b = b.path(OEMResource.class);
		Response.Status status = Response.Status.OK;

        try{
			OEMDAO dao = new OEMDAO();
			System.out.println("Check if the OEM Name exists:" + Name);
			if (dao.isOEMExisted(Name)){
				dao.DeleteOEMEntry(Name);
				return Response.status(status).type(MediaType.TEXT_PLAIN).entity("True")
		        		.build();
			}
			status = Response.Status.BAD_REQUEST;
			OpenAttestationResponseFault fault = new OpenAttestationResponseFault(
					OpenAttestationResponseFault.FaultCode.FAULT_1006);
			fault.setError_message("Data Error - OEM " + Name +" does not exist in the database");		
			return Response.status(status).entity(fault)
					.build();

		}catch (Exception e){
			status = Response.Status.INTERNAL_SERVER_ERROR;
			OpenAttestationResponseFault fault = new OpenAttestationResponseFault(
					OpenAttestationResponseFault.FaultCode.FAULT_500);
			fault.setError_message("Delete OEM entry failed." + "Exception:" + e.getMessage()); 
			return Response.status(status).entity(fault)
					.build();

		}
	}
	
//	@POST
//	@Consumes("application/json")
//	@Produces("application/json")
//	public Response updateoemEntry(@Context UriInfo uriInfo, OEM oem,
//			@Context javax.servlet.http.HttpServletRequest request){
//        UriBuilder b = uriInfo.getBaseUriBuilder();
//        b = b.path(OEMResource.class);
//		System.out.println("index:"+oem.getIndex());
//		Response.Status status = Response.Status.ACCEPTED;
//		String requestHost = request.getRemoteHost();
//        try{
//			OEMDAO dao = new OEMDAO();
//			if (! dao.isOEMExisted(oem.getIndex())){
//				status = Response.Status.NOT_FOUND;
//				OpenAttestationResponseFault fault = new OpenAttestationResponseFault(
//						OpenAttestationResponseFault.FaultCode.FAULT_404);
//				fault.setError_message("Update oem entry failed.");
//				fault.setDetail("oem Index:" + oem.getIndex() + " doesn't exist in DB.");
//				return Response.status(status)//.header("Location", b.build())
//						.entity(fault).build();
//			}
//			if (dao.isOEMExisted(oem.getoemNumber(), oem.getoemValue(), oem.getIndex())){
//				status = Response.Status.CONFLICT;
//				OpenAttestationResponseFault fault = new OpenAttestationResponseFault(
//						OpenAttestationResponseFault.FaultCode.FAULT_400);
//				fault.setError_message("Update oem entry failed.");
//				fault.setDetail("oemNumber:" + oem.getoemNumber() +", oemValue: " 
//						+ oem.getoemValue()+" already exist in DB.");
//				return Response.status(status)//.header("Location", b.build())
//						.entity(fault).build();
//
//			}
//			oem.setLastUpdateRequestHost(requestHost);
//			dao.updateoemEntry(oem);
//	        return Response.status(status)//.header("Location", b.build())
//	        		.entity(oem).build();
//		}catch (Exception e){
//			e.printStackTrace();
//			status = Response.Status.INTERNAL_SERVER_ERROR;
//			OpenAttestationResponseFault fault = new OpenAttestationResponseFault(
//					OpenAttestationResponseFault.FaultCode.FAULT_500);
//			fault.setError_message("Update oem entry failed.");
//			fault.setDetail("Exception:" + e.getMessage()); 
//			return Response.status(status)//.header("Location", b.build())
//					.entity(fault).build();
//
//		}
//	}

}
