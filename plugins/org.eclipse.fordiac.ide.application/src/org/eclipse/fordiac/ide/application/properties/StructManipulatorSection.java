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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Assert;
import org.eclipse.fordiac.ide.application.editparts.StructManipulatorEditPart;
import org.eclipse.fordiac.ide.gef.properties.AbstractSection;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.resource.JFaceResources;
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
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.views.properties.tabbed.ISection;
import org.eclipse.ui.views.properties.tabbed.TabContents;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class StructManipulatorSection extends AbstractSection {

	protected CCombo muxStructSelector;
	protected CLabel muxLabel;
	private TreeViewer memberVarViewer;
	private Button openEditorButton;

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
		muxStructSelector.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (null != getType()) {
					int index = muxStructSelector.getSelectionIndex();
					String newStructName = muxStructSelector.getItem(index);
					disableOpenEditorForAnyType(newStructName);
					boolean newStructSelected = !newStructName.contentEquals(getType().getStructType().getName());
					if (newStructSelected && null != getDatatypeLibrary()) {
						StructuredType newStruct = (StructuredType) getDatatypeLibrary().getType(newStructName);
						ChangeStructCommand cmd = new ChangeStructCommand(getType(), newStruct);
						commandStack.execute(cmd);
						selectNewStructManipulatorFB(cmd);
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
		openEditorButton.setText(Messages.StructManipulatorSection_OPEN_IN_EDITOR_BUTTON);
		openEditorButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				openStructEditor(getType().getStructType().getPaletteEntry().getFile());
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
	}

	private static void openStructEditor(IFile file) {
		IWorkbench workbench = PlatformUI.getWorkbench();
		if (workbench != null) {
			IWorkbenchWindow activeWorkbenchWindow = workbench.getActiveWorkbenchWindow();
			if (activeWorkbenchWindow != null) {

				IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();
				IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(file.getName());
				try {
					activePage.openEditor(new FileEditorInput(file), desc.getId());
				} catch (PartInitException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		createSuperControls = false;
		super.createControls(parent, tabbedPropertySheetPage);
		parent.setLayout(new GridLayout(1, true));
		parent.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		createStructSelector(parent);
		Group memberVarGroup = getWidgetFactory().createGroup(parent, "Contained Variables");
		createMemberVariableViewer(memberVarGroup);
		memberVarGroup.setLayout(new GridLayout(1, true));
		memberVarGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	}

	private void createMemberVariableViewer(Composite parent) {
		memberVarViewer = new TreeViewer(parent);
		configureTreeLayout(memberVarViewer);
		memberVarViewer.setContentProvider(new TreeContentProvider());
		memberVarViewer.setLabelProvider(new TreeLabelProvider());
		GridLayoutFactory.fillDefaults().generateLayout(parent);

		createContextMenu(memberVarViewer.getControl());
	}

	private void createContextMenu(Control ctrl) {
		Menu openEditorMenu = new Menu(memberVarViewer.getTree());
		MenuItem openItem = new MenuItem(openEditorMenu, SWT.NONE);
		openItem.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				StructuredType sel = getSelectedStructuredType();
				if (sel != null) {					
					openStructEditor(sel.getPaletteEntry().getFile());
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		openItem.setText(JFaceResources.getString("Open Editor")); //$NON-NLS-1$
	
		openEditorMenu.addMenuListener(new MenuListener() {
			@Override
			public void menuShown(MenuEvent e) {
				openItem.setEnabled(getSelectedStructuredType() != null && 
						!getSelectedStructuredType().getName().contentEquals("ANY_STRUCT")); //$NON-NLS-1$
			}

			@Override
			public void menuHidden(MenuEvent e) {
			}
		});
		ctrl.setMenu(openEditorMenu);
	}

	private StructuredType getSelectedStructuredType() {
		TreeItem[] selected = memberVarViewer.getTree().getSelection();
		if (selected[0].getData() instanceof VarDeclaration) {
			VarDeclaration varDecl = (VarDeclaration) selected[0].getData();
			if (varDecl.getType() instanceof StructuredType) {
				return (StructuredType) varDecl.getType();
			}
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
		if (null != getType() && null != getType().getFbNetwork()) {
			memberVarViewer.setInput(getType());
			String structName = ((StructManipulator) getType()).getStructType().getName();
			muxStructSelector.removeAll();
			for (DataType dtp : getDatatypeLibrary().getDataTypesSorted()) {
				if (dtp instanceof StructuredType) {
					muxStructSelector.add(dtp.getName());
					if (dtp.getName().contentEquals(structName)) {
						muxStructSelector.select(muxStructSelector.getItemCount() - 1);
					}
				}
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

	@Override
	protected void setInputCode() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setInputInit() {
		// TODO Auto-generated method stub

	}

	public static class TreeContentProvider implements ITreeContentProvider {
		@Override
		public Object[] getElements(final Object inputElement) {
			if (inputElement instanceof StructManipulator) {
				return ((StructManipulator) inputElement).getStructType().getMemberVariables().toArray();
			}
			if (inputElement instanceof StructuredType) {
				return ((StructuredType) inputElement).getMemberVariables().toArray();
			}
			return new Object[] {};
		}

		@Override
		public Object[] getChildren(Object parentElement) {
			return ((StructuredType) ((VarDeclaration) parentElement).getType()).getMemberVariables().toArray();
		}

		@Override
		public Object getParent(Object element) {
			return ((INamedElement) element).eContainer();
		}

		@Override
		public boolean hasChildren(Object element) {
			return ((IInterfaceElement) element).getType() instanceof StructuredType;
		}
	}

	public static class TreeLabelProvider extends LabelProvider implements ITableLabelProvider {
		@Override
		public Image getColumnImage(final Object element, final int columnIndex) {
			return null;
		}

		@Override
		public String getColumnText(final Object element, final int columnIndex) {
			if (element instanceof VarDeclaration) {
				switch (columnIndex) {
				case 0:
					return ((VarDeclaration) element).getName();
				case 1:
					return ((VarDeclaration) element).getTypeName();
				case 2:
					return ((VarDeclaration) element).getComment();
				default:
					break;
				}
			}
			return element.toString();
		}
	}

}
