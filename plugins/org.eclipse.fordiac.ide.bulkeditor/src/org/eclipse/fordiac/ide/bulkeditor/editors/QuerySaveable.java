/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
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

import java.io.IOException;
import java.text.MessageFormat;
import java.util.function.Supplier;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.fordiac.ide.bulkeditor.Messages;
import org.eclipse.fordiac.ide.bulkeditor.query.QueryViewer;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.Saveable;

/** The query of a bulk editor, saved to its query file. */
final class QuerySaveable extends Saveable {

	private final Supplier<QueryViewer> queryViewer;
	private final Supplier<String> editorName;

	QuerySaveable(final Supplier<QueryViewer> queryViewer, final Supplier<String> editorName) {
		this.queryViewer = queryViewer;
		this.editorName = editorName;
	}

	@Override
	public String getName() {
		final URI queryFile = getQueryFile();
		return queryFile != null ? queryFile.lastSegment()
				: MessageFormat.format(Messages.BulkEditor_QueryName, editorName.get());
	}

	@Override
	public String getToolTipText() {
		final URI queryFile = getQueryFile();
		return queryFile != null ? queryFile.toFileString() : getName();
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return FordiacImage.ICON_BULK_EDITOR.getImageDescriptor();
	}

	@Override
	public void doSave(final IProgressMonitor monitor) throws CoreException {
		final QueryViewer viewer = queryViewer.get();
		try {
			if (viewer != null && !viewer.saveQuery()) {
				monitor.setCanceled(true);
			}
		} catch (final IOException e) {
			throw new CoreException(Status.error(Messages.BulkEditor_QuerySaveFailed, e));
		}
	}

	@Override
	public boolean isDirty() {
		final QueryViewer viewer = queryViewer.get();
		return viewer != null && viewer.isDirty();
	}

	@Override
	public boolean equals(final Object object) {
		return this == object;
	}

	@Override
	public int hashCode() {
		return System.identityHashCode(this);
	}

	private URI getQueryFile() {
		final QueryViewer viewer = queryViewer.get();
		return viewer != null ? viewer.getQueryFile() : null;
	}
}
