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

package com.intel.mountwilson.common;

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author jbuhacoff
 */
public class CommandArg {
    
    private static final HashSet<Character> badFilenameCharset;
    private static final char[] badFilenameChars = new char[] { '%', '\'', '"', '&', '*' }; // percent doesn't cause problems but we're using it as the escape character so that's why we encode it too
    
    static {
        HashSet<Character> set = new HashSet<Character>();
        for(int i=0; i<badFilenameChars.length; i++) {
            set.add(Character.valueOf(badFilenameChars[i]));
        }
        badFilenameCharset = set;
    }
    
    public static String escapeFilename(String arg) {
        StringBuilder s = new StringBuilder(arg.length()*5/4);
        int len = arg.length();
        for(int i=0; i<len; i++) {
            char c = arg.charAt(i);
            if(badFilenameCharset.contains(Character.valueOf(c))) {
                s.append("%").append(Integer.toHexString((int)c));
            }
            else {
                s.append(c);
            }
        }
        return s.toString();
    }
    
    private static Pattern hexPattern = Pattern.compile("%([A-Za-z0-9]{2})");
    public static String unescapeFilename(String arg) {
        StringBuilder s = new StringBuilder(arg.length());
        int cursor = 0;
        Matcher m = hexPattern.matcher(arg);
        while( m.find() ) {
            if( m.start() > cursor ) {
                s.append(arg.substring(cursor, m.start())); // string BEFORE the code
            }
            String hex = m.group();
            char c = (char)Integer.parseInt(hex, 16);
            s.append(c);
            cursor = m.end();
        }
        if( cursor < arg.length() ) {
            s.append(arg.substring(cursor)); // append the remainder of the string AFTER the last code
        }
        return s.toString();
    }

}
