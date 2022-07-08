/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring.views;

import org.eclipse.fordiac.ide.monitoring.Messages;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class WatchesViewSelectionFilterAction extends Action {

	private boolean selectionActive = false;
	WatchesView watchesView;

	public WatchesViewSelectionFilterAction(final String text, final int style, final WatchesView watchesView) {
		super(text,style);
		this.watchesView = watchesView;
		setImageDescriptor(
				PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_ELCL_SYNCED));
		setToolTipText(Messages.MonitoringManagerUtils_SelectionFilteringActive);
	}


	@Override
	public void run() {
		selectionActive = !selectionActive;
		setChecked(selectionActive);
		watchesView.updateSelectionFilter(selectionActive);
	}

	public boolean getSelectionActive() {
		return selectionActive;
	}
}
