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

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Currently supports only Glassfish. 
 * TODO: refactor for modular support and add support for Apache Httpd, Tomcat, Nginx
 * 
 * @author jbuhacoff
 */
public class HtmlErrorParser {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private String html;
    private String serverName;
    private String rootCause;
    
    public HtmlErrorParser(String html) {
        this.html = html;
        this.serverName = findServerName();
        log.debug("Server name is {}", serverName);
        this.rootCause = StringEscapeUtils.unescapeHtml(findRootCause());
        log.debug("Root cause is {}", rootCause);
    }
    
    private String findServerName() {
        if( html != null && html.contains("<h3>GlassFish Server") ) { //  Open Source Edition 3.1.2.2</h3>
            return "GlassFish";
        }
        return null;
    }
    
    public String getServerName() { return serverName; }
    
    private String findRootCause() {
        if( serverName != null && serverName.equals("GlassFish") ) {
            Pattern pRootCause = Pattern.compile(".*<b>root cause</b>"+RegexUtil.WHITESPACE_CHAR_CLASS+"*<pre>(.+)</pre>.*", Pattern.MULTILINE);
            Matcher mRootCause = pRootCause.matcher(html);
            if( mRootCause.matches() ) {
                return mRootCause.group(1);
            }
            Pattern pException = Pattern.compile(".*<b>exception</b>"+RegexUtil.WHITESPACE_CHAR_CLASS+"*<pre>(.+)</pre>.*", Pattern.MULTILINE);
            Matcher mException = pException.matcher(html);
            if( mException.matches() ) {
                return mException.group(1);
            }
        }
        log.debug("Didn't find root cause. HTML: "+html);
        return null;
    }
    
    public String getRootCause() { return rootCause; }
}
