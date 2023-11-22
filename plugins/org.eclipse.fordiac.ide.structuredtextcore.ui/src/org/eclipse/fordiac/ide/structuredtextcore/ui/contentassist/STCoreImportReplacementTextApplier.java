/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextcore.ui.contentassist;

import java.util.List;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STSource;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentRewriteSession;
import org.eclipse.jface.text.DocumentRewriteSessionType;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentExtension4;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.contentassist.ConfigurableCompletionProposal;
import org.eclipse.xtext.ui.editor.contentassist.ConfigurableCompletionProposal.IReplacementTextApplier;

public class STCoreImportReplacementTextApplier implements IReplacementTextApplier {

	private final XtextResource resource;
	private final String importedNamespace;

	public STCoreImportReplacementTextApplier(final XtextResource resource, final String importedNamespace) {
		this.resource = resource;
		this.importedNamespace = importedNamespace;
	}

	@Override
	public void apply(final IDocument document, final ConfigurableCompletionProposal proposal)
			throws BadLocationException {
		if (document instanceof final IDocumentExtension4 documentExtension) {
			final DocumentRewriteSession rewriteSession = documentExtension
					.startRewriteSession(DocumentRewriteSessionType.UNRESTRICTED_SMALL);
			try {
				applyReplacementString(document, proposal);
				applyImport(document, proposal);
			} finally {
				documentExtension.stopRewriteSession(rewriteSession);
			}
		} else {
			applyReplacementString(document, proposal);
			applyImport(document, proposal);
		}
	}

	@SuppressWarnings("static-method") // subclasses may override
	protected void applyReplacementString(final IDocument document, final ConfigurableCompletionProposal proposal)
			throws BadLocationException {
		document.replace(proposal.getReplacementOffset(), proposal.getReplacementLength(),
				proposal.getReplacementString());
	}

	protected void applyImport(final IDocument document, final ConfigurableCompletionProposal proposal)
			throws BadLocationException {
		final STSource source = getSource();
		final StringBuilder importString = getImportString();
		int offset = findImportOffset(source);
		if (offset > 0) {
			importString.insert(0, System.lineSeparator());
		} else if ((offset = findPackageOffset(source)) > 0) {
			importString.insert(0, System.lineSeparator());
			importString.insert(0, System.lineSeparator());
		} else {
			importString.append(System.lineSeparator());
			importString.append(System.lineSeparator());
		}
		document.replace(offset, 0, importString.toString());
		proposal.shiftOffset(importString.length());
	}

	protected StringBuilder getImportString() {
		final StringBuilder result = new StringBuilder();
		result.append("IMPORT"); //$NON-NLS-1$
		result.append(' ');
		result.append(getImportedNamespace());
		result.append(';');
		return result;
	}

	protected int findPackageOffset(final STSource source) {
		final List<INode> nodes = NodeModelUtils.findNodesForFeature(source, getPackageFeature(source));
		if (!nodes.isEmpty()) {
			INode last = nodes.get(nodes.size() - 1);
			while (!";".equals(last.getText())) { //$NON-NLS-1$
				last = last.getNextSibling();
			}
			return last.getEndOffset();
		}
		return 0;
	}

	protected int findImportOffset(final STSource source) {
		final List<INode> importNodes = NodeModelUtils.findNodesForFeature(source, getImportsFeature(source));
		if (!importNodes.isEmpty()) {
			return importNodes.get(importNodes.size() - 1).getEndOffset();
		}
		return 0;
	}

	@SuppressWarnings("static-method") // subclasses may override
	protected EStructuralFeature getPackageFeature(final STSource source) {
		final EStructuralFeature packageFeature = source.eClass().getEStructuralFeature("name"); //$NON-NLS-1$
		if (packageFeature != null && packageFeature.getEType() == EcorePackage.eINSTANCE.getEString()
				&& !packageFeature.isMany()) {
			return packageFeature;
		}
		return null;
	}

	@SuppressWarnings("static-method") // subclasses may override
	protected EStructuralFeature getImportsFeature(final STSource source) {
		final EStructuralFeature importsFeature = source.eClass().getEStructuralFeature("imports"); //$NON-NLS-1$
		if (importsFeature != null && importsFeature.getEType() == STCorePackage.eINSTANCE.getSTImport()
				&& importsFeature.isMany()) {
			return importsFeature;
		}
		return null;
	}

	protected STSource getSource() {
		return resource.getContents().stream().filter(STSource.class::isInstance).map(STSource.class::cast).findFirst()
				.orElse(null);
	}

	public XtextResource getResource() {
		return resource;
	}

	public String getImportedNamespace() {
		return importedNamespace;
	}
}
