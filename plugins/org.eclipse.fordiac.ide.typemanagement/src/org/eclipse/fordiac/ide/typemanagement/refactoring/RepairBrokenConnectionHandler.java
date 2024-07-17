package org.eclipse.fordiac.ide.typemanagement.refactoring;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.ui.editors.DataTypeTreeSelectionDialog;
import org.eclipse.fordiac.ide.model.ui.nat.DataTypeSelectionTreeContentProvider;
import org.eclipse.fordiac.ide.model.ui.nat.TypeNode;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

public class RepairBrokenConnectionHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final ISelection sel = HandlerUtil.getCurrentSelection(event);
		final IEditorPart editor = HandlerUtil.getActiveEditor(event);
		final CommandStack commandStack = editor.getAdapter(CommandStack.class);
		if (sel instanceof final IStructuredSelection ssel && ssel.getFirstElement() instanceof final EditPart part
				&& part.getModel() instanceof final ErrorMarkerInterface errormarker) {
			final RepairBrokenConnectionWizard wizard = new RepairBrokenConnectionWizard(errormarker);
			final WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getActivePage().getActiveEditor().getSite().getShell(), wizard);
			dialog.create();

			if (dialog.open() == Window.OK) {
				final CompoundCommand repairCommands = new CompoundCommand();
				System.out.println("Hello");
				(errormarker.isIsInput() ? errormarker.getInputConnections() : errormarker.getOutputConnections())
						.forEach(con -> {
							repairCommands.add(new RepairBrokenConnectionCommand(con, !errormarker.isIsInput(),
									wizard.getType(), wizard.getVar()));

						});
				commandStack.execute(repairCommands);
			}

		}
		return null;
	}

	private class RepairBrokenConnectionWizard extends Wizard {
		private RepairBrokenConnectionWizardPage repairPage;
		private final TypeLibrary lib;
		private final DataType errorType;
		private StructuredType type;
		private String var;

		public RepairBrokenConnectionWizard(final ErrorMarkerInterface errormarker) {
			setWindowTitle("Repair broken Connection");
			this.lib = errormarker.getFBNetworkElement().getTypeLibrary();
			this.errorType = errormarker.getType();
		}

		@Override
		public boolean performFinish() {
			type = repairPage.getType();
			var = repairPage.getVar();
			return type != null && var != null;
		}

		@Override
		public void addPages() {
			repairPage = new RepairBrokenConnectionWizardPage("Repair broken Connection", lib, errorType);
			addPage(repairPage);
		}

		public StructuredType getType() {
			return type;
		}

		public String getVar() {
			return var;
		}

	}

	private class RepairBrokenConnectionWizardPage extends WizardPage {
		private final TypeLibrary lib;
		private final DataType errorType;
		private StructuredType type;
		private String var;

		protected RepairBrokenConnectionWizardPage(final String pageName, final TypeLibrary lib,
				final DataType errorType) {
			super(pageName);
			this.setTitle("Select Repair Connection");
			this.setDescription("Select a Structured Type and a compatiple Member Variable.");
			this.lib = lib;
			this.errorType = errorType;
		}

		@Override
		public void createControl(final Composite parent) {
			final Composite container = new Composite(parent, SWT.NONE);
			final GridData containerData = new GridData();
			containerData.horizontalAlignment = GridData.FILL;
			containerData.grabExcessHorizontalSpace = true;
			container.setLayoutData(containerData);

			final GridLayout gridl = new GridLayout();
			gridl.numColumns = 2;
			gridl.marginWidth = 0;
			container.setLayout(gridl);

//			final Label structLabel = new Label(container, SWT.NONE);
//			structLabel.setText("SelectStruct");
			final Text currentType = new Text(container, SWT.AUTO_TEXT_DIRECTION);
			currentType.setText("ANY_STRUCT"); //$NON-NLS-1$
			currentType.setEditable(false);
			final Button structButton = new Button(container, SWT.NONE);
			structButton.setText("...");

			final Table varTable = new Table(container, SWT.FULL_SELECTION | SWT.SINGLE | SWT.BORDER);
			varTable.setLinesVisible(true);
			varTable.setHeaderVisible(true);
			final GridData tableData = new GridData();
			tableData.horizontalAlignment = GridData.FILL;
			tableData.grabExcessHorizontalSpace = true;
			tableData.grabExcessVerticalSpace = true;
			tableData.heightHint = 200;
			tableData.horizontalSpan = 2;
			varTable.setLayoutData(tableData);
			TableColumn column = new TableColumn(varTable, SWT.NONE);
			column.setText("Name");
			column = new TableColumn(varTable, SWT.NONE);
			column.setText("Type");

			structButton.addListener(SWT.Selection, event -> {
				final DataTypeSelectionTreeContentProvider instance = DataTypeSelectionTreeContentProvider.INSTANCE;
				final DataTypeTreeSelectionDialog dialog = new DataTypeTreeSelectionDialog(getShell(), instance);
				dialog.setInput(lib);

				if ((dialog.open() == Window.OK) && (dialog.getFirstResult() instanceof final TypeNode node
						&& !node.isDirectory() && node.getType() instanceof final StructuredType structType)) {
					type = structType;
					currentType.setText(node.getFullName());
					varTable.removeAll();
					type.getMemberVariables().forEach(var -> {
						if (var.getType().equals(errorType)) {
							final TableItem item = new TableItem(varTable, SWT.NONE);
							item.setText(0, var.getName());
							item.setText(1, var.getTypeName());
						}
						varTable.getColumn(0).pack();
						varTable.getColumn(1).pack();
					});
				}

			});

			varTable.addListener(SWT.Selection, event -> {
				var = varTable.getSelection()[0].getText(0);
				this.setPageComplete(true);
			});

			setControl(container);
		}

		@Override
		public boolean isPageComplete() {
			return type != null && var != null;
		}

		public StructuredType getType() {
			return type;
		}

		private String getVar() {
			return var;
		}

	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		setBaseEnabled(isRepairable());
	}

	private static boolean isRepairable() {
		final IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (window != null) {
			final IStructuredSelection selection = (IStructuredSelection) window.getSelectionService().getSelection();
			return selection.size() == 1 && selection.getFirstElement() instanceof final EditPart part
					&& part.getModel() instanceof ErrorMarkerInterface;
		}
		return false;
	}
}
