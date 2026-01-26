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
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.eclipse.core.commands.operations.IOperationHistoryListener;
import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.commands.operations.ObjectUndoContext;
import org.eclipse.core.commands.operations.OperationHistoryEvent;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.commands.operations.UndoContext;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SafeRunner;
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
	private final Set<LibraryElementStateListener> listeners = ConcurrentHashMap.newKeySet();

	protected AbstractLibraryElementProvider() {
		OperationHistoryFactory.getOperationHistory().addOperationHistoryListener(new LibraryElementUndoManager());
	}

	@Override
	public void connect(final IEditorInput input) throws CoreException {
		checkAccess();
		T info = getLibraryElementInfo(input);
		if (info == null) {
			info = Objects.requireNonNull(createLibraryElementInfo(input));
			infos.put(info.getEditorInput(), info);
			fireLibraryElementStateChange(listener -> listener.elementConnected(input));
		}
		info.connect();
	}

	@Override
	public void disconnect(final IEditorInput input) {
		checkAccess();
		final T info = getLibraryElementInfo(input);
		if (info != null && info.disconnect()) {
			infos.remove(info.getEditorInput(), info);
			fireLibraryElementStateChange(listener -> listener.elementDisconnected(input));
			info.dispose();
		}
	}

	@Override
	public <U> U getElement(final IEditorInput input, final Class<? extends U> elementClass) throws ClassCastException {
		if (input instanceof final ISubEditorInput subEditorInput) {
			return elementClass.cast(getSubElement(subEditorInput));
		}
		return elementClass.cast(getLibraryElement(input));
	}

	protected Object getSubElement(final ISubEditorInput input) {
		final LibraryElement libraryElement = getLibraryElement(input);
		if (libraryElement == null) {
			return null;
		}
		if (input.getFragment().startsWith("/") && libraryElement.eResource() != null) { //$NON-NLS-1$
			return libraryElement.eResource().getEObject(input.getFragment());
		}
		return libraryElement.findByQualifiedName(input.getFragment()).filter(input.getElementClass()::isInstance)
				.findFirst().orElse(null);
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

	@Override
	public IUndoContext getUndoContext(final IEditorInput input) {
		checkAccess();
		final T info = getLibraryElementInfo(input);
		return info != null ? info.getUndoContext() : null;
	}

	@Override
	public void addLibraryElementStateListener(final LibraryElementStateListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeLibraryElementStateListener(final LibraryElementStateListener listener) {
		listeners.remove(listener);
	}

	protected void fireLibraryElementStateChange(final Consumer<LibraryElementStateListener> consumer) {
		listeners.forEach(listener -> SafeRunner.run(() -> consumer.accept(listener)));
	}

	protected T createLibraryElementInfo(final IEditorInput input) throws CoreException {
		if (input instanceof final ISubEditorInput subEditorInput) {
			return createLibraryElementInfo(subEditorInput.getParent());
		}
		throw new CoreException(Status.error(MessageFormat
				.format(Messages.AbstractLibraryElementProvider_CannotHandleInput, input.getToolTipText())));
	}

	protected final T getLibraryElementInfo(final IEditorInput input) {
		if (input instanceof final ISubEditorInput subEditorInput) {
			return infos.get(subEditorInput.getParent());
		}
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

		private IUndoContext undoContext;
		private IUndoableOperation saveLocation;

		private final LibraryElementDependencyUpdater updater = new LibraryElementDependencyUpdater();

		protected LibraryElementInfo(final IEditorInput input, final LibraryElement libraryElement) {
			this.input = input;
			setLibraryElement(libraryElement);
		}

		protected IEditorInput getEditorInput() {
			return input;
		}

		protected LibraryElement getLibraryElement() {
			return libraryElement;
		}

		protected void setLibraryElement(final LibraryElement libraryElement) {
			if (this.libraryElement != libraryElement) {
				this.libraryElement = libraryElement;
				undoContext = new ObjectUndoContext(libraryElement);
				updater.setLibraryElement(libraryElement);
			}
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
			undoContext = new UndoContext();
		}

		protected boolean isDirty() {
			return dirty;
		}

		protected void setDirty(final boolean dirty) {
			if (this.dirty != dirty) {
				this.dirty = dirty;
				fireLibraryElementStateChange(listener -> listener.elementDirtyStateChanged(input, dirty));
			}
		}

		protected void markDirty() {
			setDirty(true);
		}

		protected void updateDirty() {
			setDirty(getLastOperation() != getSaveLocation());
		}

		protected IUndoableOperation getSaveLocation() {
			return saveLocation;
		}

		protected void markSaveLocation() {
			this.saveLocation = getLastOperation();
		}

		protected IUndoContext getUndoContext() {
			return undoContext;
		}

		protected IUndoableOperation getLastOperation() {
			return Arrays.asList(OperationHistoryFactory.getOperationHistory().getUndoHistory(undoContext)).reversed()
					.stream().filter(this::hasContextStrict).findFirst().orElse(null);
		}

		private boolean hasContextStrict(final IUndoableOperation operation) {
			return Arrays.stream(operation.getContexts()).anyMatch(undoContext::equals);
		}
	}

	protected class LibraryElementUndoManager implements IOperationHistoryListener {

		@Override
		public void historyNotification(final OperationHistoryEvent event) {
			switch (event.getEventType()) {
			case OperationHistoryEvent.DONE -> findInfos(event).forEach(T::markDirty);
			case OperationHistoryEvent.UNDONE, OperationHistoryEvent.REDONE -> findInfos(event).forEach(T::updateDirty);
			default -> {
				// ignore
			}
			}
		}

		protected Stream<T> findInfos(final OperationHistoryEvent event) {
			return findInfos(event.getOperation().getContexts());
		}

		protected Stream<T> findInfos(final IUndoContext[] contexts) {
			return Arrays.stream(contexts).map(this::findInfo).flatMap(Optional::stream);
		}

		protected Optional<T> findInfo(final IUndoContext context) {
			return infos.values().stream().filter(info -> info.getUndoContext().equals(context)).findFirst();
		}
	}

	@FunctionalInterface
	protected interface LibraryElementProviderOperation<T extends AbstractLibraryElementProvider<T>.LibraryElementInfo> {

		void run(T info, IProgressMonitor monitor) throws CoreException;
	}
}
