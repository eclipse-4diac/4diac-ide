/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Ernst Blecha
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.typelibrary;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.stream.Stream;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;

public class CMakeListsMarker implements INamedElement {

	@Override
	public String getName() {
		return "CMakeLists.txt"; //$NON-NLS-1$
	}

	@Override
	public String getQualifiedName() {
		return getName();
	}

	@Override
	public void setName(final String value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getComment() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setComment(final String value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public EClass eClass() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Resource eResource() {
		throw new UnsupportedOperationException();
	}

	@Override
	public EObject eContainer() {
		throw new UnsupportedOperationException();
	}

	@Override
	public EStructuralFeature eContainingFeature() {
		throw new UnsupportedOperationException();
	}

	@Override
	public EReference eContainmentFeature() {
		throw new UnsupportedOperationException();
	}

	@Override
	public EList<EObject> eContents() {
		throw new UnsupportedOperationException();
	}

	@Override
	public TreeIterator<EObject> eAllContents() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean eIsProxy() {
		throw new UnsupportedOperationException();
	}

	@Override
	public EList<EObject> eCrossReferences() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object eGet(final EStructuralFeature feature) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object eGet(final EStructuralFeature feature, final boolean resolve) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void eSet(final EStructuralFeature feature, final Object newValue) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean eIsSet(final EStructuralFeature feature) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void eUnset(final EStructuralFeature feature) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object eInvoke(final EOperation operation, final EList<?> arguments) throws InvocationTargetException {
		throw new UnsupportedOperationException();
	}

	@Override
	public EList<Adapter> eAdapters() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean eDeliver() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void eSetDeliver(final boolean deliver) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void eNotify(final Notification notification) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean validateName(final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		return true;
	}

	@Override
	public Stream<INamedElement> findBySimpleName(final String name) {
		return Stream.empty();
	}

	@Override
	public Stream<INamedElement> findByQualifiedName(final String name) {
		return Stream.empty();
	}
}
