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

import org.eclipse.core.commands.operations.IOperationHistoryListener;
import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.commands.operations.OperationHistoryEvent;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.runtime.ICoreRunnable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.ProgressProvider;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.gef.Messages;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotation;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotationModel;
import org.eclipse.fordiac.ide.model.commands.ScopedOperation;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.IProgressService;
import org.eclipse.ui.progress.UIJob;

public class ValidationJob extends UIJob {

	private static final long DELAY = 500; // in milliseconds

	private final IUndoContext undoContext;
	private final GraphicalAnnotationModel annotationModel;
	private final ValidationOperationHistoryListener listener = new ValidationOperationHistoryListener();
	private final BlockingQueue<EObject> queue = new LinkedBlockingQueue<>();
	private boolean enabled = true;

	public ValidationJob(final String name, final IUndoContext undoContext,
			final GraphicalAnnotationModel annotationModel) {
		super(MessageFormat.format(Messages.ValidationJob_ValidationJobName, name));
		this.undoContext = undoContext;
		this.annotationModel = annotationModel;
		listener.install();
		reload();
	}

	@Override
	public IStatus runInUIThread(final IProgressMonitor monitor) {
		final List<EObject> queued = new ArrayList<>();
		queue.drainTo(queued);
		final List<EObject> filtered = EcoreUtil.filterDescendants(queued);
		try {
			final CancelableDiagnostician diagnostician = new VariableDiagnostician(monitorFor(monitor));
			final List<Diagnostic> diagnostics = filtered.stream().map(diagnostician::validate).toList();
			runInBackground(MessageFormat.format(Messages.ValidationJob_UpdateJobName, getName()),
					pm -> updateAnnotations(diagnostics, pm));
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

	protected static void runInBackground(final String name, final ICoreRunnable runnable) {
		final Job job = Job.create(name, runnable);
		job.setPriority(Job.DECORATE);
		job.schedule();
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

	protected void handleOperation(final IUndoableOperation operation) {
		if (operation instanceof final ScopedOperation scopedOperation) {
			elementsChanged(scopedOperation.getAffectedObjects());
		}
	}

	public void clear() {
		queue.clear();
		cancel();
	}

	public void reset() {
		clear();
		runInBackground(MessageFormat.format(Messages.ValidationJob_RefreshJobName, getName()),
				unused -> annotationModel.refresh());
	}

	public void reload() {
		clear();
		runInBackground(MessageFormat.format(Messages.ValidationJob_ReloadJobName, getName()),
				unused -> annotationModel.reload());
	}

	public void dispose() {
		listener.uninstall();
		setEnabled(false);
		clear();
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(final boolean enabled) {
		this.enabled = enabled;
	}

	protected class ValidationOperationHistoryListener implements IOperationHistoryListener {

		@Override
		public void historyNotification(final OperationHistoryEvent event) {
			if (!event.getOperation().hasContext(undoContext)) {
				return;
			}
			switch (event.getEventType()) {
			case OperationHistoryEvent.DONE, OperationHistoryEvent.UNDONE, OperationHistoryEvent.REDONE ->
				handleOperation(event.getOperation());
			default -> {
				// ignore
			}
			}
		}

		public void install() {
			OperationHistoryFactory.getOperationHistory().addOperationHistoryListener(this);
		}

		public void uninstall() {
			OperationHistoryFactory.getOperationHistory().removeOperationHistoryListener(this);
		}
	}
}
