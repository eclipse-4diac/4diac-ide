/*******************************************************************************
 * Copyright (c) 2026 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.application.views.graph;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Tree;

public class FBNetworkLaneGraphTreeViewer extends TreeViewer {

	private int graphIndex;

	public FBNetworkLaneGraphTreeViewer(final Composite parent, final int style) {
		super(parent, style);
	}

	@Override
	protected void hookControl(final Control control) {
		super.hookControl(control);
		final Tree treeControl = (Tree) control;
		final FBNetworkLaneGraphPainter painter = new FBNetworkLaneGraphPainter(treeControl);

		treeControl.addListener(SWT.MeasureItem, event -> {
			if (event.index == graphIndex) {
				painter.measure(event);
			}
		});

		treeControl.addListener(SWT.EraseItem, event -> {
			if (event.index == graphIndex) {
				event.detail &= ~SWT.FOREGROUND;
			}
		});

		treeControl.addListener(SWT.PaintItem, event -> {
			if (event.index == graphIndex) {
				painter.paint(event);
			}
		});
	}

	public int getGraphIndex() {
		return graphIndex;
	}

	public void setGraphIndex(final int graphIndex) {
		this.graphIndex = graphIndex;
	}
}
