/*******************************************************************************
 * Copyright (c) 2025 Wolfgang Schedl.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Wolfgang Schedl - initial API and implementation
 *******************************************************************************/

package org.eclipse.fordiac.ide.gef.utilities;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.osgi.service.prefs.BackingStoreException;

/**
 * Tracks Most Recently Used (MRU) function block types per project.
 *
 * This class maintains a per-project history of recently used FB types and
 * stores them in Eclipse project-scoped preferences. The MRU list is ordered
 * with the most recently used item first, and is limited to a maximum of 5
 * entries.
 */
public class MostRecentlyUsedTracker {

	/** Preference node ID for storing MRU data */
	private static final String PREF_NODE = "org.eclipse.fordiac.ide.gef";

	/** Preference key for MRU list */
	private static final String PREF_KEY = "mru_fb_types"; //$NON-NLS-1$

	/** Delimiter for serializing MRU list */
	private static final String DELIMITER = ";"; //$NON-NLS-1$

	/** Maximum number of items to keep in MRU list */
	private static final int MAX_MRU_ITEMS = 5;

	/** Eclipse preferences for this project */
	private IEclipsePreferences preferences;

	/** The project this tracker is associated with */
	private IProject currentProject;

	/** In-memory MRU list (most recent first) */
	private LinkedList<String> mruList;

	/** Flag indicating if this tracker has been disposed */
	private boolean disposed = false;

	/**
	 * Create an MRU tracker for a specific project.
	 *
	 * @param project the project to track MRU for, or null
	 */
	public MostRecentlyUsedTracker(final IProject project) {
		this.currentProject = project;

		if (project != null && project.exists()) {
			final ProjectScope projectScope = new ProjectScope(project);
			this.preferences = projectScope.getNode(PREF_NODE);
			loadFromPreferences();

		} else {
			this.preferences = null;
			this.mruList = new LinkedList<>();
		}
	}

	/**
	 * Record that a FB type was used (created/inserted). Moves the type to the
	 * front of the MRU list.
	 *
	 * @param fbTypeName the full type name (e.g., "E_CYCLE", "E_SWITCH")
	 */
	public synchronized void recordUsage(final String fbTypeName) {
		if (disposed || fbTypeName == null || fbTypeName.isEmpty()) {
			return;
		}

		if (currentProject == null || !currentProject.exists()) {
			return;
		}

		// Remove if already exists (to move to front)
		mruList.remove(fbTypeName);

		mruList.addFirst(fbTypeName);

		while (mruList.size() > MAX_MRU_ITEMS) {
			mruList.removeLast();
		}

		saveToPreferences();
	}

	/**
	 * Get the MRU list in order (most recent first).
	 *
	 * @return unmodifiable list of type names, or empty list if disposed
	 */
	public synchronized List<String> getMRUList() {
		if (disposed || currentProject == null || !currentProject.exists()) {
			return new ArrayList<>();
		}
		return new ArrayList<>(mruList);
	}

	/**
	 * Clear the MRU history for this project.
	 */
	public synchronized void clear() {
		if (disposed) {
			return;
		}
		mruList.clear();
		saveToPreferences();
	}

	/**
	 * Dispose of this MRUTracker and release resources. Should be called when the
	 * editor is closed or project is deleted.
	 */
	public synchronized void dispose() {
		if (disposed) {
			return;
		}

		disposed = true;
		preferences = null;
		currentProject = null;
		mruList.clear();
	}

	/**
	 * Load MRU list from Eclipse preferences.
	 */
	private void loadFromPreferences() {
		mruList = new LinkedList<>();

		if (preferences == null || disposed) {
			return;
		}

		try {
			final String stored = preferences.get(PREF_KEY, "");

			if (stored != null && !stored.isEmpty()) {
				final String[] items = stored.split(DELIMITER);
				for (final String item : items) {
					if (!item.isEmpty()) {
						mruList.add(item);
					}
				}
			}
		} catch (final IllegalStateException e) {
			// Preference node has been removed (project deleted) - silent handling
		} catch (final Exception e) {
			System.err.println("[MRU] Failed to load preferences: " + e.getMessage());
		}
	}

	/**
	 * Save MRU list to Eclipse preferences.
	 */
	private void saveToPreferences() {
		if (preferences == null || disposed) {
			return;
		}

		if (currentProject == null || !currentProject.exists()) {
			return;
		}

		try {
			final StringBuilder sb = new StringBuilder();
			for (int i = 0; i < mruList.size(); i++) {
				if (i > 0) {
					sb.append(DELIMITER);
				}
				sb.append(mruList.get(i));
			}

			preferences.put(PREF_KEY, sb.toString());
			preferences.flush();

		} catch (final IllegalStateException e) {
			disposed = true;
		} catch (final BackingStoreException e) {
			System.err.println("[MRU] Failed to save preferences: " + e.getMessage());
		} catch (final Exception e) {
			System.err.println("Unexpected error saving MRU: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Get the project this tracker is associated with.
	 *
	 * @return the project, or null if none
	 */
	public IProject getProject() {
		return currentProject;
	}

	/**
	 * Check if this tracker has been disposed.
	 *
	 * @return true if disposed, false otherwise
	 */
	public boolean isDisposed() {
		return disposed;
	}
}