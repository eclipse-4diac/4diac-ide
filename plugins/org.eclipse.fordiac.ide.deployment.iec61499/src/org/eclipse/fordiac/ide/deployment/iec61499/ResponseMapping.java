/*******************************************************************************
 * Copyright (c) 2012 Profactor GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.iec61499;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.XMLResource.XMLInfo;
import org.eclipse.emf.ecore.xmi.impl.XMLInfoImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLMapImpl;
import org.eclipse.fordiac.ide.deployment.devResponse.DevResponsePackage;

public class ResponseMapping {
	private Map<String, Object> loadOptions;

	public ResponseMapping() {
		loadOptions = new HashMap<String, Object>();
		loadOptions.put(XMLResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE);

		XMLMapImpl map = new XMLMapImpl();
		map.setNoNamespacePackage(DevResponsePackage.eINSTANCE);

		XMLInfoImpl fbInfo = new XMLInfoImpl();
		fbInfo.setXMLRepresentation(XMLInfo.ELEMENT);
		EClass fbClass = DevResponsePackage.eINSTANCE.getFB();
		map.add(fbClass, fbInfo);

		XMLInfoImpl portInfo = new XMLInfoImpl();
		portInfo.setXMLRepresentation(XMLInfo.ELEMENT);
		EClass portClass = DevResponsePackage.eINSTANCE.getPort();
		map.add(portClass, portInfo);

		XMLInfoImpl dataInfo = new XMLInfoImpl();
		dataInfo.setXMLRepresentation(XMLInfo.ELEMENT);
		EClass dataClass = DevResponsePackage.eINSTANCE.getData();
		map.add(dataClass, dataInfo);

		XMLInfoImpl responseInfo = new XMLInfoImpl();
		responseInfo.setXMLRepresentation(XMLInfo.ELEMENT);
		EClass responseClass = DevResponsePackage.eINSTANCE.getResponse();
		map.add(responseClass, responseInfo);

		XMLInfoImpl watchesInfo = new XMLInfoImpl();
		watchesInfo.setXMLRepresentation(XMLInfo.ELEMENT);
		EClass watchesClass = DevResponsePackage.eINSTANCE.getWatches();
		map.add(watchesClass, watchesInfo);

		EClass resourceClass = DevResponsePackage.eINSTANCE.getResource();
		XMLInfoImpl resourceInfo = new XMLInfoImpl();
		resourceInfo.setXMLRepresentation(XMLInfo.ELEMENT);
		resourceInfo.setName("Resource"); //$NON-NLS-1$
		map.add(resourceClass, resourceInfo);

		loadOptions.put(XMLResource.OPTION_XML_MAP, map);
	}
	
	public Map<String, Object> getLoadOptions() {
		return loadOptions;
	}
}