
package gov.niarl.his.webservices.hisPrivacyCAWebServices2.clientWsImport;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.7-b01-
 * Generated source version: 2.1
 * 
 */
@WebService(name = "HisPrivacyCAWebService2", targetNamespace = "http://server.hisPrivacyCAWebService2.webservices.his.niarl.gov/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface HisPrivacyCAWebService2 {


    /**
     * 
     * @param endorsementCertificate
     * @param identityRequest
     * @return
     *     returns gov.niarl.his.webservices.hisPrivacyCAWebServices2.clientWsImport.ByteArray
     */
    @WebMethod
    @WebResult(name = "identityRequestChallenge", targetNamespace = "")
    @RequestWrapper(localName = "identityRequestGetChallenge", targetNamespace = "http://server.hisPrivacyCAWebService2.webservices.his.niarl.gov/", className = "gov.niarl.his.webservices.hisPrivacyCAWebServices2.clientWsImport.IdentityRequestGetChallenge")
    @ResponseWrapper(localName = "identityRequestGetChallengeResponse", targetNamespace = "http://server.hisPrivacyCAWebService2.webservices.his.niarl.gov/", className = "gov.niarl.his.webservices.hisPrivacyCAWebServices2.clientWsImport.IdentityRequestGetChallengeResponse")
    public ByteArray identityRequestGetChallenge(
        @WebParam(name = "identityRequest", targetNamespace = "")
        ByteArray identityRequest,
        @WebParam(name = "endorsementCertificate", targetNamespace = "")
        ByteArray endorsementCertificate);

    /**
     * 
     * @param arg2
     * @param arg1
     * @param encryptedEkMod
     * @return
     *     returns gov.niarl.his.webservices.hisPrivacyCAWebServices2.clientWsImport.ByteArray
     */
    @WebMethod
    @WebResult(name = "requestGetEC", targetNamespace = "")
    @RequestWrapper(localName = "requestGetEC", targetNamespace = "http://server.hisPrivacyCAWebService2.webservices.his.niarl.gov/", className = "gov.niarl.his.webservices.hisPrivacyCAWebServices2.clientWsImport.RequestGetEC")
    @ResponseWrapper(localName = "requestGetECResponse", targetNamespace = "http://server.hisPrivacyCAWebService2.webservices.his.niarl.gov/", className = "gov.niarl.his.webservices.hisPrivacyCAWebServices2.clientWsImport.RequestGetECResponse")
    public ByteArray requestGetEC(
        @WebParam(name = "encryptedEkMod", targetNamespace = "")
        ByteArray encryptedEkMod,
        @WebParam(name = "arg1", targetNamespace = "")
        ByteArray arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        int arg2);

    /**
     * 
     * @param identityRequestResponseToChallenge
     * @return
     *     returns gov.niarl.his.webservices.hisPrivacyCAWebServices2.clientWsImport.ByteArray
     */
    @WebMethod
    @WebResult(name = "encryptedCertificate", targetNamespace = "")
    @RequestWrapper(localName = "identityRequestSubmitResponse", targetNamespace = "http://server.hisPrivacyCAWebService2.webservices.his.niarl.gov/", className = "gov.niarl.his.webservices.hisPrivacyCAWebServices2.clientWsImport.IdentityRequestSubmitResponse")
    @ResponseWrapper(localName = "identityRequestSubmitResponseResponse", targetNamespace = "http://server.hisPrivacyCAWebService2.webservices.his.niarl.gov/", className = "gov.niarl.his.webservices.hisPrivacyCAWebServices2.clientWsImport.IdentityRequestSubmitResponseResponse")
    public ByteArray identityRequestSubmitResponse(
        @WebParam(name = "identityRequestResponseToChallenge", targetNamespace = "")
        ByteArray identityRequestResponseToChallenge);

}