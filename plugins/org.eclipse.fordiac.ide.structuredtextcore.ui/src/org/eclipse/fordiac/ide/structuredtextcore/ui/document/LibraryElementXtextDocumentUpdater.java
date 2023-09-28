/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextcore.ui.document;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.structuredtextcore.resource.LibraryElementXtextResource;
import org.eclipse.xtext.Constants;
import org.eclipse.xtext.ui.editor.reconciler.XtextReconciler;

import com.google.inject.ImplementedBy;
import com.google.inject.Inject;
import com.google.inject.name.Named;

public class LibraryElementXtextDocumentUpdater extends Job {

	private final LibraryElementChangeAdapter changeAdapter;

	private LibraryElementXtextDocument document;
	private volatile boolean paused = false;

	/**
	 * The delay for running the update job.
	 *
	 * @implNote Ensure that this is not an exact multiple of the delay for the
	 *           {@link XtextReconciler} to avoid a resonance effect.
	 */
	private long delay = 750;

	@Inject
	public LibraryElementXtextDocumentUpdater(@Named(Constants.LANGUAGE_NAME) final String name,
			final LibraryElementChangeAdapterFilter filter) {
		super(name);
		this.changeAdapter = new LibraryElementChangeAdapter(this, filter);
		setPriority(Job.SHORT);
		setSystem(true);
	}

	@Override
	protected IStatus run(final IProgressMonitor monitor) {
		if (monitor.isCanceled() || paused) {
			return Status.CANCEL_STATUS;
		}
		if (document != null) {
			document.internalModify(state -> {
				doRun((LibraryElementXtextResource) state, monitor);
				return null;
			});
		}
		if (monitor.isCanceled()) {
			return Status.CANCEL_STATUS;
		}
		return Status.OK_STATUS;
	}

	@SuppressWarnings("static-method") // subclasses may override
	protected void doRun(final LibraryElementXtextResource resource, final IProgressMonitor monitor) {
		final LibraryElement libraryElement = resource.getLibraryElement();
		if (libraryElement != null) {
			final TypeEntry typeEntry = libraryElement.getTypeEntry();
			if (typeEntry != null) {
				resource.setLibraryElement(typeEntry.getTypeEditable());
			}
		}
	}

	public void install(final LibraryElementXtextDocument document) {
		if (this.document != null) {
			uninstall();
		}
		this.document = document;
		changeAdapter.install(document);
		handleLibraryElementChanged();
	}

	public void uninstall() {
		changeAdapter.uninstall(document);
		cancel();
		this.document = null;
	}

	public void pause() {
		paused = true;
	}

	public void resume() {
		paused = false;
		schedule(delay);
	}

	protected void handleLibraryElementChanged() {
		cancel();
		if (!paused) {
			schedule(delay);
		}
	}

	public LibraryElementXtextDocument getDocument() {
		return document;
	}

	public boolean isPaused() {
		return paused;
	}

	public long getDelay() {
		return delay;
	}

	public void setDelay(final long delay) {
		this.delay = delay;
	}

	protected static class LibraryElementChangeAdapter extends EContentAdapter {
		private final LibraryElementXtextDocumentUpdater reconciler;
		private final LibraryElementChangeAdapterFilter filter;

		protected LibraryElementChangeAdapter(final LibraryElementXtextDocumentUpdater reconciler,
				final LibraryElementChangeAdapterFilter filter) {
			this.reconciler = reconciler;
			this.filter = filter;
		}

		@Override
		public void notifyChanged(final Notification notification) {
			super.notifyChanged(notification);
			if (filter.shouldNotify(notification)) {
				reconciler.handleLibraryElementChanged();
			}
		}

		public void install(final LibraryElementXtextDocument document) {
			if (document != null) {
				document.readOnly(state -> {
					if (state instanceof final LibraryElementXtextResource libraryElementXtextResource) {
						final LibraryElement libraryElement = libraryElementXtextResource.getLibraryElement();
						if (libraryElement != null) {
							final var adapters = libraryElement.eAdapters();
							if (!adapters.contains(this)) {
								adapters.add(this);
							}
						}
					}
					return null;
				});

			}
		}

		public void uninstall(final LibraryElementXtextDocument document) {
			if (document != null) {
				document.readOnly(state -> {
					if (state instanceof final LibraryElementXtextResource libraryElementXtextResource) {
						final LibraryElement libraryElement = libraryElementXtextResource.getLibraryElement();
						if (libraryElement != null) {
							final var adapters = libraryElement.eAdapters();
							adapters.remove(this);
						}
					}
					return null;
				});
			}
		}

		@Override
		protected void addAdapter(final Notifier notifier) {
			if (filter.shouldAdapt(notifier)) {
				super.addAdapter(notifier);
			}
		}

		@Override
		protected boolean useRecursion() {
			return false;
		}
	}

	@ImplementedBy(DefaultLibraryElementChangeAdapterFilter.class)
	public interface LibraryElementChangeAdapterFilter {
		boolean shouldAdapt(Notifier notifier);

		boolean shouldNotify(Notification notification);
	}

	public static class DefaultLibraryElementChangeAdapterFilter implements LibraryElementChangeAdapterFilter {

		@Override
		public boolean shouldAdapt(final Notifier notifier) {
			return true;
		}

		@Override
		public boolean shouldNotify(final Notification notification) {
			return !notification.isTouch();
		}
	}
}
