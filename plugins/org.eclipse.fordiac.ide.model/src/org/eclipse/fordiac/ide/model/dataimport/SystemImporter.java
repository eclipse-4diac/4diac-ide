/*******************************************************************************
 * Copyright (c) 2016 - 2017 fortiss GmbH, 2018 Johannes Kepler University
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Waldemar Eisenmenger, Alois Zoitl, Monika Wenger 
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.dataimport;

import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.Activator;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.Palette.DeviceTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.ResourceTypeEntry;
import org.eclipse.fordiac.ide.model.Palette.SegmentTypePaletteEntry;
import org.eclipse.fordiac.ide.model.dataimport.exceptions.TypeImportException;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Color;
import org.eclipse.fordiac.ide.model.libraryElement.ColorizableElement;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SystemImporter {
	Palette palette = null;
	AutomationSystem system;

	/**
	 * This method populates the AutomationSystem with all elements from the system file.
	 *  
	 * @param systemFile
	 * 				IEC 61499 complient system file
	 * @param system
	 * 				AutomationSystem with an initialized Palette
	 */
	public void importSystem(final InputStream systemStream, AutomationSystem system) {
		this.palette = system.getPalette();
		this.system = system;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(false);
		DocumentBuilder db;

		try {
			dbf.setAttribute("http://apache.org/xml/features/nonvalidating/load-external-dtd", Boolean.FALSE); //$NON-NLS-1$
			db = dbf.newDocumentBuilder();
			Document document = db.parse(systemStream);
			Element rootNode = document.getDocumentElement();

			parseAutomationSystem(rootNode);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void parseAutomationSystem(final Element rootNode)
			throws TypeImportException, ParseException {
		if (rootNode.getNodeName().equals(LibraryElementTags.SYSTEM)) {
			NamedNodeMap map = rootNode.getAttributes();
			CommonElementImporter.readNameCommentAttributes(system, map);
			SystemConfiguration sysConf = system.getSystemConfiguration();
			NodeList childNodes = rootNode.getChildNodes();
			for (int i = 0; i < childNodes.getLength(); i++) {
				Node n = childNodes.item(i);
				if (n.getNodeName().equals(LibraryElementTags.VERSION_INFO_ELEMENT)) {
					CommonElementImporter.parseVersionInfo(system, n);
				}
				if (n.getNodeName().equals(LibraryElementTags.APPLICATION_ELEMENT)) {
					system.getApplication().add(parseApplication(n));
				}
				if (n.getNodeName().equals(LibraryElementTags.DEVICE_ELEMENT)) {
					sysConf.getDevices().add(parseDevice(n));
				}
				if (n.getNodeName().equals(LibraryElementTags.MAPPING_ELEMENT)) {
					parseMapping(n);	
				}
				if (n.getNodeName().equals(LibraryElementTags.SEGMENT_ELEMENT)) {
					sysConf.getSegments().add(parseSegment(n));
				}
				if (n.getNodeName().equals(LibraryElementTags.LINK_ELEMENT)) {
					parseLink(n, sysConf);
				}
			}
		}
	}

	private Segment parseSegment(Node n) throws TypeImportException {
		Segment segment = LibraryElementFactory.eINSTANCE.createSegment();
		
		NamedNodeMap mapSegment = n.getAttributes();
		CommonElementImporter.readNameCommentAttributes(segment, mapSegment);

		Node dx1 = mapSegment.getNamedItem(LibraryElementTags.DX1_ATTRIBUTE);
		if(dx1 != null){
			String dx1String = dx1.getNodeValue();
			double dx1double = 0.;
			if ((null != dx1String) && dx1String.length() != 0) {
				dx1double = ImportUtils.convertCoordinate(Double.parseDouble(dx1.getNodeValue()));
			}
			segment.setWidth(((int) dx1double));
		}
		CommonElementImporter.getXandY(mapSegment, segment);
		Node type = mapSegment.getNamedItem(LibraryElementTags.TYPE_ATTRIBUTE);
		if(type != null){
			PaletteEntry entry = palette.getTypeEntry(type.getNodeValue());
			if (entry instanceof SegmentTypePaletteEntry) {
				segment.setPaletteEntry(entry);
			}			
		}
		
		parseSegmentNodeChildren(segment, n.getChildNodes());
		
		return segment;
	}

	private void parseSegmentNodeChildren(Segment segment, NodeList childNodes) {
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node node = childNodes.item(i);
			if (node.getNodeName().equals(LibraryElementTags.ATTRIBUTE_ELEMENT)) {
				NamedNodeMap attributeMap = node.getAttributes();
				if(isColorAttributeNode(attributeMap)){
					parseColor(segment, attributeMap);
				}else{
					CommonElementImporter.parseGenericAttributeNode(segment, attributeMap);
				}
			}
		}
	}


	private void parseLink(Node linkNode, SystemConfiguration sysConf) {
		NamedNodeMap attributeMap = linkNode.getAttributes();
		String commResource = CommonElementImporter.getAttributeValue(attributeMap, LibraryElementTags.SEGMENT_COMM_RESOURCE);
		String comment = CommonElementImporter.getAttributeValue(attributeMap, LibraryElementTags.COMMENT_ATTRIBUTE);
		String segmentName = CommonElementImporter.getAttributeValue(attributeMap, LibraryElementTags.SEGMENT_NAME_ELEMENT);

		Segment segment = sysConf.getSegmentNamed(segmentName);
		Device device = sysConf.getDeviceNamed(commResource);
		
		if(null != segment && null != device){
			Link link = LibraryElementFactory.eINSTANCE.createLink();
			link.setComment(comment);
			segment.getOutConnections().add(link);
			device.getInConnections().add(link);
			sysConf.getLinks().add(link);
		}
		//TODO implement some mechnism for the case that we can not find the device or the segement
	}

	private Device parseDevice(Node node) throws TypeImportException {
		Device device = LibraryElementFactory.eINSTANCE.createDevice();	
		parseCommon(node, device);
		parseDeviceNodeChildren(device, node.getChildNodes());
		return device;
	}

	private void parseCommon(Node node, IVarElement element) throws TypeImportException{
		NamedNodeMap mapElement = node.getAttributes();
		CommonElementImporter.readNameCommentAttributes(((INamedElement)element), mapElement);
		Node type = mapElement.getNamedItem(LibraryElementTags.TYPE_ATTRIBUTE);
		if (type != null) {
			PaletteEntry entry = palette.getTypeEntry(type.getNodeValue());
			if (null != entry) {
				((TypedConfigureableObject)element).setPaletteEntry(entry);
				createParamters(element);				
			}
		}
		if(element instanceof PositionableElement){
			CommonElementImporter.getXandY(mapElement, ((PositionableElement)element));
		}
	}
	
	private void parseMapping(Node n) {
		NamedNodeMap attributes = n.getAttributes();
		String fromValue = CommonElementImporter.getAttributeValue(attributes, LibraryElementTags.MAPPING_FROM_ATTRIBUTE);
		String toValue = CommonElementImporter.getAttributeValue(attributes, LibraryElementTags.MAPPING_TO_ATTRIBUTE);
		FBNetworkElement fromElement = findMappingTargetFromName(fromValue);
		FBNetworkElement toElement = findMappingTargetFromName(toValue);
		if(null != fromElement && null != toElement){
			system.getMapping().add(createMappingEntry(toElement, fromElement));
		}
		//TODO perform some notificatin to the user that the mapping has an issue
	}
	
	private Mapping createMappingEntry(FBNetworkElement toElement, FBNetworkElement fromElement) {
		Mapping mapping = LibraryElementFactory.eINSTANCE.createMapping();
		mapping.setFrom(fromElement);
		mapping.setTo(toElement);
		toElement.setMapping(mapping);
		fromElement.setMapping(mapping);
		return mapping;
	}

	private FBNetworkElement findMappingTargetFromName(String targetName) {
		FBNetworkElement element = null;
		if(null != targetName){
			ArrayDeque<String> parts = new ArrayDeque<>(Arrays.asList(targetName.split("\\."))); ////$NON-NLS-1$
			if(parts.size() >= 2){
				FBNetwork nw = null;
				//first find out if the mapping points to a device/resoruce or application and get the approprate starting fbnetwork
				Device dev = system.getDeviceNamed(parts.getFirst());
				Application application = system.getApplicationNamed(parts.getFirst());
				if(null != dev){
					parts.pollFirst();
					Resource res = dev.getResourceNamed(parts.pollFirst());
					if(null != res){
						nw = res.getFBNetwork();
						element = findMappingTargetInFBNetwork(nw, parts);
					}
				}
				if(null == element && null != application){
					parts = new ArrayDeque<>(Arrays.asList(targetName.split("\\."))); //$NON-NLS-1$
					parts.pollFirst();
					nw = application.getFBNetwork();
					element = findMappingTargetInFBNetwork(nw, parts);
				}
			}
		}
		return element;
	}
	
	private FBNetworkElement findMappingTargetInFBNetwork(FBNetwork nw, ArrayDeque<String> parts){
		if(null != nw){
			FBNetworkElement element = nw.getElementNamed(parts.pollFirst());
			if(null != element){
				if(parts.isEmpty()){
					//the list is empty this should be the entity we are looking for
					return element;
				}else if(element instanceof SubApp){
					//as there are more elements the current should be a subapp
					findMappingTargetInFBNetwork(((SubApp)element).getFbNetwork(), parts);
				}
			}
		}
		return null;
	}

	private void parseDeviceNodeChildren(Device device, NodeList childNodes)  throws TypeImportException {
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node n = childNodes.item(i);
			switch(n.getNodeName()){
				case LibraryElementTags.ATTRIBUTE_ELEMENT:
					parseDeviceAttribute(device, n);
					break;
				case LibraryElementTags.PARAMETER_ELEMENT:
					try {
						VarDeclaration parameter = ImportUtils.parseParameter(n);
						if (null != parameter) {
							VarDeclaration devParam = getDeviceParamter(device, parameter.getName());
							if(null != devParam){
								devParam.setValue(parameter.getValue());
							}else{
								parameter.setIsInput(true);
								device.getVarDeclarations().add(parameter);
							}
						}
					} catch (TypeImportException e) {
						Activator.getDefault().logError(e.getMessage(), e);
					}
					break;
				case LibraryElementTags.RESOURCE_ELEMENT:
					device.getResource().add(parseResource(n));
					break;
				default:
					break;
			}
		}		
	}

	private static void parseDeviceAttribute(Device device, Node node) {
		NamedNodeMap attributeMap = node.getAttributes();
		if(isColorAttributeNode(attributeMap)){
			parseColor(device, attributeMap);
		}else if(CommonElementImporter.isProfileAttribute(attributeMap)){
			parseProfile(device, attributeMap);	
		}else {
			CommonElementImporter.parseGenericAttributeNode(device, attributeMap);
		}
	}

	private static boolean isColorAttributeNode(NamedNodeMap attributeMap) {
		Node name = attributeMap.getNamedItem(LibraryElementTags.NAME_ATTRIBUTE);
		return (null != name) && LibraryElementTags.COLOR.equals(name.getNodeValue());
	}
	
	static void parseColor(ColorizableElement colElement, NamedNodeMap attributeMap) {
		Color color = LibraryElementFactory.eINSTANCE.createColor();
		Node value = attributeMap.getNamedItem(LibraryElementTags.VALUE_ATTRIBUTE);
		if (value != null) {
			String[] colors = value.getNodeValue().split(","); //$NON-NLS-1$
			color.setRed(Integer.valueOf(colors[0]));
			color.setGreen(Integer.valueOf(colors[1]));
			color.setBlue(Integer.valueOf(colors[2]));
			colElement.setColor(color);
		}
	}
	
	private static void parseProfile(Device device, NamedNodeMap attributeMap) {
		Node value = attributeMap.getNamedItem(LibraryElementTags.VALUE_ATTRIBUTE);
		if(null != value){
			device.setProfile(value.getNodeValue());
		}
	}

	/**
	 * Creates the values.
	 */
	public static void createParamters(IVarElement element) {		
		if(element instanceof Device){
			element.getVarDeclarations().addAll(EcoreUtil.copyAll(((DeviceTypePaletteEntry)((TypedConfigureableObject)element).getPaletteEntry()).getDeviceType().getVarDeclaration()));
		}
		if(element instanceof Resource){
			element.getVarDeclarations().addAll(EcoreUtil.copyAll(((ResourceTypeEntry)((TypedConfigureableObject)element).getPaletteEntry()).getResourceType().getVarDeclaration()));
		}
		for (VarDeclaration varDecl : element.getVarDeclarations()) {
			Value value = LibraryElementFactory.eINSTANCE.createValue();
			varDecl.setValue(value);
			if (varDecl.getVarInitialization() != null && varDecl.getVarInitialization().getInitialValue() != null) {
				String initialValue = varDecl.getVarInitialization().getInitialValue();
				value.setValue(initialValue);
			}
		}
	}

	private VarDeclaration getDeviceParamter(Device device, String name) {
		for (VarDeclaration varDecl : device.getVarDeclarations()) {
			if(varDecl.getName().equals(name)){
				return varDecl;
			}
		}
		return null;
	}

	private Resource parseResource(Node n) throws TypeImportException {
		//TODO model refactoring - try to merge this code with the dev importer code
		Resource resource = LibraryElementFactory.eINSTANCE.createResource();
		resource.setDeviceTypeResource(false);  //TODO model refactoring - check if a resource of given name is already in the list then it would be a device type resource 
		parseCommon(n, resource);
		FBNetwork fbNetwork = LibraryElementFactory.eINSTANCE.createFBNetwork();
		createResourceTypeNetwork(((ResourceTypeEntry) resource.getPaletteEntry()).getResourceType(),
				fbNetwork);
		resource.setFBNetwork(new ResDevFBNetworkImporter(palette, fbNetwork, resource.getVarDeclarations()).parseFBNetwork(
				CommonElementImporter.findChildNodeNamed(n, LibraryElementTags.FBNETWORK_ELEMENT)));
		return resource;
	}

	private Application parseApplication(Node node) throws TypeImportException {
		Application application = LibraryElementFactory.eINSTANCE.createApplication();
		NamedNodeMap mapApplicationElement = node.getAttributes();
		CommonElementImporter.readNameCommentAttributes(application, mapApplicationElement);
		parseAttributes(application, node.getChildNodes());
		application.setFBNetwork(new SubAppNetworkImporter(palette).parseFBNetwork(
				CommonElementImporter.findChildNodeNamed(node, LibraryElementTags.SUBAPPNETWORK_ELEMENT)));
		return application;
	}

	public void parseAttributes(ConfigurableObject configurableObject, NodeList node) {
		for (int i = 0; i < node.getLength(); i++) {
			Node n = node.item(i);
			if(n.getNodeName() == LibraryElementTags.ATTRIBUTE_ELEMENT){
				CommonElementImporter.parseGenericAttributeNode(configurableObject, n.getAttributes());
			}
		}
	}

	public static void createResourceTypeNetwork(final ResourceType type,
			final FBNetwork resourceFBNetwork) {
		Map<String, Event> events = new HashMap<>();
		Map<String, VarDeclaration> varDecls = new HashMap<>();

		for (FBNetworkElement element : type.getFBNetwork().getNetworkElements()){
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
				// eventCopy.setType(event.getType());
				eventCopy.setName(event.getName());
				eventCopy.setComment(event.getComment());
				eventCopy.setIsInput(event.isIsInput());
				if (event.getValue() != null) {
					eventCopy.setValue(EcoreUtil.copy(event.getValue()));
				}
				events.put(element.getName() + "." + event.getName(), eventCopy); //$NON-NLS-1$
				interfaceList.getEventInputs().add(eventCopy);
			}
			for (Iterator<Event> iterator2 = element.getInterface().getEventOutputs().iterator(); iterator2.hasNext();) {
				Event event = iterator2.next();
				Event eventCopy = LibraryElementFactory.eINSTANCE.createEvent();
				// eventCopy.setType(event.getType());
				eventCopy.setName(event.getName());
				eventCopy.setComment(event.getComment());
				eventCopy.setIsInput(event.isIsInput());
				if (event.getValue() != null) {
					eventCopy.setValue(EcoreUtil.copy(event.getValue()));
				}
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
