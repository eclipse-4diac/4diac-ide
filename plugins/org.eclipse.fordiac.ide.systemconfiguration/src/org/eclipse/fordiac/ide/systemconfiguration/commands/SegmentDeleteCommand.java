/*******************************************************************************
 * Copyright (c) 2008, 2023 Profactor GbmH, TU Wien ACIN, fortiss GmbH
 * 		            Johannes Keppler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - removed editor check from canUndo
 *   Bianca Wiesmayr - updated command to handle communication mapping
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration.commands;

import org.eclipse.fordiac.ide.model.commands.change.UnmapCommand;
import org.eclipse.fordiac.ide.model.libraryElement.CommunicationChannel;
import org.eclipse.fordiac.ide.model.libraryElement.CommunicationConfiguration;
import org.eclipse.fordiac.ide.model.libraryElement.CommunicationMappingTarget;
import org.eclipse.fordiac.ide.model.libraryElement.Link;
import org.eclipse.fordiac.ide.model.libraryElement.Segment;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

public class SegmentDeleteCommand extends Command {
	private CompoundCommand deleteLinks;
	private Segment segment;
	private CommunicationConfiguration config;
	private SystemConfiguration segmentParent;
	private CompoundCommand unmapCommands;

	@Override
	public void execute() {
		removeLinks();
		segmentParent = (SystemConfiguration) segment.eContainer();
		segmentParent.getSegments().remove(segment);
		unmapCommands = handleCommunicationMapping();
		unmapCommands.execute();
	}

	private CompoundCommand handleCommunicationMapping() {
		final CompoundCommand cmd = new CompoundCommand();
		config = segment.getCommunication();
		if (config != null) {
			for (final CommunicationMappingTarget tgt : config.getMappingTargets()) {
				for (final CommunicationChannel channel : tgt.getMappedElements()) {
					cmd.add(new UnmapCommand(channel));
				}
			}
		}
		return cmd;
	}

	private void removeLinks() {
		deleteLinks = new CompoundCommand();
		for (final Link link : segment.getOutConnections()) {
			final DeleteLinkCommand cmd = new DeleteLinkCommand(link);
			deleteLinks.add(cmd);
		}
		deleteLinks.execute();
	}

	@Override
	public void undo() {
		segmentParent.getSegments().add(segment);
		deleteLinks.undo();
		unmapCommands.undo();
	}

	@Override
	public void redo() {
		segmentParent.getSegments().remove(segment);
		deleteLinks.redo();
		unmapCommands.redo();
	}

	public Segment getSegment() {
		return segment;
	}

	public void setSegment(final Segment segment) {
		this.segment = segment;
	}
}
