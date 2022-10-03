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
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.widget;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ColumnViewerEditor;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationStrategy;
import org.eclipse.nebula.jface.gridviewer.GridColumnLayout;
import org.eclipse.nebula.jface.gridviewer.GridTableViewer;
import org.eclipse.nebula.jface.gridviewer.GridViewerEditor;
import org.eclipse.nebula.widgets.grid.GridColumn;
import org.eclipse.nebula.widgets.grid.GridHeaderRenderer;
import org.eclipse.nebula.widgets.grid.IRenderer;
import org.eclipse.nebula.widgets.grid.internal.DefaultColumnHeaderRenderer;
import org.eclipse.nebula.widgets.grid.internal.DefaultEmptyColumnHeaderRenderer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Composite;
public final class NebulaGridWidgetFactory {

	public static GridTableViewer createPropertyGridTableViewer(final Composite parent, final GridColumnLayout layout) {
		return createPropertyGridTableViewer(parent, layout, 0);
	}

	public static GridTableViewer createPropertyGridTableViewer(final Composite parent, final GridColumnLayout layout,
			final int style) {
		final Composite composite = new Composite(parent, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(composite);
		composite.setLayout(layout);

		final GridTableViewer gridTableViewer = new GridTableViewer(composite,
				SWT.FULL_SELECTION | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI | style);

		final ColumnViewerEditorActivationStrategy actSupport = new ColumnViewerEditorActivationStrategy(gridTableViewer) {
			@Override
			protected boolean isEditorActivationEvent(final ColumnViewerEditorActivationEvent event) {
				return event.eventType == ColumnViewerEditorActivationEvent.TRAVERSAL
						|| event.eventType == ColumnViewerEditorActivationEvent.MOUSE_DOUBLE_CLICK_SELECTION
						|| (event.eventType == ColumnViewerEditorActivationEvent.KEY_PRESSED && event.keyCode == SWT.CR)
						|| event.eventType == ColumnViewerEditorActivationEvent.PROGRAMMATIC;
			}
		};

		GridViewerEditor.create(gridTableViewer, actSupport,
				ColumnViewerEditor.TABBING_HORIZONTAL | ColumnViewerEditor.TABBING_MOVE_TO_ROW_NEIGHBOR
				| ColumnViewerEditor.TABBING_VERTICAL | ColumnViewerEditor.KEYBOARD_ACTIVATION);

		return gridTableViewer;
	}

	public static GridHeaderRenderer createSimpleColumnHeader() {
		return new DefaultColumnHeaderRenderer() {
			@Override
			public void paint(final GC gc, final Object value) {
				final GridColumn column = (GridColumn) value;
				final Color lineColor = getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND);

				// get coordinates
				final int x = getBounds().x;
				final int y = getBounds().y;
				final int width = getBounds().width;
				final int height = getBounds().height;

				// set foreground color and draw rectangle
				final Color oldForeground = gc.getForeground();
				gc.setForeground(lineColor);
				gc.drawRectangle(x - 1, y - 1, width, height);
				gc.setForeground(oldForeground);

				// draw text
				final String text = column.getText();
				gc.drawString(text, x + 4, y + 2);
			}
		};
	}

	public static IRenderer createSimpleEmptyColumnHeader() {
		return new DefaultEmptyColumnHeaderRenderer() {
			@Override
			public void paint(final GC gc, final Object value) {
				final Color oldForeground = gc.getForeground();
				gc.setForeground(getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
				gc.drawRectangle(getBounds().x - 1, getBounds().y - 1, getBounds().width + 1, getBounds().height);
				gc.setForeground(oldForeground);
			}
		};
	}

	private NebulaGridWidgetFactory() {
		throw new UnsupportedOperationException("Widget Factory should not be instantiated"); //$NON-NLS-1$
	}
}
