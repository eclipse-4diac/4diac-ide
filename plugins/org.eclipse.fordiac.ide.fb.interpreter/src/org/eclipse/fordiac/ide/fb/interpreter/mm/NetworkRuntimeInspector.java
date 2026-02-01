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

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.CompositeFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBNetworkRuntime;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.impl.SubAppImpl;

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

	private static final String NAME_SEPARATOR = "."; //$NON-NLS-1$
	private static final String FIRST_LEVEL_CONTAINER_PREFIX = ""; //$NON-NLS-1$

	private final String firstLevelPrefix;
	private final Map<String, IInterfaceElement> valueHolderElements = new HashMap<>();
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
	public NetworkRuntimeInspector(final FBNetworkRuntime networkRuntime, final String firstLevelPrefix) {
		this.firstLevelPrefix = firstLevelPrefix;
		collectAllValueHolderElements(networkRuntime, FIRST_LEVEL_CONTAINER_PREFIX);
	}

	public Map<String, IInterfaceElement> getAllValueHolderElements() {
		return valueHolderElements;
	}

	public BlockFBNetworkElement getRealFB(final String name) {
		return realBlockNames.get(name);
	}

	public IInterfaceElement getRuntimeInterfaceElement(final IInterfaceElement realInterfaceElement) {
		if (realToRuntimeElementMap.containsKey(realInterfaceElement)) {
			return realToRuntimeElementMap.get(realInterfaceElement);
		}
		return realInterfaceElement;
	}

	private String getElementName(final FBNetworkElement networkElement, final String containerPrefix) {
		if (containerPrefix.isEmpty()) {
			// we are at the first level and therefore we need to use the qualified name
			// without the device.resource prefix
			return networkElement.getQualifiedName().substring(firstLevelPrefix.length());
		}
		return containerPrefix + NAME_SEPARATOR + networkElement.getName();

	}

	private void collectAllValueHolderElements(final FBNetworkRuntime networkRuntime, final String containerPrefix) {

		for (final var entry : networkRuntime.getTypeRuntimes()) {
			final var runtime = entry.getValue();
			final var networkElement = entry.getKey();

			final var elementName = getElementName(networkElement, containerPrefix);
			realBlockNames.put(elementName, (BlockFBNetworkElement) networkElement);
			// collect value holders from the current element
			if (runtime.getModel() != null) {
				final TreeIterator<EObject> it = runtime.getModel().eAllContents();
				while (it.hasNext()) {
					final EObject obj = it.next();
					if (obj instanceof final IInterfaceElement varDecl
							&& !(varDecl.getBlockFBNetworkElement() instanceof SubAppImpl)) {
						valueHolderElements.put(elementName + NAME_SEPARATOR + varDecl.getName(), varDecl);
						final var realInterface = ((BlockFBNetworkElement) networkElement).getInterface()
								.getInterfaceElement(List.of(varDecl.getName()));

						realToRuntimeElementMap.put(realInterface, varDecl);
					}
				}

			}

			if (runtime instanceof final CompositeFBTypeRuntime compositeTypeRT) {
				collectAllValueHolderElements(compositeTypeRT.getNetworkRuntime(), elementName);
			} else if (runtime instanceof final FBNetworkRuntime fbNetworkRT) {
				collectAllValueHolderElements(fbNetworkRT, elementName);
			}
		}
	}
}
