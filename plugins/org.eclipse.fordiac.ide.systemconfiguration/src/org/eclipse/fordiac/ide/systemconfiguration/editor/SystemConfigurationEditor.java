/*******************************************************************************
 * Copyright (c) 2008, 2012 - 2018 Profactor GbmH, TU Wien ACIN, fortiss GmbH,
 * 								Johannes Kepler University
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration.editor;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.gef.DiagramEditorWithFlyoutPalette;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.eclipse.fordiac.ide.systemconfiguration.editparts.SystemConfEditPartFactory;
import org.eclipse.fordiac.ide.systemmanagement.ISystemEditor;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.ui.palette.FlyoutPaletteComposite.FlyoutPreferences;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.jface.util.TransferDropTargetListener;
import org.eclipse.ui.IEditorInput;

public class SystemConfigurationEditor extends DiagramEditorWithFlyoutPalette implements ISystemEditor {
	private SystemConfiguration sysConf;

	@Override
	protected EditPartFactory getEditPartFactory() {
		return new SystemConfEditPartFactory(this);
	}

	@Override
	protected ContextMenuProvider getContextMenuProvider(final ScrollingGraphicalViewer viewer,
			final ZoomManager zoomManager) {
		return new SystemConfigurationContextMenueProvider(viewer, zoomManager, getActionRegistry());
	}

	@Override
	protected TransferDropTargetListener createTransferDropTargetListener() {
		return new SysConfTemplateTransferDropTargetListener(getViewer(), getSystem());
	}

	@Override
	public SystemConfiguration getModel() {
		return sysConf;
	}

	@Override
	public void setInput(final IEditorInput input) {
		final SystemConfigurationEditorInput sysConfInput = checkEditorInput(input);
		sysConf = sysConfInput.getContent();
		super.setInput(input);
	}

	private SystemConfigurationEditorInput checkEditorInput(final IEditorInput input) {
		if (!(input instanceof final SystemConfigurationEditorInput sysConfEI)) {
			throw new IllegalArgumentException(
					"System configuration editors only accept SystemConfigurationEditorInput as valid inputs!"); //$NON-NLS-1$
		}
		final SystemConfigurationEditorInput currentEditorInput = (SystemConfigurationEditorInput) getEditorInput();
		if (currentEditorInput != null && currentEditorInput.getContent() != sysConfEI.getContent()) {
			throw new IllegalArgumentException(
					"Editor input with new content given to system configuration editor. This is currently not supported!"); //$NON-NLS-1$
		}
		return sysConfEI;
	}

	@Override
	public AutomationSystem getSystem() {
		return (AutomationSystem) sysConf.eContainer();
	}

	@Override
	public void doSave(final IProgressMonitor monitor) {
		// with the breadcrumb based automation system editor this editor should not
		// support a save method
	}

	@Override
	protected FlyoutPreferences getPalettePreferences() {
		return SystemConfPaletteFactory.PALETTE_PREFERENCES;
	}

	@Override
	protected PaletteRoot getPaletteRoot() {

		if (getModel() != null && getSystem() != null) {
			return SystemConfPaletteFactory.createPalette(getSystem());
		}
		return new PaletteRoot();
	}

	@Override
	public void doSaveAs() {
		// empty
	}

	@Override
	public <T> T getAdapter(final Class<T> type) {
		if (SystemConfiguration.class == type) {
			return type.cast(getModel());
		}
		return super.getAdapter(type);
	}

}
