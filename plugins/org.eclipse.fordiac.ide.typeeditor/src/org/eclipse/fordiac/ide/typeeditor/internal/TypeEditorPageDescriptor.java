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
package org.eclipse.fordiac.ide.typeeditor.internal;

import java.text.MessageFormat;

import org.eclipse.core.expressions.EvaluationContext;
import org.eclipse.core.expressions.EvaluationResult;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.expressions.ExpressionConverter;
import org.eclipse.core.expressions.ExpressionTagNames;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.typeeditor.ITypeEditorPage;

public class TypeEditorPageDescriptor {

	private static final String CLASS_ATTRIBUTE = "class"; //$NON-NLS-1$
	private static final String SORT_INDEX_ATTRIBUTE = "sortIndex"; //$NON-NLS-1$
	private static final String LOG_MESSAGE = "{0} in extension {1} from plugin {2}"; //$NON-NLS-1$

	private final IConfigurationElement element;
	private Expression enablement;
	private int sortIndex = -1;

	public TypeEditorPageDescriptor(final IConfigurationElement element) {
		this.element = element;
	}

	public boolean handlesType(final LibraryElement type) {
		try {
			return getEnablement().evaluate(new EvaluationContext(null, type)) == EvaluationResult.TRUE;
		} catch (final CoreException e) {
			log("Exception occurred evaluating enablement expression", e); //$NON-NLS-1$
			return false;
		}
	}

	public ITypeEditorPage createEditorPage() {
		return SafeRunner.run(() -> (ITypeEditorPage) element.createExecutableExtension(CLASS_ATTRIBUTE));
	}

	public int getSortIndex() {
		if (sortIndex < 0) {
			try {
				sortIndex = Integer.parseInt(element.getAttribute(SORT_INDEX_ATTRIBUTE));
				if (sortIndex < 0) {
					sortIndex = 0;
				}
			} catch (final NumberFormatException e) {
				log("Invalid sortIndex attribute", e); //$NON-NLS-1$
				sortIndex = Integer.MAX_VALUE;
			}
		}
		return sortIndex;
	}

	private Expression getEnablement() {
		if (enablement == null) {
			enablement = createEnablement();
		}
		return enablement;
	}

	private Expression createEnablement() {
		final IConfigurationElement[] children = element.getChildren(ExpressionTagNames.ENABLEMENT);
		if (children.length == 0) {
			return Expression.TRUE;
		}
		if (children.length > 1) {
			log("More than one enablement sub-element"); //$NON-NLS-1$
			return Expression.FALSE;
		}
		try {
			return ExpressionConverter.getDefault().perform(children[0]);
		} catch (final CoreException e) {
			log("Invalid enablement expression", e); //$NON-NLS-1$
			return Expression.FALSE;
		}
	}

	private void log(final String message) {
		Platform.getLog(getClass())
				.error(MessageFormat.format(LOG_MESSAGE, message,
						element.getDeclaringExtension().getExtensionPointUniqueIdentifier(),
						element.getDeclaringExtension().getContributor().getName()));
	}

	private void log(final String message, final Throwable t) {
		Platform.getLog(getClass())
				.error(String.format(LOG_MESSAGE, message,
						element.getDeclaringExtension().getExtensionPointUniqueIdentifier(),
						element.getDeclaringExtension().getContributor().getName()), t);
	}
}
