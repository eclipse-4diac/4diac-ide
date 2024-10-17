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
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.attributetypeeditor.properties;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.attributetypeeditor.editors.AttributeTypeEditor;
import org.eclipse.fordiac.ide.gef.properties.AbstractSection;
import org.eclipse.fordiac.ide.model.commands.change.ChangeTargetAttributeCommand;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.datatype.helper.InternalAttributeDeclarations;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class AttributeTargetSection extends AbstractSection {
	private final List<Button> buttons = new ArrayList<>();
	private StructuredType target;

	private final SelectionListener buttonListener = new SelectionAdapter() {
		@Override
		public void widgetSelected(final SelectionEvent event) {
			final Button btn = (Button) event.getSource();
			target.getMemberVariables().stream().filter(member -> member.getName().equals(btn.getText())).findFirst()
					.ifPresent(correctMember -> {
						final Value val = correctMember.getValue();
						val.setValue(Boolean.toString(btn.getSelection()).toUpperCase());
						executeCommand(new ChangeTargetAttributeCommand(getType(), target));
						refresh();
					});
		}
	};

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		createCheckBoxes(parent);
	}

	public void createCheckBoxes(final Composite parent) {
		final Composite composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		((StructuredType) InternalAttributeDeclarations.TARGET.getType()).getMemberVariables()
				.forEach(member -> createButton(composite, member.getName()));
	}

	private void createButton(final Composite parent, final String buttonName) {
		final Button button = new Button(parent, SWT.CHECK);
		button.setBackground(parent.getBackground());
		button.setText(buttonName);
		button.addSelectionListener(buttonListener);
		buttons.add(button);
	}

	@Override
	public void performRefresh() {
		updateButtons(getType());
	}

	private void updateButtons(final AttributeDeclaration attributeDeclaration) {
		final StructuredType targetStruct = getTarget(attributeDeclaration);
		targetStruct.getMemberVariables().forEach(member -> {
			for (final Button button : buttons) {
				if (member.getName().equals(button.getText())) {
					button.setSelection(Boolean.parseBoolean(member.getValue().getValue()));
				}
			}
		});
	}

	private static StructuredType getTarget(final AttributeDeclaration attributeDeclaration) {
		final StructuredType targetStruct = attributeDeclaration.getTarget();
		if (targetStruct == null) {
			return (StructuredType) EcoreUtil.copy(InternalAttributeDeclarations.TARGET.getType());
		}
		return targetStruct;
	}

	@Override
	public void setInput(final IWorkbenchPart part, final ISelection selection) {
		super.setInput(part, selection);
		if (part instanceof final AttributeTypeEditor editor) {
			setType(editor.getAdapter(LibraryElement.class));
			target = getTarget(getType());
		}
	}

	@Override
	protected AttributeDeclaration getType() {
		return type instanceof final AttributeDeclaration decl ? decl : null;
	}

	@Override
	protected Object getInputType(final Object input) {
		if (input instanceof final AttributeDeclaration attDecl) {
			updateButtons(attDecl);
			target = getTarget(attDecl);
			return attDecl;
		}
		return null;
	}

	@Override
	protected void setInputCode() {
		// nothing to do here
	}

	@Override
	protected void setInputInit() {
		// nothing to do here
	}
}
