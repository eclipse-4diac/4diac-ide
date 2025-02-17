/*******************************************************************************
 * Copyright (c) 2017, 2018 fortiss GmbH
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.data;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.util.DeploymentHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

/**
 * Class for storing the information for deploying a resource
 *
 * This is the FBs collected from the resource and the mapped SubApps as well as
 * the connections and the SubApp interface crossing connections
 */
public class ResourceDeploymentData {

	public static class ParameterData {
		private final String value;
		private final VarDeclaration variable;
		private final String prefix;

		public String getValue() {
			return value;
		}

		public VarDeclaration getVar() {
			return variable;
		}

		public String getPrefix() {
			return prefix;
		}

		public ParameterData(final String value, final String prefix, final VarDeclaration variable) {
			this.value = value;
			this.variable = variable;
			this.prefix = prefix;
		}
	}

	private final Resource res;

	private final List<FBDeploymentData> fbs = new ArrayList<>();

	private final List<ConnectionDeploymentData> connections = new ArrayList<>();

	private final List<ParameterData> params = new ArrayList<>();

	public Resource getRes() {
		return res;
	}

	public List<FBDeploymentData> getFbs() {
		return fbs;
	}

	public List<ConnectionDeploymentData> getConnections() {
		return connections;
	}

	public List<ParameterData> getParams() {
		return params;
	}

	public void addFbs(final FBDeploymentData fb) {
		fbs.add(fb);
	}

	public void addConnections(final ConnectionDeploymentData connection) {
		connections.add(connection);
	}

	public void addParameter(final ParameterData param) {
		params.add(param);
	}

	public ResourceDeploymentData(final Resource res) throws DeploymentException {
		this.res = res;
		addFBNetworkElements(new ArrayDeque<>(), res.getFBNetwork(), ""); //$NON-NLS-1$
	}

	private void addFBNetworkElements(final Deque<SubApp> subAppHierarchy, final FBNetwork fbNetwork,
			final String prefix) throws DeploymentException {
		for (final FBNetworkElement fbnElement : fbNetwork.getNetworkElements()) {
			final String prefixToUse = prefix.isEmpty() ? getMappedParentName(fbnElement) : prefix;
			if (fbnElement instanceof FB) {
				fbs.add(new FBDeploymentData(prefixToUse, fbnElement));
			} else if (fbnElement instanceof final SubApp subApp) {
				addSubAppParams(subApp, subAppHierarchy, prefixToUse);
				final FBNetwork subAppInternalNetwork = getFBNetworkForSubApp(subApp);
				if (null != subAppInternalNetwork) { // TODO somehow inform the user that we could not get the internals
					// of the subapp and therefore are not deploying its internals
					subAppHierarchy.addLast((SubApp) fbnElement);
					addFBNetworkElements(subAppHierarchy, subAppInternalNetwork,
							prefixToUse + fbnElement.getName() + "."); //$NON-NLS-1$
					subAppHierarchy.removeLast();
				}
			}
		}

		Stream.concat(Stream.concat(fbNetwork.getEventConnections().stream(), fbNetwork.getDataConnections().stream()),
				fbNetwork.getAdapterConnections().stream()).forEach(con -> addConnection(subAppHierarchy, con, prefix));
	}

	public static String getMappedParentName(final FBNetworkElement fbnElement) {
		if (fbnElement.isMapped()
				&& fbnElement.getOpposite().eContainer().eContainer() instanceof final INamedElement namedEl) {
			return namedEl.getQualifiedName() + "."; //$NON-NLS-1$
		}
		return ""; //$NON-NLS-1$
	}

	private void addSubAppParams(final SubApp subApp, final Deque<SubApp> subAppHierarchy, final String prefix)
			throws DeploymentException {
		// follow the connection through the subapp hierachy until there is a loose one
		for (final VarDeclaration dataInput : subApp.getInterface().getInputVars()) {
			final Function<VarDeclaration, String> dataInputValue = DeploymentHelper
					.getVariableValueRetargetable(dataInput, subApp);
			if (dataInputValue != null && useSubAppInitialValue(subAppHierarchy, subApp, dataInput)) {
				for (final ConDeploymentDest destData : getSubappInterfaceconnections(subAppHierarchy, prefix,
						dataInput)) {
					final VarDeclaration destVar = (VarDeclaration) destData.destination;
					final String destDataValue = dataInputValue.apply(destVar);
					params.add(new ParameterData(destDataValue, destData.prefix, destVar));
				}
			}
		}
	}

	private static boolean useSubAppInitialValue(final Deque<SubApp> subAppHierachy, final SubApp subApp,
			final VarDeclaration varDec) {
		final IInterfaceElement ie = subApp.getInterfaceElement(varDec.getName());
		if (!hasInputConnections(ie)) {
			return true;
		}
		IInterfaceElement source = getSourcePin(ie);
		while (hasInputConnections(source)) {
			if (!source.isIsInput()) {
				return false;
			}
			final Optional<SubApp> sA = getSubAppFromInterfacePin(subAppHierachy, source);
			if (sA.isPresent()) {
				if (source.getInputConnections().isEmpty() && (source instanceof final VarDeclaration varDecl)) {
					return !DeploymentHelper.hasTypeOrInstanceInitialValue(sA.get(), varDecl);
				}

				// toplevel subapp and already checked for connections
				if (isToplevelSubapp(sA.get())) {
					break;
				}
			}
			source = getSourcePin(source);
		}
		return false;

	}

	private static Optional<SubApp> getSubAppFromInterfacePin(final Deque<SubApp> subappHierachy,
			final IInterfaceElement ie) {
		return subappHierachy.stream().filter(sub -> sub.getInterface().getInput(ie.getName()) != null).findFirst();
	}

	private static IInterfaceElement getSourcePin(final IInterfaceElement pin) {
		final EList<Connection> conList = pin.getInputConnections();
		if (conList.isEmpty()) {
			return null;
		}
		return conList.getFirst().getSource();
	}

	private static boolean isToplevelSubapp(final SubApp subApp) {
		return subApp.eContainer() != null && subApp.eContainer().eContainer() != null
				&& subApp.eContainer().eContainer() instanceof Resource;
	}

	private static boolean hasInputConnections(final IInterfaceElement pin) {
		return pin != null && pin.isIsInput() && !pin.getInputConnections().isEmpty();
	}

	private static FBNetwork getFBNetworkForSubApp(final SubApp subApp) {
		FBNetwork retVal = subApp.getSubAppNetwork();
		if (null == retVal) {
			if (subApp.isTyped()) {
				// we have a typed subapp
				retVal = subApp.getType().getFBNetwork();
			} else if (subApp.getOpposite() instanceof final SubApp oppositeSubApp) {
				// we should have a mapped subapp. Then the network is in the opposite subapp
				retVal = oppositeSubApp.getSubAppNetwork();
			}
		}
		return retVal;
	}

	private static class ConDeploymentDest {
		private final String prefix;
		private final IInterfaceElement destination;

		public ConDeploymentDest(final String prefix, final IInterfaceElement destination) {
			this.prefix = prefix;
			this.destination = destination;
		}

	}

	private void addConnection(final Deque<SubApp> subAppHierarchy, final Connection con, final String prefix) {
		// Only handle the connection if it is no subapp, typed subapp originated or
		// resource type connection
		if (null != con.getSourceElement() && !(con.getSourceElement() instanceof SubApp)) {
			final String sourcePrefix = prefix.isEmpty() ? getMappedParentName(con.getSourceElement()) : prefix;
			for (final ConDeploymentDest destData : getConnectionEndPoint(subAppHierarchy, prefix,
					con.getDestination())) {
				connections.add(new ConnectionDeploymentData(sourcePrefix, con.getSource(), destData.prefix,
						destData.destination));
			}
		}
	}

	private List<ConDeploymentDest> getConnectionEndPoint(final Deque<SubApp> subAppHierarchy, final String prefix,
			final IInterfaceElement destination) {
		final ArrayList<ConDeploymentDest> retVal = new ArrayList<>();
		final String destPrefix = prefix.isEmpty() ? getMappedParentName(destination.getFBNetworkElement()) : prefix;
		if (null != destination.getFBNetworkElement() && !(destination.getFBNetworkElement() instanceof SubApp)) {
			// we reached an FB endpoint return it
			retVal.add(new ConDeploymentDest(destPrefix, destination));
		} else {
			retVal.addAll(getSubappInterfaceconnections(subAppHierarchy, destPrefix, destination));
		}
		return retVal;
	}

	private List<ConDeploymentDest> getSubappInterfaceconnections(final Deque<SubApp> subAppHierarchy,
			final String prefix, final IInterfaceElement destination) {
		final ArrayList<ConDeploymentDest> retVal = new ArrayList<>();
		if (destination.isIsInput()) {
			// we are entering a subapplication

			final String newPrefix = (prefix.isEmpty() ? // if the prefix is empty we need to add mapping info
					getMappedParentName(destination.getFBNetworkElement()) : prefix)
					+ destination.getFBNetworkElement().getName() + "."; //$NON-NLS-1$
			subAppHierarchy.addLast((SubApp) destination.getFBNetworkElement());
			final IInterfaceElement internalElement = getSubAppInteralElement(destination);
			if (null != internalElement) {
				for (final Connection con : internalElement.getOutputConnections()) {
					retVal.addAll(getConnectionEndPoint(subAppHierarchy, newPrefix, con.getDestination()));
				}
			}
			subAppHierarchy.removeLast();
		} else {
			// we are leaving a subapp
			final SubApp currentSubApp = subAppHierarchy.removeLast();
			final String newPrefix = subAppHierarchy.isEmpty() ? "" : removeLastEntry(prefix, currentSubApp.getName()); //$NON-NLS-1$
			final IInterfaceElement internalElement = currentSubApp.getInterfaceElement(destination.getName());
			for (final Connection con : internalElement.getOutputConnections()) {
				retVal.addAll(getConnectionEndPoint(subAppHierarchy, newPrefix, con.getDestination()));
			}
			subAppHierarchy.addLast(currentSubApp);
		}
		return retVal;
	}

	private static IInterfaceElement getSubAppInteralElement(final IInterfaceElement destination) {
		final SubApp subApp = (SubApp) destination.getFBNetworkElement();
		if (null != subApp.getSubAppNetwork()) {
			return destination;
		}

		InterfaceList interfaceList = null;
		if (subApp.isTyped()) {
			// we have a typed subapp
			interfaceList = subApp.getType().getInterfaceList();
		} else if (subApp.getOpposite() instanceof final SubApp subAppOpposite) {
			interfaceList = subAppOpposite.getInterface();
		}
		if (null != interfaceList) {
			return interfaceList.getInterfaceElement(destination.getName());
		}
		return null;
	}

	private static String removeLastEntry(final String prefix, final String lastEntryName) {
		final int newEnd = prefix.length() - lastEntryName.length() - 1;
		if (newEnd > 0) {
			return prefix.substring(0, newEnd);
		}
		return ""; //$NON-NLS-1$
	}

}
