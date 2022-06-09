/*******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.debug.fb;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.fordiac.ide.debug.CommonLaunchConfigurationDelegate;
import org.eclipse.fordiac.ide.debug.LaunchConfigurationAttributes;
import org.eclipse.fordiac.ide.model.eval.Evaluator;
import org.eclipse.fordiac.ide.model.eval.fb.FBEvaluator;
import org.eclipse.fordiac.ide.model.eval.variable.FBVariable;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;

public abstract class FBLaunchConfigurationDelegate extends CommonLaunchConfigurationDelegate {

	@Override
	public void launch(final ILaunchConfiguration configuration, final String mode, final ILaunch launch,
			final IProgressMonitor monitor) throws CoreException {
		final IResource resource = LaunchConfigurationAttributes.getResource(configuration);
		if (resource instanceof IFile) {
			final FBType type = (FBType) TypeLibraryManager.INSTANCE.getTypeEntryForFile((IFile) resource).getType();
			final var event = FBLaunchConfigurationAttributes.getEvent(configuration, type, getDefaultEvent(type));
			final var repeatEvent = FBLaunchConfigurationAttributes.isRepeatEvent(configuration);
			final var defaultArguments = getDefaultArguments(type);
			final var variables = LaunchConfigurationAttributes.getArguments(configuration, defaultArguments);
			final Queue<Event> queue = new LaunchEventQueue(event, repeatEvent);
			final Evaluator evaluator = createEvaluator(type, queue, variables);
			launch(evaluator, configuration, mode, launch, monitor);
		}
	}

	public abstract FBEvaluator<? extends FBType> createEvaluator(FBType type, Queue<Event> queue,
			List<Variable<?>> variables) throws CoreException;

	@SuppressWarnings("static-method")
	protected List<Variable<?>> getDefaultArguments(final FBType type) {
		return List.copyOf(new FBVariable("dummy", type).getMembers().values()); //$NON-NLS-1$
	}

	@SuppressWarnings("static-method")
	protected Event getDefaultEvent(final FBType type) {
		final var eventInputs = type.getInterfaceList().getEventInputs();
		if (!eventInputs.isEmpty()) {
			return eventInputs.get(0);
		}
		return null;
	}

	protected static class LaunchEventQueue extends AbstractQueue<Event> {
		private final Event event;
		private final boolean repeat;
		private boolean full;

		public LaunchEventQueue(final Event event, final boolean repeat) {
			this.event = event;
			this.repeat = repeat;
			this.full = true;
		}

		@Override
		public boolean offer(final Event e) {
			return true; // infinite sink
		}

		@Override
		public Event poll() {
			if (full) {
				full = repeat;
				return event;
			}
			return null;
		}

		@Override
		public Event peek() {
			return full ? event : null;
		}

		@Override
		public Iterator<Event> iterator() {
			return new LaunchEventIterator();
		}

		@Override
		public int size() {
			return full ? 1 : 0;
		}

		private class LaunchEventIterator implements Iterator<Event> {
			private boolean start;

			@Override
			public boolean hasNext() {
				return start || repeat;
			}

			@Override
			public Event next() {
				if (!hasNext()) {
					throw new NoSuchElementException();
				}
				start = false;
				return event;
			}
		}
	}
}
