/*******************************************************************************
 * Copyright (c) 2019 Johannes Kepler University Linz
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

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.gef.editparts.LabelDirectEditManager;
import org.eclipse.fordiac.ide.gef.editparts.NameCellEditorLocator;
import org.eclipse.fordiac.ide.gef.listeners.DiagramFontChangeListener;
import org.eclipse.fordiac.ide.gef.listeners.IFontUpdateListener;
import org.eclipse.fordiac.ide.gef.policies.AbstractViewRenameEditPolicy;
import org.eclipse.fordiac.ide.model.commands.change.ChangeFBNetworkElementName;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.fordiac.ide.ui.preferences.PreferenceConstants;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.viewers.TextCellEditor;

public class InstanceNameEditPart extends AbstractGraphicalEditPart implements NodeEditPart {

	private static class InstanceNameFigure extends Label implements IFontUpdateListener {

		public InstanceNameFigure() {
			super();
			setFont();
			setTextAlignment(PositionConstants.CENTER);
			setLabelAlignment(PositionConstants.CENTER);
		}

		private void setFont() {
			setFont(JFaceResources.getFontRegistry().getBold(PreferenceConstants.DIAGRAM_FONT));
		}

		@Override
		public void updateFonts() {
			setFont();
			invalidateTree();
			revalidate();
		}
	}

	private DirectEditManager manager;
	private DiagramFontChangeListener fontChangeListener;

	@Override
	public void activate() {
		super.activate();
		getModel().getRefElement().eAdapters().add(contentAdapter);
		JFaceResources.getFontRegistry().addListener(getFontChangeListener());
	}

	@Override
	public void deactivate() {
		super.deactivate();
		getModel().getRefElement().eAdapters().remove(contentAdapter);
		JFaceResources.getFontRegistry().removeListener(getFontChangeListener());
		if (manager != null) {
			manager = null;
		}
	}

	private IPropertyChangeListener getFontChangeListener() {
		if (null == fontChangeListener) {
			fontChangeListener = new DiagramFontChangeListener(getFigure());
		}
		return fontChangeListener;
	}

	private final EContentAdapter contentAdapter = new EContentAdapter() {

		@Override
		public void notifyChanged(Notification notification) {
			refreshValue();
			super.notifyChanged(notification);
		}
	};

	void refreshValue() {
		getFigure().setText(getModel().getInstanceName());
	}

	@Override
	public void refresh() {
		super.refresh();
		refreshValue();
	}

	@Override
	public InstanceNameFigure getFigure() {
		return (InstanceNameFigure) super.getFigure();
	}

	@Override
	protected void createEditPolicies() {
		// FBNetwork elements need a special rename command therefore we remove the
		// standard edit policy and add a adjusted one
		removeEditPolicy(EditPolicy.DIRECT_EDIT_ROLE);
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new AbstractViewRenameEditPolicy() {
			@Override
			protected Command getDirectEditCommand(DirectEditRequest request) {
				if (getHost() instanceof InstanceNameEditPart) {
					return new ChangeFBNetworkElementName(((InstanceNameEditPart) getHost()).getModel().getRefElement(),
							(String) request.getCellEditor().getValue());
				}
				return null;
			}
		});
	}

	@Override
	public InstanceName getModel() {
		return (InstanceName) super.getModel();
	}

	@Override
	protected IFigure createFigure() {
		InstanceNameFigure figure = new InstanceNameFigure();
		if (isResoruceTypeFBNElement()) {
			figure.setIcon(FordiacImage.ICON_LOCKED_STATE.getImage());
		}
		return figure;
	}

	@Override
	public void performRequest(final Request request) {
		// REQ_DIRECT_EDIT -> first select 0.4 sec pause -> click -> edit
		// REQ_OPEN -> doubleclick

		if (request.getType() == RequestConstants.REQ_DIRECT_EDIT || request.getType() == RequestConstants.REQ_OPEN) {
			performDirectEdit();

		} else {
			super.performRequest(request);
		}
	}

	private DirectEditManager getManager() {
		if (null == manager) {
			manager = createDirectEditManager();
		}
		return manager;
	}

	private DirectEditManager createDirectEditManager() {
		Label l = getFigure();
		return new LabelDirectEditManager(this, TextCellEditor.class, new NameCellEditorLocator(l), l);
	}

	private void performDirectEdit() {
		getManager().show();
	}

	private boolean isResoruceTypeFBNElement() {
		return ((getModel().getRefElement() instanceof FB) && (((FB) getModel().getRefElement()).isResourceTypeFB()));
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(ConnectionEditPart connection) {
		return null;
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(ConnectionEditPart connection) {
		return null;
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(Request request) {
		return null;
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(Request request) {
		return null;
	}

}
