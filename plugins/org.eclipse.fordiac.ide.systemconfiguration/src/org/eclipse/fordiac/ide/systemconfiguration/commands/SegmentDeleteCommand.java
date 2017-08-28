/*******************************************************************************
 * Copyright (c) 2008, 2009, 2011, 2016, 2017 Profactor GbmH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger 
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration.commands;

import org.eclipse.fordiac.ide.model.libraryElement.Link;
import org.eclipse.fordiac.ide.model.libraryElement.Segment;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.eclipse.fordiac.ide.ui.controls.Abstract4DIACUIPlugin;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.ui.IEditorPart;

public class SegmentDeleteCommand extends Command {
	private IEditorPart editor;
	private CompoundCommand deleteLinks;
	private Segment segment;
	private SystemConfiguration segmentParent;

	@Override
	public boolean canUndo() {
		return editor.equals(Abstract4DIACUIPlugin.getCurrentActiveEditor());
	}

	@Override
	public void execute() {
		editor = Abstract4DIACUIPlugin.getCurrentActiveEditor();
		removeLinks();
		segmentParent = (SystemConfiguration) segment.eContainer();
		segmentParent.getSegments().remove(segment);
	}

	private void removeLinks() {
		deleteLinks = new CompoundCommand();
		for (Link link : segment.getOutConnections()) {
			DeleteLinkCommand cmd = new DeleteLinkCommand(link);
			deleteLinks.add(cmd);
		}
		deleteLinks.execute();
	}

	@Override
	public void undo() {
		segmentParent.getSegments().add(segment);
		deleteLinks.undo();
	}

	@Override
	public void redo() {
		segmentParent.getSegments().remove(segment);
		deleteLinks.redo();
	}

	public Segment getSegment() {
		return segment;
	}

	public void setSegment(final Segment segment) {
		this.segment = segment;
	}
}
