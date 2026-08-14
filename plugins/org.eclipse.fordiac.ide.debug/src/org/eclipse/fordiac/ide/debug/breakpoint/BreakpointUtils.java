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
package org.eclipse.fordiac.ide.debug.breakpoint;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.debug.EvaluatorDebugStackFrame;
import org.eclipse.fordiac.ide.model.eval.Evaluator;
import org.eclipse.fordiac.ide.model.eval.EvaluatorFactory;
import org.eclipse.fordiac.ide.model.eval.value.Value;
import org.eclipse.fordiac.ide.model.eval.value.ValueOperations;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.util.FordiacLogHelper;

public final class BreakpointUtils {

	/**
	 * Evauate a breakpoint condition
	 *
	 * @param condition The condition
	 * @param frame     The current stack frame
	 * @return true if the condition matches, false otherwise
	 */
	public static boolean evaluateBreakpointCondition(final String condition, final EvaluatorDebugStackFrame frame) {
		try {
			final Evaluator evaluator = EvaluatorFactory.createEvaluator(condition, String.class, null, null,
					frame.getEvaluator());
			final Value result = evaluator.evaluate();
			return ValueOperations.asBoolean(result);
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
			return false;
		} catch (final Exception e) {
			FordiacLogHelper.logWarning("Couldn't evaluate breakpoint condition: " + e.getMessage(), e); //$NON-NLS-1$
			return false;
		}
	}

	/**
	 * Get the resource for a given context
	 *
	 * @param context The context
	 * @return The resource or null if not found
	 */
	public static IResource getResource(final Object context) {
		if (context instanceof final EObject eo) {
			final EObject root = EcoreUtil.getRootContainer(eo);
			if (root instanceof final LibraryElement libraryElement && libraryElement.getTypeEntry() != null) {
				return libraryElement.getTypeEntry().getFile();
			}
			return getResource(((EObject) context).eResource());
		}
		if (context instanceof final Resource resource) {
			final URI uri = resource.getURI();
			if (uri.isPlatformResource()) {
				final String path = uri.toPlatformString(true);
				return ResourcesPlugin.getWorkspace().getRoot().findMember(new Path(path));
			}
		}
		return null;
	}

	private BreakpointUtils() {
		throw new UnsupportedOperationException();
	}
}
