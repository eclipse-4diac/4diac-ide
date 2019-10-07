/********************************************************************************
 * Copyright (c)  2008 - 2014, 2016, 2017  Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 				 2018 - 2019 Johannes Keppler University, Linz
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Monika Wenger, Alois Zoitl
 *      - initial API and implementation and/or initial documentation
 *   Alois Zoitl - Refactored class hierarchy of xml exporters
 *   Alois Zoitl - fixed coordinate system resolution conversion in in- and export    
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.dataexport;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.CoordinateConverter;
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
import org.w3c.dom.Element;

public class SystemExporter extends CommonElementExporter{
	private final AutomationSystem system;
	private final Element systemRootElement;

	
	public SystemExporter(final AutomationSystem system) {
		super();
		this.system = system;
		systemRootElement = createRootElement(system, LibraryElementTags.SYSTEM); 
	}
	

	public void saveSystem(final IFile targetFile){	 
		// write the dom to the file
		if(null != getDom()){
			addIdentification(systemRootElement, system);
			addVersionInfo(systemRootElement, system);
			
			addApplication();
			
			SystemConfiguration systemConfiguration = system.getSystemConfiguration();
			if (null != systemConfiguration) {			
				systemConfiguration.getDevices().forEach(device -> addDevice(device));
				addMapping();
				addSegment(systemConfiguration.getSegments());
				addLink(systemConfiguration.getLinks());
			}
			writeToFile(targetFile);
		}
	}
	
	private void addApplication() {
		for (Application app : system.getApplication()) {
			Element appElement = createElement(LibraryElementTags.APPLICATION_ELEMENT);
			setNameAndCommentAttribute(appElement, app);
			addAttributes(appElement, app.getAttributes(), app);
			appElement.appendChild(new FBNetworkExporter(getDom()).createFBNetworkElement(app.getFBNetwork()));
			systemRootElement.appendChild(appElement);
		}
	}


	private void addLink(List<Link> linksList) {
		for (Link link : linksList) {
			Element linkElement = createElement(LibraryElementTags.LINK_ELEMENT);
			linkElement.setAttribute(LibraryElementTags.SEGMENT_NAME_ELEMENT, link.getSegment().getName());
			linkElement.setAttribute(LibraryElementTags.SEGMENT_COMM_RESOURCE, link.getDevice().getName());
			linkElement.setAttribute(LibraryElementTags.COMMENT_ATTRIBUTE, link.getComment());
			systemRootElement.appendChild(linkElement);
		}
	}

	private void addSegment(List<Segment> segmentsList) {
		for (Segment segment : segmentsList) {
			Element segmentElement = createElement(LibraryElementTags.SEGMENT_ELEMENT);
			setNameTypeCommentAttribute(segmentElement, segment, segment.getType());
			
			CommonElementExporter.setXYAttributes(segmentElement, segment.getX(), segment.getY());
			segmentElement.setAttribute(LibraryElementTags.DX1_ATTRIBUTE, 
					CoordinateConverter.INSTANCE.convertTo1499XML(segment.getWidth()));
			addColorAttributeElement(segmentElement, segment);
			addAttributes(segmentElement, segment.getAttributes(), segment);
			systemRootElement.appendChild(segmentElement);
		}
	}

	private void addMapping() {
		for (Mapping mappingEntry : system.getMapping()) {
			String fromString = getFullHierarchicalName(mappingEntry.getFrom());
			String toString = getFullHierarchicalName(mappingEntry.getTo());
			
			if((null != fromString) && (null != toString)){
				Element mappingElement = createElement(LibraryElementTags.MAPPING_ELEMENT);
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
	private static String getFullHierarchicalName(FBNetworkElement fbNetworkElement) {
		Deque<String> names = new ArrayDeque<>();

		if(null != fbNetworkElement){
			names.addFirst(fbNetworkElement.getName());
			EObject container = fbNetworkElement;
			do{
				FBNetworkElement runner = (FBNetworkElement)container;
				container = runner.getFbNetwork().eContainer();
				if(container instanceof INamedElement){
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

	private void addDevice(Device device) {
		Element deviceElement = createElement(LibraryElementTags.DEVICE_ELEMENT);
		setNameTypeCommentAttribute(deviceElement, device, device.getType());
		exportXandY(device, deviceElement);
		addParamsConfig(deviceElement, device.getVarDeclarations());
		addDeviceAttributes(deviceElement, device.getAttributes(), device);
		addResources(deviceElement, device.getResource());
		systemRootElement.appendChild(deviceElement);
	}

	private void addDeviceAttributes(Element deviceElement, EList<Attribute> attributes, Device device) {
		addDeviceProfile(deviceElement, device);
		addColorAttributeElement(deviceElement, device);
		addAttributes(deviceElement, attributes, device);
	}

	private void addAttributes(Element element, EList<Attribute> attributes, ConfigurableObject configurableObject) {
		for(Attribute attribute : attributes) {
			Element domAttribute = createAttributeElement(attribute.getName(), attribute.getType().getName(), attribute.getValue(), attribute.getComment());
			element.appendChild(domAttribute);
		}
	}
	
	private void addDeviceProfile(Element deviceElement, Device device) {
		String profileName = device.getProfile();
		if(null != profileName && !"".equals(profileName)){   //$NON-NLS-1$
			Element profileAttribute = createAttributeElement(LibraryElementTags.DEVICE_PROFILE, "STRING", profileName, "device profile");		 //$NON-NLS-1$ //$NON-NLS-2$
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
		Element resourceElement = getDom().createElement(LibraryElementTags.RESOURCE_ELEMENT);
		setNameTypeCommentAttribute(resourceElement, resource, resource.getType());
		setXYAttributes(resourceElement, 0, 0);		
		addParamsConfig(resourceElement, resource.getVarDeclarations());
		resourceElement.appendChild(new FBNetworkExporter(getDom()).createFBNetworkElement(resource.getFBNetwork()));
		parent.appendChild(resourceElement);
	}


}
