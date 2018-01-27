/*******************************************************************************
 * Copyright (c) 2008, 2011 - 2016 Profactor GbmH, TU Wien ACIN, fortiss GmbH
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
package org.eclipse.fordiac.ide.systemconfiguration.editparts;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.gef.editparts.AbstractViewEditPart;
import org.eclipse.fordiac.ide.gef.policies.AbstractViewRenameEditPolicy;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.resourceediting.editors.ResourceDiagramEditor;
import org.eclipse.fordiac.ide.resourceediting.editors.ResourceEditorInput;
import org.eclipse.fordiac.ide.systemconfiguration.Activator;
import org.eclipse.fordiac.ide.systemconfiguration.policies.DeleteResourceEditPolicy;
import org.eclipse.fordiac.ide.util.imageprovider.FordiacImage;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class ResourceEditPart extends AbstractViewEditPart {
	private ResourceFigure figure;
	private final EContentAdapter contentAdapter = new EContentAdapter() {
		@Override
		public void notifyChanged(Notification notification) {
			refresh();
			super.notifyChanged(notification);
		}
	};

	@Override
	protected void refreshVisuals() {
		// nothing to do
	}
	
	@Override
	public void refreshName() {
		getNameLabel().setText(getINamedElement().getName());
	}

	public class ResourceFigure extends Figure {
		private final Label instanceName;
		private final Label typeInfo;

		public ResourceFigure() {
			GridLayout mainLayout = new GridLayout(2, false);
			mainLayout.marginHeight = 2;
			setLayoutManager(mainLayout);
			if (getINamedElement() == null) {
				instanceName = new Label("N/D");
			} else {
				instanceName = new Label(getINamedElement().getName());
			}
			if (getModel().isDeviceTypeResource()) {
				instanceName.setIcon(FordiacImage.ICON_FirmwareResource.getImage());
			}
			add(instanceName);
			String type = "N/D";
			if (getModel() != null) {
				type = getModel().getTypeName();
			}
			typeInfo = new Label("(" //$NON-NLS-1$
					+ type + ")"); //$NON-NLS-1$
			typeInfo.setFont(JFaceResources.getFontRegistry().getItalic(JFaceResources.DEFAULT_FONT));
			add(typeInfo);
			setOpaque(false);
		}

		public Label getInstanceName() {
			return instanceName;
		}
	}

	@Override
	protected IFigure createFigureForModel() {
		if (figure == null) {
			figure = new ResourceFigure();
		}
		return figure;
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
	protected EContentAdapter getContentAdapter() {
		return contentAdapter;
	}

	@Override
	public INamedElement getINamedElement() {
		return getModel();
	}

	@Override
	public Label getNameLabel() {
		return ((ResourceFigure) getFigure()).getInstanceName();
	}

	@Override
	public IPropertyChangeListener getPreferenceChangeListener() {
		return null;
	}

	@Override
	protected void backgroundColorChanged(IFigure figure) {
	}
	
	@Override
	public void performRequest(final Request request) {
		if (request.getType() == RequestConstants.REQ_OPEN) {		
			ResourceEditorInput input = new ResourceEditorInput(getModel());
			
			IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			try {
				activePage.openEditor(input,ResourceDiagramEditor.class.getName());
			} catch (PartInitException e) {
				Activator.getDefault().logError(e.getMessage(), e);
			}
		}else{
			super.performRequest(request);
		}		
	}
}