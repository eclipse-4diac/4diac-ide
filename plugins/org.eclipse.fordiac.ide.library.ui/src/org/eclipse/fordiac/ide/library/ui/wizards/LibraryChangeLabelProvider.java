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

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.eclipse.fordiac.ide.library.LibraryChange;
import org.eclipse.fordiac.ide.library.LibraryChange.ChangeType;
import org.eclipse.fordiac.ide.library.ui.wizards.LibraryPlanningPage.LibContainer;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.swt.widgets.Display;

class LibraryChangeLabelProvider extends StyledCellLabelProvider {

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

	private static final StyledString.Styler ADD_STYLER = new StyledString.Styler() {
		@Override
		public void applyStyles(final TextStyle textStyle) {
			textStyle.foreground = Display.getCurrent().getSystemColor(SWT.COLOR_BLACK);
			textStyle.background = Display.getCurrent().getSystemColor(SWT.COLOR_GREEN);
		}
	};

	private final Function<LibraryChange, String> textProvider;
	private final boolean isModifiable;

	public LibraryChangeLabelProvider(final Function<LibraryChange, String> textProvider, final boolean isModifiable) {
		this.textProvider = Objects.requireNonNull(textProvider);
		this.isModifiable = isModifiable;
	}

	@Override
	public void update(final ViewerCell cell) {
		if (cell.getElement() instanceof LibContainer(final String containerName, final List<LibraryChange> children)
				&& cell.getColumnIndex() == 0 && !children.isEmpty()) {
			cell.setText(containerName);
		}
		if (cell.getElement() instanceof final LibraryChange change) {
			final StyledString styled = new StyledString();
			styled.append(textProvider.apply(change), getStyler(change));
			cell.setText(styled.getString());
			cell.setStyleRanges(styled.getStyleRanges());
			super.update(cell);
		}
	}

	protected Styler getStyler(final LibraryChange change) {
		final ChangeType type = change.getType();
		if (type == ChangeType.NOP && isModifiable) {
			return StyledString.QUALIFIER_STYLER;
		}

		return switch (type) {
		case ChangeType.REMOVE -> REMOVE_STYLER;
		case ChangeType.UPDATE -> UPGRADE_DOWNGRADE_STYLER;
		case ChangeType.DOWNGRADE -> UPGRADE_DOWNGRADE_STYLER;
		case ChangeType.ADD -> ADD_STYLER;
		default -> null;
		};
	}
}