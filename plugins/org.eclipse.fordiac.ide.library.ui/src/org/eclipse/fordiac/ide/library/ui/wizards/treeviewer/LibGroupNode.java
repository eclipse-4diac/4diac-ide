/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.library.ui.wizards.treeviewer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.eclipse.fordiac.ide.library.LibraryRecord;

public final class LibGroupNode {

	private final List<LibraryRecord> records = new ArrayList<>();

	public LibGroupNode(final List<LibraryRecord> recs) {
		if (recs != null) {
			recs.stream().sorted(Comparator.comparing(LibraryRecord::version).reversed()).forEach(records::add);
		}
	}

	public List<LibraryRecord> getLibraryRecords() {
		return records;
	}

	public String getLabelText() {
		return records.getFirst().name();
	}

	public String getSymbolicName() {
		return records.getFirst().symbolicName();
	}
}