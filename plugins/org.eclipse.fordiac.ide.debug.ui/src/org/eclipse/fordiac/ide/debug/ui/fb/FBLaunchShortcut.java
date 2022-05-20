/*******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.debug.ui.fb;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.fordiac.ide.debug.ui.LaunchShortcut;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;

public abstract class FBLaunchShortcut extends LaunchShortcut {

	@Override
	public void launch(final IResource resource, final ILaunchConfiguration configuration, final String mode) {
		final FBType type = (FBType) TypeLibraryManager.INSTANCE.getTypeEntryForFile((IFile) resource).getType();
		launch(type, configuration, mode);
	}

	public abstract void launch(FBType type, ILaunchConfiguration configuration, String mode);
}
