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

import org.apache.commons.lang3.ArrayUtils;

/**
 * Contains methods for encoding Strings into Filenames. Very conservative
 * encoding, allowing only letters, digits, and a small set of allowed 
 * non-alphanumeric characters, currently underscore and hyphen.
 * Use it when you have a string and need to use it as a filename but
 * it may contain forward slashes, dots, backslashes, and other characters
 * that may not be allowed in a filename by an operating system.
 * 
 * First version of this was naive and use % as the encoding character, which
 * causes havoc on Windows. 
 * 
 * See also: http://en.wikipedia.org/wiki/Filename
 * 
 * @author jbuhacoff
 */
public class Filename {
    
    private static final char[] allowedCharacters = new char[] { '_', '-' };
    
    /**
     * 
     * @param text to encode
     * @param escape character to prefix the hex encodings of special characters
     * @return encoded text
     */
    public static String encode(String text, char escape) {
        StringBuilder s = new StringBuilder();
        for(int i=0; i<text.length(); i++) {
            if( Character.isLetter(text.charAt(i)) || Character.isDigit(text.charAt(i)) || ArrayUtils.contains(allowedCharacters, text.charAt(i))) {
                s.append(text.charAt(i));
            }
            else {
                s.append(escape).append(String.format("%02x", text.codePointAt(i)));
            }
        }
        return s.toString();
    }
    
    /**
     * The % was chosen as the escape character for familiarity with URL's but
     * it's a bad choice for Windows platforms. On Windows, use the two-argument
     * form of this function that allows you to select a different escape character
     * such as #. 
     * 
     * @param text to encode using % as the escape character
     * @return encoded text
     */
    public static String encode(String text) {
        return encode(text, '%');
    }
    
    /**
     * 
     * @param text to decode
     * @param escape character that prefixes the hex encodings of special characters
     * @return decoded text
     */
    public static String decode(String text, char escape) {
        StringBuilder s = new StringBuilder();
        for(int i=0; i<text.length(); i++) {
            if( text.charAt(i) == escape ) {
                String hex = String.format("%c%c", text.charAt(i+1), text.charAt(i+2));
                s.append(Character.toChars(Integer.valueOf(hex, 16).intValue()));
                i += 2;
            }
            else {
                s.append(text.charAt(i));
            }
        }
        return s.toString();        
    }
    
    /**
     * The % was chosen as the escape character for familiarity with URL's but
     * it's a bad choice for Windows platforms. On Windows, use the two-argument
     * form of this function that allows you to select a different escape character
     * such as #. 
     * 
     * @param text to decode using % as the escape character
     * @return decoded text
     */
    public static String decode(String text) {
        return decode(text, '%');
    }

}
