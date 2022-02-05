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
package org.eclipse.fordiac.ide.debug.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.ILaunchShortcut2;
import org.eclipse.fordiac.ide.debug.LaunchConfigurationAttributes;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.part.FileEditorInput;

/**
 * @author mjobst
 *
 */
public abstract class LaunchShortcut implements ILaunchShortcut2 {

	@Override
	public void launch(final ISelection selection, final String mode) {
		launch(getLaunchableResource(selection), mode);
	}

	@Override
	public void launch(final IEditorPart editor, final String mode) {
		launch(getLaunchableResource(editor), mode);
	}

	public void launch(final IResource resource, final String mode) {
		try {
			final ILaunchConfiguration[] configurations = getLaunchConfgurations(resource);
			if (configurations.length == 0) {
				final ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
				final ILaunchConfigurationType type = manager.getLaunchConfigurationType(getLaunchConfigurationId());
				final ILaunchConfigurationWorkingCopy configuration = type.newInstance(null, resource.getName());
				initializeDefaultLaunchConfiguration(configuration, resource, mode);
				configuration.doSave();
				launch(resource, configuration, mode);
			} else {
				launch(resource, configurations[0], mode);
			}
		} catch (final CoreException e) {
			launch(resource, null, mode);
		}
	}

	public abstract void launch(IResource resource, ILaunchConfiguration configuration, String mode);

	public void initializeDefaultLaunchConfiguration(final ILaunchConfigurationWorkingCopy configuration, final IResource resource,
			final String mode) {
		configuration.setAttribute(LaunchConfigurationAttributes.RESOURCE, resource.getFullPath().toString());
	}

	@Override
	public ILaunchConfiguration[] getLaunchConfigurations(final ISelection selection) {
		return getLaunchConfgurations(getLaunchableResource(selection));
	}

	@Override
	public ILaunchConfiguration[] getLaunchConfigurations(final IEditorPart editorpart) {
		return getLaunchConfgurations(getLaunchableResource(editorpart));
	}

	protected ILaunchConfiguration[] getLaunchConfgurations(final IResource resource) {
		final List<ILaunchConfiguration> configurations = new ArrayList<>();

		final ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
		final ILaunchConfigurationType type = manager.getLaunchConfigurationType(getLaunchConfigurationId());

		try {
			for (final ILaunchConfiguration configuration : manager.getLaunchConfigurations(type)) {
				try {
					final IResource targetResource = LaunchConfigurationAttributes.getResource(configuration);
					if (resource.equals(targetResource)) {
						configurations.add(configuration);
					}
				} catch (final CoreException e) {
				}
			}
		} catch (final CoreException e) {
		}

		return configurations.toArray(new ILaunchConfiguration[configurations.size()]);
	}

	@Override
	public IResource getLaunchableResource(final ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			final Object firstElement = ((IStructuredSelection) selection).getFirstElement();
			if (firstElement instanceof IResource) {
				return ((IResource) firstElement);
			}
		}
		return null;
	}

	@Override
	public IResource getLaunchableResource(final IEditorPart editorpart) {
		final IEditorInput editorInput = editorpart.getEditorInput();
		if (editorInput instanceof FileEditorInput) {
			return ((FileEditorInput) editorInput).getFile();
		}
		return null;
	}

	public abstract String getLaunchConfigurationId();
}
