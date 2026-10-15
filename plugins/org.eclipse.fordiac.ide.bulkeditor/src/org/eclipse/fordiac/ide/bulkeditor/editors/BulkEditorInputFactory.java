/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.bulkeditor.editors;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.util.URI;
import org.eclipse.fordiac.ide.bulkeditor.query.QueryViewer.QuerySnapshot;
import org.eclipse.ui.IElementFactory;
import org.eclipse.ui.IMemento;

public class BulkEditorInputFactory implements IElementFactory {

	private static final String ID_FACTORY = "org.eclipse.fordiac.ide.bulkeditor.BulkEditorInputFactory"; //$NON-NLS-1$

	private static final String TAG_BULKEDITOR_PROJECT = "BULKEDITOR_PROJECT"; //$NON-NLS-1$
	private static final String TAG_BULKEDITOR_QUERY = "BULKEDITOR_QUERY"; //$NON-NLS-1$
	private static final String TAG_BULKEDITOR_QUERY_FILE = "BULKEDITOR_QUERY_FILE"; //$NON-NLS-1$
	private static final String TAG_BULKEDITOR_QUERY_DIRTY = "BULKEDITOR_QUERY_DIRTY"; //$NON-NLS-1$

	@Override
	public IAdaptable createElement(final IMemento memento) {
		final var project = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(memento.getString(TAG_BULKEDITOR_PROJECT));

		if (project != null) {
			final IMemento queryMemento = memento.getChild(TAG_BULKEDITOR_QUERY);
			if (queryMemento != null) {
				return new BulkEditorInput(project, restoreQuery(queryMemento));
			}
			return new BulkEditorInput(project);
		}
		return null;
	}

	private static QuerySnapshot restoreQuery(final IMemento queryMemento) {
		final String queryFile = queryMemento.getString(TAG_BULKEDITOR_QUERY_FILE);
		return new QuerySnapshot(queryMemento.getTextData(), queryFile != null ? URI.createURI(queryFile) : null,
				Boolean.TRUE.equals(queryMemento.getBoolean(TAG_BULKEDITOR_QUERY_DIRTY)));
	}

	public static void saveState(final IMemento memento, final BulkEditorInput input) {
		memento.putString(TAG_BULKEDITOR_PROJECT, input.getProject().getName());
		final QuerySnapshot query = input.snapshotQuery();
		if (query != null) {
			final IMemento queryMemento = memento.createChild(TAG_BULKEDITOR_QUERY);
			queryMemento.putTextData(query.xmi());
			if (query.queryFile() != null) {
				queryMemento.putString(TAG_BULKEDITOR_QUERY_FILE, query.queryFile().toString());
			}
			queryMemento.putBoolean(TAG_BULKEDITOR_QUERY_DIRTY, query.dirty());
		}
	}

	public static String getFactoryId() {
		return ID_FACTORY;
	}
}
