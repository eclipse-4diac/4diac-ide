/*******************************************************************************
 * Copyright (c) 2026 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring;

import java.io.IOException;
import java.util.List;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.ide.refactoring.RefactoringIssueAcceptor;
import org.eclipse.xtext.ide.refactoring.ResourceRelocationChange;
import org.eclipse.xtext.ide.refactoring.ResourceRelocationContext;
import org.eclipse.xtext.ide.serializer.IChangeSerializer;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("restriction")
public class STCoreResourceRelocationContext extends ResourceRelocationContext {

	public STCoreResourceRelocationContext(final ChangeType changeType, final List<ResourceRelocationChange> changes,
			final RefactoringIssueAcceptor issueAcceptor, final IChangeSerializer changeSerializer,
			final ResourceSet resourceSet) {
		super(changeType, changes, issueAcceptor, changeSerializer, resourceSet);
	}

	@Override
	protected Resource loadAndWatchResource(final ResourceRelocationChange change) {
		if (getChangeType() != null) {
			switch (getChangeType()) {
			case MOVE, RENAME:
				final Resource original = getResourceSet().getResource(change.getFromURI(), true);
				getChangeSerializer().addModification(original,
						(final Resource it) -> original.setURI(change.getToURI()));
				return original;
			case COPY:
				Resource copy = getResourceSet().getResource(change.getToURI(), false);
				if (copy != null) {
					return copy;
				}
				copy = getResourceSet().createResource(change.getToURI());
				try {
					copy.load(getResourceSet().getURIConverter().createInputStream(change.getFromURI()), null);
				} catch (final IOException e) {
					Exceptions.sneakyThrow(e);
				}
				return copy;
			default:
				return null;
			}
		}
		return null;
	}
}
