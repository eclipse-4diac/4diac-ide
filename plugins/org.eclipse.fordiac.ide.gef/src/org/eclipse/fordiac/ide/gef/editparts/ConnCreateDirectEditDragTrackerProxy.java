/*******************************************************************************
 * Copyright (c) 2016, 2018 fortiss GmbH
 *				 2019 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *   			 - handle scrolling during connection creation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.editparts;

import java.util.Map;

import org.eclipse.fordiac.ide.gef.tools.FordiacConnectionDragCreationTool;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.tools.ConnectionDragCreationTool;
import org.eclipse.gef.tools.SelectEditPartTracker;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.widgets.Event;

public class ConnCreateDirectEditDragTrackerProxy implements DragTracker {

	private ConnectionDragCreationTool connectionTool;
	private SelectEditPartTracker editPartTracker;

	public ConnCreateDirectEditDragTrackerProxy(EditPart editPart) {
		this.connectionTool = new FordiacConnectionDragCreationTool();
		this.editPartTracker = new SelectEditPartTracker(editPart);
	}
	
	public ConnectionDragCreationTool getConnectionTool() {
		return connectionTool;
	}

	@Override
	public void activate() {
		connectionTool.activate();
		editPartTracker.activate();
	}

	@Override
	public void deactivate() {
		connectionTool.deactivate();
		editPartTracker.deactivate();
	}

	@Override
	public void focusGained(FocusEvent event, EditPartViewer viewer) {
		connectionTool.focusGained(event, viewer);
		editPartTracker.focusGained(event, viewer);
	}

	@Override
	public void focusLost(FocusEvent event, EditPartViewer viewer) {
		connectionTool.focusLost(event, viewer);
		editPartTracker.focusLost(event, viewer);
	}

	@Override
	public void keyDown(KeyEvent keyEvent, EditPartViewer viewer) {
		connectionTool.keyDown(keyEvent, viewer);
		editPartTracker.keyDown(keyEvent, viewer);
	}

	@Override
	public void keyTraversed(TraverseEvent event, EditPartViewer viewer) {
		connectionTool.keyTraversed(event, viewer);
		editPartTracker.keyTraversed(event, viewer);
	}

	@Override
	public void keyUp(KeyEvent keyEvent, EditPartViewer viewer) {
		connectionTool.keyUp(keyEvent, viewer);
		editPartTracker.keyUp(keyEvent, viewer);
	}

	@Override
	public void mouseDoubleClick(MouseEvent mouseEvent, EditPartViewer viewer) {
		connectionTool.mouseDoubleClick(mouseEvent, viewer);
		editPartTracker.mouseDoubleClick(mouseEvent, viewer);
	}

	@Override
	public void mouseDown(MouseEvent mouseEvent, EditPartViewer viewer) {
		connectionTool.mouseDown(mouseEvent, viewer);
		editPartTracker.mouseDown(mouseEvent, viewer);
	}

	@Override
	public void mouseDrag(MouseEvent mouseEvent, EditPartViewer viewer) {
		connectionTool.mouseDrag(mouseEvent, viewer);
		editPartTracker.mouseDrag(mouseEvent, viewer);
	}

	@Override
	public void mouseHover(MouseEvent mouseEvent, EditPartViewer viewer) {
		connectionTool.mouseHover(mouseEvent, viewer);
		editPartTracker.mouseHover(mouseEvent, viewer);
	}

	@Override
	public void mouseMove(MouseEvent mouseEvent, EditPartViewer viewer) {
		connectionTool.mouseMove(mouseEvent, viewer);
		editPartTracker.mouseMove(mouseEvent, viewer);
	}

	@Override
	public void mouseUp(MouseEvent mouseEvent, EditPartViewer viewer) {
		connectionTool.mouseUp(mouseEvent, viewer);
		editPartTracker.mouseUp(mouseEvent, viewer);
	}

	@Override
	public void mouseWheelScrolled(Event event, EditPartViewer viewer) {
		connectionTool.mouseWheelScrolled(event, viewer);
		editPartTracker.mouseWheelScrolled(event, viewer);
	}

	@Override
	public void nativeDragFinished(DragSourceEvent event, EditPartViewer viewer) {
		connectionTool.nativeDragFinished(event, viewer);
		editPartTracker.nativeDragFinished(event, viewer);
	}

	@Override
	public void nativeDragStarted(DragSourceEvent event, EditPartViewer viewer) {
		connectionTool.nativeDragStarted(event, viewer);
		editPartTracker.nativeDragStarted(event, viewer);
	}

	@Override
	public void setEditDomain(EditDomain domain) {
		connectionTool.setEditDomain(domain);
		editPartTracker.setEditDomain(domain);
	}

	@Override
	public void setViewer(EditPartViewer viewer) {
		connectionTool.setViewer(viewer);
		editPartTracker.setViewer(viewer);
	}

	@Override
	public void viewerEntered(MouseEvent mouseEvent, EditPartViewer viewer) {
		connectionTool.viewerEntered(mouseEvent, viewer);
		editPartTracker.viewerEntered(mouseEvent, viewer);
	}

	@Override
	public void viewerExited(MouseEvent mouseEvent, EditPartViewer viewer) {
		connectionTool.viewerExited(mouseEvent, viewer);
		editPartTracker.viewerExited(mouseEvent, viewer);
	}

	@Override
	public void setProperties(@SuppressWarnings("rawtypes") Map properties) {
		connectionTool.setProperties(properties);
		editPartTracker.setProperties(properties);
	}

	@Override
	public void commitDrag() {
		connectionTool.commitDrag();
		editPartTracker.commitDrag();
	}

}
