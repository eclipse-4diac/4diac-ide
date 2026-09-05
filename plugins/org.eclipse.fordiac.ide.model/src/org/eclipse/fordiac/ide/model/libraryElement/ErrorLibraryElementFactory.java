/*******************************************************************************
 * Copyright (c) 2026 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.model.libraryElement;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.fordiac.ide.model.libraryElement.impl.ErrorLibraryElementFactoryImpl;

public interface ErrorLibraryElementFactory {

	ErrorLibraryElementFactory INSTANCE = new ErrorLibraryElementFactoryImpl();

	ErrorLibraryElement create(String fullTypeName, final EClass eClass);

	ErrorAdapterType createErrorAdapterType(String fullTypeName);

	ErrorAttributeDeclaration createErrorAttributeDeclaration(String fullTypeName);

	ErrorAutomationSystem createErrorAutomationSystem(String fullTypeName);

	ErrorDeviceType createErrorDeviceType(String fullTypeName);

	ErrorFBType createErrorFBType(String fullTypeName);

	ErrorFunctionFBType createErrorFunctionFBType(String fullTypeName);

	ErrorGlobalConstants createErrorGlobalConstants(String fullTypeName);

	ErrorLibraryElement createErrorLibraryElement(String fullTypeName);

	ErrorResourceType createErrorResourceType(String fullTypeName);

	ErrorSegmentType createErrorSegmentType(String fullTypeName);

	ErrorSubAppType createErrorSubAppType(String fullTypeName);
}
