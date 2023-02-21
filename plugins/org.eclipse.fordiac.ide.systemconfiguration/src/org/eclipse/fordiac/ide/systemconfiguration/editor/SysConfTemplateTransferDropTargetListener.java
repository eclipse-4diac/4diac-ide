/*******************************************************************************
 * Copyright (c) 2016 Profactor GbmH, fortiss GmbH,
 * 				 2018 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration.editor;

import org.eclipse.core.resources.IProject;
import org.eclipse.fordiac.ide.gef.utilities.TemplateCreationFactory;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.typelibrary.DeviceTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.ResourceTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.SegmentTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.dnd.TemplateTransfer;
import org.eclipse.gef.dnd.TemplateTransferDropTargetListener;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.swt.dnd.DND;

public class SysConfTemplateTransferDropTargetListener extends TemplateTransferDropTargetListener {

	private final IProject targetProject;

	private static class SysConfTemplateCreationFactory implements CreationFactory {

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
	 * @param viewer the EditPartViewer
	 */
	public SysConfTemplateTransferDropTargetListener(final EditPartViewer viewer, final AutomationSystem system) {
		super(viewer);
		targetProject = (null != system) ? system.getTypeLibrary().getProject() : null;
	}

	@Override
	protected void handleDragOver() {
		super.handleDragOver();
		getCurrentEvent().feedback = DND.FEEDBACK_SCROLL | DND.FEEDBACK_EXPAND;
		if (TemplateTransfer.getInstance().getTemplate() == null) {
			getCurrentEvent().detail = DND.DROP_NONE;
			getCurrentEvent().operations = DND.DROP_NONE;

		} else {
			if (TemplateTransfer.getInstance().getTemplate() instanceof TypeEntry) {
				final TypeEntry entry = (TypeEntry) TemplateTransfer.getInstance().getTemplate();
				final IProject srcProject = entry.getFile().getProject();

				// If project is null it is an entry from the tool palette
				if (isSysConfEditorType(TemplateTransfer.getInstance().getTemplate()) && (null != targetProject)
						&& (targetProject.equals(srcProject))) {
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

		if (isSysConfEditorType(template)) {
			return new SysConfTemplateCreationFactory(template);
		} else if (template instanceof TemplateCreationFactory) {
			return super.getFactory(template);
		} else {
			FordiacLogHelper.logError("Type not in list: " + template.getClass().getName()); //$NON-NLS-1$
		}
		return null;
	}

	private static boolean isSysConfEditorType(final Object template) {
		return (template instanceof DeviceTypeEntry) || (template instanceof ResourceTypeEntry)
				|| (template instanceof SegmentTypeEntry);
	}

}
