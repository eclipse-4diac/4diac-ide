/*******************************************************************************
 * Copyright (c) 2023, 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editparts;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.application.policies.DeleteTargetInterfaceElementPolicy;
import org.eclipse.fordiac.ide.gef.policies.ModifiedNonResizeableEditPolicy;
import org.eclipse.fordiac.ide.gef.preferences.PreferenceInitializer;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.ui.actions.OpenListenerManager;
import org.eclipse.fordiac.ide.model.ui.editors.AdvancedScrollingGraphicalViewer;
import org.eclipse.fordiac.ide.model.ui.editors.HandlerHelper;
import org.eclipse.fordiac.ide.ui.preferences.PreferenceGetter;
import org.eclipse.fordiac.ide.ui.preferences.UIPreferenceConstants;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.IEditorPart;

public class TargetInterfaceElementEditPart extends AbstractGraphicalEditPart {

	public static final int LABEL_ALPHA = 120;
	private static final String NOT_SIGN = "\u00AC"; //$NON-NLS-1$

	private int maxLabelLength = PreferenceInitializer.DEFAULT_MAX_INTERFACE_BAR_SIZE;

	private final Adapter nameChangeAdapter = new AdapterImpl() {
		@Override
		public void notifyChanged(final Notification notification) {
			final Object feature = notification.getFeature();
			if (LibraryElementPackage.eINSTANCE.getINamedElement_Name().equals(feature)
					|| LibraryElementPackage.eINSTANCE.getINamedElement_Comment().equals(feature)
					|| !getModel().getRefElement().getOutputConnections().stream().map(Connection::isNegated).toList()
							.isEmpty()) {
				refreshVisuals();
			}
			super.notifyChanged(notification);
		}
	};

	@Override
	public void activate() {
		if (!isActive()) {
			initializeMaxLabelLength();
			super.activate();
			getRefElement().eAdapters().add(nameChangeAdapter);
			final FBNetworkElement parent = getRefElement().getFBNetworkElement();
			if (parent != null) {
				parent.eAdapters().add(nameChangeAdapter);
				final FBNetworkElement grandParent = parent.getOuterFBNetworkElement();
				if (grandParent != null) {
					grandParent.eAdapters().add(nameChangeAdapter);
				}
			}
		}
	}

	@Override
	public void deactivate() {
		if (isActive()) {
			getRefElement().eAdapters().remove(nameChangeAdapter);
			final FBNetworkElement parent = getRefElement().getFBNetworkElement();
			if (parent != null && parent.eAdapters().contains(nameChangeAdapter)) {
				parent.eAdapters().remove(nameChangeAdapter);
				final FBNetworkElement grandParent = parent.getOuterFBNetworkElement();
				if (grandParent != null && grandParent.eAdapters().contains(nameChangeAdapter)) {
					grandParent.eAdapters().remove(nameChangeAdapter);
				}
			}
			super.deactivate();
		}
	}

	@Override
	public TargetInterfaceElement getModel() {
		return (TargetInterfaceElement) super.getModel();
	}

	private IInterfaceElement getRefElement() {
		return getModel().getRefElement();
	}

	@Override
	protected IFigure createFigure() {
		final var label = new Label() {
			@Override
			protected void paintFigure(final Graphics graphics) {
				final int alpha = graphics.getAlpha();
				graphics.setAlpha(LABEL_ALPHA);
				graphics.fillRoundRectangle(getBounds(), 8, 8);
				graphics.setAlpha(alpha);
				super.paintFigure(graphics);
			}
		};
		label.setOpaque(false);
		label.setBackgroundColor(getModelColor());
		label.setText(getLabelText());
		label.setLabelAlignment(PositionConstants.LEFT);
		return label;
	}

	@Override
	public Label getFigure() {
		return (Label) super.getFigure();
	}

	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		getFigure().setText(getLabelText());
	}

	private String getLabelText() {
		String labelText = labelTruncate(getModel().getRefPinFullName());
		if (getRefElement().getOutputConnections().stream().anyMatch(con -> !con.isVisible() && con.isNegated())) {
			labelText = NOT_SIGN + labelText;
		}
		return labelText + "\n" + labelTruncate(getRefElement().getComment()); //$NON-NLS-1$
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, new ModifiedNonResizeableEditPolicy());
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new DeleteTargetInterfaceElementPolicy());
	}

	@Override
	public void performRequest(final Request request) {
		if ((request.getType() == RequestConstants.REQ_OPEN)) {
			final var viewer = getViewer();
			final EditPart editPart = viewer.getEditPartForModel(getRefElement());
			if (editPart == null) {
				// we need to go to another breadcrumb item
				openInBreadCrumb(getRefElement());
			} else if (viewer instanceof final AdvancedScrollingGraphicalViewer asgv) {
				asgv.selectAndRevealEditPart(editPart);
			} else {
				viewer.select(editPart);
				viewer.reveal(editPart);
			}
		} else {
			super.performRequest(request);
		}
	}

	private Color getModelColor() {
		if (getRefElement() instanceof Event) {
			return UIPreferenceConstants.getEventConnectorColor();
		}

		if (getRefElement() instanceof AdapterDeclaration) {
			return UIPreferenceConstants.getAdapterConnectorColor();
		}
		return PreferenceGetter.getDataColor(getRefElement().getType().getName());
	}

	private String labelTruncate(final String label) {
		if (label.length() <= maxLabelLength) {
			return label;
		}
		return label.substring(0, maxLabelLength) + "\u2026"; //$NON-NLS-1$
	}

	public static void openInBreadCrumb(final IInterfaceElement target) {
		final IEditorPart editor = OpenListenerManager.openEditor(getTargetEditorElement(target));
		HandlerHelper.selectElement(target, editor);
	}

	private static EObject getTargetEditorElement(final IInterfaceElement target) {
		final FBNetworkElement fbnEl = target.getFBNetworkElement();
		if (fbnEl == null) {
			// we have a typed subapp or CFB Type pin
			return target.eContainer().eContainer();
		}

		FBNetworkElement parent = fbnEl.getOuterFBNetworkElement();

		// we went from inside to outside and reached toplevel
		if (parent == null) {
			return fbnEl.getFbNetwork().getApplication();
		}

		// For unfolded subapps find the next parent that is not expanded as refElement
		while (parent instanceof final SubApp subApp && subApp.isUnfolded()) {
			if (subApp.getOuterFBNetworkElement() == null) {
				return parent.getFbNetwork().getApplication();
			}
			parent = subApp.getOuterFBNetworkElement();
		}

		return parent;
	}

	@Override
	public <T> T getAdapter(final Class<T> key) {
		if (key == IInterfaceElement.class) {
			return key.cast(getModel().getHost());
		}
		return super.getAdapter(key);
	}

	private void initializeMaxLabelLength() {
		final AdvancedScrollingGraphicalViewer viewer = (AdvancedScrollingGraphicalViewer) getViewer();
		if (viewer != null) {
			maxLabelLength = viewer.getPreferencesCache().getMaxInterfaceBarSize();
		}
	}

}
