/*******************************************************************************
 * Copyright (c) 2011 - 2017 Profactor GmbH, fortiss GmbH
 * 				 2019 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - moved openEditor helper function to EditorUtils
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.editparts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseMotionListener;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.fordiac.ide.fbtypeeditor.policies.DeleteInterfaceEditPolicy;
import org.eclipse.fordiac.ide.gef.draw2d.ConnectorBorder;
import org.eclipse.fordiac.ide.gef.draw2d.UnderlineAlphaLabel;
import org.eclipse.fordiac.ide.gef.policies.INamedElementRenameEditPolicy;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.requests.SelectionRequest;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

public class AdapterInterfaceEditPart extends InterfaceEditPart {
	private final TypeLibrary typeLib;

	AdapterInterfaceEditPart(final TypeLibrary typeLib) {
		this.typeLib = typeLib;
	}

	private class AdapterInterfaceFigure extends UnderlineAlphaLabel {
		public AdapterInterfaceFigure() {
			setOpaque(false);
			setBorder(new ConnectorBorder(getCastedModel()));
			setText(getINamedElement().getName());
			if (isInput()) {
				setLabelAlignment(PositionConstants.LEFT);
				setTextAlignment(PositionConstants.LEFT);
			} else {
				setLabelAlignment(PositionConstants.RIGHT);
				setTextAlignment(PositionConstants.RIGHT);
			}
			setupMouseListener();
		}

		private void setupMouseListener() {
			addMouseMotionListener(new MouseMotionListener() {
				@Override
				public void mouseDragged(final MouseEvent me) {
					// Nothing to be done here
				}

				@Override
				public void mouseEntered(final MouseEvent me) {
					if (0 != (me.getState() & SWT.CONTROL)) {
						setDrawUnderline(true);
					}
				}

				@Override
				public void mouseExited(final MouseEvent me) {
					setDrawUnderline(false);
				}

				@Override
				public void mouseHover(final MouseEvent me) {
					// currently mouseHover should be the same as mouse moved
					mouseMoved(me);
				}

				@Override
				public void mouseMoved(final MouseEvent me) {
					if (0 != (me.getState() & SWT.CONTROL)) {
						if (!isDrawUnderline()) {
							setDrawUnderline(true);
						}
					} else if (isDrawUnderline()) {
						setDrawUnderline(false);
					}
				}
			});
		}
	}

	@Override
	protected IFigure createFigure() {
		final AdapterInterfaceFigure fig = new AdapterInterfaceFigure();
		fig.addAncestorListener(createAncestorListener());
		return fig;
	}

	@Override
	protected void createEditPolicies() {
		if (isDirectEditable()) {
			installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new INamedElementRenameEditPolicy());
		}
		// allow delete of a FB
		if (isInterfaceEditable()) {
			installEditPolicy(EditPolicy.COMPONENT_ROLE, new DeleteInterfaceEditPolicy());
		}
	}

	@Override
	public DragTracker getDragTracker(final Request request) {
		if ((request instanceof final SelectionRequest selRequest)
				&& ((selRequest.getLastButtonPressed() == 1) && (selRequest.isControlKeyPressed()))) {
			// open the default editor for the adapter file
			final TypeEntry entry = typeLib.getAdapterTypeEntry(getAdapter().getType().getName());
			if (null != entry) {
				final IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry()
						.getDefaultEditor(entry.getFile().getName());
				EditorUtils.openEditor(new FileEditorInput(entry.getFile()), desc.getId());
			}
		}
		return super.getDragTracker(request);
	}

	@Override
	public void refreshName() {
		((AdapterInterfaceFigure) getFigure()).setText(getINamedElement().getName());
		super.refreshName();
	}

	private AdapterDeclaration getAdapter() {
		return (AdapterDeclaration) getCastedModel();
	}
}