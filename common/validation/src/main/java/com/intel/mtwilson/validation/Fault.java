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

package com.intel.mtwilson.validation;

/**
 * This class strongly resembles an Exception object but it is used differently.
 * 
 * In this validation package, Faults are collected, not thrown. This allows
 * an application to fully validate potentially complex user input, collect
 * all the faults, and then provide
 * complete guidance on any errors that may exist in the input. 
 * 
 * Contrast this to Exceptions, that can only be thrown one at a time and
 * interrupt the flow of execution, forcing the user to correct and resubmit
 * one error at a time until the input is completely validated.
 * 
 * First, 
 * @since 1.1
 * @author jbuhacoff
 */
public class Fault {
    private final String description;
    private final Throwable cause;
    private final Fault[] more;
    
    public Fault(String description) {
        this.cause = null;
        this.description = description;
        this.more = new Fault[0];
    }
    
    public Fault(String format, Object... args) {
        this.cause = null;
        this.description = String.format(format, args);
        this.more = new Fault[0];
    }
    
    public Fault(Throwable e, String description) {
        this.cause = e;
        this.description = description;
        this.more = new Fault[0];
    }
    
    public Fault(Throwable e, String format, Object... args) {
        this.cause = e;
        this.description = String.format(format, args);
        this.more = new Fault[0];
    }
    
    /**
     * Faults from the given model are copied to as "more faults" for this one.
     * It is safe to reset or continue using the given model.
     * @param m
     * @param format
     * @param args 
     */
    public Fault(Model m, String format, Object... args) {
        this.cause = null;
        this.description = String.format(format, args);
        int size = m.getFaults().size();
        this.more = new Fault[size];
        for(int i=0; i<size; i++) {
            this.more[i] = m.getFaults().get(i);
        }
    }
    
    @Override
    public String toString() {
        return description;
    }
    
    /**
     * This method allows the application to get additional information about 
     * the error or exception that caused the fault, if that was the case.
     * 
     * @return the Throwable that is represented by this Fault, or null if there isn't one
     */
    public Throwable getCause() {
        return cause;
    }
    
    /**
     * This method returns an array of faults related to this one. Typically
     * these are faults that resulted in the failure described by this fault.
     * 
     * @return an array of Fault objects; may be null or zero-length if there aren't any other associated faults
     */
    public Fault[] getMore() {
        return more;
    }
}
