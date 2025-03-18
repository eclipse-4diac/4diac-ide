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

import org.eclipse.fordiac.ide.bulkeditor.editors.BulkEditorSettings.BulkEditorSubSettings;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
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
		public final Button selected;
		public final Text textField;

		public final Button caseSensitive;
		public final Button wholeWord;
		public final Button regularExpression;

		public Filter(final Composite parent, final int style, final String name) {
			super(parent, style);

			this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
			this.setLayout(GridLayoutFactory.swtDefaults().margins(0, 0).numColumns(5).create());

			selected = WidgetFactory.button(SWT.CHECK).text(name).create(this);
			selected.setLayoutData(GridDataFactory.swtDefaults().hint(80, SWT.DEFAULT).create());

			textField = WidgetFactory.text(SWT.BORDER).layoutData(new GridData(SWT.FILL, SWT.FILL, true, false))
					.create(this);
			caseSensitive = WidgetFactory.button(SWT.CHECK).text("CS").create(this);
			wholeWord = WidgetFactory.button(SWT.CHECK).text("WW").create(this);
			regularExpression = WidgetFactory.button(SWT.CHECK).text("RE").create(this);

			selected.addListener(SWT.Selection, event -> {
				textField.setEnabled(selected.getSelection());
				caseSensitive.setEnabled(selected.getSelection());
				wholeWord.setEnabled(selected.getSelection());
				regularExpression.setEnabled(selected.getSelection());
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
			wholeWord.setEnabled(isSelected);
			wholeWord.addListener(SWT.Selection, event -> subSetting.wholeWord = wholeWord.getSelection());

			regularExpression.setSelection(subSetting.regularExpression);
			regularExpression.setEnabled(isSelected);
			regularExpression.addListener(SWT.Selection,
					event -> subSetting.regularExpression = regularExpression.getSelection());
		}
	}
}
