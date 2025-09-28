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
package org.eclipse.fordiac.ide.ui.utils;

import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.fordiac.ide.contractSpec.ContractSpecFactory;
import org.eclipse.fordiac.ide.contractSpec.Port;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
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

	private final BlockFBNetworkElement fbElem;
	private final List<String> inPorts;
	private final List<String> outPorts;

	public ContractspecResourceProvider(final BlockFBNetworkElement fbElem) {
		this.fbElem = fbElem;
		inPorts = null;
		outPorts = null;
	}

	public ContractspecResourceProvider(final List<String> inPorts, final List<String> outPorts) {
		this.fbElem = null;
		this.inPorts = inPorts;
		this.outPorts = outPorts;
	}

	@Override
	public XtextResource createResource() {
		final XtextResourceSet resourceSet = (XtextResourceSet) SERVICE_PROVIDER.get(ResourceSet.class);
		final XtextResource resource = SERVICE_PROVIDER.get(XtextResource.class);
		resource.setURI(SYNTHETIC_URI);
		resourceSet.getResources().add(resource);

		final Resource resInter = resourceSet.createResource(SYNTHETIC_URI_INTERFACE);
		ContractSpecScopeProvider.setInterfaceURI(SYNTHETIC_URI_INTERFACE);
		if (fbElem != null) {
			addFBInterface(resInter, fbElem);
		} else {
			addFBInterface(resInter, inPorts, outPorts);
		}
		return resource;
	}

	public static EmbeddedEditorFactory.Builder getEmbeddedEditorBuilder(final BlockFBNetworkElement fbElem) {
		final IEditedResourceProvider resourceProvider = new ContractspecResourceProvider(fbElem);
		return SERVICE_PROVIDER.get(EmbeddedEditorFactory.class).newEditor(resourceProvider);
	}

	private static void addFBInterface(final Resource res, final BlockFBNetworkElement fbElem) {
		fbElem.getInterface().getInputs().forEach(ie -> createPort(res, ie.getName(), true));
		fbElem.getInterface().getOutputs().forEach(oe -> createPort(res, oe.getName(), false));
	}

	private static void addFBInterface(final Resource res, final List<String> inputs, final List<String> outputs) {
		if (inputs != null) {
			for (final String ie : inputs) {
				createPort(res, ie, true);
			}
		}
		if (outputs != null) {
			for (final String oe : outputs) {
				createPort(res, oe, false);
			}
		}
	}

	private static void createPort(final Resource res, final String name, final boolean isInput) {
		final Port p = ContractSpecFactory.eINSTANCE.createPort();
		p.setName(name);
		p.setIsInput(isInput ? 1 : 0);
		res.getContents().add(p);
	}
}
