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

package com.intel.mtwilson.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Definition of a Resource here is something that can be re-used. Therefore
 * the getInputStream must be repeatable and it must be possible to repeatedly
 * call getOutputStream to obtain an OutputStream to write to.
 * 
 * XXX Should we add a getURL() method so users can identify the resource?
 * The creator of the resource would set the URL.
 * 
 * @author jbuhacoff
 */
public interface Resource {
    /**
     * If the resource does not have any data, this method may return null or
     * it may throw an EOFException.
     * If the resource represents a file and the file is not found, calling
     * this method may throw a FileNotFoundException.  
     * @return
     * @throws IOException 
     */
    InputStream getInputStream() throws IOException;
    
    /**
     * You must close the OutputStream after writing to ensure that the
     * contents are written to their destination.
     * @return
     * @throws IOException 
     */
    OutputStream getOutputStream() throws IOException;
}
