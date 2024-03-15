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
package org.eclipse.fordiac.ide.structuredtextalgorithm.ui.resource;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.fordiac.ide.structuredtextalgorithm.ui.internal.StructuredtextalgorithmActivator;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.xtext.resource.IResourceFactory;
import org.eclipse.xtext.ui.resource.IResourceSetInitializer;

import com.google.inject.Injector;

public class STAlgorithmResourceSetInitializer implements IResourceSetInitializer {

	@Override
	public void initialize(final ResourceSet resourceSet, final IProject project) {
		if (has4diacProjectNature(project)) {
			final IResourceFactory resourceFactory = getInjector().getInstance(IResourceFactory.class);
			resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("fbt", resourceFactory); //$NON-NLS-1$
			resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("FBT", resourceFactory); //$NON-NLS-1$
			resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("sys", resourceFactory); //$NON-NLS-1$
			resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("SYS", resourceFactory); //$NON-NLS-1$
			resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("sub", resourceFactory); //$NON-NLS-1$
			resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("SUB", resourceFactory); //$NON-NLS-1$
			resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("atp", resourceFactory); //$NON-NLS-1$
			resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ATP", resourceFactory); //$NON-NLS-1$
			resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("dtp", resourceFactory); //$NON-NLS-1$
			resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("DTP", resourceFactory); //$NON-NLS-1$
		}
	}

	public static boolean has4diacProjectNature(final IProject project) {
		try {
			return project != null && project.isOpen() && project.hasNature(SystemManager.FORDIAC_PROJECT_NATURE_ID);
		} catch (final CoreException e) {
			return false;
		}
	}

	public static Injector getInjector() {
		final StructuredtextalgorithmActivator activator = StructuredtextalgorithmActivator.getInstance();
		if (activator == null) {
			throw new IllegalStateException("The bundle has not been started!"); //$NON-NLS-1$
		}
		final Injector injector = activator.getInjector(
				StructuredtextalgorithmActivator.ORG_ECLIPSE_FORDIAC_IDE_STRUCTUREDTEXTALGORITHM_STALGORITHM);
		if (injector == null) {
			throw new IllegalStateException("The bundle was not initialized properly!"); //$NON-NLS-1$
		}
		return injector;
	}
}
