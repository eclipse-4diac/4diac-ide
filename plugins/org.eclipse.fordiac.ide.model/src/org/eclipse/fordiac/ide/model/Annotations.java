package org.eclipse.fordiac.ide.model;

import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;

public enum Annotations {
	GEN;
	
	public void setAttribute(ConfigurableObject object, final String attributeName, final String value, final String comment) {
		Attribute attribute = null;
		// find already existing parameter with parameterName
		for (Attribute attr : object.getAttributes()) {
			if (attr.getName().equalsIgnoreCase(attributeName)) {
				attribute = attr;
				break;
			}
		}
		if (attribute == null) {
			attribute = LibraryElementFactory.eINSTANCE.createAttribute();
			attribute.setName(attributeName);
			attribute.setValue(value);
			attribute.setComment(comment);
			object.getAttributes().add(attribute);
		} else {
			attribute.setValue(value);
		}
	}
	
	public String getAttribute(ConfigurableObject object, final String attributeName) {
		if (attributeName == null) {
			return null;
		}
		for (Attribute attribute : object.getAttributes()) {
			if (attribute.getName().equalsIgnoreCase(attributeName)) {
				return attribute.getValue();
			}
		}
		return null;
	}
}
