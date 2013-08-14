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

package com.intel.mtwilson.datatypes;

import org.apache.commons.lang3.Validate;

/**
 * Representation of a Measured Launch Environment (MLE) comprised
 * of a Bios and Vmm.
 * 
 * XXX TODO need to change the name of this class because throughout the codebase
 * we refer to Bios and Vmm separately as Mle's, so a pair (Bios,Vmm) should
 * be called something else to avoid confusion. Also an Os may be an Mle separate
 * from a Vmm so that adds to the confusion.
 * 
 * @since 0.5.1
 * @author jbuhacoff
 */
public class MLE {
    private Bios bios = null;
    private Vmm vmm = null;
    
    public MLE(Bios bios, Vmm vmm) {
        setBios(bios);
        setVmm(vmm);
    }
    
    public final void setBios(Bios bios) {
        Validate.notNull(bios);
        this.bios = bios;
    }

    public final void setVmm(Vmm vmm) {
        Validate.notNull(vmm);
        this.vmm = vmm;
    }
    
    public Bios getBios() { return bios; }
    public Vmm getVmm() { return vmm; }
    
    @Override
    public String toString() {
        return String.format("BIOS:%s,VMM:%s", bios.toString(), vmm.toString());
    }
    
    
}
