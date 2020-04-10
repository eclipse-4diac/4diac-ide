/**
 * generated by Xtext 2.20.0
 */
package org.eclipse.fordiac.ide.model.structuredtext.structuredText;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Statement List</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.model.structuredtext.structuredText.StatementList#getStatements
 * <em>Statements</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.structuredtext.structuredText.StructuredTextPackage#getStatementList()
 * @model
 * @generated
 */
public interface StatementList extends EObject {
	/**
	 * Returns the value of the '<em><b>Statements</b></em>' containment reference
	 * list. The list contents are of type
	 * {@link org.eclipse.fordiac.ide.model.structuredtext.structuredText.Statement}.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Statements</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.model.structuredtext.structuredText.StructuredTextPackage#getStatementList_Statements()
	 * @model containment="true"
	 * @generated
	 */
	EList<Statement> getStatements();

} // StatementList
