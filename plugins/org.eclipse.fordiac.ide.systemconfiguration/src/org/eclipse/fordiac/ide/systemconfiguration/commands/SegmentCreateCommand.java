/*******************************************************************************
 * Copyright (c) 2008, 2009, 2011 - 2017 Profactor GbmH, TU Wien ACIN, fortiss GmbH
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

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.Palette.SegmentTypePaletteEntry;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Segment;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.eclipse.fordiac.ide.ui.controls.Abstract4DIACUIPlugin;
import org.eclipse.fordiac.ide.util.ColorHelper;
import org.eclipse.gef.commands.Command;
import org.eclipse.ui.IEditorPart;

public class SegmentCreateCommand extends Command {
	private IEditorPart editor;
	private final SegmentTypePaletteEntry type;
	protected final SystemConfiguration parent;
	private final Rectangle bounds;
	private Segment segment;

	public SegmentCreateCommand(final SegmentTypePaletteEntry type, final SystemConfiguration parent,
			final Rectangle bounds) {
		super();
		this.type = type;
		this.parent = parent;
		this.bounds = bounds;
		editor = Abstract4DIACUIPlugin.getCurrentActiveEditor();
	}

	@Override
	public boolean canUndo() {
		return editor.equals(Abstract4DIACUIPlugin.getCurrentActiveEditor());
	}

	@Override
	public boolean canExecute() {
		return null != parent;
	}

	@Override
	public void execute() {
		setLabel(getLabel() + "(" + (editor != null ? editor.getTitle() : "") + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		segment = LibraryElementFactory.eINSTANCE.createSegment();
		segment.setColor(ColorHelper.createRandomColor());
		segment.setPaletteEntry(type);
		segment.getVarDeclarations().addAll(EcoreUtil.copyAll(type.getSegmentType().getVarDeclaration()));
		segment.setX(bounds.x);
		segment.setY(bounds.y);
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
