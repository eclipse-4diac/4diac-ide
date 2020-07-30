/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import org.eclipse.core.runtime.Assert;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.application.editparts.StructManipulatorEditPart;
import org.eclipse.fordiac.ide.gef.properties.AbstractSection;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.fordiac.ide.model.ui.widgets.OpenStructMenu;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
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
import org.eclipse.swt.graphics.Image;
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

	@Override
	protected FBNetworkElement getInputType(Object input) {
		if (input instanceof StructManipulatorEditPart) {
			return ((StructManipulatorEditPart) input).getModel();
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

	private void disableOpenEditorForAnyType(String newStructName) {
		openEditorButton.setEnabled(!"ANY_STRUCT".contentEquals(newStructName)); //$NON-NLS-1$
	}

	private void createStructSelector(Composite composite) {
		Composite structComp = getWidgetFactory().createComposite(composite);
		structComp.setLayout(new GridLayout(3, false));
		structComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		muxLabel = getWidgetFactory().createCLabel(structComp, Messages.StructManipulatorSection_STRUCTURED_TYPE);
		muxStructSelector = getWidgetFactory().createCCombo(structComp);

		muxStructSelector.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				fillStructTypeCombo();
			}

			@Override
			public void focusLost(FocusEvent e) {
			}
		});
		muxStructSelector.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (null != getType()) {
					int index = muxStructSelector.getSelectionIndex();
					String newStructName = muxStructSelector.getItem(index);
					disableOpenEditorForAnyType(newStructName);
					boolean newStructSelected = !newStructName.contentEquals(getType().getStructType().getName());
					if (newStructSelected && (null != getDatatypeLibrary())) {
						StructuredType newStruct = getDatatypeLibrary().getStructuredType(newStructName);
						ChangeStructCommand cmd = new ChangeStructCommand(getType(), newStruct);
						commandStack.execute(cmd);
						selectNewStructManipulatorFB(cmd);
						refresh();
					}
				}
			}

			private void selectNewStructManipulatorFB(ChangeStructCommand cmd) {
				GraphicalViewer viewer = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
						.getActiveEditor().getAdapter(GraphicalViewer.class);
				if (null != viewer) {
					viewer.flush();
					Object obj = viewer.getEditPartRegistry().get(cmd.getNewMux());
					viewer.select((EditPart) obj);
					setInput(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor(),
							viewer.getSelection());
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		openEditorButton = new Button(structComp, SWT.PUSH);
		openEditorButton.setText(FordiacMessages.OPEN_TYPE_EDITOR_MESSAGE);
		openEditorButton.addListener(SWT.Selection,
				e -> OpenStructMenu.openStructEditor(getType().getStructType().getPaletteEntry().getFile()));
	}

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		createSuperControls = false;
		super.createControls(parent, tabbedPropertySheetPage);
		parent.setLayout(new GridLayout(1, true));
		parent.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		createStructSelector(parent);
		Group memberVarGroup = getWidgetFactory().createGroup(parent,
				Messages.StructManipulatorSection_Contained_variables);
		createMemberVariableViewer(memberVarGroup);
		memberVarGroup.setLayout(new GridLayout(1, true));
		memberVarGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	}

	private void createMemberVariableViewer(Composite parent) {
		memberVarViewer = createTreeViewer(parent);
		configureTreeLayout(memberVarViewer);
		memberVarViewer.setContentProvider(new TreeContentProvider());
		memberVarViewer.setLabelProvider(new TreeLabelProvider());
		GridLayoutFactory.fillDefaults().generateLayout(parent);

		createContextMenu(memberVarViewer.getControl());
	}

	protected TreeViewer createTreeViewer(Composite parent) {
		return new TreeViewer(parent);
	}

	private void createContextMenu(Control ctrl) {
		Menu openEditorMenu = new Menu(memberVarViewer.getTree());
		MenuItem openItem = new MenuItem(openEditorMenu, SWT.NONE);
		openItem.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				StructuredType sel = getSelectedStructuredType();
				if (sel != null) {
					OpenStructMenu.openStructEditor(sel.getPaletteEntry().getFile());
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		openItem.setText(FordiacMessages.OPEN_TYPE_EDITOR_MESSAGE);

		openEditorMenu.addMenuListener(new MenuListener() {
			@Override
			public void menuShown(MenuEvent e) {
				openItem.setEnabled((getSelectedStructuredType() != null)
						&& !getSelectedStructuredType().getName().contentEquals("ANY_STRUCT")); //$NON-NLS-1$
			}

			@Override
			public void menuHidden(MenuEvent e) {
			}
		});
		ctrl.setMenu(openEditorMenu);
	}

	private StructuredType getSelectedStructuredType() {
		TreeNode selected = (TreeNode) memberVarViewer.getTree().getSelection()[0].getData();
		VarDeclaration varDecl = selected.getVariable();
		if (varDecl.getType() instanceof StructuredType) {
			return (StructuredType) varDecl.getType();
		}
		return null;
	}

	private static void configureTreeLayout(TreeViewer viewer) {
		TreeViewerColumn col1 = new TreeViewerColumn(viewer, SWT.LEFT);
		TreeViewerColumn col2 = new TreeViewerColumn(viewer, SWT.LEFT);
		TreeViewerColumn col3 = new TreeViewerColumn(viewer, SWT.LEFT);

		col1.getColumn().setText(Messages.StructManipulatorSection_MEMBERVAR_COLUMN_NAME);
		col2.getColumn().setText(Messages.StructManipulatorSection_MEMBERVAR_COLUMN_TYPE);
		col3.getColumn().setText(Messages.StructManipulatorSection_MEMBERVAR_COLUMN_COMMENT);

		col1.getColumn().setWidth(200);
		col2.getColumn().setWidth(100);
		col3.getColumn().setWidth(800);
	}

	@Override
	public void refresh() {
		if ((null != getType()) && (null != getType().getFbNetwork())) {
			fillStructTypeCombo();
		}
	}

	private void fillStructTypeCombo() {
		memberVarViewer.setInput(getType());
		String structName = getType().getStructType().getName();
		muxStructSelector.removeAll();
		for (StructuredType dtp : getDatatypeLibrary().getStructuredTypesSorted()) {
			muxStructSelector.add(dtp.getName());
			if (dtp.getName().contentEquals(structName)) {
				muxStructSelector.select(muxStructSelector.getItemCount() - 1);
			}
		}
	}

	@Override
	public void setInput(IWorkbenchPart part, ISelection selection) {
		Assert.isTrue(selection instanceof IStructuredSelection);
		Object input = ((IStructuredSelection) selection).getFirstElement();
		commandStack = getCommandStack(part, input);
		if (null == commandStack) { // disable all fields
			muxLabel.setEnabled(false);
			muxStructSelector.setEnabled(false);
			memberVarViewer.setInput(null);
		}
		setType(input);
		disableOpenEditorForAnyType(getType().getStructType().getName());
	}

	private DataTypeLibrary getDatatypeLibrary() {
		try {
			return getType().getFbNetwork().getAutomationSystem().getPalette().getTypeLibrary().getDataTypeLibrary();
		} catch (NullPointerException e) {
			return null;
		}
	}

	public static class TreeContentProvider implements ITreeContentProvider {
		@Override
		public Object[] getElements(final Object inputElement) {
			if (inputElement instanceof StructManipulator) {
				return getMemberVariableNodes(((StructManipulator) inputElement).getStructType(), null);
			}
			if (inputElement instanceof StructuredType) {
				return getMemberVariableNodes((StructuredType) inputElement, null);
			}
			return new Object[] {};
		}

		private static Object[] getMemberVariableNodes(final StructuredType struct, final String path) {
			return struct.getMemberVariables().stream().map(var -> new TreeNode(var, var.getName(), path)).toArray();
		}

		@Override
		public Object[] getChildren(Object parentElement) {
			VarDeclaration parentVar = ((TreeNode) parentElement).getVariable();
			return getMemberVariableNodes((StructuredType) parentVar.getType(),
					((TreeNode) parentElement).getPathName());
		}

		@Override
		public Object getParent(Object element) {
			return null;
		}

		@Override
		public boolean hasChildren(Object element) {
			return ((TreeNode) element).getVariable().getType() instanceof StructuredType;
		}
	}

	public static class TreeLabelProvider extends LabelProvider implements ITableLabelProvider {
		@Override
		public Image getColumnImage(final Object element, final int columnIndex) {
			return null;
		}

		@Override
		public String getColumnText(final Object element, final int columnIndex) {
			if (element instanceof TreeNode) {
				VarDeclaration var = ((TreeNode) element).getVariable();
				switch (columnIndex) {
				case 0:
					return var.getName();
				case 1:
					return var.getTypeName();
				case 2:
					return var.getComment();
				default:
					break;
				}
			}
			return element.toString();
		}
	}

	protected static class TreeNode {
		private VarDeclaration variable;
		private String parentVarName;
		private String pathName;

		public TreeNode(VarDeclaration variable, String parentVarName, String pathName) {
			this.variable = variable;
			this.parentVarName = parentVarName;
			this.pathName = pathName == null ? variable.getName() : (pathName + "." + variable.getName()); //$NON-NLS-1$
		}

		public String getParentVarName() {
			return parentVarName;
		}

		public VarDeclaration getVariable() {
			return variable;
		}

		public String getPathName() {
			return pathName;
		}
	}

	@Override
	protected void setInputCode() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setInputInit() {
		// TODO Auto-generated method stub

	}

}
