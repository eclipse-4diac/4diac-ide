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
package org.eclipse.fordiac.ide.monitoring.communication;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMLInfoImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLMapImpl;
import org.eclipse.fordiac.ide.monitoring.monCom.MonComPackage;

public class MonitoringCommunicationOptions {
	public Map<String, Object> loadOptions;

	public MonitoringCommunicationOptions() {
		loadOptions = new HashMap<String, Object>();
		loadOptions.put(XMLResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE);

		XMLMapImpl map = new XMLMapImpl();
		map.setNoNamespacePackage(MonComPackage.eINSTANCE);

		XMLInfoImpl fbInfo = new XMLInfoImpl();
		fbInfo.setXMLRepresentation(XMLInfoImpl.ELEMENT);
		EClass fbClass = MonComPackage.eINSTANCE.getFB();
		map.add(fbClass, fbInfo);

		XMLInfoImpl portInfo = new XMLInfoImpl();
		portInfo.setXMLRepresentation(XMLInfoImpl.ELEMENT);
		EClass portClass = MonComPackage.eINSTANCE.getPort();
		map.add(portClass, portInfo);

		XMLInfoImpl dataInfo = new XMLInfoImpl();
		dataInfo.setXMLRepresentation(XMLInfoImpl.ELEMENT);
		EClass dataClass = MonComPackage.eINSTANCE.getData();
		map.add(dataClass, dataInfo);

		XMLInfoImpl responseInfo = new XMLInfoImpl();
		responseInfo.setXMLRepresentation(XMLInfoImpl.ELEMENT);
		EClass responseClass = MonComPackage.eINSTANCE.getResponse();
		map.add(responseClass, responseInfo);

		XMLInfoImpl watchesInfo = new XMLInfoImpl();
		watchesInfo.setXMLRepresentation(XMLInfoImpl.ELEMENT);
		EClass watchesClass = MonComPackage.eINSTANCE.getWatches();
		map.add(watchesClass, watchesInfo);

		EClass resourceClass = MonComPackage.eINSTANCE.getResource();
		XMLInfoImpl resourceInfo = new XMLInfoImpl();
		resourceInfo.setXMLRepresentation(XMLInfoImpl.ELEMENT);
		resourceInfo.setName("Resource"); //$NON-NLS-1$
		map.add(resourceClass, resourceInfo);

		loadOptions.put(XMLResource.OPTION_XML_MAP, map);
	}
	
	public Map<String, Object> getLoadOptions() {
		return loadOptions;
	}
}