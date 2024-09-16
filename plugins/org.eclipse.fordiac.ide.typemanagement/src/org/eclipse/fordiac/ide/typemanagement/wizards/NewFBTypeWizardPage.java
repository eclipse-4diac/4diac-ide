/*******************************************************************************
 * Copyright (c) 2010 - 2020 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 * 							 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Gerd Kainz
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - reworked type selection to a type list with description
 *   Bianca Wiesmayr - extracted TableViewer creation
 *   Daniel Lindhuber - added Data Type
 *   Lisa Sonnleithner - added duplicate check
 *   Martin Melik Merkumians - fixed Comment regex to accept score and underscore,
 *                         added case when description is null
 *                         replaced magic strings with constants for file endings
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.wizards;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.fordiac.ide.model.IdentifierVerifier;
import org.eclipse.fordiac.ide.model.buildpath.util.BuildpathUtil;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.model.ui.widgets.PackageSelectionProposalProvider;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.widget.TableWidgetFactory;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.eclipse.ui.fieldassist.ContentAssistCommandAdapter;

public class NewFBTypeWizardPage extends WizardNewFileCreationPage {
	private static final Pattern NAME_PATTERN = Pattern.compile("Name=\"\\w+\""); //$NON-NLS-1$
	private static final Pattern COMMENT_PATTERN = Pattern.compile("Comment=\"[\\w\\s-]+\""); //$NON-NLS-1$

	private Button openTypeCheckbox;
	private int openTypeParentHeight = -1;
	private boolean openType = true;
	private Text packageNameText;
	private TableViewer templateTableViewer;

	private static class TemplateInfo {
		private final File templateFile;
		private final String templateName;
		private final String templateDescription;

		public TemplateInfo(final File templateFile, final String templateName, final String templateDescription) {
			this.templateFile = templateFile;
			this.templateName = templateName;
			this.templateDescription = templateDescription;
		}
	}

	private TemplateInfo[] templateList;
	private FilteredArrayContentProvider templateProvider;

	private class FilteredArrayContentProvider extends ArrayContentProvider {
		private FileFilter templateFilter;

		public FilteredArrayContentProvider(final FileFilter filter) {
			templateFilter = filter;
		}

		public void setFileFilter(final FileFilter filter) {
			this.templateFilter = filter;
		}

		public FileFilter getFileFilter() {
			return templateFilter;
		}

		@Override
		public Object[] getElements(final Object inputElement) {
			final Object[] fullList = super.getElements(inputElement);
			if (templateFilter == null) {
				return fullList;
			}
			//@formatter:off
			return Arrays.asList(fullList)
					.stream()
					.filter(TemplateInfo.class::isInstance)
					.map(TemplateInfo.class::cast)
					.filter(t -> templateFilter.accept(t.templateFile))
					.toArray();
			//@formatter:on
		}
	}

	private static class TypeTemplatesLabelProvider extends LabelProvider implements ITableLabelProvider {

		@Override
		public Image getColumnImage(final Object element, final int columnIndex) {
			return null;
		}

		@Override
		public String getColumnText(final Object element, final int columnIndex) {
			if (element instanceof final TemplateInfo info) {
				switch (columnIndex) {
				case 0:
					return info.templateName;
				case 1:
					return info.templateDescription;
				default:
					break;
				}
			}
			return ""; //$NON-NLS-1$
		}
	}

	public NewFBTypeWizardPage(final IStructuredSelection selection) {
		super(Messages.NewFBTypeWizardPage_NewTypePage, selection);
		this.setTitle(Messages.NewFBTypeWizardPage_CreateNewType);
		this.setDescription(Messages.NewFBTypeWizardPage_CreateNewTypeFromTemplateType);
		this.setAllowExistingResources(true); // needed for correct duplicate type check
		loadTypeTemplates();
	}

	@Override
	public void createControl(final Composite parent) {
		super.createControl(parent);
		final Composite composite = (Composite) getControl();
		// Show description on opening
		setErrorMessage(null);
		setMessage(null);
		setControl(composite);
	}

	@Override
	protected boolean validatePage() {
		if ((null == templateList) || (0 == templateList.length)) {
			setErrorMessage(
					MessageFormat.format(Messages.NewFBTypeWizardPage_NoTypeTemplatesFoundCheckTemplatesDirectory,
							getTypeTemplatesFolder().toString()));
			return false;
		}
		if (getTypeName().isEmpty()) {
			setErrorMessage(Messages.NewFBTypeWizardPage_EmptyTypenameIsNotValid);
			return false;
		}

		final Optional<String> errorMessage = IdentifierVerifier.verifyIdentifier(getTypeName());
		if (errorMessage.isPresent()) {
			setErrorMessage(errorMessage.get());
			return false;
		}

		final Optional<String> packageNameErrorMessage = IdentifierVerifier.verifyPackageName(getPackageName());
		if (packageNameErrorMessage.isPresent()) {
			setErrorMessage(packageNameErrorMessage.get());
			return false;
		}

		if (null == getTemplate()) {
			setErrorMessage(Messages.NewFBTypeWizardPage_NoTypeSelected);
			return false;
		}

		if (isDuplicate()) {
			setErrorMessage(MessageFormat.format(Messages.NewFBTypeWizardPage_TypeAlreadyExists, getFullTypeName()));
			return false;
		}

		if (fileExists()) {
			setErrorMessage(MessageFormat.format(Messages.NewFBTypeWizardPage_FileAlreadyExists, getFileName()));
			return false;
		}

		return super.validatePage();
	}

	private boolean isDuplicate() {
		final TypeLibrary typeLibrary = getTypeLibrary();
		if (typeLibrary == null) {
			return false;
		}

		final String fullTypeName = getFullTypeName();
		final String fileExtension = IPath.fromFile(getTemplate()).getFileExtension();
		return switch (fileExtension.toUpperCase()) {
		case TypeLibraryTags.ATTRIBUTE_TYPE_FILE_ENDING -> typeLibrary.getAttributeTypeEntry(fullTypeName) != null;
		case TypeLibraryTags.GLOBAL_CONST_FILE_ENDING -> typeLibrary.getGlobalConstantsEntry(fullTypeName) != null;
		default -> typeLibrary.find(fullTypeName) != null;
		};
	}

	private boolean fileExists() {
		final String fileName = getFileName().toLowerCase();
		final IPath containerPath = getContainerFullPath();
		if (containerPath == null || containerPath.segmentCount() < 2) {
			return false;
		}
		final IFolder folder = ResourcesPlugin.getWorkspace().getRoot().getFolder(containerPath);
		if (!folder.exists()) {
			return false;
		}
		try {
			for (final IResource member : folder.members()) {
				if (fileName.equalsIgnoreCase(member.getName())) {
					return true;
				}
			}
		} catch (final CoreException e) {
			return false;
		}
		return false;
	}

	public File getTemplate() {
		if (templateTableViewer.getSelection() instanceof StructuredSelection) {
			final Object selection = templateTableViewer.getStructuredSelection().getFirstElement();
			return selection instanceof final TemplateInfo info ? info.templateFile : null;
		}
		return null;
	}

	public void setTemplateFileFilter(final FileFilter filter) {
		templateProvider.setFileFilter(filter);
		templateTableViewer.refresh();
	}

	public FileFilter getTemplateFileFilter() {
		return templateProvider.getFileFilter();
	}

	public TableViewer getTemplateViewer() {
		return templateTableViewer;
	}

	@Override
	protected String getNewFileLabel() {
		return FordiacMessages.TypeName + ":"; //$NON-NLS-1$
	}

	@Override
	public String getFileName() {
		String retval = super.getFileName();
		if (null != getTemplate()) {
			final String[] splited = getTemplate().getName().split("\\."); //$NON-NLS-1$
			if (2 == splited.length) {
				retval = retval + "." + splited[1]; //$NON-NLS-1$
			}
		}
		return retval;
	}

	public String getTypeName() {
		// use super.getFileName here to get the type name without extension
		return super.getFileName();
	}

	public String getFullTypeName() {
		final String packageName = getPackageName();
		if (!packageName.isBlank()) {
			return packageName + PackageNameHelper.PACKAGE_NAME_DELIMITER + getTypeName();
		}
		return getTypeName();
	}

	public boolean getOpenType() {
		return openType;
	}

	@Override
	protected void createAdvancedControls(final Composite parent) {
		createPackageNameSelection(parent);
		createTemplateTypeSelection(parent);
		super.createAdvancedControls(parent);
	}

	private void createPackageNameSelection(final Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(composite);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(composite);

		final Label packageNameLabel = new Label(composite, SWT.NONE);
		packageNameLabel.setText(FordiacMessages.Package + ":"); //$NON-NLS-1$
		packageNameLabel.setFont(parent.getFont());

		packageNameText = new Text(composite, SWT.BORDER);
		packageNameText.setText(getInitialPackageName());
		packageNameText.addListener(SWT.Modify, this);
		GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).hint(250, SWT.DEFAULT)
				.applyTo(packageNameText);

		final ContentAssistCommandAdapter packageNameProposalAdapter = new ContentAssistCommandAdapter(packageNameText,
				new TextContentAdapter(), new PackageSelectionProposalProvider(this::getTypeLibrary), null, null, true);
		packageNameProposalAdapter.setProposalAcceptanceStyle(ContentProposalAdapter.PROPOSAL_REPLACE);
	}

	private void createTemplateTypeSelection(final Composite parent) {
		final Label fbTypeTypeLabel = new Label(parent, SWT.NONE);
		fbTypeTypeLabel.setText(FordiacMessages.SelectType + ":"); //$NON-NLS-1$
		fbTypeTypeLabel.setFont(parent.getFont());

		templateTableViewer = TableWidgetFactory.createPropertyTableViewer(parent, SWT.SINGLE);
		configureTypeTableLayout(templateTableViewer.getTable());

		templateProvider = new FilteredArrayContentProvider(createTemplatesFileFilter());
		templateTableViewer.setContentProvider(templateProvider);
		templateTableViewer.setLabelProvider(new TypeTemplatesLabelProvider());

		templateTableViewer.setInput(templateList);

		templateTableViewer.addSelectionChangedListener(ev -> handleEvent(null));

		final GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL);
		data.widthHint = 250;
		templateTableViewer.getControl().setLayoutData(data);
	}

	private static void configureTypeTableLayout(final Table table) {
		TableColumn tc = new TableColumn(table, SWT.LEFT);
		tc.setText(FordiacMessages.Name);

		tc = new TableColumn(table, SWT.LEFT);
		tc.setText(FordiacMessages.Description);

		final TableLayout tabLayout = new TableLayout();
		tabLayout.addColumnData(new ColumnWeightData(1, 30));
		tabLayout.addColumnData(new ColumnWeightData(2, 70));
		table.setLayout(tabLayout);
	}

	private void loadTypeTemplates() {
		final File templateFolder = getTypeTemplatesFolder();
		final FileFilter ff = createTemplatesFileFilter();
		if (templateFolder.isDirectory()) {
			final File[] files = templateFolder.listFiles(ff);
			if (null != files) {
				Arrays.sort(files);
				templateList = new TemplateInfo[files.length];
				for (int i = 0; i < files.length; i++) {
					templateList[i] = createTemplateFileInfo(files[i]);
				}
			}
		}
	}

	private static File getTypeTemplatesFolder() {
		final String templateFolderPath = Platform.getInstallLocation().getURL().getFile();
		return new File(templateFolderPath + File.separatorChar + "template"); //$NON-NLS-1$
	}

	@SuppressWarnings("static-method") // this method is need to allow sub-classes to override it with specific filters
	protected FileFilter createTemplatesFileFilter() {
		return pathname -> pathname.getName().toUpperCase().endsWith(TypeLibraryTags.FB_TYPE_FILE_ENDING_WITH_DOT)
				|| pathname.getName().toUpperCase().endsWith(TypeLibraryTags.FC_TYPE_FILE_ENDING_WITH_DOT)
				|| pathname.getName().toUpperCase().endsWith(TypeLibraryTags.ADAPTER_TYPE_FILE_ENDING_WITH_DOT)
				|| pathname.getName().toUpperCase().endsWith(TypeLibraryTags.DATA_TYPE_FILE_ENDING_WITH_DOT)
				|| pathname.getName().toUpperCase().endsWith(TypeLibraryTags.GLOBAL_CONST_FILE_ENDING_WITH_DOT)
				|| pathname.getName().toUpperCase().endsWith(TypeLibraryTags.SUBAPP_TYPE_FILE_ENDING_WITH_DOT)
				|| pathname.getName().toUpperCase().endsWith(TypeLibraryTags.ATTRIBUTE_TYPE_FILE_ENDING_WITH_DOT);
	}

	private static TemplateInfo createTemplateFileInfo(final File f) {
		String name = f.getName();
		String description = ""; //$NON-NLS-1$
		try (Scanner nameScanner = new Scanner(f); Scanner commentScanner = new Scanner(f)) {
			name = nameScanner.findWithinHorizon(NAME_PATTERN, 0);
			name = name.substring(6, name.length() - 1);

			// we need a second scanner as name and comment may be in arbitrary order
			description = commentScanner.findWithinHorizon(COMMENT_PATTERN, 0);
			if (null == description) {
				description = Messages.NewFBTypeWizardPage_InvalidOrNoComment;
			} else {
				description = description.substring(9, description.length() - 1);
			}
		} catch (final FileNotFoundException e) {
			FordiacLogHelper.logError(Messages.NewFBTypeWizardPage_CouldNotFindTemplateFiles, e);
		}
		return new TemplateInfo(f, name, description);
	}

	@Override
	protected void handleAdvancedButtonSelect() {
		final Shell shell = getShell();
		final Point shellSize = shell.getSize();
		final Composite composite = (Composite) getControl();

		if (null != openTypeCheckbox) {
			openTypeCheckbox.dispose();
			openTypeCheckbox = null;
			shell.setSize(shellSize.x, shellSize.y - openTypeParentHeight);
		} else {
			openTypeCheckbox = createOpenTypeGroup(composite);
			if (-1 == openTypeParentHeight) {
				final Point groupSize = openTypeCheckbox.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
				openTypeParentHeight = groupSize.y;
			}
			shell.setSize(shellSize.x, shellSize.y + openTypeParentHeight);
		}
		super.handleAdvancedButtonSelect();
	}

	private Button createOpenTypeGroup(final Composite parent) {
		openTypeCheckbox = new Button(parent, SWT.CHECK);
		openTypeCheckbox.setText(Messages.NewFBTypeWizardPage_OpenTypeForEditingWhenDone);
		openTypeCheckbox.setSelection(true);
		setPageComplete(validatePage());
		openTypeCheckbox.addListener(SWT.Selection, ev -> openType = openTypeCheckbox.getSelection());
		return openTypeCheckbox;
	}

	public String getInitialPackageName() {
		final IContainer container = getSelectedContainer();
		if (container != null) {
			final TypeLibrary typeLibrary = TypeLibraryManager.INSTANCE.getTypeLibrary(container.getProject());
			final IPath relativePath = BuildpathUtil.findRelativePath(typeLibrary.getBuildpath(), container)
					.orElse(container.getFullPath());
			return Stream.of(relativePath.segments())
					.collect(Collectors.joining(PackageNameHelper.PACKAGE_NAME_DELIMITER));
		}
		return ""; //$NON-NLS-1$
	}

	public TypeLibrary getTypeLibrary() {
		final IContainer container = getSelectedContainer();
		if (container != null) {
			return TypeLibraryManager.INSTANCE.getTypeLibrary(container.getProject());
		}
		return null;
	}

	public IContainer getSelectedContainer() {
		final IPath containerFullPath = getContainerFullPath();
		if (containerFullPath != null && containerFullPath.segmentCount() > 0) {
			return containerFullPath.segmentCount() == 1
					? ResourcesPlugin.getWorkspace().getRoot().getProject(containerFullPath.segment(0))
					: ResourcesPlugin.getWorkspace().getRoot().getFolder(containerFullPath);
		}
		return null;
	}

	public void setPackageName(final String packageName) {
		packageNameText.setText(packageName);
	}

	public String getPackageName() {
		return packageNameText.getText();
	}
}
