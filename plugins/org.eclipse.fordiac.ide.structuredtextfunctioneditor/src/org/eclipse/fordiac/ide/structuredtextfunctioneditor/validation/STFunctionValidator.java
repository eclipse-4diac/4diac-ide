/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies GmbH
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextfunctioneditor.validation;

import java.text.MessageFormat;

import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.Messages;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunction;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionPackage;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.resource.IContainer;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;
import org.eclipse.xtext.validation.Check;

import com.google.inject.Inject;

public class STFunctionValidator extends AbstractSTFunctionValidator {

	@Inject
	ResourceDescriptionsProvider resourceDescriptionsProvider;

	@Inject
	IContainer.Manager containerManager;

	public static final String ISSUE_CODE_PREFIX = "org.eclipse.fordiac.ide.structuredtextfunction."; //$NON-NLS-1$
	public static final String DUPLICATE_FUNCTION_NAME = ISSUE_CODE_PREFIX + "duplicateFunctionName"; //$NON-NLS-1$

	/** Check on duplicate names in self-defined functions. Standard functions are already checked in STCore */
	@Check
	public void checkDuplicateFunctionNames(final STFunction function) {
		final IResourceDescriptions resourceDescriptions = resourceDescriptionsProvider
				.getResourceDescriptions(function.eResource());
		final IResourceDescription resourceDescription = resourceDescriptions
				.getResourceDescription(function.eResource().getURI());
		for (final IContainer c : containerManager.getVisibleContainers(resourceDescription, resourceDescriptions)) {
			for (final IEObjectDescription od : c.getExportedObjectsByType(STFunctionPackage.Literals.ST_FUNCTION)) {
				if (function.getName().equalsIgnoreCase(od.getQualifiedName().toString())
						&& !od.getEObjectURI().equals(EcoreUtil2.getNormalizedURI(function))) {
					error(MessageFormat.format(Messages.STFunctionValidator_Duplicate_Function_Name,
							function.getName(), od.getEObjectURI().toPlatformString(true)),
							LibraryElementPackage.Literals.INAMED_ELEMENT__NAME, DUPLICATE_FUNCTION_NAME);
				}
			}
		}
	}
}
