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
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.TextStyle;
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

	public void setActiveVersion(final String activeVersion) {
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

	public LibraryChangeAction getAction() {
		return action;
	}

	public void setAction(final LibraryChangeAction action) {
		this.action = action;
	}

	public static class LibraryDescriptorLabelProvider extends StyledCellLabelProvider {

		private static final StyledString.Styler UPGRADE_DOWNGRADE_STYLER = new StyledString.Styler() {
			@Override
			public void applyStyles(final TextStyle textStyle) {
				textStyle.foreground = Display.getCurrent().getSystemColor(SWT.COLOR_DARK_RED);
				textStyle.background = Display.getCurrent().getSystemColor(SWT.COLOR_YELLOW);
			}
		};

		private static final StyledString.Styler REMOVE_STYLER = new StyledString.Styler() {
			@Override
			public void applyStyles(final TextStyle textStyle) {
				textStyle.foreground = Display.getCurrent().getSystemColor(SWT.COLOR_WHITE);
				textStyle.background = Display.getCurrent().getSystemColor(SWT.COLOR_RED);
			}
		};

		private final Function<LibraryDescriptorNode, String> textProvider;
		private final boolean isModifiable;

		public LibraryDescriptorLabelProvider(final Function<LibraryDescriptorNode, String> textProvider,
				final boolean isModifiable) {
			this.textProvider = Objects.requireNonNull(textProvider);
			this.isModifiable = isModifiable;
		}

		@Override
		public void update(final ViewerCell cell) {
			if (cell.getElement() instanceof final LibraryDescriptorNode node) {
				final StyledString styled = new StyledString();
				if (node.getChildren().isEmpty()) {
					styled.append(textProvider.apply(node), getStyler(node));
				} else {
					styled.append(textProvider.apply(node));
				}
				cell.setText(styled.getString());
				cell.setStyleRanges(styled.getStyleRanges());
				super.update(cell);
			}
		}

		protected Styler getStyler(final LibraryDescriptorNode node) {
			final ActionType type = node.getAction().getType();
			if (type == ActionType.EMPTY && isModifiable) {
				return StyledString.QUALIFIER_STYLER;
			}

			return switch (type) {
			case ActionType.REMOVE -> REMOVE_STYLER;
			case ActionType.UPDATE -> UPGRADE_DOWNGRADE_STYLER;
			case ActionType.DOWNGRADE -> UPGRADE_DOWNGRADE_STYLER;
			default -> null;
			};
		}
	}

}