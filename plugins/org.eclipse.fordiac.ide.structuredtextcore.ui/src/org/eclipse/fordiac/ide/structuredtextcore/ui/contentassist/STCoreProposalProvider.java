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

import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.globalconstantseditor.globalConstants.STVarGlobalDeclarationBlock;
import org.eclipse.fordiac.ide.model.data.AnyDerivedType;
import org.eclipse.fordiac.ide.model.eval.value.ValueOperations;
import org.eclipse.fordiac.ide.model.libraryElement.ICallable;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStandardFunction;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
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
import com.google.common.base.Predicate;
import com.google.common.collect.Streams;

/** See https://www.eclipse.org/Xtext/documentation/310_eclipse_support.html#content-assist on how to customize the
 * content assistant. */
public class STCoreProposalProvider extends AbstractSTCoreProposalProvider {

	@Override
	public void completeSTFeatureExpression_Feature(final EObject model, final Assignment assignment,
			final ContentAssistContext context, final ICompletionProposalAcceptor acceptor) {
		lookupCrossReference((CrossReference) assignment.getTerminal(), context, acceptor,
				(Predicate<IEObjectDescription>) desc -> isVisible(desc, context));
	}

	@SuppressWarnings("static-method") // subclasses may override
	protected boolean isVisible(final IEObjectDescription description, final ContentAssistContext context) {
		return description.getName().getSegmentCount() == 1
				|| (STCorePackage.eINSTANCE.getSTVarDeclaration().equals(description.getEClass())
						&& EcoreUtil.resolve(description.getEObjectOrProxy(), context.getResource())
								.eContainer() instanceof STVarGlobalDeclarationBlock)
				|| LibraryElementPackage.eINSTANCE.getLibraryElement().isSuperTypeOf(description.getEClass());
	}

	@Override
	protected StyledString getStyledDisplayString(final EObject element, final String qualifiedNameAsString,
			final String shortName) {
		if (!element.eIsProxy() && element instanceof final ICallable callable) {
			final StyledString result = new StyledString(shortName).append('(')
					.append(Streams
							.concat(callable.getInputParameters().stream(), callable.getInOutParameters().stream(),
									callable.getOutputParameters().stream())
							.map(getLabelProvider()::getText).collect(Collectors.joining(", "))); //$NON-NLS-1$
			if (callable instanceof final STStandardFunction standardFunction && standardFunction.isVarargs()) {
				result.append(" ..."); //$NON-NLS-1$
			}
			result.append(')');
			if (callable.getReturnType() != null) {
				result.append(" : ").append(callable.getReturnType().getName()); //$NON-NLS-1$
			}
			final QualifiedName qualifiedName = getQualifiedNameConverter().toQualifiedName(qualifiedNameAsString);
			if (qualifiedName.getSegmentCount() > 1) {
				result.append(" - " + qualifiedNameAsString, StyledString.QUALIFIER_STYLER); //$NON-NLS-1$
			}
			return result;
		}
		return super.getStyledDisplayString(element, qualifiedNameAsString, shortName);
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
		private final IQualifiedNameConverter qualifiedNameConverter;

		public STCoreProposalCreator(final ContentAssistContext contentAssistContext, final String ruleName,
				final IQualifiedNameConverter qualifiedNameConverter) {
			super(contentAssistContext, ruleName, qualifiedNameConverter);
			this.contentAssistContext = contentAssistContext;
			this.qualifiedNameConverter = qualifiedNameConverter;
		}

		@Override
		public ICompletionProposal apply(final IEObjectDescription candidate) {
			final ICompletionProposal result = super.apply(candidate);
			if (result instanceof final STCoreConfigurableCompletionProposal configurableResult
					&& isCallableDescription(candidate) && EcoreUtil.resolve(candidate.getEObjectOrProxy(),
							contentAssistContext.getResource()) instanceof final ICallable callable) {
				final String nameProposal = configurableResult.getReplacementString();
				final int replacementOffset = configurableResult.getReplacementOffset();
				final STCoreProposalString proposal = getCallableParameterProposal(callable, nameProposal);
				configurableResult.setReplacementString(proposal.toString());
				configurableResult.setDisplayString(getStyledDisplayString(callable,
						qualifiedNameConverter.toString(candidate.getQualifiedName()), callable.getName()));
				if (proposal.getRegions().isEmpty()) {
					configurableResult.setCursorPosition(proposal.length());
					configurableResult.setSelectionStart(replacementOffset + proposal.length() - 1);
					configurableResult.setSelectionLength(0);
					configurableResult.setSimpleLinkedMode(contentAssistContext.getViewer(), ')');
				} else {
					final ITextRegion firstRegion = proposal.getRegions().get(0);
					configurableResult.setCursorPosition(firstRegion.getOffset() + firstRegion.getLength());
					configurableResult.setSelectionStart(replacementOffset + firstRegion.getOffset());
					configurableResult.setSelectionLength(firstRegion.getLength());
					configurableResult.setCustomLinkedMode(contentAssistContext.getViewer(),
							proposal.getRegions(replacementOffset), ')');
				}
			}
			return result;
		}

		protected STCoreProposalString getCallableParameterProposal(final ICallable callable,
				final String nameProposal) {
			if (callable instanceof final STStandardFunction standardFunction && standardFunction.isVarargs()) {
				return new STCoreProposalString(nameProposal).append("()"); //$NON-NLS-1$
			}
			return new STCoreProposalString(nameProposal).concat(Streams
					.concat(callable.getInputParameters().stream(), callable.getInOutParameters().stream(),
							callable.getOutputParameters().stream())
					.map(parameter -> getCallableParameterProposal(callable, parameter))
					.collect(STCoreProposalStringCollectors.joining(", ", "(", ")"))); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}

		protected STCoreProposalString getCallableParameterProposal(final ICallable callable,
				final INamedElement parameter) {
			if (callable instanceof STStandardFunction) { // non-formal call for standard functions
				return new STCoreProposalString(getCallableParameterDefaultValue(callable, parameter), true);
			}
			return new STCoreProposalString(parameter.getName())
					.append(callable.getOutputParameters().contains(parameter) ? " => " : " := ") //$NON-NLS-1$ //$NON-NLS-2$
					.append(getCallableParameterDefaultValue(callable, parameter), true);
		}

		protected String getCallableParameterDefaultValue(final ICallable callable, final INamedElement parameter) {
			if (callable.getInOutParameters().contains(parameter)
					|| callable.getOutputParameters().contains(parameter)) {
				return "VAR"; //$NON-NLS-1$
			}
			if (parameter instanceof final VarDeclaration varDeclaration) {
				return getCallableParameterTypeDefaultValue(varDeclaration.getType());
			}
			if (parameter instanceof final STVarDeclaration stVarDeclaration) {
				return getCallableParameterTypeDefaultValue(stVarDeclaration.getType());
			}
			return ""; //$NON-NLS-1$
		}

		protected String getCallableParameterTypeDefaultValue(final INamedElement type) {
			if (type instanceof AnyDerivedType) {
				return "VAR"; //$NON-NLS-1$
			}
			try {
				return ValueOperations.defaultValue(type).toString();
			} catch (final Exception e) {
				FordiacLogHelper.logWarning(e.getMessage(), e);
			}
			return ""; //$NON-NLS-1$
		}

		protected boolean isCallableDescription(final IEObjectDescription description) {
			return description != null && description.getEClass() != null
					&& LibraryElementPackage.eINSTANCE.getICallable().isSuperTypeOf(description.getEClass());
		}
	}
}
