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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.bulkeditor.Messages;
import org.eclipse.fordiac.ide.bulkeditor.query.QueryModelHelper;
import org.eclipse.fordiac.ide.bulkeditor.query.QueryModelHelper.FieldConstraintData;
import org.eclipse.fordiac.ide.bulkeditor.query.figures.QueryNodeFigure.EditableValue;
import org.eclipse.fordiac.ide.bulkeditor.query.figures.QueryNodeFigure.FeatureChangeHandler;
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

	private final EObject fieldConstraint;
	private final FeatureChangeHandler changeHandler;
	private final Label valueLabel = QueryValueLabel.createFixedWidth(VALUE_WIDTH);
	private final QueryToggleButton caseSensitive;
	private final QueryToggleButton wholeWord;
	private final QueryToggleButton exactMatch;
	private final QueryToggleButton regularExpression;

	public FieldConstraintFigure(final EObject fieldConstraint, final FeatureChangeHandler changeHandler) {
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
				QueryModelHelper.FEATURE_CASE_SENSITIVE);
		wholeWord = addToggle(FordiacImage.ICON_WHOLE_WORD.getImageDescriptor(), Messages.WholeWord,
				QueryModelHelper.FEATURE_WHOLE_WORD);
		exactMatch = addToggle(getTextEditorImage(EXACT_MATCH_IMAGE), Messages.ExactMatch,
				QueryModelHelper.FEATURE_ENTIRE);
		regularExpression = addToggle(getTextEditorImage(REGULAR_EXPRESSION_IMAGE), Messages.RegularExpression,
				QueryModelHelper.FEATURE_REGEX);
	}

	public EObject getFieldConstraint() {
		return fieldConstraint;
	}

	public EditableValue getEditableValue() {
		return new EditableValue(valueLabel, fieldConstraint, QueryModelHelper.FEATURE_VALUE);
	}

	/** Updates the figure to the current state of its field constraint. */
	public void refresh() {
		final FieldConstraintData data = QueryModelHelper.readFieldConstraint(fieldConstraint);
		valueLabel.setText(data.value());
		caseSensitive.setSelected(data.caseSensitive());
		wholeWord.setSelected(data.wholeWord());
		exactMatch.setSelected(data.entire());
		regularExpression.setSelected(data.regex());
		updateEnablement();
	}

	private QueryToggleButton addToggle(final ImageDescriptor image, final String tooltip, final String featureName) {
		final QueryToggleButton toggle = new QueryToggleButton(image);
		toggle.setToolTip(new Label(tooltip));
		toggle.addActionListener(_ -> {
			updateEnablement();
			changeHandler.changeFeature(fieldConstraint, featureName, Boolean.valueOf(toggle.isSelected()));
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
