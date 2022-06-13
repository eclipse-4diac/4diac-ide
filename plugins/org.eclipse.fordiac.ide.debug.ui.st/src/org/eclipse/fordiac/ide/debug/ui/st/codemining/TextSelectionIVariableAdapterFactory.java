/*******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.debug.ui.st.codemining;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

import org.eclipse.core.runtime.Adapters;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.debug.EvaluatorDebugStackFrame;
import org.eclipse.fordiac.ide.debug.EvaluatorDebugVariable;
import org.eclipse.fordiac.ide.model.eval.Evaluator;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.xtext.resource.EObjectAtOffsetHelper;
import org.eclipse.xtext.ui.editor.model.XtextDocument;

public class TextSelectionIVariableAdapterFactory implements IAdapterFactory {

	private static final EObjectAtOffsetHelper offsetHelper = new EObjectAtOffsetHelper();

	@Override
	public <T> T getAdapter(final Object adaptableObject, final Class<T> adapterType) {
		if (!adapterType.isAssignableFrom(IVariable.class)) {
			return null;
		}
		if (!(adaptableObject instanceof TextSelection)) {
			return null;
		}
		final TextSelection selection = (TextSelection) adaptableObject;
		final IDocument document = getDocument(selection);
		if (document == null) {
			return null;
		}
		final EvaluatorDebugStackFrame frame = getFrame();
		if (frame == null) {
			return null;
		}
		final Evaluator evaluator = frame.getEvaluator();
		if (evaluator == null) {
			return null;
		}
		final Object sourceElement = evaluator.getSourceElement();
		if (!(sourceElement instanceof EObject)) {
			return null;
		}
		final URI sourceUri = EcoreUtil.getURI((EObject) sourceElement);
		final URI documentUri = Adapters.adapt(document, URI.class);
		if (sourceUri == null || !Objects.equals(sourceUri.trimFragment(), documentUri)) {
			return null;
		}
		final INamedElement sourceVariable = getSourceVariableAt(document, selection.getOffset());
		if (sourceVariable == null) {
			return null;
		}
		return adapterType.cast(getVariable(frame, sourceVariable));
	}

	protected static INamedElement getSourceVariableAt(final IDocument document, final int offset) {
		if (document instanceof XtextDocument) {
			return ((XtextDocument) document).readOnly(resource -> {
				EObject candidate = offsetHelper.resolveElementAt(resource, offset);
				if (candidate instanceof VarDeclaration || candidate instanceof STVarDeclaration) {
					return (INamedElement) candidate;
				}
				candidate = offsetHelper.resolveCrossReferencedElementAt(resource, offset);
				if (candidate instanceof VarDeclaration || candidate instanceof STVarDeclaration) {
					return (INamedElement) candidate;
				}
				return null;
			});
		}
		return null;
	}

	protected static IDocument getDocument(final TextSelection sel) {
		try {
			final Method documentMethod = TextSelection.class.getDeclaredMethod("getDocument"); //$NON-NLS-1$
			documentMethod.setAccessible(true); // necessary until TextSelection.getDocument() is made public
			return (IDocument) documentMethod.invoke(sel);
		} catch (NoSuchMethodException | IllegalArgumentException | IllegalAccessException
				| InvocationTargetException e) {
			return null;
		}
	}

	protected static EvaluatorDebugVariable getVariable(final EvaluatorDebugStackFrame frame,
			final INamedElement sourceVariable) {
		final EvaluatorDebugVariable variable = frame.getVariable(sourceVariable.getName());
		if (variable != null) {
			return variable;
		}
		final EvaluatorDebugVariable context = frame.getVariable(Evaluator.CONTEXT_NAME);
		if (context != null) {
			return context.getValue().getVariable(sourceVariable.getName());
		}
		return null;
	}

	protected static EvaluatorDebugStackFrame getFrame() {
		final IAdaptable adaptable = DebugUITools.getDebugContext();
		if (adaptable != null) {
			return adaptable.getAdapter(EvaluatorDebugStackFrame.class);
		}
		return null;
	}

	@Override
	public Class<?>[] getAdapterList() {
		return new Class<?>[] { IVariable.class };
	}
}
