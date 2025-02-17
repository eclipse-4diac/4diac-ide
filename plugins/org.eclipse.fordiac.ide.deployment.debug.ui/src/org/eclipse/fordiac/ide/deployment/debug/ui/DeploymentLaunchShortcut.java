/*******************************************************************************
 * Copyright (c) 2022, 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.deployment.debug.ui;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.ILaunchShortcut2;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.deployment.debug.DeploymentLaunchConfigurationAttributes;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.eclipse.fordiac.ide.model.typelibrary.SystemEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchSite;

public class DeploymentLaunchShortcut implements ILaunchShortcut2 {

	@Override
	public void launch(final ISelection selection, final String mode) {
		launch(getLaunchableResource(selection), collectDeployableObjects(selection).collect(Collectors.toSet()), mode);
	}

	@Override
	public void launch(final IEditorPart editor, final String mode) {
		// try to get selection from editor
		final IWorkbenchSite site = editor.getSite();
		if (site != null) {
			final ISelectionProvider provider = site.getSelectionProvider();
			if (provider != null) {
				final ISelection selection = provider.getSelection();
				if (selection instanceof IStructuredSelection && !selection.isEmpty()) {
					launch(getLaunchableResource(editor),
							collectDeployableObjects(selection).collect(Collectors.toSet()), mode);
					return;
				}
			}
		}
		// fall back to getting deployable objects from editor resource
		launch(getLaunchableResource(editor), collectDeployableObjects(editor).collect(Collectors.toSet()), mode);
	}

	public void launch(final IResource resource, final Set<INamedElement> selection, final String mode) {
		if (resource == null || selection.isEmpty()) {
			return;
		}
		try {
			final ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
			final ILaunchConfigurationType type = manager
					.getLaunchConfigurationType(DeploymentLaunchConfigurationAttributes.ID);
			final ILaunchConfigurationWorkingCopy configuration = type.newInstance(null, resource.getName());
			initializeDefaultLaunchConfiguration(configuration, resource, selection, mode);
			configuration.launch(mode, new NullProgressMonitor());
		} catch (final CoreException e) {
			FordiacLogHelper.logWarning(e.getMessage(), e);
		}
	}

	@SuppressWarnings("static-method") // subclasses may override
	public void initializeDefaultLaunchConfiguration(final ILaunchConfigurationWorkingCopy configuration,
			final IResource resource, final Set<INamedElement> selection, final String mode) {
		configuration.setAttribute(DeploymentLaunchConfigurationAttributes.SYSTEM, resource.getFullPath().toString());
		configuration.setAttribute(DeploymentLaunchConfigurationAttributes.SELECTION,
				selection.stream().map(INamedElement::getQualifiedName).collect(Collectors.toSet()));
	}

	@Override
	public ILaunchConfiguration[] getLaunchConfigurations(final ISelection selection) {
		return getLaunchConfgurations(getLaunchableResource(selection));
	}

	@Override
	public ILaunchConfiguration[] getLaunchConfigurations(final IEditorPart editorpart) {
		return getLaunchConfgurations(getLaunchableResource(editorpart));
	}

	protected static ILaunchConfiguration[] getLaunchConfgurations(final IResource resource) {
		if (resource != null) {
			final ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
			final ILaunchConfigurationType type = manager
					.getLaunchConfigurationType(DeploymentLaunchConfigurationAttributes.ID);

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
			return resource.equals(DeploymentLaunchConfigurationAttributes.getSystemResource(configuration));
		} catch (final CoreException e) {
			FordiacLogHelper.logWarning(e.getMessage(), e);
		}
		return false;
	}

	@Override
	public IResource getLaunchableResource(final ISelection selection) {
		if (selection instanceof final IStructuredSelection structuredSelection) {
			return structuredSelection.stream().map(DeploymentLaunchShortcut::getLaunchableResource)
					.flatMap(Optional::stream).findFirst().orElse(null);
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

	protected static Optional<IResource> getLaunchableResource(final Object element) {
		if (element instanceof final IResource resource) {
			return Optional.of(resource);
		}
		if (element instanceof final EObject object
				&& EcoreUtil.getRootContainer(object) instanceof final AutomationSystem system) {
			return Optional.ofNullable(system.getTypeEntry()).map(TypeEntry::getFile);
		}
		return Optional.empty();
	}

	protected static Stream<INamedElement> collectDeployableObjects(final Object object) {
		if (object instanceof final IStructuredSelection structuredSelection) {
			return structuredSelection.stream().flatMap(DeploymentLaunchShortcut::collectDeployableObjects);
		}
		if (object instanceof final IEditorPart editorPart) {
			return collectDeployableObjects(editorPart.getEditorInput());
		}
		if (object instanceof final IFileEditorInput editorInput) {
			return collectDeployableObjects(editorInput.getFile());
		}
		if (object instanceof final EditPart editPart) {
			return collectDeployableObjects(editPart.getModel());
		}
		if (object instanceof final IFile file) {
			return collectDeployableObjects(TypeLibraryManager.INSTANCE.getTypeEntryForFile(file));
		}
		if (object instanceof final SystemEntry systemEntry) {
			return collectDeployableObjects(systemEntry.getSystem());
		}
		if (object instanceof final AutomationSystem automationSystem) {
			return collectDeployableObjects(automationSystem.getSystemConfiguration());
		}
		if (object instanceof final SystemConfiguration systemConfiguration) {
			return systemConfiguration.getDevices().stream()
					.flatMap(DeploymentLaunchShortcut::collectDeployableObjects);
		}
		if (object instanceof final Device device) {
			return Stream.concat(Stream.of(device),
					device.getResource().stream().flatMap(DeploymentLaunchShortcut::collectDeployableObjects));
		}
		if (object instanceof final Resource resource) {
			return Stream.of(resource);
		}
		if (object instanceof final EObject eObject) {
			return collectDeployableObjects(eObject.eContainer());
		}
		return Stream.empty();
	}
}
