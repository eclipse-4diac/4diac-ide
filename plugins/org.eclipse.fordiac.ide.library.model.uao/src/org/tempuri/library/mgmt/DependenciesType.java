/**
 */
package org.tempuri.library.mgmt;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Dependencies Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.tempuri.library.mgmt.DependenciesType#getDependency <em>Dependency</em>}</li>
 * </ul>
 *
 * @see org.tempuri.library.mgmt.MgmtPackage#getDependenciesType()
 * @model extendedMetaData="name='Dependencies_._type' kind='elementOnly'"
 * @generated
 */
public interface DependenciesType extends EObject {
	/**
	 * Returns the value of the '<em><b>Dependency</b></em>' containment reference list.
	 * The list contents are of type {@link org.tempuri.library.mgmt.DependencyType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dependency</em>' containment reference list.
	 * @see org.tempuri.library.mgmt.MgmtPackage#getDependenciesType_Dependency()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Dependency' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<DependencyType> getDependency();

} // DependenciesType
