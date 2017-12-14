/*******************************************************************************
 * Copyright (c) 2014 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.util;

import java.util.StringTokenizer;

public class STStringTokenHandling {

	public final static String stTokenDelimiters = new String(
			" &():.=[]+-*/><;\n\r\t\"\'!,"); //$NON-NLS-1$
	
	public static String replaceSTToken(String stString, final String oldToken, final String newToken){
		String retVal = new String();
		
		StringTokenizer t = new StringTokenizer(stString, STStringTokenHandling.stTokenDelimiters,
				true);
		while (t.hasMoreElements()) {
			String s = t.nextToken();
			if(s.equals(oldToken)){
				retVal += newToken;
			}else{
				retVal += s;
			}
		}
		return retVal;
	}

}
