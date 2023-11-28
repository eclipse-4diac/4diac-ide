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
package org.eclipse.fordiac.ide.structuredtextalgorithm.ui.contentassist;

import org.eclipse.fordiac.ide.structuredtextcore.ui.contentassist.STCoreProposalProvider.STCoreReferenceProposalCreator;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.contentassist.ConfigurableCompletionProposal.IReplacementTextApplier;

public class STAlgorithmReferenceProposalCreator extends STCoreReferenceProposalCreator {

	@Override
	protected IReplacementTextApplier getImportReplacementTextApplier(final XtextResource resource,
			final String importedNamespace) {
		return new STAlgorithmImportReplacementTextApplier(resource, importedNamespace);
	}
}
