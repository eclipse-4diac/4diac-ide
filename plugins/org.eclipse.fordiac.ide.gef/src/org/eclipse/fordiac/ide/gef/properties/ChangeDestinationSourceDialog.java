/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.properties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.fordiac.ide.gef.Messages;
import org.eclipse.fordiac.ide.model.commands.change.ReconnectAdapterConnectionCommand;
import org.eclipse.fordiac.ide.model.commands.change.ReconnectDataConnectionCommand;
import org.eclipse.fordiac.ide.model.commands.change.ReconnectEventConnectionCommand;
import org.eclipse.fordiac.ide.model.commands.create.AbstractConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.AdapterConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.DataConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.EventConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteConnectionCommand;
import org.eclipse.fordiac.ide.model.data.provider.DataItemProviderAdapterFactory;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterConnection;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.DataConnection;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.EventConnection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.provider.LibraryElementItemProviderAdapterFactory;
import org.eclipse.fordiac.ide.model.validation.LinkConstraints;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.widgets.LabelFactory;
import org.eclipse.jface.widgets.WidgetFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IEditorPart;

public class ChangeDestinationSourceDialog extends MessageDialog {

	private static final int NUMBER_OF_COLLUMNS = 1;
	private static final int TABLE_COL_WIDTH = 150;
	private static final String[] BUTTON_LABELS = { SWT.getMessage("SWT_Cancel") }; //$NON-NLS-1$

	private Connection connection;
	private final IInterfaceElement ieToChange;
	private IInterfaceElement ie;

	private static TreeViewer treeViewer;

	private ColumnLabelProvider labelTarget;
	private ColumnLabelProvider labelPin;
	private ColumnLabelProvider labelComment;

	private ComposedAdapterFactory adapterFactory;
	private CommandStack commandStack = null;

	enum Case {
		OUTPUT_NOSUBAPP, OUTPUT_SUBAPP, INPUT_NOSUBAPP, INPUT_SUBAPP
	}

	public ChangeDestinationSourceDialog(final Shell parentShell, final Connection con, final IInterfaceElement ie) {
		super(parentShell, Messages.InterfaceElementSection_MessageDialog_TITLE, null, "", MessageDialog.NONE, //$NON-NLS-1$
				0, BUTTON_LABELS);
		this.connection = con;
		this.ieToChange = ie;
		if (con.getSource().equals(ie)) {
			this.ie = connection.getDestination();
		} else {
			this.ie = connection.getSource();
		}
	}

	void setConnection(final Connection con) {
		this.connection = con;
	}

	void setInterfaceElement(final IInterfaceElement ie) {
		this.ie = ie;
	}

	@Override
	protected Control createCustomArea(final Composite parent) {
		parent.setLayout(new GridLayout(NUMBER_OF_COLLUMNS, true));

		final Composite fbResultArea = WidgetFactory.composite(NONE).create(parent);
		fbResultArea.setLayout(new GridLayout(1, true));
		fbResultArea.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		final List<IInterfaceElement> result = createInputSet();

		if (result.isEmpty()) {
			// No results - display just the info
			final Label warningLabel = LabelFactory.newLabel(NONE).create(fbResultArea);
			warningLabel.setText("No Interface Elements found"); //$NON-NLS-1$
		} else {
			treeViewer = createTreeViewer(fbResultArea);
			configureTableViewer(treeViewer);
			treeViewer.setInput(result.toArray());
			GridLayoutFactory.fillDefaults().generateLayout(fbResultArea);
		}
		return parent;
	}

	private List<IInterfaceElement> createInputSet() {
		final IEditorPart currentActiveEditor = EditorUtils.getCurrentActiveEditor();
		final GraphicalViewer viewer = currentActiveEditor.getAdapter(GraphicalViewer.class);

		if (viewer != null) {
			commandStack = viewer.getEditDomain().getCommandStack();
		}

		if (connection != null && ie != null && connection.getFBNetwork() != null
				&& connection.getFBNetwork().getNetworkElements() != null) {
			final List<InterfaceList> possibleInterfaceLists;

			if (connection.getFBNetwork().isSubApplicationNetwork()
					&& connection.getFBNetwork().eContainer() instanceof final SubApp subapp) {
				possibleInterfaceLists = getPossibleInterfaceLists(connection.getFBNetwork().getNetworkElements(),
						subapp);
			} else {
				possibleInterfaceLists = getPossibleInterfaceLists(connection.getFBNetwork().getNetworkElements(),
						null);
			}

			if (!possibleInterfaceLists.isEmpty()) {
				if (connection instanceof DataConnection) {
					return getVarDeclarationsFromInterfaceLists(possibleInterfaceLists, ie.isIsInput());
				}
				if (connection instanceof EventConnection) {
					return getEventFromInterfaceLists(possibleInterfaceLists, ie.isIsInput());
				}
				if (connection instanceof AdapterConnection) {
					return getAdapterFromInterfaceLists(possibleInterfaceLists, ie.isIsInput());
				}
			}
		}
		return Collections.emptyList();
	}

	private List<InterfaceList> getPossibleInterfaceLists(final List<FBNetworkElement> fbs, final SubApp subapp) {
		final List<InterfaceList> possibleInterfaceLists = new ArrayList<>(
				fbs.stream().filter(currentFb -> !currentFb.getName().equals(ie.getFBNetworkElement().getName()))
						.toList().stream().map(FBNetworkElement::getInterface).toList());

		if (subapp != null && !((ie.eContainer().eContainer() instanceof final SubApp subappContainer)
				&& subappContainer.equals(subapp))) {
			possibleInterfaceLists.add(subapp.getInterface());
		}
		return possibleInterfaceLists;
	}

	// Returns the input or output swapped if needed for interfaceElements of Subapps
	private boolean inputSwap(final boolean input, final InterfaceList ieList) {
		if (ie.getFBNetworkElement() instanceof final SubApp subapp && !subapp.isTyped()) {
			if (subapp.getSubAppNetwork().getNetworkElements().contains(ieList.getFBNetworkElement())) {
				return !input;
			}

			if (subapp.getOuterFBNetworkElement() instanceof final SubApp outsideSubApp && !outsideSubApp.isTyped()
					&& !outsideSubApp.getSubAppNetwork().getNetworkElements().contains(ieList.getFBNetworkElement())) {
				return !input;
			}
			return input;
		}

		if (ieList.getFBNetworkElement() instanceof final SubApp subapp && !subapp.isTyped()) {
			if (subapp.getSubAppNetwork().getNetworkElements().contains(ie.getFBNetworkElement())) {
				return !input;
			}
			return input;
		}
		return input;
	}

	private List<IInterfaceElement> getVarDeclarationsFromInterfaceLists(final List<InterfaceList> ieLists,
			final boolean input) {
		final List<IInterfaceElement> varDec = new ArrayList<>();
		for (final InterfaceList ieList : ieLists) {
			if (inputSwap(input, ieList)) {
				varDec.addAll(ieList.getOutputVars().stream().filter(newIE -> newIE != ieToChange)
						.filter(newIE -> LinkConstraints.typeCheck(ie, newIE)).toList());
			} else {

				varDec.addAll(ieList.getInputVars().stream().filter(newIE -> newIE != ieToChange)
						.filter(newIE -> LinkConstraints.typeCheck(ie, newIE))
						.filter(newIE -> newIE.getInputConnections().isEmpty()).toList());
			}
		}
		return varDec;

	}

	private List<IInterfaceElement> getEventFromInterfaceLists(final List<InterfaceList> ieLists, final boolean input) {
		final List<IInterfaceElement> event = new ArrayList<>();
		for (final InterfaceList ieList : ieLists) {
			if (inputSwap(input, ieList)) {
				event.addAll(ieList.getEventOutputs().stream().filter(newIE -> newIE != ieToChange).toList());
			} else {
				event.addAll(ieList.getEventInputs().stream().filter(newIE -> newIE != ieToChange).toList());
			}
		}
		return event;
	}

	private List<IInterfaceElement> getAdapterFromInterfaceLists(final List<InterfaceList> ieLists,
			final boolean input) {
		final List<IInterfaceElement> adapter = new ArrayList<>();
		for (final InterfaceList ieList : ieLists) {

			if (inputSwap(input, ieList)) {
				adapter.addAll(ieList.getPlugs().stream().filter(newIE -> newIE != ieToChange)
						.filter(newIE -> newIE.getOutputConnections().isEmpty()).toList());
			} else {
				adapter.addAll(ieList.getSockets().stream().filter(newIE -> newIE != ieToChange)
						.filter(newIE -> newIE.getInputConnections().isEmpty()).toList());
			}
		}
		return adapter;
	}

	private static TreeViewer createTreeViewer(final Composite parent) {
		return new TreeViewer(parent, SWT.FULL_SELECTION | SWT.BORDER);
	}

	private void configureTableViewer(final TreeViewer viewer) {
		createLabelProviders();
		viewer.setContentProvider(new ITreeContentProvider() {

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

		});

		final Tree table = viewer.getTree();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setLayout(createTableLayout());
		table.setSortDirection(SWT.DOWN);

		// Target column
		final TreeViewerColumn colTarget = new TreeViewerColumn(viewer, SWT.LEAD);
		colTarget.getColumn().setText(FordiacMessages.Target);
		colTarget.setLabelProvider(labelTarget);

		table.setSortColumn(colTarget.getColumn());

		// Pin name column
		final TreeViewerColumn colPin = new TreeViewerColumn(viewer, SWT.LEAD);
		colPin.getColumn().setText(FordiacMessages.Pin);
		colPin.setLabelProvider(labelPin);

		// Comment name column
		final TreeViewerColumn colName = new TreeViewerColumn(viewer, SWT.LEAD);
		colName.getColumn().setText(FordiacMessages.Comment);
		colName.setLabelProvider(labelComment);

		treeViewer.addDoubleClickListener(new DoubleClickListener());

	}

	private void createLabelProviders() {

		final AdapterFactoryLabelProvider labelProvider = new AdapterFactoryLabelProvider(getAdapterFactory());

		this.labelTarget = new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				if (element instanceof final IInterfaceElement ieElement) {
					return ieElement.getFBNetworkElement().getName();
				}
				return super.getText(element);
			}

			@Override
			public Image getImage(final Object element) {

				if (element instanceof final IInterfaceElement ieElement) {
					return labelProvider.getImage(ieElement.getFBNetworkElement());
				}
				return super.getImage(element);
			}
		};
		this.labelPin = new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				if (element instanceof final IInterfaceElement ieElement) {
					return ieElement.getName();
				}
				return super.getText(element);
			}

			@Override
			public Image getImage(final Object element) {

				if (element instanceof final IInterfaceElement ieElement) {
					return labelProvider.getImage(ieElement);
				}
				return super.getImage(element);
			}
		};
		this.labelComment = new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				if (element instanceof final IInterfaceElement ieElement) {
					return ieElement.getComment();
				}
				return super.getText(element);
			}
		};
	}

	private final ComposedAdapterFactory getAdapterFactory() {
		if (adapterFactory == null) {
			adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
			adapterFactory.addAdapterFactory(new ResourceItemProviderAdapterFactory());
			adapterFactory.addAdapterFactory(new LibraryElementItemProviderAdapterFactory());
			adapterFactory.addAdapterFactory(new DataItemProviderAdapterFactory());
			adapterFactory.addAdapterFactory(new EcoreItemProviderAdapterFactory());
			adapterFactory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());
		}
		return adapterFactory;
	}

	private static TableLayout createTableLayout() {
		final TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnPixelData(TABLE_COL_WIDTH));
		layout.addColumnData(new ColumnPixelData(TABLE_COL_WIDTH));
		layout.addColumnData(new ColumnPixelData(TABLE_COL_WIDTH));
		return layout;
	}

	private class DoubleClickListener implements IDoubleClickListener {
		@Override
		public void doubleClick(final DoubleClickEvent event) {
			final IStructuredSelection selection = (IStructuredSelection) event.getSelection();
			if (selection == null || selection.isEmpty()) {
				return;
			}

			if (selection.getFirstElement() instanceof final IInterfaceElement ieElement) {
				if (ieElement.isIsInput() == ie.isIsInput()) {
					createNewConnectionCommand(ieElement);
				} else {
					createReconnectConnectionCommand(ieElement);
				}
			}
			close();
		}
	}

	private void createReconnectConnectionCommand(final IInterfaceElement pinSelection) {

		Command cmdCommand = null;

		if (pinSelection instanceof final VarDeclaration varDec) {
			cmdCommand = new ReconnectDataConnectionCommand(connection, connection.getSource().equals(ieToChange),
					varDec, ie.getFBNetworkElement().getFbNetwork());
		}

		if (pinSelection instanceof final Event eventPin) {
			cmdCommand = new ReconnectEventConnectionCommand(connection, connection.getSource().equals(ieToChange),
					eventPin, ie.getFBNetworkElement().getFbNetwork());
		}

		if (pinSelection instanceof final AdapterDeclaration adapter) {
			cmdCommand = new ReconnectAdapterConnectionCommand(connection, connection.getSource().equals(ieToChange),
					adapter, ie.getFBNetworkElement().getFbNetwork());
		}

		if (cmdCommand != null && cmdCommand.canExecute() && commandStack != null) {
			commandStack.execute(cmdCommand);
		}
	}

	private void createNewConnectionCommand(final IInterfaceElement pinSelection) {

		AbstractConnectionCreateCommand createCommand = null;

		if (pinSelection instanceof VarDeclaration) {
			createCommand = new DataConnectionCreateCommand(connection.getFBNetwork());
		}

		if (pinSelection instanceof Event) {
			createCommand = new EventConnectionCreateCommand(connection.getFBNetwork());
		}

		if (pinSelection instanceof AdapterDeclaration) {
			createCommand = new AdapterConnectionCreateCommand(connection.getFBNetwork());
		}

		final CompoundCommand cmd = new CompoundCommand();
		cmd.add(new DeleteConnectionCommand(connection));

		if (createCommand != null && cmd.canExecute() && commandStack != null) {
			if (ie.isIsInput()) {
				createCommand.setSource(pinSelection);
				createCommand.setDestination(ie);
			} else {
				createCommand.setDestination(pinSelection);
				createCommand.setSource(ie);
			}
			cmd.add(createCommand);
			commandStack.execute(cmd);
		}
	}

}
