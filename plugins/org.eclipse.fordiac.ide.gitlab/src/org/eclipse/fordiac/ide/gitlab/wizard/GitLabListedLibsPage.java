package org.eclipse.fordiac.ide.gitlab.wizard;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.eclipse.fordiac.ide.ui.widget.TableWidgetFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.gitlab4j.api.models.Package;

public class GitLabListedLibsPage extends WizardPage {
	
	private static final String PATH = "";
	private static final String DIRECTORY = ".fblib";
	private TableViewer viewer;

	protected GitLabListedLibsPage(String pageName) {
		super(pageName);
		setTitle(pageName);
		setDescription("Available packages in GitLab");
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        container.setLayout(layout);
        layout.numColumns = 1;
       
        viewer = TableWidgetFactory.createPropertyTableViewer(container,
        		SWT.CHECK | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
        viewer.setContentProvider(new ArrayContentProvider());
        createColumns(viewer);

        final Table table = viewer.getTable();
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
        table.setLayout(createTableLayout());
        
        table.addListener(SWT.Selection, event -> {
			if (event.detail == SWT.CHECK) {
				final TableItem tableItem = (TableItem) event.item;
				if (tableItem.getData() instanceof Package) {
					setPageComplete(true);
				}
			}
		});
        
        // required to avoid an error in the system
        setControl(container);
        setPageComplete(false);
	}
	
	private void createColumns(TableViewer viewer) {
		// Check-box column
		final TableViewerColumn colCheckBox = new TableViewerColumn(viewer, SWT.WRAP);
		colCheckBox.getColumn().setText("Download");

		colCheckBox.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				return ""; //$NON-NLS-1$
			}
		});
		
		// Package name column
		TableViewerColumn colPackageName = new TableViewerColumn(viewer, SWT.NONE);
		colPackageName.getColumn().setText("Package name");
		colPackageName.setLabelProvider(new ColumnLabelProvider() {
		    @Override
		    public String getText(Object element) {
		    	if (element instanceof Package) {
		    		Package p = (Package) element;
		    		return p.getName();
		    	}
		        return "";
		    }
		});
		
				
		// Version column
		TableViewerColumn colVersion = new TableViewerColumn(viewer, SWT.NONE);
		colVersion.getColumn().setText("Version");
		colVersion.setLabelProvider(new ColumnLabelProvider() {
		    @Override
		    public String getText(Object element) {
		    	if (element instanceof Package) {
		    		Package p = (Package) element;
		    		return p.getVersion();
		    	}
		        return "";
		    }
		});
	}
	
	private static TableLayout createTableLayout() {
		final TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnPixelData(200));
		layout.addColumnData(new ColumnPixelData(200));
		layout.addColumnData(new ColumnPixelData(100));
		return layout;
	}
	
	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		// Setting the input here insures that we have already connected to GitLab and that the packages are indeed available
		viewer.setInput(getPackages());
	}

	private List<Package> getPackages() {
		if (getPreviousPage() instanceof GitLabImportWizardPage && getPreviousPage().isPageComplete()) {
				return ((GitLabImportWizardPage) getPreviousPage()).getDownloadManager().getPackages();
		}
		return Collections.emptyList();
	}

	private void saveTypeLibrary() {
		File directory = new File(PATH + DIRECTORY);
		directory.mkdirs();
		
	}
	

}
