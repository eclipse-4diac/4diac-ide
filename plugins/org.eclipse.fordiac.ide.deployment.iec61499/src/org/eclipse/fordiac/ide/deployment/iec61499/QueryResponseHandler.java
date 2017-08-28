/*******************************************************************************
 * Copyright (c) 2016, 2017 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Florian Noack, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.iec61499;

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class QueryResponseHandler extends DefaultHandler {

	private HashSet<String> queryResult = null;
	private boolean response = false;
	private boolean nameList = false;
	private final String PATTERN = "([A-Za-z_]\\w+)(\\1,\\s*[A-Za-z_]\\w+)*"; //$NON-NLS-1$

	public QueryResponseHandler() {
		queryResult = new HashSet<String>();
	}

	public void reset() {
		queryResult = new HashSet<String>();
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) {
		if (qName.equalsIgnoreCase("Response")) { //$NON-NLS-1$
			response = true;
		} else if (qName.equalsIgnoreCase("NameList")) { //$NON-NLS-1$
			nameList = true;
		} else {
			response = false;
			nameList = false;
		}
	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
		if (response && nameList) {
			String query = new String(ch, start, length);
			Pattern pattern = Pattern.compile(PATTERN);
			Matcher matcher = pattern.matcher(query);
			while (matcher.find()) {
				queryResult.add(matcher.group());
			}
			response = false;
			nameList = false;
		}
	}

	public HashSet<String> getQueryResult() {
		return this.queryResult;
	}
}
