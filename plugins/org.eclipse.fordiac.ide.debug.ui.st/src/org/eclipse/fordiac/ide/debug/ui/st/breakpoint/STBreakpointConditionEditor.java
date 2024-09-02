/*******************************************************************************
 * Copyright (c) 2022-2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.debug.ui.st.breakpoint;

import java.util.Collections;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.fordiac.ide.debug.st.breakpoint.STLineBreakpoint;
import org.eclipse.fordiac.ide.debug.ui.st.Messages;
import org.eclipse.fordiac.ide.debug.ui.st.util.STDebugUIUtil;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
import org.eclipse.fordiac.ide.structuredtextalgorithm.ui.editor.embedded.STAlgorithmConditionEditedResourceProvider;
import org.eclipse.fordiac.ide.structuredtextalgorithm.ui.editor.embedded.STAlgorithmEmbeddedEditorUtil;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.fordiac.ide.ui.providers.SourceViewerColorProvider;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.IWorkbenchPartConstants;
import org.eclipse.xtext.ui.editor.embedded.EmbeddedEditor;
import org.eclipse.xtext.ui.editor.embedded.EmbeddedEditorModelAccess;
import org.eclipse.xtext.ui.editor.embedded.IEditedResourceProvider;

@SuppressWarnings("restriction")
public class STBreakpointConditionEditor {
	public static final int PROP_CONDITION = 1;
	public static final int PROP_CONDITION_ENABLED = 2;

	private final ListenerList<IPropertyListener> listeners = new ListenerList<>();

	private STLineBreakpoint input;

	private Button conditional;
	private EmbeddedEditor conditionEditor;
	private EmbeddedEditorModelAccess conditionEditorModelAccess;

	private boolean dirty;
	private boolean suppressPropertyChanges;

	public Control createControl(final Composite parent) {
		final Composite comp = new Composite(parent, SWT.NONE);
		GridLayoutFactory.swtDefaults().applyTo(comp);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(comp);

		conditional = new Button(comp, SWT.CHECK);
		conditional.setText(Messages.STBreakpointConditionEditor_Conditional);
		conditional.setEnabled(false);
		conditional.addSelectionListener(SelectionListener.widgetSelectedAdapter(e -> {
			if (conditionEditor != null) {
				conditionEditor.getViewer().setEditable(input != null && conditional.getSelection());
			}
			firePropertyChange(PROP_CONDITION_ENABLED);
			setDirty(true);
		}));
		GridDataFactory.fillDefaults().grab(true, false).applyTo(conditional);

		createConditionEditor(comp);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(conditionEditor.getViewer().getControl());

		return comp;
	}

	protected void createConditionEditor(final Composite parent) {
		final IEditedResourceProvider editedResourceProvider = new STAlgorithmConditionEditedResourceProvider(null,
				Collections.emptyList(), ElementaryTypes.BOOL);
		conditionEditor = STAlgorithmEmbeddedEditorUtil.getEmbeddedEditorFactory().newEditor(editedResourceProvider)
				.withParent(parent);
		conditionEditorModelAccess = conditionEditor.createPartialEditor();
		conditionEditor.getViewer().setEditable(false);
		conditionEditor.getDocument().addDocumentListener(new IDocumentListener() {
			@Override
			public void documentChanged(final DocumentEvent event) {
				firePropertyChange(PROP_CONDITION);
				setDirty(true);
			}

			@Override
			public void documentAboutToBeChanged(final DocumentEvent event) {
				// do nothing
			}
		});
		SourceViewerColorProvider.initializeSourceViewerColors(conditionEditor.getViewer());
	}

	protected void updateConditionEditor() {
		if (input != null) {
			try {
				STAlgorithmEmbeddedEditorUtil.updateEditor(conditionEditor, input.getFBType().eResource().getURI(),
						input.getFBType(),
						STDebugUIUtil.getAdditionalScope(input.getMarker().getResource(), input.getLineNumber()),
						ElementaryTypes.BOOL);
			} catch (final CoreException e) {
				FordiacLogHelper.logError("Couldn't get breakpoint attributes", e); //$NON-NLS-1$
				STAlgorithmEmbeddedEditorUtil.updateEditor(conditionEditor, null, null, null, ElementaryTypes.BOOL);
			}
			conditionEditorModelAccess.updateModel(input.getCondition());
		} else {
			STAlgorithmEmbeddedEditorUtil.updateEditor(conditionEditor, null, null, null, ElementaryTypes.BOOL);
			conditionEditorModelAccess.updateModel(""); //$NON-NLS-1$
		}
	}

	public void setInput(final STLineBreakpoint input) {
		try {
			suppressPropertyChanges = true;
			this.input = input;
			conditional.setEnabled(input != null);
			conditional.setSelection(input != null && input.isConditionEnabled());
			conditionEditor.getViewer().setEditable(input != null && input.isConditionEnabled());
			updateConditionEditor();
			setDirty(false);
		} finally {
			suppressPropertyChanges = false;
		}
	}

	public void doSave() {
		try {
			if (input != null && input.getMarker() != null && input.getMarker().exists()) {
				input.setCondition(conditionEditorModelAccess.getEditablePart());
				input.setConditionEnabled(conditional.getSelection());
			}
			setDirty(false);
		} catch (final CoreException e) {
			FordiacLogHelper.logError("Couldn't set breakpoint condition on " + input, e); //$NON-NLS-1$
		}
	}

	public boolean isDirty() {
		return dirty;
	}

	public void setDirty(final boolean dirty) {
		if (dirty != this.dirty) {
			this.dirty = dirty;
			firePropertyChange(IWorkbenchPartConstants.PROP_DIRTY);
		}
	}

	public boolean setFocus() {
		return conditionEditor.getViewer().getControl().setFocus();
	}

	public void dispose() {
		listeners.clear();
	}

	public void addPropertyListener(final IPropertyListener listener) {
		listeners.add(listener);
	}

	public void removePropertyListener(final IPropertyListener listener) {
		listeners.remove(listener);
	}

	protected void firePropertyChange(final int property) {
		if (!suppressPropertyChanges) {
			for (final IPropertyListener listener : listeners) {
				listener.propertyChanged(this, property);
			}
		}
	}
}
