/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.library;

import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.VersionRange;

class DependencyNode {
	private final String symbolicName;
	private VersionRange range;
	private final Map<String, VersionRange> causes;
	private boolean changed;

	public DependencyNode(final String symbolicName) {
		this.symbolicName = symbolicName;
		range = LibraryManager.ALL_RANGE;
		causes = new HashMap<>();
		changed = true;
	}

	public DependencyNode(final String symbolicName, final String causeName, final VersionRange causeRange) {
		this(symbolicName);
		causes.put(causeName, causeRange);
		recalculate();
	}

	public String getSymbolicName() {
		return symbolicName;
	}

	public VersionRange getRange() {
		return range;
	}

	public boolean isChanged() {
		return changed;
	}

	public Map<String, VersionRange> getCauses() {
		return causes;
	}

	public boolean putCause(final String causeName, final VersionRange causeRange) {
		causes.put(causeName, causeRange);
		return recalculate();
	}

	public boolean removeCause(final String causeName) {
		causes.remove(causeName);
		return recalculate();
	}

	public boolean isValid() {
		return !causes.isEmpty() && !range.isEmpty();
	}

	public boolean isRangeEmpty() {
		return range.isEmpty();
	}

	public boolean recalculate() {
		VersionRange calc = LibraryManager.ALL_RANGE;
		for (final VersionRange r : causes.values()) {
			calc = calc.intersection(r);
		}
		if (range.equals(calc)) {
			return false;
		}
		range = calc;
		changed = true;
		return true;
	}
}