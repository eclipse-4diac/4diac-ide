/*******************************************************************************
 * Copyright (c) 2025, 2026 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.model.ui.editors;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.ui.Messages;
import org.eclipse.fordiac.ide.model.ui.annotation.GraphicalAnnotationModel;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

public abstract class AbstractLibraryElementProvider<T extends AbstractLibraryElementProvider<T>.LibraryElementInfo>
		implements LibraryElementProvider {

	private final Map<IEditorInput, T> infos = new ConcurrentHashMap<>();

	protected AbstractLibraryElementProvider() {
	}

	@Override
	public void connect(final IEditorInput input) throws CoreException {
		checkAccess();
		T info = getLibraryElementInfo(input);
		if (info == null) {
			info = Objects.requireNonNull(createLibraryElementInfo(input));
			infos.put(info.getEditorInput(), info);
		}
		info.connect();
	}

	@Override
	public void disconnect(final IEditorInput input) {
		checkAccess();
		final T info = getLibraryElementInfo(input);
		if (info != null && info.disconnect()) {
			infos.remove(info.getEditorInput(), info);
			info.dispose();
		}
	}

	@Override
	public LibraryElement getLibraryElement(final IEditorInput input) {
		// allow concurrent access
		final T info = getLibraryElementInfo(input);
		return info != null ? info.getLibraryElement() : null;
	}

	@Override
	public void resetLibraryElement(final IEditorInput input, final IProgressMonitor monitor) throws CoreException {
		checkAccess();
		final T info = getLibraryElementInfo(input);
		if (info != null) {
			executeOperation(wrapOperation(info, this::doResetLibraryElement), monitor);
		}
	}

	protected abstract void doResetLibraryElement(T info, IProgressMonitor monitor) throws CoreException;

	@Override
	public void saveLibraryElement(final IEditorInput input, final IProgressMonitor monitor) throws CoreException {
		checkAccess();
		final T info = getLibraryElementInfo(input);
		if (info != null) {
			executeOperation(wrapOperation(info, this::doSaveLibraryElement), monitor);
		}
	}

	protected abstract void doSaveLibraryElement(T info, IProgressMonitor monitor) throws CoreException;

	@Override
	public void synchronize(final IEditorInput input, final IProgressMonitor monitor) throws CoreException {
		checkAccess();
		final T info = getLibraryElementInfo(input);
		if (info != null) {
			executeOperation(wrapOperation(info, this::doSynchronize), monitor);
		}
	}

	protected abstract void doSynchronize(T info, final IProgressMonitor monitor) throws CoreException;

	@Override
	public boolean isSynchronized(final IEditorInput input) {
		checkAccess();
		final T info = getLibraryElementInfo(input);
		if (info != null) {
			return doIsSynchronized(info);
		}
		return true;
	}

	protected abstract boolean doIsSynchronized(T info);

	@Override
	public long getModificationStamp(final IEditorInput input) {
		checkAccess();
		final T info = getLibraryElementInfo(input);
		return info != null ? doGetModificationStamp(info) : 0;
	}

	protected abstract long doGetModificationStamp(T info);

	@Override
	public long getSynchronizationStamp(final IEditorInput input) {
		checkAccess();
		final T info = getLibraryElementInfo(input);
		return info != null ? info.getSynchronizationStamp() : 0;
	}

	@Override
	public boolean mustSaveLibraryElement(final IEditorInput input) {
		checkAccess();
		final T info = getLibraryElementInfo(input);
		return info != null && info.isDirty() && info.getReferenceCount() == 1;
	}

	@Override
	public boolean canSaveLibraryElement(final IEditorInput input) {
		checkAccess();
		final T info = getLibraryElementInfo(input);
		return info != null && info.isDirty();
	}

	@Override
	public GraphicalAnnotationModel getAnnotationModel(final IEditorInput input) {
		checkAccess();
		final T info = getLibraryElementInfo(input);
		return info != null ? info.getAnnotationModel() : null;
	}

	protected T createLibraryElementInfo(final IEditorInput input) throws CoreException {
		throw new CoreException(Status.error(MessageFormat
				.format(Messages.AbstractLibraryElementProvider_CannotHandleInput, input.getToolTipText())));
	}

	protected final T getLibraryElementInfo(final IEditorInput input) {
		return input != null ? infos.get(input) : null;
	}

	protected abstract WorkspaceModifyOperation wrapOperation(final T info,
			final LibraryElementProviderOperation<T> operation);

	protected static void checkAccess() throws IllegalStateException {
		if (Display.getCurrent() == null) {
			throw new IllegalStateException("Must be in the Display thread"); //$NON-NLS-1$
		}
	}

	protected static void executeOperation(final IRunnableWithProgress runnable, final IProgressMonitor monitor)
			throws CoreException {
		try {
			runnable.run(monitor);
		} catch (final InvocationTargetException e) {
			if (e.getTargetException() instanceof final CoreException coreException) {
				throw coreException;
			}
			throw new CoreException(Status.error(e.getMessage(), e));
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new CoreException(Status.error(e.getMessage(), e));
		}
	}

	protected abstract class LibraryElementInfo {
		private final IEditorInput input;
		private LibraryElement libraryElement;
		private long synchronizationStamp = IResource.NULL_STAMP;
		private int referenceCount;
		private boolean dirty;

		protected LibraryElementInfo(final IEditorInput input, final LibraryElement libraryElement) {
			this.input = input;
			this.libraryElement = libraryElement;
		}

		protected IEditorInput getEditorInput() {
			return input;
		}

		protected LibraryElement getLibraryElement() {
			return libraryElement;
		}

		protected void setLibraryElement(final LibraryElement libraryElement) {
			this.libraryElement = libraryElement;
		}

		protected long getSynchronizationStamp() {
			return synchronizationStamp;
		}

		protected void setSynchronizationStamp(final long synchronizationStamp) {
			this.synchronizationStamp = synchronizationStamp;
		}

		protected abstract GraphicalAnnotationModel getAnnotationModel();

		protected int getReferenceCount() {
			return referenceCount;
		}

		protected void connect() {
			referenceCount++;
		}

		protected boolean disconnect() {
			return --referenceCount == 0;
		}

		protected void dispose() {
			libraryElement = null;
		}

		protected boolean isDirty() {
			return dirty;
		}

		protected void setDirty(final boolean dirty) {
			this.dirty = dirty;
		}
	}

	@FunctionalInterface
	protected interface LibraryElementProviderOperation<T extends AbstractLibraryElementProvider<T>.LibraryElementInfo> {

		void run(T info, IProgressMonitor monitor) throws CoreException;
	}
}
