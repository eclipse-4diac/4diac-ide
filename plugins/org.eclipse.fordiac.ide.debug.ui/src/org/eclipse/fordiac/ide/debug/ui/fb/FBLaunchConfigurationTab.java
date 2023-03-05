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
package org.eclipse.fordiac.ide.debug.ui.fb;

import static org.eclipse.swt.events.SelectionListener.widgetSelectedAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.fordiac.ide.debug.fb.FBLaunchConfigurationAttributes;
import org.eclipse.fordiac.ide.debug.fb.FBLaunchConfigurationDelegate;
import org.eclipse.fordiac.ide.debug.ui.MainLaunchConfigurationTab;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
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
import org.eclipse.swt.widgets.Group;

public abstract class FBLaunchConfigurationTab extends MainLaunchConfigurationTab {

	private ComboViewer eventCombo;
	private Button repeatEventCheckbox;
	private Button keepDebuggerRunningCheckbox;

	@Override
	public void createControl(final Composite parent) {
		super.createControl(parent);

		final Composite eventComponent = createEventComponent((Composite) getControl());
		GridDataFactory.fillDefaults().grab(true, false).applyTo(eventComponent);

		final Composite argumentsComponent = createArgumentsComponent((Composite) getControl());
		GridDataFactory.fillDefaults().grab(true, true).applyTo(argumentsComponent);
	}

	@Override
	protected Composite createOptionsComponent(final Composite parent) {
		final Group group = (Group) super.createOptionsComponent(parent);
		final Composite comp = (Composite) group.getChildren()[0];

		repeatEventCheckbox = new Button(comp, SWT.CHECK);
		repeatEventCheckbox.setText("Repeat event"); //$NON-NLS-1$
		repeatEventCheckbox.addSelectionListener(widgetSelectedAdapter(e -> updateLaunchConfigurationDialog()));
		GridDataFactory.fillDefaults().applyTo(repeatEventCheckbox);

		keepDebuggerRunningCheckbox = new Button(comp, SWT.CHECK);
		keepDebuggerRunningCheckbox.setText("Keep debugger running when idle"); //$NON-NLS-1$
		keepDebuggerRunningCheckbox.addSelectionListener(widgetSelectedAdapter(e -> updateLaunchConfigurationDialog()));
		GridDataFactory.fillDefaults().applyTo(keepDebuggerRunningCheckbox);
		return group;
	}

	protected Composite createEventComponent(final Composite parent) {
		final Group group = new Group(parent, SWT.BORDER);
		GridLayoutFactory.swtDefaults().applyTo(group);
		group.setText("Event"); //$NON-NLS-1$

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
		if (resource instanceof IFile && resource.getFileExtension() != null
				&& resource.getFileExtension().equalsIgnoreCase(TypeLibraryTags.FB_TYPE_FILE_ENDING)) {
			final var typeEntry = TypeLibraryManager.INSTANCE.getTypeEntryForFile((IFile) resource);
			if (typeEntry != null) {
				final var libraryElement = typeEntry.getType();
				if (libraryElement instanceof FBType) {
					return filterTargetFBType((FBType) libraryElement);
				}
			}
		}
		return super.filterTargetResource(resource);
	}

	protected abstract boolean filterTargetFBType(FBType fbType) throws CoreException;

	protected FBType getFBType() {
		final IResource resource = getResource();
		if (resource instanceof IFile) {
			final var typeEntry = TypeLibraryManager.INSTANCE.getTypeEntryForFile((IFile) resource);
			if (typeEntry != null) {
				final var libraryElement = typeEntry.getType();
				if (libraryElement instanceof FBType) {
					return (FBType) libraryElement;
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
			if (element instanceof Event) {
				return getEventName((Event) element);
			}
			return super.getText(element);
		}

	}
}
