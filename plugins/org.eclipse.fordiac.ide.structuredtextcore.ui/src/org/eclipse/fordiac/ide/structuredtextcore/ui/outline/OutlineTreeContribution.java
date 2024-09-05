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
package org.eclipse.fordiac.ide.structuredtextcore.ui.outline;

import org.eclipse.fordiac.ide.structuredtextcore.ui.Messages;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.xtext.ui.editor.outline.actions.IOutlineContribution;
import org.eclipse.xtext.ui.editor.outline.impl.OutlinePage;
import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreAccess;

public class OutlineTreeContribution implements IOutlineContribution {

	@Override
	public void initialize(final IPreferenceStoreAccess access) {
		// nothing to do
	}

	@Override
	public void register(final OutlinePage outlinePage) {
		final IToolBarManager toolBarManager = outlinePage.getSite().getActionBars().getToolBarManager();
		toolBarManager.add(new ExpandAllAction(outlinePage.getTreeViewer()));
		toolBarManager.add(new CollapseAllAction(outlinePage.getTreeViewer()));
	}

	@Override
	public void deregister(final OutlinePage outlinePage) {
		// nothing to do
	}

	public class ExpandAllAction extends Action {

		private final TreeViewer viewer;

		public ExpandAllAction(final TreeViewer viewer) {
			super(Messages.ExpandAllContribution_ExpandAllLabel, FordiacImage.ICON_EXPAND_ALL.getImageDescriptor());
			setToolTipText(Messages.ExpandAllContribution_ExpandAllToolTip);
			setDescription(Messages.ExpandAllContribution_ExpandAllDescription);
			this.viewer = viewer;
		}

		@Override
		public void run() {
			viewer.expandAll();
		}
	}

	public class CollapseAllAction extends Action {

		private final TreeViewer viewer;

		public CollapseAllAction(final TreeViewer viewer) {
			super(Messages.ExpandAllContribution_CollapseAllLabel,
					PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_ELCL_COLLAPSEALL));
			setToolTipText(Messages.ExpandAllContribution_CollapseAllToolTip);
			setDescription(Messages.ExpandAllContribution_CollapseAllDescription);
			this.viewer = viewer;
		}

		@Override
		public void run() {
			viewer.collapseAll();
		}
	}
}
