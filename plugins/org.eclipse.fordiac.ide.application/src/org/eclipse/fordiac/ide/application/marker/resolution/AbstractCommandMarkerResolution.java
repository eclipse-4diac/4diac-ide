/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.application.marker.resolution;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarker;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.util.LibraryElementValidator;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.views.markers.WorkbenchMarkerResolution;

/**
 * An abstract marker resolution encapsulating a {@link Command} to be executed
 * on a particular element directly in a file or via the {@link CommandStack} of
 * an open editor.
 *
 * @implSpec Do NOT keep any direct references to any elements, since they may
 *           become out of date. Always use the element parameter passed to the
 *           respective methods.
 */
public abstract class AbstractCommandMarkerResolution<T extends EObject> extends WorkbenchMarkerResolution {

	private static record LibraryElementInfo(LibraryElement libraryElement, CompoundCommand commands,
			Optional<CommandStack> commandStack, boolean needsSave) {
	}

	private final IMarker marker;
	private final Class<T> elementClass;
	private final Map<IResource, LibraryElementInfo> infos = new HashMap<>();

	/**
	 * Create an abstract command marker resolution
	 *
	 * @param elementClass The element class
	 */
	protected AbstractCommandMarkerResolution(final IMarker marker, final Class<T> elementClass) {
		this.marker = Objects.requireNonNull(marker);
		this.elementClass = Objects.requireNonNull(elementClass);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void run(final IMarker marker) {
		if (!marker.exists()) {
			return;
		}
		run(new IMarker[] { marker }, new NullProgressMonitor());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void run(final IMarker[] markers, final IProgressMonitor monitor) {
		if (markers.length == 0) {
			return; // nothing to fix
		}
		try {
			new WorkspaceModifyOperation() {

				@Override
				protected void execute(final IProgressMonitor monitor)
						throws CoreException, InvocationTargetException, InterruptedException {
					runInWorkspace(markers, monitor);
				}
			}.run(monitor);
		} catch (final InvocationTargetException e) {
			if (e.getCause() instanceof final CoreException ce) {
				ErrorDialog.openError(null, null, null, ce.getStatus());
			} else {
				FordiacLogHelper.logError(e.getMessage(), e);
			}
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	private void runInWorkspace(final IMarker[] markers, final IProgressMonitor monitor) throws CoreException {
		monitor.beginTask(getLabel(), 100);
		if (!prepare(markers, monitor.slice(10)) || monitor.isCanceled()) {
			return; // canceled
		}
		createCommands(markers, monitor.slice(20));
		commit(monitor.slice(70));
	}

	@Override
	public IMarker[] findOtherMarkers(final IMarker[] markers) {
		return Stream.of(markers)
				.filter(other -> LibraryElementValidator.DIAGNOSTIC_SOURCE.equals(FordiacErrorMarker.getSource(other))
						&& isApplicable(other))
				.toArray(IMarker[]::new);
	}

	/**
	 * Check if the other marker is applicable for this resolution
	 *
	 * @param other The other marker
	 * @return true if applicable, false otherwise
	 */
	protected boolean isApplicable(final IMarker other) {
		return FordiacErrorMarker.getCode(marker) == FordiacErrorMarker.getCode(other)
				&& Arrays.equals(FordiacErrorMarker.getData(other), FordiacErrorMarker.getData(marker));
	}

	/**
	 * Prepare for resolving the markers (e.g., ask for additional user input)
	 *
	 * @param markers The markers (at least one)
	 * @param monitor The progress monitor
	 * @return true on success, false on cancel
	 * @throws CoreException if there was a problem preparing
	 */
	protected abstract boolean prepare(IMarker[] markers, final IProgressMonitor monitor) throws CoreException;

	protected void createCommands(final IMarker[] markers, final IProgressMonitor monitor) throws CoreException {
		monitor.beginTask(Messages.AbstractCommandMarkerResolution_PerformTask, markers.length);
		for (final IMarker marker : markers) {
			createCommand(marker, monitor.slice(1));
		}
	}

	private void createCommand(final IMarker marker, final IProgressMonitor monitor) throws CoreException {
		final LibraryElementInfo info = getOrCreateInfo(marker.getResource());
		final T element = getElement(marker, info.libraryElement());
		if (element == null) {
			throw createExceptionForMarker(Messages.AbstractCommandMarkerResolution_NoSuchElement, marker);
		}
		final Command command = createCommand(element, monitor);
		if (command == null) {
			throw createExceptionForMarker(Messages.AbstractCommandMarkerResolution_CannotCreateCommand, marker);
		}
		if (!command.canExecute()) {
			throw createExceptionForMarker(Messages.AbstractCommandMarkerResolution_CannotExecuteCommand, marker);
		}
		info.commands().add(command);
	}

	private static CoreException createExceptionForMarker(final String pattern, final IMarker marker) {
		return new CoreException(Status.error(MessageFormat.format(pattern, marker.getAttribute(IMarker.LOCATION, ""), //$NON-NLS-1$
				marker.getResource().getFullPath())));
	}

	/**
	 * Create the command for the element
	 *
	 * @param element The element
	 * @param monitor The progress monitor
	 * @return The command or null if the command could not be created
	 * @throws CoreException if there was a problem creating the command
	 */
	protected abstract Command createCommand(T element, IProgressMonitor monitor) throws CoreException;

	protected void commit(final IProgressMonitor monitor) throws CoreException {
		monitor.beginTask(Messages.AbstractCommandMarkerResolution_CommitTask, infos.size());
		for (final LibraryElementInfo info : infos.values()) {
			commit(info, monitor.slice(1));
		}
	}

	private static void commit(final LibraryElementInfo info, final IProgressMonitor monitor) throws CoreException {
		info.commandStack().ifPresentOrElse(commandStack -> commandStack.execute(info.commands()),
				info.commands()::execute);
		if (info.needsSave()) {
			info.libraryElement().getTypeEntry().save(info.libraryElement(), monitor);
			info.commandStack().ifPresent(CommandStack::markSaveLocation);
		}
	}

	/**
	 * Whether the editor needs to be saved after applying the resolutions
	 *
	 * @param editor The editor
	 * @return true if needs to be saved, false otherwise
	 * @implNote This only checks if the editor is not dirty by default.
	 */
	@SuppressWarnings("static-method") // subclasses may override
	protected boolean needsSave(final IEditorPart editor) {
		return !editor.isDirty();
	}

	/**
	 * Get the main marker
	 *
	 * @return The marker
	 */
	public IMarker getMarker() {
		return marker;
	}

	/**
	 * Get the element class
	 *
	 * @return The element class (will never be null)
	 */
	public final Class<T> getElementClass() {
		return elementClass;
	}

	/**
	 * Get the type library for the main marker
	 *
	 * @return The type library
	 */
	public TypeLibrary getTypeLibrary() {
		return TypeLibraryManager.INSTANCE.getTypeLibrary(marker.getResource().getProject());
	}

	private LibraryElementInfo getOrCreateInfo(final IResource resource) {
		return infos.computeIfAbsent(resource, this::createInfo);
	}

	private LibraryElementInfo createInfo(final IResource resource) {
		final Optional<IEditorPart> editor = findEditor(resource);
		final Optional<CommandStack> commandStack = editor.map(AbstractCommandMarkerResolution::getCommandStack);
		final LibraryElement libraryElement;
		if (editor.isPresent() && commandStack.isPresent()) {
			libraryElement = Adapters.adapt(editor.get(), LibraryElement.class);
		} else if (resource instanceof final IFile file) {
			final TypeEntry typeEntry = TypeLibraryManager.INSTANCE.getTypeEntryForFile(file);
			if (typeEntry == null) {
				return null;
			}
			libraryElement = typeEntry.copyType();
		} else {
			return null;
		}
		return new LibraryElementInfo(libraryElement, new CompoundCommand(), commandStack,
				commandStack.isEmpty() || editor.filter(this::needsSave).isPresent());
	}

	private static Optional<IEditorPart> findEditor(final IResource resource) {
		if (resource instanceof final IFile file) {
			final IEditorInput editorInput = new FileEditorInput(file);
			return Stream.of(PlatformUI.getWorkbench().getWorkbenchWindows())
					.flatMap(window -> Stream.of(window.getPages())).map(page -> page.findEditor(editorInput))
					.filter(Objects::nonNull).findAny();
		}
		return Optional.empty();
	}

	private static CommandStack getCommandStack(final IEditorPart editor) {
		return Adapters.adapt(editor, CommandStack.class);
	}

	private final T getElement(final IMarker marker, final LibraryElement libraryElement) {
		final EObject element = FordiacErrorMarker.getTargetRelative(marker, libraryElement);
		if (elementClass.isInstance(element)) {
			return elementClass.cast(element);
		}
		return null;
	}
}
