package org.eclipse.fordiac.ide.fbtypeeditor.properties;

import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;

public class FBSubappTypePropertiesFilter extends FBTypePropertiesFilter {

	@Override
	public boolean select(final Object toTest) {
		final Object retval = getFBTypeFromSelectedElement(toTest);
		return retval != null && (retval instanceof SubAppType);
	}
}
