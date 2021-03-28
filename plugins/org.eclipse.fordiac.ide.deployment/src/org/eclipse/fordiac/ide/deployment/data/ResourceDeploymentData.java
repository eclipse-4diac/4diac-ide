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

import org.eclipse.fordiac.ide.deployment.util.DeploymentHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

/**
 * Class for storing the information for deplyoing a resources
 *
 * This is the FBs collected from the resource and the mapped subapps as well as
 * the connections and the subapp interface crossing connections
 */
public class ResourceDeploymentData {

	public static class ParameterData {
		private final String value;
		private final VarDeclaration var;
		private final String prefix;

		public String getValue() {
			return value;
		}

		public VarDeclaration getVar() {
			return var;
		}

		public String getPrefix() {
			return prefix;
		}

		public ParameterData(final String value, final String prefix, final VarDeclaration var) {
			super();
			this.value = value;
			this.var = var;
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

	public ResourceDeploymentData(final Resource res) {
		this.res = res;
		addFBNetworkElements(new ArrayDeque<SubApp>(), res.getFBNetwork(), ""); //$NON-NLS-1$
	}

	private void addFBNetworkElements(final Deque<SubApp> subAppHierarchy, final FBNetwork fbNetwork, final String prefix) {
		for (final FBNetworkElement fbnElement : fbNetwork.getNetworkElements()) {
			if (fbnElement instanceof FB) {
				fbs.add(new FBDeploymentData(prefix, (FB) fbnElement));
			} else if (fbnElement instanceof SubApp) {
				addSubAppParams((SubApp) fbnElement, subAppHierarchy, prefix);
				final FBNetwork subAppInternalNetwork = getFBNetworkForSubApp((SubApp) fbnElement);
				if (null != subAppInternalNetwork) { // TODO somehow inform the user that we could not get the internals
					// of the subapp and therefore are not deploying its internals
					subAppHierarchy.addLast((SubApp) fbnElement);
					addFBNetworkElements(subAppHierarchy, subAppInternalNetwork, prefix + fbnElement.getName() + "."); //$NON-NLS-1$
					subAppHierarchy.removeLast();
				}
			}
		}
		for (final Connection con : fbNetwork.getEventConnections()) {
			addConnection(subAppHierarchy, con, prefix);
		}
		for (final Connection con : fbNetwork.getDataConnections()) {
			addConnection(subAppHierarchy, con, prefix);
		}
		for (final Connection con : fbNetwork.getAdapterConnections()) {
			addConnection(subAppHierarchy, con, prefix);
		}

	}

	private void addSubAppParams(final SubApp subApp, final Deque<SubApp> subAppHierarchy, final String prefix) {
		for (final VarDeclaration dataInput : subApp.getInterface().getInputVars()) {
			final String val = DeploymentHelper.getVariableValue(dataInput, res.getAutomationSystem());
			if (null != val) {
				for (final ConDeploymentDest destData : getSubappInterfaceconnections(subAppHierarchy, prefix, dataInput)) {
					params.add(new ParameterData(val, destData.prefix, (VarDeclaration) destData.destination));
				}
			}
		}

	}

	private static FBNetwork getFBNetworkForSubApp(final SubApp subApp) {
		FBNetwork retVal = subApp.getSubAppNetwork();
		if (null == retVal) {
			if (subApp.isTyped()) {
				// we have a typed subapp
				retVal = subApp.getType().getFBNetwork();
			} else if (null != subApp.getOpposite()) {
				// we should have a mapped subapp. Then the network is in the opposite subapp
				retVal = ((SubApp) subApp.getOpposite()).getSubAppNetwork();
			}
		}
		return retVal;
	}

	private static class ConDeploymentDest {
		private final String prefix;
		private final IInterfaceElement destination;

		public ConDeploymentDest(final String prefix, final IInterfaceElement destination) {
			super();
			this.prefix = prefix;
			this.destination = destination;
		}

	}

	private void addConnection(final Deque<SubApp> subAppHierarchy, final Connection con, final String prefix) {
		// Only handle the conneciton if it is no subapp, typed subapp originated or
		// resourcetype connection
		if (null != con.getSourceElement() && !(con.getSourceElement() instanceof SubApp)
				&& !con.isResTypeConnection()) {
			for (final ConDeploymentDest destData : getConnectionEndPoint(subAppHierarchy, prefix, con.getDestination())) {
				connections.add(
						new ConnectionDeploymentData(prefix, con.getSource(), destData.prefix, destData.destination));
			}
		}
	}

	private List<ConDeploymentDest> getConnectionEndPoint(final Deque<SubApp> subAppHierarchy, final String prefix,
			final IInterfaceElement destination) {
		final ArrayList<ConDeploymentDest> retVal = new ArrayList<>();
		if (null != destination.getFBNetworkElement() && !(destination.getFBNetworkElement() instanceof SubApp)) {
			// we reached an FB endpoint return it
			retVal.add(new ConDeploymentDest(prefix, destination));
		} else {
			retVal.addAll(getSubappInterfaceconnections(subAppHierarchy, prefix, destination));
		}
		return retVal;
	}

	private List<ConDeploymentDest> getSubappInterfaceconnections(final Deque<SubApp> subAppHierarchy, final String prefix,
			final IInterfaceElement destination) {
		final ArrayList<ConDeploymentDest> retVal = new ArrayList<>();
		if (destination.isIsInput()) {
			// we are entering a subapplication
			final String newPrefix = prefix + destination.getFBNetworkElement().getName() + "."; //$NON-NLS-1$
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
			final String newPrefix = removeLastEntry(prefix);
			final SubApp currentSubApp = subAppHierarchy.removeLast();
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
		} else if (null != subApp.getOpposite()) {
			interfaceList = ((SubApp) subApp.getOpposite()).getInterface();
		}
		if (null != interfaceList) {
			return interfaceList.getInterfaceElement(destination.getName());
		}
		return null;
	}

	private static String removeLastEntry(final String prefix) {
		final int index = prefix.lastIndexOf('.', prefix.length() - 2);
		if (-1 != index) {
			return prefix.substring(0, index + 1);
		}
		return ""; //$NON-NLS-1$
	}

}
