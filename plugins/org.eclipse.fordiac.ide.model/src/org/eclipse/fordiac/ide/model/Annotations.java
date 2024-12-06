/*******************************************************************************
 * Copyright (c) 2008, 2024 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 *                          Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Ingo Hegny, Monika Wenger, Martin Jobst
 *     - initial API and implementation and/or initial documentation
 *   Monika Wenger - extracted the model helper methods into this annotations class
 *   Monika Wenger - introduced IEC 61499 attribute support into the model
 *   Alois Zoitl   - reworked model helper functions for better mapping and sub-app support
 *   Hesam Rezaee  - add variable configuration for global constants
 *   Alois Zoitl   - extracted the helper methods for interface lists
 *******************************************************************************/
package org.eclipse.fordiac.ide.model;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
import org.eclipse.fordiac.ide.model.datatype.helper.InternalAttributeDeclarations;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterConnection;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.DataConnection;
import org.eclipse.fordiac.ide.model.libraryElement.Demultiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.EventConnection;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Mapping;
import org.eclipse.fordiac.ide.model.libraryElement.MemberVarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Multiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.Segment;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.eclipse.fordiac.ide.model.libraryElement.TypedConfigureableObject;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.impl.VarDeclarationImpl;
import org.eclipse.fordiac.ide.model.typelibrary.AdapterTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;

public final class Annotations {

	// *** Application ***//
	public static AutomationSystem getAutomationSystem(final Application a) {
		return (AutomationSystem) a.eContainer();
	}

	// *** BaseFBType ***//
	public static Algorithm getAlgorithmNamed(final BaseFBType baseFBType, final String name) {
		return baseFBType.getAlgorithm().stream().filter(alg -> alg.getName().equals(name)).findFirst().orElse(null);
	}

	// *** Connection ***//
	public static FBNetworkElement getSourceElement(final Connection c) {
		return (null != c.getSource()) ? c.getSource().getFBNetworkElement() : null;
	}

	public static FBNetworkElement getDestinationElement(final Connection c) {
		return (null != c.getDestination()) ? c.getDestination().getFBNetworkElement() : null;
	}

	public static boolean isResourceConnection(final Connection c) {
		// if source element is null it is a connection from a CFB interface element
		return ((null != c.getSourceElement()) && (null != c.getSourceElement().getFbNetwork())
				&& (c.getSourceElement().getFbNetwork().eContainer() instanceof Resource));
	}

	public static boolean isInterfaceConnection(final Connection c) {
		return isInterfaceConnection(c, null);
	}

	public static boolean isInterfaceConnection(final Connection c, final FBNetworkElement path) {

		if (c == null) {
			return false;
		}

		if (c instanceof EventConnection || c instanceof AdapterConnection) {
			// TODO Implement
			return false;
		}

		// TODO: verify interface detection for CFB
		final FBNetworkElement s = c.getSourceElement();
		final FBNetworkElement d = c.getDestinationElement();
		final EObject container = c.eContainer().eContainer();

		boolean sourceIsInterface = (s == container);
		boolean destinationIsInterface = (d == container);

		if (sourceIsInterface || destinationIsInterface) {
			return true;
		}

		if (s == path) {
			sourceIsInterface = false;
		} else if (s instanceof Demultiplexer) {
			final var connections = s.getInterface().getInputVars().get(0).getInputConnections();
			sourceIsInterface = !connections.isEmpty() && isInterfaceConnection(connections.get(0), s);
		} else if (s instanceof Multiplexer) {
			sourceIsInterface = s.getInterface().getInputVars().stream()
					.anyMatch(v -> !v.getInputConnections().isEmpty())
					&& s.getInterface().getInputVars().stream().allMatch(
							v -> v.getInputConnections().stream().allMatch(co -> isInterfaceConnection(co, s)));
		}

		if (d == path) {
			destinationIsInterface = false;
		} else if (d instanceof Demultiplexer) {
			destinationIsInterface = d.getInterface().getOutputVars().stream()
					.anyMatch(v -> !v.getOutputConnections().isEmpty())
					&& d.getInterface().getOutputVars().stream().allMatch(
							v -> v.getOutputConnections().stream().allMatch(co -> isInterfaceConnection(co, d)));
		} else if (d instanceof Multiplexer) {
			final var connections = d.getInterface().getOutputVars().get(0).getOutputConnections();
			destinationIsInterface = !connections.isEmpty() && isInterfaceConnection(connections.get(0), d);
		}

		return (sourceIsInterface || destinationIsInterface);
	}

	public static void checkifConnectionBroken(final Connection c) {
		if (!c.isResourceConnection()) {
			final Resource sourceRes = (null != c.getSourceElement()) ? c.getSourceElement().getResource() : null;
			final Resource destinationRes = (null != c.getDestinationElement())
					? c.getDestinationElement().getResource()
					: null;
			c.setBrokenConnection(((null != sourceRes) && (!sourceRes.equals(destinationRes)))
					|| ((null != destinationRes) && (!destinationRes.equals(sourceRes))));
		}
	}

	// *** Device ***//
	public static AutomationSystem getAutomationSystem(final Device d) {
		return d.getSystemConfiguration().getAutomationSystem();
	}

	public static SystemConfiguration getSystemConfiguration(final Device d) {
		return (SystemConfiguration) d.eContainer();
	}

	public static Resource getResourceNamed(final Device device, final String name) {
		return device.getResource().stream().filter(resource -> resource.getName().equals(name)).findFirst()
				.orElse(null);
	}

	// *** ECState ***//
	public static boolean isStartState(final ECState ecs) {
		return null != ecs.getECC() && ecs.equals(ecs.getECC().getStart());
	}

	// *** ECTransition ***//
	public static String getConditionText(final ECTransition ect) {
		String retVal = ""; //$NON-NLS-1$
		final Event event = ect.getConditionEvent();
		if (event != null) {
			retVal = getTransitionEventName(event);
		}
		final String expression = ect.getConditionExpression();
		if (expression != null) {
			if ("1".equals(expression)) { //$NON-NLS-1$
				retVal = expression;
			} else if (!expression.isBlank()) {
				retVal += "[" + expression + "]"; //$NON-NLS-1$ //$NON-NLS-2$
			}
		}
		return retVal;
	}

	public static String getTransitionEventName(final Event event) {
		if (event.getFBNetworkElement() instanceof AdapterFB) {
			return event.getFBNetworkElement().getName() + "." + event.getName(); //$NON-NLS-1$
		}
		return event.getName();
	}

	// *** SubApp ***//

	// *** Mapping ***//
	public static AutomationSystem getAutomationSystem(final Mapping m) {
		return (null != m.eContainer()) ? (AutomationSystem) m.eContainer() : null;
	}

	// *** Resource ***//
	public static AutomationSystem getAutomationSystem(final Resource r) {
		AutomationSystem system = null;
		if (null != r.getDevice()) {
			system = r.getDevice().getAutomationSystem();
		}
		return system;
	}

	// *** FBNetwork ***//
	public static void addConnection(final FBNetwork fbn, final Connection connection) {
		addConnectionWithIndex(fbn, connection, -1);
	}

	public static void addConnectionWithIndex(final FBNetwork fbn, final Connection connection, final int index) {
		if (connection instanceof final EventConnection evCon) {
			if (index != -1) {
				fbn.getEventConnections().add(index, evCon);
			} else {
				fbn.getEventConnections().add(evCon);
			}
		}
		if (connection instanceof final DataConnection dataCon) {
			if (index != -1) {
				fbn.getDataConnections().add(index, dataCon);
			} else {
				fbn.getDataConnections().add(dataCon);
			}
		}
		if (connection instanceof final AdapterConnection adpCon) {
			if (index != -1) {
				fbn.getAdapterConnections().add(index, adpCon);
			} else {
				fbn.getAdapterConnections().add(adpCon);
			}
		}
	}

	public static void removeConnection(final FBNetwork fbn, final Connection connection) {
		if (connection instanceof EventConnection) {
			fbn.getEventConnections().remove(connection);
		}
		if (connection instanceof DataConnection) {
			fbn.getDataConnections().remove(connection);
		}
		if (connection instanceof AdapterConnection) {
			fbn.getAdapterConnections().remove(connection);
		}
	}

	public static int getConnectionIndex(final FBNetwork fbn, final Connection connection) {
		if (connection instanceof EventConnection) {
			return fbn.getEventConnections().indexOf(connection);
		}
		if (connection instanceof DataConnection) {
			return fbn.getDataConnections().indexOf(connection);
		}
		if (connection instanceof AdapterConnection) {
			return fbn.getAdapterConnections().indexOf(connection);
		}
		return -1;
	}

	public static boolean isApplicationNetwork(final FBNetwork fbn) {
		return fbn.eContainer() instanceof Application;
	}

	public static boolean isSubApplicationNetwork(final FBNetwork fbn) {
		return fbn.eContainer() instanceof SubApp;
	}

	public static boolean isResourceNetwork(final FBNetwork fbn) {
		return fbn.eContainer() instanceof Resource;
	}

	public static boolean isCFBTypeNetwork(final FBNetwork fbn) {
		return fbn.eContainer() instanceof CompositeFBType;
	}

	public static AutomationSystem getAutomationSystem(final FBNetwork fbn) {
		final EObject root = EcoreUtil.getRootContainer(fbn);
		return root instanceof final AutomationSystem system ? system : null;
	}

	public static Application getApplication(final FBNetwork fbn) {
		if (fbn.isApplicationNetwork()) {
			// no null check is need as this is already done in isApplicationNetwork
			return (Application) fbn.eContainer();
		}
		if (fbn.isSubApplicationNetwork() && (null != ((SubApp) fbn.eContainer()).getFbNetwork())) {
			return ((SubApp) fbn.eContainer()).getFbNetwork().getApplication();
		}
		return null;
	}

	public static FB getFBNamed(final FBNetwork fbn, final String name) {
		return fbn.getNetworkElements().stream().filter(FB.class::isInstance).map(FB.class::cast)
				.filter(element -> (element.getName().equals(name))).findFirst().orElse(null);
	}

	public static SubApp getSubAppNamed(final FBNetwork fbn, final String name) {
		return fbn.getNetworkElements().stream().filter(SubApp.class::isInstance).map(SubApp.class::cast)
				.filter(element -> (element.getName().equals(name))).findFirst().orElse(null);
	}

	public static FBNetworkElement getElementNamed(final FBNetwork fbn, final String name) {
		return fbn.getNetworkElements().stream().filter(element -> (element.getName().equals(name))).findFirst()
				.orElse(null);
	}

	// *** AutomationSystem ***//
	public static Device getDeviceNamed(final AutomationSystem as, final String name) {
		return as.getSystemConfiguration() != null ? as.getSystemConfiguration().getDeviceNamed(name) : null;
	}

	public static Application getApplicationNamed(final AutomationSystem as, final String name) {
		return as.getApplication().stream().filter(element -> (element.getName().equals(name))).findFirst()
				.orElse(null);
	}

	// *** VarDeclaration ***//
	public static boolean isArray(final VarDeclaration vd) {
		return vd.getArraySize() != null && vd.getArraySize().getValue() != null
				&& !vd.getArraySize().getValue().isBlank();
	}

	public static void setVarConfig(final VarDeclarationImpl varDeclarationImpl, final boolean config) {
		setVarConfig(varDeclarationImpl, Boolean.toString(config));
	}

	private static void setVarConfig(final VarDeclarationImpl varDeclarationImpl, final String config) {
		varDeclarationImpl.setAttribute(InternalAttributeDeclarations.VAR_CONFIG, config, ""); //$NON-NLS-1$
	}

	public static boolean isVarConfig(final VarDeclaration vd) {
		final String configurationAttribute = vd.getAttributeValue(LibraryElementTags.VAR_CONFIG);
		return "true".equals(configurationAttribute); //$NON-NLS-1$
	}

	// *** AttributeDeclaration ***//
	public static void setTarget(final AttributeDeclaration attributeDeclaration, final StructuredType target) {
		final String targetString = target.getMemberVariables().stream()
				.map(member -> (member.getName() + ":=" + member.getValue().getValue())) //$NON-NLS-1$
				.collect(Collectors.joining(",", "(", ")")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		if (!targetString.contains("FALSE")) { //$NON-NLS-1$
			attributeDeclaration.deleteAttribute(InternalAttributeDeclarations.TARGET.getName());
		} else {
			attributeDeclaration.setAttribute(InternalAttributeDeclarations.TARGET, targetString, ""); //$NON-NLS-1$
		}
	}

	public static StructuredType getTarget(final AttributeDeclaration attributeDeclaration) {
		String targetString = attributeDeclaration.getAttributeValue(InternalAttributeDeclarations.TARGET.getName());
		if (targetString == null) {
			return null;
		}

		final StructuredType structType = DataFactory.eINSTANCE.createStructuredType();
		targetString = targetString.substring(1, targetString.length() - 1);
		final String[] pairs = targetString.split(","); //$NON-NLS-1$
		for (final String pair : pairs) {
			final String[] keyValue = pair.split(":="); //$NON-NLS-1$
			if (keyValue.length != 2) {
				continue;
			}

			final MemberVarDeclaration member = LibraryElementFactory.eINSTANCE.createMemberVarDeclaration();
			member.setName(keyValue[0]);
			member.setType(ElementaryTypes.BOOL);

			final Value val = LibraryElementFactory.eINSTANCE.createValue();
			val.setValue(keyValue[1]);
			member.setValue(val);

			structType.getMemberVariables().add(member);
		}

		if (structType.getMemberVariables().size() != ((StructuredType) InternalAttributeDeclarations.TARGET.getType())
				.getMemberVariables().size()) {
			// Add missing members if new ones are added
			((StructuredType) InternalAttributeDeclarations.TARGET.getType()).getMemberVariables().forEach(varDecl -> {
				final Optional<VarDeclaration> correctMember = structType.getMemberVariables().stream()
						.filter(member -> member.getName().equals(varDecl.getName())).findFirst();
				if (correctMember.isEmpty()) {
					structType.getMemberVariables().add(EcoreUtil.copy(varDecl));
				}
			});
		}

		return structType;
	}

	public static boolean isValidObject(final AttributeDeclaration attributeDeclaration,
			final ConfigurableObject object) {
		final StructuredType target = attributeDeclaration.getTarget();
		if (target != null) {
			return switch (object) {
			case final IInterfaceElement o -> getValueFromClassName(target, IInterfaceElement.class);
			case final SubApp o -> getValueFromClassName(target, SubApp.class);
			case final FBType o -> getValueFromClassName(target, FBType.class);
			case final Application o -> getValueFromClassName(target, Application.class);
			case final Connection o -> getValueFromClassName(target, Connection.class);
			case final FB o -> getValueFromClassName(target, FB.class);
			case final DataType o -> getValueFromClassName(target, DataType.class);
			default -> false;
			};
		}
		return true;
	}

	private static boolean getValueFromClassName(final StructuredType lock, final Class<?> clazz) {
		final Optional<VarDeclaration> correctMember = lock.getMemberVariables().stream()
				.filter(member -> member.getName().equals(clazz.getSimpleName())).findFirst();
		if (correctMember.isPresent()) {
			final Value val = correctMember.get().getValue();
			return Boolean.parseBoolean(val.getValue());
		}
		return true;
	}

	// *** ConfigurableObject ***//
	public static void setAttribute(final ConfigurableObject object, final String attributeName, final DataType type,
			final String value, final String comment) {
		Attribute attribute = getAttribute(object, attributeName);
		if (attribute == null) {
			attribute = LibraryElementFactory.eINSTANCE.createAttribute();
			attribute.setName(attributeName);
			attribute.setType(type);
			attribute.setValue(value);
			if (null != comment) {
				attribute.setComment(comment);
			}
			object.getAttributes().add(attribute);
		} else {
			attribute.setValue(value);
			if (null != comment) {
				attribute.setComment(comment);
			}
		}
	}

	public static void setAttribute(final ConfigurableObject object, final AttributeDeclaration attributeDeclaration,
			final String value, final String comment) {
		Attribute attribute = getAttribute(object, attributeDeclaration.getName());
		if (attribute == null) {
			attribute = LibraryElementFactory.eINSTANCE.createAttribute();
			attribute.setName(attributeDeclaration.getName());
			attribute.setAttributeDeclaration(attributeDeclaration);
			attribute.setType(attributeDeclaration.getType());
			attribute.setValue(value);
			if (null != comment) {
				attribute.setComment(comment);
			}
			object.getAttributes().add(attribute);
		} else {
			attribute.setValue(value);
			if (null != comment) {
				attribute.setComment(comment);
			}
		}
	}

	public static boolean deleteAttribute(final ConfigurableObject object, final String attributeName) {
		if ((object != null) && (attributeName != null)) {
			final List<Attribute> toDelete = object.getAttributes().stream()
					.filter(attr -> attributeName.equals(attr.getName())).toList();
			if (toDelete.isEmpty()) {
				return false;
			}
			toDelete.forEach(attr -> object.getAttributes().remove(attr));
			return true;
		}
		return false;
	}

	public static String getAttributeValue(final ConfigurableObject object, final String attributeName) {
		final Attribute a = getAttribute(object, attributeName);
		return null != a ? a.getValue() : null;
	}

	public static Attribute getAttribute(final ConfigurableObject object, final String attributeName) {
		return attributeName != null ? object.getAttributes().stream()
				.filter(attribute -> attribute.getName().equalsIgnoreCase(attributeName)).findFirst().orElse(null)
				: null;
	}

	// *** DataConnection ***//
	public static VarDeclaration getDataSource(final DataConnection dc) {
		return (VarDeclaration) dc.getSource();
	}

	public static VarDeclaration getDataDestination(final DataConnection dc) {
		return (VarDeclaration) dc.getDestination();
	}

	// *** EventConnection ***//
	public static Event getEventSource(final EventConnection ec) {
		return (Event) ec.getSource();
	}

	public static Event getEventDestination(final EventConnection ec) {
		return (Event) ec.getDestination();
	}

	// *** AdapterConnection ***//
	public static AdapterDeclaration getAdapterSource(final AdapterConnection ac) {
		return (AdapterDeclaration) ac.getSource();
	}

	public static AdapterDeclaration getAdapterDestination(final AdapterConnection ac) {
		return (AdapterDeclaration) ac.getDestination();
	}

	// *** IInterfaceElement ***//
	public static FBNetworkElement getFBNetworkElement(final IInterfaceElement iie) {
		return (iie.eContainer() instanceof final InterfaceList il) ? il.getFBNetworkElement() : null;
	}

	// *** SystemConfiguration ***//
	public static AutomationSystem getAutomationSystem(final SystemConfiguration sc) {
		return (AutomationSystem) sc.eContainer();
	}

	public static Segment getSegmentNamed(final SystemConfiguration sc, final String name) {
		return sc.getSegments().stream().filter(segment -> segment.getName().equals(name)).findFirst().orElse(null);
	}

	public static Device getDeviceNamed(final SystemConfiguration sc, final String name) {
		return sc.getDevices().stream().filter(device -> device.getName().equals(name)).findFirst().orElse(null);
	}

	// *** ResourceTypeFB ***//
	public static boolean isResourceTypeFB() {
		return true;
	}

	// *** TypedConfigureableObject ***//
	public static String getTypeName(final TypedConfigureableObject tco) {
		return (null != tco.getTypeEntry()) ? tco.getTypeEntry().getTypeName() : null;
	}

	public static LibraryElement getType(final TypedConfigureableObject tco) {
		return null != tco.getTypeEntry() ? tco.getTypeEntry().getType() : null;
	}

	public static TypeLibrary getTypeLibrary(final TypedConfigureableObject tco) {
		return null != tco.getTypeEntry() ? tco.getTypeEntry().getTypeLibrary() : null;
	}

	// *** AdapterFB ***//
	public static boolean isSocket(final AdapterFB afb) {
		return !afb.isPlug();
	}

	public static FBType getType(final AdapterFB afb) {
		if ((afb.getTypeEntry() instanceof final AdapterTypeEntry adpTypeEntry) && (null != afb.getAdapterDecl())) {
			if (afb.isPlug()) {
				return adpTypeEntry.getType().getPlugType();
			}
			return adpTypeEntry.getType();
		}
		return null;
	}

	public static boolean isPlug(final AdapterFB afb) {
		return !afb.getAdapterDecl().isIsInput();
	}

	private Annotations() {
		throw new UnsupportedOperationException("The utility class Annotations should not be instatiated"); //$NON-NLS-1$
	}

}
