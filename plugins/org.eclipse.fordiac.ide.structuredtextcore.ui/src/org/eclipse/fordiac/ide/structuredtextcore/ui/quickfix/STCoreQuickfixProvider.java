/**
 * Copyright (c) 2021, 2024 Primetals Technologies Austria GmbH
 *                          Martin Erich Jobst
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
 *       - adapt non-compatible type quickfix
 *       - externalize strings and cleanup
 *       - add unnecessary conversion quickfixes
 *       - add unused import and organize imports quickfixes
 *       - refactor adding missing variables
 *       - refactor quickfixes
 *   Ulzii Jargalsaikhan
 *   	 - add quick fixes for missing variables
 */
package org.eclipse.fordiac.ide.structuredtextcore.ui.quickfix;

import java.text.MessageFormat;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.globalconstantseditor.globalConstants.STVarGlobalDeclarationBlock;
import org.eclipse.fordiac.ide.model.IdentifierVerifier;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
import org.eclipse.fordiac.ide.model.helpers.ImportHelper;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.ICallable;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.structuredtextcore.resource.LibraryElementXtextResource;
import org.eclipse.fordiac.ide.structuredtextcore.scoping.STStandardFunctionProvider;
import org.eclipse.fordiac.ide.structuredtextcore.scoping.STStandardFunctionScope;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STAssignment;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCoreFactory;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STFeatureExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STImport;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STSource;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclarationBlock;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.util.STCoreUtil;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.util.VarDeclarationKind;
import org.eclipse.fordiac.ide.structuredtextcore.ui.Messages;
import org.eclipse.fordiac.ide.structuredtextcore.validation.STCoreImportValidator;
import org.eclipse.fordiac.ide.structuredtextcore.validation.STCoreTypeUsageCollector;
import org.eclipse.fordiac.ide.structuredtextcore.validation.STCoreValidator;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.formatting2.regionaccess.IEObjectRegion;
import org.eclipse.xtext.formatting2.regionaccess.ISemanticRegion;
import org.eclipse.xtext.formatting2.regionaccess.ITextRegionDiffBuilder;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.EObjectAtOffsetHelper;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.impl.AliasedEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.IScopeProvider;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.ui.editor.model.edit.IModification;
import org.eclipse.xtext.ui.editor.model.edit.IModificationContext;
import org.eclipse.xtext.ui.editor.quickfix.DefaultQuickfixProvider;
import org.eclipse.xtext.ui.editor.quickfix.Fix;
import org.eclipse.xtext.ui.editor.quickfix.IssueResolutionAcceptor;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.Strings;
import org.eclipse.xtext.util.concurrent.CancelableUnitOfWork;
import org.eclipse.xtext.validation.Issue;

import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class STCoreQuickfixProvider extends DefaultQuickfixProvider {

	@Inject
	private STStandardFunctionProvider standardFunctionProvider;

	@Inject
	protected EObjectAtOffsetHelper offsetHelper;

	@Inject
	private IQualifiedNameConverter nameConverter;

	@Inject
	private IQualifiedNameProvider nameProvider;

	@Inject
	private Provider<STCoreTypeUsageCollector> typeUsageCollectorProvider;

	@Inject
	private IScopeProvider scopeProvider;

	@Fix(STCoreValidator.EXIT_NOT_IN_LOOP)
	public static void fixExitNotInLoop(final Issue issue, final IssueResolutionAcceptor acceptor) {
		// multi resolutions need to have identical label, description, and image
		acceptor.acceptMulti(issue, Messages.STCoreQuickfixProvider_RemoveInvalidExitStatementLabel,
				Messages.STCoreQuickfixProvider_RemoveInvalidExitStatementDescription, null, (element, context) -> {
					context.setUpdateCrossReferences(false);
					context.setUpdateRelatedFiles(false);
					context.addModification(element, EcoreUtil::remove);
				});
	}

	@Fix(STCoreValidator.CONTINUE_NOT_IN_LOOP)
	public static void fixContinueNotInLoop(final Issue issue, final IssueResolutionAcceptor acceptor) {
		// multi resolutions need to have identical label, description, and image
		acceptor.acceptMulti(issue, Messages.STCoreQuickfixProvider_RemoveInvalidContinueStatementLabel,
				Messages.STCoreQuickfixProvider_RemoveInvalidContinueStatementDescription, null, (element, context) -> {
					context.setUpdateCrossReferences(false);
					context.setUpdateRelatedFiles(false);
					context.addModification(element, EcoreUtil::remove);
				});
	}

	@Fix(STCoreValidator.TRAILING_UNDERSCORE_IN_IDENTIFIER_ERROR)
	public static void fixTrailingUnderscore(final Issue issue, final IssueResolutionAcceptor acceptor) {
		// multi resolutions need to have identical label, description, and image
		acceptor.acceptMulti(issue, Messages.STCoreQuickfixProvider_RemoveTrailingUnderscoreLabel,
				Messages.STCoreQuickfixProvider_RemoveTrailingUnderscoreDescription, null, (element, context) -> {
					context.setUpdateCrossReferences(true);
					context.setUpdateRelatedFiles(true);
					context.addModification(element, (final INamedElement namedElement) -> namedElement
							.setName(namedElement.getName().replaceAll("_+$", ""))); //$NON-NLS-1$ //$NON-NLS-2$
				});
	}

	@Fix(STCoreValidator.CONSECUTIVE_UNDERSCORE_IN_IDENTIFIER_ERROR)
	public static void fixConsecutiveUnderscore(final Issue issue, final IssueResolutionAcceptor acceptor) {
		// multi resolutions need to have identical label, description, and image
		acceptor.acceptMulti(issue, Messages.STCoreQuickfixProvider_RemoveConsecutiveUnderscoresLabel,
				Messages.STCoreQuickfixProvider_RemoveConsecutiveUnderscoresDescription, null, (element, context) -> {
					context.setUpdateCrossReferences(true);
					context.setUpdateRelatedFiles(true);
					context.addModification(element, (final INamedElement namedElement) -> namedElement
							.setName(namedElement.getName().replaceAll("_{2,}", "_"))); //$NON-NLS-1$ //$NON-NLS-2$
				});
	}

	@Fix(STCoreValidator.NON_COMPATIBLE_TYPES)
	public void fixNonCompatibleTypes(final Issue issue, final IssueResolutionAcceptor acceptor) {
		final String castName = issue.getData()[0] + "_TO_" + issue.getData()[1]; //$NON-NLS-1$
		final boolean castPossible = StreamSupport.stream(standardFunctionProvider.get().spliterator(), true)
				.anyMatch(func -> func.getName().equals(castName));
		if (castPossible) {
			acceptor.accept(issue, Messages.STCoreQuickfixProvider_AddExplicitTypecastLabel, MessageFormat
					.format(Messages.STCoreQuickfixProvider_AddExplicitTypecastDescription, (Object[]) issue.getData()),
					null, (IModification) context -> {
						final IXtextDocument xtextDocument = context.getXtextDocument();
						if (xtextDocument != null) {
							final String original = xtextDocument.get(issue.getOffset().intValue(),
									issue.getLength().intValue());
							final String replacement = issue.getData()[0] + "_TO_" + issue.getData()[1] + "(" + original //$NON-NLS-1$ //$NON-NLS-2$
									+ ")"; //$NON-NLS-1$
							xtextDocument.replace(issue.getOffset().intValue(), issue.getLength().intValue(),
									replacement);
						}
					});
		}
	}

	@Fix(STCoreValidator.WRONG_NAME_CASE)
	public static void fixVariableNameCasing(final Issue issue, final IssueResolutionAcceptor acceptor) {
		// multi resolutions need to have identical label, description, and image
		acceptor.acceptMulti(issue, Messages.STCoreQuickfixProvider_ChangeVariableCaseAsDeclaredLabel,
				Messages.STCoreQuickfixProvider_ChangeVariableCaseAsDeclaredDescription, null, (element, context) -> {
					context.setUpdateCrossReferences(false);
					context.setUpdateRelatedFiles(false);
					context.addModification(element, (final STFeatureExpression expression) -> {
						final ITextRegionDiffBuilder textRegionDiffBuilder = context
								.getModifiableDocument((XtextResource) expression.eResource());
						if (textRegionDiffBuilder != null) {
							final IEObjectRegion expressionRegion = textRegionDiffBuilder.getOriginalTextRegionAccess()
									.regionForEObject(expression);
							if (expressionRegion != null) {
								final ISemanticRegion featureRegion = expressionRegion.getRegionFor()
										.feature(STCorePackage.Literals.ST_FEATURE_EXPRESSION__FEATURE);
								if (featureRegion != null) {
									textRegionDiffBuilder.replace(featureRegion, issue.getData()[1]);
								}
							}
						}
					});
				});
	}

	@Fix(STCoreValidator.UNNECESSARY_CONVERSION)
	public static void fixUnnecessaryConversion(final Issue issue, final IssueResolutionAcceptor acceptor) {
		// multi resolutions need to have identical label, description, and image
		acceptor.acceptMulti(issue, Messages.STCoreQuickfixProvider_RemoveUnnecessaryConversionLabel,
				Messages.STCoreQuickfixProvider_RemoveUnnecessaryConversionDescription, null, (element, context) -> {
					context.setUpdateCrossReferences(false);
					context.setUpdateRelatedFiles(false);
					context.addModification(element, (final STFeatureExpression expression) -> {
						final ITextRegionDiffBuilder textRegionDiffBuilder = context
								.getModifiableDocument((XtextResource) expression.eResource());
						if (textRegionDiffBuilder != null) {
							final IEObjectRegion expressionRegion = textRegionDiffBuilder.getOriginalTextRegionAccess()
									.regionForEObject(expression);
							if (expressionRegion != null) {
								final ISemanticRegion parametersRegionBegin = expressionRegion.getRegionFor()
										.keyword("("); //$NON-NLS-1$
								final ISemanticRegion parametersRegionEnd = expressionRegion.getRegionFor()
										.keyword(")"); //$NON-NLS-1$
								if (parametersRegionBegin != null && parametersRegionEnd != null) {
									textRegionDiffBuilder.remove(expressionRegion.getPreviousHiddenRegion(),
											parametersRegionBegin.getNextHiddenRegion());
									textRegionDiffBuilder.remove(parametersRegionEnd.getPreviousHiddenRegion(),
											expressionRegion.getNextHiddenRegion());
								}
							}
						}
					});
				});
	}

	@Fix(STCoreValidator.UNNECESSARY_NARROW_CONVERSION)
	@Fix(STCoreValidator.UNNECESSARY_WIDE_CONVERSION)
	public static void fixUnnecessaryNarrowOrWideConversion(final Issue issue, final IssueResolutionAcceptor acceptor) {
		// multi resolutions need to have identical label, description, and image
		acceptor.acceptMulti(issue, Messages.STCoreQuickfixProvider_ChangeConversionLabel,
				Messages.STCoreQuickfixProvider_ChangeConversionDescription, null, (element, context) -> {
					context.setUpdateCrossReferences(false);
					context.setUpdateRelatedFiles(false);
					context.addModification(element, (final STFeatureExpression expression) -> {
						final ITextRegionDiffBuilder textRegionDiffBuilder = context
								.getModifiableDocument((XtextResource) expression.eResource());
						if (textRegionDiffBuilder != null) {
							final IEObjectRegion expressionRegion = textRegionDiffBuilder.getOriginalTextRegionAccess()
									.regionForEObject(expression);
							if (expressionRegion != null) {
								final ISemanticRegion featureRegion = expressionRegion.getRegionFor()
										.feature(STCorePackage.Literals.ST_FEATURE_EXPRESSION__FEATURE);
								if (featureRegion != null) {
									textRegionDiffBuilder.replace(featureRegion,
											issue.getData()[1] + "_TO_" + issue.getData()[2]); //$NON-NLS-1$
								}
							}
						}
					});
				});
	}

	@Fix(STCoreValidator.UNNECESSARY_LITERAL_CONVERSION)
	public static void fixUnnecessaryLiteralConversion(final Issue issue, final IssueResolutionAcceptor acceptor) {
		if (issue.getData()[2] != null) {
			acceptor.accept(issue, Messages.STCoreQuickfixProvider_RemoveUnnecessaryLiteralConversionLabel,
					MessageFormat.format(Messages.STCoreQuickfixProvider_RemoveUnnecessaryLiteralConversionDescription,
							issue.getData()[2]),
					null, (IModification) context -> {
						final IXtextDocument xtextDocument = context.getXtextDocument();
						if (xtextDocument != null) {
							xtextDocument.replace(issue.getOffset().intValue(), issue.getLength().intValue(),
									issue.getData()[2]);
						}
					});
		}
	}

	@Fix(STCoreValidator.PACKAGE_NAME_MISMATCH)
	public static void fixPackageNameMismatch(final Issue issue, final IssueResolutionAcceptor acceptor) {
		// multi resolutions need to have identical label, description, and image
		acceptor.acceptMulti(issue, Messages.STCoreQuickfixProvider_ChangePackage,
				Messages.STCoreQuickfixProvider_ChangePackage, null, (element, context) -> {
					context.setUpdateCrossReferences(true);
					context.setUpdateRelatedFiles(true);
					context.addModification(element,
							(final STSource source) -> setPackageName(source, issue.getData()[0]));
				});
	}

	@Fix(STCoreValidator.INVALID_IMPORT)
	public static void fixRemoveImport(final Issue issue, final IssueResolutionAcceptor acceptor) {
		// multi resolutions need to have identical label, description, and image
		acceptor.acceptMulti(issue, Messages.STCoreQuickfixProvider_RemoveImportLabel,
				Messages.STCoreQuickfixProvider_RemoveImportDescription, null, (element, context) -> {
					context.setUpdateCrossReferences(false);
					context.setUpdateRelatedFiles(false);
					context.addModification(element, (final STImport imp) -> {
						final ITextRegionDiffBuilder textRegionDiffBuilder = context
								.getModifiableDocument((XtextResource) imp.eResource());
						if (textRegionDiffBuilder != null) {
							final IEObjectRegion importRegion = textRegionDiffBuilder.getOriginalTextRegionAccess()
									.regionForEObject(imp);
							if (importRegion != null) {
								textRegionDiffBuilder.remove(importRegion.getPreviousHiddenRegion(),
										importRegion.getNextHiddenRegion());
							}
						}
					});
				});
	}

	@Fix(STCoreValidator.UNUSED_IMPORT)
	@Fix(STCoreValidator.WILDCARD_IMPORT)
	public void organizeImports(final Issue issue, final IssueResolutionAcceptor acceptor) {
		if (!hasSyntaxErrors(issue)) {
			// multi resolutions need to have identical label, description, and image
			acceptor.acceptMulti(issue, Messages.STCoreQuickfixProvider_OrganizeImports,
					Messages.STCoreQuickfixProvider_OrganizeImports, null, (element, context) -> {
						context.setUpdateCrossReferences(false);
						context.setUpdateRelatedFiles(false);
						context.addModification(element, (final STImport imp) -> organizeImports(
								EcoreUtil2.getContainerOfType(imp, STSource.class)));
					});
		}
	}

	protected void organizeImports(final STSource source) {
		final QualifiedName packageName = getPackageName(source);
		final EList<STImport> imports = getImports(source);
		if (imports == null) {
			return;
		}
		final Set<QualifiedName> usedTypes = typeUsageCollectorProvider.get().collectUsedTypes(source);
		final Set<QualifiedName> imported = new HashSet<>(usedTypes.stream()
				// skip implicit imports
				.filter(imp -> !STCoreImportValidator.isImplicitImport(imp, packageName))
				// create map with simple names as keys and merge imported types
				// (avoids ambiguous imports)
				.collect(Collectors.toMap(QualifiedName::getLastSegment, Function.identity(), //
						(a, b) -> mergeImportedTypes(a, b, imports)))
				// construct new set from values
				.values());
		// remove unnecessary imports (and existing imports from imported)
		imports.removeIf(imp -> !imported.remove(getQualifiedName(imp)));
		// create and add new imports based on imported
		imported.stream().map(nameConverter::toString).map(STCoreQuickfixProvider::createImport)
				.forEachOrdered(imports::add);
		// sort imports
		ECollections.sort(imports, Comparator.comparing(STImport::getImportedNamespace));
	}

	protected QualifiedName mergeImportedTypes(final QualifiedName first, final QualifiedName second,
			final List<STImport> imports) {
		if (matchesImports(first, imports)) {
			return first;
		}
		if (matchesImports(second, imports)) {
			return second;
		}
		return null; // skip colliding names
	}

	protected boolean matchesImports(final QualifiedName name, final List<STImport> imports) {
		return imports.stream().anyMatch(imp -> matchesImport(name, imp));
	}

	protected boolean matchesImport(final QualifiedName name, final STImport imp) {
		final QualifiedName importedNamespace = getQualifiedName(imp);
		return importedNamespace.equals(name)
				|| (importedNamespace.getLastSegment().equals(ImportHelper.WILDCARD_IMPORT)
						&& importedNamespace.skipLast(1).equals(name.skipLast(1)));
	}

	protected QualifiedName getPackageName(final STSource source) {
		if (source != null) {
			final EStructuralFeature nameFeature = getPackageNameFeature(source);
			if (nameFeature != null) {
				final String name = (String) source.eGet(nameFeature);
				if (!Strings.isEmpty(name)) {
					return nameConverter.toQualifiedName(name);
				}
			}
		}
		return QualifiedName.EMPTY;
	}

	protected static void setPackageName(final STSource source, final String newPackageName) {
		final EStructuralFeature nameFeature = getPackageNameFeature(source);
		if (nameFeature != null) {
			if (newPackageName == null || newPackageName.isEmpty()) {
				source.eUnset(nameFeature);
			} else {
				source.eSet(nameFeature, newPackageName);
			}
		}
		if (source.eResource() instanceof final LibraryElementXtextResource libResource) {
			PackageNameHelper.setPackageName(libResource.getInternalLibraryElement(), newPackageName);
		}
	}

	protected static EStructuralFeature getPackageNameFeature(final STSource source) {
		if (source != null) {
			final EStructuralFeature nameFeature = source.eClass().getEStructuralFeature("name"); //$NON-NLS-1$
			if (nameFeature.getEType() == EcorePackage.eINSTANCE.getEString() && !nameFeature.isMany()) {
				return nameFeature;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	protected static EList<STImport> getImports(final STSource source) {
		if (source != null) {
			final EStructuralFeature importsFeature = source.eClass().getEStructuralFeature("imports"); //$NON-NLS-1$
			if (importsFeature != null && importsFeature.getEType() == STCorePackage.eINSTANCE.getSTImport()
					&& importsFeature.isMany()) {
				return (EList<STImport>) source.eGet(importsFeature);
			}
		}
		return null; // NOSONAR
	}

	protected QualifiedName getQualifiedName(final STImport imp) {
		final String importedNamespace = imp.getImportedNamespace();
		if (Strings.isEmpty(importedNamespace)) {
			return QualifiedName.EMPTY;
		}
		return nameConverter.toQualifiedName(importedNamespace);
	}

	protected static STImport createImport(final String importedNamespace) {
		final STImport result = STCoreFactory.eINSTANCE.createSTImport();
		result.setImportedNamespace(importedNamespace);
		return result;
	}

	@Override
	protected Iterable<IEObjectDescription> queryScope(final IScope scope) {
		if (scope instanceof final STStandardFunctionScope standardFunctionScope) {
			return Iterables.concat(super.queryScope(standardFunctionScope.getParent()),
					standardFunctionScope.getAllLocalElements());
		}
		return super.queryScope(scope);
	}

	/*
	 * Copied from XbaseQuickfixProvider.createLinkingIssueResolutions(Issue,
	 * IssueResolutionAcceptor)
	 */
	@Override
	public void createLinkingIssueResolutions(final Issue issue, final IssueResolutionAcceptor acceptor) {
		final IModificationContext modificationContext = getModificationContextFactory()
				.createModificationContext(issue);
		final IXtextDocument xtextDocument = modificationContext.getXtextDocument();
		if (xtextDocument != null) {
			xtextDocument.tryReadOnly(new CancelableUnitOfWork<Void, XtextResource>() {
				@Override
				public java.lang.Void exec(final XtextResource state, final CancelIndicator cancelIndicator)
						throws Exception {
					try {
						final EObject target = state.getEObject(issue.getUriToProblem().fragment());
						final EReference reference = getUnresolvedEReference(issue, target);
						if (reference != null && reference.getEReferenceType() != null) {
							createLinkingIssueQuickfixes(issue, getCancelableAcceptor(acceptor, cancelIndicator),
									xtextDocument, state, target, reference);
						}
					} catch (final WrappedException e) {
						// issue information seems to be out of sync, e.g. there is no
						// EObject with the given fragment
					}
					return null;
				}
			});
		}
		super.createLinkingIssueResolutions(issue, acceptor);
	}

	protected void createLinkingIssueQuickfixes(final Issue issue, final IssueResolutionAcceptor acceptor,
			final IXtextDocument xtextDocument, final XtextResource state, final EObject target,
			final EReference reference) throws BadLocationException {
		final String issueString = xtextDocument.get(issue.getOffset().intValue(), issue.getLength().intValue());
		final IScope scope = scopeProvider.getScope(target, reference);
		final QualifiedName packageName = nameProvider.getFullyQualifiedName(state.getContents().get(0));
		for (final IEObjectDescription description : scope.getAllElements()) {
			if (!isAliased(description) && Objects.equals(issueString, description.getQualifiedName().getLastSegment())
					&& !STCoreImportValidator.isImplicitImport(description.getQualifiedName(), packageName)
					&& isVisible(description, target)) {
				createImportProposal(issue, description.getQualifiedName(), acceptor);
			}
		}
	}

	protected static boolean isAliased(final IEObjectDescription description) {
		return description instanceof AliasedEObjectDescription;
	}

	@SuppressWarnings("static-method") // subclasses may override
	protected boolean isVisible(final IEObjectDescription description, final EObject context) {
		return description.getName().getSegmentCount() == 1
				|| (STCorePackage.eINSTANCE.getSTVarDeclaration().equals(description.getEClass())
						&& EcoreUtil.resolve(description.getEObjectOrProxy(), context)
								.eContainer() instanceof STVarGlobalDeclarationBlock)
				|| LibraryElementPackage.eINSTANCE.getLibraryElement().isSuperTypeOf(description.getEClass());
	}

	protected void createImportProposal(final Issue issue, final QualifiedName qualifiedName,
			final IssueResolutionAcceptor acceptor) {
		final String label = MessageFormat.format(Messages.STCoreQuickfixProvider_CreateImport,
				qualifiedName.getLastSegment(), nameConverter.toString(qualifiedName.skipLast(1)));
		final String importedNamespace = nameConverter.toString(qualifiedName);
		createImportProposal(issue, label, importedNamespace, acceptor);
	}

	@SuppressWarnings("static-method") // subclasses may override
	protected void createImportProposal(final Issue issue, final String label, final String importedNamespace,
			final IssueResolutionAcceptor acceptor) {
		acceptor.accept(issue, label, label, null, (element, context) -> {
			final EList<STImport> imports = getImports(EcoreUtil2.getContainerOfType(element, STSource.class));
			if (imports != null) {
				imports.add(createImport(importedNamespace));
			}
		}, 100);
	}

	public void createMissingVariable(final Issue issue, final IssueResolutionAcceptor acceptor) {
		acceptor.accept(issue, Messages.STCoreQuickfixProvider_CreateMissingInputVariable,
				Messages.STCoreQuickfixProvider_CreateMissingInputVariable, null,
				(element, context) -> createMissingVariable(element, VarDeclarationKind.INPUT));
		acceptor.accept(issue, Messages.STCoreQuickfixProvider_CreateMissingInOutVariable,
				Messages.STCoreQuickfixProvider_CreateMissingInOutVariable, null,
				(element, context) -> createMissingVariable(element, VarDeclarationKind.INOUT));
		acceptor.accept(issue, Messages.STCoreQuickfixProvider_CreateMissingOutputVariable,
				Messages.STCoreQuickfixProvider_CreateMissingOutputVariable, null,
				(element, context) -> createMissingVariable(element, VarDeclarationKind.OUTPUT));
		acceptor.accept(issue, Messages.STCoreQuickfixProvider_CreateMissingTempVariable,
				Messages.STCoreQuickfixProvider_CreateMissingTempVariable, null,
				(element, context) -> createMissingVariable(element, VarDeclarationKind.TEMP));
	}

	protected void createMissingVariable(final EObject element, final VarDeclarationKind kind) {
		if (element instanceof final STFeatureExpression expression) {
			final ICallable callable = EcoreUtil2.getContainerOfType(expression, ICallable.class);
			final String name = getFeatureText(expression);
			final INamedElement type = getExpectedFeatureType(expression);
			if (callable != null && IdentifierVerifier.verifyIdentifier(name).isEmpty() && type != null) {
				createMissingVariable(callable, name, type, kind);
			}
		}
	}

	@SuppressWarnings("static-method") // subclasses may override
	protected void createMissingVariable(final ICallable callable, final String name, final INamedElement type,
			final VarDeclarationKind kind) {
		throw new UnsupportedOperationException();
	}

	protected static void createSTVarDeclaration(final EList<STVarDeclarationBlock> blocks, final String name,
			final INamedElement type, final VarDeclarationKind kind) {
		final STVarDeclarationBlock block = getOrCreateSTVarDeclarationBlock(blocks, kind.getBlockClass());
		final STVarDeclaration varDeclaration = createSTVarDeclaration(name, type);
		block.getVarDeclarations().add(varDeclaration);
	}

	protected static STVarDeclaration createSTVarDeclaration(final String name, final INamedElement type) {
		final STVarDeclaration varDeclaration = STCoreFactory.eINSTANCE.createSTVarDeclaration();
		varDeclaration.setName(name);
		varDeclaration.setType(type);
		return varDeclaration;
	}

	protected static STVarDeclarationBlock getOrCreateSTVarDeclarationBlock(final EList<STVarDeclarationBlock> blocks,
			final EClass eClass) {
		return blocks.stream().filter(eClass::isInstance).filter(Predicate.not(STVarDeclarationBlock::isConstant))
				.findFirst().orElseGet(() -> {
					final STVarDeclarationBlock block = (STVarDeclarationBlock) STCoreFactory.eINSTANCE.create(eClass);
					blocks.add(block);
					return block;
				});
	}

	protected static String getFeatureText(final STFeatureExpression element) {
		return NodeModelUtils.findNodesForFeature(element, STCorePackage.Literals.ST_FEATURE_EXPRESSION__FEATURE)
				.stream().map(INode::getText).map(String::trim).collect(Collectors.joining());
	}

	protected static INamedElement getExpectedFeatureType(final STFeatureExpression element) {
		final INamedElement expectedType = STCoreUtil.getExpectedType(element);
		if (expectedType != null) {
			return expectedType;
		}
		if (element.eContainer() instanceof final STAssignment assignment && assignment.getLeft() == element
				&& assignment.getRight() != null) {
			final INamedElement resultType = assignment.getRight().getResultType();
			if (resultType != null) {
				return resultType;
			}
		}
		return ElementaryTypes.SINT;
	}

	protected boolean hasSyntaxErrors(final Issue issue) {
		final IModificationContext modificationContext = getModificationContextFactory()
				.createModificationContext(issue);
		final IXtextDocument xtextDocument = modificationContext.getXtextDocument();
		return xtextDocument == null || xtextDocument
				.tryReadOnly(resource -> Boolean
						.valueOf(resource.getParseResult() == null || resource.getParseResult().hasSyntaxErrors()))
				.booleanValue();
	}
}
