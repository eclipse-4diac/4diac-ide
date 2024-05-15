/*******************************************************************************
 * Copyright (c) 2024 Fabio Gandolfi
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.CreateServiceSequenceCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts.SequenceRootEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

public class LoadServiceSequencesHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelection(event);
		if (!selection.isEmpty() && selection.getFirstElement() instanceof final SequenceRootEditPart serviceSeqEp) {

			final FileDialog fileDialog = new FileDialog(
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
			fileDialog.setText("Open File"); //$NON-NLS-1$

			fileDialog.setFilterExtensions(new String[] { "*.opsem" }); //$NON-NLS-1$

			final String selectedFilePath = fileDialog.open();
			if (selectedFilePath != null) {
				final var uri = URI.createFileURI(selectedFilePath);
				final Resource res = new ResourceSetImpl().getResource(uri, true);

				final CompoundCommand cmds = new CompoundCommand();

				res.getContents().forEach(con -> {
					if (con instanceof final ServiceSequence seqs) {
						cmds.add(new CreateServiceSequenceCommand(serviceSeqEp.getFBType().getService(), seqs));
					}
				});

				if (cmds.canExecute()) {
					cmds.execute();
				}
			}
		}
		return null;
	}
}
