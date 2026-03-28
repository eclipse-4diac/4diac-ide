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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ICoreRunnable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.ui.Messages;
import org.eclipse.fordiac.ide.model.ui.annotation.FordiacMarkerGraphicalAnnotationModel;
import org.eclipse.fordiac.ide.model.ui.annotation.GraphicalAnnotationModel;
import org.eclipse.fordiac.ide.model.ui.validation.ValidationJob;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.progress.UIJob;

public class FileLibraryElementProvider
		extends AbstractLibraryElementProvider<FileLibraryElementProvider.FileLibraryElementInfo> {

	private final LibraryElementSynchronizer resourceChangeListener = new LibraryElementSynchronizer();

	protected FileLibraryElementProvider() {
		resourceChangeListener.install();
	}

	@Override
	protected void doResetLibraryElement(final FileLibraryElementInfo info, final IProgressMonitor monitor)
			throws CoreException {
		fireLibraryElementStateChange(listener -> listener.elementContentAboutToBeReplaced(info.getEditorInput()));
		info.getEditorInput().getFile().refreshLocal(IResource.DEPTH_INFINITE, monitor);
		info.setLibraryElement(copyLibraryElement(info.getEditorInput().getFile()));
		info.setSynchronizationStamp(info.getEditorInput().getFile().getModificationStamp());
		info.markSaveLocation();
		info.setDirty(false);
		info.getValidationJob().reload();
		fireLibraryElementStateChange(listener -> listener.elementContentReplaced(info.getEditorInput()));
	}

	@Override
	protected void doSaveLibraryElement(final FileLibraryElementInfo info, final IProgressMonitor monitor)
			throws CoreException {
		info.getLibraryElement().getTypeEntry().save(info.getLibraryElement(), monitor);
		info.setSynchronizationStamp(info.getEditorInput().getFile().getModificationStamp());
		info.markSaveLocation();
		info.setDirty(false);
		info.getValidationJob().reset();
	}

	@Override
	protected void doSynchronize(final FileLibraryElementInfo info, final IProgressMonitor monitor)
			throws CoreException {
		final SubMonitor subMonitor = SubMonitor.convert(monitor, 10);
		info.getEditorInput().getFile().refreshLocal(IResource.DEPTH_INFINITE, subMonitor.split(2));
		doResetLibraryElement(info, subMonitor.split(8));
	}

	@Override
	protected boolean doIsSynchronized(final FileLibraryElementInfo info) {
		return info.getEditorInput().getFile().isSynchronized(IResource.DEPTH_ZERO)
				&& info.getSynchronizationStamp() == info.getEditorInput().getFile().getModificationStamp();
	}

	@Override
	protected long doGetModificationStamp(final FileLibraryElementInfo info) {
		return info.getEditorInput().getFile().getModificationStamp();
	}

	@Override
	public boolean isReadOnly(final IEditorInput input) {
		return false;
	}

	@Override
	public boolean isDeleted(final IEditorInput input) {
		return input instanceof final IFileEditorInput fileEditorInput && !fileEditorInput.getFile().exists();
	}

	@Override
	protected FileLibraryElementInfo createLibraryElementInfo(final IEditorInput input) throws CoreException {
		if (input instanceof final IFileEditorInput fileEditorInput) {
			fileEditorInput.getFile().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
			return new FileLibraryElementInfo(fileEditorInput, copyLibraryElement(fileEditorInput.getFile()));
		}
		return super.createLibraryElementInfo(input);
	}

	protected void handleLibraryElementContentChanged(final FileLibraryElementInfo info) {
		if (info.getSynchronizationStamp() == info.getEditorInput().getFile().getModificationStamp()
				|| info.isDirty()) {
			return;
		}
		try {
			doResetLibraryElement(info, new NullProgressMonitor());
		} catch (final CoreException e) {
			FordiacLogHelper.logWarning("Error refreshing changed library element", e); //$NON-NLS-1$
		}
	}

	protected void handleLibraryElementMoved(final FileLibraryElementInfo info, final IFileEditorInput movedInput) {
		fireLibraryElementStateChange(listener -> listener.elementMoved(info.getEditorInput(), movedInput));
	}

	protected void handleLibraryElementDeleted(final FileLibraryElementInfo info) {
		fireLibraryElementStateChange(listener -> listener.elementDeleted(info.getEditorInput()));
	}

	protected static LibraryElement copyLibraryElement(final IFile file) throws CoreException {
		final TypeEntry entry = TypeLibraryManager.INSTANCE.getTypeEntryForFile(file);
		if (entry == null) {
			throw new CoreException(Status.error(MessageFormat
					.format(Messages.FileLibraryElementProvider_LibraryElementDoesNotExist, file.getFullPath())));
		}
		final LibraryElement libraryElement = entry.copyType();
		if (libraryElement == null) {
			throw new CoreException(Status.error(MessageFormat
					.format(Messages.FileLibraryElementProvider_LibraryElementCannotBeLoaded, file.getFullPath())));
		}
		return libraryElement;
	}

	@Override
	protected WorkspaceModifyOperation wrapOperation(final FileLibraryElementInfo info,
			final LibraryElementProviderOperation<FileLibraryElementInfo> operation) {
		return new WorkspaceModifyOperation(info.getEditorInput().getFile().getParent()) {

			@Override
			protected void execute(final IProgressMonitor monitor)
					throws CoreException, InvocationTargetException, InterruptedException {
				operation.run(info, monitor);
			}
		};
	}

	protected class FileLibraryElementInfo extends
			AbstractLibraryElementProvider<FileLibraryElementProvider.FileLibraryElementInfo>.LibraryElementInfo {
		private final GraphicalAnnotationModel annotationModel;
		private final ValidationJob validationJob;

		private boolean readOnly;

		protected FileLibraryElementInfo(final IFileEditorInput input, final LibraryElement libraryElement) {
			super(input, libraryElement);
			setSynchronizationStamp(input.getFile().getModificationStamp());
			annotationModel = new FordiacMarkerGraphicalAnnotationModel(input.getFile(), this::getLibraryElement);
			validationJob = new ValidationJob(PackageNameHelper.getFullTypeName(libraryElement), this::getUndoContext,
					annotationModel);
		}

		protected boolean isReadOnly() {
			return readOnly;
		}

		protected void setReadOnly(final boolean readOnly) {
			this.readOnly = readOnly;
		}

		@Override
		protected GraphicalAnnotationModel getAnnotationModel() {
			return annotationModel;
		}

		protected ValidationJob getValidationJob() {
			return validationJob;
		}

		@Override
		protected IFileEditorInput getEditorInput() {
			return (IFileEditorInput) super.getEditorInput();
		}

		@Override
		protected void dispose() {
			validationJob.dispose();
			annotationModel.dispose();
			super.dispose();
		}
	}

	protected class LibraryElementSynchronizer implements IResourceChangeListener, IResourceDeltaVisitor {

		@Override
		public void resourceChanged(final IResourceChangeEvent event) {
			final IResourceDelta delta = event.getDelta();
			try {
				if (delta != null) {
					delta.accept(this);
				}
			} catch (final CoreException e) {
				FordiacLogHelper.logError(e.getMessage(), e);
			}
		}

		@Override
		public boolean visit(final IResourceDelta delta) throws CoreException {
			switch (delta.getKind()) {
			case IResourceDelta.CHANGED -> handleResourceChanged(delta);
			case IResourceDelta.REMOVED -> handleResourceRemoved(delta);
			default -> {
				// ignore
			}
			}
			return true;
		}

		protected void handleResourceChanged(final IResourceDelta delta) {
			if (delta.getResource() instanceof final IFile file) {
				final FileLibraryElementInfo info = getLibraryElementInfo(new FileEditorInput(file));
				if (info != null && (IResourceDelta.CONTENT & delta.getFlags()) != 0) {
					final UIJob job = UIJob.create(
							MessageFormat.format(Messages.FileLibraryElementProvider_RefreshJobName, file.getName()),
							(ICoreRunnable) monitor -> handleLibraryElementContentChanged(info));
					job.setRule(ResourcesPlugin.getWorkspace().getRoot());
					job.setPriority(Job.INTERACTIVE);
					job.schedule();
				}
			}
		}

		protected void handleResourceRemoved(final IResourceDelta delta) {
			if (delta.getResource() instanceof final IFile file) {
				final FileLibraryElementInfo info = getLibraryElementInfo(new FileEditorInput(file));
				if (info != null) {
					if ((IResourceDelta.MOVED_TO & delta.getFlags()) != 0) {
						final IFile newFile = file.getWorkspace().getRoot().getFile(delta.getMovedToPath());
						final UIJob job = UIJob.create(
								MessageFormat.format(Messages.FileLibraryElementProvider_MoveJobName, file.getName()),
								(ICoreRunnable) monitor -> handleLibraryElementMoved(info,
										new FileEditorInput(newFile)));
						job.setRule(ResourcesPlugin.getWorkspace().getRoot());
						job.setPriority(Job.INTERACTIVE);
						job.schedule();
					} else {
						final UIJob job = UIJob.create(
								MessageFormat.format(Messages.FileLibraryElementProvider_DeleteJobName, file.getName()),
								(ICoreRunnable) monitor -> handleLibraryElementDeleted(info));
						job.setRule(ResourcesPlugin.getWorkspace().getRoot());
						job.setPriority(Job.INTERACTIVE);
						job.schedule();
					}
				}
			}
		}

		public void install() {
			ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
		}

		public void uninstall() {
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
		}
	}
}
