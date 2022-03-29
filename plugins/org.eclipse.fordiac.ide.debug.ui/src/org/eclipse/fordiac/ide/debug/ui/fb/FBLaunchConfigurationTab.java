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

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.fordiac.ide.debug.fb.FBLaunchConfigurationAttributes;
import org.eclipse.fordiac.ide.debug.ui.MainLaunchConfigurationTab;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.eval.variable.VariableOperations;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

public abstract class FBLaunchConfigurationTab extends MainLaunchConfigurationTab {

	private ComboViewer eventCombo;

	@Override
	public void createControl(final Composite parent) {
		super.createControl(parent);

		final Composite eventComponent = createEventComponent((Composite) getControl());
		GridDataFactory.fillDefaults().grab(true, false).applyTo(eventComponent);

		final Composite argumentsComponent = createArgumentsComponent((Composite) getControl());
		GridDataFactory.fillDefaults().grab(true, true).applyTo(argumentsComponent);
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
			configuration.setAttribute(FBLaunchConfigurationAttributes.EVENT, event.getName());
		} else {
			configuration.removeAttribute(FBLaunchConfigurationAttributes.EVENT);
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
		if (fbType != null) {
			return fbType.getInterfaceList().getEventInputs();
		}
		return Collections.emptyList();
	}

	@Override
	protected List<Variable> getDefaultArguments() {
		final FBType fbType = getFBType();
		if (fbType != null) {
			return fbType.getInterfaceList().getInputVars().stream().map(VariableOperations::newVariable)
					.collect(Collectors.toList());
		}
		return Collections.emptyList();
	}

	@Override
	protected boolean filterTargetResource(final IResource resource) throws CoreException {
		if (resource instanceof IFile) {
			if (resource.getFileExtension().equalsIgnoreCase(TypeLibraryTags.FB_TYPE_FILE_ENDING)) {
				final var paletteEntry = TypeLibrary.getPaletteEntryForFile((IFile) resource);
				if (paletteEntry != null) {
					final var libraryElement = paletteEntry.getType();
					if (libraryElement instanceof FBType) {
						return filterTargetFBType((FBType) libraryElement);
					}
				}
			}
		}
		return super.filterTargetResource(resource);
	}

	protected abstract boolean filterTargetFBType(FBType fbType) throws CoreException;

	protected FBType getFBType() {
		final IResource resource = getResource();
		if (resource instanceof IFile) {
			final var paletteEntry = TypeLibrary.getPaletteEntryForFile((IFile) resource);
			if (paletteEntry != null) {
				final var libraryElement = paletteEntry.getType();
				if (libraryElement instanceof FBType) {
					return (FBType) libraryElement;
				}
			}
		}
		return null;
	}

	private static class EventsLabelProvider extends LabelProvider {

		@Override
		public String getText(final Object element) {
			if (element instanceof Event) {
				final Event event = (Event) element;
				return event.getName();
			}
			return super.getText(element);
		}

	}
}
