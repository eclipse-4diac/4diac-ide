/*******************************************************************************
 * Copyright (c) 2016 - 2017 fortiss GmbH
 * 				 2018 - 2020 Johannes Kepler University, Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Waldemar Eisenmenger, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - fixed coordinate system resolution conversion in in- and export
 *   			 - Changed XML parsing to Staxx cursor interface for improved
 *  			   parsing performance
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.dataimport;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

import javax.xml.stream.XMLStreamException;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.CoordinateConverter;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.Palette.DeviceTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.ResourceTypeEntry;
import org.eclipse.fordiac.ide.model.dataimport.exceptions.TypeImportException;
import org.eclipse.fordiac.ide.model.helpers.FBNetworkHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Color;
import org.eclipse.fordiac.ide.model.libraryElement.ColorizableElement;
import org.eclipse.fordiac.ide.model.libraryElement.DataConnection;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.EventConnection;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.IVarElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Link;
import org.eclipse.fordiac.ide.model.libraryElement.Mapping;
import org.eclipse.fordiac.ide.model.libraryElement.PositionableElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.ResourceType;
import org.eclipse.fordiac.ide.model.libraryElement.Segment;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.eclipse.fordiac.ide.model.libraryElement.TypedConfigureableObject;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.gef.commands.CommandStack;

public class SystemImporter extends CommonElementImporter {

	public SystemImporter(final IFile systemfile) {
		super(systemfile);
	}

	@Override
	public AutomationSystem getElement() {
		return (AutomationSystem) super.getElement();
	}

	@Override
	protected LibraryElement createRootModelElement() {
		return createAutomationSystem(getFile());
	}

	/** Create an empty automation system model for a given file.
	 *
	 * this can either be used for the importer or for creating a new system
	 *
	 * @param systemFile the file where the system should be stored
	 * @return the automation system model with its basic setup */
	public static AutomationSystem createAutomationSystem(final IFile systemFile) {
		final AutomationSystem system = LibraryElementFactory.eINSTANCE.createAutomationSystem();
		system.setName(TypeLibrary.getTypeNameFromFile(systemFile));
		system.setSystemFile(systemFile);

		system.setCommandStack(new CommandStack());

		// create PhysicalConfiguration
		final SystemConfiguration sysConf = LibraryElementFactory.eINSTANCE.createSystemConfiguration();
		system.setSystemConfiguration(sysConf);

		system.setPalette(TypeLibrary.getTypeLibrary(systemFile.getProject()).getBlockTypeLib());
		return system;
	}

	@Override
	protected String getStartElementName() {
		return LibraryElementTags.SYSTEM;
	}

	@Override
	protected IChildHandler getBaseChildrenHandler() {
		final SystemConfiguration sysConf = getElement().getSystemConfiguration();
		return name -> {
			switch (name) {
			case LibraryElementTags.VERSION_INFO_ELEMENT:
				parseVersionInfo(getElement());
				break;
			case LibraryElementTags.IDENTIFICATION_ELEMENT:
				parseIdentification(getElement());
				break;
			case LibraryElementTags.APPLICATION_ELEMENT:
				getElement().getApplication().add(parseApplication());
				break;
			case LibraryElementTags.DEVICE_ELEMENT:
				sysConf.getDevices().add(parseDevice());
				break;
			case LibraryElementTags.MAPPING_ELEMENT:
				parseMapping();
				break;
			case LibraryElementTags.SEGMENT_ELEMENT:
				sysConf.getSegments().add(parseSegment());
				break;
			case LibraryElementTags.LINK_ELEMENT:
				parseLink(sysConf);
				break;
			default:
				return false;
			}
			return true;
		};
	}

	private Segment parseSegment() throws TypeImportException, XMLStreamException {
		final Segment segment = LibraryElementFactory.eINSTANCE.createSegment();

		readNameCommentAttributes(segment);

		getXandY(segment);
		final String dx1 = getAttributeValue(LibraryElementTags.DX1_ATTRIBUTE);
		if (null != dx1) {
			segment.setWidth(CoordinateConverter.INSTANCE.convertFrom1499XML(dx1));
		}

		final String type = getAttributeValue(LibraryElementTags.TYPE_ATTRIBUTE);
		if (null != type) {
			segment.setPaletteEntry(getPalette().getSegmentTypeEntry(type));
		}

		parseSegmentNodeChildren(segment);
		return segment;
	}

	private void parseSegmentNodeChildren(final Segment segment) throws XMLStreamException, TypeImportException {
		processChildren(LibraryElementTags.SEGMENT_ELEMENT, name -> {
			if (LibraryElementTags.ATTRIBUTE_ELEMENT.equals(name)) {
				if (isColorAttributeNode()) {
					parseColor(segment);
				} else {
					parseGenericAttributeNode(segment);
				}
				proceedToEndElementNamed(LibraryElementTags.ATTRIBUTE_ELEMENT);
				return true;
			}
			return false;
		});
	}

	private void parseLink(final SystemConfiguration sysConf) throws XMLStreamException {
		final String commResource = getAttributeValue(LibraryElementTags.SEGMENT_COMM_RESOURCE);
		final String comment = getAttributeValue(LibraryElementTags.COMMENT_ATTRIBUTE);
		final String segmentName = getAttributeValue(LibraryElementTags.SEGMENT_NAME_ELEMENT);

		final Segment segment = sysConf.getSegmentNamed(segmentName);
		final Device device = sysConf.getDeviceNamed(commResource);

		if (null != segment && null != device) {
			final Link link = LibraryElementFactory.eINSTANCE.createLink();
			link.setComment(comment);
			segment.getOutConnections().add(link);
			device.getInConnections().add(link);
			sysConf.getLinks().add(link);
		}
		// TODO implement some mechnism for the case that we can not find the device or
		// the segement
		proceedToEndElementNamed(LibraryElementTags.LINK_ELEMENT);
	}

	private Device parseDevice() throws TypeImportException, XMLStreamException {
		final Device device = LibraryElementFactory.eINSTANCE.createDevice();
		parseCommon(device);
		parseDeviceType(device);
		parseDeviceNodeChildren(device);
		return device;
	}

	private void parseCommon(final IVarElement element) throws TypeImportException {
		readNameCommentAttributes(((INamedElement) element));

		if (element instanceof PositionableElement) {
			getXandY(((PositionableElement) element));
		}
	}

	private void parseDeviceType(final Device device) {
		final String typeName = getAttributeValue(LibraryElementTags.TYPE_ATTRIBUTE);
		if (typeName != null) {
			final DeviceTypePaletteEntry entry = getPalette().getDeviceTypeEntry(typeName);
			if (null != entry) {
				device.setPaletteEntry(entry);
				createParamters(device);
			}
		}
	}

	private void parseMapping() throws XMLStreamException {
		final String fromValue = getAttributeValue(LibraryElementTags.MAPPING_FROM_ATTRIBUTE);
		final String toValue = getAttributeValue(LibraryElementTags.MAPPING_TO_ATTRIBUTE);
		final FBNetworkElement fromElement = findMappingTargetFromName(fromValue);
		final FBNetworkElement toElement = findMappingTargetFromName(toValue);

		if (fromElement instanceof SubApp) {
			FBNetworkHelper.loadSubappNetwork(fromElement);
		}

		if (null != fromElement && null != toElement) {
			getElement().getMapping().add(createMappingEntry(toElement, fromElement));
		}

		// TODO perform some notificatin to the user that the mapping has an issue
		proceedToEndElementNamed(LibraryElementTags.MAPPING_ELEMENT);
	}

	private static Mapping createMappingEntry(final FBNetworkElement toElement, final FBNetworkElement fromElement) {
		final Mapping mapping = LibraryElementFactory.eINSTANCE.createMapping();
		mapping.setFrom(fromElement);
		mapping.setTo(toElement);
		toElement.setMapping(mapping);
		fromElement.setMapping(mapping);
		return mapping;
	}

	private FBNetworkElement findMappingTargetFromName(final String targetName) {
		FBNetworkElement element = null;
		if (null != targetName) {
			Deque<String> parts = new ArrayDeque<>(Arrays.asList(targetName.split("\\."))); ////$NON-NLS-1$
			if (parts.size() >= 2) {
				FBNetwork nw = null;
				// first find out if the mapping points to a device/resource or application and
				// get the appropriate starting fbnetwork
				final Device dev = getElement().getDeviceNamed(parts.getFirst());
				final Application application = getElement().getApplicationNamed(parts.getFirst());
				if (null != dev) {
					parts.pollFirst();
					final Resource res = dev.getResourceNamed(parts.pollFirst());
					if (null != res) {
						nw = res.getFBNetwork();
						element = findMappingTargetInFBNetwork(nw, parts);
					}
				}
				if (null == element && null != application) {
					parts = new ArrayDeque<>(Arrays.asList(targetName.split("\\."))); //$NON-NLS-1$
					parts.pollFirst();
					nw = application.getFBNetwork();
					element = findMappingTargetInFBNetwork(nw, parts);
				}
			}
		}
		return element;
	}

	private static FBNetworkElement findMappingTargetInFBNetwork(final FBNetwork nw, final Deque<String> parts) {
		if (null != nw) {
			final FBNetworkElement element = nw.getElementNamed(parts.pollFirst());
			if (null != element) {
				if (parts.isEmpty()) {
					// the list is empty this should be the entity we are looking for
					return element;
				} else if (element instanceof SubApp) {
					// as there are more elements the current should be a subapp
					findMappingTargetInFBNetwork(((SubApp) element).getFbNetwork(), parts);
				}
			}
		}
		return null;
	}

	private void parseDeviceNodeChildren(final Device device) throws TypeImportException, XMLStreamException {

		processChildren(LibraryElementTags.DEVICE_ELEMENT, name -> {
			switch (name) {
			case LibraryElementTags.ATTRIBUTE_ELEMENT:
				parseDeviceAttribute(device);
				break;
			case LibraryElementTags.PARAMETER_ELEMENT:
				final VarDeclaration parameter = parseParameter();
				if (null != parameter) {
					final VarDeclaration devParam = getParamter(device.getVarDeclarations(), parameter.getName());
					if (null != devParam) {
						devParam.setValue(parameter.getValue());
					} else {
						parameter.setIsInput(true);
						device.getVarDeclarations().add(parameter);
					}
				}
				break;
			case LibraryElementTags.RESOURCE_ELEMENT:
				device.getResource().add(parseResource());
				break;
			default:
				return false;
			}
			return true;
		});
	}

	private void parseDeviceAttribute(final Device device) throws XMLStreamException {
		if (isColorAttributeNode()) {
			parseColor(device);
		} else if (isProfileAttribute()) {
			parseProfile(device);
		} else {
			parseGenericAttributeNode(device);
		}
		proceedToEndElementNamed(LibraryElementTags.ATTRIBUTE_ELEMENT);
	}

	private boolean isColorAttributeNode() {
		final String name = getAttributeValue(LibraryElementTags.NAME_ATTRIBUTE);
		return (null != name) && LibraryElementTags.COLOR.equals(name);
	}

	private void parseColor(final ColorizableElement colElement) {
		final Color color = LibraryElementFactory.eINSTANCE.createColor();
		final String value = getAttributeValue(LibraryElementTags.VALUE_ATTRIBUTE);
		if (value != null) {
			final String[] colors = value.split(","); //$NON-NLS-1$
			color.setRed(Integer.parseInt(colors[0]));
			color.setGreen(Integer.parseInt(colors[1]));
			color.setBlue(Integer.parseInt(colors[2]));
			colElement.setColor(color);
		}
	}

	/** Creates the values. */
	public static void createParamters(final IVarElement element) {
		if (element instanceof Device) {
			element.getVarDeclarations().addAll(
					EcoreUtil.copyAll(((DeviceTypePaletteEntry) ((TypedConfigureableObject) element).getPaletteEntry())
							.getDeviceType().getVarDeclaration()));
		}
		if (element instanceof Resource) {
			element.getVarDeclarations().addAll(
					EcoreUtil.copyAll(((ResourceTypeEntry) ((TypedConfigureableObject) element).getPaletteEntry())
							.getResourceType().getVarDeclaration()));
		}
		for (final VarDeclaration varDecl : element.getVarDeclarations()) {
			final Value value = LibraryElementFactory.eINSTANCE.createValue();
			varDecl.setValue(value);
			final VarDeclaration typeVar = getTypeVariable(varDecl);
			if (null != typeVar && null != typeVar.getValue()) {
				value.setValue(typeVar.getValue().getValue());
			}
		}
	}

	private static VarDeclaration getTypeVariable(final VarDeclaration variable) {
		EList<VarDeclaration> varList = null;
		if (variable.eContainer() instanceof Device) {
			final Device dev = (Device) variable.eContainer();
			if (null != dev.getType()) {
				varList = dev.getType().getVarDeclaration();
			}
		} else if (variable.eContainer() instanceof Resource) {
			final Resource res = (Resource) variable.eContainer();
			if (null != res.getType()) {
				varList = res.getType().getVarDeclaration();
			}
		}

		if (null != varList) {
			for (final VarDeclaration typeVar : varList) {
				if (typeVar.getName().equals(variable.getName())) {
					return typeVar;
				}
			}
		}
		return null;
	}

	private static VarDeclaration getParamter(final EList<VarDeclaration> paramList, final String name) {
		for (final VarDeclaration varDecl : paramList) {
			if (varDecl.getName().equals(name)) {
				return varDecl;
			}
		}
		return null;
	}

	private Resource parseResource() throws TypeImportException, XMLStreamException {
		// TODO model refactoring - try to merge this code with the dev importer code
		final Resource resource = LibraryElementFactory.eINSTANCE.createResource();
		resource.setDeviceTypeResource(false); // TODO model refactoring - check if a resource of given name is already
		// in the list then it would be a device type resource
		parseCommon(resource);
		parseResourceType(resource);
		final FBNetwork fbNetwork = LibraryElementFactory.eINSTANCE.createFBNetwork();
		resource.setFBNetwork(fbNetwork);
		createResourceTypeNetwork(((ResourceTypeEntry) resource.getPaletteEntry()).getResourceType(), fbNetwork);

		processChildren(LibraryElementTags.RESOURCE_ELEMENT, name -> {
			switch (name) {
			case LibraryElementTags.FBNETWORK_ELEMENT:
				new ResDevFBNetworkImporter(this, fbNetwork, resource.getVarDeclarations())
				.parseFBNetwork(LibraryElementTags.FBNETWORK_ELEMENT);
				break;
			case LibraryElementTags.ATTRIBUTE_ELEMENT:
				parseGenericAttributeNode(resource);
				proceedToEndElementNamed(LibraryElementTags.ATTRIBUTE_ELEMENT);
				break;
			case LibraryElementTags.PARAMETER_ELEMENT:
				final VarDeclaration parameter = parseParameter();
				if (null != parameter) {
					final VarDeclaration devParam = getParamter(resource.getVarDeclarations(), parameter.getName());
					if (null != devParam) {
						devParam.setValue(parameter.getValue());
					} else {
						parameter.setIsInput(true);
						resource.getVarDeclarations().add(parameter);
					}
				}
				break;
			default:
				return false;
			}
			return true;
		});
		return resource;
	}

	private void parseResourceType(final Resource resource) {
		final String typeName = getAttributeValue(LibraryElementTags.TYPE_ATTRIBUTE);
		if (typeName != null) {
			final ResourceTypeEntry entry = getPalette().getResourceTypeEntry(typeName);
			if (null != entry) {
				resource.setPaletteEntry(entry);
				createParamters(resource);
			}
		}
	}

	private Application parseApplication() throws TypeImportException, XMLStreamException {
		final Application application = LibraryElementFactory.eINSTANCE.createApplication();
		readNameCommentAttributes(application);

		processChildren(LibraryElementTags.APPLICATION_ELEMENT, name -> {
			switch (name) {
			case LibraryElementTags.ATTRIBUTE_ELEMENT:
				parseGenericAttributeNode(application);
				proceedToEndElementNamed(LibraryElementTags.ATTRIBUTE_ELEMENT);
				break;
			case LibraryElementTags.SUBAPPNETWORK_ELEMENT:
				final SubAppNetworkImporter supAppImporter = new SubAppNetworkImporter(this);
				application.setFBNetwork(supAppImporter.getFbNetwork());
				supAppImporter.parseFBNetwork(LibraryElementTags.SUBAPPNETWORK_ELEMENT);
				break;
			default:
				return false;
			}
			return true;
		});

		return application;
	}

	public static void createResourceTypeNetwork(final ResourceType type, final FBNetwork resourceFBNetwork) {
		final Map<String, Event> events = new HashMap<>();
		final Map<String, VarDeclaration> varDecls = new HashMap<>();

		for (final FBNetworkElement element : type.getFBNetwork().getNetworkElements()) {
			final FB copy = LibraryElementFactory.eINSTANCE.createResourceTypeFB();

			resourceFBNetwork.getNetworkElements().add(copy);
			copy.setPaletteEntry(element.getPaletteEntry());
			copy.setName(element.getName()); // name should be last so that checks
			// are working correctly

			final InterfaceList interfaceList = LibraryElementFactory.eINSTANCE.createInterfaceList();

			for (final VarDeclaration varDecl : element.getInterface().getOutputVars()) {
				final VarDeclaration varDeclCopy = LibraryElementFactory.eINSTANCE.createVarDeclaration();
				varDeclCopy.setType(varDecl.getType());
				varDeclCopy.setName(varDecl.getName());
				varDeclCopy.setComment(varDecl.getComment());
				varDeclCopy.setIsInput(varDecl.isIsInput());
				if (varDecl.getValue() != null) {
					varDeclCopy.setValue(EcoreUtil.copy(varDecl.getValue()));
				}
				varDecls.put(element.getName() + "." + varDeclCopy.getName(), //$NON-NLS-1$
						varDeclCopy);
				interfaceList.getOutputVars().add(varDeclCopy);
			}
			for (final VarDeclaration varDecl : element.getInterface().getInputVars()) {
				final VarDeclaration varDeclCopy = LibraryElementFactory.eINSTANCE.createVarDeclaration();
				varDeclCopy.setType(varDecl.getType());
				varDeclCopy.setName(varDecl.getName());
				varDeclCopy.setComment(varDecl.getComment());
				varDeclCopy.setIsInput(varDecl.isIsInput());
				if (varDecl.getValue() != null) {
					varDeclCopy.setValue(EcoreUtil.copy(varDecl.getValue()));
				}
				varDecls.put(element.getName() + "." + varDeclCopy.getName(), //$NON-NLS-1$
						varDeclCopy);
				interfaceList.getInputVars().add(varDeclCopy);
			}
			for (final Event event : element.getInterface().getEventInputs()) {
				final Event eventCopy = LibraryElementFactory.eINSTANCE.createEvent();
				eventCopy.setType(event.getType());
				eventCopy.setName(event.getName());
				eventCopy.setComment(event.getComment());
				eventCopy.setIsInput(event.isIsInput());
				events.put(element.getName() + "." + event.getName(), eventCopy); //$NON-NLS-1$
				interfaceList.getEventInputs().add(eventCopy);
			}
			for (final Event event : element.getInterface().getEventOutputs()) {
				final Event eventCopy = LibraryElementFactory.eINSTANCE.createEvent();
				eventCopy.setType(event.getType());
				eventCopy.setName(event.getName());
				eventCopy.setComment(event.getComment());
				eventCopy.setIsInput(event.isIsInput());
				events.put(element.getName() + "." + event.getName(), eventCopy); //$NON-NLS-1$
				interfaceList.getEventOutputs().add(eventCopy);
			}
			copy.setInterface(interfaceList);
			copy.setPosition(EcoreUtil.copy(element.getPosition()));
		}

		for (final EventConnection eventCon : type.getFBNetwork().getEventConnections()) {
			if (eventCon.getSource() != null && eventCon.getDestination() != null) {
				final FB sourceFB = (FB) eventCon.getSource().eContainer().eContainer();
				final FB destFB = (FB) eventCon.getDestination().eContainer().eContainer();

				final Event source = events.get(sourceFB.getName() + "." //$NON-NLS-1$
						+ eventCon.getSource().getName());
				final Event dest = events.get(destFB.getName() + "." //$NON-NLS-1$
						+ eventCon.getDestination().getName());

				final EventConnection copyEventCon = LibraryElementFactory.eINSTANCE.createEventConnection();
				copyEventCon.setSource(source);
				copyEventCon.setDestination(dest);
				copyEventCon.setResTypeConnection(true);
				resourceFBNetwork.getEventConnections().add(copyEventCon); // TODO
				// check
				// this
			} else {
				// TODO error log -> no valid event connection!
			}
		}
		for (final DataConnection dataCon : type.getFBNetwork().getDataConnections()) {
			if (dataCon.getSource() != null && dataCon.getDestination() != null) {
				final FB sourceFB = (FB) dataCon.getSource().eContainer().eContainer();
				final FB destFB = (FB) dataCon.getDestination().eContainer().eContainer();

				final VarDeclaration source = varDecls.get(sourceFB.getName() + "." //$NON-NLS-1$
						+ dataCon.getSource().getName());
				final VarDeclaration dest = varDecls.get(destFB.getName() + "." //$NON-NLS-1$
						+ dataCon.getDestination().getName());

				final DataConnection copyDataCon = LibraryElementFactory.eINSTANCE.createDataConnection();
				copyDataCon.setSource(source);
				copyDataCon.setDestination(dest);
				copyDataCon.setResTypeConnection(true);
				resourceFBNetwork.getDataConnections().add(copyDataCon);
			} else {
				// TODO error log -> no valid data connection
			}
		}
	}

}
