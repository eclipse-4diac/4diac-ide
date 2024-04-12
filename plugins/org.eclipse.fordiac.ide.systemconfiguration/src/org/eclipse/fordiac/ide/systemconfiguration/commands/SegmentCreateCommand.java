/*******************************************************************************
 * Copyright (c) 2008, 2009, 2011 - 2017 Profactor GbmH, TU Wien ACIN, fortiss GmbH
 * 				 2019 Johannes Keppler University Linz
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration.commands;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.CoordinateConverter;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.fordiac.ide.model.libraryElement.Segment;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.eclipse.fordiac.ide.model.systemconfiguration.CommunicationConfigurationDetails;
import org.eclipse.fordiac.ide.model.typelibrary.SegmentTypeEntry;
import org.eclipse.fordiac.ide.util.ColorHelper;
import org.eclipse.gef.commands.Command;

public class SegmentCreateCommand extends Command {
	private static final int DEFAULT_SEGMENT_WIDTH = 300;
	private final SegmentTypeEntry type;
	private final SystemConfiguration parent;
	private final Position pos;
	private final int width;
	private Segment segment;

	public SegmentCreateCommand(final SegmentTypeEntry type, final SystemConfiguration parent, final Rectangle bounds) {
		this.type = type;
		this.parent = parent;
		pos = CoordinateConverter.INSTANCE.createPosFromScreenCoordinates(bounds.x, bounds.y);
		width = (-1 != bounds.width) ? bounds.width : DEFAULT_SEGMENT_WIDTH;
	}

	@Override
	public boolean canExecute() {
		return null != parent;
	}

	@Override
	public void execute() {
		segment = LibraryElementFactory.eINSTANCE.createSegment();
		segment.setColor(ColorHelper.createRandomColor());
		segment.setTypeEntry(type);
		final CommunicationConfigurationDetails commConfig = CommunicationConfigurationDetails
				.getCommConfigUiFromExtensionPoint(type.getTypeName(),
						CommunicationConfigurationDetails.COMM_EXT_ATT_ID);
		if (commConfig != null) {
			segment.setCommunication(commConfig.createModel(segment.getVarDeclarations()));
		}
		segment.getVarDeclarations().addAll(EcoreUtil.copyAll(type.getType().getVarDeclaration()));
		segment.setPosition(pos);
		segment.setWidth(width);
		redo();
		segment.setName(NameRepository.createUniqueName(segment, type.getType().getName()));
	}

	@Override
	public void undo() {
		parent.getSegments().remove(segment);
	}

	@Override
	public void redo() {
		parent.getSegments().add(segment);
	}
}
