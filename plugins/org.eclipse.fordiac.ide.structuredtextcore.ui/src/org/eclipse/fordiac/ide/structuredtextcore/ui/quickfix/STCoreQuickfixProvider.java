/**
 * Copyright (c) 2021, 2023 Primetals Technologies Austria GmbH
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
 *   Ulzii Jargalsaikhan
 *   	 - add quick fixes for missing variables
 */
package org.eclipse.fordiac.ide.structuredtextcore.ui.quickfix;

import java.text.MessageFormat;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.StreamSupport;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.globalconstantseditor.globalConstants.STVarGlobalDeclarationBlock;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithm;
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STMethod;
import org.eclipse.fordiac.ide.structuredtextcore.scoping.STStandardFunctionProvider;
import org.eclipse.fordiac.ide.structuredtextcore.scoping.STStandardFunctionScope;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STAssignment;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCoreFactory;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STFeatureExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STImport;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STSource;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarInOutDeclarationBlock;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarInputDeclarationBlock;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarOutputDeclarationBlock;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarTempDeclarationBlock;
import org.eclipse.fordiac.ide.structuredtextcore.ui.Messages;
import org.eclipse.fordiac.ide.structuredtextcore.validation.STCoreImportValidator;
import org.eclipse.fordiac.ide.structuredtextcore.validation.STCoreTypeUsageCollector;
import org.eclipse.fordiac.ide.structuredtextcore.validation.STCoreValidator;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunction;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.diagnostics.Diagnostic;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
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
		acceptor.accept(issue, Messages.STCoreQuickfixProvider_RemoveInvalidExitStatementLabel,
				Messages.STCoreQuickfixProvider_RemoveInvalidExitStatementDescription, null,
				(element, context) -> EcoreUtil.delete(element));
	}

	@Fix(STCoreValidator.CONTINUE_NOT_IN_LOOP)
	public static void fixContinueNotInLoop(final Issue issue, final IssueResolutionAcceptor acceptor) {
		acceptor.accept(issue, Messages.STCoreQuickfixProvider_RemoveInvalidContinueStatementLabel,
				Messages.STCoreQuickfixProvider_RemoveInvalidContinueStatementDescription, null,
				(element, context) -> EcoreUtil.delete(element));
	}

	@Fix(STCoreValidator.TRAILING_UNDERSCORE_IN_IDENTIFIER_ERROR)
	public static void fixTrailingUnderscore(final Issue issue, final IssueResolutionAcceptor acceptor) {
		acceptor.accept(issue, Messages.STCoreQuickfixProvider_RemoveTrailingUnderscoreLabel,
				MessageFormat.format(Messages.STCoreQuickfixProvider_RemoveTrailingUnderscoreDescription,
						(Object[]) issue.getData()),
				null, (IModification) context -> {
					final IXtextDocument xtextDocument = context.getXtextDocument();
					if (xtextDocument != null) {
						xtextDocument.replace(issue.getOffset().intValue(), issue.getLength().intValue(),
								issue.getData()[0].substring(0, issue.getData()[0].length() - 1));
					}
				});
	}

	@Fix(STCoreValidator.CONSECUTIVE_UNDERSCORE_IN_IDENTIFIER_ERROR)
	public static void fixConsecutiveUnderscore(final Issue issue, final IssueResolutionAcceptor acceptor) {
		acceptor.accept(issue, Messages.STCoreQuickfixProvider_RemoveConsecutiveUnderscoresLabel,
				MessageFormat.format(Messages.STCoreQuickfixProvider_RemoveConsecutiveUnderscoresDescription,
						(Object[]) issue.getData()),
				null, (IModification) context -> {
					final IXtextDocument xtextDocument = context.getXtextDocument();
					if (xtextDocument != null) {
						xtextDocument.replace(issue.getOffset().intValue(), issue.getLength().intValue(),
								issue.getData()[0].replaceAll("_(_)+", "_")); //$NON-NLS-1$ //$NON-NLS-2$
					}
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
		acceptor.accept(issue, Messages.STCoreQuickfixProvider_ChangeVariableCaseAsDeclaredLabel,
				MessageFormat.format(Messages.STCoreQuickfixProvider_ChangeVariableCaseAsDeclaredDescription,
						(Object[]) issue.getData()),
				null, (IModification) context -> {
					final IXtextDocument xtextDocument = context.getXtextDocument();
					if (xtextDocument != null) {
						xtextDocument.replace(issue.getOffset().intValue(), issue.getLength().intValue(),
								issue.getData()[1]);
					}
				});
	}

	@Fix(STCoreValidator.UNNECESSARY_CONVERSION)
	public static void fixUnnecessaryConversion(final Issue issue, final IssueResolutionAcceptor acceptor) {
		acceptor.accept(issue, Messages.STCoreQuickfixProvider_RemoveUnnecessaryConversionLabel,
				MessageFormat.format(Messages.STCoreQuickfixProvider_RemoveUnnecessaryConversionDescription,
						(Object[]) issue.getData()),
				null, (final EObject element, final IModificationContext context) -> {
					if (element instanceof final STFeatureExpression featureExpression
							&& featureExpression.eContainer() != null && featureExpression.eContainingFeature() != null
							&& featureExpression.getParameters().size() == 1) {
						final STExpression argument = featureExpression.getParameters().get(0).getArgument();
						featureExpression.eContainer().eSet(featureExpression.eContainingFeature(), argument);
					}
				});
	}

	@Fix(STCoreValidator.UNNECESSARY_NARROW_CONVERSION)
	@Fix(STCoreValidator.UNNECESSARY_WIDE_CONVERSION)
	public void fixUnnecessaryNarrowOrWideConversion(final Issue issue, final IssueResolutionAcceptor acceptor) {
		final String castName = issue.getData()[1] + "_TO_" + issue.getData()[2]; //$NON-NLS-1$
		final boolean castPossible = StreamSupport.stream(standardFunctionProvider.get().spliterator(), true)
				.anyMatch(func -> func.getName().equals(castName));
		if (castPossible) {
			acceptor.accept(issue, Messages.STCoreQuickfixProvider_ChangeConversionLabel,
					MessageFormat.format(Messages.STCoreQuickfixProvider_ChangeConversionDescription, castName), null,
					(IModification) context -> {
						final IXtextDocument xtextDocument = context.getXtextDocument();
						if (xtextDocument != null) {
							xtextDocument.replace(issue.getOffset().intValue(), issue.getLength().intValue(), castName);
						}
					});
		}
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

	@Fix(STCoreValidator.UNUSED_IMPORT)
	public static void fixUnusedImport(final Issue issue, final IssueResolutionAcceptor acceptor) {
		acceptor.accept(issue, Messages.STCoreQuickfixProvider_RemoveUnusedImportLabel,
				MessageFormat.format(Messages.STCoreQuickfixProvider_RemoveUnusedImportDescription, issue.getData()[0]),
				null, (element, context) -> EcoreUtil.delete(element));
	}

	@Fix(STCoreValidator.UNUSED_IMPORT)
	@Fix(STCoreValidator.WILDCARD_IMPORT)
	public void organizeImports(final Issue issue, final IssueResolutionAcceptor acceptor) {
		acceptor.accept(issue, Messages.STCoreQuickfixProvider_OrganizeImports,
				Messages.STCoreQuickfixProvider_OrganizeImports, null,
				(element, context) -> organizeImports(EcoreUtil2.getContainerOfType(element, STSource.class)));
	}

	protected void organizeImports(final STSource source) {
		final QualifiedName packageName = getPackageName(source);
		final EList<STImport> imports = getImports(source);
		if (imports == null) {
			return;
		}
		final STCoreTypeUsageCollector collector = typeUsageCollectorProvider.get();
		final Set<QualifiedName> imported = new HashSet<>(collector.collectUsedTypes(source));
		imported.removeIf(imp -> STCoreImportValidator.isImplicitImport(imp, packageName));
		imports.removeIf(imp -> shouldRemoveImport(imp, imported));
		imported.stream().map(nameConverter::toString).map(STCoreQuickfixProvider::createImport)
				.forEachOrdered(imports::add);
		ECollections.sort(imports, Comparator.comparing(STImport::getImportedNamespace));
	}

	protected QualifiedName getPackageName(final STSource source) {
		if (source != null) {
			final EStructuralFeature nameFeature = source.eClass().getEStructuralFeature("name"); //$NON-NLS-1$
			if (nameFeature.getEType() == EcorePackage.eINSTANCE.getEString() && !nameFeature.isMany()) {
				final String name = (String) source.eGet(nameFeature);
				if (!Strings.isEmpty(name)) {
					return nameConverter.toQualifiedName(name);
				}
			}
		}
		return QualifiedName.EMPTY;
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

	protected boolean shouldRemoveImport(final STImport imp, final Set<QualifiedName> imported) {
		final String importedNamespace = imp.getImportedNamespace();
		if (Strings.isEmpty(importedNamespace)) {
			return true;
		}

		final QualifiedName qualifiedName = nameConverter.toQualifiedName(importedNamespace);
		if (qualifiedName == null || qualifiedName.isEmpty()) {
			return true;
		}

		return !imported.remove(qualifiedName);
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

	@Fix(Diagnostic.LINKING_DIAGNOSTIC)
	public void createMissingVariable(final Issue issue, final IssueResolutionAcceptor acceptor) {
		final IModificationContext modificationContext = getModificationContextFactory()
				.createModificationContext(issue);
		final IXtextDocument xtextDocument = modificationContext.getXtextDocument();
		if (xtextDocument != null) {
			final var resolvedElement = xtextDocument.readOnly((final XtextResource resource) -> (offsetHelper
					.resolveContainedElementAt(resource, issue.getOffset().intValue())));
			if (EcoreUtil2.getContainerOfType(resolvedElement, STFunction.class) != null
					|| EcoreUtil2.getContainerOfType(resolvedElement, STMethod.class) != null) {
				acceptor.accept(issue, Messages.STCoreQuickfixProvider_CreateMissingInputVariable,
						Messages.STCoreQuickfixProvider_CreateMissingInputVariable, null,
						(final EObject element, final IModificationContext context) -> {
							final IXtextDocument document = context.getXtextDocument();
							if (document != null && element.eContainer() instanceof final STAssignment assignment) {
								final var factory = STCoreFactory.eINSTANCE;
								final var type = assignment.getRight().getResultType();
								final var varDeclaration = factory.createSTVarDeclaration();
								varDeclaration.setType(type);
								varDeclaration.setName(
										document.get(issue.getOffset().intValue(), issue.getLength().intValue()));
								final EObject container = EcoreUtil2.getContainerOfType(element,
										STFunction.class) != null
												? EcoreUtil2.getContainerOfType(element, STFunction.class)
												: EcoreUtil2.getContainerOfType(element, STMethod.class);
								final var inputBlocks = EcoreUtil2.getAllContentsOfType(container,
										STVarInputDeclarationBlock.class);
								if (inputBlocks.isEmpty()) {
									final var block = factory.createSTVarInputDeclarationBlock();
									block.getVarDeclarations().add(varDeclaration);
									if (container instanceof final STFunction function) {
										function.getVarDeclarations().add(block);
									} else if (container instanceof final STMethod method) {
										method.getBody().getVarDeclarations().add(block);
									}
								} else {
									inputBlocks.get(inputBlocks.size() - 1).getVarDeclarations().add(varDeclaration);
								}

							}
						});
				acceptor.accept(issue, Messages.STCoreQuickfixProvider_CreateMissingOutputVariable,
						Messages.STCoreQuickfixProvider_CreateMissingOutputVariable, null,
						(final EObject element, final IModificationContext context) -> {

							final IXtextDocument document = context.getXtextDocument();
							if (document != null && element.eContainer() instanceof final STAssignment assignment) {
								final var factory = STCoreFactory.eINSTANCE;
								final var type = assignment.getRight().getResultType();
								final var varDeclaration = factory.createSTVarDeclaration();
								varDeclaration.setType(type);
								varDeclaration.setName(
										document.get(issue.getOffset().intValue(), issue.getLength().intValue()));
								final EObject container = EcoreUtil2.getContainerOfType(element,
										STFunction.class) != null
												? EcoreUtil2.getContainerOfType(element, STFunction.class)
												: EcoreUtil2.getContainerOfType(element, STMethod.class);
								final var outputBlocks = EcoreUtil2.getAllContentsOfType(container,
										STVarOutputDeclarationBlock.class);
								if (outputBlocks.isEmpty()) {
									final var block = factory.createSTVarOutputDeclarationBlock();
									block.getVarDeclarations().add(varDeclaration);
									if (container instanceof final STFunction function) {
										function.getVarDeclarations().add(block);
									} else if (container instanceof final STMethod method) {
										method.getBody().getVarDeclarations().add(block);
									}
								} else {
									outputBlocks.get(outputBlocks.size() - 1).getVarDeclarations().add(varDeclaration);
								}

							}
						});
				acceptor.accept(issue, Messages.STCoreQuickfixProvider_CreateMissingInOutVariable,
						Messages.STCoreQuickfixProvider_CreateMissingInOutVariable, null,
						(final EObject element, final IModificationContext context) -> {
							final IXtextDocument document = context.getXtextDocument();
							if (document != null && element.eContainer() instanceof final STAssignment assignment) {
								final var factory = STCoreFactory.eINSTANCE;
								final var type = assignment.getRight().getResultType();
								final var varDeclaration = factory.createSTVarDeclaration();
								varDeclaration.setType(type);
								varDeclaration.setName(
										document.get(issue.getOffset().intValue(), issue.getLength().intValue()));
								final EObject container = EcoreUtil2.getContainerOfType(element,
										STFunction.class) != null
												? EcoreUtil2.getContainerOfType(element, STFunction.class)
												: EcoreUtil2.getContainerOfType(element, STMethod.class);
								final var inOutBlocks = EcoreUtil2.getAllContentsOfType(container,
										STVarInOutDeclarationBlock.class);
								if (inOutBlocks.isEmpty()) {
									final var block = factory.createSTVarInOutDeclarationBlock();
									block.getVarDeclarations().add(varDeclaration);
									if (container instanceof final STFunction function) {
										function.getVarDeclarations().add(block);
									} else if (container instanceof final STMethod method) {
										method.getBody().getVarDeclarations().add(block);
									}
								} else {
									inOutBlocks.get(inOutBlocks.size() - 1).getVarDeclarations().add(varDeclaration);
								}
							}
						});
			}
			acceptor.accept(issue, Messages.STCoreQuickfixProvider_CreateMissingTempVariable,
					Messages.STCoreQuickfixProvider_CreateMissingTempVariable, null,
					(final EObject element, final IModificationContext context) -> {
						final IXtextDocument document = context.getXtextDocument();
						if (document != null && element.eContainer() instanceof final STAssignment assignment) {
							final var factory = STCoreFactory.eINSTANCE;
							final var type = assignment.getRight().getResultType();
							final var varDeclaration = factory.createSTVarDeclaration();
							varDeclaration.setType(type);
							varDeclaration
									.setName(document.get(issue.getOffset().intValue(), issue.getLength().intValue()));
							EObject container = EcoreUtil2.getContainerOfType(element, STFunction.class);
							if (container == null) {
								container = EcoreUtil2.getContainerOfType(element, STAlgorithm.class);
							}
							if (container == null) {
								container = EcoreUtil2.getContainerOfType(element, STMethod.class);
							}
							final var tempBlocks = EcoreUtil2.getAllContentsOfType(container,
									STVarTempDeclarationBlock.class);
							if (tempBlocks.isEmpty()) {
								final var block = factory.createSTVarTempDeclarationBlock();
								block.getVarDeclarations().add(varDeclaration);
								if (container instanceof final STFunction function) {
									function.getVarDeclarations().add(block);
								} else if (container instanceof final STAlgorithm algorithm) {
									algorithm.getBody().getVarTempDeclarations().add(block);
								} else if (container instanceof final STMethod method) {
									method.getBody().getVarDeclarations().add(block);
								}
							} else {
								tempBlocks.get(tempBlocks.size() - 1).getVarDeclarations().add(varDeclaration);
							}
						}
					});
		}
	}
}
