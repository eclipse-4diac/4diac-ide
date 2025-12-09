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
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.ui.editors.UntypedEditorInput;

public class ApplicationEditorInput extends UntypedEditorInput {

	public ApplicationEditorInput(final Application app) {
		super(app, app.getName());
	}

	public AutomationSystem getAutomationSystem() {
		return getContent().getAutomationSystem();
	}

	@Override
	public Application getContent() {
		return (Application) super.getContent();
	}

}
