/*******************************************************************************
 * Copyright (c) 2022, 2023 Martin Erich Jobst
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

import java.util.stream.Stream;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.ILaunchShortcut2;
import org.eclipse.fordiac.ide.debug.LaunchConfigurationAttributes;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;

/** @author mjobst */
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
		if (resource == null) {
			return;
		}
		try {
			final ILaunchConfiguration[] configurations = getLaunchConfgurations(resource);
			if (configurations == null) {
				return;
			}
			if (configurations.length == 0) {
				final ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
				final ILaunchConfigurationType type = manager.getLaunchConfigurationType(getLaunchConfigurationId());
				final ILaunchConfigurationWorkingCopy configuration = type.newInstance(null,
						manager.generateLaunchConfigurationName(resource.getName()));
				initializeDefaultLaunchConfiguration(configuration, resource, mode);
				configuration.doSave();
				launch(resource, configuration, mode);
			} else {
				launch(resource, configurations[0], mode);
			}
		} catch (final CoreException e) {
			FordiacLogHelper.logWarning(e.getMessage(), e);
		}
	}

	public abstract void launch(IResource resource, ILaunchConfiguration configuration, String mode);

	@SuppressWarnings("static-method") // subclasses may override
	public void initializeDefaultLaunchConfiguration(final ILaunchConfigurationWorkingCopy configuration,
			final IResource resource, final String mode) {
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
		if (resource != null) {
			final ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
			final ILaunchConfigurationType type = manager.getLaunchConfigurationType(getLaunchConfigurationId());

			try {
				return Stream.of(manager.getLaunchConfigurations(type))
						.filter(configuration -> isRelevantLaunchConfiguration(configuration, resource))
						.toArray(ILaunchConfiguration[]::new);
			} catch (final CoreException e) {
				FordiacLogHelper.logWarning(e.getMessage(), e);
			}
		}
		return new ILaunchConfiguration[0];
	}

	protected static boolean isRelevantLaunchConfiguration(final ILaunchConfiguration configuration,
			final IResource resource) {
		try {
			final IResource targetResource = LaunchConfigurationAttributes.getResource(configuration);
			return resource.equals(targetResource);
		} catch (final CoreException e) {
			FordiacLogHelper.logWarning(e.getMessage(), e);
		}
		return false;
	}

	@Override
	public IResource getLaunchableResource(final ISelection selection) {
		if ((selection instanceof final IStructuredSelection structuredSelection)
				&& (structuredSelection.getFirstElement() instanceof final IResource resource)) {
			return resource;
		}
		return null;
	}

	@Override
	public IResource getLaunchableResource(final IEditorPart editorpart) {
		if (editorpart.getEditorInput() instanceof final IFileEditorInput fileEditorInput) {
			return fileEditorInput.getFile();
		}
		return null;
	}

	public abstract String getLaunchConfigurationId();
}
