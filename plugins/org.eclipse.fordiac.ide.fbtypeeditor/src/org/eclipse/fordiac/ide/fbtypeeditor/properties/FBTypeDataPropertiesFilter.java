package org.eclipse.fordiac.ide.fbtypeeditor.properties;

import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;

public class FBTypeDataPropertiesFilter extends FBTypePropertiesFilter {

	@Override
	public boolean select(final Object toTest) {
		final Object retVal = getFBTypeFromSelectedElement(toTest);
		return retVal != null && !(retVal instanceof SubAppType);
	}

}
