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
package org.eclipse.fordiac.ide.deployment.debug.ui.annotation;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.eclipse.core.resources.IFile;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IDebugEventSetListener;
import org.eclipse.fordiac.ide.deployment.debug.DeploymentDebugElement;
import org.eclipse.fordiac.ide.deployment.debug.DeploymentDebugStackFrame;
import org.eclipse.fordiac.ide.deployment.debug.DeploymentDebugTarget;
import org.eclipse.fordiac.ide.deployment.debug.watch.IContainerWatch;
import org.eclipse.fordiac.ide.deployment.debug.watch.IInterfaceElementWatch;
import org.eclipse.fordiac.ide.deployment.debug.watch.IWatch;
import org.eclipse.fordiac.ide.gef.annotation.FordiacMarkerGraphicalAnnotationModel;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotation;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotationModel;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotationProvider;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;

public class WatchValueAnnotationProvider implements GraphicalAnnotationProvider, IDebugEventSetListener {

	private FordiacMarkerGraphicalAnnotationModel model;

	@Override
	public boolean initialize(final GraphicalAnnotationModel model, final Object context) {
		if (model instanceof final FordiacMarkerGraphicalAnnotationModel markerModel
				&& context instanceof final IFile file
				&& TypeLibraryTags.SYSTEM_TYPE_FILE_ENDING.equalsIgnoreCase(file.getFileExtension())) {
			this.model = markerModel;
			DebugPlugin.getDefault().addDebugEventListener(this);
			return true;
		}
		return false;
	}

	@Override
	public void handleDebugEvents(final DebugEvent[] events) {
		final Set<GraphicalAnnotation> add = new HashSet<>();
		final Set<GraphicalAnnotation> remove = new HashSet<>();
		final Set<GraphicalAnnotation> changed = new HashSet<>();
		for (final DebugEvent event : events) {
			if (!isRelevant(event)) {
				continue;
			}
			switch (event.getSource()) {
			case final IWatch watch when event.getKind() == DebugEvent.CHANGE ->
				handleWatchChanged(watch, add, changed);
			case final DeploymentDebugStackFrame stackFrame when event.getKind() == DebugEvent.CHANGE ->
				handleWatchesChanged(stackFrame, add, remove, changed);
			case final DeploymentDebugTarget debugTarget when event.getKind() == DebugEvent.TERMINATE -> reload();
			default -> {
				return;
			}
			}
		}
		model.updateAnnotations(add, remove, changed);
	}

	private void handleWatchesChanged(final DeploymentDebugStackFrame stackFrame, final Set<GraphicalAnnotation> add,
			final Set<GraphicalAnnotation> remove, final Set<GraphicalAnnotation> changed) {
		stackFrame.getDebugTarget().getWatches().values().forEach(watch -> handleWatchChanged(watch, add, changed));
		model.forEach(annotation -> { // remove any watch value annotations we have not visited
			if (annotation instanceof final WatchValueAnnotation watchValueAnnotation && !add.contains(annotation)
					&& !changed.contains(annotation)) {
				remove.add(watchValueAnnotation);
			}
		});
	}

	protected void handleWatchChanged(final IWatch watch, final Set<GraphicalAnnotation> add,
			final Set<GraphicalAnnotation> changed) {
		if (watch instanceof final IInterfaceElementWatch interfaceElementWatch) {
			handleInterfaceElementWatchChanged(interfaceElementWatch, add, changed);
		}
		if (watch instanceof final IContainerWatch containerWatch) {
			containerWatch.getSubWatches().forEach(subWatch -> handleWatchChanged(subWatch, add, changed));
		}
	}

	private void handleInterfaceElementWatchChanged(final IInterfaceElementWatch watch,
			final Set<GraphicalAnnotation> add, final Set<GraphicalAnnotation> changed) {
		final Optional<IInterfaceElement> target = findTarget(watch, IInterfaceElement.class);
		if (target.isPresent()) {
			getTargetFBNetworks(target.get()).forEachOrdered(
					network -> handleInterfaceElementWatchChanged(watch, target.get(), network, add, changed));
		}
	}

	private void handleInterfaceElementWatchChanged(final IInterfaceElementWatch watch, final IInterfaceElement target,
			final FBNetwork network, final Set<GraphicalAnnotation> add, final Set<GraphicalAnnotation> changed) {
		final Optional<WatchValueAnnotation> annotation = findAnnotation(network, target);
		if (annotation.isEmpty()) {
			add.add(new WatchValueAnnotation(network, target, watch));
		} else {
			changed.add(annotation.get());
		}
	}

	private <T extends INamedElement> Optional<T> findTarget(final IWatch watch, final Class<T> targetClass) {
		return model.getLibraryElement().findByQualifiedName(watch.getQualifiedName()).filter(targetClass::isInstance)
				.map(targetClass::cast).findFirst();
	}

	private Optional<WatchValueAnnotation> findAnnotation(final FBNetwork network, final IInterfaceElement element) {
		return model.getAnnotations(network).stream().filter(WatchValueAnnotation.class::isInstance)
				.map(WatchValueAnnotation.class::cast).filter(annotation -> annotation.getElement() == element)
				.findAny();
	}

	private static Stream<FBNetwork> getTargetFBNetworks(final IInterfaceElement target) {
		final FBNetworkElement networkElement = target.getFBNetworkElement();
		return switch (networkElement) {
		case null -> Stream.empty();
		case final AdapterFB adapterFB -> Stream.concat(Stream.ofNullable(networkElement.getFbNetwork()),
				getTargetFBNetworks(adapterFB.getAdapterDecl()));
		default -> Stream.ofNullable(networkElement.getFbNetwork());
		};
	}

	protected boolean isRelevant(final DebugEvent event) {
		return event.getSource() instanceof final DeploymentDebugElement element
				&& element.getDebugTarget().getSystem().getTypeEntry().getFile().equals(model.getResource());
	}

	@Override
	public void reload() {
		model.removeAnnotationIf(WatchValueAnnotation.class::isInstance);
	}

	@Override
	public void dispose() {
		DebugPlugin.getDefault().removeDebugEventListener(this);
	}
}
