/********************************************************************************
 * Copyright (c)  2008 - 2014, 2016, 2017  Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 				  2018 - 2020 Johannes Keppler University, Linz
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
 *               - fixed coordinate system resolution conversion in in- and export
 *               - changed exporting the Saxx cursor api
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.dataexport;

import java.util.ArrayDeque;
import java.util.Deque;

import javax.xml.stream.XMLStreamException;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.Activator;
import org.eclipse.fordiac.ide.model.CoordinateConverter;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.Link;
import org.eclipse.fordiac.ide.model.libraryElement.Mapping;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.Segment;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;

public class SystemExporter extends CommonElementExporter {
	private final AutomationSystem system;

	public SystemExporter(final AutomationSystem system) {
		super();
		this.system = system;
	}

	public void saveSystem(final IFile targetFile) {
		long startTime = System.currentTimeMillis();
		if (null != getWriter()) {
			try {
				createNamedElementEntry(system, LibraryElementTags.SYSTEM);
				addIdentification(system);
				addVersionInfo(system);
				addApplications();

				SystemConfiguration systemConfiguration = system.getSystemConfiguration();
				if (null != systemConfiguration) {
					addDevices(systemConfiguration.getDevices());
					addMapping();
					addSegment(systemConfiguration.getSegments());
					addLink(systemConfiguration.getLinks());
				}

				addEndElement();
			} catch (XMLStreamException e) {
				Activator.getDefault().logError(e.getMessage(), e);
			}
			writeToFile(targetFile);
		}
		long endTime = System.currentTimeMillis();
		System.out
				.println("Overall saving time for System (" + system.getName() + "): " + (endTime - startTime) + " ms"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	private void addApplications() throws XMLStreamException {
		for (Application app : system.getApplication()) {
			addStartElement(LibraryElementTags.APPLICATION_ELEMENT);
			addNameAndCommentAttribute(app);
			addAttributes(app.getAttributes());
			new FBNetworkExporter(this).createFBNetworkElement(app.getFBNetwork());
			addEndElement();
		}
	}

	private void addLink(EList<Link> linkList) throws XMLStreamException {
		for (Link link : linkList) {
			addStartElement(LibraryElementTags.LINK_ELEMENT);
			getWriter().writeAttribute(LibraryElementTags.SEGMENT_NAME_ELEMENT, link.getSegment().getName());
			getWriter().writeAttribute(LibraryElementTags.SEGMENT_COMM_RESOURCE, link.getDevice().getName());
			getWriter().writeAttribute(LibraryElementTags.COMMENT_ATTRIBUTE, link.getComment());
			addEndElement();
		}
	}

	private void addSegment(EList<Segment> segementList) throws XMLStreamException {
		for (Segment segment : segementList) {
			addStartElement(LibraryElementTags.SEGMENT_ELEMENT);
			addNameTypeCommentAttribute(segment, segment.getType());
			addXYAttributes(segment.getX(), segment.getY());
			getWriter().writeAttribute(LibraryElementTags.DX1_ATTRIBUTE,
					CoordinateConverter.INSTANCE.convertTo1499XML(segment.getWidth()));
			addColorAttributeElement(segment);
			addAttributes(segment.getAttributes());
			addEndElement();
		}
	}

	private void addMapping() throws XMLStreamException {
		for (Mapping mappingEntry : system.getMapping()) {
			String fromString = getFullHierarchicalName(mappingEntry.getFrom());
			String toString = getFullHierarchicalName(mappingEntry.getTo());

			if ((null != fromString) && (null != toString)) {
				addEmptyStartElement(LibraryElementTags.MAPPING_ELEMENT);
				getWriter().writeAttribute(LibraryElementTags.MAPPING_FROM_ATTRIBUTE, fromString);
				getWriter().writeAttribute(LibraryElementTags.MAPPING_TO_ATTRIBUTE, toString);
			}
		}
	}

	/**
	 * Got through the containment of the FB and generate a name for all containers
	 * the FB is contained in up to the application or the device (e.g.,
	 * app1.subapp2.fbname, dev1.res3.fb3name).
	 *
	 * @param fbNetworkElement the FBNetworkElement for which the name should be
	 *                         generated
	 * @return dot separated full name
	 */
	private static String getFullHierarchicalName(FBNetworkElement fbNetworkElement) {
		Deque<String> names = new ArrayDeque<>();

		if (null != fbNetworkElement) {
			names.addFirst(fbNetworkElement.getName());
			EObject container = fbNetworkElement;
			do {
				FBNetworkElement runner = (FBNetworkElement) container;
				container = runner.getFbNetwork().eContainer();
				if (container instanceof INamedElement) {
					names.addFirst("."); //$NON-NLS-1$
					names.addFirst(((INamedElement) container).getName());
					if (container instanceof Resource) {
						names.addFirst("."); //$NON-NLS-1$
						names.addFirst(((Resource) container).getDevice().getName());
						break;
					}
				} else {
					break;
				}
			} while (container instanceof FBNetworkElement); // we are still in a subapp, try to find the resource or
																// application as stop point

			StringBuilder fullName = new StringBuilder();
			for (String string : names) {
				fullName.append(string);
			}
			return fullName.toString();
		}
		return null;
	}

	private void addDevices(EList<Device> deviceList) throws XMLStreamException {
		for (Device device : deviceList) {
			addStartElement(LibraryElementTags.DEVICE_ELEMENT);
			addNameTypeCommentAttribute(device, device.getType());
			addXYAttributes(device);
			addParamsConfig(device.getVarDeclarations());
			addDeviceAttributes(device);
			addResources(device.getResource());
			addEndElement();
		}
	}

	private void addDeviceAttributes(Device device) throws XMLStreamException {
		addDeviceProfile(device);
		addColorAttributeElement(device);
		addAttributes(device.getAttributes());
	}

	private void addDeviceProfile(Device device) throws XMLStreamException {
		String profileName = device.getProfile();
		if ((null != profileName) && !"".equals(profileName)) { //$NON-NLS-1$
			addAttributeElement(LibraryElementTags.DEVICE_PROFILE, "STRING", profileName, //$NON-NLS-1$
					"device profile"); //$NON-NLS-1$
		}
	}

	/**
	 * Adds the resource.
	 *
	 * @param resourceList the list of resource to add to the MXL file
	 */
	private void addResources(EList<Resource> resourceList) throws XMLStreamException {
		for (Resource resource : resourceList) {
			if (!resource.isDeviceTypeResource()) {
				addStartElement(LibraryElementTags.RESOURCE_ELEMENT);
				addNameTypeCommentAttribute(resource, resource.getType());
				addXYAttributes(0, 0);
				addParamsConfig(resource.getVarDeclarations());
				new FBNetworkExporter(this).createFBNetworkElement(resource.getFBNetwork());
				addEndElement();
			}
		}
	}

}
