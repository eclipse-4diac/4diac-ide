/*******************************************************************************
 * Copyright (c) 2019, 2024 Johannes Kepler University Linz
 *                          Primetals Technologies Austria GmbH
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *   Bianca Wiesmayr - added positioning calculations
 *   Daniel Lindhuber - added recursive type insertion check
 *   Martin Jobst - fix copying of connections with var-in-out pins
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.helpers;

import java.text.MessageFormat;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Objects;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.annotations.MappingAnnotations;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterConnection;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.CFBInstance;
import org.eclipse.fordiac.ide.model.libraryElement.CommunicationChannel;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.DataConnection;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.EventConnection;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.MappingTarget;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.ErrorTypeEntry;
import org.eclipse.fordiac.ide.ui.errormessages.ErrorMessenger;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

public final class FBNetworkHelper {

	/**
	 * Take the src FBNetwork and copy it into a new network.
	 *
	 * @param srcNetwork    the FBNetwork to copy
	 * @param destInterface if not null the interface of the component the new
	 *                      FBNetwork should be contained in
	 * @return the copied FBNetwork
	 */
	public static FBNetwork copyFBNetWork(final FBNetwork srcNetwork, final InterfaceList destInterface) {
		final FBNetwork dstNetwork = LibraryElementFactory.eINSTANCE.createFBNetwork();
		dstNetwork.getNetworkElements().addAll(EcoreUtil.copyAll(srcNetwork.getNetworkElements()));
		checkForAdapterFBs(dstNetwork, destInterface);
		createConnections(srcNetwork, dstNetwork, destInterface);
		return dstNetwork;
	}

	/**
	 * Take the src FBNetwork and copy it into a new network with the members of the
	 * srce network as resource type fbs.
	 *
	 * @param srcNetwork    the FBNetwork to copy
	 * @param destInterface if not null the interface of the component the new
	 *                      FBNetwork should be contained in
	 * @return the copied FBNetwork
	 */
	public static FBNetwork createResourceFBNetwork(final FBNetwork resourceTypeNetwork,
			final InterfaceList destInterface) {
		final FBNetwork dstNetwork = LibraryElementFactory.eINSTANCE.createFBNetwork();
		createResourceTypeFBs(resourceTypeNetwork.getNetworkElements(), dstNetwork);
		checkForAdapterFBs(dstNetwork, destInterface);
		createConnections(resourceTypeNetwork, dstNetwork, destInterface);
		return dstNetwork;
	}

	private static void createResourceTypeFBs(final EList<FBNetworkElement> networkElements,
			final FBNetwork dstNetwork) {
		networkElements.forEach(fb -> createResourceTypeFB(fb, dstNetwork));
	}

	private static void createResourceTypeFB(final FBNetworkElement srcFb, final FBNetwork dstNetwork) {
		final FB copy = LibraryElementFactory.eINSTANCE.createResourceTypeFB();
		dstNetwork.getNetworkElements().add(copy);
		copy.setTypeEntry(srcFb.getTypeEntry());
		copy.setName(srcFb.getName()); // name should be last so that checks
		// are working correctly
		final InterfaceList interfaceList = InterfaceListCopier.copy(srcFb.getInterface(), true, false);
		copy.setInterface(interfaceList);
		copy.setPosition(EcoreUtil.copy(srcFb.getPosition()));
	}

	private static void checkForAdapterFBs(final FBNetwork dstNetwork, final InterfaceList destInterface) {
		for (final FBNetworkElement elem : dstNetwork.getNetworkElements()) {
			if (elem instanceof final AdapterFB adapterfb) {
				final AdapterDeclaration adapter = destInterface.getAdapter(elem.getName());
				if (null != adapter) {
					adapterfb.setAdapterDecl(adapter);
					adapter.setAdapterNetworkFB(adapterfb);
				}
			}
		}
	}

	private static void createConnections(final FBNetwork srcNetwork, final FBNetwork dstNetwork,
			final InterfaceList destInterface) {
		for (final Connection connection : srcNetwork.getEventConnections()) {
			dstNetwork.getEventConnections()
					.add((EventConnection) createConnection(srcNetwork, destInterface, dstNetwork, connection));
		}

		for (final Connection connection : srcNetwork.getDataConnections()) {
			dstNetwork.getDataConnections()
					.add((DataConnection) createConnection(srcNetwork, destInterface, dstNetwork, connection));
		}

		for (final Connection connection : srcNetwork.getAdapterConnections()) {
			dstNetwork.getAdapterConnections()
					.add((AdapterConnection) createConnection(srcNetwork, destInterface, dstNetwork, connection));
		}
	}

	private static Connection createConnection(final FBNetwork srcNetwork, final InterfaceList destInterface,
			final FBNetwork dstNetwork, final Connection connection) {
		final Connection newConn = EcoreUtil.copy(connection);
		newConn.setSource(getInterfaceElement(connection.getSource(), destInterface, dstNetwork, srcNetwork));
		newConn.setDestination(getInterfaceElement(connection.getDestination(), destInterface, dstNetwork, srcNetwork));
		return newConn;
	}

	private static IInterfaceElement getInterfaceElement(final IInterfaceElement ie, final InterfaceList destInterface,
			final FBNetwork dstNetwork, final FBNetwork srcNetwork) {
		final IInterfaceElement interfaceElement;
		if (ie.getFBNetworkElement() == null || srcNetwork != ie.getFBNetworkElement().getFbNetwork()) {
			interfaceElement = destInterface.getInterfaceElement(ie.getName());
		} else {
			final FBNetworkElement element = dstNetwork.getElementNamed(ie.getFBNetworkElement().getName());
			if (null == element) {
				return null;
			}
			interfaceElement = element.getInterfaceElement(ie.getName());
		}
		if (interfaceElement instanceof final VarDeclaration varDeclaration && varDeclaration.isInOutVar()
				&& varDeclaration.isIsInput() != ie.isIsInput()) {
			return varDeclaration.getInOutVarOpposite();
		}
		return interfaceElement;
	}

	/**
	 * methods for updating position of FBNetwork after creating/flattening
	 * subapp/...
	 */

	public static Position getTopLeftCornerOfFBNetwork(final List<?> selection) {
		Assert.isNotNull(selection);
		double x = Double.MAX_VALUE;
		double y = Double.MAX_VALUE;
		for (final Object sel : selection) {
			final Object fb = (sel instanceof final EditPart ep) ? ep.getModel() : sel;
			if (fb instanceof final FBNetworkElement fbnEL) {
				final Position pos = fbnEL.getPosition();
				x = Math.min(x, pos.getX());
				y = Math.min(y, pos.getY());
			}
		}
		final Position pos = LibraryElementFactory.eINSTANCE.createPosition();
		pos.setX(x);
		pos.setY(y);
		return pos;
	}

	public static void moveFBNetworkByOffset(final Iterable<FBNetworkElement> fbNetwork, final double xOffset,
			final double yOffset) {
		for (final FBNetworkElement el : fbNetwork) {
			final Position pos = LibraryElementFactory.eINSTANCE.createPosition();
			pos.setX(el.getPosition().getX() + xOffset);
			pos.setY(el.getPosition().getY() + yOffset);
			el.setPosition(pos);
		}
	}

	public static Position removeXYOffsetForFBNetwork(final List<FBNetworkElement> fbNetwork) {
		final Position offset = getTopLeftCornerOfFBNetwork(fbNetwork);
		moveFBNetworkByOffset(fbNetwork, -offset.getX(), -offset.getY());
		return offset;
	}

	public static void moveFBNetworkToDestination(final List<FBNetworkElement> fbnetwork, final Position destination) {
		final Position current = getTopLeftCornerOfFBNetwork(fbnetwork);
		moveFBNetworkByOffset(fbnetwork, destination.getX() - current.getX(), destination.getY() - current.getY());
	}

	public static void moveFBNetworkByOffset(final List<FBNetworkElement> fbNetwork, final Position offset) {
		moveFBNetworkByOffset(fbNetwork, offset.getX(), offset.getY());
	}

	public static boolean targetSubappIsInSameFbNetwork(final List<FBNetworkElement> elements,
			final SubApp targetSubApp) {
		for (final FBNetworkElement block : elements) {
			if (!block.getFbNetwork().getNetworkElements().contains(targetSubApp)) {
				return false;
			}
		}
		return true;
	}

	public static boolean isTypeInsertionSafe(final FBType type, final EObject element) {
		if ((type == null) || (element == null)) {
			return true;
		}
		final FBType editorType = getRootType(element);
		if (editorType != null) {
			if (type.equals(editorType)) {
				ErrorMessenger
						.popUpErrorMessage(MessageFormat.format(Messages.Error_SelfInsertion, editorType.getName()));
				return false;
			}
			if (containsType(editorType, getChildFBNElements(type))) {
				ErrorMessenger.popUpErrorMessage(
						MessageFormat.format(Messages.Error_RecursiveType, type.getName(), editorType.getName()));
				return false;
			}
		}
		return true;
	}

	public static void selectElements(final List<FBNetworkElement> elements) {
		final IWorkbenchPart part = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getActivePart();
		final GraphicalViewer viewer = part.getAdapter(GraphicalViewer.class);
		if (viewer != null) {
			final List<EditPart> eps = elements.stream().map(viewer::getEditPartForModel).filter(Objects::nonNull)
					.toList();
			if (eps != null) {
				viewer.setSelection(new StructuredSelection(eps));
			}
		}
	}

	private static EList<? extends FBNetworkElement> getChildFBNElements(final FBNetworkElement networkElem) {
		if (networkElem instanceof final SubApp subapp) {
			if (subapp.isTyped()) {
				return subapp.getType().getFBNetwork().getNetworkElements();
			}
			return subapp.getSubAppNetwork().getNetworkElements();
		}
		final FBType type = networkElem.getType();
		if (type != null) {
			return getChildFBNElements(type);
		}
		return new BasicEList<>();
	}

	private static EList<? extends FBNetworkElement> getChildFBNElements(final FBType type) {
		if (!(type.getTypeEntry() instanceof ErrorTypeEntry)) {
			if (type instanceof final BaseFBType baseFBType) { // basic and simple fb type
				return baseFBType.getInternalFbs();
			}
			if (type instanceof final CompositeFBType cFBType) { // subapp and composite fb type
				return cFBType.getFBNetwork().getNetworkElements();
			}
		}

		return new BasicEList<>();
	}

	public static FBType getRootType(final EObject element) {
		final EObject root = EcoreUtil.getRootContainer(element);
		if (root instanceof final FBType fbType) {
			return fbType;
		}
		return null;
	}

	private static boolean containsType(final FBType editorType,
			final EList<? extends FBNetworkElement> networkElementList) {
		for (final FBNetworkElement elem : networkElementList) {
			if (editorType.equals(elem.getType()) || containsType(editorType, getChildFBNElements(elem))) {
				return true;
			}
		}
		return false;
	}

	private FBNetworkHelper() {
		throw new IllegalStateException("FBNetworkHelper is a utility class that can not be instantiated"); //$NON-NLS-1$
	}

	public static void loadSubappNetwork(final FBNetworkElement network) {
		if (network instanceof final SubApp subApp) {
			subApp.loadSubAppNetwork();
			parseSubNetworks(subApp.getSubAppNetwork().getNetworkElements());
		} else if (network instanceof final CFBInstance cFB) {
			cFB.loadCFBNetwork();
			parseSubNetworks(cFB.getCfbNetwork().getNetworkElements());
		}
	}

	private static void parseSubNetworks(final List<FBNetworkElement> networkElements) {
		for (final FBNetworkElement fbe : networkElements) {
			if (hasNetwork(fbe)) {
				loadSubappNetwork(fbe);
			}
		}
	}

	private static boolean hasNetwork(final FBNetworkElement networkElement) {
		return ((networkElement instanceof SubApp) || (networkElement instanceof CFBInstance));
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
	public static String getFullHierarchicalName(final FBNetworkElement fbNetworkElement) {
		final Deque<String> names = new ArrayDeque<>();

		if (null != fbNetworkElement) {
			names.addFirst(fbNetworkElement.getName());
			EObject container = fbNetworkElement;
			do {
				final FBNetworkElement runner = (FBNetworkElement) container;
				if (isMappedCommunicationFb(runner)) {
					names.addFirst("."); //$NON-NLS-1$
					names.addFirst(MappingAnnotations.getHierarchicalName(runner));
					break;
				}
				final FBNetwork network = runner.getFbNetwork();
				if (network != null) {
					container = network.eContainer();
				} else {
					container = runner.eContainer(); // BaseFB case
				}
				if (!(container instanceof INamedElement)) {
					break;
				}
				if (container instanceof final MappingTarget mappingtarget) {
					names.addFirst("."); //$NON-NLS-1$
					names.addFirst(MappingAnnotations.getHierarchicalName(mappingtarget));
					break;
				}
				names.addFirst("."); //$NON-NLS-1$
				names.addFirst(((INamedElement) container).getName());
			} while (container instanceof FBNetworkElement); // we are still in a subapp, try to find the resource or
			// application as stop point

			final StringBuilder fullName = new StringBuilder();
			for (final String string : names) {
				fullName.append(string);
			}
			return fullName.toString();
		}
		return null;
	}

	protected static boolean isMappedCommunicationFb(final FBNetworkElement runner) {
		return (runner instanceof CommunicationChannel) && (runner.getFbNetwork() == null);
	}

	/**
	 * Get the model from the hierarchical path
	 *
	 * @param fullHierarchicalName the complete path of the element separated by '.'
	 * @param system               the {@link AutomationSystem} in which to find the
	 *                             item in
	 * @return the model as an {@link EObject} (can also return the
	 *         {@link Application} if the path does not include any other elements)
	 */
	public static EObject getModelFromHierarchicalName(final String fullHierarchicalName,
			final AutomationSystem system) {
		final String[] path = fullHierarchicalName.split("\\."); //$NON-NLS-1$
		EObject retVal = system.getApplicationNamed(path[0]);
		if (null != retVal) {
			if (path.length > 1) {
				// we are within a subapplication in the application
				retVal = parseSubappPath(((Application) retVal).getFBNetwork(),
						Arrays.copyOfRange(path, 1, path.length));
			}
		} else if (path.length > 2) {
			// we need to have at least a device and a resource in the path
			retVal = system.getDeviceNamed(path[0]);
			if (null != retVal) {
				retVal = ((Device) retVal).getResourceNamed(path[1]);
				if ((null != retVal) && (path.length > 2)) {
					// we are within a subapplication in the resource
					retVal = parseSubappPath(((Resource) retVal).getFBNetwork(),
							Arrays.copyOfRange(path, 2, path.length));
				}
			}
		}
		return retVal;
	}

	private static EObject parseSubappPath(FBNetwork network, final String[] path) {
		EObject retVal = null;
		for (final String element : path) {
			retVal = network.getElementNamed(element);
			if (retVal instanceof final SubApp subApp) {
				network = subApp.loadSubAppNetwork();
			} else if (retVal instanceof final CFBInstance cFB) {
				network = cFB.loadCFBNetwork();
			} else {
				return null;
			}
			if (null == network) {
				// we couldn't load the network, memento seems to be broken
				return null;
			}
		}
		return retVal;
	}
}
