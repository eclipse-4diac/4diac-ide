/*******************************************************************************
 * Copyright (c) 2008, 2009, 2013, 2015 - 2017 Profactor GbmH, fortiss GmbH,
 * 				 2018 - 2019 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Gerd Kainz, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - removed editor check from canUndo
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration.commands;

import org.eclipse.fordiac.ide.gef.DiagramEditorWithFlyoutPalette;
import org.eclipse.fordiac.ide.model.commands.change.UnmapCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.ui.IEditorPart;

public class ResourceDeleteCommand extends Command {
	private Device device;
	private final Resource resource;
	private CompoundCommand commands;

	public ResourceDeleteCommand(final Resource resource) {
		this.resource = resource;
	}

	@Override
	public boolean canExecute() {
		return null != resource && !resource.isDeviceTypeResource();
	}

	@Override
	public void execute() {
		closeResourceEditor(); // we need to do this before any model changes so that the editor closes cleanly
		device = resource.getDevice();
		commands = new CompoundCommand();
		for (final FBNetworkElement element : resource.getFBNetwork().getNetworkElements()) {
			Command cmd = null;
			if (element.isMapped()) {
				cmd = new UnmapCommand(element);
			}
			if (null != cmd && cmd.canExecute()) {
				commands.add(cmd);
			}
		}
		commands.execute();
		if (device != null) {
			device.getResource().remove(resource);
		}
		SystemManager.INSTANCE.notifyListeners();
	}

	private void closeResourceEditor() {
		EditorUtils
		.closeEditorsFiltered((final IEditorPart editorPart) -> (editorPart instanceof DiagramEditorWithFlyoutPalette)
				&& (resource.getFBNetwork().equals(((DiagramEditorWithFlyoutPalette) editorPart).getModel())));
	}

	@Override
	public void undo() {
		if (device != null) {
			device.getResource().add(resource);
		}
		commands.undo();
		SystemManager.INSTANCE.notifyListeners();
	}

	@Override
	public void redo() {
		commands.redo();
		if (device != null) {
			device.getResource().remove(resource);
		}
		SystemManager.INSTANCE.notifyListeners();
	}
}
