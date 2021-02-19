/*******************************************************************************
 * Copyright (c) 2020 Primetals Technologies Austria GmbH, Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael J�ger - initial implementation and/or documentation
 *                  - extracted a base class for all model listener
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.ui.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.jface.resource.ImageDescriptor;

public abstract class AbstractOpenSystemElementListener extends OpenListener {

	private static final String SYSTEM_EDITOR_ID = "org.eclipse.fordiac.ide.editors.SystemEditor"; //$NON-NLS-1$

	@Override
	public final String getActionText() {
		return "System Editor";
	}

	@Override
	public final ImageDescriptor getImageDescriptor() {
		return FordiacImage.ICON_SYSTEM.getImageDescriptor();
	}

	protected void openInSystemEditor(final IFile systemFile, final EObject element) {
		openInBreadCrumbEditor(systemFile, SYSTEM_EDITOR_ID, element);
	}
}
