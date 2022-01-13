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
	public void launch(ISelection selection, String mode) {
		launch(getLaunchableResource(selection), mode);
	}

	@Override
	public void launch(IEditorPart editor, String mode) {
		launch(getLaunchableResource(editor), mode);
	}

	public void launch(IResource resource, String mode) {
		try {
			ILaunchConfiguration[] configurations = getLaunchConfgurations(resource);
			if (configurations.length == 0) {
				ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
				ILaunchConfigurationType type = manager.getLaunchConfigurationType(getLaunchConfigurationId());
				ILaunchConfigurationWorkingCopy configuration = type.newInstance(null, resource.getName());
				initializeDefaultLaunchConfiguration(configuration, resource, mode);
				configuration.doSave();
				launch(resource, configuration, mode);
			} else {
				launch(resource, configurations[0], mode);
			}
		} catch (CoreException e) {
			launch(resource, null, mode);
		}
	}

	public abstract void launch(IResource resource, ILaunchConfiguration configuration, String mode);

	public void initializeDefaultLaunchConfiguration(ILaunchConfigurationWorkingCopy configuration, IResource resource,
			String mode) {
		configuration.setAttribute(LaunchConfigurationAttributes.RESOURCE, resource.getFullPath().toString());
	}

	@Override
	public ILaunchConfiguration[] getLaunchConfigurations(ISelection selection) {
		return getLaunchConfgurations(getLaunchableResource(selection));
	}

	@Override
	public ILaunchConfiguration[] getLaunchConfigurations(IEditorPart editorpart) {
		return getLaunchConfgurations(getLaunchableResource(editorpart));
	}

	protected ILaunchConfiguration[] getLaunchConfgurations(IResource resource) {
		List<ILaunchConfiguration> configurations = new ArrayList<ILaunchConfiguration>();

		ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
		ILaunchConfigurationType type = manager.getLaunchConfigurationType(getLaunchConfigurationId());

		try {
			for (ILaunchConfiguration configuration : manager.getLaunchConfigurations(type)) {
				try {
					IResource targetResource = LaunchConfigurationAttributes.getResource(configuration);
					if (resource.equals(targetResource)) {
						configurations.add(configuration);
					}
				} catch (CoreException e) {
				}
			}
		} catch (CoreException e) {
		}

		return configurations.toArray(new ILaunchConfiguration[configurations.size()]);
	}

	@Override
	public IResource getLaunchableResource(ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			final Object firstElement = ((IStructuredSelection) selection).getFirstElement();
			if (firstElement instanceof IResource) {
				return ((IResource) firstElement);
			}
		}
		return null;
	}

	@Override
	public IResource getLaunchableResource(IEditorPart editorpart) {
		IEditorInput editorInput = editorpart.getEditorInput();
		if (editorInput instanceof FileEditorInput) {
			return ((FileEditorInput) editorInput).getFile();
		}
		return null;
	}

	public abstract String getLaunchConfigurationId();
}
