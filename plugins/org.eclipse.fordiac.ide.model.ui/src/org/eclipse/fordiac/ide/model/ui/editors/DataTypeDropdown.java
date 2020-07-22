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
 *   Daniel Lindhuber - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.model.ui.editors;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.fordiac.ide.model.ui.Messages;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.IContentProposalListener2;
import org.eclipse.jface.fieldassist.SimpleContentProposalProvider;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;

public class DataTypeDropdown extends TextCellEditor {

	private ContentProposalAdapter adapter;
	private Text textControl;
	private DataTypeLibrary library;
	private SimpleContentProposalProvider provider;
	private List<DataType> types;
	private boolean isDialogOpen;

	public DataTypeDropdown(Composite parent, DataTypeLibrary library) {
		super(parent);
		this.library = library;
		types = new ArrayList<>(); // empty list for initial provider creation
		configureTextControl();
		createDialogButton();
		enableContentProposal();
		loadContent();
	}

	public DataType getType(String value) {
		return types.stream().filter(type -> value.equals(type.getName())).findAny().orElse(null);
	}

	@Override
	protected void doSetValue(Object value) {
		if (value == null) {
			textControl.setText(""); //$NON-NLS-1$
		} else {
			super.doSetValue(value);
		}
	}

	// is called with every opening of the content proposal popup, may lead to
	// performance issues
	private void loadContent() {
		types = library.getDataTypesSorted(); // get sorted types for convenient order in dialog
		provider.setProposals(getTypesAsStringArray());
	}

	@Override
	protected Control createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		GridLayout contLayout = new GridLayout(2, false);
		contLayout.horizontalSpacing = 0;
		contLayout.marginTop = 0;
		contLayout.marginBottom = 0;
		contLayout.marginWidth = 0;
		contLayout.marginHeight = 0;
		contLayout.verticalSpacing = 0;
		contLayout.horizontalSpacing = 0;
		container.setLayout(contLayout);
		textControl = (Text) super.createControl(container);
		return container;
	}

	private void configureTextControl() {
		textControl.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	}

	private String[] getTypesAsStringArray() {
		return types.stream().map(DataType::getName).toArray(String[]::new);
	}

	private void createDialogButton() {
		Button menuButton = new Button((Composite) getControl(), SWT.FLAT);
		menuButton.setText("..."); //$NON-NLS-1$
		menuButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				isDialogOpen = true;
				openDialog();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// no need to listen to the default selection
			}
		});
	}

	private void openDialog() {
		loadContent(); // refresh content before opening
		ITreeContentProvider treeProvider = createTreeContentProvider();

		LabelProvider labelProvider = createTreeLabelProvider();

		ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(getControl().getShell(), labelProvider,
				treeProvider);
		dialog.setInput(types);
		dialog.setTitle(Messages.DataTypeDropdown_Type_Selection);
		dialog.setMessage(Messages.DataTypeDropdown_Select_Type);
		// user pressed cancel
		if (dialog.open() != Window.OK) {
			isDialogOpen = false;
			deactivate();
			return;
		}
		Object result = dialog.getFirstResult();
		// check for DataType so that no VarDeclaration can be selected
		if (result instanceof DataType) {
			doSetValue(((DataType) result).getName());
			fireApplyEditorValue();
		}
		isDialogOpen = false;
		deactivate();
	}

	private LabelProvider createTreeLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof DataType) {
					return ((DataType) element).getName();
				}
				if (element instanceof VarDeclaration) {
					return ((VarDeclaration) element).getName();
				}
				if (element instanceof TypeNode) {
					return ((TypeNode) element).getName();
				}
				return element.toString();
			}
		};
	}

	private ITreeContentProvider createTreeContentProvider() {
		return new ITreeContentProvider() {

			@Override
			public boolean hasChildren(Object element) {
				if (element instanceof TypeNode) {
					return !((TypeNode) element).getTypes().isEmpty();
				}
				return false;
			}

			@Override
			public Object getParent(Object element) {
				return null;
			}

			/*
			 * This method separates elementary types and structs into different type nodes
			 * before displaying them in the tree
			 */
			@Override
			public Object[] getElements(Object inputElement) {
				Object[] arr = new Object[] { new TypeNode(Messages.DataTypeDropdown_Elementary_Types),
						new TypeNode(Messages.DataTypeDropdown_STRUCT_Types) };
				if (inputElement instanceof List<?>) {
					((List<?>) inputElement).forEach(type -> {
						if (type instanceof StructuredType) {
							((TypeNode) arr[1]).addType((DataType) type);
						} else if (type instanceof DataType) {
							((TypeNode) arr[0]).addType((DataType) type);
						}
					});
				}
				return arr;
			}

			@Override
			public Object[] getChildren(Object parentElement) {
				if (parentElement instanceof TypeNode) {
					return ((TypeNode) parentElement).getTypes().toArray();
				}
				return new Object[0];
			}
		};
	}

	private void enableContentProposal() {
		provider = new SimpleContentProposalProvider(getTypesAsStringArray());
		provider.setFiltering(true);

		adapter = new ContentProposalAdapter(text, new TextContentAdapter(), provider, null, null);

		adapter.addContentProposalListener(new IContentProposalListener2() {

			@Override
			public void proposalPopupClosed(ContentProposalAdapter adapter) {
				/*
				 * deactivate the editor if the popup is closed, no dialog is open and the text
				 * field has no focus, otherwise the search field would stay editable even when
				 * its focus is lost. This is due to the event order, focusLost() of the text
				 * control is called before isProposalPopupOpen() changes its value.
				 */
				if (!isDialogOpen && !textControl.isFocusControl()) {
					setValue(""); //$NON-NLS-1$
					deactivate();
				}
			}

			@Override
			public void proposalPopupOpened(ContentProposalAdapter adapter) {
				loadContent();
			}

		});

		adapter.addContentProposalListener(proposal -> fireApplyEditorValue());

		adapter.setProposalAcceptanceStyle(ContentProposalAdapter.PROPOSAL_REPLACE);

	}

	@Override
	protected void focusLost() {
		if (!adapter.isProposalPopupOpen() && !isDialogOpen) {
			setValue(""); //$NON-NLS-1$
			deactivate();
		}
	}

	@Override
	protected boolean dependsOnExternalFocusListener() {
		/*
		 * if true, a separate focus listener is created and the whole cell editor
		 * looses focus when the proposal popup is opened
		 */
		return false;
	}

	private class TypeNode {
		private String name;
		private Set<DataType> types;

		public TypeNode(String name) {
			this.name = name;
			types = new HashSet<>();
		}

		public String getName() {
			return name;
		}

		public Set<DataType> getTypes() {
			return types;
		}

		public void addType(DataType type) {
			types.add(type);
		}
	}
}
