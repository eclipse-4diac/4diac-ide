package org.eclipse.fordiac.ide.systemmanagement.ui.systemexplorer;

import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class SystemExplorer extends CommonNavigator implements ITabbedPropertySheetPageContributor{

	@Override
	public String getContributorId() {
		return "org.eclipse.fordiac.ide.application.editors.DiagramEditor";
	}

	@Override
	public <T> T getAdapter(Class<T> adapter) {
		if (adapter == IPropertySheetPage.class) {
			return adapter.cast(new TabbedPropertySheetPage(this));
		}
		return super.getAdapter(adapter);
	}
}
