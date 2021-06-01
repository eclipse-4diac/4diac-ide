/*******************************************************************************
 * Copyright (c) 2020, 2021 Primetals Technologies Germany GmbH,
 *                          Primetals Technologies Austria GmbH,
 *                          Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Daniel Lindhuber - initial API and implementation and/or initial documentation
 *   Bianca Wiesmayr  - fix column traversal, add context menu
 *   Michael Jaeger   - replaced HashSet with ArrayList
 *   Lukas Wais		  - implemented tree menu for structured types
 *   Alois Zoitl	  - fixed fokus checking for linux.
 *******************************************************************************/

package org.eclipse.fordiac.ide.model.ui.editors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.fordiac.ide.model.ui.Messages;
import org.eclipse.fordiac.ide.model.ui.widgets.OpenStructMenu;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.IContentProposalListener2;
import org.eclipse.jface.fieldassist.SimpleContentProposalProvider;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.MenuListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;

public class DataTypeDropdown extends TextCellEditor {

	private ContentProposalAdapter adapter;
	private Text textControl;
	private final DataTypeLibrary library;
	private SimpleContentProposalProvider provider;
	private List<DataType> types;
	private String[] elementaryTypes;
	private final TableViewer viewer;
	/* A flag that indicates if the content proposals have been set to elementary types (this is the case if the text
	 * field is empty) or all types. This means that the ModifyListener of the textControl does not have to set the
	 * types for the proposal provider with every modify event but only when its necessary. */
	private boolean isElementary;

	private boolean isTraverseNextProcessActive;
	private boolean isTraversePreviousProcessActive;

	public DataTypeDropdown(final DataTypeLibrary library, final TableViewer viewer) {
		super(viewer.getTable());
		this.library = library;
		this.viewer = viewer;
		types = new ArrayList<>(); // empty list for initial provider creation
		configureTextControl();
		createDialogButton();
		enableContentProposal();
		loadContent();
	}

	public DataType getType(final String value) {
		return types.stream().filter(type -> value.equals(type.getName())).findAny().orElse(null);
	}

	@Override
	protected void doSetValue(final Object value) {
		if (null == value) {
			textControl.setText(""); //$NON-NLS-1$
		} else {
			super.doSetValue(value);
		}
	}

	/* is called with every opening of the content proposal popup, may lead to performance issues */
	private void loadContent() {
		types = getDataTypesSorted(); // get sorted types for convenient order in dialog
		provider.setProposals(getTypesAsStringArray());
	}

	// can be overridden to filter the list differently
	protected List<DataType> getDataTypesSorted() {
		if (null != library) {
			return library.getDataTypesSorted().stream().filter(Objects::nonNull).collect(Collectors.toList());
		}
		return Collections.emptyList();
	}

	@Override
	protected Control createControl(final Composite parent) {
		final Composite container = new Composite(parent, SWT.NONE);
		final GridLayout contLayout = new GridLayout(2, false);
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
		textControl.addModifyListener(e -> {
			if (textControl.getText().isEmpty()) {
				provider.setProposals(getElementaryTypes());
				isElementary = true;
			} else if (isElementary) {
				provider.setProposals(getTypesAsStringArray());
				isElementary = false;
			}
		});
		textControl.addListener(SWT.Traverse, e -> {
			e.doit = false; // prevent the action from doing anything automatically
			if ((e.detail == SWT.TRAVERSE_TAB_NEXT) && ((e.stateMask & SWT.CTRL) != 0)) {
				// move within column down
				final int selIndex = viewer.getTable().getSelectionIndex() + 1;
				if (selIndex < viewer.getTable().getItemCount()) {
					final Object data = viewer.getTable().getItem(selIndex).getData();
					viewer.editElement(data, 1); // type column
				}
			} else if ((e.detail == SWT.TRAVERSE_TAB_PREVIOUS) && ((e.stateMask & SWT.CTRL) != 0)) {
				// move within column up
				final int selIndex = viewer.getTable().getSelectionIndex() - 1;
				if (selIndex >= 0) {
					final Object data = viewer.getTable().getItem(selIndex).getData();
					viewer.editElement(data, 1); // type column
				}
			} else if (e.detail == SWT.TRAVERSE_TAB_NEXT) {
				// move one column to the right
				if (adapter.isProposalPopupOpen()) {
					triggerEnterKeyEvent();
					isTraverseNextProcessActive = true;
				} else {
					traverseToNextCell();
				}
			} else if (e.detail == SWT.TRAVERSE_TAB_PREVIOUS) {
				// move one column to the left
				if (adapter.isProposalPopupOpen()) {
					triggerEnterKeyEvent();
					isTraversePreviousProcessActive = true;
				} else {
					traverseToPreviousCell();
				}
			}
		});
	}

	private static void triggerEnterKeyEvent() {
		Display display = Display.getCurrent();
		if (display == null) {
			display = Display.getDefault();
		}
		final Event event = new Event();
		event.keyCode = SWT.CR;
		event.display = display;
		event.type = SWT.KeyDown;
		display.post(event);
	}

	private void traverseToPreviousCell() {
		final int selIndex = viewer.getTable().getSelectionIndex();
		final Object data = viewer.getTable().getItem(selIndex).getData();
		adapter.setEnabled(false);
		viewer.editElement(data, 0); // name column
		adapter.setEnabled(true);
	}

	private void traverseToNextCell() {
		final int selIndex = viewer.getTable().getSelectionIndex();
		final Object data = viewer.getTable().getItem(selIndex).getData();
		adapter.setEnabled(false);
		viewer.editElement(data, 2); // comment column
		adapter.setEnabled(true);
	}

	private String[] getElementaryTypes() {
		// only load elementary types once because they do not change
		if (null == elementaryTypes) {
			elementaryTypes = types.stream().filter(type -> !(type instanceof StructuredType)).map(DataType::getName)
					.toArray(String[]::new);
		}
		return elementaryTypes;
	}

	private String[] getTypesAsStringArray() {
		return types.stream().map(DataType::getName).toArray(String[]::new);
	}

	private void createDialogButton() {
		final Button menuButton = new Button((Composite) getControl(), SWT.FLAT);
		menuButton.setText("..."); //$NON-NLS-1$
		menuButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(final SelectionEvent e) {
				openDialog();
			}

			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				// no need to listen to the default selection
			}
		});
	}

	private void openDialog() {
		loadContent(); // refresh content before opening
		final ITreeContentProvider treeProvider = createTreeContentProvider();
		final LabelProvider labelProvider = createTreeLabelProvider();

		final DataTypeTreeSelectionDialog dialog = new DataTypeTreeSelectionDialog(getControl().getShell(),
				labelProvider, treeProvider);
		dialog.setInput(types);
		dialog.setTitle(Messages.DataTypeDropdown_Type_Selection);
		dialog.setMessage(Messages.DataTypeDropdown_Select_Type);
		dialog.setDoubleClickSelects(false); // because it is incompatible with multi-level tree
		dialog.setHelpAvailable(false);

		// user pressed cancel
		if (dialog.open() != Window.OK) {
			deactivate();
			return;
		}
		final Object result = dialog.getFirstResult();
		// check for DataType so that no VarDeclaration or directories can be selected
		if (result instanceof TypeNode) {
			final TypeNode node = (TypeNode) result;
			if (!node.isDirectory()) { // nodes without types are directories
				doSetValue(node.getName());
				fireApplyEditorValue();
			}
		}
		deactivate();
	}

	private class DataTypeTreeSelectionDialog extends ElementTreeSelectionDialog {

		public DataTypeTreeSelectionDialog(final Shell parent, final IBaseLabelProvider labelProvider,
				final ITreeContentProvider contentProvider) {
			super(parent, labelProvider, contentProvider);
		}

		@Override
		protected Control createDialogArea(final Composite parent) {
			final Control control = super.createDialogArea(parent);
			createContextMenu(getTreeViewer().getTree());
			return control;
		}

		private void createContextMenu(final Control control) {
			final Menu openEditorMenu = new Menu(control);
			final MenuItem openItem = new MenuItem(openEditorMenu, SWT.NONE);
			openItem.addListener(SWT.Selection, e -> {
				final StructuredType sel = getSelectedStructuredType(control);
				if (sel != null) {
					handleShellCloseEvent();
					setResult(null); // discard selection, do not update type
					OpenStructMenu.openStructEditor(sel.getPaletteEntry().getFile());
				}
			});
			openItem.setText(FordiacMessages.OPEN_TYPE_EDITOR_MESSAGE);

			openEditorMenu.addMenuListener(new MenuListener() {
				@Override
				public void menuShown(final MenuEvent e) {
					final StructuredType type = getSelectedStructuredType(control);
					openItem.setEnabled((type != null) && (type != IecTypes.GenericTypes.ANY_STRUCT));
				}

				@Override
				public void menuHidden(final MenuEvent e) {
					// nothing to be done here
				}
			});
			control.setMenu(openEditorMenu);
		}

		private StructuredType getSelectedStructuredType(final Control control) {
			final Object selected = ((TreeSelection) getTreeViewer().getSelection()).getFirstElement();
			if (selected instanceof TypeNode) {
				final DataType dtp = ((TypeNode) selected).getType();
				if (dtp instanceof StructuredType) {
					return (StructuredType) dtp;
				}
			}
			return null;
		}
	}

	private static LabelProvider createTreeLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(final Object element) {
				if (element instanceof TypeNode) {
					return ((TypeNode) element).getName();
				}
				return element.toString();
			}

			@Override
			public Image getImage(final Object element) {
				if (element instanceof TypeNode) {
					final TypeNode node = (TypeNode) element;
					if (node.isDirectory()) {
						return PlatformUI.getWorkbench().getSharedImages()
								.getImageDescriptor(ISharedImages.IMG_OBJ_FOLDER).createImage();
					}
					return FordiacImage.ICON_DATA_TYPE.getImage();
				}
				return super.getImage(element);
			}
		};
	}

	private static ITreeContentProvider createTreeContentProvider() {
		return new ITreeContentProvider() {

			@Override
			public boolean hasChildren(final Object element) {
				if (element instanceof TypeNode) {
					return !((TypeNode) element).getChildren().isEmpty();
				}
				return false;
			}

			/* This method separates elementary types and structs into different type nodes before displaying them in
			 * the tree */
			@Override
			public Object[] getElements(final Object inputElement) {
				final TypeNode elementaries = new TypeNode(Messages.DataTypeDropdown_Elementary_Types);
				final TypeNode structures = new TypeNode(Messages.DataTypeDropdown_STRUCT_Types);

				if (inputElement instanceof List<?>) {
					((List<?>) inputElement).forEach(type -> {
						if (type instanceof StructuredType) {
							final StructuredType structuredType = (StructuredType) type;
							// some files are created at runtime and do not have a path
							if (null != structuredType.getPaletteEntry()) {
								final String parentPath = structuredType.getPaletteEntry().getFile().getParent()
										.getProjectRelativePath().toOSString();
								createSubdirectories(structures, structuredType, parentPath);
							} else {
								final TypeNode runtimeNode = new TypeNode(structuredType.getName(), structuredType);
								runtimeNode.setParent(structures);
								structures.addChild(runtimeNode);
							}
						} else if (type instanceof DataType) {
							final DataType simpleType = (DataType) type;
							final TypeNode newNode = new TypeNode(simpleType.getName(), simpleType);
							elementaries.addChild(newNode);
						}
					});
				}

				return new TypeNode[] { elementaries, structures };
			}

			private void createSubdirectories(TypeNode node, final StructuredType structuredType,
					final String parentPath) {
				// split up the path in subdirectories
				final String[] paths = parentPath.split("\\\\"); //$NON-NLS-1$

				// start after Type Library
				for (int i = 1; i < paths.length; i++) {
					final TypeNode current = new TypeNode(paths[i]);
					// check if we already have a parent node
					final int index = node.getChildren().indexOf(current);
					if (-1 != index) {
						node = node.getChildren().get(index);
					} else {
						current.setParent(node);
						node.addChild(current);
						node = current;
					}
				}
				final TypeNode actualType = new TypeNode(structuredType.getName(), structuredType);
				actualType.setParent(node);
				node.addChild(actualType);
			}

			@Override
			public Object[] getChildren(final Object parentElement) {
				if (parentElement instanceof TypeNode) {
					return ((TypeNode) parentElement).getChildren().toArray();
				}
				return new Object[0];
			}

			@Override
			public Object getParent(final Object element) {
				if (element instanceof TypeNode) {
					return ((TypeNode) element).getParent();
				}
				return null;
			}
		};
	}

	static final char[] ACTIVATION_CHARS = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
			'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
			'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4',
			'5', '6', '7', '8', '9', '_', '.', SWT.BS };

	private void enableContentProposal() {
		provider = new SimpleContentProposalProvider(getTypesAsStringArray());
		provider.setFiltering(true);

		adapter = new ContentProposalAdapter(text, new TextContentAdapter(), provider, null, ACTIVATION_CHARS);
		adapter.addContentProposalListener(new IContentProposalListener2() {

			@Override
			public void proposalPopupClosed(final ContentProposalAdapter adapter) {
				// no need to listen to closing
			}

			@Override
			public void proposalPopupOpened(final ContentProposalAdapter adapter) {
				loadContent();
			}

		});

		adapter.addContentProposalListener(proposal -> {
			fireApplyEditorValue();
			// if apply value was triggered programmatically -> tab to next/previous cell
			if (isTraverseNextProcessActive) {
				traverseToNextCell();
				isTraverseNextProcessActive = false;
			} else if (isTraversePreviousProcessActive) {
				traverseToPreviousCell();
				isTraversePreviousProcessActive = false;
			}
		});

		adapter.setProposalAcceptanceStyle(ContentProposalAdapter.PROPOSAL_REPLACE);

	}

	@Override
	protected void focusLost() {
		if (!insideAnyEditorArea()) {
			deactivate();
		}
	}
	private boolean insideAnyEditorArea() {
		final Point cursorLocation = getControl().getDisplay().getCursorLocation();
		final Point containerRelativeCursor = getControl().getParent().toControl(cursorLocation);
		return getControl().getBounds().contains(containerRelativeCursor);
	}


	@Override
	protected boolean dependsOnExternalFocusListener() {
		/* if true, a separate focus listener is created and the whole cell editor looses focus when the proposal popup
		 * is opened */
		return false;
	}

	private static class TypeNode implements Comparable<TypeNode> {
		private final String name;
		private final List<TypeNode> children;
		private TypeNode parent;
		private DataType type;

		private TypeNode(final String name) {
			this.name = name;
			children = new ArrayList<>();
		}

		public boolean isDirectory() {
			return null == type;
		}

		private TypeNode(final String name, final DataType type) {
			this.name = name;
			this.type = type;
			children = new ArrayList<>();
		}

		private String getName() {
			return name;
		}

		private List<TypeNode> getChildren() {
			return children;
		}

		// inserting in place
		private void addChild(final TypeNode child) {
			final int index = Collections.binarySearch(children, child);
			if (index < 0) {
				children.add(-index - 1, child);
			}
		}

		private TypeNode getParent() {
			return parent;
		}

		private void setParent(final TypeNode parent) {
			this.parent = parent;
		}

		public DataType getType() {
			return type;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = (prime * result) + ((name == null) ? 0 : name.hashCode());
			return result;
		}

		@Override
		public boolean equals(final Object obj) {
			if (this == obj) {
				return true;
			}
			if (null == obj) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			final TypeNode other = (TypeNode) obj;
			if (null == name) {
				if (null != other.name) {
					return false;
				}
			} else if (!name.equals(other.name)) {
				return false;
			}
			return true;
		}

		@Override
		public int compareTo(final TypeNode other) {
			// otherwise lower case would be after upper case names
			return this.name.toLowerCase().compareTo(other.getName().toLowerCase());
		}
	}
}
