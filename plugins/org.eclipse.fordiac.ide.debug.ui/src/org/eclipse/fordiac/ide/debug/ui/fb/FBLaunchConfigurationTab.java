/*******************************************************************************
 * Copyright (c) 2022, 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.debug.ui.fb;

import static org.eclipse.swt.events.SelectionListener.widgetSelectedAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.fordiac.ide.debug.fb.FBDebugClockMode;
import org.eclipse.fordiac.ide.debug.fb.FBLaunchConfigurationAttributes;
import org.eclipse.fordiac.ide.debug.fb.FBLaunchConfigurationDelegate;
import org.eclipse.fordiac.ide.debug.ui.MainLaunchConfigurationTab;
import org.eclipse.fordiac.ide.debug.ui.Messages;
import org.eclipse.fordiac.ide.debug.ui.widgets.DebugClockWidget;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;

public class FBLaunchConfigurationTab extends MainLaunchConfigurationTab {

	private ComboViewer eventCombo;
	private Button repeatEventCheckbox;
	private Button keepDebuggerRunningCheckbox;
	private final DebugClockWidget clockWidget = new DebugClockWidget(this::updateLaunchConfigurationDialog);

	@Override
	public void createControl(final Composite parent) {
		super.createControl(parent);

		final Composite comp = (Composite) getControl();

		final Composite eventComponent = createEventComponent(comp);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(eventComponent);

		final Composite argumentsComponent = createArgumentsComponent(comp);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(argumentsComponent);

		final Control clockComponent = clockWidget.createControl(comp);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(clockComponent);
	}

	@Override
	protected Composite createOptionsComponent(final Composite parent) {
		final Group group = (Group) super.createOptionsComponent(parent);
		final Composite comp = (Composite) group.getChildren()[0];

		repeatEventCheckbox = new Button(comp, SWT.CHECK);
		repeatEventCheckbox.setText(Messages.FBLaunchConfigurationTab_RepeatEvent);
		repeatEventCheckbox.addSelectionListener(widgetSelectedAdapter(e -> updateLaunchConfigurationDialog()));
		GridDataFactory.fillDefaults().applyTo(repeatEventCheckbox);

		keepDebuggerRunningCheckbox = new Button(comp, SWT.CHECK);
		keepDebuggerRunningCheckbox.setText(Messages.FBLaunchConfigurationTab_KeepDebuggerRunningWhenIdle);
		keepDebuggerRunningCheckbox.addSelectionListener(widgetSelectedAdapter(e -> updateLaunchConfigurationDialog()));
		GridDataFactory.fillDefaults().applyTo(keepDebuggerRunningCheckbox);

		return group;
	}

	protected Composite createEventComponent(final Composite parent) {
		final Group group = new Group(parent, SWT.BORDER);
		GridLayoutFactory.swtDefaults().applyTo(group);
		group.setText(Messages.FBLaunchConfigurationTab_Event);

		final Composite comp = new Composite(group, SWT.NONE);
		GridLayoutFactory.swtDefaults().numColumns(1).applyTo(comp);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(comp);

		eventCombo = new ComboViewer(comp, SWT.DROP_DOWN | SWT.READ_ONLY);
		eventCombo.setContentProvider(ArrayContentProvider.getInstance());
		eventCombo.setLabelProvider(new EventsLabelProvider());
		eventCombo.addSelectionChangedListener(e -> updateLaunchConfigurationDialog());
		GridDataFactory.fillDefaults().grab(true, false).applyTo(eventCombo.getCombo());

		return group;
	}

	@Override
	public void setDefaults(final ILaunchConfigurationWorkingCopy configuration) {
		super.setDefaults(configuration);
		configuration.removeAttribute(FBLaunchConfigurationAttributes.EVENT);
		configuration.removeAttribute(FBLaunchConfigurationAttributes.REPEAT_EVENT);
		configuration.removeAttribute(FBLaunchConfigurationAttributes.KEEP_RUNNING_WHEN_IDLE);
		configuration.removeAttribute(FBLaunchConfigurationAttributes.CLOCK_MODE);
		configuration.removeAttribute(FBLaunchConfigurationAttributes.CLOCK_INTERVAL);
		configuration.removeAttribute(FBLaunchConfigurationAttributes.CLOCK_REALTIME_OFFSET);
		configuration.removeAttribute(FBLaunchConfigurationAttributes.CLOCK_MONOTONIC_OFFSET);
	}

	@Override
	public void initializeFrom(final ILaunchConfiguration configuration) {
		super.initializeFrom(configuration);
		try {
			final FBType fbType = getFBType();
			final List<Event> events = getInputEvents(fbType);
			eventCombo.setInput(events);
			if (!events.isEmpty()) {
				final Event event = FBLaunchConfigurationAttributes.getEvent(configuration, fbType, events.get(0));
				eventCombo.setSelection(new StructuredSelection(event), true);
			}
			repeatEventCheckbox.setSelection(FBLaunchConfigurationAttributes.isRepeatEvent(configuration));
			keepDebuggerRunningCheckbox
					.setSelection(FBLaunchConfigurationAttributes.isKeepRunningWhenIdle(configuration));
			clockWidget.setSelectedClockMode(FBLaunchConfigurationAttributes.getClockMode(configuration));
			clockWidget.setClockIntervalText(FBLaunchConfigurationAttributes.getClockIntervalText(configuration));
			clockWidget
					.setRealtimeClockText(FBLaunchConfigurationAttributes.getClockRealtimeOffsetText(configuration));
			clockWidget.setMonotonicClockText(
					FBLaunchConfigurationAttributes.getClockMonotonicOffsetText(configuration));
		} catch (final CoreException e) {
			// ignore
		}
		initializeArgumentsFrom(configuration);
	}

	@Override
	public void performApply(final ILaunchConfigurationWorkingCopy configuration) {
		super.performApply(configuration);
		final Event event = (Event) eventCombo.getStructuredSelection().getFirstElement();
		if (event != null) {
			configuration.setAttribute(FBLaunchConfigurationAttributes.EVENT, getEventName(event));
		} else {
			configuration.removeAttribute(FBLaunchConfigurationAttributes.EVENT);
		}
		configuration.setAttribute(FBLaunchConfigurationAttributes.REPEAT_EVENT, repeatEventCheckbox.getSelection());
		configuration.setAttribute(FBLaunchConfigurationAttributes.KEEP_RUNNING_WHEN_IDLE,
				keepDebuggerRunningCheckbox.getSelection());

		final FBDebugClockMode clockMode = clockWidget.getSelectedClockMode();
		configuration.setAttribute(FBLaunchConfigurationAttributes.CLOCK_MODE, clockMode.toString());
		if (clockMode == FBDebugClockMode.INTERVAL) {
			configuration.setAttribute(FBLaunchConfigurationAttributes.CLOCK_INTERVAL, clockWidget.getClockIntervalText());
		}
		if (clockMode == FBDebugClockMode.INTERVAL || clockMode == FBDebugClockMode.FIXED) {
			configuration.setAttribute(FBLaunchConfigurationAttributes.CLOCK_REALTIME_OFFSET,
					clockWidget.getRealtimeClockText());
			configuration.setAttribute(FBLaunchConfigurationAttributes.CLOCK_MONOTONIC_OFFSET,
					clockWidget.getMonotonicClockText());
		}
	}

	@Override
	protected void handleResourceUpdated() {
		final FBType fbType = getFBType();
		// event
		final Event oldEvent = (Event) eventCombo.getStructuredSelection().getFirstElement();
		final List<Event> events = getInputEvents(fbType);
		eventCombo.setInput(events);
		if (!events.isEmpty()) {
			Event event;
			if (oldEvent != null) {
				event = events.stream().filter(e -> e.getName().equals(oldEvent.getName())).findFirst()
						.orElse(events.get(0));
			} else {
				event = events.get(0);
			}
			eventCombo.setSelection(new StructuredSelection(event), true);
		}
		// arguments
		updateArguments();
		super.handleResourceUpdated();
	}

	protected static List<Event> getInputEvents(final FBType fbType) {
		final List<Event> result = new ArrayList<>();
		if (fbType != null) {
			result.addAll(fbType.getInterfaceList().getEventInputs());
			Stream.concat(fbType.getInterfaceList().getSockets().stream(),
					fbType.getInterfaceList().getPlugs().stream()).map(AdapterDeclaration::getAdapterFB)
					.map(AdapterFB::getInterface).map(InterfaceList::getEventOutputs).forEachOrdered(result::addAll);
		}
		return result;
	}

	@Override
	protected List<Variable<?>> getDefaultArguments() throws CoreException {
		final FBType fbType = getFBType();
		if (fbType != null) {
			return FBLaunchConfigurationDelegate.getDefaultArguments(fbType);
		}
		return Collections.emptyList();
	}

	@Override
	protected boolean filterTargetResource(final IResource resource) throws CoreException {
		if (resource instanceof final IFile file && resource.getFileExtension() != null
				&& resource.getFileExtension().equalsIgnoreCase(TypeLibraryTags.FB_TYPE_FILE_ENDING)) {
			final var typeEntry = TypeLibraryManager.INSTANCE.getTypeEntryForFile(file);
			if (typeEntry != null) {
				final var libraryElement = typeEntry.getType();
				if (libraryElement instanceof final FBType fbType) {
					return filterTargetFBType(fbType);
				}
			}
		}
		return super.filterTargetResource(resource);
	}

	@Override
	public boolean isValid(final ILaunchConfiguration launchConfig) {
		final Optional<String> clockValid = clockWidget.validate();
		setErrorMessage(clockValid.orElse(null));
		return clockValid.isEmpty();
	}

	@SuppressWarnings("static-method") // subclasses may override
	protected boolean filterTargetFBType(final FBType fbType) {
		return fbType instanceof BasicFBType || fbType instanceof FunctionFBType || fbType instanceof SimpleFBType;
	}

	protected FBType getFBType() {
		final IResource resource = getResource();
		if (resource instanceof final IFile file) {
			final var typeEntry = TypeLibraryManager.INSTANCE.getTypeEntryForFile(file);
			if (typeEntry != null) {
				final var libraryElement = typeEntry.getType();
				if (libraryElement instanceof final FBType fbType) {
					return fbType;
				}
			}
		}
		return null;
	}

	protected static String getEventName(final Event event) {
		if (event.getFBNetworkElement() instanceof AdapterFB) {
			return event.getFBNetworkElement().getName() + "." + event.getName(); //$NON-NLS-1$
		}
		return event.getName();
	}

	private static class EventsLabelProvider extends LabelProvider {

		@Override
		public String getText(final Object element) {
			if (element instanceof final Event event) {
				return getEventName(event);
			}
			return super.getText(element);
		}

	}
}
