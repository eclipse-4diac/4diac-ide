/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mario Kastner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.library.ui.wizards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.eclipse.fordiac.ide.library.ui.wizards.LibraryChangeAction.ActionType;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public class LibraryDescriptorNode {
	private String name;
	private String activeVersion;
	private List<LibraryDescriptorNode> children;
	private LibraryChangeAction action;

	public LibraryDescriptorNode(final String name, final String activeVersion) {
		this.name = name;
		this.activeVersion = activeVersion;
		this.action = LibraryChangeAction.emptyAction();
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getActiveVersion() {
		return activeVersion;
	}

	public void setActiveVerion(final String activeVersion) {
		this.activeVersion = activeVersion;
	}

	public void addChild(final LibraryDescriptorNode node) {
		if (this.children == null) {
			children = new ArrayList<>();
		}
		children.add(node);
	}

	public List<LibraryDescriptorNode> getChildren() {
		if (children != null) {
			return children;
		}
		return Collections.emptyList();
	}

	public LibraryChangeAction getActionType() {
		return action;
	}

	public void setAction(final LibraryChangeAction action) {
		this.action = action;
	}

	public static class ActionLabelProvider extends StyledCellLabelProvider {
		@Override
		public void update(final ViewerCell cell) {
			if (cell.getElement() instanceof final LibraryDescriptorNode node) {
				final StyledString styled = new StyledString();

				if (node.getChildren().isEmpty()) {
					if (node.getActionType().getType() == ActionType.EMPTY) {
						styled.append(LibraryChangeAction.getActionText(node.getActionType()),
								StyledString.QUALIFIER_STYLER);
					} else {
						styled.append(LibraryChangeAction.getActionText(node.getActionType()));
					}
				}
				cell.setText(styled.getString());
				cell.setStyleRanges(styled.getStyleRanges());

				super.update(cell);
			}
		}
	}

	public static class LibraryDescriptorLabelProvider extends ColumnLabelProvider {

		private final Function<LibraryDescriptorNode, String> textProvider;

		public LibraryDescriptorLabelProvider(final Function<LibraryDescriptorNode, String> textProvider) {
			Objects.requireNonNull(textProvider);
			this.textProvider = textProvider;
		}

		@Override
		public String getText(final Object element) {
			if (element instanceof final LibraryDescriptorNode desc) {
				return textProvider.apply(desc);
			}
			return ""; //$NON-NLS-1$
		}

		@Override
		public Color getBackground(final Object element) {
			if (element instanceof final LibraryDescriptorNode desc) {
				if (desc.getActionType().getType() == ActionType.REMOVE) {
					return Display.getCurrent().getSystemColor(SWT.COLOR_RED);
				}
				if (desc.getActionType().getType() == ActionType.EMPTY) {
					return super.getBackground(element);
				}
			}
			return Display.getCurrent().getSystemColor(SWT.COLOR_YELLOW);
		}

		@Override
		public Color getForeground(final Object element) {
			if (element instanceof final LibraryDescriptorNode desc) {
				if (desc.getActionType().getType() == ActionType.REMOVE) {
					return Display.getCurrent().getSystemColor(SWT.COLOR_WHITE);
				}
				if (desc.getActionType().getType() == ActionType.EMPTY) {
					return super.getForeground(element);
				}
			}
			return Display.getCurrent().getSystemColor(SWT.COLOR_DARK_RED);
		}

	}

}