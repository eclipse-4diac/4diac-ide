/**
 */
package org.eclipse.fordiac.ide.library.model.library;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Library</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.library.model.library.Library#getIncludes <em>Includes</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.library.model.library.Library#getExcludes <em>Excludes</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.library.model.library.Library#getAttribute <em>Attribute</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.library.model.library.Library#getComment <em>Comment</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.library.model.library.Library#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.library.model.library.Library#getSymbolicName <em>Symbolic Name</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.library.model.library.LibraryPackage#getLibrary()
 * @model extendedMetaData="name='Library' kind='elementOnly'"
 * @generated
 */
public interface Library extends EObject {
	/**
	 * Returns the value of the '<em><b>Includes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Includes</em>' containment reference.
	 * @see #setIncludes(Includes)
	 * @see org.eclipse.fordiac.ide.library.model.library.LibraryPackage#getLibrary_Includes()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Includes' namespace='##targetNamespace'"
	 * @generated
	 */
	Includes getIncludes();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.library.model.library.Library#getIncludes <em>Includes</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Includes</em>' containment reference.
	 * @see #getIncludes()
	 * @generated
	 */
	void setIncludes(Includes value);

	/**
	 * Returns the value of the '<em><b>Excludes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Excludes</em>' containment reference.
	 * @see #setExcludes(Excludes)
	 * @see org.eclipse.fordiac.ide.library.model.library.LibraryPackage#getLibrary_Excludes()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Excludes' namespace='##targetNamespace'"
	 * @generated
	 */
	Excludes getExcludes();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.library.model.library.Library#getExcludes <em>Excludes</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Excludes</em>' containment reference.
	 * @see #getExcludes()
	 * @generated
	 */
	void setExcludes(Excludes value);

	/**
	 * Returns the value of the '<em><b>Attribute</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fordiac.ide.library.model.library.Attribute}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.library.model.library.LibraryPackage#getLibrary_Attribute()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Attribute' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<Attribute> getAttribute();

	/**
	 * Returns the value of the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Comment</em>' attribute.
	 * @see #setComment(String)
	 * @see org.eclipse.fordiac.ide.library.model.library.LibraryPackage#getLibrary_Comment()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='Comment' namespace='##targetNamespace'"
	 * @generated
	 */
	String getComment();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.library.model.library.Library#getComment <em>Comment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Comment</em>' attribute.
	 * @see #getComment()
	 * @generated
	 */
	void setComment(String value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.fordiac.ide.library.model.library.LibraryPackage#getLibrary_Name()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='Name' namespace='##targetNamespace'"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.library.model.library.Library#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Symbolic Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Symbolic Name</em>' attribute.
	 * @see #setSymbolicName(String)
	 * @see org.eclipse.fordiac.ide.library.model.library.LibraryPackage#getLibrary_SymbolicName()
	 * @model dataType="org.eclipse.fordiac.ide.library.model.library.SymbolicName" required="true"
	 *        extendedMetaData="kind='attribute' name='SymbolicName' namespace='##targetNamespace'"
	 * @generated
	 */
	String getSymbolicName();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.library.model.library.Library#getSymbolicName <em>Symbolic Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Symbolic Name</em>' attribute.
	 * @see #getSymbolicName()
	 * @generated
	 */
	void setSymbolicName(String value);

} // Library
