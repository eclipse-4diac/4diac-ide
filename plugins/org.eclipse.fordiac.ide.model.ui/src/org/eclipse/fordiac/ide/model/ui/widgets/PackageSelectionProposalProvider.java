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
package org.eclipse.fordiac.ide.model.ui.widgets;

import java.util.Comparator;
import java.util.function.Supplier;

import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.jface.fieldassist.ContentProposal;
import org.eclipse.jface.fieldassist.IContentProposal;
import org.eclipse.jface.fieldassist.IContentProposalProvider;

public class PackageSelectionProposalProvider implements IContentProposalProvider {

	private final Supplier<TypeLibrary> supplier;

	public PackageSelectionProposalProvider(final Supplier<TypeLibrary> supplier) {
		this.supplier = supplier;
	}

	@Override
	public IContentProposal[] getProposals(final String contents, final int position) {
		final TypeLibrary typeLibrary = supplier.get();
		if (typeLibrary != null) {
			return typeLibrary.getPackages().stream().filter(proposal -> matches(proposal, contents, position))
					.map(this::createProposal).sorted(Comparator.comparing(IContentProposal::getLabel))
					.toArray(IContentProposal[]::new);
		}
		return new IContentProposal[0];
	}

	protected static boolean matches(final String name, final String prefix, final int position) {
		return name.regionMatches(true, 0, prefix, 0, position);
	}

	protected ContentProposal createProposal(final String packageName) {
		return new ContentProposal(packageName);
	}
}
