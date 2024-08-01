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
 *   Sebastian Hollersbacher
 *    - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.wizards;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.commands.change.UpdateFBTypeCommand;
import org.eclipse.fordiac.ide.model.commands.change.UpdateInternalFBCommand;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerFBNElement;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.search.AbstractLiveSearchContext;
import org.eclipse.fordiac.ide.model.search.types.BlockTypeInstanceSearch;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.ui.editors.DataTypeTreeSelectionDialog;
import org.eclipse.fordiac.ide.model.ui.nat.FBTypeSelectionTreeContentProvider;
import org.eclipse.fordiac.ide.model.ui.nat.TypeNode;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class MissingFBTypeWizard extends AbstractMissingTypeWizard {
	private static final String PAGE_NAME = "FBRepairPage"; //$NON-NLS-1$

	public MissingFBTypeWizard(final ErrorMarkerFBNElement type) {
		super(type);
	}

	@Override
	protected AbstractRepairOperationPage createPage() {
		return new FBRepairOperationPage(PAGE_NAME);
	}

	@Override
	protected void handleChangeType() {
		if (result instanceof final FBType fbtype && fbtype.getTypeEntry() instanceof final FBTypeEntry typeEntry) {
			searchAndUpdate(typeEntry);
		}
	}

	@Override
	protected void handleCreateMissing() {
		if (errorType instanceof final FBNetworkElement fbnElement) {
			final TypeEntry typeEntry = RestrictedNewTypeWizard.showRestrictedNewTypeWizard(
					fbnElement.getType().getName(), ".fbt", fbnElement.getTypeLibrary().getProject().getName()); //$NON-NLS-1$

			if (typeEntry instanceof final FBTypeEntry fbtypeEntry) {
				searchAndUpdate(fbtypeEntry);
			}
		}
	}

	private void searchAndUpdate(final FBTypeEntry newEntry) {
		final BlockTypeInstanceSearch search = new BlockTypeInstanceSearch(
				((ErrorMarkerFBNElement) errorType).getTypeEntry(), newEntry.getTypeLibrary().getProject());
		final var res = search.performSearch();
		for (final EObject el : res) {
			if (el instanceof final FB fb && fb.eContainer() instanceof final BaseFBType base
					&& base.getInternalFbs().contains(fb)) {
				AbstractLiveSearchContext.executeAndSave(new UpdateInternalFBCommand(fb, newEntry), fb,
						new NullProgressMonitor());
			}
			if (el instanceof final ErrorMarkerFBNElement errorFB) {
				AbstractLiveSearchContext.executeAndSave(new UpdateFBTypeCommand(errorFB, newEntry), errorFB,
						new NullProgressMonitor());
			}
		}
	}

	private class FBRepairOperationPage extends AbstractRepairOperationPage {
		protected FBRepairOperationPage(final String pageName) {
			super(pageName);
		}

		@Override
		public void createControl(final Composite parent) {
			final Composite container = new Composite(parent, SWT.NONE);
			container.setLayout(new GridLayout(3, false));

			final Label selectionLabel = new Label(container, 0);
			final Button openDialogButton = new Button(container, SWT.PUSH);
			openDialogButton.setText("..."); //$NON-NLS-1$
			openDialogButton.addListener(SWT.Selection, e -> {
				final DataTypeTreeSelectionDialog dialog = new DataTypeTreeSelectionDialog(getShell(),
						FBTypeSelectionTreeContentProvider.INSTANCE);
				dialog.setInput(getTypeLibrary(errorType));
				if ((dialog.open() == Window.OK)
						&& (dialog.getFirstResult() instanceof final TypeNode node && !node.isDirectory())) {
					result = node.getType();
					selectionLabel.setText("Selected: " + node.getType().getName()); //$NON-NLS-1$
					selectionLabel.pack();
				}
			});

			final Button changeTypeButton = new Button(container, SWT.RADIO);
			changeTypeButton.setText(FordiacMessages.Repair_Dialog_ChangeFBType);
			changeTypeButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(final SelectionEvent e) {
					super.widgetSelected(e);
					if (changeTypeButton.getSelection()) {
						openDialogButton.setVisible(true);
						selectionLabel.setVisible(true);
						choice = Choices.CHANGE_TYPE;
					} else {
						openDialogButton.setVisible(false);
						selectionLabel.setVisible(false);
					}
				}
			});

			openDialogButton.moveAbove(selectionLabel);
			changeTypeButton.moveAbove(openDialogButton);

			final Button creatTypeButton = new Button(container, SWT.RADIO);
			creatTypeButton.setText(FordiacMessages.Repair_Dialog_New_FB);
			creatTypeButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(final SelectionEvent e) {
					super.widgetSelected(e);
					if (creatTypeButton.getSelection()) {
						choice = Choices.CREATE_MISSING_TYPE;
					}
				}
			});

			this.setControl(container);
			this.setTitle(FordiacMessages.Repair_Dialog_FB_Title);
			getShell().pack();
			this.setPageComplete(true);
		}
	}
}
