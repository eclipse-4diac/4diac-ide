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
package org.eclipse.fordiac.ide.gef.annotation;

import org.eclipse.core.expressions.EvaluationContext;
import org.eclipse.core.expressions.EvaluationResult;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.expressions.ExpressionConverter;
import org.eclipse.core.expressions.ExpressionTagNames;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SafeRunner;

public class GraphicalAnnotationProviderDescriptor {

	private static final String CLASS_ATTRIBUTE = "class"; //$NON-NLS-1$
	private final IConfigurationElement element;
	private Expression enablement;

	public GraphicalAnnotationProviderDescriptor(final IConfigurationElement element) {
		this.element = element;
	}

	public boolean isEnabled(final Object context) {
		try {
			return getEnablement().evaluate(new EvaluationContext(null, context)) == EvaluationResult.TRUE;
		} catch (final CoreException e) {
			log("Exception occurred evaluating enablement expression", e); //$NON-NLS-1$
			return false;
		}
	}

	public GraphicalAnnotationProvider createProvider() {
		return SafeRunner.run(() -> (GraphicalAnnotationProvider) element.createExecutableExtension(CLASS_ATTRIBUTE));
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
			log("More than one enablement sub-element", null); //$NON-NLS-1$
			return Expression.FALSE;
		}
		try {
			return ExpressionConverter.getDefault().perform(children[0]);
		} catch (final CoreException e) {
			log("Invalid enablement expression", e); //$NON-NLS-1$
			return Expression.FALSE;
		}
	}

	private void log(final String message, final Throwable t) {
		Platform.getLog(getClass()).error(String.format("%s in extension %s from plugin %s", message, //$NON-NLS-1$
				element.getDeclaringExtension().getExtensionPointUniqueIdentifier(),
				element.getDeclaringExtension().getContributor().getName()), t);
	}
}
