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

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.widgets.WidgetFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class FilterComposite extends Composite {
	public final Filter nameFilter;
	public final Filter typeFilter;
	public final Filter commentFilter;
	public final Filter valueFilter;

	public FilterComposite(final Composite parent, final int style, final boolean initialValue) {
		super(parent, style);

		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		this.setLayout(GridLayoutFactory.swtDefaults().numColumns(1).create());

		nameFilter = new Filter(this, style, "Name");
		typeFilter = new Filter(this, style, "Type");
		commentFilter = new Filter(this, style, "Comment");
		if (initialValue) {
			valueFilter = new Filter(this, style, "Initial Value");
		} else {
			valueFilter = null;
		}

//		caseSensitive = WidgetFactory.button(SWT.CHECK).text("Case Sensitive")
//				.onSelect(event -> settings.nameSubSettings.caseSensitve = caseSensitive.getSelection())
//				.create(buttonComposite);
//		caseSensitive.setSelection(settings.nameSubSettings.caseSensitve);
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
					.enabled(selected.getSelection()).create(this);
			caseSensitive = WidgetFactory.button(SWT.CHECK).text("CS").enabled(selected.getSelection()).create(this);
			wholeWord = WidgetFactory.button(SWT.CHECK).text("WW").enabled(selected.getSelection()).create(this);
			regularExpression = WidgetFactory.button(SWT.CHECK).text("RE").enabled(selected.getSelection())
					.create(this);

			selected.addListener(SWT.Selection, event -> {
				textField.setEnabled(selected.getSelection());
				caseSensitive.setEnabled(selected.getSelection());
				wholeWord.setEnabled(selected.getSelection());
				regularExpression.setEnabled(selected.getSelection());
			});
		}
	}
}
