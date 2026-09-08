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
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.bulkeditor.query;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.draw2d.ActionListener;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.fordiac.ide.bulkeditor.QueryUIPreferenceConstants;
import org.eclipse.fordiac.ide.bulkeditor.query.QueryModelHelper.FieldConstraintData;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Text;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphItem;

public class FilterFigure extends Figure {
	@FunctionalInterface
	public interface FilterChangeListener {
		void onFilterChanged(FieldConstraintData data);
	}

	private static final Color COLOR_VALUE_BG = QueryUIPreferenceConstants.getDefaultQueryBackground();
	private static final Color COLOR_VALUE_BORDER = QueryUIPreferenceConstants.getValueBorder();

	private static final String IMAGE_BUNDLE = "org.eclipse.ui.workbench.texteditor"; //$NON-NLS-1$
	private static final String CASE_SENSITIVE_IMAGE = "icons/full/elcl16/case_sensitive.png"; //$NON-NLS-1$
	private static final String EXACT_MATCH_IMAGE = "icons/full/elcl16/whole_word.png"; //$NON-NLS-1$
	private static final String REGULAR_EXPRESSION_IMAGE = "icons/full/elcl16/regex.png"; //$NON-NLS-1$

	private final Label valueLabel;
	private final ToggleButton caseSensitive;
	private final ToggleButton wholeWord;
	private final ToggleButton exactMatch;
	private final ToggleButton regularExpression;

	private final FigureCanvas canvas;
	private final List<FilterChangeListener> listeners = new ArrayList<>();

	public FilterFigure(final String name, final FieldConstraintData initial, final FigureCanvas canvas) {
		this.canvas = canvas;
		final GridLayout gl = new GridLayout(6, false);
		gl.marginHeight = 2;
		gl.marginWidth = 4;
		setLayoutManager(gl);

		final Label nameLabel = new Label(name + ":"); //$NON-NLS-1$
		add(nameLabel);
		gl.setConstraint(nameLabel, new GridData(SWT.BEGINNING, SWT.CENTER, false, false));

		valueLabel = new Label(initial.value());
		valueLabel.setOpaque(true);
		valueLabel.setBackgroundColor(COLOR_VALUE_BG);
		valueLabel.setBorder(new LineBorder(COLOR_VALUE_BORDER, 1));
		valueLabel.addMouseListener(new MouseListener.Stub() {
			@Override
			public void mouseDoubleClicked(final MouseEvent me) {
				if (canvas instanceof final Graph graph) {
					graph.setSelection(new GraphItem[0]);
				}
				openDirectEdit();
			}
		});
		add(valueLabel);
		final var valueGd = new GridData(SWT.FILL, SWT.CENTER, true, false);
		valueGd.widthHint = 120;
		gl.setConstraint(valueLabel, valueGd);

		caseSensitive = createImageToggle(
				ImageDescriptor.createFromURL(
						FileLocator.find(Platform.getBundle(IMAGE_BUNDLE), new Path(CASE_SENSITIVE_IMAGE))),
				gl, initial.caseSensitive());

		wholeWord = createImageToggle(FordiacImage.ICON_WHOLE_WORD.getImageDescriptor(), gl, initial.wholeWord());
		exactMatch = createImageToggle(
				ImageDescriptor
						.createFromURL(FileLocator.find(Platform.getBundle(IMAGE_BUNDLE), new Path(EXACT_MATCH_IMAGE))),
				gl, initial.entire());
		regularExpression = createImageToggle(
				ImageDescriptor.createFromURL(
						FileLocator.find(Platform.getBundle(IMAGE_BUNDLE), new Path(REGULAR_EXPRESSION_IMAGE))),
				gl, initial.regex());

		final ActionListener onToggleChanged = event -> {
			updateEnablement();
			fireFilterChanged();
		};
		caseSensitive.addActionListener(onToggleChanged);
		wholeWord.addActionListener(onToggleChanged);
		exactMatch.addActionListener(onToggleChanged);
		regularExpression.addActionListener(onToggleChanged);

		updateEnablement();
	}

	public void addFilterChangeListener(final FilterChangeListener listener) {
		listeners.add(listener);
	}

	public void removeFilterChangeListener(final FilterChangeListener listener) {
		listeners.remove(listener);
	}

	private void updateEnablement() {
		wholeWord.setEnabled(!exactMatch.isSelected() && !regularExpression.isSelected());
		exactMatch.setEnabled(!wholeWord.isSelected());
		regularExpression.setEnabled(!wholeWord.isSelected());
	}

	private void openDirectEdit() {
		if (canvas == null || canvas.isDisposed()) {
			return;
		}

		final var figureBounds = valueLabel.getBounds().getCopy();
		valueLabel.translateToAbsolute(figureBounds);

		final Text textWidget = new Text(canvas, SWT.BORDER);
		textWidget.setText(valueLabel.getText());
		textWidget.setBounds(figureBounds.x, figureBounds.y, figureBounds.width, figureBounds.height);
		textWidget.selectAll();
		textWidget.setFocus();

		final Runnable commit = () -> {
			if (!textWidget.isDisposed()) {
				valueLabel.setText(textWidget.getText() != null ? textWidget.getText() : ""); //$NON-NLS-1$
				textWidget.dispose();
				fireFilterChanged();
			}
		};
		final Runnable cancel = () -> {
			if (!textWidget.isDisposed()) {
				textWidget.dispose();
			}
		};

		textWidget.addListener(SWT.FocusOut, e -> commit.run());
		textWidget.addListener(SWT.KeyDown, e -> {
			if (e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR) {
				commit.run();
			} else if (e.keyCode == SWT.ESC) {
				cancel.run();
			}
		});
	}

	private ToggleButton createImageToggle(final ImageDescriptor descriptor, final GridLayout gl,
			final boolean initialSelection) {
		final var btn = new ToggleButton(descriptor);
		add(btn);
		gl.setConstraint(btn, new GridData(SWT.CENTER, SWT.CENTER, false, false));
		btn.setSelected(initialSelection);
		return btn;
	}

	private void fireFilterChanged() {
		final var data = new FieldConstraintData(valueLabel.getText(), caseSensitive.isSelected(),
				wholeWord.isSelected(), exactMatch.isSelected(), regularExpression.isSelected());
		listeners.forEach(l -> l.onFilterChanged(data));
	}
}
