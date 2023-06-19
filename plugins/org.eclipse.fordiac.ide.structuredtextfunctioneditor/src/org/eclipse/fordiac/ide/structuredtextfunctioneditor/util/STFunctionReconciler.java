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
package org.eclipse.fordiac.ide.structuredtextfunctioneditor.util;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ICallable;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.STFunction;
import org.eclipse.fordiac.ide.model.libraryElement.STFunctionBody;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.structuredtextcore.util.STCoreReconciler;

public class STFunctionReconciler implements STCoreReconciler {

	@Override
	public void reconcile(final FBType dest, final EList<? extends ICallable> source) {
		if (dest instanceof final FunctionFBType functionFbType) {
			reconcile(functionFbType, source);
		}
	}

	protected static void reconcile(final FunctionFBType dest, final EList<? extends ICallable> source) {
		// check duplicates in source (very bad)
		if (checkDuplicates(source)) {
			return; // don't even try to attempt this or risk screwing dest up
		}
		// initialize
		dest.setComment(""); //$NON-NLS-1$
		dest.setBody(LibraryElementFactory.eINSTANCE.createSTFunctionBody());
		// merge body
		source.forEach(callable -> merge(dest, callable));
		// reconcile interface
		if (!source.isEmpty() && source.get(0) instanceof final STFunction function) {
			reconcileInterface(dest, function);
		}
	}

	protected static void reconcileInterface(final FunctionFBType dest, final STFunction source) {
		ECollections.setEList(dest.getInterfaceList().getInputVars(),
				source.getInputParameters().stream().map(VarDeclaration.class::cast).map(EcoreUtil::copy).toList());
		ECollections.setEList(dest.getInterfaceList().getOutputVars(),
				source.getOutputParameters().stream().map(VarDeclaration.class::cast).map(EcoreUtil::copy).toList());
		ECollections.setEList(dest.getInterfaceList().getInOutVars(),
				source.getInOutParameters().stream().map(VarDeclaration.class::cast).map(EcoreUtil::copy).toList());
		if (source.getReturnType() != null) {
			final VarDeclaration returnVar = LibraryElementFactory.eINSTANCE.createVarDeclaration();
			returnVar.setName(""); //$NON-NLS-1$
			returnVar.setType(source.getReturnType());
			dest.getInterfaceList().getOutputVars().add(0, returnVar);
		}
	}

	protected static void merge(final FunctionFBType dest, final ICallable source) {
		if (source instanceof final STFunction sourceFunction) {
			merge(dest, sourceFunction);
		}
	}

	protected static void merge(final FunctionFBType dest, final STFunction source) {
		dest.setComment(dest.getComment() + source.getComment());
		if (dest.getBody() instanceof final STFunctionBody body) {
			if (body.getText() != null) {
				body.setText(body.getText() + source.getText());
			} else {
				body.setText(source.getText());
			}
		}
	}

	protected static boolean checkDuplicates(final EList<? extends ICallable> list) {
		return list.stream().map(ICallable::getName).distinct().count() != list.size();
	}
}
