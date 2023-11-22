/*******************************************************************************
 * Copyright (c) 2023 Fabio Gandolfi
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts.SequenceRootEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts.ServiceSequenceEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.exporter.ExportServiceSequenceToCppTest;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

public class ExportServiceSequencesAsTestHandler extends AbstractHandler {

	FBType fbType = null;

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelection(event);
		for (final Object sel : selection) {
			if (((EditPart) sel) instanceof final SequenceRootEditPart serviceSeqEP) {
				if (fbType == null) {
					fbType = serviceSeqEP.getFBType();
				}
				final ExportServiceSequenceToCppTest exportServiceSequenceToCppTest = new ExportServiceSequenceToCppTest();
				exportServiceSequenceToCppTest.exportServiceSequenceToCppTest(
						serviceSeqEP.getChildren().stream().filter(ServiceSequenceEditPart.class::isInstance)
								.map(ss -> (ServiceSequence) ss.getModel()).toList(),
						fbType);

			}
		}

		return Status.OK_STATUS;
	}

}
