/*******************************************************************************
 * Copyright (c) 2025 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.hierarchymanager.build;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xtext.resource.containers.IAllContainersState;
import org.eclipse.xtext.ui.resource.generic.EmfUiModule;
import org.eclipse.xtext.ui.shared.Access;

import com.google.inject.Provider;

@SuppressWarnings("static-method")
public class HierarchyUiModule extends EmfUiModule {

	public HierarchyUiModule(final AbstractUIPlugin plugin) {
		super(plugin);
	}

	public Provider<? extends IAllContainersState> provideIAllContainersState() {
		return Access.getWorkspaceProjectsState();
	}
}
