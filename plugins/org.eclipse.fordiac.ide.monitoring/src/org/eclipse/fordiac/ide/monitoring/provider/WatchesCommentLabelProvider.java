/* Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi - initial API and implementation and/or initial documentation
 *
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring.provider;

import org.eclipse.fordiac.ide.monitoring.views.WatchValueTreeNode;
import org.eclipse.jface.viewers.ColumnLabelProvider;

public class WatchesCommentLabelProvider extends ColumnLabelProvider {
	@Override
	public String getText(final Object element) {
		if (element instanceof final WatchValueTreeNode tn) {
			return tn.getMonitoringBaseElement().getPort().getInterfaceElement().getComment();
		}
		return ""; //$NON-NLS-1$
	}
}
