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
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
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

	protected static <T extends INamedElement> void updateType(final T type, final Consumer<? super T> consumer,
			final ResourceSet resourceSet) {
		if (type instanceof AnyDerivedType) {
			final Class<? extends T> clazz = getClass(type);
			if (type.eResource() != null && type.eResource().getResourceSet() != resourceSet) {
				final EObject canonicalObject = resourceSet.getEObject(EcoreUtil.getURI(type), true);
				if (clazz.isInstance(canonicalObject)) {
					updateCrossReferences(canonicalObject.eResource(), resourceSet);
					consumer.accept(clazz.cast(canonicalObject));
				}
			} else if (type instanceof final ArrayType arrayType) {
				updateType(arrayType.getBaseType(), arrayType::setBaseType, resourceSet);
			}
		}
	}

	@SuppressWarnings("unchecked")
	protected static <T> Class<? extends T> getClass(final T type) {
		return (Class<? extends T>) type.getClass();
	}
}
