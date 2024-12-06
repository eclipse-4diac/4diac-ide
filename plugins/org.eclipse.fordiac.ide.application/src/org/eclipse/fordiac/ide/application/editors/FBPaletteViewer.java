/*******************************************************************************
 * Copyright (c) 2015, 2017 fortiss GmbH
 * 				 2019 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *               - added automatic tree expansion on searching
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editors;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.typemanagement.util.TypeListPatternFilter;
import org.eclipse.gef.ui.palette.PaletteViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.ui.navigator.CommonViewer;
import org.eclipse.ui.navigator.CommonViewerSorter;
import org.eclipse.ui.navigator.INavigatorContentService;
import org.eclipse.ui.navigator.INavigatorFilterService;
import org.eclipse.ui.navigator.NavigatorContentServiceFactory;

public class FBPaletteViewer extends PaletteViewer {
	private CommonViewer commonViewer;
	private PatternFilter patternFilter = null;
	private final String navigatorId;
	private Object[] expandedElements;

	public FBPaletteViewer(final String navigatorId) {
		this.navigatorId = navigatorId;
	}

	public void createTypeLibTreeControl(final Composite parent, final IProject project) {

		final Composite container = new Composite(parent, SWT.NONE);

		final GridLayout layout = new GridLayout(1, false);
		layout.marginLeft = 0;
		layout.marginRight = 0;
		layout.marginBottom = 0;
		layout.marginTop = 0;
		container.setLayout(layout);

		final Text text = new Text(container, SWT.SEARCH | SWT.ICON_CANCEL | SWT.ICON_SEARCH);
		text.setMessage(Messages.FBPaletteViewer_SearchForType);

		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		text.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				if (e.detail == SWT.CANCEL) {
					setSearchFilter(""); //$NON-NLS-1$
				} else {
					setSearchFilter(text.getText());
				}
			}

		});
		text.addModifyListener(e -> setSearchFilter(text.getText()));

		createCommonViewer(container, project);

		setControl(container);

		setupResourceChangeListener(project);
	}

	private void createCommonViewer(final Composite container, final IProject project) {
		commonViewer = new CommonViewer(navigatorId, container, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL);

		final INavigatorContentService contentService = NavigatorContentServiceFactory.INSTANCE
				.createContentService(navigatorId, commonViewer);

		contentService.createCommonContentProvider();
		contentService.createCommonLabelProvider();

		final INavigatorFilterService filterService = commonViewer.getNavigatorContentService().getFilterService();
		final ViewerFilter[] visibleFilters = filterService.getVisibleFilters(true);
		for (final ViewerFilter visibleFilter : visibleFilters) {
			commonViewer.addFilter(visibleFilter);
		}

		commonViewer.setSorter(new CommonViewerSorter());
		commonViewer.addFilter(new TypeListPatternFilter());

		commonViewer.getControl().addMouseListener(new MouseListener() {

			@Override
			public void mouseUp(final MouseEvent e) {
				// currently nothing to do here
			}

			@Override
			public void mouseDown(final MouseEvent e) {
				// set the focus on this part on any mouse click, fixes issue in drag and drop
				commonViewer.getControl().forceFocus();
			}

			@Override
			public void mouseDoubleClick(final MouseEvent e) {
				// currently nothing to do here
			}
		});

		commonViewer.setInput(project);

		final GridData fillBoth = new GridData(GridData.FILL, GridData.FILL, true, true);
		commonViewer.getControl().setLayoutData(fillBoth);
	}

	private void setSearchFilter(final String string) {
		if (string.length() != 1) { // min. 2 search letters for performance
			if (null == patternFilter) {
				patternFilter = new TypeListPatternFilter();
				commonViewer.addFilter(patternFilter);
			}
			patternFilter.setPattern(string);
			commonViewer.refresh(false);
			handleTreeExpansion(string);
		}
	}

	private void handleTreeExpansion(final String string) {
		if (!string.isEmpty()) {
			if (null == expandedElements) {
				expandedElements = commonViewer.getExpandedElements();
			}
			commonViewer.expandAll();
		} else if (null != expandedElements) {
			commonViewer.setExpandedElements(expandedElements);
			expandedElements = null;
		}
	}

	@Override
	protected void hookControl() {
		// do nothing here! Especially do not call super.hookControl!
	}

	private void setupResourceChangeListener(final IProject project) {
		ResourcesPlugin.getWorkspace().addResourceChangeListener(event -> {
			if (event.getType() != IResourceChangeEvent.POST_CHANGE) {
				return;
			}
			final IResourceDelta rootDelta = event.getDelta();
			final IResourceDelta docDelta = rootDelta.findMember(project.getFullPath());
			if (docDelta == null) {
				return;
			}
			Display.getDefault().asyncExec(() -> {
				if ((null != commonViewer) && !commonViewer.getControl().isDisposed()) {
					commonViewer.refresh();
				}
			});
		});
	}

}
