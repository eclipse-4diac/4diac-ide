package org.eclipse.fordiac.ide.application.properties;

import org.eclipse.fordiac.ide.application.editparts.FBNetworkEditPart;
import org.eclipse.fordiac.ide.application.editparts.UISubAppNetworkEditPart;
import org.eclipse.jface.viewers.IFilter;

public class InfoSectionFilter implements IFilter {

	@Override
	public boolean select(final Object toTest) {
		return toTest instanceof UISubAppNetworkEditPart || toTest instanceof FBNetworkEditPart;
	}
}
