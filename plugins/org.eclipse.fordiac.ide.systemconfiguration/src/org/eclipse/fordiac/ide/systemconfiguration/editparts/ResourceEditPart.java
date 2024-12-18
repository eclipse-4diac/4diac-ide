/*******************************************************************************
 * Copyright (c) 2008, 2011 - 2016 Profactor GbmH, TU Wien ACIN, fortiss GmbH
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
 *   Alois Zoitl - added diagram font preference
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration.editparts;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.fordiac.ide.gef.editparts.AbstractViewEditPart;
import org.eclipse.fordiac.ide.gef.listeners.DiagramFontChangeListener;
import org.eclipse.fordiac.ide.gef.listeners.IFontUpdateListener;
import org.eclipse.fordiac.ide.gef.policies.AbstractViewRenameEditPolicy;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.ui.actions.OpenListenerManager;
import org.eclipse.fordiac.ide.systemconfiguration.policies.DeleteResourceEditPolicy;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.fordiac.ide.ui.preferences.UIPreferenceConstants;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.util.IPropertyChangeListener;

public class ResourceEditPart extends AbstractViewEditPart {
	private DiagramFontChangeListener fontChangeListener;

	@Override
	public void activate() {
		super.activate();
		JFaceResources.getFontRegistry().addListener(getFontChangeListener());
	}

	@Override
	public void deactivate() {
		super.deactivate();
		JFaceResources.getFontRegistry().removeListener(getFontChangeListener());
	}

	private IPropertyChangeListener getFontChangeListener() {
		if (null == fontChangeListener) {
			fontChangeListener = new DiagramFontChangeListener(getFigure());
		}
		return fontChangeListener;
	}

	@Override
	protected void refreshVisuals() {
		// nothing to do
	}

	@Override
	public void refreshName() {
		getNameLabel().setText(getINamedElement().getName());
	}

	public class ResourceFigure extends Figure implements IFontUpdateListener {
		private final Label instanceName;
		private final Label typeInfo;

		public ResourceFigure() {
			final GridLayout mainLayout = new GridLayout(3, false);
			mainLayout.marginHeight = 2;
			setLayoutManager(mainLayout);
			if (getINamedElement() == null) {
				instanceName = new Label("N/D");
			} else {
				instanceName = new Label(getINamedElement().getName());
			}
			if (getModel().isDeviceTypeResource()) {
				instanceName.setIcon(FordiacImage.ICON_FIRMWARE_RESOURCE.getImage());
			}
			add(instanceName);
			String type = "N/D";
			if (getModel() != null) {
				type = getModel().getTypeName();
			}

			add(new Label(":")); //$NON-NLS-1$

			typeInfo = new Label(type);
			setTypeLabelFonts();
			add(typeInfo);
			setOpaque(false);
		}

		public Label getInstanceName() {
			return instanceName;
		}

		@Override
		public void updateFonts() {
			setTypeLabelFonts();
			invalidateTree();
			revalidate();
		}

		public void setTypeLabelFonts() {
			typeInfo.setFont(JFaceResources.getFontRegistry().getItalic(UIPreferenceConstants.DIAGRAM_FONT));
		}
	}

	@Override
	protected IFigure createFigureForModel() {
		return new ResourceFigure();
	}

	@Override
	public ResourceFigure getFigure() {
		return (ResourceFigure) super.getFigure();
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new DeleteResourceEditPolicy());
		// EditPolicy which allows the direct edit of the Instance Name
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new AbstractViewRenameEditPolicy());
	}

	@Override
	public boolean understandsRequest(final Request req) {
		if (getModel().isDeviceTypeResource()) {
			return false;
		}
		return super.understandsRequest(req);
	}

	@Override
	public Resource getModel() {
		return (Resource) super.getModel();
	}

	@Override
	protected Adapter createContentAdapter() {
		return new AdapterImpl() {
			@Override
			public void notifyChanged(final Notification notification) {
				refresh();
				super.notifyChanged(notification);
			}
		};
	}

	@Override
	public INamedElement getINamedElement() {
		return getModel();
	}

	@Override
	public Label getNameLabel() {
		return getFigure().getInstanceName();
	}

	@Override
	public IPropertyChangeListener getPreferenceChangeListener() {
		return null;
	}

	@Override
	protected void backgroundColorChanged(final IFigure figure) {
		// currently nothing to be done here
	}

	@Override
	public void performRequest(final Request request) {
		if (request.getType() == RequestConstants.REQ_OPEN) {
			OpenListenerManager.openEditor(getModel());
		} else {
			super.performRequest(request);
		}
	}
}