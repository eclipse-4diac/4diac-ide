/*******************************************************************************
 * Copyright (c) 2025 Felix Schmid
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Felix Schmid
 *     - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.widgets;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.fordiac.ide.contractspec.ui.internal.ContractspecActivator;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.ui.editor.embedded.EmbeddedEditorFactory;
import org.eclipse.xtext.ui.editor.embedded.IEditedResourceProvider;

import com.google.inject.Injector;

@SuppressWarnings("restriction")
public class ContractspecResourceProvider implements IEditedResourceProvider {

	private static final URI DUMMY_URI = URI.createURI("__dummy.contract"); //$NON-NLS-1$
	private static final IResourceServiceProvider SERVICE_PROVIDER = IResourceServiceProvider.Registry.INSTANCE
			.getResourceServiceProvider(DUMMY_URI);

	private static Injector injector;

	@Override
	public XtextResource createResource() {
		final XtextResourceSet resourceSet = (XtextResourceSet) SERVICE_PROVIDER.get(ResourceSet.class);
		final XtextResource resource = SERVICE_PROVIDER.get(XtextResource.class);
		resource.setURI(DUMMY_URI);
		resourceSet.getResources().add(resource);
		return resource;
	}

	static EmbeddedEditorFactory.Builder getEmbeddedEditorBuilder() {
		if (injector == null) {
			final ContractspecActivator activator = ContractspecActivator.getInstance();
			injector = activator.getInjector(ContractspecActivator.ORG_ECLIPSE_FORDIAC_IDE_CONTRACTSPEC);
		}
		final IEditedResourceProvider resourceProvider = new ContractspecResourceProvider();
		return injector.getInstance(EmbeddedEditorFactory.class).newEditor(resourceProvider);
	}
}
