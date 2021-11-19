/*******************************************************************************
 * Copyright (c) 2012 - 2017 Profactor GmbH, fortiss GmbH
 * 						2021 Primetals Technologies Austria GmbH
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *    - initial implementation
 *   Christoph Binder - deactivate method
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.fbtester.editparts;

import java.util.Set;

import org.eclipse.draw2d.AncestorListener;
import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.fordiac.ide.fbtypeeditor.fbtester.TestingManager;
import org.eclipse.fordiac.ide.fbtypeeditor.fbtester.model.TestElement;
import org.eclipse.fordiac.ide.fbtypeeditor.fbtester.policies.SetTestValueEditPolicy;
import org.eclipse.fordiac.ide.gef.editparts.AbstractViewEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;

/** The Class TestEditPart. */
public class TestEditPart extends AbstractViewEditPart {

	/** The parent part. */
	private org.eclipse.fordiac.ide.fbtypeeditor.fbtester.editparts.InterfaceEditPart parentPart;

	public org.eclipse.fordiac.ide.fbtypeeditor.fbtester.editparts.InterfaceEditPart getParentPart() {
		return parentPart;
	}

	@Override
	public void activate() {
		super.activate();
		@SuppressWarnings("rawtypes")
		final Set set = getViewer().getEditPartRegistry().keySet();
		for (final Object object : set) {
			if (object instanceof IInterfaceElement
					&& ((IInterfaceElement) object).equals(getModel().getInterfaceElement())) {
				final EditPart part = (EditPart) getViewer().getEditPartRegistry().get(object);
				if (part instanceof org.eclipse.fordiac.ide.fbtypeeditor.fbtester.editparts.InterfaceEditPart) {
					parentPart = (org.eclipse.fordiac.ide.fbtypeeditor.fbtester.editparts.InterfaceEditPart) part;
					final IFigure f = parentPart.getFigure();
					f.addAncestorListener(new AncestorListener() {

						@Override
						public void ancestorRemoved(final IFigure ancestor) {
							// currently nothing to do here
						}

						@Override
						public void ancestorMoved(final IFigure ancestor) {
							refreshPosition();

						}

						@Override
						public void ancestorAdded(final IFigure ancestor) {
							// currently nothing to do here
						}
					});
				}
			}
		}
		updatePos();
		registerElement();
	}
	
	@Override
	public void deactivate() {
		super.deactivate();
		unregisterElement();
	}

	/** Set the background color of this editparts figure
	 *
	 * @param color */
	public void setBackgroundColor(final Color color) {
		getFigure().setBackgroundColor(color);
	}

	@Override
	protected void refreshName() {
		getNameLabel().setText(getModel().getValue());
		getNameLabel().setToolTip(new Label(getModel().getValue()));
	}

	/** Register element. */
	protected void registerElement() {
		TestingManager.getInstance().addTestElement(getModel());
	}
	
	/** Unregister element. */
	protected void unregisterElement() {
		TestingManager.getInstance().deleteTestElement(getModel());
	}

	/** Checks if is input.
	 *
	 * @return true, if is input */
	public boolean isInput() {
		return getModel().getInterfaceElement().isIsInput();
	}

	/** Checks if is event.
	 *
	 * @return true, if is event */
	public boolean isEvent() {
		return getModel().getInterfaceElement() instanceof Event;
	}

	/** Checks if is variable.
	 *
	 * @return true, if is variable */
	public boolean isVariable() {
		return getModel().getInterfaceElement() instanceof VarDeclaration;
	}

	/** The oldx. */
	protected int oldx;

	/** The oldy. */
	protected int oldy;

	/** Update pos. */
	protected void updatePos() {
		if (null != parentPart) {
			final String label = ((Label) getFigure()).getText();
			final Rectangle bounds = parentPart.getFigure().getBounds();

			int x = 0;
			if (isInput()) {
				final Font font = ((Label) getFigure()).getFont();
				int width = 50;
				if (font != null) {
					width = FigureUtilities.getTextWidth(label, getFigure().getFont());
					width = Math.max(width, getFigure().getBounds().width);
				}
				x = bounds.x - 10 - width - 15 * getModel().getFb().getInterface().getEventInputs().size();
			} else {
				x = bounds.x + bounds.width + 10 + 15 * getModel().getFb().getInterface().getEventInputs().size();

			}
			final int y = bounds.y;
			if (x != oldx || y != oldy) {
				getModel().updatePosition(x, y);
				oldx = x;
				oldy = y;
			}
		}
	}

	/** Gets the casted model.
	 *
	 * @return the casted model */
	@Override
	public TestElement getModel() {
		return (TestElement) super.getModel();
	}

	@Override
	protected void createEditPolicies() {
		// EditPolicy which allows the direct edit of the Instance Name
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new SetTestValueEditPolicy());
	}

	@Override
	protected IFigure createFigureForModel() {
		final Label l = new Label();
		l.setSize(100, 100);
		l.setOpaque(true);
		l.setBackgroundColor(org.eclipse.draw2d.ColorConstants.yellow);
		l.setPreferredSize(150, 20);
		l.setBorder(new MarginBorder(3, 5, 3, 5));

		if (isInput()) {
			final LineBorder lb = new LineBorder() {
				@Override
				public Insets getInsets(final IFigure figure) {
					return new Insets(3, 5, 3, 5);
				}
			};
			l.setBorder(lb);
		}
		l.setText("--"); //$NON-NLS-1$
		return l;
	}

	@Override
	protected Adapter createContentAdapter() {
		return new AdapterImpl() {
			private boolean blockAdapter;

			@Override
			public void notifyChanged(final Notification notification) {
				if (!blockAdapter) {
					blockAdapter = true;
					super.notifyChanged(notification);
					refreshVisuals();
					blockAdapter = false;
				}
			}

		};
	}

	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		refreshPosition();
	}

	protected void refreshPosition() {
		updatePos();
		if (null != getModel()) {
			final Rectangle bounds = new Rectangle(getModel().getPosition().asPoint(), new Dimension(80, 21));
			((GraphicalEditPart) getParent()).setLayoutConstraint(this, getFigure(), bounds);
		}
	}

	@Override
	public boolean understandsRequest(final Request request) {
		if (request.getType() == RequestConstants.REQ_MOVE) {
			return false;
		}
		if (request.getType() == RequestConstants.REQ_DIRECT_EDIT || request.getType() == RequestConstants.REQ_OPEN) {
			return isInput();
		}
		return super.understandsRequest(request);
	}

	@Override
	public void performRequest(final Request request) {
		if ((request.getType() == RequestConstants.REQ_DIRECT_EDIT || request.getType() == RequestConstants.REQ_OPEN)
				&& !isInput()) {
			return;
		}
		super.performRequest(request);
	}

	@Override
	public INamedElement getINamedElement() {
		return getModel().getInterfaceElement();
	}

	@Override
	public Label getNameLabel() {
		return (Label) getFigure();
	}

	@Override
	public IPropertyChangeListener getPreferenceChangeListener() {
		return null;
	}

	/** Sets the value.
	 *
	 * @param string the new value */
	public void setValue(final String string) {
		if (isActive() && getFigure() != null) {
			((Label) getFigure()).setText(string);
		}
	}

}
