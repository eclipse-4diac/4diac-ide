/*******************************************************************************
 * Copyright (c) 2026 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.views.graph;

import org.eclipse.core.runtime.Adapters;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.IPage;
import org.eclipse.ui.part.MessagePage;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.part.PageBookView;

public class EventSequenceView extends PageBookView {

	@Override
	protected IPage createDefaultPage(final PageBook book) {
		final MessagePage page = new MessagePage();
		initPage(page);
		page.createControl(book);
		page.setMessage(Messages.EventSequenceView_DefaultMessage);
		return page;
	}

	@Override
	protected PageRec doCreatePage(final IWorkbenchPart part) {
		final FBNetwork network = getNetwork(part);
		if (network == null) {
			return null;
		}

		final EventSequencePage page = new EventSequencePage();
		initPage(page);
		page.createControl(getPageBook());
		page.setNetwork(network);
		return new PageRec(part, page);
	}

	@Override
	protected void doDestroyPage(final IWorkbenchPart part, final PageRec pageRecord) {
		pageRecord.page.dispose();
		pageRecord.dispose();
	}

	@Override
	protected IWorkbenchPart getBootstrapPart() {
		final IEditorPart activeEditor = getSite().getPage().getActiveEditor();
		if (isImportant(activeEditor) && getNetwork(activeEditor) != null) {
			return activeEditor;
		}
		return null;
	}

	@Override
	protected boolean isImportant(final IWorkbenchPart part) {
		return part instanceof IEditorPart;
	}

	@Override
	public void partActivated(final IWorkbenchPart part) {
		// update page network in case the network open inside an editor changes,
		// such as for breadcrumb or multi-page editors
		final PageRec pageRec = getPageRec(part);
		if (pageRec != null && pageRec.page instanceof final EventSequencePage page) {
			final FBNetwork network = getNetwork(part);
			if (network != null) {
				page.setNetwork(network);
			} else {
				// use partClosed to remove stale page if the editor no longer shows a network,
				// since PageBookView provides no direct means of removing a page
				partClosed(part);
			}
		}
		super.partActivated(part);
	}

	private static FBNetwork getNetwork(final IWorkbenchPart part) {
		return Adapters.adapt(part, FBNetwork.class);
	}
}
