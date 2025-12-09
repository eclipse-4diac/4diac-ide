/**
 * Copyright (c) 2022 Primetals Technologies GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Melik Merkumians
 *       - initial API and implementation and/or initial documentation
 */
package org.eclipse.fordiac.ide.structuredtextcore;

import org.eclipse.xtext.ISetup;
import org.eclipse.xtext.resource.FileExtensionProvider;
import org.eclipse.xtext.resource.IResourceServiceProvider;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class DTPStandaloneSetup implements ISetup {
	@Inject
	private FileExtensionProvider fileExtensionProvider;

	@Inject
	private IResourceServiceProvider.Registry registry;

	@Inject
	private IResourceServiceProvider resourceServiceProvider;

	@Override
	public Injector createInjectorAndDoEMFRegistration() {
		final Injector injector = Guice.createInjector(new DTPRuntimeModule());
		injector.injectMembers(this);
		fileExtensionProvider.getFileExtensions()
				.forEach(extension -> registry.getExtensionToFactoryMap().put(extension, resourceServiceProvider));
		return injector;
	}
}
