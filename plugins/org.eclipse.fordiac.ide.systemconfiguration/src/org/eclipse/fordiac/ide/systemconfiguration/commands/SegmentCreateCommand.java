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
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.Palette.SegmentTypePaletteEntry;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Segment;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.eclipse.fordiac.ide.util.ColorHelper;
import org.eclipse.gef.commands.Command;

public class SegmentCreateCommand extends Command {
	private final SegmentTypePaletteEntry type;
	private final SystemConfiguration parent;
	private final Rectangle bounds;
	private Segment segment;

	public SegmentCreateCommand(final SegmentTypePaletteEntry type, final SystemConfiguration parent,
			final Rectangle bounds) {
		super();
		this.type = type;
		this.parent = parent;
		this.bounds = bounds;
	}

	@Override
	public boolean canExecute() {
		return null != parent;
	}

	@Override
	public void execute() {
		segment = LibraryElementFactory.eINSTANCE.createSegment();
		segment.setColor(ColorHelper.createRandomColor());
		segment.setPaletteEntry(type);
		segment.getVarDeclarations().addAll(EcoreUtil.copyAll(type.getSegmentType().getVarDeclaration()));
		segment.updatePosition(bounds.getTopLeft());
		segment.setWidth((-1 != bounds.width) ? bounds.width : 300);
		redo();
		segment.setName(NameRepository.createUniqueName(segment, type.getSegmentType().getName()));
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
