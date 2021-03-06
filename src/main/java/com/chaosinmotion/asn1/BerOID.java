/*  BerBoolean.java
 *
 *  Created on Jun 2, 2006 by William Edward Woody
 */

/*
 * Copyright 2007 William Woody, All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, 
 * are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this list 
 * of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice, this 
 * list of conditions and the following disclaimer in the documentation and/or other 
 * materials provided with the distribution.
 * 
 * 3. Neither the name of Chaos In Motion nor the names of its contributors may be used 
 * to endorse or promote products derived from this software without specific prior 
 * written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY 
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES 
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT 
 * SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, 
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED 
 * TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR 
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN 
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH 
 * DAMAGE.
 * 
 * Contact William Woody at woody@alumni.caltech.edu or at woody@chaosinmotion.com. 
 * Chaos In Motion is at http://www.chaosinmotion.com
 */

package com.chaosinmotion.asn1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Represents an object identifier in the BER stream. An object identifier is a
 * sequence of integers which represent an OID.
 * 
 * TODO: Add a parser to convert to and from an OID string, a dotted-notation 
 * string representing an OID.
 */
public class BerOID extends JacNode
{
    private long[] fValue;
    
    /**
     * This constructor is added by Fatih Batuk
     * Costructs empty BerOID
     * @author Fatih Batuk
     */
    public BerOID()	
    { 
    	super(Tag.OBJECTID);
    }

    /**
     * Construct a new boolean object with the specified tag
     * @param tag
     * @param value
     */
    public BerOID(int tag, long[] value)
    {
        super(tag);
        fValue = value;
    }
    
    /**
     * Construct a boolean of type BOOLEAN
     * @param value
     */
    public BerOID(long[] value)
    {
        this(Tag.OBJECTID,value);
    }
    
    /**
     * Construct a boolean from the input stream
     * @param tag
     * @param stream
     * @throws IOException
     */
    public BerOID(int tag, BerInputStream stream) throws IOException
    {
        super(tag);
        
        fValue = stream.readOID();
    }

    /**
     * Write this BER element to the output stream
     * Comment
     * @param stream
     * @throws IOException
     * @see com.chaosinmotion.asn1.BerNode#writeElement(com.chaosinmotion.asn1.BerOutputStream)
     */
    public void writeElement(BerOutputStream stream) throws IOException
    {
    	if ( ! isInitialized ) {	//added by Fatih Batuk
    		throw new AsnEncodingException("\n >> OBJECT ID is uninitialized(empty) and will not be encoded ! (If exists)OID name is : " + getName());
    	}
    	
    	/*
    	 * Added by Fatih Batuk
    	 * for explicitly encoding :
    	 */
    	if (getTaggingMethod() == Tag.EXPLICIT) {	

    		 stream.writeBerTag(getTag() | Tag.CONSTRUCTED);
    		 
    		 ByteArrayOutputStream baos = new ByteArrayOutputStream();
    		 
    		 //stream.getEncodingMethod() returns the encoding method : BER, DER or CER
             BerOutputStream tmp = new BerOutputStream(baos,stream.getEncodingMethod());
             
             tmp.writeBerTag(Tag.OBJECTID);
             tmp.writeOID(fValue);
             
             tmp.close();
             baos.close();
             
             byte[] data = baos.toByteArray();
             stream.writeBerLength(data.length);
             stream.write(data);       
    	}
    	else {
    		stream.writeBerTag(getTag());
    		stream.writeOID(fValue);
    	}
    }

    /**
     * Return the value of this boolean object
     * @return
     */
    public long[] getValue()
    {
        return fValue;
    }
    
    /**
     * added by Fatih Batuk
     * Sets the value of this object
     * @author Fatih Batuk
     */
    public void setValue(long[] parameter)
    {
        fValue = parameter;
        isInitialized = true;
    }

    public String toString()
    {
        return "BerOID(" + Tag.toString(getTag()) + ")=" + fValue;
    }
    
    /**
     * Added by Fatih Batuk to decode the object..
     * @author Fatih Batuk
     */
    public void readElement(BerInputStream in) throws IOException {  
		
		if(getTaggingMethod() == Tag.EXPLICIT) {
			ReadSequence readSeq = new ReadSequence(in);
			if (0 != (readSeq.readBerTag())) {
				setValue(in.readOID());
			 }
		}
		else {
			setValue(in.readOID());
		}
	}
}


