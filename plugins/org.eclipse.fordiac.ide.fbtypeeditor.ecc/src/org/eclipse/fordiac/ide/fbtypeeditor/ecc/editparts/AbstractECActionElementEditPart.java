/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Tisya Gupta - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts;

import static org.eclipse.fordiac.ide.fbtypeeditor.ecc.preferences.FBTypeEditorPreferenceConstants.MARGIN_HORIZONTAL;
import static org.eclipse.fordiac.ide.fbtypeeditor.ecc.preferences.FBTypeEditorPreferenceConstants.MARGIN_VERTICAL;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.DeleteECActionCommand;
import org.eclipse.fordiac.ide.gef.editparts.AbstractDirectEditableEditPart;
import org.eclipse.fordiac.ide.gef.editparts.ComboCellEditorLocator;
import org.eclipse.fordiac.ide.gef.editparts.ComboDirectEditManager;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.jface.viewers.ComboBoxCellEditor;

public abstract class AbstractECActionElementEditPart extends AbstractGraphicalEditPart {

	private static final Insets ACTION_ELEMENT_INSETS = new Insets(MARGIN_VERTICAL, MARGIN_HORIZONTAL, MARGIN_VERTICAL,
			MARGIN_HORIZONTAL);

	private final Adapter actionAdapter = new AdapterImpl() {
		@Override
		public void notifyChanged(final Notification notification) {
			super.notifyChanged(notification);
			refreshVisuals();
		}
	};

	public abstract ECAction getAction();

	public Label getNameLabel() {
		return (Label) getFigure();
	}

	@Override
	public void activate() {
		if (!isActive()) {
			super.activate();
			getAction().eAdapters().add(actionAdapter);
		}
	}

	@Override
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
			getAction().eAdapters().remove(actionAdapter);
		}
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new ComponentEditPolicy() {
			@Override
			protected Command getDeleteCommand(final GroupRequest request) {
				return new DeleteECActionCommand<>(getAction(), getAction().getECState());
			}
		});
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, createDirectEditPolicy());
	}

	protected abstract DirectEditPolicy createDirectEditPolicy();

	@Override
	public void performRequest(final Request request) {
		if (request.getType() == RequestConstants.REQ_DIRECT_EDIT || request.getType() == RequestConstants.REQ_OPEN) {
			performDirectEdit();
		} else {
			super.performRequest(request);
		}
	}

	protected ComboDirectEditManager createDirectEditManager() {
		return new ComboDirectEditManager(this, ComboBoxCellEditor.class, new ComboCellEditorLocator(getNameLabel()),
				getNameLabel());
	}

	public abstract void performDirectEdit();

	@Override
	protected IFigure createFigure() {
		final Label label = new Label();
		label.setOpaque(true);
		label.setBorder(new MarginBorder(ACTION_ELEMENT_INSETS));
		label.setTextAlignment(PositionConstants.LEFT);
		label.setLabelAlignment(PositionConstants.LEFT);
		setupFigure(label);
		return label;
	}

	protected abstract void setupFigure(final Label label);

	public static void executeCommand(final Command cmd) {
		AbstractDirectEditableEditPart.executeCommand(cmd);
	}
}

