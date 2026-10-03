/*******************************************************************************
 * Copyright (c) 2026 Wolfgang Schedl
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Wolfgang Schedl - initial API and implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.typelibrary;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.fordiac.ide.util.FordiacLogHelper;
import org.osgi.service.prefs.BackingStoreException;

/**
 * Counts how often each FB type is instantiated, per project, feeding the
 * usage-based part of the type search's Frequent section. Companion of
 * {@link MostRecentlyUsedTracker}. Counts persist in project-scoped preferences
 * as "fullName=count;..." pairs.
 */
public class TypeUsageTracker {

	private static final String PREF_NODE = "org.eclipse.fordiac.ide.gef"; //$NON-NLS-1$

	private static final String PREF_KEY = "type_usage_counts"; //$NON-NLS-1$

	private static final String ENTRY_DELIMITER = ";"; //$NON-NLS-1$
	private static final String COUNT_DELIMITER = "="; //$NON-NLS-1$

	private static final int MAX_STORED_ENTRIES = 50;

	/**
	 * A type only counts as "frequent" from the second use on.
	 */
	private static final int MIN_FREQUENT_COUNT = 2;

	private final IEclipsePreferences preferences;
	private final IProject project;
	private final Map<String, Integer> usageCounts = new HashMap<>();

	public TypeUsageTracker(final IProject project) {
		this.project = project;
		if (project != null && project.exists()) {
			preferences = new ProjectScope(project).getNode(PREF_NODE);
			loadFromPreferences();
		} else {
			preferences = null;
		}
	}

	public synchronized void recordUsage(final TypeEntry typeEntry) {
		if (typeEntry == null || project == null || !project.exists()) {
			return;
		}
		final String fullName = typeEntry.getFullTypeName();
		if (fullName == null || fullName.isEmpty()) {
			return;
		}
		usageCounts.merge(fullName, Integer.valueOf(1), Integer::sum);
		saveToPreferences();
	}

	public synchronized List<String> getMostUsed(final int limit) {
		return usageCounts.entrySet().stream().filter(entry -> entry.getValue().intValue() >= MIN_FREQUENT_COUNT)
				.sorted(Comparator.<Map.Entry<String, Integer>>comparingInt(entry -> -entry.getValue().intValue())
						.thenComparing(Map.Entry::getKey))
				.limit(limit).map(Map.Entry::getKey).toList();
	}

	public synchronized void clear() {
		usageCounts.clear();
		saveToPreferences();
	}

	private void loadFromPreferences() {
		if (preferences == null) {
			return;
		}
		try {
			final String stored = preferences.get(PREF_KEY, ""); //$NON-NLS-1$
			for (final String item : stored.split(ENTRY_DELIMITER)) {
				final int split = item.lastIndexOf(COUNT_DELIMITER);
				if (split > 0) {
					try {
						usageCounts.put(item.substring(0, split), Integer.valueOf(item.substring(split + 1)));
					} catch (final NumberFormatException e) {
						// skip the malformed entry, keep the rest
					}
				}
			}
		} catch (final IllegalStateException e) {
			// preference node has been removed (project is being deleted)
		} catch (final Exception e) {
			FordiacLogHelper.logWarning("Failed to load type usage preferences", e); //$NON-NLS-1$
		}
	}

	private void saveToPreferences() {
		if (preferences == null || project == null || !project.exists()) {
			return;
		}
		try {
			final String serialized = usageCounts.entrySet().stream()
					.sorted(Comparator.<Map.Entry<String, Integer>>comparingInt(entry -> -entry.getValue().intValue())
							.thenComparing(Map.Entry::getKey))
					.limit(MAX_STORED_ENTRIES).map(entry -> entry.getKey() + COUNT_DELIMITER + entry.getValue())
					.reduce((a, b) -> a + ENTRY_DELIMITER + b).orElse(""); //$NON-NLS-1$
			preferences.put(PREF_KEY, serialized);
			preferences.flush();
		} catch (final IllegalStateException e) {
			// preference node has been removed (project is being deleted)
		} catch (final BackingStoreException | RuntimeException e) {
			FordiacLogHelper.logWarning("Failed to save type usage preferences", e); //$NON-NLS-1$
		}
	}
}
