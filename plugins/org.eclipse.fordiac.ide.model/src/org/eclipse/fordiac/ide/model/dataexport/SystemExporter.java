/********************************************************************************
 * Copyright (c)  2008, 2022  Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 *                            Johannes Kepler University Linz
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
 *   Fabio Gandolfi - system export via outputStream
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.dataexport;

import java.util.List;

import javax.xml.stream.XMLStreamException;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Link;
import org.eclipse.fordiac.ide.model.libraryElement.Mapping;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.Segment;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;

public class SystemExporter extends AbstractTypeExporter {

	/**
	 * An adjusted FBNetwork exporter that takes mapping into account. That means:
	 *
	 * <ul>
	 * <li>mapped fbs are not written to xml</li>
	 * <li>connections between mapped fbs are not written to xml</li>
	 * <li>connections between mapped and not mapped fbs: names of</li>
	 * <li>mapped fbs are extended with the application hierarchy</li>
	 * </ul>
	 *
	 */
	private static final class ResourceInSystemFBNetworkExporter extends FBNetworkExporter {
		private ResourceInSystemFBNetworkExporter(final CommonElementExporter parent) {
			super(parent);
		}

		@Override
		protected void addFBNetworkElement(final FBNetworkElement fbnElement) throws XMLStreamException {
			if (!fbnElement.isMapped()) {
				super.addFBNetworkElement(fbnElement);
			}
		}

		@Override
		protected void addConnections(final List<? extends Connection> connections, final String connectionElementName,
				final FBNetwork fbNetwork) throws XMLStreamException {
			final List<? extends Connection> resourceConns = connections.stream()
					.filter(ResourceInSystemFBNetworkExporter::isResCon).toList();
			super.addConnections(resourceConns, connectionElementName, fbNetwork);
		}

		@Override
		protected String getFBNElementName(final FBNetworkElement fbnElement) {
			if (fbnElement.isMapped()) {
				return fbnElement.getOpposite().getQualifiedName();
			}
			return super.getFBNElementName(fbnElement);
		}

		private static boolean isResCon(final Connection con) {
			final FBNetworkElement sourceElement = con.getSourceElement();
			if (sourceElement == null) {
				// connection to the resource interface
				return false;
			}
			return !sourceElement.isMapped() || !con.getDestinationElement().isMapped();
		}
	}

	public SystemExporter(final AutomationSystem system) {
		super(system);
	}

	@Override
	public AutomationSystem getType() {
		return (AutomationSystem) super.getType();
	}

	@Override
	protected void createTypeSpecificXMLEntries() throws XMLStreamException {
		addCompilerInfo(getType().getCompilerInfo());
		addAttributes(getType().getAttributes());
		addApplications();

		final SystemConfiguration systemConfiguration = getType().getSystemConfiguration();
		if (null != systemConfiguration) {
			addDevices(systemConfiguration.getDevices());
			addSegment(systemConfiguration.getSegments());
			addMapping();
			addLink(systemConfiguration.getLinks());
		}
	}

	private void addApplications() throws XMLStreamException {
		for (final Application app : getType().getApplication()) {
			addStartElement(LibraryElementTags.APPLICATION_ELEMENT);
			addNameAndCommentAttribute(app);
			addAttributes(app.getAttributes());
			new FBNetworkExporter(this).createFBNetworkElement(app.getFBNetwork());
			addEndElement();
		}
	}

	private void addLink(final EList<Link> linkList) throws XMLStreamException {
		for (final Link link : linkList) {
			addStartElement(LibraryElementTags.LINK_ELEMENT);
			getWriter().writeAttribute(LibraryElementTags.SEGMENT_NAME_ELEMENT, link.getSegment().getName());
			getWriter().writeAttribute(LibraryElementTags.SEGMENT_COMM_RESOURCE, link.getDevice().getName());
			getWriter().writeAttribute(LibraryElementTags.COMMENT_ATTRIBUTE, link.getComment());
			addAttributes(link.getAttributes());
			addEndElement();
		}
	}

	private void addSegment(final EList<Segment> segementList) throws XMLStreamException {
		for (final Segment segment : segementList) {
			addStartElement(LibraryElementTags.SEGMENT_ELEMENT);
			addNameTypeCommentAttribute(segment, segment.getType());
			addXYAttributes(segment);
			getWriter().writeAttribute(LibraryElementTags.DX1_ATTRIBUTE, positionFormater.format(segment.getWidth()));
			addColorAttributeElement(segment);
			addAttributes(segment.getAttributes());
			addParamsConfig(segment.getCommunication().getParameters());
			addEndElement();
		}
	}

	private void addMapping() throws XMLStreamException {
		for (final Mapping mappingEntry : getType().getMapping()) {
			final var resource = mappingEntry.getTo().getResource();
			if (resource != null) {
				addEmptyStartElement(LibraryElementTags.MAPPING_ELEMENT);
				getWriter().writeAttribute(LibraryElementTags.MAPPING_FROM_ATTRIBUTE,
						mappingEntry.getFrom().getQualifiedName());
				getWriter().writeAttribute(LibraryElementTags.MAPPING_TO_ATTRIBUTE, resource.getQualifiedName());
			}
		}
	}

	private void addDevices(final EList<Device> deviceList) throws XMLStreamException {
		for (final Device device : deviceList) {
			addStartElement(LibraryElementTags.DEVICE_ELEMENT);
			addNameTypeCommentAttribute(device, device.getType());
			addXYAttributes(device);
			addParamsConfig(device.getVarDeclarations());
			addDeviceAttributes(device);
			addResources(device.getResource());
			addEndElement();
		}
	}

	private void addDeviceAttributes(final Device device) throws XMLStreamException {
		addDeviceProfile(device);
		addColorAttributeElement(device);
		addAttributes(device.getAttributes());
	}

	private void addDeviceProfile(final Device device) throws XMLStreamException {
		final String profileName = device.getProfile();
		if ((null != profileName) && !"".equals(profileName)) { //$NON-NLS-1$
			addAttributeElement(LibraryElementTags.DEVICE_PROFILE, IecTypes.ElementaryTypes.STRING, profileName, null);
		}
	}

	/**
	 * Adds the resource.
	 *
	 * @param resourceList the list of resource to add to the MXL file
	 */
	private void addResources(final EList<Resource> resourceList) throws XMLStreamException {
		for (final Resource resource : resourceList) {
			if (!resource.isDeviceTypeResource()) {
				addStartElement(LibraryElementTags.RESOURCE_ELEMENT);
				addNameTypeCommentAttribute(resource, resource.getType());
				addXYAttributes(0, 0);
				addParamsConfig(resource.getVarDeclarations());
				final var resNetworkExporter = new ResourceInSystemFBNetworkExporter(this);
				resNetworkExporter.createFBNetworkElement(resource.getFBNetwork());
				addAttributes(resource.getAttributes());
				addEndElement();
			}
		}
	}

	@Override
	protected String getRootTag() {
		return LibraryElementTags.SYSTEM;
	}

}
