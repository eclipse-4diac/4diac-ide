/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mario Kastner
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.typemanagement.wizards;

import java.util.Optional;
import java.util.function.Supplier;

import org.eclipse.fordiac.ide.model.IdentifierVerifier;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.ui.widgets.PackageSelectionProposalProvider;
import org.eclipse.fordiac.ide.typemanagement.refactoring.IFordiacModifiablePreviewChange;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.ltk.ui.refactoring.ChangePreviewViewerInput;
import org.eclipse.ltk.ui.refactoring.IChangePreviewViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.fieldassist.ContentAssistCommandAdapter;

public class MoveToPackageChangeViewer implements IChangePreviewViewer {
	private Composite control;
	private IFordiacModifiablePreviewChange change;
	private Text nameText;

	@Override
	public void createControl(final Composite parent) {
		control = new Composite(parent, SWT.FILL);

		GridLayoutFactory.fillDefaults().numColumns(2).margins(new Point(5, 5)).applyTo(control);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(control);

		final CLabel label = new CLabel(control, SWT.NONE);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(label);
		label.setText(Messages.MoveToPackageChangePreview_EnterPackageName + ":"); //$NON-NLS-1$

		this.nameText = new Text(control, SWT.BORDER | SWT.FILL);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(nameText);
		nameText.setEnabled(false);

		final ControlDecoration errorDecorator = createErrorDecorator(nameText);
		nameText.addModifyListener(e -> {
			if (change != null) {
				final String text = nameText.getText();
				final Optional<String> errorMessage = IdentifierVerifier.verifyPackageName(text);
				if (errorMessage.isPresent()) {
					errorDecorator.setDescriptionText(errorMessage.get());
					errorDecorator.show();
				} else {
					change.updateChange(nameText.getText());
					errorDecorator.hide();
				}
			}
		});

		final Supplier<TypeLibrary> typeLibrary = () -> change.getType().getTypeLibrary();
		final ContentAssistCommandAdapter packageNameProposalAdapter = new ContentAssistCommandAdapter(nameText,
				new TextContentAdapter(), new PackageSelectionProposalProvider(typeLibrary), null, null, true);
		packageNameProposalAdapter.setProposalAcceptanceStyle(ContentProposalAdapter.PROPOSAL_REPLACE);
	}

	@Override
	public Control getControl() {
		return control;
	}

	@Override
	public void setInput(final ChangePreviewViewerInput input) {
		if (input.getChange() instanceof final IFordiacModifiablePreviewChange previewChange) {
			this.change = previewChange;
			if (nameText != null) {
				nameText.setText(change.getText());
				if (change.isEditable()) {
					nameText.setEnabled(true);
				}
			}
		}
	}

	private static ControlDecoration createErrorDecorator(final Control control) {
		final ControlDecoration errorDecorator = new ControlDecoration(control, SWT.TOP | SWT.RIGHT);
		final Image img = FieldDecorationRegistry.getDefault().getFieldDecoration(FieldDecorationRegistry.DEC_ERROR)
				.getImage();
		errorDecorator.setImage(img);
		errorDecorator.hide();
		return errorDecorator;
	}

}
