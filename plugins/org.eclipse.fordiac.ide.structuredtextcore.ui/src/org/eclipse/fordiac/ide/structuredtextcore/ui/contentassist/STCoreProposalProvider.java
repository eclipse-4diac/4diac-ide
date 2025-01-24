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
import org.eclipse.emf.ecore.EReference;
import org.eclipse.fordiac.ide.globalconstantseditor.globalConstants.GlobalConstantsPackage;
import org.eclipse.fordiac.ide.globalconstantseditor.globalConstants.STVarGlobalDeclarationBlock;
import org.eclipse.fordiac.ide.model.helpers.ImportHelper;
import org.eclipse.fordiac.ide.model.libraryElement.ICallable;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.structuredtextcore.resource.STCoreResourceDescriptionStrategy;
import org.eclipse.fordiac.ide.structuredtextcore.services.STCoreGrammarAccess;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STImport;
import org.eclipse.fordiac.ide.structuredtextcore.util.STCoreRegionString;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.CrossReference;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.formatting.IWhitespaceInformationProvider;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.impl.AliasedEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.ui.editor.contentassist.ConfigurableCompletionProposal;
import org.eclipse.xtext.ui.editor.contentassist.ConfigurableCompletionProposal.IReplacementTextApplier;
import org.eclipse.xtext.ui.editor.contentassist.ContentAssistContext;
import org.eclipse.xtext.ui.editor.contentassist.ICompletionProposalAcceptor;
import org.eclipse.xtext.util.ITextRegion;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.inject.Inject;

/**
 * See
 * https://www.eclipse.org/Xtext/documentation/310_eclipse_support.html#content-assist
 * on how to customize the content assistant.
 */
@SuppressWarnings("java:S4738")
public class STCoreProposalProvider extends AbstractSTCoreProposalProvider {

	@Inject
	private STCoreGrammarAccess grammarAccess;

	@Override
	public void completeKeyword(final Keyword keyword, final ContentAssistContext contentAssistContext,
			final ICompletionProposalAcceptor acceptor) {
		if (isKeywordWorthyToPropose(keyword)) {
			super.completeKeyword(keyword, contentAssistContext, acceptor);
		}
	}

	protected static boolean isKeywordWorthyToPropose(final Keyword keyword) {
		return keyword.getValue().length() > 1 && Character.isLetter(keyword.getValue().charAt(0));
	}

	@Override
	public void completeSTFeatureExpression_Feature(final EObject model, final Assignment assignment,
			final ContentAssistContext context, final ICompletionProposalAcceptor acceptor) {
		lookupCrossReference((CrossReference) assignment.getTerminal(), context, acceptor, this::isVisible);
	}

	@Override
	public void completeSTImport_ImportedNamespace(final EObject model, final Assignment assignment,
			final ContentAssistContext context, final ICompletionProposalAcceptor acceptor) {
		createReferenceImportProposals(model, context, acceptor);
		createWildcardImportProposals(model, context, acceptor);
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

	protected void createPackageProposals(final EObject model, final ContentAssistContext context,
			final ICompletionProposalAcceptor acceptor) {
		final TypeLibrary typeLibrary = TypeLibraryManager.INSTANCE.getTypeLibraryFromContext(model);
		if (typeLibrary != null) {
			for (final String packageName : typeLibrary.getPackages()) {
				if (!acceptor.canAcceptMoreProposals()) {
					return;
				}
				acceptor.accept(createPackageProposal(packageName, context));
			}
		}
	}

	protected ICompletionProposal createPackageProposal(final String packageName, final ContentAssistContext context) {
		final ICompletionProposal result = createCompletionProposal(packageName, new StyledString(packageName),
				PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FOLDER), context);
		getPriorityHelper().adjustCrossReferencePriority(result, context.getPrefix());
		return result;
	}

	protected void createReferenceImportProposals(final EObject model, final ContentAssistContext context,
			final ICompletionProposalAcceptor acceptor) {
		getCrossReferenceProposalCreator().lookupCrossReference(model,
				STCorePackage.Literals.ST_FEATURE_EXPRESSION__FEATURE, acceptor,
				getCrossReferenceProposalCreator()::isImportableDescription,
				getProposalFactory(grammarAccess.getQualifiedNameWithWildcardRule().getName(), context));
		getCrossReferenceProposalCreator().lookupCrossReference(model, STCorePackage.Literals.ST_VAR_DECLARATION__TYPE,
				acceptor, getCrossReferenceProposalCreator()::isImportableDescription,
				getProposalFactory(grammarAccess.getQualifiedNameWithWildcardRule().getName(), context));
	}

	protected void createWildcardImportProposals(final EObject model, final ContentAssistContext context,
			final ICompletionProposalAcceptor acceptor) {
		final TypeLibrary typeLibrary = TypeLibraryManager.INSTANCE.getTypeLibraryFromContext(model);
		if (typeLibrary != null) {
			createSimpleProposals(typeLibrary.getPackages(), acceptor, value -> createWildcardImportProposal(value,
					PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FOLDER), context));
			createSimpleProposals(typeLibrary.getGlobalConstants().stream().map(TypeEntry::getFullTypeName).toList(),
					acceptor, value -> createWildcardImportProposal(value,
							PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FILE), context));
		}
	}

	protected ICompletionProposal createWildcardImportProposal(final String value, final Image image,
			final ContentAssistContext context) {
		final String proposal = value + ImportHelper.WILDCARD_IMPORT_SUFFIX;
		return createCompletionProposal(proposal, new StyledString(proposal), image, context);
	}

	protected static void createSimpleProposals(final Iterable<String> values,
			final ICompletionProposalAcceptor acceptor, final Function<String, ICompletionProposal> proposalFactory) {
		for (final String value : values) {
			if (!acceptor.canAcceptMoreProposals()) {
				return;
			}
			acceptor.accept(proposalFactory.apply(value));
		}
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
			final StyledString result = new StyledString(callable.getSignature());
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
			final StringBuilder result = new StringBuilder(callable.getSignature());
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
		private final String ruleName;

		protected STCoreProposalCreator(final ContentAssistContext contentAssistContext, final String ruleName,
				final IQualifiedNameConverter qualifiedNameConverter) {
			super(contentAssistContext, ruleName, qualifiedNameConverter);
			this.contentAssistContext = contentAssistContext;
			this.ruleName = ruleName;
		}

		@Override
		public ICompletionProposal apply(final IEObjectDescription candidate) {
			final ICompletionProposal result = super.apply(candidate);
			if (result instanceof final STCoreConfigurableCompletionProposal configurableResult
					&& isCallableDescription(candidate) && shouldAddParameters()) {
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
							replacementOffset + proposal.length(), ')');
				}
			}
			return result;
		}

		protected boolean isCallableDescription(final IEObjectDescription description) {
			return description != null && description.getEClass() != null
					&& LibraryElementPackage.eINSTANCE.getICallable().isSuperTypeOf(description.getEClass());
		}

		protected boolean shouldAddParameters() {
			return grammarAccess.getSTFeatureNameRule().getName().equals(ruleName);
		}
	}

	public static class STCoreReferenceProposalCreator extends ReferenceProposalCreator {

		@Inject
		private IQualifiedNameConverter qualifiedNameConverter;

		@Inject
		private IWhitespaceInformationProvider whitespaceInformationProvider;

		@Override
		public void lookupCrossReference(final IScope scope, final EObject model, final EReference reference,
				final ICompletionProposalAcceptor acceptor, final Predicate<IEObjectDescription> filter,
				final Function<IEObjectDescription, ICompletionProposal> proposalFactory) {
			final Function<IEObjectDescription, ICompletionProposal> wrappedFactory = getWrappedFactory(model,
					reference, proposalFactory);
			final Function<IEObjectDescription, ICompletionProposal> wrappedImportingFactory = getWrappedImportingFactory(
					model, reference, proposalFactory);
			final Iterable<IEObjectDescription> candidates = queryScope(scope, model, reference, filter);
			for (final IEObjectDescription candidate : candidates) {
				if (!acceptor.canAcceptMoreProposals()) {
					return;
				}
				if (filter.apply(candidate)) {
					if (shouldImport(scope, model, reference, candidate)) {
						acceptor.accept(wrappedImportingFactory.apply(candidate));
					} else {
						acceptor.accept(wrappedFactory.apply(candidate));
					}
				}
			}
		}

		protected boolean shouldImport(final IScope scope, final EObject model, final EReference reference,
				final IEObjectDescription candidate) {
			return isImportableDescription(candidate) && !hasConflictingName(scope, candidate)
					&& !isImport(model, reference);
		}

		protected boolean isImportableDescription(final IEObjectDescription description) {
			return description != null && description.getEClass() != null
			// library element with package (more than one segment)
					&& ((LibraryElementPackage.eINSTANCE.getLibraryElement().isSuperTypeOf(description.getEClass())
							&& description.getName().getSegmentCount() > 1)
							// global constant with package on enclosing type (more than two segments)
							|| (isGlobalVariable(description) && description.getName().getSegmentCount() > 2));
		}

		@SuppressWarnings("static-method") // subclasses may override
		protected boolean hasConflictingName(final IScope scope, final IEObjectDescription description) {
			return scope.getSingleElement(getAlias(description)) != null;
		}

		@SuppressWarnings("static-method") // subclasses may override
		protected boolean isImport(final EObject model, final EReference reference) {
			return model instanceof STImport;
		}

		@SuppressWarnings("java:S1172")
		protected Function<IEObjectDescription, ICompletionProposal> getWrappedImportingFactory(final EObject model,
				final EReference reference, final Function<IEObjectDescription, ICompletionProposal> proposalFactory) {
			return description -> {
				final ICompletionProposal proposal = proposalFactory.apply(createImportedDescription(description));
				if (proposal instanceof final ConfigurableCompletionProposal configurableProposal) {
					configurableProposal.setTextApplier(getImportReplacementTextApplier(model, description));
				}
				return proposal;
			};
		}

		protected IReplacementTextApplier getImportReplacementTextApplier(final EObject model,
				final IEObjectDescription candidate) {
			return getImportReplacementTextApplier((XtextResource) model.eResource(),
					qualifiedNameConverter.toString(getImportedNamespace(candidate)));
		}

		protected IReplacementTextApplier getImportReplacementTextApplier(final XtextResource resource,
				final String importedNamespace) {
			return new STCoreImportReplacementTextApplier(resource, importedNamespace, whitespaceInformationProvider);
		}

		protected static AliasedEObjectDescription createImportedDescription(final IEObjectDescription description) {
			return new AliasedEObjectDescription(getAlias(description), description);
		}

		protected static QualifiedName getAlias(final IEObjectDescription description) {
			final QualifiedName qualifiedName = description.getQualifiedName();
			if (isGlobalVariable(description)) {
				return qualifiedName.skipFirst(qualifiedName.getSegmentCount() - 2);
			}
			return qualifiedName.skipFirst(qualifiedName.getSegmentCount() - 1);
		}

		private static QualifiedName getImportedNamespace(final IEObjectDescription description) {
			if (isGlobalVariable(description)) {
				return description.getQualifiedName().skipLast(1);
			}
			return description.getQualifiedName();
		}

		public IWhitespaceInformationProvider getWhitespaceInformationProvider() {
			return whitespaceInformationProvider;
		}
	}

	@Override
	public STCoreReferenceProposalCreator getCrossReferenceProposalCreator() {
		return (STCoreReferenceProposalCreator) super.getCrossReferenceProposalCreator();
	}
}
