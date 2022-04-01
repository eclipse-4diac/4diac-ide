/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
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
 *
 *   Ulzii Jargalsaikhan
 *       - custom validation for identifiers
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextcore.validation;

import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.xtext.validation.Check;

public class STCoreValidator extends AbstractSTCoreValidator {

	public static final String ISSUE_CODE_PREFIX = "org.eclipse.fordiac.ide.structuredtextcore."; //$NON-NLS-1$
	public static final String INVALID_ID_FORMAT = ISSUE_CODE_PREFIX + "invalidIDFormat"; //$NON-NLS-1$

	private static final String INVALID_ID_MESSAGE = "Identifiers shall not contain more than one consecutive \"_\" character"; //$NON-NLS-1$

	@Check
	public void checkValidIDFormat(final INamedElement iNamedElement) {
		if (iNamedElement.getName().indexOf("__") != -1) { //$NON-NLS-1$
			error(INVALID_ID_MESSAGE, iNamedElement, LibraryElementPackage.Literals.INAMED_ELEMENT__NAME,
					INVALID_ID_FORMAT);
		}
	}

}
