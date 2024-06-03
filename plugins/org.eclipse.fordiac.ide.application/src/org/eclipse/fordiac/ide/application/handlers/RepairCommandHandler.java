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
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.application.editparts.ErrorMarkerFBNEditPart;
import org.eclipse.fordiac.ide.application.editparts.ErrorMarkerInterfaceEditPart;
import org.eclipse.fordiac.ide.gef.editparts.AbstractConnectableEditPart;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarker;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerDataType;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.impl.ErrorMarkerDataTypeImpl;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.typemanagement.util.TypeCreator;
import org.eclipse.fordiac.ide.typemanagement.wizards.NewFBTypeWizardPage;
import org.eclipse.fordiac.ide.typemanagement.wizards.NewTypeWizard;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.views.markers.MarkerItem;

public class RepairCommandHandler extends AbstractHandler {

	private IStructuredSelection sel;

	private enum Choices {
		CREATE_MISSING_TYPE(FordiacMessages.Dialog_Repair_Pin), DELETE_AFFECTED_ELEMENTS(FordiacMessages.Delete_Elements);

		private final String text;

		Choices(final String s) {
			text = s;
		}

		@Override
		public String toString() {
			return text;
		}

	}

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final TypeLibrary typeLib;

		IEditorPart editor;
		sel = HandlerUtil.getCurrentStructuredSelection(event);
		editor = HandlerUtil.getActiveEditor(event);
		final EObject eObject = getEObjectFromSelection(sel);

		if (eObject instanceof final VarDeclaration varDecl
				&& varDecl.getType() instanceof final ErrorMarkerDataType errorDataType) {

			openWizard(varDecl);

		}

		return null;
	}

	private void openWizard(final EObject type) {
		final ChooseActionWizardPage<Choices> choosePage = new ChooseActionWizardPage<>(FordiacMessages.Delete_Elements,
				Choices.class);
		final ChangedTypesWizardPage infoPage = new ChangedTypesWizardPage("Repair Missing type:" + type.toString());
		final var wizard = new Wizard() {

			@Override
			public boolean performFinish() {
				// TODO depending on choice, search instances with faulty pin, and delete pins
				// or simply add pin to typentry.
				Choices choice = null;
				choice = choosePage.getSelection();
				switch (choice) {
				case CREATE_MISSING_TYPE:
					System.out.println("CREATE_MISSING_TYPE");
					TypeCreator.repairMissingDataType(type);
					break;
				case DELETE_AFFECTED_ELEMENTS:
					//not supported yet
					System.out.println("REMove");
					break;
				default:
					break;
				}
				return true;
			}

			@Override
			public IWizardPage getNextPage(final IWizardPage page) {
				if ((page instanceof final ChooseActionWizardPage<?> choosePage)
						&& choosePage.choice.equals(Choices.DELETE_AFFECTED_ELEMENTS)) {
					return Arrays.stream(getPages()).filter(ChangedTypesWizardPage.class::isInstance).findFirst()
							.orElse(null);
				}
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
		infoPage.setPreviousPage(choosePage);
		wizard.addPage(infoPage);

		dialog.create();
		dialog.open();

	}

	private static TypeLibrary getTypeLibraryFromEditorInput(final IEditorInput input) {
		return TypeLibraryManager.INSTANCE.getTypeEntryForFile(((FileEditorInput) input).getFile()).getTypeLibrary();
	}

	private void repairEditPart(final AbstractConnectableEditPart editPart, final String projectName) {
		final ErrorMarkerDataType dataTypeMarker = editPart.getAdapter(ErrorMarkerDataTypeImpl.class);
		if (dataTypeMarker != null) {
			showRestrictedNewTypeWizard(dataTypeMarker.getTypeEntry().getTypeName(),
					TypeLibraryTags.DATA_TYPE_FILE_ENDING_WITH_DOT, projectName);
		}
		final ErrorMarkerFBNEditPart fBNEditPartMarker = editPart.getAdapter(ErrorMarkerFBNEditPart.class);
		if (fBNEditPartMarker != null) {
			showRestrictedNewTypeWizard(((FBNetworkElement) fBNEditPartMarker.getINamedElement()).getTypeName(),
					TypeLibraryTags.FB_TYPE_FILE_ENDING_WITH_DOT, projectName);
		}
		final ErrorMarkerInterfaceEditPart interfaceEditPart = editPart.getAdapter(ErrorMarkerInterfaceEditPart.class);
		if (interfaceEditPart != null) {
			showAddOrRemoveWizard(interfaceEditPart);
		}
	}

	private void showAddOrRemoveWizard(final ErrorMarkerInterfaceEditPart interfaceEditPart) {
		final ChooseActionWizardPage<Choices> choosePage = new ChooseActionWizardPage<>(FordiacMessages.Delete_Elements,
				Choices.class);
		final ChangedTypesWizardPage infoPage = new ChangedTypesWizardPage("Pending changes");

		final var wizard = new Wizard() {

			@Override
			public boolean performFinish() {
				// TODO depending on choice, search instances with faulty pin, and delete pins
				// or simply add pin to typentry.
				Choices choice = null;
				choice = choosePage.getSelection();
				switch (choice) {
				case CREATE_MISSING_TYPE:
					System.out.println("CREATE_MISSING_TYPE");
					break;
				case DELETE_AFFECTED_ELEMENTS:
					System.out.println("Remove");
					break;
				default:
					break;
				}
				return true;
			}

			@Override
			public IWizardPage getNextPage(final IWizardPage page) {
				if ((page instanceof final ChooseActionWizardPage<?> choosePage)
						&& choosePage.choice.equals(Choices.DELETE_AFFECTED_ELEMENTS)) {
					return Arrays.stream(getPages()).filter(ChangedTypesWizardPage.class::isInstance).findFirst()
							.orElse(null);
				}
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
		infoPage.setPreviousPage(choosePage);
		wizard.addPage(infoPage);

		dialog.create();
		dialog.open();

	}

	private static void removeInterfaceElementFromInstances(final ErrorMarkerInterfaceEditPart elem) {
//		TODO use the element to remove similar pins from all instances
//		final FBType te = ((FBType) elem.getParent().getModel()).getTypeEntry().getTypeEditable();
//		te.getInterfaceList().getAllInterfaceElements().removeIf(el -> {
//			System.out.println(el);
//			return true;
//		});
	}

	private static EObject getEObjectFromSelection(final IStructuredSelection sel) {

		final Object firstElement = sel.getFirstElement();

		// selection from problem view
		if (firstElement instanceof final MarkerItem item) {
			final EObject eObj = getEObjectFromMarkerItem(item);
			// this should already be validated by the property tester therefore we want to
			// know early if this fails.
			Assert.isNotNull(eObj);

			return eObj;
		}

		return null;
	}

	public static EObject getEObjectFromMarkerItem(final MarkerItem item) {
		final IMarker marker = item.getMarker();
		try {
			final EObject target = FordiacErrorMarker.getTarget(marker);
			if (target != null) {
				return target;
			}

		} catch (IllegalArgumentException | CoreException e) {
			return null;
		}
		return null;
	}

	private void showRestrictedNewTypeWizard(final String name, final String fileEnding, final String projectName) {
		final NewTypeWizard wizard = new NewTypeWizard() {
			@Override
			protected NewFBTypeWizardPage createNewFBTypeWizardPage() {
				return new NewFBTypeWizardPage(sel);
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

	private class ChangedTypesWizardPage extends WizardPage {

		protected ChangedTypesWizardPage(final String pageName) {
			super(pageName);
		}

		@Override
		public void createControl(final Composite parent) {
			Composite container;
			container = new Composite(parent, SWT.NONE);
			final GridLayout layout = new GridLayout();
			container.setLayout(layout);

			// search instances with pin and delete pins.
			/*
			 * final FBTypeSearchResultTable table = new FBTypeSearchResultTable(container,
			 * new FBTypeEntryDataHandler(type));
			 */

			// table.setLayout(layout);

			this.setControl(container);
			this.setDescription(FordiacMessages.Dialog_Repair_Remove);
			this.setTitle(FordiacMessages.Dialog_Repair_Pin);
		}

	}

	private class ChooseActionWizardPage<T extends Enum<T>> extends WizardPage {
		private Class<T> choicesEnum = null;
		private T choice;

		private final List<ActionListener> listeners = new ArrayList<>();

		protected ChooseActionWizardPage(final String pageName, final Class<T> choicesEnum) {
			super(pageName);
			this.choicesEnum = choicesEnum;
			this.choice = choicesEnum.getEnumConstants()[0];// make default selection work
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
//			@formatter:off
			Arrays.stream(choicesEnum.getEnumConstants())
			.forEach(s -> {
				final Button b = new Button(container, SWT.RADIO);
				b.setText(s.toString());
				b.addSelectionListener(new SelectionAdapter(){
				    @Override
				    public void widgetSelected(final SelectionEvent e){
				        super.widgetSelected(e);
				        if(b.getSelection()){
				            choice=s;
				            listeners.forEach(l-> l.actionPerformed(null));
				        }
				    }
				});
			});
//			@formatter:on
			this.setControl(container);
			this.setDescription(FordiacMessages.Delete_Elements);
			this.setTitle(FordiacMessages.Dialog_Repair_Pin);
			getShell().pack();
			this.setPageComplete(true);
		}

		public T getSelection() {
			return choice;
		}

	}

}
