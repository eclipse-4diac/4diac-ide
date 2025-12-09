/*******************************************************************************
 * Copyright (c) 2022, 2023 Primetals Technologies Austria GmbH
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Hesam Rezaee, Martin Jobst
 *       - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.globalconstantseditor.ui.contentassist;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.structuredtextcore.ui.contentassist.STCoreProposalProvider;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.ui.editor.contentassist.ContentAssistContext;
import org.eclipse.xtext.ui.editor.contentassist.ICompletionProposalAcceptor;

/**
 * Represents a generated, default implementation of superclass {@link STCoreProposalProvider}.
 * Methods are dynamically dispatched on the first parameter, i.e., you can override them 
 * with a more concrete subtype. 
 */
public abstract class AbstractGlobalConstantsProposalProvider extends STCoreProposalProvider {

	public void completeSTGlobalConstsSource_Name(EObject model, Assignment assignment, ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		completeRuleCall(((RuleCall)assignment.getTerminal()), context, acceptor);
	}
	public void completeSTGlobalConstsSource_Imports(EObject model, Assignment assignment, ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		completeRuleCall(((RuleCall)assignment.getTerminal()), context, acceptor);
	}
	public void completeSTGlobalConstsSource_Constants(EObject model, Assignment assignment, ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		completeRuleCall(((RuleCall)assignment.getTerminal()), context, acceptor);
	}
	public void completeSTGlobalConstants_Name(EObject model, Assignment assignment, ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		completeRuleCall(((RuleCall)assignment.getTerminal()), context, acceptor);
	}
	public void completeSTGlobalConstants_Elements(EObject model, Assignment assignment, ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		completeRuleCall(((RuleCall)assignment.getTerminal()), context, acceptor);
	}
	public void completeSTVarGlobalDeclarationBlock_Constant(EObject model, Assignment assignment, ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		// subclasses may override
	}
	public void completeSTVarGlobalDeclarationBlock_VarDeclarations(EObject model, Assignment assignment, ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		completeRuleCall(((RuleCall)assignment.getTerminal()), context, acceptor);
	}

	public void complete_STGlobalConstsSource(EObject model, RuleCall ruleCall, ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		// subclasses may override
	}
	public void complete_STGlobalConstants(EObject model, RuleCall ruleCall, ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		// subclasses may override
	}
	public void complete_STVarGlobalDeclarationBlock(EObject model, RuleCall ruleCall, ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		// subclasses may override
	}
}
