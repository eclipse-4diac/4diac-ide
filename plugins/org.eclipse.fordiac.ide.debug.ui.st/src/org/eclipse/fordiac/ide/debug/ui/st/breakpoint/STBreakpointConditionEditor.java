/*******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst
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
import java.util.stream.Stream;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.fordiac.ide.debug.st.breakpoint.STLineBreakpoint;
import org.eclipse.fordiac.ide.debug.ui.st.Messages;
import org.eclipse.fordiac.ide.debug.ui.st.util.STDebugUIUtil;
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
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IPropertyListener;
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
	private Composite conditionEditorComposite;
	private EmbeddedEditor conditionEditor;
	private EmbeddedEditorModelAccess conditionEditorModelAccess;

	private boolean dirty;

	public Control createControl(final Composite parent) {
		final Composite comp = new Composite(parent, SWT.NONE);
		GridLayoutFactory.swtDefaults().applyTo(comp);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(comp);

		conditional = new Button(comp, SWT.CHECK);
		conditional.setText(Messages.STBreakpointConditionEditor_Conditional);
		conditional.setEnabled(false);
		conditional.addSelectionListener(SelectionListener.widgetSelectedAdapter(e -> {
			conditionEditor.getViewer().setEditable(input != null && conditional.getSelection());
			firePropertyChange(PROP_CONDITION_ENABLED);
			setDirty(true);
		}));
		GridDataFactory.fillDefaults().grab(true, false).applyTo(conditional);

		conditionEditorComposite = new Composite(comp, SWT.NONE);
		GridLayoutFactory.fillDefaults().applyTo(conditionEditorComposite);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(conditionEditorComposite);

		createConditionPlaceholder(conditionEditorComposite);

		return comp;
	}

	@SuppressWarnings("static-method")
	protected Control createConditionPlaceholder(final Composite parent) {
		final Text conditionText = new Text(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		conditionText.setEditable(false);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(conditionText);
		return conditionText;
	}

	protected Control createConditionEditor(final Composite parent) {
		final IEditedResourceProvider editedResourceProvider = createEditedResourceProvider();
		conditionEditor = STAlgorithmEmbeddedEditorUtil.getEmbeddedEditorFactory().newEditor(editedResourceProvider)
				.withParent(parent);
		conditionEditorModelAccess = conditionEditor.createPartialEditor("", input.getCondition(), //$NON-NLS-1$
				"", false);  //$NON-NLS-1$
		conditionEditor.getViewer().setEditable(input.isConditionEnabled());
		conditionEditor.getDocument().addDocumentListener(new IDocumentListener() {
			@Override
			public void documentChanged(final DocumentEvent event) {
				dirty = true;
				firePropertyChange(PROP_CONDITION);
			}

			@Override
			public void documentAboutToBeChanged(final DocumentEvent event) {
				// do nothing
			}
		});
		SourceViewerColorProvider.initializeSourceViewerColors(conditionEditor.getViewer());
		return conditionEditor.getViewer().getControl();
	}

	protected IEditedResourceProvider createEditedResourceProvider() {
		try {
			return new STAlgorithmConditionEditedResourceProvider(input.getFBType(),
					STDebugUIUtil.getAdditionalScope(input.getMarker().getResource(), input.getLineNumber()));
		} catch (final CoreException e) {
			FordiacLogHelper.logError("Couldn't get breakpoint attributes", e); //$NON-NLS-1$
		}
		return new STAlgorithmConditionEditedResourceProvider(null, Collections.emptyList());
	}

	protected void updateConditionEditor() {
		conditionEditor = null;
		conditionEditorModelAccess = null;
		Stream.of(conditionEditorComposite.getChildren()).forEach(Control::dispose);
		if (input != null) {
			createConditionEditor(conditionEditorComposite);
		} else {
			createConditionPlaceholder(conditionEditorComposite);
		}
		conditionEditorComposite.layout();
	}

	public void setInput(final STLineBreakpoint input) {
		if (this.input == input) {
			if (conditionEditorModelAccess != null && input != null) {
				conditionEditorModelAccess.updateModel(input.getCondition());
			}
		} else {
			this.input = input;
			updateConditionEditor();
		}
		conditional.setEnabled(input != null);
		conditional.setSelection(input != null && input.isConditionEnabled());
		setDirty(false);
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
		this.dirty = dirty;
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
		for (final IPropertyListener listener : listeners) {
			listener.propertyChanged(this, property);
		}
	}
}
