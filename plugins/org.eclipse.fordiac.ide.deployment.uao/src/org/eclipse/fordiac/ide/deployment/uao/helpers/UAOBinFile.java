/*******************************************************************************
 * Copyright (c) 2024 AIMIRIM STI - https://en.aimirimsti.com.br/
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Pedro Ricardo
 *   Felipe Adriano
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.uao.helpers;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;


public class UAOBinFile {

	private Document deployXml;
	private List<String> stringList = new ArrayList<>();
	
    private final byte OPEN_TAG_W_ATTRIBS = (byte)0xC4;
    private final byte OPEN_TAG_WO_ATTRIBS = 0x44;
    private final byte OPEN_CLOSE_TAG_W_ATTRIBS = (byte)0x84;
    private final byte OPEN_CLOSE_TAG_WO_ATTRIBS = 0x04;
    private final byte ATTRIB_VALUE = 0x03;
    private final byte ATTRIB_NAME = (byte)0x83;
    private final byte CLOSE = 0x01;

	/** Initialize UAOBinFile class
	 * @param xml The element to parse*/
	public UAOBinFile(Document xml) {
		this.deployXml = xml;
	}

	/** Convert the XML into the BIN format
	 * @return Bytes for the bin file.*/
	public byte[] parseToBin() {
		Element root = deployXml.getDocumentElement();
		stringList = extractStringList(root);
		stringList = removeDuplicates(stringList);
		
		List<Byte> bin = new ArrayList<>();
		
		bin.addAll(binHeader());
		bin.addAll(stringListInBytes());
		bin.addAll(xmlInBytes(root));
		
		return(toPrimitive(bin.toArray(new Byte[0])));
	}

	/** Parse an integer into a single byte
	 * @param i Integer to convert.
	 * @param endianess Byte order.
	 * @return Bytes */
	public static byte[] write_byte(int i, ByteOrder endianess) {
	    return(ByteBuffer.allocate(1).order(endianess).put((byte)i).array());
	}
	
	/** Parse an integer into a single byte assuming Little Endian
	 * @param i Integer to convert.
	 * @return Bytes */
	public static byte[] write_byte(int i) {
	    return(write_byte(i,ByteOrder.LITTLE_ENDIAN));
	}
	
	/** Parse an integer into 4 bytes
	 * @param i Integer to convert.
	 * @param endianess Byte order.
	 * @return Bytes*/
	public static byte[] write_word(int i, ByteOrder endianess) {
	    return(ByteBuffer.allocate(4).order(endianess).putInt(i).array());
	}
	

	/** Parse an integer into 4 bytes
	 * @param i Integer to convert.
	 * @return Bytes */
	public static byte[] write_word(int i) {
	    return(write_word(i,ByteOrder.LITTLE_ENDIAN));
	}

	/** Find the starting byte index of a string in the string list.
	 * @param str The sting to search for.
	 * @return The index in bytes of the string. Or the length..*/
	private int searchStringList(String str) {
		int idx = 0;
	    if (str==null) {
	    	idx = stringList.size();
	    } else {
	        idx = stringList.indexOf(str);	
	    }
	    int bytepos = 0;
		for (String s : stringList.subList(0, idx)) {
			bytepos += formatStringBytes(s).length;
	    }
	    return(bytepos);
	}

	/** Recursively iterate in xml element finding strings.
	 * @param tag Xml element to search into.
	 * @return  Array of all strings in XML*/
	private List<String> extractStringList(Element tag) {
		List<String> tmpList = new ArrayList<>();
		
		tmpList.add(tag.getTagName());
		
		NamedNodeMap tagAttribs = tag.getAttributes();
		int nattr = tagAttribs.getLength(); 
		for (int n=0; n<nattr; n++ ) {
			if (tagAttribs.item(n).getNodeValue()!="") { //$NON-NLS-1$
				tmpList.add( tagAttribs.item(n).getNodeName()+"=" ); //$NON-NLS-1$
			}
		}
		
		NodeList children = tag.getChildNodes();
		int nchild = children.getLength(); 
		for (int n=0; n<nchild; n++ ) {
			// NOTE: Recursive call
			tmpList.addAll(extractStringList((Element)children.item(n)));
		}
		return(tmpList);
	}

	/**  Remove the duplicate entries in a list.
	 * @param strlist List of strings.
	 * @return Unique list of strings*/
	private static List<String> removeDuplicates(List<String> strlist) {
		return(new ArrayList<>(new LinkedHashSet<>(strlist)));
	}

	/** Convert array to list and append to an existing list.
	 * @param list List to append.
	 * @param bytearray Array to convert.
	 * @return Bytes*/
	private static void concatBytesInList(List<Byte> list ,byte [] bytearray) {
		for (byte b : bytearray) {list.add(Byte.valueOf(b));}
	}

	/** Initial bytes in Bin in file.
	 * @return Bytes */
	private List<Byte> binHeader() {
		List<Byte> header = new ArrayList<>();
		
		final byte[] ini = {0x03, 0x00, 0x00, 0x00, 0x00, 0x6A, 0x00, 0x00, 0x00};
		concatBytesInList(header, ini);
		
		int strListSize = searchStringList(null);
		concatBytesInList(header, write_word(strListSize));
		
		return(header);
	}

	/** Find the byte for the tag behavior.
	 * @param hasAT True if it has attributes.
	 * @param hasChild True if it has children.
	 * @return Byte*/
	private byte[] tag_id(boolean hasAt, boolean hasChild) {
		int id;
	    if (hasAt && hasChild) {
	    	id = OPEN_TAG_W_ATTRIBS;
	    } else if (hasChild && !hasAt) {
	    	id = OPEN_TAG_WO_ATTRIBS;
	    } else if (!hasChild && hasAt) {
	    	id = OPEN_CLOSE_TAG_W_ATTRIBS;
	    } else { // (!hasAt && !hasChild)
	    	id = OPEN_CLOSE_TAG_WO_ATTRIBS;
	    }
		return(write_byte(id));
	}

	/** Format a string and into bytes.
	 * @param str A name to encode in bytes.
	 * @return Bytes.*/
	private static byte[] formatStringBytes(String str) {
		final ByteBuffer bb = ByteBuffer.allocate(str.length()+1);
		bb.put(str.getBytes());
		bb.put((byte) 0x00);
		return(bb.array());
	}

	/** Transform the whole string list into bytes
	 * @return List of Bytes representing the string list.*/
	private List<Byte> stringListInBytes() {
		List<Byte> byteStrList = new ArrayList<>();
		for (String s : stringList) {
			concatBytesInList(byteStrList,formatStringBytes(s));
		}
		return(byteStrList);
	}

	/** Find the tag in string list and get the position in bytes.
	 * @param name Tag or Attribute name.
	 * @return Byte index of this string.*/
	private List<Byte> tagNameInBytes(String name) {
		List<Byte> byteStrList = new ArrayList<>();
		int strListIndex = searchStringList(name);
		concatBytesInList(byteStrList, write_word(strListIndex));
		return(byteStrList);
	}
	
	/** Parse a tag attributes into bytes.
	 * @param attribList List of attributes on XML element.
	 * @return List of bytes in bin format */
	private List<Byte> attribListInBytes(NamedNodeMap attribList) {
		List<Byte> byteAttrList = new ArrayList<>();
		int nattr = attribList.getLength(); 
		for (int n=0; n<nattr; n++ ) {
			String name = attribList.item(n).getNodeName();
			String value = attribList.item(n).getNodeValue();
			if (value!="") { //$NON-NLS-1$
				concatBytesInList(byteAttrList, write_byte(ATTRIB_NAME));
				byteAttrList.addAll(tagNameInBytes(name+"=")); //$NON-NLS-1$
				concatBytesInList(byteAttrList, write_byte(ATTRIB_VALUE));
				concatBytesInList(byteAttrList, formatStringBytes(value));
			}
		}
		concatBytesInList(byteAttrList, write_byte(CLOSE));
		return(byteAttrList);
	}

	/** Convert 'Byte' array to primitive type 'byte' array.
	 * @param src Array of 'Byte'.
	 * @return Array of 'byte'.*/
	private static byte[] toPrimitive(Byte[] src) {
		byte[] dest = new byte[src.length];
		int i=0;
		for(Byte b: src)
		    dest[i++] = b.byteValue();
		return(dest);
	}
	
	
	/** Recursively iterates over an XML element and parse it
	 *  to bin bytes
	 * @param ele XML element.
	 * @return List of bin file Bytes.*/
	private List<Byte> xmlInBytes(Element ele) {
		List<Byte> byteXmlList = new ArrayList<>();
		
		boolean hasAt = ele.hasAttributes();
		boolean hasChild = ele.hasChildNodes();
		
		concatBytesInList(byteXmlList,tag_id(hasAt,hasChild));
		
		byteXmlList.addAll(tagNameInBytes(ele.getTagName()));
		if(hasAt) {
			byteXmlList.addAll(attribListInBytes(ele.getAttributes()));
		}
		
		if(hasChild) {
			NodeList children = ele.getChildNodes();
			int nchild = children.getLength(); 
			for (int n=0; n<nchild; n++ ) {
				// NOTE: Recursive call
				byteXmlList.addAll(xmlInBytes((Element)children.item(n)));
			}
			concatBytesInList(byteXmlList,write_byte(CLOSE));
		}
		
		return(byteXmlList);
	}

}
