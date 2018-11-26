/********************************************************************************
 * Copyright (c) 2008, 2010 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *    - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.Palette;

import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Sub Application Type Palette Entry</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.fordiac.ide.model.Palette.PalettePackage#getSubApplicationTypePaletteEntry()
 * @model
 * @generated
 */
public interface SubApplicationTypePaletteEntry extends PaletteEntry {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='LibraryElement type = getType();\nif((null !=type) &amp;&amp; (type instanceof SubAppType)){\n   return (SubAppType) type;\n}\nreturn null;'"
	 * @generated
	 */
	SubAppType getSubApplicationType();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel body='if((null != type) &amp;&amp; (type instanceof SubAppType)){\r\n\tsuper.setType(type);\r\n}else{\r\n\tsuper.setType(null);\r\n\tif(null != type){\r\n\t\t&lt;%org.eclipse.core.runtime.Status%&gt; exception = new Status(&lt;%org.eclipse.core.runtime.IStatus%&gt;.ERROR, Activator.PLUGIN_ID, \"tried to set no SubAppType as type entry for SubApplicationTypePaletteEntry\");\r\n\t\tActivator.getDefault().getLog().log(exception);\r\n\t}\r\n}'"
	 * @generated
	 */
	@Override
	void setType(LibraryElement type);
} // SubApplicationTypePaletteEntry
