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
package org.eclipse.fordiac.ide.structuredtextalgorithm.ui.editor.hyperlinking;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.hyperlinking.AbstractHyperlink;
import org.eclipse.xtext.ui.editor.hyperlinking.HyperlinkHelper;
import org.eclipse.xtext.ui.editor.hyperlinking.IHyperlinkAcceptor;
import org.eclipse.xtext.ui.editor.hyperlinking.XtextHyperlink;
import org.eclipse.xtext.util.TextRegion;

public class STAlgorithmHyperlinkHelper extends HyperlinkHelper {
	protected static class RenamingHyperlinkAcceptor implements IHyperlinkAcceptor {
		private final IHyperlinkAcceptor delegate;
		private final String text;

		protected RenamingHyperlinkAcceptor(final IHyperlinkAcceptor acceptor, final String text) {
			this.delegate = acceptor;
			this.text = text;
		}

		@Override
		public void accept(final IHyperlink hyperlink) {
			if (hyperlink instanceof AbstractHyperlink) {
				((AbstractHyperlink) hyperlink).setHyperlinkText(text);
			}
			delegate.accept(hyperlink);
		}
	}

	protected static class TrimmingHyperlinkAcceptor implements IHyperlinkAcceptor {
		private final IHyperlinkAcceptor delegate;

		protected TrimmingHyperlinkAcceptor(final IHyperlinkAcceptor acceptor) {
			this.delegate = acceptor;
		}

		@Override
		public void accept(final IHyperlink hyperlink) {
			if (hyperlink instanceof XtextHyperlink) {
				final XtextHyperlink xtextHyperlink = (XtextHyperlink) hyperlink;
				xtextHyperlink.setURI(xtextHyperlink.getURI().trimFragment());
			}
			delegate.accept(hyperlink);
		}
	}

	@Override
	public void createHyperlinksByOffset(final XtextResource resource, final int offset,
			final IHyperlinkAcceptor acceptor) {
		final INode node = getEObjectAtOffsetHelper().getCrossReferenceNode(resource, new TextRegion(offset, 0));
		if (node != null) {
			final EObject target = getEObjectAtOffsetHelper().getCrossReferencedElement(node);
			if (target != null && !target.eIsProxy()) {
				if (target instanceof FB) {
					createHyperlinksToType(resource, node, ((FB) target).getType(), acceptor);
				} else if (target instanceof Event && target.eContainer() instanceof InterfaceList
						&& target.eContainer().eContainer() instanceof FBType) {
					createHyperlinksToType(resource, node, (FBType) target.eContainer().eContainer(), acceptor);
				} else if (target instanceof AdapterDeclaration) {
					createHyperlinksToType(resource, node, ((AdapterDeclaration) target).getAdapterFB().getType(),
							acceptor);
				} else {
					createHyperlinksTo(resource, node, target, acceptor);
				}
			}
		}
	}

	protected void createHyperlinksToType(final XtextResource from, final INode node, final FBType target,
			final IHyperlinkAcceptor acceptor) {
		super.createHyperlinksTo(from, node, target,
				new RenamingHyperlinkAcceptor(new TrimmingHyperlinkAcceptor(acceptor),
						"Open Type - " + target.getName())); //$NON-NLS-1$
	}
}
