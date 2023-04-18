package org.eclipse.fordiac.ide.gitlab.wizard;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.fordiac.ide.gitlab.management.GitLabDownloadManager;
import org.eclipse.fordiac.ide.gitlab.treeviewer.GLTreeContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.gitlab4j.api.models.Package;
import org.gitlab4j.api.models.Project;

public class GitLabListedLibsPage extends WizardPage {
	
	private TreeViewer treeViewer;
	private Package selectedPackage;
	private Object parentItem;

	protected GitLabListedLibsPage(String pageName) {
		super(pageName);
		setTitle(pageName);
		setDescription("Available packages in GitLab");
	}
	
	@Override
	public void setPageComplete(boolean complete) {
		super.setPageComplete(complete);
		if (complete) {
			try {
				((GitLabImportWizardPage) getPreviousPage()).getDownloadManager().packageDownloader(selectedPackage, parentItem);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
	    container.setLayoutData(gd);
	    
        GridLayout layout = new GridLayout(1, true);
        container.setLayout(layout);
        
        treeViewer = new TreeViewer(container, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
        
        
        treeViewer.getTree().setHeaderVisible(true);
        treeViewer.getTree().setLinesVisible(true);
        treeViewer.getTree().setLayoutData(new GridData(GridData.FILL_BOTH));
       
        createColumns(treeViewer);
        
        Tree tree = (Tree) treeViewer.getControl();
        tree.addSelectionListener(new SelectionAdapter() {
          @Override
          public void widgetSelected(SelectionEvent e) {
              TreeItem item = (TreeItem) e.item;
                if (item.getItemCount() > 0 && item.getData() instanceof Package) {
                	selectedPackage = ((Package) item.getData());
                	parentItem = item.getParentItem().getData();
                	setPageComplete(true);
                }
            }
        });
        
        treeViewer.expandAll();
        // required to avoid an error in the system
        setControl(container);
        setPageComplete(false);
	}
	
	private void createColumns(TreeViewer viewer) {
		// Projects and packages column
		TreeViewerColumn viewerColumn = new TreeViewerColumn(viewer, SWT.NONE);
        viewerColumn.getColumn().setWidth(500);
        viewerColumn.getColumn().setText("Packages and projects");
        viewerColumn.setLabelProvider(new ColumnLabelProvider() {
        	@Override
        	public String getText(Object element) {
        		if (element instanceof Project) {
        			return ((Project) element).getName();
        		} else if (element instanceof Package) {
        			return ((Package) element).getName();
        		} else if (element instanceof String) {
        			return (String) element;
        		}
        		return "";
        	}
        });
	}
	
	
	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		// Setting the input here insures that we have already connected to GitLab and that the packages are indeed available
		treeViewer.setContentProvider(new GLTreeContentProvider(((GitLabImportWizardPage)getPreviousPage()).getDownloadManager().getPackagesAndVersions()));
		treeViewer.setInput(getProjectAndPackagesMap());
	}

	private Map<Project, List<Package>> getProjectAndPackagesMap() {
		if (getPreviousPage() instanceof GitLabImportWizardPage && getPreviousPage().isPageComplete()) {
				return ((GitLabImportWizardPage)getPreviousPage()).getDownloadManager().getProjectsAndPackages();
		}
		return new HashMap<>(); 
	}

}
