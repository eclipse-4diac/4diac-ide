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
 */
package org.eclipse.fordiac.ide.structuredtextcore.ui.contentassist;

import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.xtext.ui.editor.contentassist.ContentProposalPriorities;

public class STCoreContentProposalPriorities extends ContentProposalPriorities {
	@Override
	protected void adjustPriority(final ICompletionProposal proposal, final String prefix, final int priority) {
		final var additionalData = proposal != null ? proposal.getAdditionalProposalInfo() : null;
		if (additionalData != null && !additionalData.contains("STStandardFunction")
				&& !additionalData.contains("STFunction")
				&& !additionalData.contains("org.eclipse.fordiac.ide.model.data")) {
			super.adjustPriority(proposal, prefix, 2000);
		}
		super.adjustPriority(proposal, prefix, priority);
	}

}
