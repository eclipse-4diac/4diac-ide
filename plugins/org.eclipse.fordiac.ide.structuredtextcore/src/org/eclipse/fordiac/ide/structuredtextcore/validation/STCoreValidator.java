/*******************************************************************************
 * Copyright (c) 2021 - 2022 Primetals Technologies Austria GmbH
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
 *       - adds check for trailing underscore on identifiers
 *
 *   Ulzii Jargalsaikhan
 *       - custom validation for identifiers
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextcore.validation;

import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.structuredtextcore.Messages;
import org.eclipse.xtext.validation.Check;

public class STCoreValidator extends AbstractSTCoreValidator {

	public static final String ISSUE_CODE_PREFIX = "org.eclipse.fordiac.ide.structuredtextcore."; //$NON-NLS-1$
	public static final String CONSECUTIVE_UNDERSCORE_IN_IDENTIFIER_ERROR = ISSUE_CODE_PREFIX
			+ "consecutiveUnderscoreInIdentifierError"; //$NON-NLS-1$
	public static final String TRAILING_UNDERSCORE_IN_IDENTIFIER_ERROR = ISSUE_CODE_PREFIX
			+ "identiferEndsInUnderscoreError"; //$NON-NLS-1$

	@Check
	public void checkConsecutiveUnderscoresInIdentifier(final INamedElement iNamedElement) {
		if (iNamedElement.getName().indexOf("__") != -1) { //$NON-NLS-1$
			error(Messages.STCoreValidator_Consecutive_Underscores_In_Identifier, iNamedElement,
					LibraryElementPackage.Literals.INAMED_ELEMENT__NAME, CONSECUTIVE_UNDERSCORE_IN_IDENTIFIER_ERROR,
					iNamedElement.getName());
		}
	}

	@Check
	public void checkIdentiferForTrailingUnderscore(final INamedElement iNamedElement) {
		if (iNamedElement.getName().charAt(iNamedElement.getName().length() - 1) == '_') {
			error(Messages.STCoreValidator_Trailing_Underscore_In_Identifier, iNamedElement,
					LibraryElementPackage.Literals.INAMED_ELEMENT__NAME, TRAILING_UNDERSCORE_IN_IDENTIFIER_ERROR,
					iNamedElement.getName());
		}
	}

}
