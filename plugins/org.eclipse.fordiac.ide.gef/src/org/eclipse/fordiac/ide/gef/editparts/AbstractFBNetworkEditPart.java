/*******************************************************************************
 * Copyright (c) 2016, 2017 fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Monika Wenger
 *    - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.editparts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.gef.Activator;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public abstract class AbstractFBNetworkEditPart extends AbstractDiagramEditPart {

	/** The child providers. */
	private List<IChildrenProvider> childProviders = null;

	protected EList<FBNetworkElement> getNetworkElements() {
		return getModel().getNetworkElements();
	}

	@Override
	public FBNetwork getModel() {
		return (FBNetwork) super.getModel();
	}

	@Override
	public void deactivate() {
		super.deactivate();
		if (childProviders != null) {
			childProviders.clear();
			childProviders = null;
		}
	}

	@Override
	protected List<?> getModelChildren() {
		List<Object> children = new ArrayList<>(getNetworkElements());
		children.addAll(getFBValues());

		for (IChildrenProvider provider : getChildrenProviders()) {
			if (provider.isEnabled()) {
				children.addAll(provider.getChildren(getModel()));
			}
		}
		return children;
	}

	/**
	 * go through all fb network elements and find inputs with parameters to be
	 * shown.
	 */
	private Collection<? extends Object> getFBValues() {
		ArrayList<Object> valueElements = new ArrayList<>();
		for (FBNetworkElement element : getNetworkElements()) {
			for (VarDeclaration interfaceElement : element.getInterface().getInputVars()) {
				if (null != interfaceElement.getValue()) {
					valueElements.add(interfaceElement.getValue());
				}
			}
		}
		return valueElements;
	}

	private List<IChildrenProvider> getChildrenProviders() {
		if (childProviders == null) {
			getExtensions();
		}
		return childProviders;
	}

	private void getExtensions() {
		childProviders = new ArrayList<>();
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] elems = registry.getConfigurationElementsFor("org.eclipse.fordiac.ide.gef", //$NON-NLS-1$
				"ChildrenProvider"); //$NON-NLS-1$
		for (IConfigurationElement element : elems) {
			try {
				Object object = element.createExecutableExtension("class"); //$NON-NLS-1$
				if (object instanceof IChildrenProvider) {
					IChildrenProvider childrenProvider = (IChildrenProvider) object;
					childProviders.add(childrenProvider);
				}
			} catch (CoreException corex) {
				Activator.getDefault()
				.logError("Error loading ChildrenProvider Extensions in org.eclipse.fordiac.ide.gef", corex); //$NON-NLS-1$
			}
		}
	}

}
