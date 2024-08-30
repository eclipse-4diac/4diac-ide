/*******************************************************************************
 * Copyright (c) 2015, 2024 fortiss GmbH, Johannes Kepler University Linz,
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Bianca Wiesmayr - consistent dropdown menu edit
 *   Virendra Ashiwal, Bianca Wiesmayr
 *   - Used extracted method from ECCContentAndLabelProvider
 *   Martin Jobst - adopt ST editor for transition conditions
 *   Alois Zoitl  - updated for new adapter FB handling
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.properties;

import java.util.Collections;

import org.eclipse.fordiac.ide.fbtypeeditor.ecc.Messages;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.ChangeConditionEventCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.ChangeConditionExpressionCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.ChangeECTransitionCommentCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.contentprovider.ECCContentAndLabelProvider;
import org.eclipse.fordiac.ide.gef.properties.AbstractSection;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.structuredtextalgorithm.ui.editor.embedded.STAlgorithmConditionEditedResourceProvider;
import org.eclipse.fordiac.ide.structuredtextalgorithm.ui.editor.embedded.STAlgorithmEmbeddedEditorUtil;
import org.eclipse.fordiac.ide.ui.providers.SourceViewerColorProvider;
import org.eclipse.fordiac.ide.ui.widget.ComboBoxWidgetFactory;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.xtext.ui.editor.embedded.EmbeddedEditor;
import org.eclipse.xtext.ui.editor.embedded.EmbeddedEditorModelAccess;
import org.eclipse.xtext.ui.editor.embedded.IEditedResourceProvider;

/**
 * Section that appears in the Properties view, when a Transition is selected in
 * the ECC
 */
@SuppressWarnings("restriction")
public class TransitionSection extends AbstractSection {
	private Text commentText;

	private Composite conditionEditingContainer;
	private CCombo eventCombo;

	private EmbeddedEditor conditionEditor;
	private EmbeddedEditorModelAccess conditionEditorModelAccess;

	@Override
	protected ECTransition getType() {
		return (ECTransition) type;
	}

	protected BasicFBType getBasicFBType() {
		final ECTransition type = getType();
		if (type != null) {
			final ECC ecc = type.getECC();
			if (ecc != null) {
				return ecc.getBasicFBType();
			}
		}
		return null;
	}

	@Override
	protected Object getInputType(Object input) {
		if (input instanceof final EditPart ep) {
			input = ep.getModel();
		}
		if (input instanceof ECTransition) {
			return input;
		}
		return null;
	}

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		final Composite composite = getWidgetFactory().createComposite(parent);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(composite);
		GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(composite);
		getWidgetFactory().createCLabel(composite, Messages.TransitionSection_Condition);

		createConditionEditingContainer(composite);
		GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(conditionEditingContainer);

		getWidgetFactory().createCLabel(composite, Messages.TransitionSection_Comment);
		commentText = createGroupText(composite, true);
		commentText.addModifyListener(event -> {
			removeContentAdapter();
			executeCommand(new ChangeECTransitionCommentCommand(getType(), commentText.getText()));
			addContentAdapter();
		});
	}

	private void createConditionEditingContainer(final Composite parent) {
		conditionEditingContainer = getWidgetFactory().createComposite(parent);
		GridLayoutFactory.fillDefaults().numColumns(4).applyTo(conditionEditingContainer);

		eventCombo = ComboBoxWidgetFactory.createCombo(getWidgetFactory(), conditionEditingContainer);
		eventCombo.addListener(SWT.Selection, event -> {
			removeContentAdapter();
			executeCommand(new ChangeConditionEventCommand(getType(), eventCombo.getText()));
			updateConditionEditor();
			addContentAdapter();
		});

		getWidgetFactory().createCLabel(conditionEditingContainer, "["); //$NON-NLS-1$

		createConditionEditor(conditionEditingContainer);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(conditionEditor.getViewer().getControl());

		getWidgetFactory().createCLabel(conditionEditingContainer, "]"); //$NON-NLS-1$
	}

	protected void createConditionEditor(final Composite parent) {
		final IEditedResourceProvider editedResourceProvider = new STAlgorithmConditionEditedResourceProvider(
				getBasicFBType(), Collections.emptySet(), ElementaryTypes.BOOL);
		conditionEditor = STAlgorithmEmbeddedEditorUtil.getEmbeddedEditorFactory().newEditor(editedResourceProvider)
				.withStyle(SWT.SINGLE | SWT.BORDER).withParent(parent);
		conditionEditorModelAccess = conditionEditor.createPartialEditor();
		conditionEditor.getViewer().setEditable(false);
		conditionEditor.getDocument().addDocumentListener(new IDocumentListener() {
			@Override
			public void documentChanged(final DocumentEvent event) {
				removeContentAdapter();
				// updating the condition editor or modification lead to this change
				if (conditionEditorModelAccess.getEditablePart().contentEquals("")) { //$NON-NLS-1$
					if (!ECCContentAndLabelProvider.isOneConditionExpression(getType())
							|| getType().getConditionEvent() != null) {
						executeCommand(new ChangeConditionExpressionCommand(getType(), ""));//$NON-NLS-1$
					}
				} else {
					executeCommand(new ChangeConditionExpressionCommand(getType(),
							conditionEditorModelAccess.getEditablePart()));
				}
				addContentAdapter();
			}

			@Override
			public void documentAboutToBeChanged(final DocumentEvent event) {
				// do nothing
			}
		});
		SourceViewerColorProvider.initializeSourceViewerColors(conditionEditor.getViewer());
	}

	protected void updateConditionEditor() {
		if (ECCContentAndLabelProvider.isOneConditionExpression(getType())) {
			conditionEditorModelAccess.updateModel(""); //$NON-NLS-1$
			conditionEditor.getViewer().setEditable(false);
		} else {
			conditionEditorModelAccess.updateModel(getType().getConditionExpression());
			conditionEditor.getViewer().setEditable(true);
		}
	}

	@Override
	protected void setInputInit() {
		STAlgorithmEmbeddedEditorUtil.updateEditor(conditionEditor, getType().eResource().getURI(), getBasicFBType(),
				null, ElementaryTypes.BOOL);
	}

	@Override
	protected void setInputCode() {
		commentText.setEnabled(false);
		eventCombo.removeAll();
		eventCombo.setEnabled(false);
		conditionEditor.getViewer().setEditable(false);
	}

	@Override
	protected void performRefresh() {
		if (null != getBasicFBType()) {
			fillEventConditionDropdown();
			commentText.setText(getType().getComment() != null ? getType().getComment() : ""); //$NON-NLS-1$
			if (ECCContentAndLabelProvider.isOneConditionExpression(getType())) {
				eventCombo.select(eventCombo.indexOf(ECCContentAndLabelProvider.ONE_CONDITION));
			} else {
				final Event conditionEvent = getType().getConditionEvent();
				eventCombo.select(
						conditionEvent != null ? getEventIndex(conditionEvent) : (eventCombo.getItemCount() - 1));
			}
			updateConditionEditor();
		}
	}

	private int getEventIndex(final Event conditionEvent) {
		return eventCombo.indexOf(ECCContentAndLabelProvider.getEventName(conditionEvent));
	}

	public void fillEventConditionDropdown() {
		eventCombo.removeAll();
		ECCContentAndLabelProvider.getTransitionConditionEventNames(getBasicFBType()).stream()
				.forEach(name -> eventCombo.add(name));
	}
}
