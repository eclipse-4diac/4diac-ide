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
package org.eclipse.fordiac.ide.application.editors;

import java.util.Objects;
import java.util.stream.Stream;

import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

public class FBNetworkEditDomain extends DefaultEditDomain {

	public FBNetworkEditDomain(final IEditorPart editorPart) {
		super(editorPart);
	}

	@Override
	public void mouseUp(final MouseEvent me, final EditPartViewer viewer) {
		final EditPartViewer actualViewer = findViewer(new Point(me.x, me.y), viewer);
		final MouseEvent convertedEvent = convertMouseEventCoordinates(me, viewer, actualViewer);
		super.mouseUp(convertedEvent, actualViewer);
	}

	@Override
	public void mouseMove(final MouseEvent me, final EditPartViewer viewer) {
		final EditPartViewer actualViewer = findViewer(new Point(me.x, me.y), viewer);
		final MouseEvent convertedEvent = convertMouseEventCoordinates(me, viewer, actualViewer);
		super.mouseMove(convertedEvent, actualViewer);
	}

	@Override
	public void mouseDrag(final MouseEvent me, final EditPartViewer viewer) {
		final EditPartViewer actualViewer = findViewer(new Point(me.x, me.y), viewer);
		final MouseEvent convertedEvent = convertMouseEventCoordinates(me, viewer, actualViewer);
		super.mouseDrag(convertedEvent, actualViewer);
	}

	private static EditPartViewer findViewer(final Point point, final EditPartViewer viewer) {
		if (viewer.getControl().getBounds().contains(point)) {
			return viewer;
		}
		final Point absolute = viewer.getControl().toDisplay(point);
		return Stream.of(PlatformUI.getWorkbench().getWorkbenchWindows())
				.flatMap(window -> Stream.of(window.getPages())).flatMap(page -> Stream.of(page.getEditorReferences()))
				.map(ref -> ref.getEditor(false)).filter(Objects::nonNull)
				.<EditPartViewer>map(editor -> editor.getAdapter(GraphicalViewer.class)).filter(Objects::nonNull)
				.filter(candidate -> containsAbsolutePoint(candidate, absolute)).findAny().orElse(viewer);
	}

	private static boolean containsAbsolutePoint(final EditPartViewer viewer, final Point point) {
		final Control control = viewer.getControl();
		if (control.getParent() != null) {
			return control.getBounds().contains(control.getParent().toControl(point));
		}
		return control.getBounds().contains(point);
	}

	private static MouseEvent convertMouseEventCoordinates(final MouseEvent event, final EditPartViewer from,
			final EditPartViewer to) {
		if (from == to) {
			return event;
		}
		final Point converted = to.getControl().toControl(from.getControl().toDisplay(event.x, event.y));
		final Event swtEvent = new Event();
		swtEvent.display = event.display;
		swtEvent.widget = event.widget;
		swtEvent.time = event.time;
		swtEvent.data = event.data;
		swtEvent.x = converted.x;
		swtEvent.y = converted.y;
		swtEvent.button = event.button;
		swtEvent.stateMask = event.stateMask;
		swtEvent.count = event.count;
		return new MouseEvent(swtEvent);
	}
}
