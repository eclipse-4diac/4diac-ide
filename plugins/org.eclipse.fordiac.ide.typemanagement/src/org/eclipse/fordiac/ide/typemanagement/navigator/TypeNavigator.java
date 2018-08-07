/*******************************************************************************
 * Copyright (c) 2012 - 2017 TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.navigator;

import org.eclipse.fordiac.ide.typemanagement.util.TypeListPatternFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.ui.navigator.CommonViewer;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class TypeNavigator extends CommonNavigator implements ITabbedPropertySheetPageContributor {
	private PatternFilter patternFilter = null;
	
	@Override
	public void createPartControl(Composite aParent) {
		Composite container = new Composite(aParent, SWT.NONE);
		GridLayout layout = new GridLayout(1, false);
		layout.marginLeft = 0;
		layout.marginRight = 0;
		layout.marginBottom = 0;
		layout.marginTop = 0;
		container.setLayout(layout);
		final Text text = new Text(container, SWT.SEARCH | SWT.ICON_CANCEL);	
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		text.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				if (e.detail == SWT.CANCEL) {
					setSearchFilter(""); //$NON-NLS-1$
				} else {
					setSearchFilter(text.getText());
				}
			}
		});
		text.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(final ModifyEvent e) {
				setSearchFilter(text.getText());
			}
		});		
		super.createPartControl(container);		
		GridData fillBoth = new GridData();
		fillBoth.horizontalAlignment = GridData.FILL;
		fillBoth.grabExcessHorizontalSpace = true;
		fillBoth.verticalAlignment = GridData.FILL;
		fillBoth.grabExcessVerticalSpace = true;
		getCommonViewer().getControl().setLayoutData(fillBoth);		
	}
	
	
	void setSearchFilter(String filterString){
		CommonViewer cv = getCommonViewer();
		if (patternFilter == null)	{
			patternFilter = new TypeListPatternFilter();
			cv.addFilter(patternFilter);
		}		
		patternFilter.setPattern(filterString);
		cv.refresh(false);
	}

	@Override
	public String getContributorId() {
		return "property.contributor.fb"; //$NON-NLS-1$
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getAdapter(Class required) {
		if(required == IPropertySheetPage.class){
			return new TabbedPropertySheetPage(this);
		}
		return super.getAdapter(required);
	}
}
