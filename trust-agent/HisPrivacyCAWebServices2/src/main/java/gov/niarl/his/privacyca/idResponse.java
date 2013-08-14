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

package gov.niarl.his.privacyca;

/**
 * <p>This class is needed to provide a return type for the utils.processIdentityRequest function that includes
 * both the Symmetric and Asymmetric parts of the Privacy CA's response. It is simply a holding container. 
 * The data is not manipulated in any way, except as needed for formatting.</p>
 * 
 * @author schawki
 *
 */
public class idResponse {
	private TpmAsymCaContents asymPart;
	private TpmSymCaAttestation symPart;
	/**
	 * Create a new idResponse of the supplied TPM_ASYM_CA_CONTENTS and TPM_SYM_CA_ATTESTATION structures.
	 * 
	 * @param asym TPM_ASYM_CA_CONTENTS
	 * @param sym TPM_SYM_CA_ATTESTATION
	 */
	public idResponse(TpmAsymCaContents asym, TpmSymCaAttestation sym) {
		asymPart = asym;
		symPart = sym;
	}
	/**
	 * If needed, this function provides a byte array of the Asym and Sym parts of the response concatenated together.
	 * 
	 * @return Byte array form of the idResponse (asym + sym).
	 * @throws TpmUtils.TpmUnsignedConversionException Thrown if bad data is encountered when assembling the byte array.
	 * @throws PrivacyCaException Thrown if either part is not complete and ready to be turned into a byte array.
	 */
	public byte [] toByteArray() 
			throws TpmUtils.TpmUnsignedConversionException,
			PrivacyCaException {
		byte [] asym = asymPart.toByteArray();
		byte [] sym = symPart.toByteArray();
		byte [] returnArray = new byte[asym.length + sym.length];
		System.arraycopy(asym, 0, returnArray, 0, asym.length);
		System.arraycopy(sym, 0, returnArray, asym.length, sym.length);
		return returnArray;
	}
	/**
	 * Asym getter function.
	 * 
	 * @return Asymmetric portion of idResponse.
	 */
	public TpmAsymCaContents getAsymPart() {
		return asymPart;
	}
	/**
	 * Sym getter function.
	 * 
	 * @return Symmetric portion of idResponse.
	 */
	public TpmSymCaAttestation getSymPart() {
		return symPart;
	}
	/**
	 * Asym setter function.
	 * 
	 * @param newPart
	 */
	public void setAsymPart(TpmAsymCaContents newPart) {
		asymPart = newPart;
	}
	/**
	 * Sym setter function.
	 * 
	 * @param newPart
	 */
	public void setSymPart(TpmSymCaAttestation newPart) {
		symPart = newPart;
	}
}
