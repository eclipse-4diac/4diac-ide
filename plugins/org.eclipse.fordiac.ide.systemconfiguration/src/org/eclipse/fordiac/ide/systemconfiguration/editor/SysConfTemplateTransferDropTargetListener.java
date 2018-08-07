/*******************************************************************************
 * Copyright (c) 2016 Profactor GbmH, fortiss GmbH,  
 * 				 2018 Johannes Kepler University
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl 
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration.editor;

import org.eclipse.fordiac.ide.gef.utilities.TemplateCreationFactory;
import org.eclipse.fordiac.ide.model.Palette.DeviceTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.ResourceTypeEntry;
import org.eclipse.fordiac.ide.model.Palette.SegmentTypePaletteEntry;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.dnd.TemplateTransfer;
import org.eclipse.gef.dnd.TemplateTransferDropTargetListener;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.swt.dnd.DND;

public class SysConfTemplateTransferDropTargetListener extends
		TemplateTransferDropTargetListener {

	AutomationSystem system;
	
	private class SysConfTemplateCreationFactory implements CreationFactory {

		/** The type template. */
		private final Object typeTemplate;

		public SysConfTemplateCreationFactory(final Object typeTemplate) {
			this.typeTemplate = typeTemplate;
		}

		@Override
		public Object getNewObject() {
			return null;
		}

		@Override
		public Object getObjectType() {
			return typeTemplate;
		}

	}


	/**
	 * Constructs a listener on the specified viewer.
	 * 
	 * @param viewer
	 *            the EditPartViewer
	 */
	public SysConfTemplateTransferDropTargetListener(
			final EditPartViewer viewer, AutomationSystem system) {
		super(viewer);
		this.system = system;
	}

	@Override
	protected void handleDragOver() {
		super.handleDragOver();
		getCurrentEvent().feedback = DND.FEEDBACK_SCROLL | DND.FEEDBACK_EXPAND;
		if (TemplateTransfer.getInstance().getTemplate() == null) {
			getCurrentEvent().detail = DND.DROP_NONE;
			getCurrentEvent().operations = DND.DROP_NONE;

		} else {
			if (TemplateTransfer.getInstance().getTemplate() instanceof PaletteEntry) {
				PaletteEntry entry = (PaletteEntry) TemplateTransfer.getInstance().getTemplate();
				AutomationSystem paletteSystem = entry.getGroup().getPallete().getAutomationSystem();

				// If project is null it is an entry from the tool palette
				if (isSysConfEditorType(TemplateTransfer.getInstance().getTemplate()) && (null != paletteSystem) && (paletteSystem.equals(system))) {
					getCurrentEvent().detail = DND.DROP_COPY;
				} else {
					getCurrentEvent().detail = DND.DROP_NONE;
					getCurrentEvent().operations = DND.DROP_NONE;
				}
			}
		}
	}


	@Override
	protected void handleDrop() {
		if (!isSysConfEditorType(getCurrentEvent().data) 
				&& !(getCurrentEvent().data instanceof TemplateCreationFactory)) {
			return;
		}
		//
		super.handleDrop();
		TemplateTransfer.getInstance().setTemplate(null);
	}
	
	@Override
	protected CreationFactory getFactory(final Object template) {
		getCurrentEvent().detail = DND.DROP_COPY;

		if (isSysConfEditorType(template)){
			return new SysConfTemplateCreationFactory(template);
		}else if(template instanceof TemplateCreationFactory){
			return super.getFactory(template);
		}else {
			System.out.println("Type not in list: " + template.getClass().getName()); //$NON-NLS-1$
		}
		return null;
	}
	
	private boolean isSysConfEditorType(Object template) {
		return (template instanceof DeviceTypePaletteEntry) 
				|| (template instanceof ResourceTypeEntry)
				|| (template instanceof SegmentTypePaletteEntry);
	}


}
