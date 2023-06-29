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
 *   Ulzii Jargalsaikhan
 *   	 - add quick fixes for missing variables
 */
package org.eclipse.fordiac.ide.structuredtextcore.ui.quickfix;

import java.text.MessageFormat;
import java.util.stream.StreamSupport;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithm;
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STMethod;
import org.eclipse.fordiac.ide.structuredtextcore.scoping.STStandardFunctionProvider;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STAssignmentStatement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCoreFactory;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STFeatureExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarInOutDeclarationBlock;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarInputDeclarationBlock;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarOutputDeclarationBlock;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarTempDeclarationBlock;
import org.eclipse.fordiac.ide.structuredtextcore.ui.Messages;
import org.eclipse.fordiac.ide.structuredtextcore.validation.STCoreValidator;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunction;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.diagnostics.Diagnostic;
import org.eclipse.xtext.resource.EObjectAtOffsetHelper;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.ui.editor.model.edit.IModification;
import org.eclipse.xtext.ui.editor.model.edit.IModificationContext;
import org.eclipse.xtext.ui.editor.quickfix.DefaultQuickfixProvider;
import org.eclipse.xtext.ui.editor.quickfix.Fix;
import org.eclipse.xtext.ui.editor.quickfix.IssueResolutionAcceptor;
import org.eclipse.xtext.validation.Issue;

import com.google.inject.Inject;

public class STCoreQuickfixProvider extends DefaultQuickfixProvider {

	@Inject
	private STStandardFunctionProvider standardFunctionProvider;

	@Inject
	protected EObjectAtOffsetHelper offsetHelper;

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
							if (document != null
									&& element.eContainer() instanceof final STAssignmentStatement assignment) {
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
							if (document != null
									&& element.eContainer() instanceof final STAssignmentStatement assignment) {
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
							if (document != null
									&& element.eContainer() instanceof final STAssignmentStatement assignment) {
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
						if (document != null
								&& element.eContainer() instanceof final STAssignmentStatement assignment) {
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
