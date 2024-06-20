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
import org.eclipse.draw2d.ConnectionRouter;
import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.gef.router.MoveableRouter;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;

public abstract class AbstractFBNetworkEditPart extends AbstractDiagramEditPart {

	/** The child providers. */
	private List<IChildrenProvider> childProviders = null;

	protected List<FBNetworkElement> getNetworkElements() {
		return getModel().getNetworkElements().stream().filter(el -> !el.isInGroup()).toList();
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
		final List<Object> children = new ArrayList<>(getNetworkElements());
		children.addAll(getFBValues());

		for (final IChildrenProvider provider : getChildrenProviders()) {
			if (provider.isEnabled()) {
				children.addAll(provider.getChildren(getModel()));
			}
		}
		return children;
	}

	@Override
	protected ConnectionRouter createConnectionRouter(final IFigure figure) {
		return new MoveableRouter();
	}

	@Override
	protected void addChildVisual(final EditPart childEditPart, final int index) {
		if (childEditPart instanceof final SpecificLayerEditPart slEP) {
			final String layer = slEP.getSpecificLayer();
			final IFigure layerFig = getLayer(layer);
			if (layerFig != null) {
				final IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
				layerFig.add(child);
				return;
			}
		}
		// as some of the children are in a different layer we can not use the index
		// given.

		// Currently -1 seems to be the best option
		super.addChildVisual(childEditPart, -1);
	}

	@Override
	protected void removeChildVisual(final EditPart childEditPart) {
		if (childEditPart instanceof final SpecificLayerEditPart slEP) {
			final String layer = slEP.getSpecificLayer();
			final IFigure layerFig = getLayer(layer);
			if (layerFig != null) {
				final IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
				// Check if the figure was not yet removed
				if (layerFig.equals(child.getParent())) {
					layerFig.remove(child);
				}
				return;
			}
		}
		super.removeChildVisual(childEditPart);
	}

	/**
	 * go through all fb network elements and find inputs with parameters to be
	 * shown.
	 */
	protected Collection<Value> getFBValues() {
		final ArrayList<Value> valueElements = new ArrayList<>();
		for (final FBNetworkElement element : getNetworkElements()) {
			element.getInterface().getVisibleInputVars().stream().filter(di -> (di.getValue() != null))
					.forEach(di -> valueElements.add(di.getValue()));
			element.getInterface().getInOutVars().stream().filter(di -> (di.getValue() != null))
					.filter(this::varInOutFilter).forEach(di -> valueElements.add(di.getValue()));
			element.getInterface().getErrorMarker().stream().filter(er -> (er.getValue() != null))
					.forEach(er -> valueElements.add(er.getValue()));
		}
		return valueElements;
	}

	protected boolean varInOutFilter(final VarDeclaration it) {
		if (it.getFBNetworkElement() instanceof final SubApp subApp) {
			VarDeclaration varToUse = it;
			if (subApp.isTyped()) {
				final EList<VarDeclaration> varList = (it.isIsInput())
						? subApp.getType().getInterfaceList().getInOutVars()
						: subApp.getType().getInterfaceList().getOutMappedInOutVars();
				varToUse = varList.stream().filter(typeVar -> it.getName().equals(typeVar.getName())).findAny()
						.orElse(null);
			}
			if (varToUse != null) {
				return varToUse.isIsInput() ? !varToUse.getOutputConnections().isEmpty()
						: !varToUse.getInputConnections().isEmpty();
			}
		}
		return true;
	}

	private List<IChildrenProvider> getChildrenProviders() {
		if (childProviders == null) {
			getExtensions();
		}
		return childProviders;
	}

	private void getExtensions() {
		childProviders = new ArrayList<>();
		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		final IConfigurationElement[] elems = registry.getConfigurationElementsFor("org.eclipse.fordiac.ide.gef", //$NON-NLS-1$
				"ChildrenProvider"); //$NON-NLS-1$
		for (final IConfigurationElement element : elems) {
			try {
				final Object object = element.createExecutableExtension("class"); //$NON-NLS-1$
				if (object instanceof final IChildrenProvider childrenProvider) {
					childProviders.add(childrenProvider);
				}
			} catch (final CoreException corex) {
				FordiacLogHelper.logError("Error loading ChildrenProvider Extensions in org.eclipse.fordiac.ide.gef", //$NON-NLS-1$
						corex);
			}
		}
	}

}
