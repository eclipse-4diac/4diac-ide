/*******************************************************************************
 * Copyright (c) 2023, 2025 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextcore.validation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.fordiac.ide.globalconstantseditor.globalConstants.GlobalConstantsPackage;
import org.eclipse.fordiac.ide.globalconstantseditor.globalConstants.STVarGlobalDeclarationBlock;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.structuredtextcore.resource.STCoreResourceDescriptionStrategy;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage;
import org.eclipse.xtext.linking.impl.LinkingHelper;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.impl.AliasedEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.IScopeProvider;

import com.google.inject.Inject;

public class STCoreTypeUsageCollector {

	// ignore references which do not individually contribute to the used types,
	// since they get their scope from somewhere else (e.g., members from the
	// receiver or parameters from the called feature)
	private static final Set<EReference> IGNORED_REFERENCES = Set.of(
			STCorePackage.Literals.ST_MEMBER_ACCESS_EXPRESSION__MEMBER, //
			STCorePackage.Literals.ST_CALL_NAMED_INPUT_ARGUMENT__PARAMETER, //
			STCorePackage.Literals.ST_CALL_NAMED_OUTPUT_ARGUMENT__PARAMETER, //
			STCorePackage.Literals.ST_FOR_STATEMENT__VARIABLE, //
			STCorePackage.Literals.ST_STRUCT_INIT_ELEMENT__VARIABLE //
	);

	@Inject
	private IQualifiedNameProvider nameProvider;

	@Inject
	private IQualifiedNameConverter nameConverter;

	@Inject
	private LinkingHelper linkingHelper;

	@Inject
	private IScopeProvider scopeProvider;

	private final Set<QualifiedName> usedTypes = new HashSet<>();
	private boolean includeFullyQualifiedReferences;
	private boolean includeUnresolvedReferences;

	public Set<QualifiedName> collectUsedTypes(final EObject object) {
		for (final EReference reference : object.eClass().getEAllReferences()) {
			if (object.eIsSet(reference) && !IGNORED_REFERENCES.contains(reference)) {
				if (reference.isContainment()) {
					handleContainment(object, reference);
				} else if (!reference.isContainer()) {
					handleCrossReference(object, reference);
				}
			}
		}
		return usedTypes;
	}

	protected void handleContainment(final EObject object, final EReference reference) {
		if (reference.isMany()) {
			@SuppressWarnings("unchecked")
			final List<EObject> children = (List<EObject>) object.eGet(reference);
			for (final EObject child : children) {
				collectUsedTypes(child);
			}
		} else {
			final EObject child = (EObject) object.eGet(reference);
			if (child != null) {
				collectUsedTypes(child);
			}
		}
	}

	protected void handleCrossReference(final EObject object, final EReference reference) {
		if (reference.isMany()) {
			@SuppressWarnings("unchecked")
			final List<EObject> targets = (List<EObject>) object.eGet(reference);
			for (int index = 0; index < targets.size(); index++) {
				handleCrossReference(object, reference, index, targets.get(index));
			}
		} else {
			final EObject target = (EObject) object.eGet(reference);
			if (target != null) {
				handleCrossReference(object, reference, 0, target);
			}
		}
	}

	protected void handleCrossReference(final EObject source, final EReference reference, final int index,
			final EObject target) {
		if (isExternalReference(source, target)) {
			final QualifiedName name = getCrossReferenceName(source, reference, index);
			if (target.eIsProxy()) {
				handleUnresolvedReference(source, reference, name);
			} else {
				handleExternalReference(source, reference, name, target);
			}
		}
	}

	protected void handleExternalReference(final EObject source, final EReference reference, final QualifiedName name,
			final EObject target) {
		final IScope scope = scopeProvider.getScope(source, reference);
		if (name != null) {
			final IEObjectDescription description = scope.getSingleElement(name);
			if (description != null) {
				addUsedType(description);
			}
		} else {
			final IEObjectDescription description = scope.getSingleElement(target);
			if (description != null) {
				addUsedType(description);
			}
		}
	}

	protected void handleUnresolvedReference(final EObject source, final EReference reference,
			final QualifiedName name) {
		if (name == null || !includeUnresolvedReferences) {
			return;
		}
		final IScope scope = scopeProvider.getScope(source, reference);
		IEObjectDescription result = null;
		for (final IEObjectDescription candidate : scope.getAllElements()) {
			final QualifiedName candidateName = candidate.getName();
			if (isVisible(candidate) && endsWith(candidateName, name)) {
				if (result == null) {
					result = new AliasedEObjectDescription(name, candidate);
				} else if (!result.getQualifiedName().equals(candidate.getQualifiedName())) {
					return; // ambiguous
				}
			}
		}
		if (result != null) {
			addUsedType(result);
		}
	}

	protected static boolean isVisible(final IEObjectDescription description) {
		return description.getQualifiedName().getSegmentCount() == 1
				|| LibraryElementPackage.eINSTANCE.getLibraryElement().isSuperTypeOf(description.getEClass())
				|| (STCorePackage.eINSTANCE.getSTVarDeclaration().equals(description.getEClass())
						&& isGlobalVariable(description));
	}

	protected static boolean isGlobalVariable(final IEObjectDescription description) {
		final String containerEClassName = description
				.getUserData(STCoreResourceDescriptionStrategy.CONTAINER_ECLASS_NAME);
		if (containerEClassName != null) {
			return GlobalConstantsPackage.Literals.ST_VAR_GLOBAL_DECLARATION_BLOCK.getName()
					.equals(containerEClassName);
		}
		return description.getEObjectOrProxy().eContainer() instanceof STVarGlobalDeclarationBlock;
	}

	protected static boolean endsWith(final QualifiedName name, final QualifiedName suffix) {
		if (suffix.getSegmentCount() > name.getSegmentCount()) {
			return false;
		}
		final int offset = name.getSegmentCount() - suffix.getSegmentCount();
		for (int i = 0; i < suffix.getSegmentCount(); ++i) {
			if (!name.getSegment(i + offset).equalsIgnoreCase(suffix.getSegment(i))) {
				return false;
			}
		}
		return true;
	}

	protected QualifiedName getCrossReferenceName(final EObject source, final EReference reference, final int index) {
		final List<INode> nodes = NodeModelUtils.findNodesForFeature(source, reference);
		if (index >= nodes.size()) {
			return null;
		}
		final String crossRefString = linkingHelper.getCrossRefNodeAsString(nodes.get(index), true);
		if (crossRefString == null || crossRefString.isBlank()) {
			return null;
		}
		return nameConverter.toQualifiedName(crossRefString);
	}

	public void addUsedType(final LibraryElement libraryElement) {
		final QualifiedName qualifiedName = nameProvider.apply(libraryElement);
		if (includeFullyQualifiedReferences || qualifiedName.getSegmentCount() > 1) {
			usedTypes.add(qualifiedName);
		}
	}

	protected void addUsedType(final IEObjectDescription description) {
		final QualifiedName relativeName = description.getName();
		final QualifiedName qualifiedName = description.getQualifiedName();
		if (relativeName.getSegmentCount() < qualifiedName.getSegmentCount()) {
			usedTypes.add(qualifiedName.skipLast(relativeName.getSegmentCount() - 1));
		} else if (includeFullyQualifiedReferences) {
			usedTypes.add(qualifiedName);
		}
	}

	protected static boolean isExternalReference(final EObject source, final EObject target) {
		return source.eResource() != target.eResource();
	}

	public STCoreTypeUsageCollector includeFullyQualifiedReferences() {
		includeFullyQualifiedReferences = true;
		return this;
	}

	public STCoreTypeUsageCollector includeUnresolvedReferences() {
		includeUnresolvedReferences = true;
		return this;
	}

	public Set<QualifiedName> getUsedTypes() {
		return usedTypes;
	}
}
