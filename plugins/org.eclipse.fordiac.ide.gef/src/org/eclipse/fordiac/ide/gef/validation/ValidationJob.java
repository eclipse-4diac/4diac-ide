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
package org.eclipse.fordiac.ide.gef.validation;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Spliterators;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ProgressProvider;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.gef.Messages;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotation;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotationModel;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CommandStackEvent;
import org.eclipse.gef.commands.CommandStackEventListener;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.IProgressService;
import org.eclipse.ui.progress.UIJob;

public class ValidationJob extends UIJob {

	private static final long DELAY = 500; // in milliseconds

	private final CommandStack commandStack;
	private final GraphicalAnnotationModel annotationModel;
	private final ValidationCommandStackEventListener commandStackEventListener = new ValidationCommandStackEventListener();
	private final BlockingQueue<EObject> queue = new LinkedBlockingQueue<>();
	private boolean enabled = true;

	public ValidationJob(final String name, final CommandStack commandStack,
			final GraphicalAnnotationModel annotationModel) {
		super(MessageFormat.format(Messages.ValidationJob_ValidationJobName, name));
		this.commandStack = commandStack;
		this.annotationModel = annotationModel;
		commandStackEventListener.install(commandStack);
	}

	@Override
	public IStatus runInUIThread(final IProgressMonitor monitor) {
		final List<EObject> queued = new ArrayList<>();
		queue.drainTo(queued);
		final List<EObject> filtered = EcoreUtil.filterDescendants(queued);
		try {
			final CancelableDiagnostician diagnostician = new VariableDiagnostician(monitorFor(monitor));
			final List<Diagnostic> diagnostics = filtered.stream().map(diagnostician::validate).toList();
			updateAnnotations(diagnostics, monitor);
		} catch (final OperationCanceledException e) {
			queue.addAll(filtered); // add (filtered) elements back to queue
			return Status.CANCEL_STATUS;
		}
		return Status.OK_STATUS;
	}

	protected static IProgressMonitor monitorFor(final IProgressMonitor monitor) {
		if (PlatformUI.isWorkbenchRunning()) {
			final IProgressService progressService = PlatformUI.getWorkbench().getProgressService();
			if (progressService instanceof final ProgressProvider progressProvider) {
				return progressProvider.monitorFor(monitor);
			}
		}
		return IProgressMonitor.nullSafe(monitor);
	}

	protected void updateAnnotations(final List<Diagnostic> diagnostics, final IProgressMonitor monitor) {
		final Set<GraphicalAnnotation> add = diagnostics.stream().map(Diagnostic::getChildren)
				.flatMap(Collection::stream).map(GraphicalValidationAnnotation::forDiagnostic).flatMap(Optional::stream)
				.collect(Collectors.toSet());
		final Set<GraphicalAnnotation> remove = StreamSupport
				.stream(Spliterators.spliteratorUnknownSize(EcoreUtil.getAllContents(
						diagnostics.stream().map(FordiacMarkerHelper::getDiagnosticTarget).toList(), true), 0), false)
				.map(annotationModel::getAnnotations).flatMap(Collection::stream)
				.filter(ValidationJob::isValidationAnnotation).filter(Predicate.not(add::contains))
				.collect(Collectors.toSet());
		if (monitor.isCanceled()) {
			throw new OperationCanceledException();
		}
		annotationModel.updateAnnotations(add, remove, Collections.emptySet());
	}

	protected static boolean isValidationAnnotation(final GraphicalAnnotation annotation) {
		return GraphicalAnnotation.TYPE_ERROR.equals(annotation.getType())
				|| GraphicalAnnotation.TYPE_WARNING.equals(annotation.getType())
				|| GraphicalAnnotation.TYPE_INFO.equals(annotation.getType());
	}

	protected void elementsChanged(final Collection<? extends EObject> elements) {
		if (enabled) {
			queue.addAll(elements);
			cancel();
			schedule(DELAY);
		}
	}

	protected void handleCommand(final Command command) {
		if (command instanceof final CompoundCommand compoundCommand) {
			compoundCommand.getCommands().forEach(this::handleCommand);
		} else if (command instanceof final ScopedCommand scopedCommand) {
			elementsChanged(scopedCommand.getAffectedObjects());
		}
	}

	public void reset() {
		cancel();
		queue.clear();
		annotationModel.removeAnnotationIf(GraphicalValidationAnnotation.class::isInstance);
		annotationModel.reload();
	}

	public void dispose() {
		setEnabled(false);
		commandStackEventListener.uninstall(commandStack);
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(final boolean enabled) {
		this.enabled = enabled;
		if (!enabled) {
			reset();
		}
	}

	protected class ValidationCommandStackEventListener implements CommandStackEventListener {

		@Override
		public void stackChanged(final CommandStackEvent event) {
			if (event.isPostChangeEvent()) {
				switch (event.getDetail()) {
				case CommandStack.POST_EXECUTE, CommandStack.POST_UNDO, CommandStack.POST_REDO ->
					handleCommand(event.getCommand());
				case CommandStack.POST_FLUSH, CommandStack.POST_MARK_SAVE -> reset();
				default -> {
					// empty
				}
				}
			}
		}

		public void install(final CommandStack commandStack) {
			commandStack.addCommandStackEventListener(this);
		}

		public void uninstall(final CommandStack commandStack) {
			commandStack.removeCommandStackEventListener(this);
		}
	}
}
