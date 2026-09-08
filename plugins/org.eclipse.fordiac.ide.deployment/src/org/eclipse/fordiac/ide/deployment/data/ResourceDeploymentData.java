/*******************************************************************************
 * Copyright (c) 2017, 2025 fortiss GmbH, Martin Erich Jobst
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *   Martin Jobst - rework initial value and connection handling
 *                - add connection source suffix for delegate connections
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.data;

import java.text.MessageFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.SequencedMap;
import java.util.SequencedSet;

import org.eclipse.fordiac.ide.deployment.Messages;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.util.DeploymentHelper;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableFB;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.ContainerVarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.libraryElement.TypedSubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarConfigInstance;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;

/**
 * Class for storing the information for deploying a resource
 *
 * This is the FBs collected from the resource and the mapped SubApps as well as
 * the connections and the SubApp interface crossing connections
 */
public class ResourceDeploymentData {

	private final Resource res;

	private final List<FBDeploymentData> fbs = new ArrayList<>();

	private final List<ConnectionDeploymentData> connections = new ArrayList<>();

	private final SequencedMap<String, String> params = new LinkedHashMap<>();

	private final SequencedSet<FBTypeEntry> fbTypes = new LinkedHashSet<>();

	private final SequencedSet<DataTypeEntry> dataTypes = new LinkedHashSet<>();

	private final Deque<SubApp> subAppHierarchy = new ArrayDeque<>();
	private final Map<String, VarConfigInstance> varConfigParams = new HashMap<>();
	private final StringBuilder prefix = new StringBuilder();

	public Resource getRes() {
		return res;
	}

	public List<FBDeploymentData> getFbs() {
		return fbs;
	}

	public List<ConnectionDeploymentData> getConnections() {
		return connections;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public SequencedSet<FBTypeEntry> getFbTypes() {
		return fbTypes;
	}

	public SequencedSet<DataTypeEntry> getDataTypes() {
		return dataTypes;
	}

	public void addFbs(final FBDeploymentData fb) {
		fbs.add(fb);
	}

	public void addConnections(final ConnectionDeploymentData connection) {
		connections.add(connection);
	}

	public void addParameter(final String qualifiedName, final String value) {
		params.put(qualifiedName, value);
	}

	public ResourceDeploymentData(final Resource res) throws DeploymentException {
		this.res = res;
		addFBNetworkElements(res.getFBNetwork());
		if (!subAppHierarchy.isEmpty() || !prefix.isEmpty()) {
			throw new IllegalStateException("subAppHierarchy and prefix must both be empty"); //$NON-NLS-1$
		}
	}

	private void addFBNetworkElements(final FBNetwork fbNetwork) throws DeploymentException {
		for (final FBNetworkElement fbnElement : fbNetwork.getNetworkElements()) {
			if (fbnElement instanceof final FB fb) {
				addFB(fb);
			} else if (fbnElement instanceof final SubApp subApp) {
				enterSubApp(subApp);
				final FBNetwork subAppInternalNetwork = getFBNetworkForSubApp(subApp);
				if (subAppInternalNetwork == null) {
					throw new DeploymentException(
							MessageFormat.format(Messages.ResourceDeploymentData_MissingSubAppNetwork, prefix));
				}
				addFBNetworkElements(subAppInternalNetwork);
				leaveSubApp();
			}
		}
	}

	private void addFB(final FB fb) throws DeploymentException {
		fbs.add(new FBDeploymentData(ensurePrefix(fb), fb));
		if (fb.getTypeEntry() instanceof final FBTypeEntry entry) {
			fbTypes.add(entry);
		}
		if (fb instanceof final ConfigurableFB configurableFB && configurableFB.getDataType() != null
				&& configurableFB.getDataType().getTypeEntry() instanceof final DataTypeEntry entry) {
			dataTypes.add(entry);
		}
		for (final VarDeclaration inputVar : fb.getInterface().getInputVars()) {
			addInputParam(inputVar, fb);
		}
		for (final VarDeclaration inoutVar : fb.getInterface().getInOutVars()) {
			addInputParam(inoutVar, fb);
		}
		fb.getInterface().getAllInputs().forEach(this::addInputConnections);
	}

	private void addInputParam(final VarDeclaration input, final FB fb) throws DeploymentException {
		final String value = findInitialValue(input);
		if (value != null) {
			params.put(ensurePrefix(fb) + fb.getName() + "." + input.getRelativeName(fb), value); //$NON-NLS-1$
		}
		if (input instanceof final ContainerVarDeclaration container) {
			for (final VarDeclaration member : container.getCachedMembers()) {
				addInputParam(member, fb);
			}
		}
	}

	private String findInitialValue(final VarDeclaration input) throws DeploymentException {
		boolean negate = false;
		VarDeclaration result = input;
		int resultPrefixLength = prefix.length();
		SubApp subApp;
		final Iterator<SubApp> subAppIterator = subAppHierarchy.descendingIterator();
		while (!result.getInputConnections().isEmpty()
				&& result.getInputConnections().getFirst().getSource() instanceof final VarDeclaration source
				&& source.isIsInput() // source is a (subapp) input
				// get the external source of the subapp instance
				&& subAppIterator.hasNext() && getSubAppExternalElement(source,
						subApp = subAppIterator.next()) instanceof final VarDeclaration externalSource) {
			negate ^= result.getInputConnections().getFirst().isNegated();
			result = externalSource;
			resultPrefixLength -= subApp.getName().length() + 1;
		}
		if (!subAppIterator.hasNext()) {
			resultPrefixLength = 0;
		}
		result = findVarConfigParam(result, prefix.substring(0, resultPrefixLength)).orElse(result);
		return DeploymentHelper.getVariableValue(result, input, negate);
	}

	private Optional<VarDeclaration> findVarConfigParam(final VarDeclaration input, final String inputPrefix) {
		final BlockFBNetworkElement fb = input.getBlockFBNetworkElement();
		if (fb == null || inputPrefix.isEmpty()) {
			return Optional.empty();
		}
		final String resultQualifiedName = inputPrefix + fb.getName() + "." + input.getName(); //$NON-NLS-1$
		return Optional.ofNullable(varConfigParams.get(resultQualifiedName));
	}

	private void addInputConnections(final IInterfaceElement input) {
		final String inputPrefix = ensurePrefix(input.getBlockFBNetworkElement());
		for (final ConnectionDeploymentSource sourceData : findSourceEndPoints(input, new StringBuilder(),
				new ArrayList<>())) {
			connections.add(sourceData.toConnectionData(inputPrefix, input));
		}
	}

	private List<ConnectionDeploymentSource> findSourceEndPoints(final IInterfaceElement destination,
			final StringBuilder suffix, final List<ConnectionDeploymentSource> result) {
		for (final Connection con : destination.getInputConnections()) {
			DeploymentHelper.addSourceSuffix(suffix, con);
			findConnectionEndPoints(con.getSource(), suffix, result);
			DeploymentHelper.removeSourceSuffix(suffix, con);
		}
		return result;
	}

	private void findConnectionEndPoints(final IInterfaceElement source, final StringBuilder suffix,
			final List<ConnectionDeploymentSource> result) {
		if (source.isIsInput()) { // source is a (subapp) input
			final SubApp subApp = leaveSubApp();
			final IInterfaceElement externalElement = getSubAppExternalElement(source, subApp);
			if (externalElement != null) {
				findSourceEndPoints(externalElement, suffix, result);
			}
			enterSubApp(subApp);
		} else if (source.getBlockFBNetworkElement() instanceof final SubApp subApp) { // source is a subapp output
			enterSubApp(subApp);
			final IInterfaceElement internalElement = getSubAppInternalElement(source, subApp);
			if (internalElement != null) {
				findSourceEndPoints(internalElement, suffix, result);
			}
			leaveSubApp();
		} else { // source is a regular FB output
			result.add(new ConnectionDeploymentSource(ensurePrefix(source.getBlockFBNetworkElement()),
					suffix.toString(), source));
		}
	}

	private void enterSubApp(final SubApp subApp) {
		subAppHierarchy.addLast(subApp);
		if (prefix.isEmpty()) { // if the prefix is empty we need to add mapping info
			prefix.append(getMappedParentName(subApp));
		}
		prefix.append(subApp.getName());
		prefix.append('.');
		if (subApp instanceof final TypedSubApp typedSubApp) {
			for (final VarConfigInstance param : getVarConfigParams(typedSubApp)) {
				varConfigParams.putIfAbsent(prefix + param.getName(), param);
			}
		}
	}

	private SubApp leaveSubApp() {
		final SubApp subApp = subAppHierarchy.removeLast();
		if (subAppHierarchy.isEmpty()) {
			varConfigParams.clear();
			prefix.setLength(0);
		} else {
			if (subApp instanceof final TypedSubApp typedSubApp) {
				for (final VarConfigInstance param : getVarConfigParams(typedSubApp)) {
					varConfigParams.remove(prefix + param.getName(), param);
				}
			}
			final int newLength = prefix.length() - subApp.getName().length() - 1;
			if (newLength >= 0) {
				prefix.setLength(newLength);
			}
		}
		return subApp;
	}

	private String ensurePrefix(final FBNetworkElement element) {
		return prefix.isEmpty() ? getMappedParentName(element) : prefix.toString();
	}

	private static IInterfaceElement getSubAppInternalElement(final IInterfaceElement element, final SubApp subApp) {
		final InterfaceList interfaceList;
		if (subApp instanceof TypedSubApp) {
			final SubAppType type = subApp.getType();
			if (type == null) {
				return null;
			}
			interfaceList = type.getInterfaceList();
		} else if (subApp.getSubAppNetwork() == null && subApp.getOpposite() instanceof final SubApp subAppOpposite) {
			// we should have a mapped subapp, then the network is in the opposite subapp
			interfaceList = subAppOpposite.getInterface();
		} else {
			return element;
		}
		return interfaceList.getInterfaceElement(element);
	}

	private static IInterfaceElement getSubAppExternalElement(final IInterfaceElement element, final SubApp subApp) {
		final InterfaceList interfaceList;
		if (subApp instanceof TypedSubApp) {
			interfaceList = subApp.getInterface();
		} else if (subApp.isMapped() && subApp.getMapping().getTo() instanceof final SubApp subAppOpposite) {
			// go back to mapped untyped subapp
			interfaceList = subAppOpposite.getInterface();
		} else {
			return element;
		}
		return interfaceList.getInterfaceElement(element);
	}

	private static FBNetwork getFBNetworkForSubApp(final SubApp subApp) {
		if (subApp instanceof TypedSubApp) {
			final SubAppType type = subApp.getType();
			if (type == null) {
				return null;
			}
			return type.getFBNetwork();
		}
		if (subApp.getSubAppNetwork() == null && subApp.getOpposite() instanceof final SubApp oppositeSubApp) {
			// we should have a mapped subapp, then the network is in the opposite subapp
			return oppositeSubApp.getSubAppNetwork();
		}
		return subApp.getSubAppNetwork();
	}

	private static String getMappedParentName(final FBNetworkElement fbnElement) {
		if (fbnElement.isMapped()
				&& fbnElement.getMapping().getFrom().eContainer().eContainer() instanceof final INamedElement namedEl) {
			return namedEl.getQualifiedName() + "."; //$NON-NLS-1$
		}
		return ""; //$NON-NLS-1$
	}

	private static List<VarConfigInstance> getVarConfigParams(final TypedSubApp subApp) {
		if (subApp.isMapped() && subApp.getMapping().getTo() == subApp
				&& subApp.getOpposite() instanceof final TypedSubApp oppositeSubApp) {
			return oppositeSubApp.getVarConfigParams();
		}
		return subApp.getVarConfigParams();
	}
}
