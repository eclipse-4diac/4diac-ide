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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.attributetypeeditor.editors.AttributeTypeEditor;
import org.eclipse.fordiac.ide.gef.properties.AbstractSection;
import org.eclipse.fordiac.ide.model.AttributeInheritMode;
import org.eclipse.fordiac.ide.model.AttributeTarget;
import org.eclipse.fordiac.ide.model.commands.change.ChangeInheritAttributeCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeTargetAttributeCommand;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.datatype.helper.InternalAttributeDeclarations;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
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
	private static final String TARGET_CATEGORY = "category"; //$NON-NLS-1$

	private final List<Button> buttons = new ArrayList<>();
	private StructuredType lock;

	private Button inheritButton;
	private Button copyButton;

	private final SelectionListener buttonListener = new SelectionAdapter() {
		@Override
		public void widgetSelected(final SelectionEvent event) {
			final Button btn = (Button) event.getSource();
			lock.getMemberVariables().stream().filter(member -> AttributeTarget.checkTargetName(member.getName(),
					btn.getText(), (String) btn.getParent().getData(TARGET_CATEGORY))).findFirst()
					.ifPresent(correctMember -> {
						final Value val = correctMember.getValue();
						val.setValue(Boolean.toString(btn.getSelection()).toUpperCase());
						executeCommand(new ChangeTargetAttributeCommand(getType(), lock));
						refresh();
					});
		}
	};

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		createCheckBoxes(parent);
		createInheritButtons(parent);
	}

	public void createCheckBoxes(final Composite parent) {
		final Composite composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(7, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final Composite ungroupedComposite = getWidgetFactory().createComposite(composite);
		ungroupedComposite.setData(TARGET_CATEGORY, AttributeTarget.EMPTY_GROUP);
		ungroupedComposite.setLayout(new GridLayout(1, false));
		ungroupedComposite.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));

		final Map<String, Composite> groups = new HashMap<>();
		final StructuredType targetType = (StructuredType) InternalAttributeDeclarations.TARGET.getType();

		targetType.getMemberVariables().stream().map(member -> AttributeTarget.fromName(member.getName()))
				.filter(Objects::nonNull).forEach(target -> {
					final String category = target.getCategory();
					Composite container = composite;

					if (category.equals(AttributeTarget.EMPTY_GROUP)) {
						container = ungroupedComposite;
					} else {
						container = groups.computeIfAbsent(category, cat -> {
							final Composite group = getWidgetFactory().createComposite(composite);
							group.setLayout(new GridLayout(1, false));
							group.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));

							getWidgetFactory().createLabel(group, cat);
							group.setData(TARGET_CATEGORY, cat);
							return group;
						});
					}
					createButton(container, target);
				});

	}

	private void createButton(final Composite parent, final AttributeTarget target) {
		final Button button = getWidgetFactory().createButton(parent, target.getDisplayName(), SWT.CHECK);
		button.setToolTipText(target.getToolTip());
		button.addSelectionListener(buttonListener);
		buttons.add(button);
	}

	public void createInheritButtons(final Composite parent) {
		final Composite composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(1, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		getWidgetFactory().createLabel(composite, Messages.AttributeInherit_SectionTitle);

		inheritButton = getWidgetFactory().createButton(parent, Messages.AttributeInherit_InheritAttribute, SWT.CHECK);
		inheritButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent event) {
				executeCommand(new ChangeInheritAttributeCommand(getType(), getInheritModeFromButtons()));
			}
		});

		copyButton = getWidgetFactory().createButton(parent, Messages.AttributeInherit_CopyAttribute, SWT.CHECK);
		copyButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent event) {
				executeCommand(new ChangeInheritAttributeCommand(getType(), getInheritModeFromButtons()));
			}
		});
		updateInheritButtons();
	}

	@Override
	public void performRefresh() {
		updateButtons(getType());
		updateInheritButtons();
	}

	private void updateButtons(final AttributeDeclaration attributeDeclaration) {
		final StructuredType targetStruct = getTarget(attributeDeclaration);
		targetStruct.getMemberVariables().forEach(member -> {
			for (final Button button : buttons) {
				if (AttributeTarget.checkTargetName(member.getName(), button.getText(),
						(String) button.getParent().getData(TARGET_CATEGORY))) {
					button.setSelection(Boolean.parseBoolean(member.getValue().getValue()));
				}
			}
		});
	}

	private void updateInheritButtons() {
		final Attribute inheritAttribute = getType().getAttribute(InternalAttributeDeclarations.INHERIT.getName());
		final AttributeInheritMode mode = inheritAttribute != null
				? AttributeInheritMode.valueOf(inheritAttribute.getValue())
				: AttributeInheritMode.IGNORE;

		inheritButton.setSelection(mode == AttributeInheritMode.COPY_INHERIT || mode == AttributeInheritMode.INHERIT);
		copyButton.setSelection(mode == AttributeInheritMode.COPY_INHERIT || mode == AttributeInheritMode.COPY);
	}

	private static StructuredType getTarget(final AttributeDeclaration attributeDeclaration) {
		final StructuredType targetStruct = attributeDeclaration.getTarget();
		if (targetStruct == null || targetStruct.getMemberVariables().stream()
				.anyMatch(member -> member.getName().equals(IInterfaceElement.class.getSimpleName())
						|| member.getName().equals(SubApp.class.getSimpleName()))) {
			// no/old lock
			return (StructuredType) EcoreUtil.copy(InternalAttributeDeclarations.TARGET.getType());
		}
		return targetStruct;
	}

	private AttributeInheritMode getInheritModeFromButtons() {
		if (inheritButton.getSelection() && copyButton.getSelection()) {
			return AttributeInheritMode.COPY_INHERIT;
		}
		if (copyButton.getSelection()) {
			return AttributeInheritMode.COPY;
		}
		if (inheritButton.getSelection()) {
			return AttributeInheritMode.INHERIT;
		}
		return AttributeInheritMode.IGNORE;
	}

	@Override
	public void setInput(final IWorkbenchPart part, final ISelection selection) {
		super.setInput(part, selection);
		if (part instanceof final AttributeTypeEditor editor) {
			setType(editor.getAdapter(LibraryElement.class));
			lock = getTarget(getType());
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
			lock = getTarget(attDecl);
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
