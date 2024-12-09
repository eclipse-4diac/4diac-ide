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

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
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
	public static final String NO_ALGORITHM_FOR_INPUT_EVENT = STAlgorithmValidator.ISSUE_CODE_PREFIX
			+ "noAlgorithmForInputEvent"; //$NON-NLS-1$
	public static final String NO_INPUT_EVENT_FOR_ALGORITHM = STAlgorithmValidator.ISSUE_CODE_PREFIX
			+ "noInputEventForAlgorithm"; //$NON-NLS-1$
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
	public void checkAlgorithmForSimpleECAction(final STAlgorithmSource source) {
		if (source.eResource() instanceof final STAlgorithmResource resource
				&& resource.getInternalLibraryElement() instanceof final SimpleFBType simpleFBType) {
			simpleFBType.getSimpleECStates().stream().flatMap(state -> state.getSimpleECActions().stream())
					.filter(action -> simpleFBType.getAlgorithm().stream()
							.noneMatch(alg -> alg.getName().equalsIgnoreCase(action.getAlgorithm()))
							&& source.getElements().stream().filter(STAlgorithm.class::isInstance)
									.noneMatch(alg -> alg.getName().equalsIgnoreCase(action.getAlgorithm())))
					.forEach(action -> acceptError(
							MessageFormat.format(Messages.STAlgorithmValidator_MissingAlgorithmForECState,
									action.getAlgorithm(), action.getSimpleECState().getName()),
							source, 0, 0, STAlgorithmValidator.NO_ALGORITHM_FOR_INPUT_EVENT,
							action.getSimpleECState().getName()));
		}
	}

	@Check
	public void checkUnusedAlgorithm(final STAlgorithm algorithm) {
		if ((algorithm.eResource() instanceof final STAlgorithmResource resource
				&& resource.getInternalLibraryElement() instanceof final SimpleFBType simpleFBType)
				&& simpleFBType.getSimpleECStates().stream().flatMap(state -> state.getSimpleECActions().stream())
						.noneMatch(action -> action.getAlgorithm().equalsIgnoreCase(algorithm.getName()))) {
			addIssue(MessageFormat.format(Messages.STAlgorithmValidator_UnusedAlgorithm, algorithm.getName()),
					algorithm, LibraryElementPackage.eINSTANCE.getINamedElement_Name(),
					STAlgorithmValidator.NO_INPUT_EVENT_FOR_ALGORITHM, algorithm.getName());
		}
	}
}
