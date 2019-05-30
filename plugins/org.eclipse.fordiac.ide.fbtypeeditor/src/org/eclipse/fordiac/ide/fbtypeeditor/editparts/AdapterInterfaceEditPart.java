/*******************************************************************************
 * Copyright (c) 2011 - 2017 Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.editparts;

import org.eclipse.draw2d.AncestorListener;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseMotionListener;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.fordiac.ide.fbtypeeditor.Activator;
import org.eclipse.fordiac.ide.fbtypeeditor.policies.DeleteInterfaceEditPolicy;
import org.eclipse.fordiac.ide.fbtypeeditor.policies.WithNodeEditPolicy;
import org.eclipse.fordiac.ide.gef.draw2d.ConnectorBorder;
import org.eclipse.fordiac.ide.gef.draw2d.UnderlineAlphaLabel;
import org.eclipse.fordiac.ide.gef.editparts.AbstractDirectEditableEditPart;
import org.eclipse.fordiac.ide.gef.policies.INamedElementRenameEditPolicy;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.gef.requests.SelectionRequest;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

public class AdapterInterfaceEditPart extends InterfaceEditPart {
	private Palette systemPalette;

	AdapterInterfaceEditPart(Palette systemPalette) {
		this.systemPalette = systemPalette;
	}

	private class AdapterInterfaceFigure extends UnderlineAlphaLabel {
		public AdapterInterfaceFigure() {
			super();
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
				public void mouseDragged(MouseEvent me) {
				}

				@Override
				public void mouseEntered(MouseEvent me) {
					if (0 != (me.getState() & SWT.CONTROL)) {
						setDrawUnderline(true);
					}
				}

				@Override
				public void mouseExited(MouseEvent me) {
					setDrawUnderline(false);
				}

				@Override
				public void mouseHover(MouseEvent me) {
					// currently mouseHover should be the same as mouse moved
					mouseMoved(me);
				}

				@Override
				public void mouseMoved(MouseEvent me) {
					if (0 != (me.getState() & SWT.CONTROL)) {
						if (!isDrawUnderline()) {
							setDrawUnderline(true);
						}
					} else {
						if (isDrawUnderline()) {
							setDrawUnderline(false);
						}
					}
				}
			});
		}
	}

	@Override
	protected IFigure createFigure() {
		AdapterInterfaceFigure fig = new AdapterInterfaceFigure();
		fig.addAncestorListener(new AncestorListener() {
			@Override
			public void ancestorRemoved(IFigure ancestor) {
			}

			@Override
			public void ancestorMoved(IFigure ancestor) {
				update();
			}

			@Override
			public void ancestorAdded(IFigure ancestor) {
				update();
			}

		});
		return fig;
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new INamedElementRenameEditPolicy() {
			@Override
			protected Command getDirectEditCommand(final DirectEditRequest request) {
				if (getHost() instanceof AbstractDirectEditableEditPart) {
					return new ChangeNameCommand(getCastedModel(), (String) request.getCellEditor().getValue());
				}
				return null;
			}
		});
		// allow delete of a FB
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new DeleteInterfaceEditPolicy());
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new WithNodeEditPolicy());
	}

	@Override
	public DragTracker getDragTracker(Request request) {
		if (request instanceof SelectionRequest) {
			SelectionRequest selRequest = (SelectionRequest) request;
			if ((selRequest.getLastButtonPressed() == 1) && (selRequest.isControlKeyPressed())) {
				// open the default editor for the adapter file
				IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				PaletteEntry entry = systemPalette.getTypeEntry(getAdapter().getType().getName());
				if (null != entry) {
					IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry()
							.getDefaultEditor(entry.getFile().getName());
					try {
						page.openEditor(new FileEditorInput(entry.getFile()), desc.getId());
					} catch (PartInitException e) {
						Activator.getDefault().logError(e.getMessage(), e);
					}
				}
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