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

import org.eclipse.xtext.ISetup;
import org.eclipse.xtext.resource.FileExtensionProvider;
import org.eclipse.xtext.resource.IResourceServiceProvider;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class HierarchyStandaloneSetup implements ISetup {
	@Inject
	private FileExtensionProvider fileExtensionProvider;

	@Inject
	private IResourceServiceProvider.Registry registry;

	@Inject
	private IResourceServiceProvider resourceServiceProvider;

	@Override
	public Injector createInjectorAndDoEMFRegistration() {
		final Injector injector = Guice.createInjector(new HierarchyRuntimeModule());
		injector.injectMembers(this);
		fileExtensionProvider.getFileExtensions()
				.forEach(extension -> registry.getExtensionToFactoryMap().put(extension, resourceServiceProvider));
		return injector;
	}
}
