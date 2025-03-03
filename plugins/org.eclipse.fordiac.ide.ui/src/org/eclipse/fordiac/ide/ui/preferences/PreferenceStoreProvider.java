/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Patrick Aigner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.preferences;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;

/**
 * Encapsulates workspace/project scoped preference store access
 */
public class PreferenceStoreProvider {
	/**
	 * Flag signifying if project specific settings are active
	 */
	public static final String PROJECT_STORE_ACTIVE = "ProjectStoreActive"; //$NON-NLS-1$

	private final IPreferenceStore instanceStore;
	private final IPreferenceStore projectStore;

	public PreferenceStoreProvider(final String qualifier, final IProject project) {
		this.instanceStore = new FixedScopedPreferenceStore(InstanceScope.INSTANCE, qualifier);
		this.projectStore = (project != null) ? new FixedScopedPreferenceStore(new ProjectScope(project), qualifier)
				: null;
	}

	/**
	 * get the store with the correct scope (Instance or Project) depending on the
	 * {@link #PROJECT_STORE_ACTIVE} flag
	 *
	 * @return Preference Store with correct scope
	 */
	public IPreferenceStore getStore() {
		if (isProjectStoreActive()) {
			return projectStore;
		}
		return instanceStore;
	}

	/**
	 * Get Instance scoped Preference Store
	 *
	 * @return instance scoped Preference Store
	 */
	public IPreferenceStore getInstanceStore() {
		return instanceStore;
	}

	/**
	 * Get Project scoped Preference Store
	 *
	 * @return project scoped Preference Store
	 */
	public IPreferenceStore getProjectStore() {
		return projectStore;
	}

	/**
	 * Check if {@link #PROJECT_STORE_ACTIVE} flag is set
	 *
	 * @return {@code true}, if project specific preferences are enabled
	 */
	public boolean isProjectStoreActive() {
		return projectStore != null && projectStore.getBoolean(PROJECT_STORE_ACTIVE);
	}

	/**
	 * Set {@link #PROJECT_STORE_ACTIVE} flag
	 *
	 * @param active enable/disable flag
	 */
	public void setProjectStoreActive(final boolean active) {
		if (projectStore != null) {
			projectStore.setValue(PROJECT_STORE_ACTIVE, active);
		}
	}

	/**
	 * Adds a property change listener to the instance and project scoped stores
	 * (when available)
	 *
	 * @param listener a property change listener
	 */
	public void addPropertyChangeListener(final IPropertyChangeListener listener) {
		instanceStore.addPropertyChangeListener(listener);
		if (projectStore != null) {
			projectStore.addPropertyChangeListener(listener);
		}
	}

	/**
	 * Removes the given listener from the instance and project scoped stores (when
	 * available)
	 *
	 * @param listener a property change listener, must not be {@code null}
	 */
	public void removePropertyChangeListener(final IPropertyChangeListener listener) {
		instanceStore.removePropertyChangeListener(listener);
		if (projectStore != null) {
			projectStore.removePropertyChangeListener(listener);
		}
	}

	/**
	 * A convenience function to obtain the correct store for a project
	 * <p>
	 * <b>Note:</b> avoid attaching listeners to this store
	 * </p>
	 *
	 * @param qualifier ID of the store
	 * @param project   project context for the store
	 * @return instance or project based {@link IPreferenceStore} depending on the
	 *         activation of project-specific preferences
	 */
	public static IPreferenceStore getStore(final String qualifier, final IProject project) {
		final var projectScope = new FixedScopedPreferenceStore(new ProjectScope(project), qualifier);
		if (projectScope.getBoolean(PROJECT_STORE_ACTIVE)) {
			return projectScope;
		}
		return new FixedScopedPreferenceStore(InstanceScope.INSTANCE, qualifier);
	}

	/**
	 * A convenience function to obtain the correct store for an EObject
	 * <p>
	 * <b>Note:</b> avoid attaching listeners to this store
	 * </p>
	 *
	 * @param qualifier ID of the store
	 * @param context   EObject providing the context
	 * @return instance or project based {@link IPreferenceStore} depending on the
	 *         activation of project-specific preferences, or {@code null} if none
	 *         is available
	 */
	public static IPreferenceStore getStoreFromContext(final String qualifier, final EObject context) {
		// attempt to get from EMF resource through URI
		final Resource resource = context.eResource();
		if (resource != null) {
			return getStoreFromURI(qualifier, resource.getURI());
		}
		return null;
	}

	/**
	 * A convenience function to obtain the correct store for an EObject
	 * <p>
	 * <b>Note:</b> avoid attaching listeners to this store
	 * </p>
	 *
	 * @param qualifier ID of the store
	 * @param uri       URI providing the context
	 * @return instance or project based {@link IPreferenceStore} depending on the
	 *         activation of project-specific preferences, or {@code null} if none
	 *         is available
	 */
	public static IPreferenceStore getStoreFromURI(final String qualifier, final URI uri) {
		if (uri != null) {
			final IFile file;
			if (uri.isPlatformResource()) {
				file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(uri.toPlatformString(true)));
			} else if (uri.isFile() && uri.segmentCount() >= 2) { // need at least two segments for a valid file path
				file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(uri.toFileString()));
			} else {
				return null;
			}
			if (file.getProject() != null) {
				return getStore(qualifier, file.getProject());
			}
		}
		return null;
	}
}
