/*******************************************************************************
 * Copyright (c) 2016 - 2018 fortiss GmbH, Johannes Kepler University
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.policies;

import org.eclipse.fordiac.ide.application.ApplicationPlugin;
import org.eclipse.fordiac.ide.application.commands.ReconnectAdapterConnectionCommand;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.model.Palette.AdapterTypePaletteEntry;
import org.eclipse.fordiac.ide.model.commands.create.AdapterConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

public class AdapterNodeEditPolicy extends InterfaceElementEditPolicy {

	@Override
	protected Command getConnectionCreateCommand(final CreateConnectionRequest request) {
		
		InterfaceEditPart host = (InterfaceEditPart) getHost();
		
		if( (host.isAdapter()) && (0 != (host.getMouseState() & SWT.CONTROL))){
			openAdapterType(host);
			return null;
		}
					
		AdapterConnectionCreateCommand cmd = new AdapterConnectionCreateCommand(getParentNetwork());
		cmd.setSource(((InterfaceEditPart) getHost()).getModel());
		request.setStartCommand(cmd);
		return new AdapterConnectionCreateCommand(getParentNetwork());

	}

	private static void openAdapterType(InterfaceEditPart host) {
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		
		AdapterTypePaletteEntry entry = ((AdapterDeclaration)host.getModel()).getPaletteEntry();
		if(null != entry){			
			IEditorDescriptor desc = PlatformUI.getWorkbench().
			        getEditorRegistry().getDefaultEditor(entry.getFile().getName());
			try {
				page.openEditor(new FileEditorInput(entry.getFile()), desc.getId());
			} catch (PartInitException e) {
				ApplicationPlugin.getDefault().logError(e.getMessage(), e);
			}
		}
	}
	
	@Override
	protected Command createReconnectCommand(ReconnectRequest request) {
		return new ReconnectAdapterConnectionCommand(request, getParentNetwork());
	}
	
}
