/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.widget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.jface.fieldassist.SimpleContentProposalProvider;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.nebula.widgets.nattable.edit.editor.TextCellEditor;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer.MoveDirectionEnum;
import org.eclipse.nebula.widgets.nattable.widget.EditModeEnum;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;

class DataTypeSelectionButton extends TextCellEditor {
	Map<String, List<String>> elements;
	private Button button;

	public DataTypeSelectionButton(final Map<String, List<String>> elements) {
		super();
		this.elements = elements;

		final SimpleContentProposalProvider proposalProvider = new SimpleContentProposalProvider();
		proposalProvider.setFiltering(true);
		enableContentProposal(new TextContentAdapter(), proposalProvider, null, NatTableWidgetFactory.ACTIVATION_CHARS);
	}

	private void refreshProposal() {
		final List<String> proposals = new ArrayList<>();
		elements.forEach((k, v) -> proposals.addAll(v));
		((SimpleContentProposalProvider) proposalProvider).setProposals(proposals.toArray(new String[0]));
	}

	@Override
	protected Text createEditorControl(final Composite parent, final int style) {
		focusListener = new FocusAdapter() {
			// remove focus Listener for button popup
		};
		return super.createEditorControl(parent, style);
	}

	@Override
	protected Control activateCell(final Composite parent, final Object originalCanonicalValue) {
		refreshProposal();
		button = new Button(parent, SWT.FLAT);
		button.setText("..."); //$NON-NLS-1$
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				final ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(
						Display.getCurrent().getActiveShell(), new LabelProvider() {
							@Override
							public String getText(final Object element) {
								if (element instanceof DataTypeSelectionButton.Node) {
									return ((DataTypeSelectionButton.Node) element).getValue();
								}
								return element.toString();
							}

							@Override
							public Image getImage(final Object element) {
								if (element instanceof DataTypeSelectionButton.Node) {
									final DataTypeSelectionButton.Node node = (DataTypeSelectionButton.Node) element;
									if (node.isDirectory()) {
										return PlatformUI.getWorkbench().getSharedImages()
												.getImage(ISharedImages.IMG_OBJ_ELEMENT);
									}
									return FordiacImage.ICON_DATA_TYPE.getImage();
								}
								return super.getImage(element);
							}
						}, new ITreeContentProvider() {

							@Override
							public boolean hasChildren(final Object element) {
								if (element instanceof DataTypeSelectionButton.Node) {
									return !((DataTypeSelectionButton.Node) element).getChildren().isEmpty();
								}
								return false;
							}

							@Override
							public Object getParent(final Object element) {
								if (element instanceof DataTypeSelectionButton.Node) {
									return ((DataTypeSelectionButton.Node) element).getParent();
								}
								return null;
							}

							@Override
							public Object[] getElements(final Object inputElement) {
								final List<DataTypeSelectionButton.Node> list = new ArrayList<>();
								if (inputElement instanceof HashMap<?, ?>) {
									final HashMap<String, List<String>> map = (HashMap<String, List<String>>) inputElement;

									map.forEach((k, v) -> {
										final DataTypeSelectionButton.Node root = new Node(k, true);
										v.forEach(value -> {
											final DataTypeSelectionButton.Node node = new Node(value);
											node.setParent(root);
											root.addChild(node);
										});
										list.add(root);
									});
								}
								return list.toArray();
							}

							@Override
							public Object[] getChildren(final Object parentElement) {
								if (parentElement instanceof DataTypeSelectionButton.Node) {
									return ((DataTypeSelectionButton.Node) parentElement).getChildren().toArray();
								}
								return new Object[0];
							}
						});
				dialog.setHelpAvailable(false);
				dialog.setInput(elements);

				if (dialog.open() != Window.OK) {
					if (editMode != EditModeEnum.DIALOG) {
						close();
					}
					return;
				}

				final DataTypeSelectionButton.Node selected = (DataTypeSelectionButton.Node) dialog.getFirstResult();
				if (selected != null) {
					setEditorValue(selected.getValue());

					if (editMode == EditModeEnum.INLINE) {
						commit(MoveDirectionEnum.NONE, true);
					}
				}
			}
		});

		final Text text = (Text) super.activateCell(parent, originalCanonicalValue);
		if (editMode == EditModeEnum.DIALOG) {
			final Composite composite = new Composite(parent, 0);
			composite.setLayout(new GridLayout(2, false));
			GridDataFactory.fillDefaults().grab(true, false).applyTo(composite);
			text.setParent(composite);
			button.setParent(composite);
			return parent;
		}
		return parent;
	}

	@Override
	public Rectangle calculateControlBounds(final Rectangle cellBounds) {
		button.setBounds(cellBounds.x + cellBounds.width - 25, cellBounds.y, 25, cellBounds.height);
		cellBounds.width = cellBounds.width - 25;
		return super.calculateControlBounds(cellBounds);
	}

	@Override
	public void close() {
		super.close();
		button.dispose();
	}


	private static final class Node {
		private final String value;
		private final List<DataTypeSelectionButton.Node> children;
		private DataTypeSelectionButton.Node parent;
		private final boolean isDirectory;

		private Node(final String value) {
			this.value = value;
			this.isDirectory = false;
			children = new ArrayList<>();
		}

		private Node(final String value, final boolean isDirectory) {
			this.value = value;
			this.isDirectory = isDirectory;
			children = new ArrayList<>();
		}

		public boolean isDirectory() {
			return isDirectory;
		}

		private String getValue() {
			return value;
		}

		private List<DataTypeSelectionButton.Node> getChildren() {
			return children;
		}

		private void addChild(final DataTypeSelectionButton.Node child) {
			this.children.add(child);
		}

		private DataTypeSelectionButton.Node getParent() {
			return parent;
		}

		private void setParent(final DataTypeSelectionButton.Node parent) {
			this.parent = parent;
		}
	}
}