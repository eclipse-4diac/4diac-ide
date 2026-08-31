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

import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ContainerCheckedTreeViewer;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;

public class FilteredCheckedTree extends FilteredTree {
	private ToolBar expandCollapseBar;

	public FilteredCheckedTree(final Composite parent, final int treeStyle, final PatternFilter filter) {
		super(parent, treeStyle, configureFilter(filter), true, true);
	}

	private static PatternFilter configureFilter(final PatternFilter in) {
		final PatternFilter f = (in != null) ? in : new PatternFilter();
		f.setIncludeLeadingWildcard(true);
		return f;
	}

	@Override
	protected TreeViewer doCreateTreeViewer(final Composite parent, final int style) {
		return new ContainerCheckedTreeViewer(parent, style | SWT.CHECK);
	}

	public ContainerCheckedTreeViewer getCheckedViewer() {
		return (ContainerCheckedTreeViewer) getViewer();
	}

	@Override
	protected Control createTreeControl(final Composite parent, final int style) {
		if (expandCollapseBar == null || expandCollapseBar.isDisposed()) {
			expandCollapseBar = new ToolBar(parent, SWT.FLAT);
			expandCollapseBar.setLayoutData(new GridData(SWT.END, SWT.CENTER, true, false));

			final var shared = PlatformUI.getWorkbench().getSharedImages();

			final ToolItem expandAll = new ToolItem(expandCollapseBar, SWT.PUSH);
			expandAll.setImage(FordiacImage.ICON_EXPAND_ALL.getImage());
			expandAll.setToolTipText("Expand All"); //$NON-NLS-1$
			expandAll.addListener(SWT.Selection, _ -> getCheckedViewer().expandAll());

			final ToolItem collapseAll = new ToolItem(expandCollapseBar, SWT.PUSH);
			collapseAll.setImage(shared.getImage(ISharedImages.IMG_ELCL_COLLAPSEALL));
			collapseAll.setToolTipText("Collapse All"); //$NON-NLS-1$
			collapseAll.addListener(SWT.Selection, _ -> getCheckedViewer().collapseAll());
		}

		return super.createTreeControl(parent, style);
	}

}