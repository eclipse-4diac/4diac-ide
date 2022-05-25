/*******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst
 * 				 2022 Primetals Technologies Austria GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *   Martin Melik Merkumians - Add validators for unique name checks in algorithms
 * 		methods and warnings against duplicate function names
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextalgorithm.validation

import com.google.inject.Inject
import java.text.MessageFormat
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage
import org.eclipse.fordiac.ide.structuredtextalgorithm.Messages
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmSource
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmSourceElement
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STFeatureExpression
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionPackage
import org.eclipse.xtext.resource.IContainer
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.resource.IResourceDescription
import org.eclipse.xtext.resource.IResourceDescriptions
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider
import org.eclipse.xtext.validation.Check

/**
 * This class contains custom validation rules. 
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#validation
 */
class STAlgorithmValidator extends AbstractSTAlgorithmValidator {

	@Inject
	ResourceDescriptionsProvider resourceDescriptionsProvider

	@Inject
	IContainer.Manager containerManager

	public static final String ISSUE_CODE_PREFIX = "org.eclipse.fordiac.ide.structuredtextalgorithm."; // $NON-NLS-1$
	public static final String DUPLICATE_METHOD_OR_ALGORITHM_NAME = ISSUE_CODE_PREFIX +
		"duplicateAlgorithmOrMethodName"; // $NON-NLS-1$

	@Check
	def checkUniqunessOfSTAlgorithmSourceElementNames(STAlgorithmSourceElement sourceElement) {
		val parent = sourceElement.eContainer as STAlgorithmSource
		if (parent.elements.filter[it.name.equalsIgnoreCase(sourceElement.name)].toList.size != 1) {
			error(
				MessageFormat.format(Messages.STAlgorithmValidator_Duplicate_Method_Or_Algorithm_Name,
					sourceElement.name), sourceElement, LibraryElementPackage.Literals.INAMED_ELEMENT__NAME,
				DUPLICATE_METHOD_OR_ALGORITHM_NAME, sourceElement.getName())
		}
	}

	@Check
	def checkUniquenessOfSTAlgorithmSourceElementNamesAndFunctionNames(STFeatureExpression featureExpression) {
		val IResourceDescriptions resourceDescriptions = resourceDescriptionsProvider.getResourceDescriptions(
			featureExpression.feature.eResource());
		val IResourceDescription resourceDescription = resourceDescriptions.getResourceDescription(
			featureExpression.feature.eResource().getURI());
		for (IContainer c : containerManager.getVisibleContainers(resourceDescription, resourceDescriptions)) {
			for (IEObjectDescription od : c.getExportedObjectsByType(STFunctionPackage.Literals.ST_FUNCTION)) {
				if (featureExpression.feature.getName().equalsIgnoreCase(od.getQualifiedName().toString()) &&
					!od.getEObjectURI().equals(EcoreUtil.getURI(featureExpression))) {
					warning(
						MessageFormat.format(
							Messages.STAlgorithmValidator_Unqualified_Method_Or_Algorithm_Shadowing_Function,
							featureExpression.feature.getName(), od.EObjectURI.toPlatformString(true)),
						STCorePackage.Literals.ST_FEATURE_EXPRESSION__FEATURE);
				}
			}
		}

	}
}
