/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dunja Životin - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.widgets;

import java.util.function.Consumer;

import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.widget.CommandExecutor;
import org.eclipse.fordiac.ide.util.IdentifierVerifyListener;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class PinInfoBasicWidget implements CommandExecutor {

	private Text nameText;
	private Text commentText;
	protected TypeSelectionWidget typeSelectionWidget;
	private IInterfaceElement type;

	protected final TabbedPropertySheetWidgetFactory widgetFactory;

	private Consumer<Command> commandExecutor;

	public PinInfoBasicWidget(final Composite parent, final TabbedPropertySheetWidgetFactory widgetFactory) {
		this.widgetFactory = widgetFactory;
		createWidget(parent);
	}

	protected void createWidget(final Composite parent) {

		GridLayoutFactory.swtDefaults().numColumns(2).generateLayout(parent);

		widgetFactory.createCLabel(parent, FordiacMessages.Name + ":"); //$NON-NLS-1$
		nameText = createText(parent);
		nameText.addVerifyListener(new IdentifierVerifyListener());
		nameText.addModifyListener(e -> executeCommand(new ChangeNameCommand(type, nameText.getText())));

		widgetFactory.createCLabel(parent, FordiacMessages.Comment + ":"); //$NON-NLS-1$
		commentText = createText(parent);
		commentText.addModifyListener(e -> executeCommand(new ChangeCommentCommand(type, commentText.getText())));

		widgetFactory.createCLabel(parent, FordiacMessages.Type + ":"); //$NON-NLS-1$
		typeSelectionWidget = new TypeSelectionWidget(widgetFactory);
		typeSelectionWidget.createControls(parent);
	}

	protected Text createText(final Composite parent) {
		final Text text = widgetFactory.createText(parent, "", SWT.BORDER); //$NON-NLS-1$
		text.setLayoutData(new GridData(SWT.FILL, 0, true, false));
		text.setEditable(true);
		text.setEnabled(true);
		return text;
	}

	public void disableAllFields() {
		nameText.setEnabled(false);
		commentText.setEnabled(false);

	}

	public void refresh() {
		if (type != null) {
			final Consumer<Command> commandExecutorBuffer = commandExecutor;
			commandExecutor = null;
			if (null != type.getName() && null != type.getComment()) {
				nameText.setText(type.getName());
				commentText.setText(type.getComment());
				typeSelectionWidget.refresh();
			}
			commandExecutor = commandExecutorBuffer;
		}

	}

	public void initialize(final IInterfaceElement type, final Consumer<Command> commandExecutor) {
		this.type = type;
		this.commandExecutor = commandExecutor;
	}

	@Override
	public void executeCommand(final Command cmd) {
		if (commandExecutor != null) {
			commandExecutor.accept(cmd);
		}
	}

	public Text getNameText() {
		return nameText;
	}

	public Text getCommentText() {
		return commentText;
	}

	public TypeSelectionWidget getTypeSelectionWidget() {
		return typeSelectionWidget;
	}

	public TabbedPropertySheetWidgetFactory getWidgetFactory() {
		return widgetFactory;
	}

	public void setNameText(final Text nameText) {
		this.nameText = nameText;
	}

	public void setCommentText(final Text commentText) {
		this.commentText = commentText;
	}

	public void setTypeSelectionWidget(final TypeSelectionWidget typeSelectionWidget) {
		this.typeSelectionWidget = typeSelectionWidget;
	}




}