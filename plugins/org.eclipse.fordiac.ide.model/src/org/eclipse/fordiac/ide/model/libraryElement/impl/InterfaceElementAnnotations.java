/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.model.libraryElement.impl;

import static org.eclipse.fordiac.ide.model.helpers.ArraySizeHelper.getArraySize;

import java.text.MessageFormat;
import java.util.Map;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.FordiacKeywords;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.helpers.ImportHelper;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.util.LibraryElementValidator;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;

public final class InterfaceElementAnnotations {
	private static final String NAMED_ELEMENTS_KEY = InterfaceElementAnnotations.class.getName() + ".NAMED_ELEMENTS"; //$NON-NLS-1$

	public static String getFullTypeName(final IInterfaceElement element) {
		return ImportHelper.deresolveImport(element.getType(), element);
	}

	public static String getFullTypeName(final VarDeclaration element) {
		final String typeName = ImportHelper.deresolveImport(element.getType(), element);
		if (element.isArray() && typeName != null && !typeName.isBlank()) {
			final String arraySize = getArraySize(element);
			if (!arraySize.contains("..")) { //$NON-NLS-1$
				try {
					return "ARRAY [0.." + (Integer.parseInt(arraySize) - 1) + "] OF " + typeName; //$NON-NLS-1$ //$NON-NLS-2$
				} catch (final NumberFormatException e) {
					// fallthrough
				}
			}
			return "ARRAY [" + arraySize + "] OF " + typeName; //$NON-NLS-1$ //$NON-NLS-2$
		}
		return typeName;
	}

	public static boolean isInOutVar(final VarDeclaration varDecl) {
		return LibraryElementPackage.eINSTANCE.getInterfaceList_InOutVars().equals(varDecl.eContainingFeature())
				|| LibraryElementPackage.eINSTANCE.getInterfaceList_OutMappedInOutVars()
						.equals(varDecl.eContainingFeature());
	}

	public static boolean validateName(final IInterfaceElement element, final DiagnosticChain diagnostics,
			final Map<Object, Object> context) {
		if (isErrorMarker(element) || isInTypedInstance(element)) {
			return true; // do not check error markers or inside typed instances
		}
		if (!NamedElementAnnotations.validateName(element, diagnostics, context)) {
			return false;
		}
		if (!isOutMappedInOutVar(element)
				&& !NamedElementAnnotations.validateDuplicateName(element, diagnostics, context, NAMED_ELEMENTS_KEY)) {
			return false;
		}
		if (!FordiacKeywords.DT.equals(element.getName()) // allow "DT" for IEC 61499 standard blocks
				&& existsDataType(getDataTypeLibrary(element), element.getName())) {
			if (diagnostics != null) {
				diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR, LibraryElementValidator.DIAGNOSTIC_SOURCE,
						LibraryElementValidator.IINTERFACE_ELEMENT__VALIDATE_NAME,
						MessageFormat.format(Messages.InterfaceElementAnnotations_MemberNameCollidesWithDataType,
								element.getQualifiedName()),
						FordiacMarkerHelper.getDiagnosticData(element,
								LibraryElementPackage.Literals.INAMED_ELEMENT__NAME, element.getQualifiedName())));
			}
			return false;
		}
		return true;
	}

	static boolean isErrorMarker(final IInterfaceElement element) {
		return element instanceof ErrorMarkerInterface;
	}

	static boolean isInTypedInstance(final IInterfaceElement element) {
		final FBNetworkElement fbNetworkElement = element.getFBNetworkElement();
		return fbNetworkElement != null && fbNetworkElement.getTypeEntry() != null;
	}

	static boolean isOutMappedInOutVar(final IInterfaceElement element) {
		return element instanceof final VarDeclaration varDeclaration && varDeclaration.isInOutVar()
				&& !varDeclaration.isIsInput();
	}

	static boolean existsDataType(final DataTypeLibrary dataTypeLibrary, final String name) {
		return dataTypeLibrary != null && dataTypeLibrary.getTypeIfExists(name) != null;
	}

	static DataTypeLibrary getDataTypeLibrary(final IInterfaceElement element) {
		if (EcoreUtil.getRootContainer(element) instanceof final LibraryElement libraryElement) {
			final TypeLibrary typeLibrary = libraryElement.getTypeLibrary();
			if (typeLibrary != null) {
				return typeLibrary.getDataTypeLibrary();
			}
		}
		return null;
	}

	private InterfaceElementAnnotations() {
		throw new UnsupportedOperationException();
	}
}
