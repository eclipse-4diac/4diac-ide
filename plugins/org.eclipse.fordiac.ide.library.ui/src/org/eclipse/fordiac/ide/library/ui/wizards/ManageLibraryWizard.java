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
 * 	Mario Kastner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.library.ui.wizards;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.wizard.Wizard;

public class ManageLibraryWizard extends Wizard {

	private final IProject project;

	public ManageLibraryWizard(final IProject project) {
		this.project = project;
	}

	@Override
	public boolean performFinish() {
		return false;
	}

	@Override
	public void addPages() {
		addPage(new LibraryPlanningPage("Plan Libraries", project));
		super.addPages();
	}

}
