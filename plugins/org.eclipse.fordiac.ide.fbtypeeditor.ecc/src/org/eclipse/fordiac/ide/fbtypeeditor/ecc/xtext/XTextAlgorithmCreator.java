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

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editors.IAlgorithmEditor;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editors.IAlgorithmEditorCreator;
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
	public IAlgorithmEditor createAlgorithmEditor(Composite parent, final BaseFBType fbType) {
		IEditedResourceProvider resourceProvider = new IEditedResourceProvider() {

			@Override
			public XtextResource createResource() {
				XtextResourceSet resourceSet = resourceSetProvider.get();
				EcoreUtil.Copier copier = new EcoreUtil.Copier();
				Resource fbResource = resourceSet.createResource(computeUnusedUri(resourceSet, LINKING_FILE_EXTENSION));
				fbResource.getContents().add(copier.copy(EcoreUtil.getRootContainer(fbType)));
				for (AdapterDeclaration adapter : fbType.getInterfaceList().getSockets()) {
					createAdapterResource(resourceSet, copier, adapter);
				}
				for (AdapterDeclaration adapter : fbType.getInterfaceList().getPlugs()) {
					createAdapterResource(resourceSet, copier, adapter);
				}
				copier.copyReferences();
				return (XtextResource) resourceSet.createResource(computeUnusedUri(resourceSet, fileExtension));
			}

			private void createAdapterResource(XtextResourceSet resourceSet, EcoreUtil.Copier copier,
					AdapterDeclaration adapter) {
				Resource adapterResource = resourceSet
						.createResource(computeUnusedUri(resourceSet, LINKING_FILE_EXTENSION));
				copier.copy(adapter.getType());
				adapterResource.getContents()
						.add(copier.copy(EcoreUtil.getRootContainer(adapter.getType().getAdapterFBType())));
			}

			protected URI computeUnusedUri(ResourceSet resourceSet, String fileExtension) {
				String name = "__synthetic"; //$NON-NLS-1$
				for (int i = 0; i < Integer.MAX_VALUE; i++) {
					URI syntheticUri = URI.createURI(name + i + "." + fileExtension); //$NON-NLS-1$
					if (resourceSet.getResource(syntheticUri, false) == null) {
						return syntheticUri;
					}
				}
				throw new IllegalStateException();
			}
		};

		EmbeddedEditor editor = editorFactory.newEditor(resourceProvider).showErrorAndWarningAnnotations()
				.withParent(parent);
		return createXTextAlgorithmEditor(fbType, editor);
	}

	/**
	 * Factory method creating the Specific XTextAlgorithmEditor.
	 * 
	 * Should be overridden if you need a special XTextAlogrithm which performs
	 * additional setups for your DSL.
	 */
	protected XTextAlgorithmEditor createXTextAlgorithmEditor(BaseFBType fbType, EmbeddedEditor editor) {
		return new XTextAlgorithmEditor(editor, fbType);
	}

}
