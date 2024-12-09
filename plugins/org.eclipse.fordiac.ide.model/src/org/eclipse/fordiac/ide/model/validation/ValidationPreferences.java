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
package org.eclipse.fordiac.ide.model.validation;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

public final class ValidationPreferences {

	public static final String QUALIFIER = "org.eclipse.fordiac.ide.model.validation"; //$NON-NLS-1$

	public static final String HAS_PROJECT_SPECIFIC_OPTIONS = "hasProjectSpecificOptions"; //$NON-NLS-1$

	public static final String SEVERITY_IGNORE = "ignore"; //$NON-NLS-1$
	public static final String SEVERITY_INFO = "info"; //$NON-NLS-1$
	public static final String SEVERITY_WARNING = "warning"; //$NON-NLS-1$
	public static final String SEVERITY_ERROR = "error"; //$NON-NLS-1$

	public static final String COLLISION_SEVERITY = "collisionSeverity"; //$NON-NLS-1$

	public static int getDiagnosticSeverity(final String key, final int defaultValue, final EObject context) {
		final Resource resource = context != null ? context.eResource() : null;
		return getDiagnosticSeverity(key, defaultValue, resource);
	}

	public static int getDiagnosticSeverity(final String key, final int defaultValue, final Resource resource) {
		final URI uri = resource != null ? resource.getURI() : null;
		return getDiagnosticSeverity(key, defaultValue, uri);
	}

	public static int getDiagnosticSeverity(final String key, final int defaultValue, final URI uri) {
		final IResource resource = uri != null && uri.isPlatformResource()
				? ResourcesPlugin.getWorkspace().getRoot().findMember(uri.toPlatformString(true))
				: null;
		return getDiagnosticSeverity(key, defaultValue, resource);
	}

	public static int getDiagnosticSeverity(final String key, final int defaultValue, final IResource resource) {
		final IProject project = resource != null ? resource.getProject() : null;
		return getDiagnosticSeverity(key, defaultValue, project);
	}

	public static int getDiagnosticSeverity(final String key, final int defaultValue, final IProject project) {
		final IScopeContext[] contexts = project != null
				? new IScopeContext[] { new ProjectScope(project), InstanceScope.INSTANCE }
				: new IScopeContext[] { InstanceScope.INSTANCE };
		return getDiagnsoticSeverity(Platform.getPreferencesService().getString(QUALIFIER, key, null, contexts),
				defaultValue);
	}

	private static int getDiagnsoticSeverity(final String value, final int defaultValue) {
		return switch (value) {
		case SEVERITY_IGNORE -> Diagnostic.OK;
		case SEVERITY_INFO -> Diagnostic.INFO;
		case SEVERITY_WARNING -> Diagnostic.WARNING;
		case SEVERITY_ERROR -> Diagnostic.ERROR;
		case null, default -> defaultValue;
		};
	}

	private ValidationPreferences() {
		throw new UnsupportedOperationException();
	}
}
