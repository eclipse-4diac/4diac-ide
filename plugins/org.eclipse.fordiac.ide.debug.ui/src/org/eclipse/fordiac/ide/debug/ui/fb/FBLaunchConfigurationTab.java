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
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.fordiac.ide.debug.fb.FBLaunchConfigurationAttributes;
import org.eclipse.fordiac.ide.debug.ui.MainLaunchConfigurationTab;
import org.eclipse.fordiac.ide.model.eval.variable.ElementaryVariable;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Tree;

public abstract class FBLaunchConfigurationTab extends MainLaunchConfigurationTab {

	private ComboViewer eventCombo;
	private TreeViewer argumentsTable;

	private List<Variable> arguments;

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
		group.setText("Event");

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

	protected Composite createArgumentsComponent(final Composite parent) {
		final Group group = new Group(parent, SWT.BORDER);
		GridLayoutFactory.swtDefaults().applyTo(group);
		group.setText("Arguments");

		final Composite comp = new Composite(group, SWT.NONE);
		GridLayoutFactory.swtDefaults().numColumns(1).applyTo(comp);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(comp);

		argumentsTable = new TreeViewer(comp,
				SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI | SWT.FULL_SELECTION);
		final Tree tree = argumentsTable.getTree();
		GridDataFactory.fillDefaults().grab(true, true).applyTo(tree);
		tree.setHeaderVisible(true);
		tree.setLinesVisible(true);
		argumentsTable.setContentProvider(new ArgumentsContentProvider());

		final TreeViewerColumn nameColumn = new TreeViewerColumn(argumentsTable, SWT.NONE);
		nameColumn.getColumn().setText("Name");
		nameColumn.getColumn().setWidth(300);
		nameColumn.setLabelProvider(new DelegatingStyledCellLabelProvider(new ArgumentsNameLabelProvider()));

		final TreeViewerColumn valueColumn = new TreeViewerColumn(argumentsTable, SWT.NONE);
		valueColumn.getColumn().setText("Value");
		valueColumn.getColumn().setWidth(300);
		valueColumn.setLabelProvider(new DelegatingStyledCellLabelProvider(new ArgumentsValueLabelProvider()));
		valueColumn.setEditingSupport(new ArgumentsValueEditingSupport(argumentsTable));

		return group;
	}

	@Override
	public void setDefaults(final ILaunchConfigurationWorkingCopy configuration) {
		super.setDefaults(configuration);
		configuration.removeAttribute(FBLaunchConfigurationAttributes.EVENT);
		configuration.removeAttribute(FBLaunchConfigurationAttributes.ARGUMENTS);
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
			arguments = FBLaunchConfigurationAttributes.getArguments(configuration, getDefaultArguments(fbType));
			argumentsTable.setInput(arguments);
		} catch (final CoreException e) {
			// ignore
		}
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
		if (arguments != null) {
			configuration.setAttribute(FBLaunchConfigurationAttributes.ARGUMENTS, arguments.stream()
					.collect(Collectors.toMap(Variable::getName, variable -> variable.getValue().toString())));
		} else {
			configuration.removeAttribute(FBLaunchConfigurationAttributes.ARGUMENTS);
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
		final var oldArguments = arguments;
		arguments = getDefaultArguments(fbType);
		if (oldArguments != null && arguments != null) {
			arguments.forEach(variable -> oldArguments.stream()
					.filter(arg -> Objects.equals(arg.getName(), variable.getName())).findFirst().ifPresent(arg -> {
						try {
							variable.setValue(arg.getValue().toString());
						} catch (final Exception e) {
							// ignore
						}
					}));
		}
		argumentsTable.setInput(arguments);
		updateLaunchConfigurationDialog();
	}

	protected static List<Event> getInputEvents(final FBType fbType) {
		if (fbType != null) {
			return fbType.getInterfaceList().getEventInputs();
		}
		return Collections.emptyList();
	}

	protected static List<Variable> getDefaultArguments(final FBType fbType) {
		if (fbType != null) {
			return fbType.getInterfaceList().getInputVars().stream().map(ElementaryVariable::new)
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
			return false;
		} else if (resource instanceof IContainer) {
			for (final IResource child : ((IContainer) resource).members()) {
				if (filterTargetResource(child)) {
					return true;
				}
			}
			return false;
		}
		return true;
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

	public static class ArgumentsContentProvider implements ITreeContentProvider {

		@Override
		public Object[] getElements(final Object inputElement) {
			return ArrayContentProvider.getInstance().getElements(inputElement);
		}

		@Override
		public Object[] getChildren(final Object parentElement) {
			return new Object[0];
		}

		@Override
		public Object getParent(final Object element) {
			return null;
		}

		@Override
		public boolean hasChildren(final Object element) {
			return false;
		}
	}

	private static class ArgumentsNameLabelProvider extends LabelProvider implements IStyledLabelProvider {

		@Override
		public StyledString getStyledText(final Object element) {
			if (element instanceof Variable) {
				final Variable variable = (Variable) element;
				return new StyledString(variable.getName());
			}
			return null;
		}

	}

	private static class ArgumentsValueLabelProvider extends LabelProvider implements IStyledLabelProvider {

		@Override
		public StyledString getStyledText(final Object element) {
			if (element instanceof Variable) {
				final Variable variable = (Variable) element;
				return new StyledString(variable.getValue().toString());
			}
			return null;
		}

	}

	private class ArgumentsValueEditingSupport extends EditingSupport {
		private final CellEditor editor;

		private ArgumentsValueEditingSupport(final TreeViewer viewer) {
			super(viewer);
			this.editor = new TextCellEditor(viewer.getTree());
		}

		@Override
		protected CellEditor getCellEditor(final Object element) {
			return editor;
		}

		@Override
		protected boolean canEdit(final Object element) {
			return element instanceof Variable;
		}

		@Override
		protected Object getValue(final Object element) {
			if (element instanceof Variable) {
				return ((Variable) element).getValue().toString();
			}
			return null;
		}

		@Override
		protected void setValue(final Object element, final Object value) {
			if (element instanceof Variable) {
				final Variable variable = (Variable) element;
				try {
					variable.setValue(value.toString());
				} catch (final Throwable t) {
					MessageDialog.openError(getViewer().getControl().getShell(), "Invalid Value",
							String.format("'%s' is not a valid value for variable %s with type %s", value.toString(),
									variable.getName(), variable.getType().getName()));
				}
				getViewer().update(element, null);
				FBLaunchConfigurationTab.this.updateLaunchConfigurationDialog();
			}
		}
	}
}
