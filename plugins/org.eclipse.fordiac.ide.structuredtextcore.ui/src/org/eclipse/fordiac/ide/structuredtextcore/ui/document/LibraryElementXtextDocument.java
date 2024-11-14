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

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.structuredtextcore.resource.LibraryElementXtextResource;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.xtext.resource.ISynchronizable;
import org.eclipse.xtext.resource.OutdatedStateManager;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.service.OperationCanceledError;
import org.eclipse.xtext.service.OperationCanceledManager;
import org.eclipse.xtext.ui.editor.model.DocumentTokenSource;
import org.eclipse.xtext.ui.editor.model.IXtextModelListener;
import org.eclipse.xtext.ui.editor.model.XtextDocument;
import org.eclipse.xtext.ui.editor.model.edit.ITextEditComposer;
import org.eclipse.xtext.ui.editor.model.edit.ReconcilingUnitOfWork;
import org.eclipse.xtext.util.concurrent.CancelableUnitOfWork;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;

import com.google.inject.Inject;

public class LibraryElementXtextDocument extends XtextDocument implements IAdaptable {

	@Inject
	private OutdatedStateManager outdatedStateManager;

	@Inject
	private OperationCanceledManager operationCanceledManager;

	private final Set<IXtextModelListener> modelListeners = ConcurrentHashMap.newKeySet();

	@Inject
	public LibraryElementXtextDocument(final DocumentTokenSource tokenSource, final ITextEditComposer composer) {
		super(tokenSource, composer);
	}

	@Override
	public <T> T getAdapter(final Class<T> adapterType) {
		if (adapterType.isAssignableFrom(LibraryElement.class)) {
			return adapterType.cast(getResourceLibraryElement());
		}
		return super.getAdapter(adapterType);
	}

	public LibraryElement getResourceLibraryElement() {
		return readOnly(resource -> resource instanceof final LibraryElementXtextResource libResource
				? libResource.getLibraryElement()
				: null);
	}

	@Override
	public void addModelListener(final IXtextModelListener listener) {
		super.addModelListener(listener);
		modelListeners.add(listener);
	}

	@Override
	public void removeModelListener(final IXtextModelListener listener) {
		super.removeModelListener(listener);
		modelListeners.remove(listener);
	}

	@Override
	protected XtextDocumentLocker createDocumentLocker() {
		return new LibraryElementXtextDocumentLocker();
	}

	@SuppressWarnings({ "boxing", "java:S1141", "java:S1143", "java:S1163", "java:S2177", "java:S3776" })
	protected class LibraryElementXtextDocumentLocker extends XtextDocumentLocker {

		private final AtomicInteger potentialUpdaterCount = new AtomicInteger(0);

		private volatile boolean hadUpdates;

		private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

		private final Lock writeLock = rwLock.writeLock();

		private final Lock readLock = rwLock.readLock();

		private final ThreadLocal<Integer> readLockCount = ThreadLocal.withInitial(() -> Integer.valueOf(0)); // NOSONAR

		@Override
		public <T> T process(final IUnitOfWork<T, XtextResource> transaction) {
			if (getReadHoldCount() != 1 || getWriteHoldCount() != 0) {
				throw new IllegalStateException("Exactly one read lock and no write locks expected! But was read: " //$NON-NLS-1$
						+ getReadHoldCount() + ", write:" + getWriteHoldCount()); //$NON-NLS-1$
			}
			releaseReadLock();
			// lock upgrade followed by downgrade as described in
			// java.util.concurrent.locks.ReentrantReadWriteLock
			//
			// caveat: other readers/writers could potentially kick in here
			acquireWriteLock();
			try {
				return this.modify(transaction);
			} finally {
				acquireReadLock();
				releaseWriteLock();
			}
		}

		@Override
		protected int getWriteHoldCount() {
			return rwLock.getWriteHoldCount();
		}

		@Override
		protected int getReadHoldCount() {
			return readLockCount.get();
		}

		private void acquireReadLock() {
			readLock.lock();
			readLockCount.set(readLockCount.get() + 1);
		}

		private void releaseReadLock() {
			readLock.unlock();
			readLockCount.set(readLockCount.get() - 1);
		}

		private void acquireWriteLock() {
			final Job validationJob = getValidationJob();
			if (validationJob != null) {
				validationJob.cancel();
			}
			setOutdated(true);
			writeLock.lock();
			// next reader will get a fresh monitor instance
			setOutdated(false);
		}

		private void releaseWriteLock() {
			writeLock.unlock();
		}

		@Override
		public <T> T modify(final IUnitOfWork<T, XtextResource> work) {
			final boolean isCancelable = work instanceof CancelableUnitOfWork;
			try {
				XtextResource state = getState();
				try {
					synchronized (LibraryElementXtextDocument.this.getResourceLock()) {
						acquireWriteLock();
						T exec = null;
						try {
							potentialUpdaterCount.incrementAndGet();
							state = getState();
							exec = outdatedStateManager.exec(work, state);
							return exec;
						} catch (final RuntimeException e) {
							throw e;
						} catch (final Exception e) {
							throw new WrappedException(e);
						} catch (final OperationCanceledError e) {
							throw e.getWrapped();
						} finally {
							try {
								// downgrade lock to read lock
								acquireReadLock();
								releaseWriteLock();
								ensureThatStateIsNotReturned(exec, work);
								if (potentialUpdaterCount.decrementAndGet() == 0
										&& !(work instanceof ReconcilingUnitOfWork)) {
									notifyModelListenersOnUiThread();
								}
							} catch (final RuntimeException e) {
								if (!operationCanceledManager.isOperationCanceledException(e)) {
									throw e;
								}
								if (isCancelable) {
									throw e;
								}
							} finally {
								releaseReadLock();
							}
						}
					}
				} catch (final RuntimeException e) {
					try {
						if (state != null) {
							synchronized (getResourceLock(state)) {
								acquireWriteLock();
								try {
									state.reparse(get());
								} finally {
									releaseWriteLock();
								}
							}
						}
					} catch (final IOException ioe) {
						// ignore
					}
					throw e;
				}
			} finally {
				if (!(work instanceof ReconcilingUnitOfWork)) {
					checkAndUpdateAnnotations();
				}
			}
		}

		@Override
		protected <T> T internalReadOnly(final IUnitOfWork<T, XtextResource> work, final boolean isCancelReaders) {
			final boolean isCancelable = work instanceof CancelableUnitOfWork;
			if (isCancelReaders) {
				if (Display.getCurrent() == null) {
					FordiacLogHelper.logError("Priority read only called from non UI-thread.", //$NON-NLS-1$
							new IllegalStateException());
				}
				cancelReaders(getState());
			}
			final XtextResource state = getState();
			synchronized (LibraryElementXtextDocument.this.getResourceLock()) {
				acquireReadLock();
				if (isCancelReaders) {
					setOutdated(false);
				}
				try {
					potentialUpdaterCount.incrementAndGet();
					// Don't updateContent on write lock request. Reentrant read doesn't matter as
					// updateContentBeforeRead() is cheap when the pending event queue is swept
					if (getReadHoldCount() == 1 && getWriteHoldCount() == 0) {
						hadUpdates |= updateContentBeforeRead();
					}
					final T exec = outdatedStateManager.exec(work, state);
					ensureThatStateIsNotReturned(exec, work);
					return exec;
				} catch (final RuntimeException e) {
					throw e;
				} catch (final Exception e) {
					throw new WrappedException(e);
				} catch (final OperationCanceledError e) {
					throw e.getWrapped();
				} finally {
					try {
						if (potentialUpdaterCount.decrementAndGet() == 0 && (hadUpdates || isCancelReaders)) {
							final boolean wasHadUpdates = hadUpdates;
							hadUpdates = false;
							if (getCancelIndicator().isCanceled() && isCancelable) {
								throw new OperationCanceledException();
							}
							if (wasHadUpdates) {
								notifyModelListenersOnUiThread();
							}
						}
					} catch (final RuntimeException e) {
						if (!operationCanceledManager.isOperationCanceledException(e)) {
							throw e;
						}
						if (isCancelable) {
							throw e;
						}
					} finally {
						releaseReadLock();
					}
				}
			}
		}

		private void notifyModelListenersOnUiThread() {
			if (modelListeners.isEmpty()) {
				return;
			}
			final Display display = PlatformUI.getWorkbench().getDisplay();
			if (Thread.currentThread() == display.getThread()) {
				// We are already running on the display thread. Run the listeners immediately.
				notifyModelListeners(getState());
			} else {
				display.asyncExec(
						() -> LibraryElementXtextDocument.this.tryReadOnly(((final XtextResource resource) -> {
							notifyModelListeners(resource);
							return null;
						})));
			}
		}

		private Object getResourceLock(final XtextResource r) {
			if (r != null) {
				return (r instanceof ISynchronizable<?>) ? ((ISynchronizable<?>) r).getLock() : r;
			}
			return this;
		}

		private void cancelReaders(final XtextResource resource) {
			final Job validationJob = getValidationJob();
			if (validationJob != null) {
				validationJob.cancel();
			}
			setOutdated(true);
		}
	}
}
