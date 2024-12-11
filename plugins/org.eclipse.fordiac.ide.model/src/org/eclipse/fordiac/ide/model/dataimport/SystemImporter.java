/*******************************************************************************
 * Copyright (c) 2016, 2024 fortiss GmbH, Johannes Kepler University, Linz,
 *                          Primetals Technologies Austria GmbH
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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.xml.stream.XMLStreamException;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.dataimport.ConnectionHelper.ConnectionBuilder;
import org.eclipse.fordiac.ide.model.dataimport.exceptions.TypeImportException;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Color;
import org.eclipse.fordiac.ide.model.libraryElement.ColorizableElement;
import org.eclipse.fordiac.ide.model.libraryElement.CommunicationChannel;
import org.eclipse.fordiac.ide.model.libraryElement.CommunicationConfiguration;
import org.eclipse.fordiac.ide.model.libraryElement.CommunicationMappingTarget;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Link;
import org.eclipse.fordiac.ide.model.libraryElement.Mapping;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.Segment;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.resource.TypeImportDiagnostic;
import org.eclipse.fordiac.ide.model.systemconfiguration.CommunicationConfigurationDetails;
import org.eclipse.fordiac.ide.model.typelibrary.DeviceTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.gef.commands.CommandStack;

public class SystemImporter extends CommonElementImporter {

	private final Map<Resource, Map<String, FBNetworkElement>> resFBNElementMapping = new HashMap<>();
	private final List<FBNetworkElement> mappedFBs = new ArrayList<>();
	private final List<ConnectionBuilder<Connection>> brokenConnections = new ArrayList<>();

	public SystemImporter(final InputStream inputStream, final TypeLibrary typeLibrary) {
		super(inputStream, typeLibrary);
	}

	public SystemImporter(final IFile systemfile) {
		super(systemfile);
	}

	@Override
	public AutomationSystem getElement() {
		return (AutomationSystem) super.getElement();
	}

	@Override
	public void loadElement() throws IOException, XMLStreamException, TypeImportException {
		final long start = System.nanoTime();
		super.loadElement();
		finalizeMappingModel();
		final long elapsed = System.nanoTime() - start;
		FordiacLogHelper.logInfo("System \"" + getElement().getName() + "\" load time: " + elapsed / 1_000_000 + "ms"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	@Override
	protected LibraryElement createRootModelElement() {
		return createAutomationSystem();
	}

	/**
	 * Create an empty automation system model
	 *
	 * this can either be used for the importer or for creating a new system
	 *
	 * @return the automation system model with its basic setup
	 */
	public static AutomationSystem createAutomationSystem() {
		final AutomationSystem system = LibraryElementFactory.eINSTANCE.createAutomationSystem();
		system.setCommandStack(new CommandStack());
		// create PhysicalConfiguration
		final SystemConfiguration sysConf = LibraryElementFactory.eINSTANCE.createSystemConfiguration();
		system.setSystemConfiguration(sysConf);

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
			case LibraryElementTags.COMPILER_INFO_ELEMENT:
				getElement().setCompilerInfo(parseCompilerInfo());
				break;
			case LibraryElementTags.APPLICATION_ELEMENT:
				parseApplication(getElement());
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
			case LibraryElementTags.ATTRIBUTE_ELEMENT:
				parseGenericAttributeNode(getElement());
				proceedToEndElementNamed(LibraryElementTags.ATTRIBUTE_ELEMENT);
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
			segment.setWidth(Double.parseDouble(dx1));
		}

		final String type = getAttributeValue(LibraryElementTags.TYPE_ATTRIBUTE);
		if (null != type) {
			segment.setTypeEntry(addDependency(getTypeLibrary().getSegmentTypeEntry(type)));
			parseCommunication(segment);
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
			if (LibraryElementTags.PARAMETER_ELEMENT.equals(name)) {
				segment.getVarDeclarations().add(parseParameter());
				proceedToEndElementNamed(LibraryElementTags.PARAMETER_ELEMENT);
				return true;
			}
			return false;
		});
	}

	private static void parseCommunication(final Segment segment) {
		final String typeName = segment.getFullTypeName();
		if (typeName != null) {
			final CommunicationConfigurationDetails commConfig = CommunicationConfigurationDetails
					.getCommConfigUiFromExtensionPoint(typeName, CommunicationConfigurationDetails.COMM_EXT_ATT_ID);
			if (commConfig != null) {
				final CommunicationConfiguration config = commConfig.createModel(segment.getVarDeclarations());
				segment.setCommunication(config);
			}
		}
	}

	private void parseLink(final SystemConfiguration sysConf) throws XMLStreamException, TypeImportException {
		final String commResource = getAttributeValue(LibraryElementTags.SEGMENT_COMM_RESOURCE);
		final String comment = getAttributeValue(LibraryElementTags.COMMENT_ATTRIBUTE);
		final String segmentName = getAttributeValue(LibraryElementTags.SEGMENT_NAME_ELEMENT);

		final Segment segment = sysConf.getSegmentNamed(segmentName);
		final Device device = sysConf.getDeviceNamed(commResource);

		if ((null != segment) && (null != device)) {
			final Link link = LibraryElementFactory.eINSTANCE.createLink();
			link.setComment(comment);
			segment.getOutConnections().add(link);
			device.getInConnections().add(link);

			processChildren(LibraryElementTags.LINK_ELEMENT, name -> {
				if (LibraryElementTags.ATTRIBUTE_ELEMENT.equals(name)) {
					parseGenericAttributeNode(link);
					proceedToEndElementNamed(LibraryElementTags.ATTRIBUTE_ELEMENT);
					return true;
				}
				return false;
			});

			sysConf.getLinks().add(link);
		}
		// TODO implement some mechnism for the case that we can not find the device or
		// the segement
		proceedToEndElementNamed(LibraryElementTags.LINK_ELEMENT);
	}

	private Device parseDevice() throws TypeImportException, XMLStreamException {
		final Device device = LibraryElementFactory.eINSTANCE.createDevice();
		readNameCommentAttributes(device);
		getXandY(device);
		parseDeviceType(device);
		parseDeviceNodeChildren(device);
		return device;
	}

	private void parseDeviceType(final Device device) {
		final String typeName = getAttributeValue(LibraryElementTags.TYPE_ATTRIBUTE);
		if (typeName != null) {
			final DeviceTypeEntry entry = addDependency(getTypeLibrary().getDeviceTypeEntry(typeName));
			if (null != entry) {
				device.setTypeEntry(entry);
				createParameters(device);
			}
		}
	}

	private void parseMapping() throws XMLStreamException {
		final String fromValue = getAttributeValue(LibraryElementTags.MAPPING_FROM_ATTRIBUTE);
		final String toValue = getAttributeValue(LibraryElementTags.MAPPING_TO_ATTRIBUTE);
		final FBNetworkElement fromElement = findMappingTargetFromName(fromValue);
		final FBNetworkElement toElement = (fromElement instanceof CommunicationChannel)
				? findMappingTargetFromName(toValue, fromElement)
				: findMappingToElement(fromValue, toValue, fromElement);

		if ((null != fromElement) && (null != toElement)) {
			getElement().getMapping().add(createMappingEntry(toElement, fromElement));
		}

		// TODO perform some notificatin to the user that the mapping has an issue
		proceedToEndElementNamed(LibraryElementTags.MAPPING_ELEMENT);
	}

	private FBNetworkElement findMappingToElement(final String fromValue, final String toValue,
			final FBNetworkElement fromElement) {
		final var devResSeperator = toValue.indexOf('.');
		if (devResSeperator == -1) {
			getErrors().add(
					new TypeImportDiagnostic("Wrong to mapping string", fromValue + "->" + toValue, getLineNumber()));//$NON-NLS-1$
			return null;
		}

		final Device dev = getElement().getDeviceNamed(toValue.substring(0, devResSeperator));
		if (dev == null) {
			getErrors().add(
					new TypeImportDiagnostic("Device missing in mapping", fromValue + "->" + toValue, getLineNumber()));//$NON-NLS-1$
			return null;
		}

		final var resFBSeperator = toValue.indexOf('.', devResSeperator + 1);
		final String resName = (resFBSeperator == -1) ? toValue.substring(devResSeperator + 1)
				: toValue.substring(devResSeperator + 1, resFBSeperator);

		final Resource res = dev.getResourceNamed(resName);
		if (res == null) {
			getErrors().add(new TypeImportDiagnostic("Resource missing in mapping", fromValue + "->" + toValue, //$NON-NLS-1$
					getLineNumber()));
			return null;
		}

		final String targetFBName = (resFBSeperator == -1) ? fromValue : toValue.substring(resFBSeperator + 1);

		final Map<String, FBNetworkElement> fbNetworkElementMap = resFBNElementMapping.get(res);
		if (fbNetworkElementMap == null) {
			// we don't have a resource in our list of parsed resources
			return null;
		}
		final FBNetworkElement fbNetworkElement = fbNetworkElementMap.get(targetFBName);
		if (fbNetworkElement == null && fromElement != null) {
			final FBNetworkElement mappingTarget = MappingTargetCreator.createMappingTarget(res, fromElement,
					targetFBName);
			if (mappingTarget != null) {
				// we generated a new target element put the from element into the list for
				// connection generation at the end
				mappedFBs.add(fromElement);
				fbNetworkElementMap.put(targetFBName, mappingTarget);

			}
			return mappingTarget;
		}
		return fbNetworkElement;
	}

	private static Mapping createMappingEntry(final FBNetworkElement toElement, final FBNetworkElement fromElement) {
		final Mapping mapping = LibraryElementFactory.eINSTANCE.createMapping();
		mapping.setFrom(fromElement);
		mapping.setTo(toElement);
		toElement.setMapping(mapping);
		fromElement.setMapping(mapping);
		return mapping;
	}

	private FBNetworkElement findMappingTargetFromName(final String targetName,
			final FBNetworkElement copyCommunication) {
		if (null != targetName) {
			final Deque<String> parts = new ArrayDeque<>(Arrays.asList(targetName.split("\\."))); ////$NON-NLS-1$
			if (parts.size() >= 2) {
				final Segment segment = getElement().getSystemConfiguration().getSegmentNamed(parts.getFirst());
				if (null != segment) {
					parts.pollFirst();
					final String windowName = parts.pollFirst();
					final Optional<CommunicationMappingTarget> findWindow = segment.getCommunication()
							.getMappingTargets().stream().filter(c -> c.getName().equals(windowName)).findFirst();
					final CommunicationMappingTarget channel = findWindow.isPresent() ? findWindow.get() : null;
					if (channel != null) {
						final CommunicationChannel comm = LibraryElementFactory.eINSTANCE.createCommunicationChannel();
						comm.setName(copyCommunication.getName());
						comm.setPosition(EcoreUtil.copy(copyCommunication.getPosition()));
						comm.setTypeEntry(copyCommunication.getTypeEntry());
						comm.setInterface(copyCommunication.getType().getInterfaceList().copy());
						channel.getMappedElements().add(comm);
						return comm;
					}
				}
			}
		}
		return null;
	}

	private FBNetworkElement findMappingTargetFromName(final String targetName) {
		if (null != targetName) {
			final Deque<String> parts = new ArrayDeque<>(Arrays.asList(targetName.split("\\."))); ////$NON-NLS-1$
			if (parts.size() >= 2) {
				final Application application = getElement().getApplicationNamed(parts.getFirst());
				if (null != application) {
					parts.pollFirst();
					return findMappingTargetInFBNetwork(application.getFBNetwork(), parts);
				}
			}
		}
		return null;
	}

	private static FBNetworkElement findMappingTargetInFBNetwork(final FBNetwork nw, final Deque<String> parts) {
		if (null != nw) {
			final FBNetworkElement element = nw.getElementNamed(parts.pollFirst());
			if (null != element) {
				if (parts.isEmpty()) {
					// the list is empty this should be the entity we are looking for
					return element;
				}
				if (element instanceof final SubApp subApp) {
					// as there are more elements the current should be a subapp
					findMappingTargetInFBNetwork(subApp.getSubAppNetwork(), parts);
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
						devParam.getAttributes().addAll(parameter.getAttributes());
						devParam.setValue(parameter.getValue());
					} else {
						parameter.setIsInput(true);
						device.getVarDeclarations().add(parameter);
					}
				}
				break;
			case LibraryElementTags.RESOURCE_ELEMENT:
				final Map<String, FBNetworkElement> fbNetworkElementMap = new HashMap<>();
				final var resource = parseResource(fbNetworkElementMap);
				device.getResource().add(resource);
				resFBNElementMapping.put(resource, fbNetworkElementMap);
				break;
			default:
				return false;
			}
			return true;
		});
	}

	private void parseDeviceAttribute(final Device device) throws XMLStreamException, TypeImportException {
		if (isColorAttributeNode()) {
			parseColor(device);
		} else if (isProfileAttribute()) {
			parseProfile(device);
		} else {
			parseGenericAttributeNode(device);
		}
		proceedToEndElementNamed(LibraryElementTags.ATTRIBUTE_ELEMENT);
	}

	@Override
	protected void parseResourceNetwork(final Map<String, FBNetworkElement> fbNetworkElementMap,
			final Resource resource, final FBNetwork fbNetwork) throws TypeImportException, XMLStreamException {
		final ResDevFBNetworkImporter resNetworkImporter = new ResDevFBNetworkImporter(this, fbNetwork,
				resource.getVarDeclarations(), fbNetworkElementMap);
		resNetworkImporter.parseFBNetwork(LibraryElementTags.FBNETWORK_ELEMENT);
		brokenConnections.addAll(resNetworkImporter.getBrokenConnections());
	}

	private boolean isColorAttributeNode() {
		final String name = getAttributeValue(LibraryElementTags.NAME_ATTRIBUTE);
		return LibraryElementTags.COLOR.equals(name);
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

	private void parseApplication(final AutomationSystem automationSystem)
			throws TypeImportException, XMLStreamException {
		final Application application = LibraryElementFactory.eINSTANCE.createApplication();
		automationSystem.getApplication().add(application);
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
	}

	private void finalizeMappingModel() {
		generateMappedFBConnections();
		repairBrokenConnections();
	}

	/**
	 * For each of the mapped FBs that were generated during the mapping parsing
	 * check if connections need to be generated in resource model.
	 *
	 */
	private void generateMappedFBConnections() {
		for (final FBNetworkElement fbnEl : mappedFBs) {
			final FBNetworkElement srcResFb = fbnEl.getOpposite();
			final Resource res = fbnEl.getResource();
			fbnEl.getInterface().getOutputs().flatMap(ie -> ie.getOutputConnections().stream()) //
					.filter(con -> con.getDestinationElement().getResource() == res) //
					.forEach(con -> res.getFBNetwork().addConnection(createResourceCon(srcResFb, con)));
		}
	}

	/*
	 * Check if after the mapped fbs are created the broken resource connections can
	 * be inserted into the network
	 *
	 */
	private void repairBrokenConnections() {
		brokenConnections.forEach(ConnectionBuilder<Connection>::repair);
	}

	private static Connection createResourceCon(final FBNetworkElement srcResFB, final Connection con) {
		final FBNetworkElement dstResFB = con.getDestinationElement().getOpposite();
		final Connection resCon = EcoreUtil.copy(con);
		resCon.setSource(srcResFB.getOutput(con.getSource().getName()));
		resCon.setDestination(dstResFB.getInput(con.getDestination().getName()));
		return resCon;
	}

}
