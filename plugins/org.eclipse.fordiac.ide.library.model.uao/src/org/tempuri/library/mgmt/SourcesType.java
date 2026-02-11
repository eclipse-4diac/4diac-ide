/**
 */
package org.tempuri.library.mgmt;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Sources Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.tempuri.library.mgmt.SourcesType#getPath <em>Path</em>}</li>
 * </ul>
 *
 * @see org.tempuri.library.mgmt.MgmtPackage#getSourcesType()
 * @model extendedMetaData="name='Sources_._type' kind='elementOnly'"
 * @generated
 */
public interface SourcesType extends EObject {
	/**
	 * Returns the value of the '<em><b>Path</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Path</em>' attribute list.
	 * @see org.tempuri.library.mgmt.MgmtPackage#getSourcesType_Path()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='Path' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<String> getPath();

} // SourcesType
