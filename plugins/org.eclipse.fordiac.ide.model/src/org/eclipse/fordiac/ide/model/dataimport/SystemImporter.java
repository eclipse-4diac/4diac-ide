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

import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.stream.XMLStreamException;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.CoordinateConverter;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.Palette.DeviceTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.Palette.ResourceTypeEntry;
import org.eclipse.fordiac.ide.model.dataimport.exceptions.TypeImportException;
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

public class SystemImporter extends CommonElementImporter {
	private final AutomationSystem system;
	private final TypeLibrary typeLib;

	public TypeLibrary getTypeLib() {
		return typeLib;
	}

	private Palette getPalette() {
		return getTypeLib().getBlockTypeLib();
	}

	/**
	 * create a new system file importer for a system input stream and an automation
	 * system to populate
	 *
	 * @param system AutomationSystem with an initialized Palette
	 */
	public SystemImporter(AutomationSystem system) {
		super();
		this.system = system;
		typeLib = system.getPalette().getTypeLibrary();
	}

	/**
	 * This method populates the AutomationSystem with all elements from the system
	 * file.
	 *
	 * @param systemStream input stream providing an IEC 61499 compliant system XML
	 * @throws TypeImportException
	 *
	 */
	public void importSystem(final InputStream systemStream) throws TypeImportException {
		try (ImporterStreams streams = createInputStreams(systemStream)) {
			proceedToStartElementNamed(LibraryElementTags.SYSTEM);
			readNameCommentAttributes(system);
			parseSystemContent();
		} catch (TypeImportException e) {
			throw e;
		} catch (Exception e) {
			throw new TypeImportException(e.getMessage(), e);
		}

	}

	private void parseSystemContent() throws XMLStreamException, TypeImportException {
		SystemConfiguration sysConf = system.getSystemConfiguration();

		processChildren(LibraryElementTags.SYSTEM, name -> {
			switch (name) {
			case LibraryElementTags.VERSION_INFO_ELEMENT:
				parseVersionInfo(system);
				break;
			case LibraryElementTags.IDENTIFICATION_ELEMENT:
				parseIdentification(system);
				break;
			case LibraryElementTags.APPLICATION_ELEMENT:
				system.getApplication().add(parseApplication());
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
		});
	}

	private Segment parseSegment() throws TypeImportException, XMLStreamException {
		Segment segment = LibraryElementFactory.eINSTANCE.createSegment();

		readNameCommentAttributes(segment);

		getXandY(segment);
		String dx1 = getAttributeValue(LibraryElementTags.DX1_ATTRIBUTE);
		if (null != dx1) {
			segment.setWidth(CoordinateConverter.INSTANCE.convertFrom1499XML(dx1));
		}

		String type = getAttributeValue(LibraryElementTags.TYPE_ATTRIBUTE);
		if (null != type) {
			segment.setPaletteEntry(getPalette().getSegmentTypeEntry(type));
		}

		parseSegmentNodeChildren(segment);
		return segment;
	}

	private void parseSegmentNodeChildren(Segment segment) throws XMLStreamException, TypeImportException {
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

	private void parseLink(SystemConfiguration sysConf) throws XMLStreamException {
		String commResource = getAttributeValue(LibraryElementTags.SEGMENT_COMM_RESOURCE);
		String comment = getAttributeValue(LibraryElementTags.COMMENT_ATTRIBUTE);
		String segmentName = getAttributeValue(LibraryElementTags.SEGMENT_NAME_ELEMENT);

		Segment segment = sysConf.getSegmentNamed(segmentName);
		Device device = sysConf.getDeviceNamed(commResource);

		if (null != segment && null != device) {
			Link link = LibraryElementFactory.eINSTANCE.createLink();
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
		Device device = LibraryElementFactory.eINSTANCE.createDevice();
		parseCommon(device);
		parseDeviceType(device);
		parseDeviceNodeChildren(device);
		return device;
	}

	private void parseCommon(IVarElement element) throws TypeImportException {
		readNameCommentAttributes(((INamedElement) element));

		if (element instanceof PositionableElement) {
			getXandY(((PositionableElement) element));
		}
	}

	private void parseDeviceType(Device device) {
		String typeName = getAttributeValue(LibraryElementTags.TYPE_ATTRIBUTE);
		if (typeName != null) {
			DeviceTypePaletteEntry entry = getPalette().getDeviceTypeEntry(typeName);
			if (null != entry) {
				((TypedConfigureableObject) device).setPaletteEntry(entry);
				createParamters(device);
			}
		}
	}

	private void parseMapping() throws XMLStreamException {
		String fromValue = getAttributeValue(LibraryElementTags.MAPPING_FROM_ATTRIBUTE);
		String toValue = getAttributeValue(LibraryElementTags.MAPPING_TO_ATTRIBUTE);
		FBNetworkElement fromElement = findMappingTargetFromName(fromValue);
		FBNetworkElement toElement = findMappingTargetFromName(toValue);
		if (null != fromElement && null != toElement) {
			system.getMapping().add(createMappingEntry(toElement, fromElement));
		}
		// TODO perform some notificatin to the user that the mapping has an issue
		proceedToEndElementNamed(LibraryElementTags.MAPPING_ELEMENT);
	}

	private static Mapping createMappingEntry(FBNetworkElement toElement, FBNetworkElement fromElement) {
		Mapping mapping = LibraryElementFactory.eINSTANCE.createMapping();
		mapping.setFrom(fromElement);
		mapping.setTo(toElement);
		toElement.setMapping(mapping);
		fromElement.setMapping(mapping);
		return mapping;
	}

	private FBNetworkElement findMappingTargetFromName(String targetName) {
		FBNetworkElement element = null;
		if (null != targetName) {
			Deque<String> parts = new ArrayDeque<>(Arrays.asList(targetName.split("\\."))); ////$NON-NLS-1$
			if (parts.size() >= 2) {
				FBNetwork nw = null;
				// first find out if the mapping points to a device/resoruce or application and
				// get the approprate starting fbnetwork
				Device dev = system.getDeviceNamed(parts.getFirst());
				Application application = system.getApplicationNamed(parts.getFirst());
				if (null != dev) {
					parts.pollFirst();
					Resource res = dev.getResourceNamed(parts.pollFirst());
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

	private static FBNetworkElement findMappingTargetInFBNetwork(FBNetwork nw, Deque<String> parts) {
		if (null != nw) {
			FBNetworkElement element = nw.getElementNamed(parts.pollFirst());
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

	private void parseDeviceNodeChildren(Device device) throws TypeImportException, XMLStreamException {

		processChildren(LibraryElementTags.DEVICE_ELEMENT, name -> {
			switch (name) {
			case LibraryElementTags.ATTRIBUTE_ELEMENT:
				parseDeviceAttribute(device);
				break;
			case LibraryElementTags.PARAMETER_ELEMENT:
				VarDeclaration parameter = parseParameter();
				if (null != parameter) {
					VarDeclaration devParam = getParamter(device.getVarDeclarations(), parameter.getName());
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

	private void parseDeviceAttribute(Device device) throws XMLStreamException {
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
		String name = getAttributeValue(LibraryElementTags.NAME_ATTRIBUTE);
		return (null != name) && LibraryElementTags.COLOR.equals(name);
	}

	private void parseColor(ColorizableElement colElement) {
		Color color = LibraryElementFactory.eINSTANCE.createColor();
		String value = getAttributeValue(LibraryElementTags.VALUE_ATTRIBUTE);
		if (value != null) {
			String[] colors = value.split(","); //$NON-NLS-1$
			color.setRed(Integer.valueOf(colors[0]));
			color.setGreen(Integer.valueOf(colors[1]));
			color.setBlue(Integer.valueOf(colors[2]));
			colElement.setColor(color);
		}
	}

	/**
	 * Creates the values.
	 */
	public static void createParamters(IVarElement element) {
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
		for (VarDeclaration varDecl : element.getVarDeclarations()) {
			Value value = LibraryElementFactory.eINSTANCE.createValue();
			varDecl.setValue(value);
			VarDeclaration typeVar = getTypeVariable(varDecl);
			if (null != typeVar && null != typeVar.getValue()) {
				value.setValue(typeVar.getValue().getValue());
			}
		}
	}

	private static VarDeclaration getTypeVariable(VarDeclaration var) {
		EList<VarDeclaration> varList = null;
		if (var.eContainer() instanceof Device) {
			Device dev = (Device) var.eContainer();
			if (null != dev.getType()) {
				varList = dev.getType().getVarDeclaration();
			}
		} else if (var.eContainer() instanceof Resource) {
			Resource res = (Resource) var.eContainer();
			if (null != res.getType()) {
				varList = res.getType().getVarDeclaration();
			}
		}

		if (null != varList) {
			for (VarDeclaration typeVar : varList) {
				if (typeVar.getName().equals(var.getName())) {
					return typeVar;
				}
			}
		}
		return null;
	}

	private static VarDeclaration getParamter(EList<VarDeclaration> paramList, String name) {
		for (VarDeclaration varDecl : paramList) {
			if (varDecl.getName().equals(name)) {
				return varDecl;
			}
		}
		return null;
	}

	private Resource parseResource() throws TypeImportException, XMLStreamException {
		// TODO model refactoring - try to merge this code with the dev importer code
		Resource resource = LibraryElementFactory.eINSTANCE.createResource();
		resource.setDeviceTypeResource(false); // TODO model refactoring - check if a resource of given name is already
												// in the list then it would be a device type resource
		parseCommon(resource);
		parseResourceType(resource);
		FBNetwork fbNetwork = LibraryElementFactory.eINSTANCE.createFBNetwork();
		createResourceTypeNetwork(((ResourceTypeEntry) resource.getPaletteEntry()).getResourceType(), fbNetwork);

		processChildren(LibraryElementTags.RESOURCE_ELEMENT, name -> {
			switch (name) {
			case LibraryElementTags.FBNETWORK_ELEMENT:
				resource.setFBNetwork(
						new ResDevFBNetworkImporter(getTypeLib(), fbNetwork, resource.getVarDeclarations(), getReader())
								.parseFBNetwork(LibraryElementTags.FBNETWORK_ELEMENT));
				break;
			case LibraryElementTags.ATTRIBUTE_ELEMENT:
				parseGenericAttributeNode(resource);
				proceedToEndElementNamed(LibraryElementTags.ATTRIBUTE_ELEMENT);
				break;
			case LibraryElementTags.PARAMETER_ELEMENT:
				VarDeclaration parameter = parseParameter();
				if (null != parameter) {
					VarDeclaration devParam = getParamter(resource.getVarDeclarations(), parameter.getName());
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

	private void parseResourceType(Resource resource) {
		String typeName = getAttributeValue(LibraryElementTags.TYPE_ATTRIBUTE);
		if (typeName != null) {
			ResourceTypeEntry entry = getPalette().getResourceTypeEntry(typeName);
			if (null != entry) {
				resource.setPaletteEntry(entry);
				createParamters(resource);
			}
		}
	}

	private Application parseApplication() throws TypeImportException, XMLStreamException {
		Application application = LibraryElementFactory.eINSTANCE.createApplication();
		readNameCommentAttributes(application);

		processChildren(LibraryElementTags.APPLICATION_ELEMENT, name -> {
			switch (name) {
			case LibraryElementTags.ATTRIBUTE_ELEMENT:
				parseGenericAttributeNode(application);
				proceedToEndElementNamed(LibraryElementTags.ATTRIBUTE_ELEMENT);
				break;
			case LibraryElementTags.SUBAPPNETWORK_ELEMENT:
				application.setFBNetwork(new SubAppNetworkImporter(getTypeLib(), getReader())
						.parseFBNetwork(LibraryElementTags.SUBAPPNETWORK_ELEMENT));
				break;
			default:
				return false;
			}
			return true;
		});

		return application;
	}

	public static void createResourceTypeNetwork(final ResourceType type, final FBNetwork resourceFBNetwork) {
		Map<String, Event> events = new HashMap<>();
		Map<String, VarDeclaration> varDecls = new HashMap<>();

		for (FBNetworkElement element : type.getFBNetwork().getNetworkElements()) {
			FB copy = LibraryElementFactory.eINSTANCE.createResourceTypeFB();

			resourceFBNetwork.getNetworkElements().add(copy);
			copy.setPaletteEntry(element.getPaletteEntry());
			copy.setName(element.getName()); // name should be last so that checks
			// are working correctly

			InterfaceList interfaceList = LibraryElementFactory.eINSTANCE.createInterfaceList();

			for (Iterator<VarDeclaration> iterator2 = element.getInterface().getOutputVars().iterator(); iterator2
					.hasNext();) {
				VarDeclaration varDecl = iterator2.next();
				VarDeclaration varDeclCopy = LibraryElementFactory.eINSTANCE.createVarDeclaration();
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
			for (Iterator<VarDeclaration> iterator2 = element.getInterface().getInputVars().iterator(); iterator2
					.hasNext();) {
				VarDeclaration varDecl = iterator2.next();
				VarDeclaration varDeclCopy = LibraryElementFactory.eINSTANCE.createVarDeclaration();
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
			for (Iterator<Event> iterator2 = element.getInterface().getEventInputs().iterator(); iterator2.hasNext();) {
				Event event = iterator2.next();
				Event eventCopy = LibraryElementFactory.eINSTANCE.createEvent();
				eventCopy.setType(event.getType());
				eventCopy.setName(event.getName());
				eventCopy.setComment(event.getComment());
				eventCopy.setIsInput(event.isIsInput());
				events.put(element.getName() + "." + event.getName(), eventCopy); //$NON-NLS-1$
				interfaceList.getEventInputs().add(eventCopy);
			}
			for (Iterator<Event> iterator2 = element.getInterface().getEventOutputs().iterator(); iterator2
					.hasNext();) {
				Event event = iterator2.next();
				Event eventCopy = LibraryElementFactory.eINSTANCE.createEvent();
				eventCopy.setType(event.getType());
				eventCopy.setName(event.getName());
				eventCopy.setComment(event.getComment());
				eventCopy.setIsInput(event.isIsInput());
				events.put(element.getName() + "." + event.getName(), eventCopy); //$NON-NLS-1$
				interfaceList.getEventOutputs().add(eventCopy);
			}
			copy.setInterface(interfaceList);
			copy.setX(element.getX());
			copy.setY(element.getY());
		}

		for (Iterator<EventConnection> iterator2 = type.getFBNetwork().getEventConnections().iterator(); iterator2
				.hasNext();) {
			EventConnection eventCon = iterator2.next();
			if (eventCon.getSource() != null && eventCon.getDestination() != null) {
				FB sourceFB = (FB) eventCon.getSource().eContainer().eContainer();
				FB destFB = (FB) eventCon.getDestination().eContainer().eContainer();

				Event source = events.get(sourceFB.getName() + "." //$NON-NLS-1$
						+ eventCon.getSource().getName());
				Event dest = events.get(destFB.getName() + "." //$NON-NLS-1$
						+ eventCon.getDestination().getName());

				EventConnection copyEventCon = LibraryElementFactory.eINSTANCE.createEventConnection();
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
		for (Iterator<DataConnection> iterator2 = type.getFBNetwork().getDataConnections().iterator(); iterator2
				.hasNext();) {
			DataConnection dataCon = iterator2.next();
			if (dataCon.getSource() != null && dataCon.getDestination() != null) {
				FB sourceFB = (FB) dataCon.getSource().eContainer().eContainer();
				FB destFB = (FB) dataCon.getDestination().eContainer().eContainer();

				VarDeclaration source = varDecls.get(sourceFB.getName() + "." //$NON-NLS-1$
						+ dataCon.getSource().getName());
				VarDeclaration dest = varDecls.get(destFB.getName() + "." //$NON-NLS-1$
						+ dataCon.getDestination().getName());

				DataConnection copyDataCon = LibraryElementFactory.eINSTANCE.createDataConnection();
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
