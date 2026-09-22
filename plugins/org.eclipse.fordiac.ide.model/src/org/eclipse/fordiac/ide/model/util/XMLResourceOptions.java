/*******************************************************************************
 * Copyright (c) 2026 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.util;

import java.util.Map;

import javax.xml.XMLConstants;

import org.eclipse.emf.ecore.xmi.XMLResource;

public class XMLResourceOptions {

	public static final Map<Object, Object> XML_PARSER_FEATURES = Map.of( //
			XMLConstants.FEATURE_SECURE_PROCESSING, Boolean.TRUE, //
			"http://apache.org/xml/features/disallow-doctype-decl", Boolean.TRUE, //$NON-NLS-1$
			"http://xml.org/sax/features/external-general-entities", Boolean.FALSE, //$NON-NLS-1$
			"http://xml.org/sax/features/external-parameter-entities", Boolean.FALSE, //$NON-NLS-1$
			"http://apache.org/xml/features/nonvalidating/load-external-dtd", Boolean.FALSE //$NON-NLS-1$
	);

	public static final Map<Object, Object> XML_PARSER_PROPERTIES = Map.of( //
			XMLConstants.ACCESS_EXTERNAL_DTD, "", //$NON-NLS-1$
			XMLConstants.ACCESS_EXTERNAL_SCHEMA, "" //$NON-NLS-1$
	);

	public static final Map<Object, Object> DEFAULT_LOAD_OPTIONS = Map.of( //
			XMLResource.OPTION_PARSER_FEATURES, XML_PARSER_FEATURES, //
			XMLResource.OPTION_PARSER_PROPERTIES, XML_PARSER_PROPERTIES //
	);

	private XMLResourceOptions() {
		throw new UnsupportedOperationException();
	}
}
