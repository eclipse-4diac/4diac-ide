/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz
 * 				 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr - create dedicated section for StructManipulator to improve
 *                     the usability of the struct handling
 *   Lukas Wais - reworked tree structure
 *   Michael Oberlehner  - refactored tree structure
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import org.eclipse.core.runtime.Assert;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.application.editparts.StructManipulatorEditPart;
import org.eclipse.fordiac.ide.gef.properties.AbstractSection;
import org.eclipse.fordiac.ide.model.CheckableStructTreeNode;
import org.eclipse.fordiac.ide.model.StructTreeNode;
import org.eclipse.fordiac.ide.model.StructTreeNode.StructTreeContentProvider;
import org.eclipse.fordiac.ide.model.StructTreeNode.StructTreeLabelProvider;
import org.eclipse.fordiac.ide.model.commands.change.ChangeStructCommand;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.ui.widgets.OpenStructMenu;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.MenuListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class StructManipulatorSection extends AbstractSection {

	protected CCombo muxStructSelector;
	protected CLabel muxLabel;
	protected TreeViewer memberVarViewer;
	protected Button openEditorButton;
	protected boolean initTree = true;

	@Override
	protected FBNetworkElement getInputType(final Object input) {
		if (input instanceof StructManipulatorEditPart) {
			return ((StructManipulatorEditPart) input).getModel();
		}
		if (input instanceof StructManipulator) {
			return ((StructManipulator) input);
		}
		return null;
	}

	@Override
	protected StructManipulator getType() {
		if (type instanceof StructManipulator) {
			return (StructManipulator) type;
		}
		return null;
	}

	private void disableOpenEditorForAnyType(final String newStructName) {
		openEditorButton.setEnabled(!"ANY_STRUCT".contentEquals(newStructName)); //$NON-NLS-1$
	}

	private void createStructSelector(final Composite composite) {
		final Composite structComp = getWidgetFactory().createComposite(composite);
		structComp.setLayout(new GridLayout(3, false));
		structComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		muxLabel = getWidgetFactory().createCLabel(structComp, Messages.StructManipulatorSection_STRUCTURED_TYPE);
		muxStructSelector = getWidgetFactory().createCCombo(structComp);

		muxStructSelector.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(final FocusEvent e) {
				fillStructTypeCombo();
			}

			@Override
			public void focusLost(final FocusEvent e) {
				// nothing to be done
			}
		});
		muxStructSelector.addListener(SWT.Selection, e -> handleStructSelectionChanged());

		openEditorButton = new Button(structComp, SWT.PUSH);
		openEditorButton.setText(FordiacMessages.OPEN_TYPE_EDITOR_MESSAGE);
		openEditorButton.addListener(SWT.Selection,
				e -> OpenStructMenu.openStructEditor(getType().getStructType().getPaletteEntry().getFile()));
	}

	private void handleStructSelectionChanged() {
		if (null != getType()) {
			final String newStructName = getNewStructName();
			disableOpenEditorForAnyType(newStructName);
			if (newStructSelected(newStructName)) {
				final StructuredType newStruct = getDataTypeLib().getStructuredType(newStructName);
				final ChangeStructCommand cmd = new ChangeStructCommand(getType(), newStruct);
				commandStack.execute(cmd);
				selectNewStructManipulatorFB(cmd.getNewMux());
				refresh();
			}
		}
	}

	public String getNewStructName() {
		final int index = muxStructSelector.getSelectionIndex();
		final String newStructName = muxStructSelector.getItem(index);
		return newStructName;
	}

	public boolean newStructSelected(final String newStructName) {
		return !newStructName.contentEquals(getType().getStructType().getName());
	}

	protected void selectNewStructManipulatorFB(final StructManipulator newMux) {
		final GraphicalViewer viewer = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getActiveEditor().getAdapter(GraphicalViewer.class);
		if (null != viewer) {
			viewer.flush();
			final Object obj = viewer.getEditPartRegistry().get(newMux);
			viewer.select((EditPart) obj);
			setInput(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor(),
					viewer.getSelection());
		}
	}

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		createSuperControls = false;
		super.createControls(parent, tabbedPropertySheetPage);
		parent.setLayout(new GridLayout(1, true));
		parent.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		createStructSelector(parent);
		final Group memberVarGroup = getWidgetFactory().createGroup(parent,
				Messages.StructManipulatorSection_Contained_variables);
		createMemberVariableViewer(memberVarGroup);
		memberVarGroup.setLayout(new GridLayout(1, true));
		memberVarGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	}

	private void createMemberVariableViewer(final Composite parent) {
		memberVarViewer = createTreeViewer(parent);
		configureTreeLayout(memberVarViewer);
		memberVarViewer.setContentProvider(new StructTreeContentProvider());
		memberVarViewer.setLabelProvider(new StructTreeLabelProvider());
		GridLayoutFactory.fillDefaults().generateLayout(parent);

		createContextMenu(memberVarViewer.getControl());
	}

	protected TreeViewer createTreeViewer(final Composite parent) {
		return new TreeViewer(parent);
	}

	private void createContextMenu(final Control ctrl) {
		final Menu openEditorMenu = new Menu(memberVarViewer.getTree());
		final MenuItem openItem = new MenuItem(openEditorMenu, SWT.NONE);
		openItem.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				final StructuredType sel = getSelectedStructuredType();
				if (sel != null) {
					OpenStructMenu.openStructEditor(sel.getPaletteEntry().getFile());
				}
			}

			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				widgetSelected(e);
			}
		});
		openItem.setText(FordiacMessages.OPEN_TYPE_EDITOR_MESSAGE);

		openEditorMenu.addMenuListener(new MenuListener() {
			@Override
			public void menuShown(final MenuEvent e) {
				final StructuredType type = getSelectedStructuredType();
				openItem.setEnabled((null != type) && !type.getName().contentEquals("ANY_STRUCT")); //$NON-NLS-1$
			}

			@Override
			public void menuHidden(final MenuEvent e) {
				// nothing to be done here
			}
		});
		ctrl.setMenu(openEditorMenu);
	}

	private StructuredType getSelectedStructuredType() {
		final StructTreeNode selected = (StructTreeNode) memberVarViewer.getTree().getSelection()[0].getData();
		final VarDeclaration varDecl = selected.getVariable();
		if (varDecl.getType() instanceof StructuredType) {
			return (StructuredType) varDecl.getType();
		}
		return null;
	}

	private static void configureTreeLayout(final TreeViewer viewer) {
		final TreeViewerColumn variableName = new TreeViewerColumn(viewer, SWT.LEFT);
		final TreeViewerColumn variableType = new TreeViewerColumn(viewer, SWT.LEFT);
		final TreeViewerColumn comment = new TreeViewerColumn(viewer, SWT.LEFT);
		viewer.getTree().setHeaderVisible(true);
		variableName.getColumn().setResizable(true);
		variableType.getColumn().setResizable(true);

		variableName.getColumn().setText(Messages.StructManipulatorSection_MEMBERVAR_COLUMN_NAME);
		variableType.getColumn().setText(Messages.StructManipulatorSection_MEMBERVAR_COLUMN_TYPE);
		comment.getColumn().setText(Messages.StructManipulatorSection_MEMBERVAR_COLUMN_COMMENT);
		variableName.getColumn().setWidth(200);
		variableType.getColumn().setWidth(100);
		comment.getColumn().setWidth(800);
	}

	@Override
	public void refresh() {
		if ((null != getType()) && (null != getType().getFbNetwork()) && !blockRefresh) {
			fillStructTypeCombo();
		}
	}

	private void fillStructTypeCombo() {
		memberVarViewer.setInput(getType());
		final String structName = getType().getStructType().getName();
		muxStructSelector.removeAll();
		for (final StructuredType dtp : getDataTypeLib().getStructuredTypesSorted()) {
			muxStructSelector.add(dtp.getName());
			if (dtp.getName().contentEquals(structName)) {
				muxStructSelector.select(muxStructSelector.getItemCount() - 1);
			}
		}
	}

	@Override
	public void setInput(final IWorkbenchPart part, final ISelection selection) {
		Assert.isTrue(selection instanceof IStructuredSelection);
		final Object input = ((IStructuredSelection) selection).getFirstElement();
		commandStack = getCommandStack(part, input);
		if (null == commandStack) { // disable all fields
			muxLabel.setEnabled(false);
			muxStructSelector.setEnabled(false);
			memberVarViewer.setInput(null);
		}
		setType(input);
		if (initTree) {
			final StructTreeNode node = initTree(getType(), memberVarViewer);
			((StructTreeContentProvider) memberVarViewer.getContentProvider()).setRoot(node);

		}

		disableOpenEditorForAnyType(getType().getStructType().getName());
	}

	public StructTreeNode initTree(final StructManipulator struct, final TreeViewer viewer) {
		final StructuredType structuredType = struct.getPaletteEntry().getTypeLibrary().getDataTypeLibrary()
				.getStructuredType(struct.getStructType().getName());

		final StructTreeNode root = CheckableStructTreeNode.initTree(struct, structuredType);
		if (viewer != null) {
			root.setViewer(viewer);
		}

		return root;
	}



	@Override
	protected void setInputCode() {
		// Currently nothing needs to be done here
	}

	@Override
	protected void setInputInit() {
		// Currently nothing needs to be done here
	}
}