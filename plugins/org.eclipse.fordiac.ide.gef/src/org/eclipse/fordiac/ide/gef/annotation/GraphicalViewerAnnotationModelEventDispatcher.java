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
package org.eclipse.fordiac.ide.gef.annotation;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ProgressProvider;
import org.eclipse.fordiac.ide.gef.Messages;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.IProgressService;
import org.eclipse.ui.progress.UIJob;

public class GraphicalViewerAnnotationModelEventDispatcher extends UIJob {

	private final GraphicalViewer viewer;
	private final GraphicalAnnotationModel annotationModel;
	private final DispatcherGraphicalAnnotationModelListener annotationModelListener = new DispatcherGraphicalAnnotationModelListener();
	private final BlockingQueue<GraphicalAnnotationModelEvent> queue = new LinkedBlockingQueue<>();

	public GraphicalViewerAnnotationModelEventDispatcher(final String name, final GraphicalViewer viewer,
			final GraphicalAnnotationModel annotationModel) {
		super(MessageFormat.format(Messages.GraphicalViewerAnnotationModelEventDispatcher_Name, name));
		this.annotationModel = Objects.requireNonNull(annotationModel);
		this.viewer = Objects.requireNonNull(viewer);
		annotationModelListener.install(annotationModel);
	}

	@Override
	public IStatus runInUIThread(final IProgressMonitor monitor) {
		final List<GraphicalAnnotationModelEvent> queued = new ArrayList<>();
		queue.drainTo(queued);
		try {
			dispatch(queued, monitorFor(monitor));
		} catch (final OperationCanceledException e) {
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

	protected void dispatch(final List<GraphicalAnnotationModelEvent> events, final IProgressMonitor monitor) {
		for (final GraphicalAnnotationModelEvent event : events) {
			if (monitor.isCanceled()) {
				throw new OperationCanceledException();
			}
			dispatch(event, monitor);
		}
	}

	protected void dispatch(final GraphicalAnnotationModelEvent event, final IProgressMonitor monitor) {
		for (final Object target : event.getTargets()) {
			if (monitor.isCanceled()) {
				throw new OperationCanceledException();
			}
			dispatch(target, event);
		}
	}

	protected void dispatch(final Object target, final GraphicalAnnotationModelEvent event) {
		final AnnotableGraphicalEditPart editPart = findEditPart(target);
		if (editPart != null) {
			editPart.updateAnnotations(event);
		}
	}

	protected AnnotableGraphicalEditPart findEditPart(final Object target) {
		if ((target != null)
				&& (viewer.getEditPartForModel(target) instanceof final AnnotableGraphicalEditPart editPart)) {
			return editPart;
		}
		return null;
	}

	public void dispose() {
		annotationModelListener.uninstall(annotationModel);
		queue.clear();
		cancel();
	}

	public GraphicalViewer getViewer() {
		return viewer;
	}

	private void handleEvent(final GraphicalAnnotationModelEvent event) {
		queue.add(event);
		schedule();
	}

	protected class DispatcherGraphicalAnnotationModelListener implements GraphicalAnnotationModelListener {

		@Override
		public void modelChanged(final GraphicalAnnotationModelEvent event) {
			handleEvent(event);
		}

		public void install(final GraphicalAnnotationModel annotationModel) {
			annotationModel.addAnnotationModelListener(this, true);
		}

		public void uninstall(final GraphicalAnnotationModel annotationModel) {
			annotationModel.removeAnnotationModelListener(this);
		}
	}
}
