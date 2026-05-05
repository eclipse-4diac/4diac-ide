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
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.bulkeditor.search;

import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.fordiac.ide.bulkeditor.ui.FilterComposite;
import org.eclipse.swt.widgets.Text;

public record SearchParameters(int modeSelection, boolean advancedMode, Text searchText, FilterComposite searchFilter,
		FilterComposite fbSubappTypesFilter, boolean fbSubappTypesSelected, FilterComposite fbTypedSubappInstanceFilter,
		boolean fbTypedSubappInstanceSelected, FilterComposite untypedSubappFilter, boolean untypedSubappSelected,
		FilterComposite dataTypesFilter, boolean dataTypesSelected, FilterComposite attributeTypesFilter,
		boolean attributeTypesSelected, boolean ignoreLinkedLibraries, boolean workspaceScope, boolean projectScope,
		boolean subappHierarchyScope, List<URI> selectedSubApps) {
}
