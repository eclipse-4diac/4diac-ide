/*******************************************************************************
 * Copyright (c) 2013 - 2017 Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.network.viewer;

import org.eclipse.fordiac.ide.fbtypeeditor.network.editparts.CompositeNetworkEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.FB;

/**
 * Edit Part for the visualization of Composite Networks.
 * 
 * @author Gerhard Ebenhofer (gerhard.ebenhofer@profactor.at)
 */
public class CompositeNetworkViewerEditPart extends CompositeNetworkEditPart {

	private FB fbInstance;

	// the CompositeNetworkEditPart which contained the instance of the
	// composite FB this editor visualizes
	private CompositeNetworkViewerEditPart parentInstanceViewerEditPart;

	public CompositeNetworkViewerEditPart getparentInstanceViewerEditPart() {
		return parentInstanceViewerEditPart;
	}

	public void setparentInstanceViewerEditPart(CompositeNetworkViewerEditPart parentEditPart) {
		this.parentInstanceViewerEditPart = parentEditPart;
	}

	public FB getFbInstance() {
		return fbInstance;
	}

	public void setFbInstance(FB fbInstance) {
		this.fbInstance = fbInstance;
	}

	@Override
	protected void createEditPolicies() {
		//as we don't want to edit anything in the viewer don't install any edit policies here
	}
	
}
