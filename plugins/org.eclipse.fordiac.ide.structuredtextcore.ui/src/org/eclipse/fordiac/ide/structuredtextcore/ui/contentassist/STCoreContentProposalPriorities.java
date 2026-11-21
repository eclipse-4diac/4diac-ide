/**
 * Copyright (c) 2023 Primetals Technologies GmbH
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
 *   Franz Höpfinger
 *       - boost proposals whose declared type matches the expected type
 */
package org.eclipse.fordiac.ide.structuredtextcore.ui.contentassist;

import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.xtext.ui.editor.contentassist.ConfigurableCompletionProposal;
import org.eclipse.xtext.ui.editor.contentassist.ContentProposalPriorities;

public class STCoreContentProposalPriorities extends ContentProposalPriorities {

	/** Priority boost applied to a proposal whose declared type matches the expected type, so it sorts to the top. */
	private static final int TYPE_MATCH_PRIORITY_BOOST = 100;

	@Override
	protected void adjustPriority(final ICompletionProposal proposal, final String prefix, final int priority) {
		final var additionalData = proposal != null ? proposal.getAdditionalProposalInfo() : null;
		if (additionalData != null && !additionalData.contains("STStandardFunction") //$NON-NLS-1$
				&& !additionalData.contains("STFunction") //$NON-NLS-1$
				&& !additionalData.contains("org.eclipse.fordiac.ide.model.data")) { //$NON-NLS-1$
			super.adjustPriority(proposal, prefix, 2000);
		}
		super.adjustPriority(proposal, prefix, priority);
	}

	/**
	 * Boosts, but never lowers or removes, a proposal whose declared type matches
	 * (or is assignable to) the expected type at the completion position, so it
	 * sorts above otherwise equally ranked proposals.
	 */
	public void adjustTypeMatchPriority(final ICompletionProposal proposal, final boolean matchesExpectedType) {
		if (matchesExpectedType && proposal instanceof final ConfigurableCompletionProposal configurableProposal) {
			configurableProposal.setPriority(configurableProposal.getPriority() + TYPE_MATCH_PRIORITY_BOOST);
		}
	}

}
