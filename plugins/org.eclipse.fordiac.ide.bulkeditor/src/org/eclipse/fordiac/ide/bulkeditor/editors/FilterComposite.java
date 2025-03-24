/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.bulkeditor.editors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.fordiac.ide.bulkeditor.Messages;
import org.eclipse.fordiac.ide.bulkeditor.editors.BulkEditorSettings.BulkEditorSubSettings;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.widgets.WidgetFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class FilterComposite extends Composite {
	private final List<Filter> filterList = new ArrayList<>();
	private final List<String> filterNames;

	public FilterComposite(final Composite parent, final int style, final List<String> filterNames) {
		super(parent, style);

		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		this.setLayout(GridLayoutFactory.swtDefaults().numColumns(1).create());

		this.filterNames = filterNames;
		for (final String filterName : filterNames) {
			filterList.add(new Filter(this, style, filterName));
		}
	}

	public FilterComposite(final Composite parent, final int style, final List<String> filterNames,
			final BulkEditorSettings settings, final List<String> subSettingsReferencesNames) {
		super(parent, style);

		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		this.setLayout(GridLayoutFactory.swtDefaults().numColumns(1).create());

		this.filterNames = filterNames;
		for (int i = 0; i < filterNames.size(); i++) {
			final Filter filter = new Filter(this, style, filterNames.get(i));
			filterList.add(filter);
			if (i < subSettingsReferencesNames.size()) {
				filter.addListenerSubSetting(settings.getSubSettings(subSettingsReferencesNames.get(i)));
			}
		}
	}

	public Filter getFilter(final String filterName) {
		final int index = filterNames.indexOf(filterName);
		if (index >= 0 && index < filterList.size()) {
			return filterList.get(index);
		}
		return null;
	}

	public class Filter extends Composite {
		private static final String IMAGE_BUNDLE = "org.eclipse.ui.workbench.texteditor"; //$NON-NLS-1$
		private static final String CASE_SENSITVE_IMAGE = "icons/full/elcl16/case_sensitive.png"; //$NON-NLS-1$
		// TODO: Change to a correct icon
		private static final String WHOLE_WORD_IMAGE = "icons/full/elcl16/delete_template.png"; //$NON-NLS-1$
		private static final String EXACT_MATCH_IMAGE = "icons/full/elcl16/whole_word.png"; //$NON-NLS-1$
		private static final String REGULAR_EXPRESSION_IMAGE = "icons/full/elcl16/regex.png"; //$NON-NLS-1$

		public final Button selected;
		public final Text textField;

		public final Button caseSensitive;
		public final Button wholeWord;
		public final Button exactMatch;
		public final Button regularExpression;

		public Filter(final Composite parent, final int style, final String name) {
			super(parent, style);

			this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
			this.setLayout(GridLayoutFactory.swtDefaults().margins(0, 0).numColumns(6).create());

			selected = WidgetFactory.button(SWT.CHECK).text(name).create(this);
			selected.setLayoutData(GridDataFactory.swtDefaults().hint(80, SWT.DEFAULT).create());

			textField = WidgetFactory.text(SWT.BORDER).layoutData(new GridData(SWT.FILL, SWT.FILL, true, false))
					.create(this);

			ImageDescriptor descriptor = ImageDescriptor
					.createFromURL(FileLocator.find(Platform.getBundle(IMAGE_BUNDLE), new Path(CASE_SENSITVE_IMAGE)));
			caseSensitive = WidgetFactory.button(SWT.TOGGLE).image(descriptor.createImage())
					.tooltip(Messages.CaseSensitve).create(this);

			descriptor = ImageDescriptor
					.createFromURL(FileLocator.find(Platform.getBundle(IMAGE_BUNDLE), new Path(WHOLE_WORD_IMAGE)));
			wholeWord = WidgetFactory.button(SWT.TOGGLE).image(descriptor.createImage()).tooltip(Messages.WholeWord)
					.create(this);

			descriptor = ImageDescriptor
					.createFromURL(FileLocator.find(Platform.getBundle(IMAGE_BUNDLE), new Path(EXACT_MATCH_IMAGE)));
			exactMatch = WidgetFactory.button(SWT.TOGGLE).image(descriptor.createImage()).tooltip(Messages.ExactMatch)
					.create(this);

			descriptor = ImageDescriptor.createFromURL(
					FileLocator.find(Platform.getBundle(IMAGE_BUNDLE), new Path(REGULAR_EXPRESSION_IMAGE)));
			regularExpression = WidgetFactory.button(SWT.TOGGLE).image(descriptor.createImage())
					.tooltip(Messages.RegularExpression).create(this);

			selected.addListener(SWT.Selection, event -> {
				textField.setEnabled(selected.getSelection());
				caseSensitive.setEnabled(selected.getSelection());
				wholeWord.setEnabled(
						selected.getSelection() && !exactMatch.getSelection() && !regularExpression.getSelection());
				exactMatch.setEnabled(selected.getSelection() && !wholeWord.getSelection());
				regularExpression.setEnabled(selected.getSelection() && !wholeWord.getSelection());
			});
		}

		private void addListenerSubSetting(final BulkEditorSubSettings subSetting) {
			final boolean isSelected = subSetting.selected;
			selected.setSelection(isSelected);
			selected.addListener(SWT.Selection, event -> subSetting.selected = selected.getSelection());

			textField.setText(subSetting.textField);
			textField.setEnabled(isSelected);
			textField.addModifyListener(event -> subSetting.textField = textField.getText());

			caseSensitive.setSelection(subSetting.caseSensitive);
			caseSensitive.setEnabled(isSelected);
			caseSensitive.addListener(SWT.Selection, event -> subSetting.caseSensitive = caseSensitive.getSelection());

			wholeWord.setSelection(subSetting.wholeWord);
			wholeWord.setEnabled(isSelected && !subSetting.exactMatch && !subSetting.regularExpression);
			wholeWord.addListener(SWT.Selection, event -> {
				subSetting.wholeWord = wholeWord.getSelection();
				exactMatch.setEnabled(!wholeWord.getSelection());
				regularExpression.setEnabled(!wholeWord.getSelection());
			});

			exactMatch.setSelection(subSetting.exactMatch);
			exactMatch.setEnabled(isSelected && !subSetting.wholeWord);
			exactMatch.addListener(SWT.Selection, event -> {
				subSetting.exactMatch = exactMatch.getSelection();
				wholeWord.setEnabled(!exactMatch.getSelection() && !regularExpression.getSelection());
			});

			regularExpression.setSelection(subSetting.regularExpression);
			regularExpression.setEnabled(isSelected && !subSetting.wholeWord);
			regularExpression.addListener(SWT.Selection, event -> {
				subSetting.regularExpression = regularExpression.getSelection();
				wholeWord.setEnabled(!exactMatch.getSelection() && !regularExpression.getSelection());
			});
		}
	}
}
