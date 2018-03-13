/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Matthias Plasch, Monika Wenger 
 *   - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editors;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.application.editparts.UntypedSubAppEditPartFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;

public class SubAppNetworkEditor extends FBNetworkEditor {
	private EContentAdapter adapter= new EContentAdapter() {

		@Override
		public void notifyChanged(final Notification notification) {
			int type = notification.getEventType();
			int featureId = notification.getFeatureID(Application.class);

			switch (type) {
				case Notification.SET:
					if (featureId == LibraryElementPackage.SUB_APP__NAME) {
						setPartName(getSubApp().getName());
					}
			}
			firePropertyChange(PROP_DIRTY);
		}
	};

	@Override
	public void dispose() {
		if (adapter != null && getModel() != null) {
			if (getSubApp().eAdapters().contains(adapter)) {
				getSubApp().eAdapters().remove(adapter);
				adapter = null;
			}
		}
		super.dispose();
		getEditDomain().setPaletteViewer(null);
	}

	@Override
	protected ContextMenuProvider getContextMenuProvider(
			final ScrollingGraphicalViewer viewer,
			final ZoomManager zoomManager) {
		ContextMenuProvider cmp = new UIFBNetworkContextMenuProvider(this,
				getActionRegistry(), zoomManager, getSystem().getPalette()) {
			@Override
			protected IAction getMapAction(IEditorPart activeEditor,
					Resource res) {
				return null;
			}
		};
		return cmp;
	}
	
	@Override
	protected void setModel(final IEditorInput input) {
		if (input instanceof SubApplicationEditorInput) {
			SubApplicationEditorInput subAppInput = (SubApplicationEditorInput) input;
			setModel(subAppInput.getSubApp().getSubAppNetwork()); 
			// register EContentAdapter to be informed on changes of the subapplication name
			getSubApp().eAdapters().add(adapter);
			if (input.getName() != null) {
				setPartName(input.getName());
			}
		}
		super.setModel(input);
	}

	@Override
	protected EditPartFactory getEditPartFactory() {		
		return new UntypedSubAppEditPartFactory(this, getZoomManger());
	}
	
	private SubApp getSubApp() {
		return (SubApp)getModel().eContainer();
	}
}
