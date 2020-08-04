package org.eclipse.fordiac.ide.validation.handlers;

import java.util.ResourceBundle;

public class ConstraintHelper {
	private static final String FORDIAC_CONSTRAINT_PROPERTIES = "constraints";
	private static ResourceBundle fordiacConstraintProperties = ResourceBundle.getBundle(FORDIAC_CONSTRAINT_PROPERTIES);

	public static String[] getConstraintProperties(String name) {
		return fordiacConstraintProperties.getString(name).split(";");
	}
}
