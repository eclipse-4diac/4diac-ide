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
package org.eclipse.fordiac.ide.bulkeditor.query.figures;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.Label;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.fordiac.ide.bulkeditor.Messages;
import org.eclipse.fordiac.ide.bulkeditor.query.figures.QueryNodeFigure.EditableValue;
import org.eclipse.fordiac.ide.bulkeditor.query.figures.QueryNodeFigure.FeatureChangeHandler;
import org.eclipse.fordiac.ide.bulkeditor.query.model.FieldConstraint;
import org.eclipse.fordiac.ide.bulkeditor.query.model.QueryPackage;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;

/** Row of a constraint node showing a field constraint and its options. */
public class FieldConstraintFigure extends Figure {

	private static final int VALUE_WIDTH = 120;
	private static final String TEXT_EDITOR_BUNDLE = "org.eclipse.ui.workbench.texteditor"; //$NON-NLS-1$
	private static final String CASE_SENSITIVE_IMAGE = "icons/full/elcl16/case_sensitive.png"; //$NON-NLS-1$
	private static final String EXACT_MATCH_IMAGE = "icons/full/elcl16/whole_word.png"; //$NON-NLS-1$
	private static final String REGULAR_EXPRESSION_IMAGE = "icons/full/elcl16/regex.png"; //$NON-NLS-1$

	private final FieldConstraint fieldConstraint;
	private final FeatureChangeHandler changeHandler;
	private final Label valueLabel = QueryValueLabel.createFixedWidth(VALUE_WIDTH);
	private final QueryButton caseSensitive;
	private final QueryButton wholeWord;
	private final QueryButton exactMatch;
	private final QueryButton regularExpression;

	public FieldConstraintFigure(final FieldConstraint fieldConstraint, final FeatureChangeHandler changeHandler) {
		this.fieldConstraint = fieldConstraint;
		this.changeHandler = changeHandler;
		final GridLayout layout = new GridLayout(6, false);
		layout.marginHeight = 3;
		layout.marginWidth = 4;
		setLayoutManager(layout);

		final String fieldName = fieldConstraint.eContainmentFeature().getName();
		add(new Label(fieldName + ":"), new GridData(SWT.BEGINNING, SWT.CENTER, false, false)); //$NON-NLS-1$
		add(valueLabel, new GridData(SWT.FILL, SWT.CENTER, false, false));
		caseSensitive = addToggle(getTextEditorImage(CASE_SENSITIVE_IMAGE), Messages.CaseSensitive,
				QueryPackage.Literals.FIELD_CONSTRAINT__CASE_SENSITIVE);
		wholeWord = addToggle(FordiacImage.ICON_WHOLE_WORD.getImageDescriptor(), Messages.WholeWord,
				QueryPackage.Literals.FIELD_CONSTRAINT__WHOLE_WORD);
		exactMatch = addToggle(getTextEditorImage(EXACT_MATCH_IMAGE), Messages.ExactMatch,
				QueryPackage.Literals.FIELD_CONSTRAINT__ENTIRE);
		regularExpression = addToggle(getTextEditorImage(REGULAR_EXPRESSION_IMAGE), Messages.RegularExpression,
				QueryPackage.Literals.FIELD_CONSTRAINT__REGEX);
	}

	public FieldConstraint getFieldConstraint() {
		return fieldConstraint;
	}

	public EditableValue getEditableValue() {
		return new EditableValue(valueLabel, fieldConstraint, QueryPackage.Literals.FIELD_CONSTRAINT__VALUE);
	}

	/** Updates the figure to the current state of its field constraint. */
	public void refresh() {
		valueLabel.setText(fieldConstraint.getValue());
		caseSensitive.setSelected(fieldConstraint.isCaseSensitive());
		wholeWord.setSelected(fieldConstraint.isWholeWord());
		exactMatch.setSelected(fieldConstraint.isEntire());
		regularExpression.setSelected(fieldConstraint.isRegex());
		updateEnablement();
	}

	private QueryButton addToggle(final ImageDescriptor image, final String tooltip, final EAttribute attribute) {
		final QueryButton toggle = QueryButton.createToggle(image);
		toggle.setToolTip(new Label(tooltip));
		toggle.addActionListener(_ -> {
			updateEnablement();
			changeHandler.changeFeature(fieldConstraint, attribute, Boolean.valueOf(toggle.isSelected()));
		});
		add(toggle, new GridData(SWT.CENTER, SWT.CENTER, false, false));
		return toggle;
	}

	private void updateEnablement() {
		wholeWord.setEnabled(!exactMatch.isSelected() && !regularExpression.isSelected());
		exactMatch.setEnabled(!wholeWord.isSelected());
		regularExpression.setEnabled(!wholeWord.isSelected());
	}

	private static ImageDescriptor getTextEditorImage(final String path) {
		return ImageDescriptor.createFromURL(FileLocator.find(Platform.getBundle(TEXT_EDITOR_BUNDLE), new Path(path)));
	}
}
