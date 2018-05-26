/********************************************************************************
 * Copyright (c)  2008 - 2014, 2016, 2017  Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Gerhard Ebenhofer, Monika Wenger, Alois Zoitl
 *    - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.dataexport;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.Activator;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.Link;
import org.eclipse.fordiac.ide.model.libraryElement.Mapping;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.Segment;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SystemExporter {
	private List<String> warnings = new ArrayList<>();
	private List<String> errors = new ArrayList<>();
	private List<String> infos = new ArrayList<>();
	
	private final Document dom;
	private AutomationSystem system;
	private Element systemRootElement;

	/**
	 * Should only be called by the extension point!
	 */
	public SystemExporter() {
		dom = createDomElement();
	}
	
	private static Document createDomElement() {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;

		try {
			db = dbf.newDocumentBuilder();
			return db.newDocument();
		} catch (ParserConfigurationException e) {
			Activator.getDefault().logError(e.getMessage(), e);
			return null;
		}
	}

	public void generateSYSFileContent(final AutomationSystem system, final Result result) throws TransformerException{	 
		// write the dom to the file
		if(null != dom){
			addSystem(system);
			Transformer transformer = createTransformer();
			Source source = new DOMSource(dom); // Document to be transformed transformed
			transformer.transform(source, result);
		}
	}
	
	/*
	 * <!-- System elements --> <!ELEMENT System (Identification?, VersionInfo+,
	 * Application, Device+, Mapping, Segment, Link)> <!ATTLIST System Name
	 * CDATA #REQUIRED Comment CDATA #IMPLIED > <!ELEMENT Application
	 * (FBNetwork)> <!ATTLIST Application Name CDATA #REQUIRED Comment CDATA
	 * #IMPLIED > <!ELEMENT Mapping EMPTY> <!ATTLIST Mapping From CDATA
	 * #REQUIRED To CDATA #REQUIRED > <!ELEMENT Device
	 * (Parameter,Resource,FBNetwork?)> <!ATTLIST Device Name CDATA #REQUIRED
	 * Type CDATA #REQUIRED Comment CDATA #IMPLIED x CDATA #IMPLIED y CDATA
	 * #IMPLIED > /
	 * 
	 */
	private void addSystem(final AutomationSystem system) {
		this.system = system;
		systemRootElement = dom.createElement(LibraryElementTags.SYSTEM); 
		
		CommonElementExporter.setNameAndCommentAttribute(systemRootElement, system);
		dom.appendChild(systemRootElement);

		CommonElementExporter.addIdentification(dom, systemRootElement, system);
		CommonElementExporter.addVersionInfo(dom, systemRootElement, system);

		addApplication();
		
		SystemConfiguration systemConfiguration = system.getSystemConfiguration();
		if (null != systemConfiguration) {			
			addDevices(systemConfiguration.getDevices());
			addMapping();
			addSegment(systemConfiguration.getSegments());
			addLink(systemConfiguration.getLinks());
		}

	}
	
	private void addApplication() {
		for (Application app : system.getApplication()) {
			Element appElement = dom.createElement(LibraryElementTags.APPLICATION_ELEMENT);
			CommonElementExporter.setNameAndCommentAttribute(appElement, app);
			addAttributes(appElement, app.getAttributes(), app);
			appElement.appendChild(new FBNetworkExporter(dom).createFBNetworkElement(app.getFBNetwork()));
			systemRootElement.appendChild(appElement);
		}

	}


	private void addLink(List<Link> linksList) {
		for (Link link : linksList) {
			Element linkElement = dom.createElement(LibraryElementTags.LINK_ELEMENT);
			linkElement.setAttribute(LibraryElementTags.SEGMENT_NAME_ELEMENT, link.getSegment().getName());
			linkElement.setAttribute(LibraryElementTags.SEGMENT_COMM_RESOURCE, link.getDevice().getName());
			linkElement.setAttribute(LibraryElementTags.COMMENT_ATTRIBUTE, link.getComment());
			systemRootElement.appendChild(linkElement);
		}
	}

	private void addSegment(List<Segment> segmentsList) {
		for (Segment segment : segmentsList) {
			Element segmentElement = dom.createElement(LibraryElementTags.SEGMENT_ELEMENT);
			CommonElementExporter.setNameTypeCommentAttribute(segmentElement, segment, segment.getType());
			segmentElement.setAttribute(LibraryElementTags.X_ATTRIBUTE, CommonElementExporter.reConvertCoordinate(segment.getX()).toString());
			segmentElement.setAttribute(LibraryElementTags.Y_ATTRIBUTE, CommonElementExporter.reConvertCoordinate(segment.getY()).toString());
			segmentElement.setAttribute(LibraryElementTags.DX1_ATTRIBUTE, CommonElementExporter.reConvertCoordinate(segment.getWidth()).toString());
			CommonElementExporter.addColorAttributeElement(dom, segmentElement, segment);
			addAttributes(segmentElement, segment.getAttributes(), segment);
			systemRootElement.appendChild(segmentElement);
		}

	}

	private void addMapping() {
		for (Mapping mappingEntry : system.getMapping()) {
			String fromString = getFullHierarchicalName(mappingEntry.getFrom());
			String toString = getFullHierarchicalName(mappingEntry.getTo());
			
			if((null != fromString) && (null != toString)){
				Element mappingElement = dom.createElement(LibraryElementTags.MAPPING_ELEMENT);
				mappingElement.setAttribute(LibraryElementTags.MAPPING_FROM_ATTRIBUTE, fromString);
				mappingElement.setAttribute(LibraryElementTags.MAPPING_TO_ATTRIBUTE, toString);
				systemRootElement.appendChild(mappingElement);
			}			
		}

	}

	/** Got trhough the containment of the FB and generate a name for all containers the FB is contained in
	 *  up to the application or the device (e.g., app1.subapp2.fbname, dev1.res3.fb3name). 
	 * 
	 * @param fbNetworkElement the FBNetworkElement for which the name should be gnerated
	 * @return dot seperated full name
	 */
	private String getFullHierarchicalName(FBNetworkElement fbNetworkElement) {
		Deque<String> names = new ArrayDeque<>();

		if(null != fbNetworkElement){
			names.addFirst(fbNetworkElement.getName());
			EObject container = fbNetworkElement;
			do{
				FBNetworkElement runner = (FBNetworkElement)container;
				container = runner.getFbNetwork().eContainer();
				if(null != container && container instanceof INamedElement){
					names.addFirst("."); //$NON-NLS-1$
					names.addFirst(((INamedElement)container).getName());
					if(container instanceof Resource){
						names.addFirst("."); //$NON-NLS-1$
						names.addFirst(((Resource)container).getDevice().getName());
						break;
					}
				}else{
					break;
				}
			}while(container instanceof FBNetworkElement);  //we are still in a subapp, try to find the resource or application as stop point
			
			StringBuilder fullName = new StringBuilder();
			for (String string : names) {
				fullName.append(string);
			}
			return fullName.toString();
		}
		
		return null;
	}

	private void addDevices(List<Device> devicesList) {
		for (Device device : devicesList) {
			addDevice(device);
		}
	}

	private void addDevice(Device device) {
		Element deviceElement = dom.createElement(LibraryElementTags.DEVICE_ELEMENT);
		CommonElementExporter.setNameTypeCommentAttribute(deviceElement, device, device.getType());
		CommonElementExporter.exportXandY(device, deviceElement);
		CommonElementExporter.addParamsConfig(dom, deviceElement, device.getVarDeclarations());
		addDeviceAttributes(deviceElement, device.getAttributes(), device);
		addResources(deviceElement, device.getResource());
		systemRootElement.appendChild(deviceElement);
	}

	private void addDeviceAttributes(Element deviceElement, EList<Attribute> attributes, Device device) {
		addDeviceProfile(deviceElement, device);
		CommonElementExporter.addColorAttributeElement(dom, deviceElement, device);
		addAttributes(deviceElement, attributes, device);
	}

	private void addAttributes(Element element, EList<Attribute> attributes, ConfigurableObject configurableObject) {
		for(Attribute attribute : attributes) {
			Element domAttribute = CommonElementExporter.createAttributeElement(dom, attribute.getName(), attribute.getType().getName(), attribute.getValue(), attribute.getComment());
			element.appendChild(domAttribute);
		}
	}
	
	private void addDeviceProfile(Element deviceElement, Device device) {
		String profileName = device.getProfile();
		if(null != profileName && !"".equals(profileName)){   //$NON-NLS-1$
			Element profileAttribute = CommonElementExporter.createAttributeElement(dom, LibraryElementTags.DEVICE_PROFILE, "STRING", profileName, "device profile");		 //$NON-NLS-1$ //$NON-NLS-2$
			deviceElement.appendChild(profileAttribute);
		}
		
	}

	private void addResources(Element deviceElement, List<Resource> resourcesList) {
		for (Resource resource : resourcesList) {
			if(!resource.isDeviceTypeResource()){
				addResource(deviceElement, resource);
			}
		}
	}

	/**
	 * Adds the resource.
	 * 
	 * @param parent
	 *            the parent
	 * @param resourceView
	 *            the resource view
	 */
	private void addResource(final Element parent, Resource resource) {
		Element resourceElement = dom.createElement(LibraryElementTags.RESOURCE_ELEMENT);
		CommonElementExporter.setNameTypeCommentAttribute(resourceElement, resource, resource.getType());
		CommonElementExporter.setXYAttributes(resourceElement, 0, 0);		
		CommonElementExporter.addParamsConfig(dom, resourceElement, resource.getVarDeclarations());
		resourceElement.appendChild(new FBNetworkExporter(dom).createFBNetworkElement(resource.getFBNetwork()));
		parent.appendChild(resourceElement);
	}

	private Transformer createTransformer()
			throws TransformerFactoryConfigurationError, TransformerConfigurationException {
		Transformer transformer;
		TransformerFactory tFactory = TransformerFactory.newInstance();
		tFactory.setAttribute("indent-number", Integer.valueOf(2)); //$NON-NLS-1$
		transformer = tFactory.newTransformer();
		transformer.setOutputProperty(javax.xml.transform.OutputKeys.DOCTYPE_SYSTEM,
				"http://www.holobloc.com/xml/LibraryElement.dtd"); //$NON-NLS-1$
		transformer.setOutputProperty(javax.xml.transform.OutputKeys.ENCODING, "UTF-8"); //$NON-NLS-1$
		transformer.setOutputProperty(javax.xml.transform.OutputKeys.VERSION, "1.0"); //$NON-NLS-1$
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2"); //$NON-NLS-1$ //$NON-NLS-2$
		transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes"); //$NON-NLS-1$
		transformer.setOutputProperty(javax.xml.transform.OutputKeys.STANDALONE, "no"); //$NON-NLS-1$
		transformer.setOutputProperty(javax.xml.transform.OutputKeys.METHOD, "xml"); //$NON-NLS-1$
		return transformer;
	}
	

	public List<String> getErrors() {
		return errors;
	}

	public List<String> getInfos() {
		return infos;
	}

	public List<String> getWarnings() {
		return warnings;
	}

}
