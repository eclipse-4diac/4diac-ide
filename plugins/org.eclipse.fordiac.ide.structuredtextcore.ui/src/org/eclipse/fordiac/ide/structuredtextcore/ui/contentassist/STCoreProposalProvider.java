/**
 * Copyright (c) 2021 Primetals Technologies GmbH
 *               2023 Martin Erich Jobst
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
 *   Martin Jobst
 *       - exclude proposals based disallowing qualified names only
 *       - add proposal for callables
 *       - enable proposals for global variables in packages
 */
package org.eclipse.fordiac.ide.structuredtextcore.ui.contentassist;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.globalconstantseditor.globalConstants.GlobalConstantsPackage;
import org.eclipse.fordiac.ide.globalconstantseditor.globalConstants.STVarGlobalDeclarationBlock;
import org.eclipse.fordiac.ide.model.libraryElement.ICallable;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.structuredtextcore.resource.STCoreResourceDescriptionStrategy;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage;
import org.eclipse.fordiac.ide.structuredtextcore.util.STCoreRegionString;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.CrossReference;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.ui.editor.contentassist.ConfigurableCompletionProposal;
import org.eclipse.xtext.ui.editor.contentassist.ContentAssistContext;
import org.eclipse.xtext.ui.editor.contentassist.ICompletionProposalAcceptor;
import org.eclipse.xtext.util.ITextRegion;

import com.google.common.base.Function;

/**
 * See
 * https://www.eclipse.org/Xtext/documentation/310_eclipse_support.html#content-assist
 * on how to customize the content assistant.
 */
public class STCoreProposalProvider extends AbstractSTCoreProposalProvider {

	@Override
	public void completeSTFeatureExpression_Feature(final EObject model, final Assignment assignment,
			final ContentAssistContext context, final ICompletionProposalAcceptor acceptor) {
		lookupCrossReference((CrossReference) assignment.getTerminal(), context, acceptor, this::isVisible);
	}

	protected boolean isVisible(final IEObjectDescription description) {
		return description.getName().getSegmentCount() == 1
				|| LibraryElementPackage.eINSTANCE.getLibraryElement().isSuperTypeOf(description.getEClass())
				|| (STCorePackage.eINSTANCE.getSTVarDeclaration().equals(description.getEClass())
						&& isGlobalVariable(description));
	}

	protected static boolean isGlobalVariable(final IEObjectDescription description) {
		final String containerEClassName = description
				.getUserData(STCoreResourceDescriptionStrategy.CONTAINER_ECLASS_NAME);
		if (containerEClassName != null) {
			return GlobalConstantsPackage.eINSTANCE.getSTVarGlobalDeclarationBlock().getName()
					.equals(containerEClassName);
		}
		return description.getEObjectOrProxy().eContainer() instanceof STVarGlobalDeclarationBlock;
	}

	@Override
	protected StyledString getStyledDisplayString(final IEObjectDescription description) {
		final String signature = description.getUserData(STCoreResourceDescriptionStrategy.DISPLAY_STRING);
		if (signature != null) {
			final StyledString result = new StyledString(signature);
			if (description.getQualifiedName().getSegmentCount() > 1) {
				result.append(" - " + getQualifiedNameConverter().toString(description.getQualifiedName()), //$NON-NLS-1$
						StyledString.QUALIFIER_STYLER);
			}
			return result;
		}
		return super.getStyledDisplayString(description);
	}

	@Override
	protected StyledString getStyledDisplayString(final EObject element, final String qualifiedNameAsString,
			final String shortName) {
		if (!element.eIsProxy() && element instanceof final ICallable callable) {
			final StyledString result = new StyledString(
					STCoreResourceDescriptionStrategy.getCallableDisplayString(callable));
			final QualifiedName qualifiedName = getQualifiedNameConverter().toQualifiedName(qualifiedNameAsString);
			if (qualifiedName.getSegmentCount() > 1) {
				result.append(" - " + qualifiedNameAsString, StyledString.QUALIFIER_STYLER); //$NON-NLS-1$
			}
			return result;
		}
		return super.getStyledDisplayString(element, qualifiedNameAsString, shortName);
	}

	@Override
	protected String getDisplayString(final EObject element, final String qualifiedNameAsString,
			final String shortName) {
		if (!element.eIsProxy() && element instanceof final ICallable callable) {
			final StringBuilder result = new StringBuilder(
					STCoreResourceDescriptionStrategy.getCallableDisplayString(callable));
			final QualifiedName qualifiedName = getQualifiedNameConverter().toQualifiedName(qualifiedNameAsString);
			if (qualifiedName.getSegmentCount() > 1) {
				result.append(" - " + qualifiedNameAsString); //$NON-NLS-1$
			}
			return result.toString();
		}
		return super.getDisplayString(element, qualifiedNameAsString, shortName);
	}

	@Override
	protected ConfigurableCompletionProposal doCreateProposal(final String proposal, final StyledString displayString,
			final Image image, final int replacementOffset, final int replacementLength) {
		return new STCoreConfigurableCompletionProposal(proposal, replacementOffset, replacementLength,
				proposal.length(), image, displayString, null, null);
	}

	@Override
	protected Function<IEObjectDescription, ICompletionProposal> getProposalFactory(final String ruleName,
			final ContentAssistContext contentAssistContext) {
		return new STCoreProposalCreator(contentAssistContext, ruleName, getQualifiedNameConverter());
	}

	protected class STCoreProposalCreator extends DefaultProposalCreator {

		private final ContentAssistContext contentAssistContext;

		public STCoreProposalCreator(final ContentAssistContext contentAssistContext, final String ruleName,
				final IQualifiedNameConverter qualifiedNameConverter) {
			super(contentAssistContext, ruleName, qualifiedNameConverter);
			this.contentAssistContext = contentAssistContext;
		}

		@Override
		public ICompletionProposal apply(final IEObjectDescription candidate) {
			final ICompletionProposal result = super.apply(candidate);
			if (result instanceof final STCoreConfigurableCompletionProposal configurableResult
					&& isCallableDescription(candidate)) {
				final String nameProposal = configurableResult.getReplacementString();
				final int replacementOffset = configurableResult.getReplacementOffset();

				final String parameterProposalData = candidate
						.getUserData(STCoreResourceDescriptionStrategy.PARAMETER_PROPOSAL);
				final String parameterProposalRegionsData = candidate
						.getUserData(STCoreResourceDescriptionStrategy.PARAMETER_PROPOSAL_REGIONS);

				final String parameterProposal;
				final List<ITextRegion> parameterProposalRegions;
				if (parameterProposalData != null && parameterProposalRegionsData != null) {
					parameterProposal = parameterProposalData;
					parameterProposalRegions = STCoreRegionString.parseRegions(parameterProposalRegionsData,
							replacementOffset + nameProposal.length());
				} else if (candidate.getEObjectOrProxy() instanceof final ICallable callable && !callable.eIsProxy()) {
					final STCoreRegionString parameterProposalString = STCoreResourceDescriptionStrategy
							.getCallableParameterProposal(callable);
					parameterProposal = parameterProposalString.toString();
					parameterProposalRegions = parameterProposalString
							.getRegions(replacementOffset + nameProposal.length());
				} else {
					parameterProposal = "()"; //$NON-NLS-1$
					parameterProposalRegions = Collections.emptyList();
				}

				final String proposal = nameProposal + parameterProposal;
				configurableResult.setReplacementString(proposal);
				if (parameterProposalRegions.isEmpty()) {
					configurableResult.setCursorPosition(proposal.length());
					configurableResult.setSelectionStart(replacementOffset + proposal.length() - 1);
					configurableResult.setSelectionLength(0);
					configurableResult.setSimpleLinkedMode(contentAssistContext.getViewer(), ')');
				} else {
					final ITextRegion firstRegion = parameterProposalRegions.get(0);
					configurableResult
							.setCursorPosition(firstRegion.getOffset() + firstRegion.getLength() - replacementOffset);
					configurableResult.setSelectionStart(firstRegion.getOffset());
					configurableResult.setSelectionLength(firstRegion.getLength());
					configurableResult.setCustomLinkedMode(contentAssistContext.getViewer(), parameterProposalRegions,
							')');
				}
			}
			return result;
		}

		protected boolean isCallableDescription(final IEObjectDescription description) {
			return description != null && description.getEClass() != null
					&& LibraryElementPackage.eINSTANCE.getICallable().isSuperTypeOf(description.getEClass());
		}
	}
}
