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

package com.intel.mtwilson;

import org.apache.commons.lang.StringUtils;

/**
 *
 * @author jbuhacoff
 */
public class RegexUtil {
    public static final String[] WHITESPACE_CHAR_ARRAY =  new String[] { 
                        "\\u0009", // CHARACTER TABULATION
                        "\\u000A", // LINE FEED (LF)
                        "\\u000B", // LINE TABULATION
                        "\\u000C", // FORM FEED (FF)
                        "\\u000D", // CARRIAGE RETURN (CR)
                        "\\u0020", // SPACE
                        "\\u0085", // NEXT LINE (NEL) 
                        "\\u00A0", // NO-BREAK SPACE
                        "\\u1680", // OGHAM SPACE MARK
                        "\\u180E", // MONGOLIAN VOWEL SEPARATOR
                        "\\u2000", // EN QUAD 
                        "\\u2001", // EM QUAD 
                        "\\u2002", // EN SPACE
                        "\\u2003", // EM SPACE
                        "\\u2004", // THREE-PER-EM SPACE
                        "\\u2005", // FOUR-PER-EM SPACE
                        "\\u2006", // SIX-PER-EM SPACE
                        "\\u2007", // FIGURE SPACE
                        "\\u2008", // PUNCTUATION SPACE
                        "\\u2009", // THIN SPACE
                        "\\u200A", // HAIR SPACE
                        "\\u2028", // LINE SEPARATOR
                        "\\u2029", // PARAGRAPH SEPARATOR
                        "\\u202F", // NARROW NO-BREAK SPACE
                        "\\u205F", // MEDIUM MATHEMATICAL SPACE
                        "\\u3000" // IDEOGRAPHIC SPACE
    };
    public static final String WHITESPACE_CHAR_STRING = StringUtils.join(WHITESPACE_CHAR_ARRAY, "");
/* A \s that actually works for Java’s native character set: Unicode */
public static final String     WHITESPACE_CHAR_CLASS = "["  + WHITESPACE_CHAR_STRING + "]";    
/* A \S that actually works for  Java’s native character set: Unicode */
public static final String NOT_WHITESPACE_CHAR_CLASS = "[^" + WHITESPACE_CHAR_STRING + "]";
    
}
