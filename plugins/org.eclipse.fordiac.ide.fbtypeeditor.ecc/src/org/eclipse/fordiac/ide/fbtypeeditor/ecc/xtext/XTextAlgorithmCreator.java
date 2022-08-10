/*******************************************************************************
 * Copyright (c) 2014 - 2017 fortiss GmbH, TU Wien ACIN
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Monika Wenger, Martin Jobst, Martin Melik Merkumians
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.xtext;

import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editors.IAlgorithmEditor;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editors.IAlgorithmEditorCreator;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.xtext.Constants;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.ui.editor.embedded.EmbeddedEditor;
import org.eclipse.xtext.ui.editor.embedded.EmbeddedEditorFactory;
import org.eclipse.xtext.ui.editor.embedded.IEditedResourceProvider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

@SuppressWarnings("restriction")
public class XTextAlgorithmCreator implements IAlgorithmEditorCreator {

	private static final String LINKING_FILE_EXTENSION = "xtextfbt"; //$NON-NLS-1$

	@Inject
	private EmbeddedEditorFactory editorFactory;

	@Inject
	private Provider<XtextResourceSet> resourceSetProvider;

	@Inject
	@Named(Constants.FILE_EXTENSIONS)
	private String fileExtension;

	@Override
	public IAlgorithmEditor createAlgorithmEditor(final Composite parent, final BaseFBType fbType) {
		final IEditedResourceProvider resourceProvider = new IEditedResourceProvider() {

			@Override
			public XtextResource createResource() {
				final XtextResourceSet resourceSet = resourceSetProvider.get();
				final Resource fbResource = resourceSet.createResource(computeUnusedUri(resourceSet, LINKING_FILE_EXTENSION));
				fbResource.getContents().add(fbType);

				fbType.getInterfaceList().getSockets().forEach(adp -> createAdapterResource(resourceSet, adp));
				fbType.getInterfaceList().getPlugs().forEach(adp -> createAdapterResource(resourceSet, adp));
				if (fbType.getTypeLibrary() != null) {
					createStructResources(resourceSet,
							fbType.getTypeLibrary().getDataTypeLibrary().getStructuredTypes());
				}
				return (XtextResource) resourceSet.createResource(computeUnusedUri(resourceSet, fileExtension));
			}

			private void createStructResources(final XtextResourceSet resourceSet, final List<StructuredType> structuredTypes) {
				structuredTypes.forEach(struct -> {
					final Resource structResource = resourceSet
							.createResource(computeUnusedUri(resourceSet, LINKING_FILE_EXTENSION));
					structResource.getContents().add(struct);
				});
			}

			private void createAdapterResource(final XtextResourceSet resourceSet, final AdapterDeclaration adapter) {
				if (null != adapter.getType() && null != adapter.getAdapterType().getAdapterFBType()) {
					final Resource adapterResource = resourceSet
							.createResource(computeUnusedUri(resourceSet, LINKING_FILE_EXTENSION));
					adapterResource.getContents().add(adapter.getAdapterType().getAdapterFBType());
				}
			}

			protected URI computeUnusedUri(final ResourceSet resourceSet, final String fileExtension) {
				final String name = "__synthetic"; //$NON-NLS-1$
				for (int i = 0; i < Integer.MAX_VALUE; i++) {
					final URI syntheticUri = URI.createURI(name + i + "." + fileExtension); //$NON-NLS-1$
					if (resourceSet.getResource(syntheticUri, false) == null) {
						return syntheticUri;
					}
				}
				throw new IllegalStateException();
			}
		};

		final EmbeddedEditor editor = editorFactory.newEditor(resourceProvider).showErrorAndWarningAnnotations()
				.withParent(parent);
		return createXTextAlgorithmEditor(fbType, editor);
	}

	/**
	 * Factory method creating the Specific XTextAlgorithmEditor.
	 *
	 * Should be overridden if you need a special XTextAlogrithm which performs
	 * additional setups for your DSL.
	 */
	@SuppressWarnings("static-method") // has to be none static so that sub-classes can override
	protected XTextAlgorithmEditor createXTextAlgorithmEditor(final BaseFBType fbType, final EmbeddedEditor editor) {
		return new XTextAlgorithmEditor(editor, fbType);
	}

}
