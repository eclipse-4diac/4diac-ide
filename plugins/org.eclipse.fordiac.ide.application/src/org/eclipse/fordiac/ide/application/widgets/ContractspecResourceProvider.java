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
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.fordiac.ide.contractSpec.ContractSpecFactory;
import org.eclipse.fordiac.ide.contractSpec.Port;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.scoping.ContractSpecScopeProvider;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.ui.editor.embedded.EmbeddedEditorFactory;
import org.eclipse.xtext.ui.editor.embedded.IEditedResourceProvider;

@SuppressWarnings("restriction")
public class ContractspecResourceProvider implements IEditedResourceProvider {

	private static final URI SYNTHETIC_URI = URI.createURI("__synthetic.contract"); //$NON-NLS-1$
	private static final URI SYNTHETIC_URI_INTERFACE = URI.createURI("__synthetic_interface.contract"); //$NON-NLS-1$
	private static final IResourceServiceProvider SERVICE_PROVIDER = IResourceServiceProvider.Registry.INSTANCE
			.getResourceServiceProvider(SYNTHETIC_URI);

	private final FBNetworkElement fbElem;

	public ContractspecResourceProvider(final FBNetworkElement fbElem) {
		this.fbElem = fbElem;
	}

	@Override
	public XtextResource createResource() {
		final XtextResourceSet resourceSet = (XtextResourceSet) SERVICE_PROVIDER.get(ResourceSet.class);
		final XtextResource resource = SERVICE_PROVIDER.get(XtextResource.class);
		resource.setURI(SYNTHETIC_URI);
		resourceSet.getResources().add(resource);
		addFBInterface(resourceSet, fbElem);
		return resource;
	}

	static EmbeddedEditorFactory.Builder getEmbeddedEditorBuilder(final FBNetworkElement fbElem) {
		final IEditedResourceProvider resourceProvider = new ContractspecResourceProvider(fbElem);
		return SERVICE_PROVIDER.get(EmbeddedEditorFactory.class).newEditor(resourceProvider);
	}

	private static void addFBInterface(final ResourceSet set, final FBNetworkElement fbElem) {
		final Resource r = set.createResource(SYNTHETIC_URI_INTERFACE);
		ContractSpecScopeProvider.interfaceURI = SYNTHETIC_URI_INTERFACE;

		fbElem.getInterface().getInputs().forEach(ie -> {
			final Port p = ContractSpecFactory.eINSTANCE.createPort();
			p.setName(ie.getName());
			p.setIsInput(1);
			r.getContents().add(p);
		});
		fbElem.getInterface().getOutputs().forEach(ie -> {
			final Port p = ContractSpecFactory.eINSTANCE.createPort();
			p.setName(ie.getName());
			p.setIsInput(0);
			r.getContents().add(p);
		});
	}
}
