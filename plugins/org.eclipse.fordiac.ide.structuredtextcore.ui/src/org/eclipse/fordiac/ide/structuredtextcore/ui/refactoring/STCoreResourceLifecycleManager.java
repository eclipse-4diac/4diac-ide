/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring;

import java.util.function.Consumer;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.data.AnyDerivedType;
import org.eclipse.fordiac.ide.model.data.ArrayType;
import org.eclipse.fordiac.ide.model.data.DirectlyDerivedType;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorLibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STResource;
import org.eclipse.xtext.ide.serializer.impl.RelatedResourcesProvider.RelatedResource;
import org.eclipse.xtext.ide.serializer.impl.ResourceLifecycleManager;

@SuppressWarnings("restriction")
public class STCoreResourceLifecycleManager extends ResourceLifecycleManager {

	@Override
	public Resource openAndApplyReferences(final ResourceSet resourceSet, final RelatedResource toLoad) {
		final Resource resource = super.openAndApplyReferences(resourceSet, toLoad);
		if (resource instanceof final STResource stResource) {
			updateType(stResource.getExpectedType(), stResource::setExpectedType, resourceSet);
		}
		updateCrossReferences(resource, resourceSet);
		return resource;
	}

	protected static void updateCrossReferences(final Resource resource, final ResourceSet resourceSet) {
		final TreeIterator<EObject> contents = EcoreUtil.getAllContents(resource, true);
		while (contents.hasNext()) {
			final EObject next = contents.next();
			if (next instanceof final VarDeclaration varDeclaration) {
				updateType(varDeclaration.getType(), varDeclaration::setType, resourceSet);
			}
		}
	}

	protected static <T extends LibraryElement> void updateType(final T type, final Consumer<? super T> consumer,
			final ResourceSet resourceSet) {
		switch (type) {
		case final ErrorLibraryElement unused -> {
			// do not attempt to update error types
		}
		case final AnyDerivedType dataType when dataType.eResource() != null
				&& dataType.eResource().getResourceSet() != resourceSet ->
			// update references to types in another resource set
			updateReference(type, consumer, resourceSet);
		case final ArrayType arrayType ->
			// update base type of array types
			updateType(arrayType.getBaseType(), arrayType::setBaseType, resourceSet);
		case final DirectlyDerivedType derivedType ->
			// update base type of derived types
			updateType(derivedType.getBaseType(), derivedType::setBaseType, resourceSet);
		case null, default -> {
			// do nothing
		}
		}
	}

	protected static <T extends LibraryElement> void updateReference(final T type, final Consumer<? super T> consumer,
			final ResourceSet resourceSet) {
		final Class<? extends T> clazz = getClass(type);
		final EObject canonicalObject = resourceSet.getEObject(EcoreUtil.getURI(type), true);
		if (clazz.isInstance(canonicalObject) && canonicalObject.eResource() != null) {
			updateCrossReferences(canonicalObject.eResource(), resourceSet);
			consumer.accept(clazz.cast(canonicalObject));
		}
	}

	@SuppressWarnings("unchecked")
	protected static <T> Class<? extends T> getClass(final T type) {
		return (Class<? extends T>) type.getClass();
	}
}
