/*******************************************************************************
 * Copyright (c) 2013, 2024 fortiss GmbH, Profactor GmbH,
 *                          Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editors;

import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.ui.editors.UntypedEditorInput;

public class SubApplicationEditorInput extends UntypedEditorInput {

	public SubApplicationEditorInput(final SubApp subApp) {
		super(subApp, subApp.getName());
	}

	public SubApp getSubApp() {
		return (SubApp) getContent();
	}

	/*
	 * return the root application the sub app is contained in
	 */
	public Application getApplication() {
		// check with the FBNetwork we are contained in for the application
		return (null != getSubApp() && null != getSubApp().getFbNetwork()) ? getSubApp().getFbNetwork().getApplication()
				: null;
	}

}
