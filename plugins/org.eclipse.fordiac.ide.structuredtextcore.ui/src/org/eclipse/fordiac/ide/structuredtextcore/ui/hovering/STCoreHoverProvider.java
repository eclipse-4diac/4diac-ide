/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextcore.ui.hovering;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration;
import org.eclipse.fordiac.ide.structuredtextcore.ui.Messages;
import org.eclipse.xtext.ui.editor.hover.html.DefaultEObjectHoverProvider;

public class STCoreHoverProvider extends DefaultEObjectHoverProvider {

	@Override
	protected String getFirstLine(final EObject o) {
		final String label = getLabel(o);
		if (label != null) {
			final String kind = getKind(o);
			if (kind != null) {
				return kind + " <b>" + label + "</b>"; //$NON-NLS-1$ //$NON-NLS-2$
			}
			return "<b>" + label + "</b>"; //$NON-NLS-1$ //$NON-NLS-2$
		}
		return super.getFirstLine(o);
	}

	protected String getKind(final EObject o) {
		return switch (o) {
		case final VarDeclaration varDeclaration -> getKind(varDeclaration);
		case final STVarDeclaration stVarDeclaration -> getKind(stVarDeclaration);
		case final Event event -> Messages.STCoreHoverProvider_EventKind;
		case final FB fb -> Messages.STCoreHoverProvider_FBKind;
		case final FBType fbType -> Messages.STCoreHoverProvider_FBTypeKind;
		case final StructuredType struct -> Messages.STCoreHoverProvider_StructKind;
		default -> null;
		};
	}

	protected static String getKind(final VarDeclaration varDeclaration) {
		final EReference reference = varDeclaration.eContainmentFeature();
		if (reference == LibraryElementPackage.Literals.INTERFACE_LIST__INPUT_VARS) {
			return Messages.STCoreHoverProvider_VarInputKind;
		}
		if (reference == LibraryElementPackage.Literals.INTERFACE_LIST__OUTPUT_VARS) {
			return Messages.STCoreHoverProvider_VarOutputKind;
		}
		if (reference == LibraryElementPackage.Literals.INTERFACE_LIST__IN_OUT_VARS) {
			return Messages.STCoreHoverProvider_VarInOutKind;
		}
		if (reference == LibraryElementPackage.Literals.BASE_FB_TYPE__INTERNAL_VARS) {
			return Messages.STCoreHoverProvider_VarInternalKind;
		}
		if (reference == LibraryElementPackage.Literals.BASE_FB_TYPE__INTERNAL_CONST_VARS) {
			return Messages.STCoreHoverProvider_VarInternalConstKind;
		}
		return null;
	}

	protected String getKind(final STVarDeclaration varDeclaration) {
		return getLabel(varDeclaration.eContainer());
	}
}
