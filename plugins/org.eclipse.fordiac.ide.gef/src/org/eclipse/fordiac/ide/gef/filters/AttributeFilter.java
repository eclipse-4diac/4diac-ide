/*******************************************************************************
 * Copyright (c) 2023, 2025 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.gef.filters;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

public class AttributeFilter implements IFilter {

	@Override
	public boolean select(final Object toTest) {
		return parseObject(toTest) instanceof ConfigurableObject;
	}

	public static Object parseObject(final Object input) {
		return switch (input) {
		// for var_in_outs we can only have attributes on the input side
		case final VarDeclaration varDecl when varDecl.isInOutVar() && !varDecl.isIsInput() ->
			varDecl.getInOutVarOpposite();
		// handle exception: interface elements of functions
		case final IInterfaceElement interfaceElement when interfaceElement.getFBType() instanceof FunctionFBType ->
			null;
		// handle exception: struct of MUX/DEMUX
		case final EditPart editpart when editpart.getModel() instanceof StructuredType -> null;
		case final ConfigurableObject configurableObject -> configurableObject;
		case final FBNetwork fbNetwork -> parseObject(fbNetwork.eContainer());
		case final EditPart editpart -> parseObject(editpart.getModel());
		// handle exception: typefield of interface elements of functions
		case final IAdaptable adaptable when adaptable
				.getAdapter(ConfigurableObject.class) instanceof final IInterfaceElement ie
				&& ie.getFBType() instanceof FunctionFBType ->
			null;
		case final IAdaptable adaptable -> adaptable.getAdapter(ConfigurableObject.class);
		case final TextSelection textSel -> getConfObjectFromActiveEditor();
		case null, default -> null;
		};
	}

	private static ConfigurableObject getConfObjectFromActiveEditor() {
		final IEditorPart activeEditor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getActiveEditor();

		if (activeEditor != null) {
			return activeEditor.getAdapter(LibraryElement.class);
		}
		return null;
	}
}
