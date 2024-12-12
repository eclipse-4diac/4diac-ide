/*******************************************************************************
 * Copyright (c) 2022, 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextalgorithm.validation;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleECAction;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleECState;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.structuredtextalgorithm.Messages;
import org.eclipse.fordiac.ide.structuredtextalgorithm.resource.STAlgorithmResource;
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithm;
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmBody;
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmSource;
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmSourceElement;
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STMethod;
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STMethodBody;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STFeatureExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration;
import org.eclipse.fordiac.ide.structuredtextcore.validation.STCoreControlFlowValidator;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionPackage;
import org.eclipse.xtext.resource.IContainer;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;
import org.eclipse.xtext.validation.Check;

import com.google.inject.Inject;

/**
 * This class contains custom validation rules.
 *
 * See
 * https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#validation
 */
public class STAlgorithmValidator extends AbstractSTAlgorithmValidator {
	@Inject
	private ResourceDescriptionsProvider resourceDescriptionsProvider;

	@Inject
	private IContainer.Manager containerManager;

	public static final String ISSUE_CODE_PREFIX = "org.eclipse.fordiac.ide.structuredtextalgorithm."; //$NON-NLS-1$
	public static final String DUPLICATE_METHOD_OR_ALGORITHM_NAME = STAlgorithmValidator.ISSUE_CODE_PREFIX
			+ "duplicateAlgorithmOrMethodName"; //$NON-NLS-1$
	public static final String MISSING_ALGORITHM = STAlgorithmValidator.ISSUE_CODE_PREFIX + "missingAlgorithm"; //$NON-NLS-1$
	public static final String UNUSED_ALGORITHM = STAlgorithmValidator.ISSUE_CODE_PREFIX + "unusedAlgorithm"; //$NON-NLS-1$
	public static final String VARIABLE_NAME_IN_USE_ON_INTERFACE = STAlgorithmValidator.ISSUE_CODE_PREFIX
			+ "variableNameInUseOnInterface"; //$NON-NLS-1$
	public static final String SHADOWING_FUNCTION = STAlgorithmValidator.ISSUE_CODE_PREFIX + "shadowingFunction"; //$NON-NLS-1$

	@Check
	public void checkControlFlow(final STAlgorithmBody body) {
		final STCoreControlFlowValidator controlFlowValidator = new STCoreControlFlowValidator(this);
		controlFlowValidator.validateVariableBlocks(body.getVarTempDeclarations());
		controlFlowValidator.validateStatements(body.getStatements());
	}

	@Check
	public void checkControlFlow(final STMethodBody body) {
		final STCoreControlFlowValidator controlFlowValidator = new STCoreControlFlowValidator(this);
		controlFlowValidator.validateVariableBlocks(body.getVarDeclarations());
		controlFlowValidator.validateStatements(body.getStatements());
	}

	@Check
	public void checkUniquenessOfVariableNamesInAFunctionBlock(final STVarDeclaration varDeclaration) {
		if (varDeclaration.eResource() instanceof final STAlgorithmResource resource
				&& resource.getInternalLibraryElement() instanceof final BaseFBType baseFBType) {
			final String name = varDeclaration.getName();
			checkUniquenessOfVariableNamesInAFunctionBlock(name, baseFBType.getInterfaceList().getEventInputs(),
					Messages.STAlgorithmValidator_NameUsedAsEventInput);
			checkUniquenessOfVariableNamesInAFunctionBlock(name, baseFBType.getInterfaceList().getInputVars(),
					Messages.STAlgorithmValidator_NameUsedAsDataInput);
			checkUniquenessOfVariableNamesInAFunctionBlock(name, baseFBType.getInterfaceList().getEventOutputs(),
					Messages.STAlgorithmValidator_NameUsedAsEventOutput);
			checkUniquenessOfVariableNamesInAFunctionBlock(name, baseFBType.getInterfaceList().getOutputVars(),
					Messages.STAlgorithmValidator_NameUsedAsDataOutput);
			checkUniquenessOfVariableNamesInAFunctionBlock(name, baseFBType.getInternalVars(),
					Messages.STAlgorithmValidator_NameUsedAsVariable);
			checkUniquenessOfVariableNamesInAFunctionBlock(name, baseFBType.getInternalConstVars(),
					Messages.STAlgorithmValidator_NameUsedAsConstant);
			checkUniquenessOfVariableNamesInAFunctionBlock(name, baseFBType.getInternalFbs(),
					Messages.STAlgorithmValidator_NameUsedAsFunctionBlockVariable);
			checkUniquenessOfVariableNamesInAFunctionBlock(name, baseFBType.getAlgorithm(),
					Messages.STAlgorithmValidator_NameUsedAsAlgorithm);
			checkUniquenessOfVariableNamesInAFunctionBlock(name, baseFBType.getMethods(),
					Messages.STAlgorithmValidator_NameUsedAsMethod);
		}
	}

	private void checkUniquenessOfVariableNamesInAFunctionBlock(final String name,
			final Collection<? extends INamedElement> list, final String message) {
		if (list.stream().anyMatch(it -> it.getName().equalsIgnoreCase(name))) {
			error(MessageFormat.format(message, name), LibraryElementPackage.Literals.INAMED_ELEMENT__NAME,
					VARIABLE_NAME_IN_USE_ON_INTERFACE, name);
		}
	}

	@Check
	public void checkUniqunessOfSTAlgorithmSourceElementNames(final STAlgorithmSourceElement sourceElement) {
		if (sourceElement.eContainer() instanceof final STAlgorithmSource source) {
			final String name = sourceElement.getName();
			if (source.getElements().stream()
					.anyMatch(it -> it != sourceElement && it.getName().equalsIgnoreCase(name))) {
				error(MessageFormat.format(Messages.STAlgorithmValidator_DuplicateMethodOrAlgorithmName, name),
						sourceElement, LibraryElementPackage.Literals.INAMED_ELEMENT__NAME,
						STAlgorithmValidator.DUPLICATE_METHOD_OR_ALGORITHM_NAME, name);
			}
		}
	}

	@Check
	public void checkUniquenessOfSTAlgorithmSourceElementNamesAndFunctionNames(
			final STFeatureExpression featureExpression) {
		if (!getIssueSeverities(getContext(), featureExpression).isIgnored(SHADOWING_FUNCTION)
				&& featureExpression.getFeature() instanceof STMethod) {
			final IResourceDescriptions resourceDescriptions = this.resourceDescriptionsProvider
					.getResourceDescriptions(featureExpression.getFeature().eResource());
			final IResourceDescription resourceDescription = resourceDescriptions
					.getResourceDescription(featureExpression.getFeature().eResource().getURI());
			for (final IContainer container : containerManager.getVisibleContainers(resourceDescription,
					resourceDescriptions)) {
				for (final IEObjectDescription description : container
						.getExportedObjectsByType(STFunctionPackage.Literals.ST_FUNCTION)) {
					if (featureExpression.getFeature().getName()
							.equalsIgnoreCase(description.getQualifiedName().toString())
							&& !description.getEObjectURI().equals(EcoreUtil.getURI(featureExpression))) {
						addIssue(
								MessageFormat.format(
										Messages.STAlgorithmValidator_UnqualifiedMethodOrAlgorithmShadowingFunction,
										featureExpression.getFeature().getName(),
										description.getEObjectURI().toPlatformString(true)),
								featureExpression, STCorePackage.Literals.ST_FEATURE_EXPRESSION__FEATURE,
								SHADOWING_FUNCTION);
					}
				}
			}
		}
	}

	@Check
	public void checkMissingAlgorithm(final STAlgorithmSource source) {
		if (source.eResource() instanceof final STAlgorithmResource resource
				&& resource.getInternalLibraryElement() instanceof final SimpleFBType simpleFBType) {
			getAllReferencedAlgorithms(simpleFBType)
					.filter(alg -> !hasSTAlgorithm(source, alg) && !hasNonSTAlgorithm(simpleFBType, alg))
					.forEach(alg -> acceptError(
							MessageFormat.format(Messages.STAlgorithmValidator_MissingAlgorithm, alg), source, 0, 0,
							STAlgorithmValidator.MISSING_ALGORITHM, alg));
		}
	}

	private static boolean hasSTAlgorithm(final STAlgorithmSource source, final String algorithm) {
		return source.getElements().stream().filter(STAlgorithm.class::isInstance)
				.anyMatch(alg -> alg.getName().equalsIgnoreCase(algorithm));
	}

	private static boolean hasNonSTAlgorithm(final SimpleFBType simpleFBType, final String algorithm) {
		return simpleFBType.getAlgorithm().stream()
				.filter(Predicate.not(org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm.class::isInstance))
				.anyMatch(alg -> alg.getName().equalsIgnoreCase(algorithm));
	}

	@Check
	public void checkUnusedAlgorithm(final STAlgorithm algorithm) {
		final String name = algorithm.getName();
		if (name != null && algorithm.eResource() instanceof final STAlgorithmResource resource
				&& resource.getInternalLibraryElement() instanceof final SimpleFBType simpleFBType
				&& getAllReferencedAlgorithms(simpleFBType).noneMatch(reference -> reference.equalsIgnoreCase(name))) {
			addIssue(MessageFormat.format(Messages.STAlgorithmValidator_UnusedAlgorithm, name), algorithm,
					LibraryElementPackage.eINSTANCE.getINamedElement_Name(), STAlgorithmValidator.UNUSED_ALGORITHM,
					name);
		}
	}

	private static Stream<String> getAllReferencedAlgorithms(final SimpleFBType simpleFBType) {
		return getAllECActions(simpleFBType).map(SimpleECAction::getAlgorithm).filter(Objects::nonNull);
	}

	private static Stream<SimpleECAction> getAllECActions(final SimpleFBType simpleFBType) {
		return simpleFBType.getSimpleECStates().stream().map(SimpleECState::getSimpleECActions).flatMap(List::stream);
	}
}
