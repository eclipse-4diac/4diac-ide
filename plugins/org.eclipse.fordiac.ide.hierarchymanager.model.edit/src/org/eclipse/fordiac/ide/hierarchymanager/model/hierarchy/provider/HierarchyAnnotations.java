/*********************************************************************************
 *  Copyright (c) 2023 Primetals Technologies Austria GmbH
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License 2.0 which is available at
 *  http://www.eclipse.org/legal/epl-2.0.
 *
 *  SPDX-License-Identifier: EPL-2.0
 *
 *  Contributors:
 *    Alois Zoitl - initial API and implementation and/or initial documentation
 *********************************************************************************/
package org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.provider;

import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.Leaf;

public final class HierarchyAnnotations {

	static String getLeafText(final Leaf leaf, final LeafItemProvider leafItemProvider) {
		final String label = leaf.getRef();
		if (label != null && !label.isBlank()) {
			final String[] split = label.split("."); //$NON-NLS-1$
			return (split.length > 0) ? split[split.length - 1] : // we want the last entry as label
				label;
		}
		// if the ref is not set use the leaf type name as label
		return leafItemProvider.getString("_UI_Leaf_type"); //$NON-NLS-1$

	}

	private HierarchyAnnotations() {
		throw new UnsupportedOperationException("Utility class HierarchyAnnotations should not be instantiated"); //$NON-NLS-1$
	}
}
