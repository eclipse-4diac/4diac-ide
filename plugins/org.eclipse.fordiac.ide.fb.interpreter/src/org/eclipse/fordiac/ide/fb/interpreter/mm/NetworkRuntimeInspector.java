/*******************************************************************************
 * Copyright (c) 2026 Jose Cabral
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Jose Cabral - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.fb.interpreter.mm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.BasicFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.CompositeFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBNetworkRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBRuntimeAbstract;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

/**
 * @brief This class intends to serves as a bridge between the network of a
 *        resource and the one used by the interpreter.
 *
 *        Values of data input/outputs are stored in the types managed by the
 *        runtimes and not in the instances of the original network used to
 *        create the runtimes. Events however are triggered on the original
 *        network.
 */
public class NetworkRuntimeInspector {

	private final String firstLevelPrefix;
	private final String nameSeparator;

	private final NetworkRuntimeState networkRuntimeState = new NetworkRuntimeState();

	private final Map<String, BlockFBNetworkElement> realBlockNames = new HashMap<>();
	private final Map<IInterfaceElement, IInterfaceElement> realToRuntimeElementMap = new HashMap<>();

	/**
	 *
	 * @brief Constructor of the network runtime inspector
	 *
	 * @param networkRuntime   the network runtime to inspect
	 * @param firstLevelPrefix prefix name for the first level of the hierarchy of
	 *                         FB in the inspected network. Usually is the
	 *                         DEVICE_NAME.RESOURCE_NAME
	 */
	public NetworkRuntimeInspector(final FBNetworkRuntime networkRuntime, final String firstLevelPrefix,
			final String nameSeparator) {
		this.firstLevelPrefix = firstLevelPrefix;
		this.nameSeparator = nameSeparator;

		loadAllInformation(networkRuntime, firstLevelPrefix);
	}

	public NetworkRuntimeState getNetworkRuntimeState() {
		return networkRuntimeState;
	}

	public BlockFBNetworkElement getRealFB(final String name) {
		return realBlockNames.get(name);
	}

	public void applyOutputData(final String instanceName, final List<String> outputValues) {
		final var realFB = getRealFB(instanceName);
		// set output and transfer data in the interpreter network
		for (int i = 0; i < outputValues.size(); i++) {
			final var valueToStore = outputValues.get(i);
			networkRuntimeState.getDataValues().get(realFB.getInterface().getOutputVars().get(i).getQualifiedName()
					.substring(firstLevelPrefix.length())).setValue(valueToStore);

			final var realOutputPin = InterfacePinUtils.findPinInInterface(realFB,
					realFB.getInterface().getOutputVars().get(i));
			realOutputPin.getOutputConnections().forEach(conn -> networkRuntimeState
					.getConnectionValue(conn.getSource().getQualifiedName().substring(firstLevelPrefix.length()),
							conn.getDestination().getQualifiedName().substring(firstLevelPrefix.length()))
					.setValue(valueToStore));
		}

	}

	public Optional<Event> getRealEvent(final Optional<Event> originalEvent) {
		if (!originalEvent.isPresent()) {
			return Optional.empty();
		}

		final var eventInRuntime = getRuntimeInterfaceElement(originalEvent.get());
		if (eventInRuntime == null) {
			return originalEvent;
		}
		return Optional.of((Event) eventInRuntime);
	}

	public IInterfaceElement getRuntimeInterfaceElement(final IInterfaceElement realInterfaceElement) {
		if (realToRuntimeElementMap.containsKey(realInterfaceElement)) {
			return realToRuntimeElementMap.get(realInterfaceElement);
		}
		return realInterfaceElement;
	}

	private String getElementName(final FBNetworkElement networkElement, final String containerPrefix) {
		if (containerPrefix.equals(firstLevelPrefix)) {
			// we are at the first level and therefore we need to use the qualified name
			// without the device.resource prefix
			return networkElement.getQualifiedName().substring(firstLevelPrefix.length());
		}
		return containerPrefix + nameSeparator + networkElement.getName();
	}

	private String getElementName(final FBType fbType, final String containerPrefix) {
		return containerPrefix + nameSeparator + fbType.getName();
	}

	private String getElementName(final IInterfaceElement interfaceElement, final String containerPrefix) {
		if (interfaceElement.getBlockFBNetworkElement() != null) {
			return getElementName(interfaceElement.getBlockFBNetworkElement(), containerPrefix) + nameSeparator
					+ interfaceElement.getName();
		}
		return getElementName(interfaceElement.getFBType(), containerPrefix) + nameSeparator
				+ interfaceElement.getName();

	}

	private void loadTransferData(final FBNetworkRuntime networkRuntime, final String containerPrefix) {
		for (final var connectionAndValue : networkRuntime.getTransferData()) {
			networkRuntimeState.addDataConnectionValue(
					getElementName(connectionAndValue.getKey().getSource(), containerPrefix),
					getElementName(connectionAndValue.getKey().getDestination(), containerPrefix),
					connectionAndValue.getValue());
		}
	}

	private void loadRuntimeInformation(final FBRuntimeAbstract runtime, final FBNetworkElement networkElement,
			final String elementName) {
		if (runtime.getModel() == null) {
			return;
		}

		final TreeIterator<EObject> it = runtime.getModel().eAllContents();
		while (it.hasNext()) {
			final EObject obj = it.next();

			// omit non interface elements, or interface from subApps
			if (!(obj instanceof final IInterfaceElement interfaceElement)
					|| (interfaceElement.getBlockFBNetworkElement() instanceof SubApp)) {
				continue;
			}

			final var identifier = elementName + nameSeparator + interfaceElement.getName();
			switch (interfaceElement) {
			case final Event event -> networkRuntimeState.addEvent(identifier, event);
			case final VarDeclaration varDecl -> {
				final var value = varDecl.getValue();
				if (value == null) {
					varDecl.setValue(LibraryElementFactory.eINSTANCE.createValue());
				}
				networkRuntimeState.addDataValue(identifier, varDecl.getValue());
			}
			default -> {
				// ignore other interface elements if any as currently only events and var
				// declarations are part of the state
			}
			}

			final var realInterface = ((BlockFBNetworkElement) networkElement).getInterface()
					.getInterfaceElement(List.of(interfaceElement.getName()));

			realToRuntimeElementMap.put(realInterface, interfaceElement);
		}

		// load need information to access the state of basic FBs
		if (runtime instanceof final BasicFBTypeRuntime basicTypeRT) {
			final var eCState = basicTypeRT.getActiveState();
			if (eCState == null) {
				basicTypeRT.setActiveState(basicTypeRT.getModel().getECC().getStart().getName());
			}
			networkRuntimeState.addBasicFBRT(elementName, basicTypeRT);
		}

	}

	private void loadAllInformation(final FBNetworkRuntime networkRuntime, final String containerPrefix) {

		loadTransferData(networkRuntime, containerPrefix);

		for (final var entry : networkRuntime.getTypeRuntimes()) {
			final var runtime = entry.getValue();
			final var networkElement = entry.getKey();

			final var elementName = getElementName(networkElement, containerPrefix);
			realBlockNames.put(elementName, (BlockFBNetworkElement) networkElement);
			// collect value holders from the current element
			loadRuntimeInformation(runtime, networkElement, elementName);

			if (runtime instanceof final CompositeFBTypeRuntime compositeTypeRT) {
				loadAllInformation(compositeTypeRT.getNetworkRuntime(), elementName);
			} else if (runtime instanceof final FBNetworkRuntime fbNetworkRT) {
				loadAllInformation(fbNetworkRT, elementName);
			}
		}
	}
}
