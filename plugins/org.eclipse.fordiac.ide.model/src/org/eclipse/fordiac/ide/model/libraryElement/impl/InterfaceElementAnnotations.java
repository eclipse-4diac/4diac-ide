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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.helpers.ImportHelper;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.util.LibraryElementValidator;
import org.eclipse.fordiac.ide.model.validation.ValidationPreferences;

public final class InterfaceElementAnnotations {
	public static List<String> getBlockRelativePath(final IInterfaceElement element) {
		final List<String> path = (element.eContainer() instanceof final IInterfaceElement parent)
				? parent.getBlockRelativePath()
				: new ArrayList<>();
		path.add(element.getName());
		return path;
	}

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

	public static BlockFBNetworkElement getBlockFBNetworkElement(final IInterfaceElement element) {
		return switch (element.eContainer()) {
		case final BlockFBNetworkElement blockFbNetworkElement -> blockFbNetworkElement;
		case final InterfaceList interfaceList -> interfaceList.getBlockFBNetworkElement();
		case final IInterfaceElement varDecl -> varDecl.getBlockFBNetworkElement();
		case null, default -> null;
		};
	}

	public static FBType getFBType(final IInterfaceElement element) {
		return switch (element.eContainer()) {
		case final FBType fbType -> fbType;
		case final InterfaceList interfaceList -> interfaceList.getFBType();
		case final IInterfaceElement varDecl -> varDecl.getFBType();
		case null, default -> null;
		};
	}

	public static InterfaceList getInterfaceList(final IInterfaceElement element) {
		return switch (element.eContainer()) {
		case final InterfaceList interfaceList -> interfaceList;
		case final IInterfaceElement parent -> parent.getInterfaceList();
		case null, default -> null;
		};
	}

	public static boolean validateName(final IInterfaceElement element, final DiagnosticChain diagnostics,
			final Map<Object, Object> context) {
		if (isErrorMarker(element) || isDerivedInstance(element) || isOutMappedInOutVar(element)) {
			return true; // do not check error markers, derived instances (from a type or via mapping),
							// or out-mapped InOut variables
		}
		return NamedElementAnnotations.validateName(element, diagnostics, context)
				&& NamedElementAnnotations.validateDuplicateName(element, diagnostics, context);
	}

	public static boolean validateUnused(final IInterfaceElement element, final DiagnosticChain diagnostics,
			final Map<Object, Object> context) {
		if (isErrorMarker(element) || isDerivedInstance(element) || isOutMappedInOutVar(element)) {
			return true; // do not check error markers, derived instances (from a type or via mapping),
							// or out-mapped InOut variables
		}
		if (element.getBlockFBNetworkElement() instanceof UntypedSubApp) {
			if (!hasInternalConnections(element)) {
				addUnusedDiagnostic(Messages.InterfaceElementAnnotations_UnusedInternal, element, diagnostics);
				return false;
			}
			if (!hasExternalConnections(element) && !hasResourceExternalConnections(element)
					&& !(element instanceof final VarDeclaration varDeclaration && varDeclaration.hasValue())) {
				addUnusedDiagnostic(Messages.InterfaceElementAnnotations_UnusedExternal, element, diagnostics);
				return false;
			}
		} else if (element.getFBType() instanceof CompositeFBType && !hasInternalConnections(element)
				&& (element.getFBType() instanceof SubAppType || !(element instanceof AdapterDeclaration))) {
			addUnusedDiagnostic(Messages.InterfaceElementAnnotations_UnusedInternal, element, diagnostics);
			return false;
		}
		return true;
	}

	private static boolean hasInternalConnections(final IInterfaceElement element) {
		return !getInternalConnections(element).isEmpty()
				|| (element instanceof final VarDeclaration varDeclaration && varDeclaration.isInOutVar()
						&& !getInternalConnections(varDeclaration.getInOutVarOpposite()).isEmpty());
	}

	private static boolean hasExternalConnections(final IInterfaceElement element) {
		return !getExternalConnections(element).isEmpty()
				|| (element instanceof final VarDeclaration varDeclaration && varDeclaration.isInOutVar()
						&& !getExternalConnections(varDeclaration.getInOutVarOpposite()).isEmpty());
	}

	private static EList<Connection> getInternalConnections(final IInterfaceElement element) {
		return element.isIsInput() ? element.getOutputConnections() : element.getInputConnections();
	}

	private static EList<Connection> getExternalConnections(final IInterfaceElement element) {
		return element.isIsInput() ? element.getInputConnections() : element.getOutputConnections();
	}

	private static boolean hasResourceExternalConnections(final IInterfaceElement element) {
		if (element == null) {
			return false;
		}

		final BlockFBNetworkElement fbne = element.getBlockFBNetworkElement();
		if (fbne == null || !fbne.isMapped()) {
			return false;
		}

		final IInterfaceElement oppositeElement = fbne.getOpposite().getInterface().getInterfaceElement(element);
		if (oppositeElement == null) {
			return false;
		}

		return hasExternalConnections(oppositeElement);
	}

	private static void addUnusedDiagnostic(final String message, final IInterfaceElement element,
			final DiagnosticChain diagnostics) {
		if (diagnostics != null) {
			diagnostics.add(new BasicDiagnostic(
					ValidationPreferences.getDiagnosticSeverity(ValidationPreferences.UNUSED_INTERFACE_ELEMENT,
							Diagnostic.WARNING, element),
					LibraryElementValidator.DIAGNOSTIC_SOURCE,
					LibraryElementValidator.IINTERFACE_ELEMENT__VALIDATE_UNUSED,
					MessageFormat.format(message, element.getQualifiedName()), FordiacMarkerHelper
							.getDiagnosticData(element, LibraryElementPackage.Literals.INAMED_ELEMENT__NAME)));
		}
	}

	private static boolean isErrorMarker(final IInterfaceElement element) {
		return element instanceof ErrorMarkerInterface;
	}

	private static boolean isDerivedInstance(final IInterfaceElement element) {
		final FBNetworkElement fbne = element.getBlockFBNetworkElement();
		return fbne != null && (fbne.getTypeEntry() != null || (fbne.isMapped() && fbne.getMapping().getTo() == fbne));
	}

	private static boolean isOutMappedInOutVar(final IInterfaceElement element) {
		return element instanceof final VarDeclaration varDeclaration && varDeclaration.isInOutVar()
				&& !varDeclaration.isIsInput();
	}

	static IInterfaceElement findInTypeInterface(final IInterfaceElement element) {
		final BlockFBNetworkElement blockFbnEl = element.getBlockFBNetworkElement();
		if (blockFbnEl == null) {
			return null;
		}

		final InterfaceList typeInterface = blockFbnEl.getTypeInterface();
		if (typeInterface == null) {
			return null;
		}

		return typeInterface.getInterfaceElement(element);
	}

	private InterfaceElementAnnotations() {
		throw new UnsupportedOperationException();
	}
}
