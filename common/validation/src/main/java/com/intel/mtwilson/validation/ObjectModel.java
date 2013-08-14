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

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * This is a convenience base class for objects that implement the Model 
 * interface. It handles Fault collection and caching of validation results.
 * 
 * You can construct an ObjectModel with its subclass-specific constructors
 * and/or setter methods. The model will only be validated when you first call isValid()
 * or if you change it after that.  Repeated calls to isValid() without modifying
 * the object will immediately return the cached result.
 * 
 * @since 1.1
 * @author jbuhacoff
 */
public abstract class ObjectModel implements Model {
    private transient Integer lastHashCode = null;
    private transient ArrayList<Fault> faults = null;
    
    abstract protected void validate();
    
    protected final void fault(Fault fault) {
        faults.add(fault);
    }

    protected final void fault(String description) {
        faults.add(new Fault(description));
    }
    
    protected final void fault(String format, Object... args) {
        faults.add(new Fault(format, args));
    }
    
    protected final void fault(Throwable e, String description) {
        faults.add(new Fault(e, description));
    }
    
    protected final void fault(Throwable e, String format, Object... args) {
        faults.add(new Fault(e, format, args));
    }

    protected final void fault(Model m, String format, Object... args) {
        faults.add(new Fault(m, format, args));
    }
    
    /**
     * If the model has changed since the last call to isValid() then it will be revalidated.
     * @return true if the model is valid
     */
    @Override
    public final boolean isValid() {
        if( faults == null || lastHashCode == null || lastHashCode != hashCode() ) {
            faults = new ArrayList<Fault>(); // make a new list instead of clearing so that a caller can say getFaults(), make a change, then getFaults() again, and compare the faults. 
            validate();
            lastHashCode = hashCode();
        }
        return faults.isEmpty();
    }
    
    /**
     * 
     * @return a list of faults 
     */
    @Override
    public final List<Fault> getFaults() {
        return faults;
    }
    
    /**
     * Default implementation for derived classes automatically generates the 
     * logic to compare non-static non-transient fields using reflection.
     * This is slower than writing a custom method but provides a nice default
     * behavior with no additional work by the derived class.
     * 
     * Derived classes that need to speed up the implementation can override
     * this method and use something like this:
     * MyDerivedClass rhs = (MyDerivedClass)other;
     * return new EqualsBuilder().append(myMemberVar, rhs.myMemberVar).isEquals();
     * 
     * See also: the lombok project can generate equals() and hashCode() via annotations http://projectlombok.org/features/EqualsAndHashCode.html
     * 
     * 
     * @param other
     * @return object comparison result conforming to the Java spec
     */
    @Override
    public boolean equals(Object other) {
        if( other == null ) { return false; }
        if( other == this ) { return true; }
        if( other.getClass() != this.getClass() ) { return false; } // this refers to the subclass, not the abstract base class
        return EqualsBuilder.reflectionEquals(this, other, false);
    }
    
    /**
     * Default implementation for derived classes automatically generates the
     * hash code using reflection to process non-static and non-transient fields.
     * This is slower than writing a custom method for each class to specify
     * the fields to be used in the calculation (can still use HashCodeBuilder)
     * but provides a nice default behavior with no additional work by the 
     * derived class. 
     * 
     * Derived classes that need to speed up the implementation
     * can override this method and use something like this:
     * return new HashCodeBuilder(17,37).append(myMemberVariable).toHashCode();
     * 
     * See also: the lombok project can generate equals() and hashCode() via annotations http://projectlombok.org/features/EqualsAndHashCode.html
     * 
     * @return a hash code conforming to the Java specification
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, false)+getClass().hashCode(); // this & getClass() return the subclass, not the abstract base class
    }
    
}
