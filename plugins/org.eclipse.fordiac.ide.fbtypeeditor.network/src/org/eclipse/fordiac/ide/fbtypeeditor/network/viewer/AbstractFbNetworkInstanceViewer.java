/*******************************************************************************
 * Copyright (c) 2013 - 2017 Profactor GmbH, fortiss GmbH
 * 				 2019 Johannes Kepler University
 * 				 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - copying the FB type to fix issues in monitoring
 *   Michael Oberlehner - extracted from CompositeInstanceViewer
 *******************************************************************************/

package org.eclipse.fordiac.ide.fbtypeeditor.network.viewer;

import java.util.EventObject;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.application.editors.FBNElemEditorCloser;
import org.eclipse.fordiac.ide.application.editparts.FBNetworkRootEditPart;
import org.eclipse.fordiac.ide.gef.DiagramEditor;
import org.eclipse.fordiac.ide.gef.FordiacContextMenuProvider;
import org.eclipse.fordiac.ide.model.datatype.helper.InternalAttributeDeclarations;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.ui.editors.EditorCloserAdapter;
import org.eclipse.fordiac.ide.util.ColorHelper;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.jface.util.TransferDropTargetListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.IEditorInput;

public abstract class AbstractFbNetworkInstanceViewer extends DiagramEditor {
	private FBNetworkElement fbNetworkElement;

	private final Adapter fbNetworkElementAdapter = new EditorCloserAdapter(this) {
		@Override
		public void notifyChanged(final Notification msg) {
			super.notifyChanged(msg);
			final Object feature = msg.getFeature();
			if (((LibraryElementPackage.eINSTANCE.getTypedConfigureableObject_TypeEntry().equals(feature))
					&& (fbNetworkElement.getType() == null)) || isSubAppToggledToCollapsed(msg)) {
				// the subapp/cfb was detached from the type or subapp is being collapsed
				closeEditor();
			}
		}

		private boolean isSubAppToggledToCollapsed(final Notification msg) {
			return msg.getNewValue() == null && msg.getOldValue() instanceof final Attribute attr
					&& attr.getAttributeDeclaration() == InternalAttributeDeclarations.UNFOLDED
					&& "true".equals(attr.getValue()); //$NON-NLS-1$
		}

	};

	private Adapter fbNetworkAdapter;

	// subclasses need to override this method and return the fbnetwork contained in
	// fbNetworkElement
	@Override
	public abstract FBNetwork getModel();

	protected FBNetworkElement getFbNetworkElement() {
		return fbNetworkElement;
	}

	@Override
	public <T> T getAdapter(final Class<T> adapter) {
		if (FBNetworkElement.class == adapter) {
			return adapter.cast(getFbNetworkElement());
		}
		return super.getAdapter(adapter);
	}

	@Override
	protected void initializeGraphicalViewer() {
		final GraphicalViewer viewer = getGraphicalViewer();
		if (fbNetworkElement != null) {
			viewer.setContents(getModel());
		}
	}

	@Override
	public void setInput(final IEditorInput input) {
		if (input instanceof final CompositeAndSubAppInstanceViewerInput untypedInput) {
			fbNetworkElement = untypedInput.getContent();
			final String name = getNameHierarchy();
			setPartName(name);
			// the tooltip will show the whole name when hovering
			untypedInput.setName(name);
			fbNetworkElement.eAdapters().add(fbNetworkElementAdapter);
			final EObject container = fbNetworkElement.eContainer();
			if (container != null) {
				fbNetworkAdapter = new FBNElemEditorCloser(this, fbNetworkElement);
				container.eAdapters().add(fbNetworkAdapter);
			}
		}
		super.setInput(input);
	}

	@Override
	public void dispose() {
		if (fbNetworkElement != null) {
			fbNetworkElement.eAdapters().remove(fbNetworkElementAdapter);
			final EObject container = fbNetworkElement.eContainer();
			if (container != null) {
				container.eAdapters().remove(fbNetworkAdapter);
			}
		}
		super.dispose();
	}

	@Override
	protected ContextMenuProvider getContextMenuProvider(final ScrollingGraphicalViewer viewer,
			final ZoomManager zoomManager) {
		return new FordiacContextMenuProvider(getGraphicalViewer(), zoomManager, getActionRegistry());
	}

	@Override
	protected TransferDropTargetListener createTransferDropTargetListener() {
		return null;
	}

	@Override
	public AutomationSystem getSystem() {
		return null;
	}

	@Override
	public String getFileName() {
		return null;
	}

	@Override
	public void commandStackChanged(final EventObject event) {
		// nothing to do as its a viewer!
	}

	@Override
	public void doSave(final IProgressMonitor monitor) {
		// nothing to do as its a viewer!
	}

	protected String getNameHierarchy() {
		// TODO mabye a nice helper function to be put into the fb model
		final StringBuilder retVal = new StringBuilder(fbNetworkElement.getName());
		final EObject cont = fbNetworkElement.eContainer().eContainer();
		if (cont instanceof final INamedElement namedEl) {
			retVal.insert(0, namedEl.getName() + "."); //$NON-NLS-1$
		}
		return retVal.toString();
	}

	@Override
	protected ScalableFreeformRootEditPart createRootEditPart() {
		return new FBNetworkRootEditPart(getModel(), getSite(), getActionRegistry()) {
			@Override
			protected IFigure createFigure() {
				final IFigure viewPort = super.createFigure();
				final IFigure backGround = viewPort.getChildren().get(0);
				final IFigure drawingAreaContainer = backGround.getChildren().get(0);
				final Color backGroundColor = backGround.getBackgroundColor();
				backGround.setBackgroundColor(ColorHelper.lighter(backGroundColor));
				drawingAreaContainer.setBackgroundColor(backGroundColor);
				return viewPort;
			}
		};
	}

}
