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
 *   Dario Romano
 *    - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.commands.change.ChangeDataTypeCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeStructCommand;
import org.eclipse.fordiac.ide.model.commands.change.ConfigureFBCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarker;
import org.eclipse.fordiac.ide.model.libraryElement.ArraySize;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableFB;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerDataType;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerFBNElement;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.search.AbstractLiveSearchContext;
import org.eclipse.fordiac.ide.model.search.types.DataTypeInstanceSearch;
import org.eclipse.fordiac.ide.model.typelibrary.ErrorDataTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.ui.editors.DataTypeTreeSelectionDialog;
import org.eclipse.fordiac.ide.model.ui.nat.DataTypeSelectionTreeContentProvider;
import org.eclipse.fordiac.ide.model.ui.nat.TypeNode;
import org.eclipse.fordiac.ide.typemanagement.util.ErrorMarkerResolver;
import org.eclipse.fordiac.ide.typemanagement.wizards.NewFBTypeWizardPage;
import org.eclipse.fordiac.ide.typemanagement.wizards.NewTypeWizard;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.views.markers.MarkerItem;

public class RepairCommandHandler extends AbstractHandler {

	private IStructuredSelection selection;

	private enum Choices {
		CREATE_MISSING_TYPE(FordiacMessages.Dialog_Repair_Pin),
		DELETE_AFFECTED_ELEMENTS(FordiacMessages.Delete_Elements),
		CHANGE_TYPE(FordiacMessages.RepairCommandHandler_ChangeType0);

		private EObject result;

		private final String text;

		Choices(final String s) {
			text = s;
		}

		@Override
		public String toString() {
			return text;
		}

		public void setResult(final EObject result) {
			this.result = result;
		}

		public EObject getResult() {
			return result;
		}

	}

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		selection = HandlerUtil.getCurrentStructuredSelection(event);
		EObject eObject = getEObjectFromProblemViewSelection(selection);

		if (eObject == null) {
			eObject = getEObjectFromEditorSelection(selection);
		}

		repair(eObject); // varDecl
		return null;
	}

	private void openMissingDataTypeWizard(final EObject type) {

		final ChooseRepairOperationPage<Choices> choosePage = new ChooseRepairOperationPage<>(
				FordiacMessages.Delete_Elements, type);

		final var wizard = new Wizard() {
			@Override
			public boolean performFinish() {

				Choices choice = null;
				choice = choosePage.getSelection();
				switch (choice) {
				case CHANGE_TYPE:
					handleChangeDataType(choice);
					break;
				case CREATE_MISSING_TYPE:
					if (type instanceof final VarDeclaration varDeclaration) {
						ErrorMarkerResolver.repairMissingStructuredDataType(varDeclaration);
					}
					break;
				case DELETE_AFFECTED_ELEMENTS:
					// TODO needs to be implemented
					break;
				default:
					break;
				}
				return true;
			}

			private void handleChangeDataType(final Choices choice) {
				final var typeChoice = choice.getResult();
				if (type instanceof final IInterfaceElement interfaceElement
						&& interfaceElement.getType().getTypeEntry() instanceof final ErrorDataTypeEntry errorEntry) {
					final DataTypeInstanceSearch search = new DataTypeInstanceSearch(errorEntry,
							getTypeLibrary(interfaceElement));
					final List<? extends EObject> result = search.performSearch();
					for (final var r : result) {
						if (r instanceof final IInterfaceElement iE && typeChoice instanceof final DataType d) {
							AbstractLiveSearchContext.executeAndSave(ChangeDataTypeCommand.forDataType(iE, d), iE,
									new NullProgressMonitor());
						}

						if (r instanceof final StructManipulator fb && typeChoice instanceof final DataType d) {
							AbstractLiveSearchContext.executeAndSave(new ChangeStructCommand(fb, d), fb,
									new NullProgressMonitor());
						} else if (r instanceof final ConfigurableFB fb && typeChoice instanceof final DataType d) {
							AbstractLiveSearchContext.executeAndSave(new ConfigureFBCommand(fb, d), fb,
									new NullProgressMonitor());
						}

					}

				}
			}

			@Override
			public IWizardPage getNextPage(final IWizardPage page) {
				return null;
			}

			@Override
			public boolean canFinish() {
				return true;
			}

		};
		final WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage().getActiveEditor().getSite().getShell(), wizard);
		final ActionListener alist = e -> dialog.updateButtons();

		choosePage.addButtonListener(alist);
		wizard.addPage(choosePage);
		dialog.create();
		dialog.open();

	}

	private void repair(final EObject eObject) {

		if (eObject instanceof final VarDeclaration varDecl && varDecl.getType() instanceof ErrorMarkerDataType) {
			repairMissingDataType(varDecl);
		}

		if (eObject instanceof FBNetworkElement) {
			handleMissingBlockType();
		}
	}

	private void handleMissingBlockType() {
		// TODO Open wizard for Missing Block
	}

	private void repairMissingDataType(final VarDeclaration errorDataType) {
		openMissingDataTypeWizard(errorDataType);
	}

	public static EObject getEObjectFromEditorSelection(final IStructuredSelection sel) {
		Object model = sel.getFirstElement();

		if (model instanceof final EditPart editPart) {
			model = editPart.getModel();
		}

		return getEObjectFromEditor(model);

	}

	public static EObject getEObjectFromEditor(final Object model) {

		if (model instanceof final ArraySize arraySize
				&& arraySize.eContainer() instanceof final VarDeclaration varDecl) {
			return getEObjectFromEditor(varDecl);
		}

		if (model instanceof final VarDeclaration varDecl && varDecl.getType() instanceof ErrorMarkerDataType) {
			return varDecl;
		}

		// missing pin
		if (model instanceof final ErrorMarkerInterface ie) {
			return ie;
		}

		// missing block
		if (model instanceof final ErrorMarkerFBNElement errorBlock) {
			return errorBlock;
		}
		return null;
	}

	private static EObject getEObjectFromProblemViewSelection(final IStructuredSelection sel) {

		final Object firstElement = sel.getFirstElement();

		// selection from problem view
		if (firstElement instanceof final MarkerItem item) {
			EObject eObj = getEObjectFromMarkerItem(item);
			// this should already be validated by the property tester therefore we want to
			// know early if this fails.

			if (eObj instanceof final ArraySize arraySize
					&& arraySize.eContainer() instanceof final VarDeclaration varDecl) {
				eObj = varDecl;
			}

			Assert.isNotNull(eObj);

			return eObj;
		}

		return null;
	}

	public static EObject getEObjectFromMarkerItem(final MarkerItem item) {
		return FordiacErrorMarker.getTarget(item.getMarker());
	}

	private void showRestrictedNewTypeWizard(final String name, final String fileEnding, final String projectName) {
		final NewTypeWizard wizard = new NewTypeWizard() {
			@Override
			protected NewFBTypeWizardPage createNewFBTypeWizardPage() {
				return new NewFBTypeWizardPage(selection);
			}

		};
		final WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage().getActiveEditor().getSite().getShell(), wizard);
		dialog.create();
		final NewFBTypeWizardPage page = (NewFBTypeWizardPage) dialog.getCurrentPage();
		page.setTemplateFileFilter(pathname -> pathname.getName().toUpperCase().endsWith(fileEnding));
		page.setFileName(name);

		final TableViewer tv = page.getTemplateViewer();
		final Object selection = tv.getElementAt(0);

		// have to check if any template at all could be found
		if (selection != null) {
			tv.setSelection(new StructuredSelection(selection), true);
			// unlock table if there are possible selections
			if (tv.getElementAt(1) == null) {
				tv.getTable().setEnabled(false);
			}
		}
		restrictName(page.getControl());
		restrictProjectSelection(page.getControl(), projectName);
		if (Window.OK == dialog.open()) {
			// the type could be created new execute the command
			final TypeEntry newEntry = wizard.getTypeEntry();
			newEntry.refresh();
		}
	}

	private static void restrictName(final Control control) {
		final Composite root = (Composite) control;

		final List<Control> children = getFlattenedList(root);
		final List<Control> textfields = children.stream().filter(Text.class::isInstance).toList();
		//@formatter:off
		textfields.stream()
			.filter(c -> Arrays.stream(c.getParent().getChildren())
					.filter(Label.class::isInstance)
					.map(Label.class::cast)
					.anyMatch(l -> l.getText().equals(FordiacMessages.TypeName+":"))) //$NON-NLS-1$
			.forEach(c -> c.setEnabled(false));
		//@formatter:on

	}

	private static void restrictProjectSelection(final Control control, final String projectName) {
		final Composite root = (Composite) control;
		final List<Control> elements = getFlattenedList(root);
		//@formatter:off
		elements.stream()
			.filter(Tree.class::isInstance)
			.map(Tree.class::cast) //get the tree
			.flatMap(a-> Arrays.stream(a.getItems())) //get all root elements
			.filter(i -> !i.getText().equals(projectName)) //find all elements which are not the current project
			.forEach(TreeItem::dispose); //dispose them, so only the current project is left
		//@formatter:on
		control.redraw();
	}

	private static List<Control> getFlattenedList(final Composite root) {
		final List<Control> flattenedSet = new ArrayList<>();
		if (root.getChildren().length == 0) {
			flattenedSet.add(root);
			return flattenedSet;
		}
		for (final Control c : root.getChildren()) {
			if (c instanceof final Composite com) {
				flattenedSet.addAll(getFlattenedList(com));
			} else {
				flattenedSet.add(c);
			}
		}
		return flattenedSet;
	}

	private class ChooseRepairOperationPage<T extends Enum<T>> extends WizardPage {
		private Choices choice;
		private final EObject eObj;

		private final List<ActionListener> listeners = new ArrayList<>();

		protected ChooseRepairOperationPage(final String pageName, final EObject eObj) {
			super(pageName);
			this.choice = Choices.CHANGE_TYPE;// make default selection work
			this.eObj = eObj;
		}

		public void addButtonListener(final ActionListener l) {
			listeners.add(l);
		}

		@Override
		public void createControl(final Composite parent) {
			Composite container;
			container = new Composite(parent, SWT.NONE);
			final GridLayout layout = new GridLayout();
			container.setLayout(layout);
			layout.numColumns = 1;

			final Button changeTypeButton = new Button(container, SWT.RADIO);
			final Group group = new Group(container, SWT.NONE);
			group.setEnabled(true);
			group.setLayout(new GridLayout(2, false));
			changeTypeButton.setText(Choices.CHANGE_TYPE.toString());
			changeTypeButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(final SelectionEvent e) {
					super.widgetSelected(e);
					if (changeTypeButton.getSelection()) {
						choice = Choices.CHANGE_TYPE;
						choice.setResult(IecTypes.GenericTypes.ANY_STRUCT);
					}
				}
			});

			final Text currentType = new Text(group, SWT.AUTO_TEXT_DIRECTION);
			currentType.setText("ANY_STRUCT"); //$NON-NLS-1$
			currentType.setEditable(false);

			final Button openDialogButton = new Button(group, SWT.PUSH);

			openDialogButton.setText("..."); //$NON-NLS-1$
			openDialogButton.addListener(SWT.Selection, e -> {
				final DataTypeSelectionTreeContentProvider instance = DataTypeSelectionTreeContentProvider.INSTANCE;
				// instance.inputChanged(null, typeSelectionButton, e)

				final DataTypeTreeSelectionDialog dialog = new DataTypeTreeSelectionDialog(getShell(), instance);
				dialog.setInput(getTypeLibrary(eObj));
				if ((dialog.open() == Window.OK)
						&& (dialog.getFirstResult() instanceof final TypeNode node && !node.isDirectory())) {
					choice.setResult(node.getType());
					currentType.setText(node.getFullName());

				}
			});

			final Button creatTypeButton = new Button(container, SWT.RADIO);
			creatTypeButton.setText(Choices.CREATE_MISSING_TYPE.toString());
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
			this.setDescription(FordiacMessages.Delete_Elements);
			this.setTitle(FordiacMessages.Dialog_Repair_Pin);
			getShell().pack();
			this.setPageComplete(true);
		}

		public Choices getSelection() {
			return choice;
		}

	}

	public static TypeLibrary getTypeLibrary(final EObject eObj) {
		if (EcoreUtil.getRootContainer(eObj) instanceof final LibraryElement le) {
			return le.getTypeLibrary();
		}
		return null;
	}
}
