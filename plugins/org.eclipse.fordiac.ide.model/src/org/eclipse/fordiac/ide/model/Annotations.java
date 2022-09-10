/*******************************************************************************
 * Copyright (c) 2008 - 2018 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 				 2018 Johannes Kepler University
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
 *   Alois Zoitl - reworked model helper functions for better mapping and sub-app
 *                 support
 *******************************************************************************/
package org.eclipse.fordiac.ide.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.data.BaseType1;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterConnection;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFBType;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
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
import org.eclipse.fordiac.ide.model.libraryElement.Multiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.Segment;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.eclipse.fordiac.ide.model.libraryElement.TypedConfigureableObject;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.AdapterTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;

public final class Annotations {

	// *** AdapterType ***//
	public static InterfaceList getInterfaceList(final AdapterType at) {
		return at.getAdapterFBType().getInterfaceList();
	}

	public static AdapterFBType getPlugType(final AdapterType adapterType) {
		final AdapterFBType temp = EcoreUtil.copy(adapterType.getAdapterFBType());
		// fetch the interface to invert it
		final List<Event> inputEvents = new ArrayList<>(temp.getInterfaceList().getEventOutputs());
		for (final Event event : inputEvents) {
			event.setIsInput(true);
		}
		final List<Event> outputEvents = new ArrayList<>(temp.getInterfaceList().getEventInputs());
		for (final Event event : outputEvents) {
			event.setIsInput(false);
		}
		final List<VarDeclaration> inputVars = new ArrayList<>(temp.getInterfaceList().getOutputVars());
		for (final VarDeclaration varDecl : inputVars) {
			varDecl.setIsInput(true);
		}
		final List<VarDeclaration> outputVars = new ArrayList<>(temp.getInterfaceList().getInputVars());
		for (final VarDeclaration varDecl : outputVars) {
			varDecl.setIsInput(false);
		}
		temp.getInterfaceList().getEventInputs().clear();
		temp.getInterfaceList().getEventOutputs().clear();
		temp.getInterfaceList().getInputVars().clear();
		temp.getInterfaceList().getOutputVars().clear();
		temp.getInterfaceList().getEventInputs().addAll(inputEvents);
		temp.getInterfaceList().getEventOutputs().addAll(outputEvents);
		temp.getInterfaceList().getInputVars().addAll(inputVars);
		temp.getInterfaceList().getOutputVars().addAll(outputVars);
		return temp;
	}

	public static AdapterFBType getSocketType(final AdapterType at) {
		return EcoreUtil.copy(at.getAdapterFBType());
	}

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
			return (sourceIsInterface || destinationIsInterface);
		}

		if (s == path) {
			sourceIsInterface = false;
		} else {
			if (s instanceof Demultiplexer) {
				final var connections = s.getInterface().getInputVars().get(0).getInputConnections();
				sourceIsInterface = !connections.isEmpty() && isInterfaceConnection(connections.get(0), s);
			} else if (s instanceof Multiplexer) {
				sourceIsInterface = s.getInterface().getInputVars().stream()
						.anyMatch(v -> !v.getInputConnections().isEmpty())
						&& s.getInterface().getInputVars().stream()
						.allMatch(v -> v.getInputConnections().stream().allMatch(co -> isInterfaceConnection(co, s)));
			}
		}

		if (d == path) {
			destinationIsInterface = false;
		} else {
			if (d instanceof Demultiplexer) {
				destinationIsInterface = d.getInterface().getOutputVars().stream()
						.anyMatch(v -> !v.getOutputConnections().isEmpty())
						&& d.getInterface().getOutputVars().stream()
						.allMatch(v -> v.getOutputConnections().stream().allMatch(co -> isInterfaceConnection(co, d)));
			} else if (d instanceof Multiplexer) {
				final var connections = d.getInterface().getOutputVars().get(0).getOutputConnections();
				destinationIsInterface = !connections.isEmpty() && isInterfaceConnection(connections.get(0), d);
			}
		}

		return (sourceIsInterface || destinationIsInterface);
	}

	public static FBNetwork getFBNetwork(final Connection c) {
		return (FBNetwork) c.eContainer();
	}

	public static void checkifConnectionBroken(final Connection c) {
		if (!c.isResourceConnection()) {
			final Resource sourceRes = (null != c.getSourceElement()) ? c.getSourceElement().getResource() : null;
			final Resource destinationRes = (null != c.getDestinationElement()) ? c.getDestinationElement().getResource()
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

	public static Resource getResourceNamed(final Device d, final String name) {
		for (final Resource res : d.getResource()) {
			if (res.getName().equals(name)) {
				return res;
			}
		}
		return null;
	}

	// *** ECState ***//
	public static boolean isStartState(final ECState ecs) {
		if (null != ecs.eContainer()) {
			return ecs.equals(ecs.getECC().getStart());
		}
		return false;
	}

	// *** ECTransition ***//
	public static String getConditionText(final ECTransition ect) {
		String retVal = ""; //$NON-NLS-1$
		final Event event = ect.getConditionEvent();
		final String expression = ect.getConditionExpression();
		if (event != null) {
			retVal = event.getName();
		}
		if (expression != null) {
			if (expression.equals("1")) { //$NON-NLS-1$
				retVal = expression;
			} else if (!expression.equals("")) { //$NON-NLS-1$
				retVal += "[" + expression + "]"; //$NON-NLS-1$ //$NON-NLS-2$
			}
		}
		return retVal;
	}

	// *** SubApp ***//

	// *** InterfaceList ***
	public static EList<IInterfaceElement> getAllInterfaceElements(final InterfaceList il) {
		final EList<IInterfaceElement> retVal = new BasicEList<>();
		retVal.addAll(il.getEventInputs());
		retVal.addAll(il.getInputVars());
		retVal.addAll(il.getSockets());
		retVal.addAll(il.getEventOutputs());
		retVal.addAll(il.getOutputVars());
		retVal.addAll(il.getPlugs());
		retVal.addAll(il.getErrorMarker());
		return retVal;
	}

	public static Event getEvent(final InterfaceList il, final String name) {
		for (final Event event : il.getEventInputs()) {
			if (event.getName().equals(name)) {
				return event;
			}
		}
		for (final Event event : il.getEventOutputs()) {
			if (event.getName().equals(name)) {
				return event;
			}
		}
		return null;
	}

	public static VarDeclaration getVariable(final InterfaceList il, final String name) {
		for (final VarDeclaration inVar : il.getInputVars()) {
			if (inVar.getName().equals(name)) {
				return inVar;
			}
		}
		for (final VarDeclaration outVar : il.getOutputVars()) {
			if (outVar.getName().equals(name)) {
				return outVar;
			}
		}
		return null;
	}

	public static IInterfaceElement getInterfaceElement(final InterfaceList il, final String name) {
		IInterfaceElement element = il.getEvent(name);
		if (element == null) {
			element = il.getVariable(name);
		}
		if (element == null) {
			element = il.getAdapter(name);
		}

		if (element == null) {
			element = il.getErrorMarker().stream().filter(e -> e.getName().equals(name)).findAny()
					.orElse(null);
		}

		return element;
	}

	public static FBNetworkElement getFBNetworkElement(final InterfaceList il) {
		// an FB should mostly in an FBNetworkElement otherwise it is in CFB interface
		// this is at the same time also a null check
		return (il.eContainer() instanceof FBNetworkElement) ? (FBNetworkElement) il.eContainer() : null;
	}

	public static AdapterDeclaration getAdapter(final InterfaceList il, final String name) {
		for (final AdapterDeclaration adapt : il.getPlugs()) {
			if (adapt.getName().equals(name)) {
				return adapt;
			}
		}
		for (final AdapterDeclaration adapt : il.getSockets()) {
			if (adapt.getName().equals(name)) {
				return adapt;
			}
		}
		return null;
	}

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
		if (connection instanceof EventConnection) {
			fbn.getEventConnections().add((EventConnection) connection);
		}
		if (connection instanceof DataConnection) {
			fbn.getDataConnections().add((DataConnection) connection);
		}
		if (connection instanceof AdapterConnection) {
			fbn.getAdapterConnections().add((AdapterConnection) connection);
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
		final EObject system = EcoreUtil.getRootContainer(fbn);
		return system instanceof AutomationSystem ? (AutomationSystem) system : null;
	}

	public static Application getApplication(final FBNetwork fbn) {
		if (fbn.isApplicationNetwork()) {
			// no null check is need as this is already done in isApplicationNetwork
			return (Application) fbn.eContainer();
		} else if (fbn.isSubApplicationNetwork() && (null != ((SubApp) fbn.eContainer()).getFbNetwork())) {
			return ((SubApp) fbn.eContainer()).getFbNetwork().getApplication();
		}
		return null;
	}

	public static FB getFBNamed(final FBNetwork fbn, final String name) {
		for (final FBNetworkElement element : fbn.getNetworkElements()) {
			if ((element instanceof FB) && (element.getName().equals(name))) {
				return (FB) element;
			}
		}
		return null;
	}

	public static SubApp getSubAppNamed(final FBNetwork fbn, final String name) {
		for (final FBNetworkElement element : fbn.getNetworkElements()) {
			if ((element instanceof SubApp) && element.getName().equals(name)) {
				return (SubApp) element;
			}
		}
		return null;
	}

	public static FBNetworkElement getElementNamed(final FBNetwork fbn, final String name) {
		for (final FBNetworkElement element : fbn.getNetworkElements()) {
			if (element.getName().equals(name)) {
				return element;
			}
		}
		return null;
	}

	// *** AutomationSystem ***//
	public static Device getDeviceNamed(final AutomationSystem as, final String name) {
		if (as.getSystemConfiguration() != null) {
			return as.getSystemConfiguration().getDeviceNamed(name);
		}
		return null;
	}

	public static Application getApplicationNamed(final AutomationSystem as, final String name) {
		for (final Application app : as.getApplication()) {
			if (app.getName().equals(name)) {
				return app;
			}
		}
		return null;
	}

	// *** VarDeclaration ***//
	public static boolean isArray(final VarDeclaration vd) {
		return vd.getArraySize() > 0;
	}

	// *** ConfigurableObject ***//
	public static void setAttribute(final ConfigurableObject object, final String attributeName, final String type,
			final String value, final String comment) {
		Attribute attribute = getAttribute(object, attributeName);
		if (attribute == null) {
			attribute = LibraryElementFactory.eINSTANCE.createAttribute();
			attribute.setName(attributeName);
			attribute.setType(BaseType1.getByName(type));
			object.getAttributes().add(attribute);
		}
		attribute.setValue(value);
		if (null != comment) {
			attribute.setComment(comment);
		}
	}

	public static boolean deleteAttribute(final ConfigurableObject object, final String attributeName) {
		if ((object != null) && (attributeName != null)) {
			final List<Attribute> toDelete = object.getAttributes().stream()
					.filter(attr -> attributeName.equals(attr.getName())).collect(Collectors.toList());
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
		if (null != a) {
			return a.getValue();
		}
		return null;
	}

	public static Attribute getAttribute(final ConfigurableObject object, final String attributeName) {
		if (attributeName == null) {
			return null;
		}
		for (final Attribute attribute : object.getAttributes()) {
			if (attribute.getName().equalsIgnoreCase(attributeName)) {
				return attribute;
			}
		}
		return null;
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
		return (iie.eContainer() instanceof InterfaceList) ? ((InterfaceList) iie.eContainer()).getFBNetworkElement()
				: null;
	}

	// *** SystemConfiguration ***//
	public static AutomationSystem getAutomationSystem(final SystemConfiguration sc) {
		return (AutomationSystem) sc.eContainer();
	}

	public static Segment getSegmentNamed(final SystemConfiguration sc, final String name) {
		for (final Segment segment : sc.getSegments()) {
			if (segment.getName().equals(name)) {
				return segment;
			}
		}
		return null;
	}

	public static Device getDeviceNamed(final SystemConfiguration sc, final String name) {
		for (final Device device : sc.getDevices()) {
			if (device.getName().equals(name)) {
				return device;
			}
		}
		return null;
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
		if (null != tco.getTypeEntry()) {
			return tco.getTypeEntry().getType();
		}
		return null;
	}

	public static TypeLibrary getTypeLibrary(final TypedConfigureableObject tco) {
		if (null != tco.getTypeEntry()) {
			return tco.getTypeEntry().getTypeLibrary();
		}
		return null;
	}

	// *** AdapterFB ***//
	public static boolean isSocket(final AdapterFB afb) {
		return !afb.isPlug();
	}

	public static FBType getType(final AdapterFB afb) {
		FBType retVal = null;
		if ((afb.getTypeEntry() instanceof AdapterTypeEntry) && (null != afb.getAdapterDecl())) {
			if (afb.isPlug()) {
				retVal = ((AdapterTypeEntry) afb.getTypeEntry()).getType().getPlugType();
			} else {
				retVal = ((AdapterTypeEntry) afb.getTypeEntry()).getType().getSocketType();
			}
		}
		return retVal;
	}

	public static boolean isPlug(final AdapterFB afb) {
		return !afb.getAdapterDecl().isIsInput();
	}

	private Annotations() {
		throw new UnsupportedOperationException("The utility class Annotations should not be instatiated"); //$NON-NLS-1$
	}

}
