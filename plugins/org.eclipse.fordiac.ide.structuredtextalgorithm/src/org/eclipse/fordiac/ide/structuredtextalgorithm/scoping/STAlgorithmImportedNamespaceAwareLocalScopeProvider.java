/*******************************************************************************
 * Copyright (c) 2022, 2023 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextalgorithm.scoping;

import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.STMethod;
import org.eclipse.fordiac.ide.structuredtextcore.resource.LibraryElementXtextResource;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.ISelectable;
import org.eclipse.xtext.scoping.Scopes;
import org.eclipse.xtext.scoping.impl.ImportNormalizer;
import org.eclipse.xtext.scoping.impl.ImportedNamespaceAwareLocalScopeProvider;
import org.eclipse.xtext.scoping.impl.MultimapBasedSelectable;

import com.google.inject.Inject;

public class STAlgorithmImportedNamespaceAwareLocalScopeProvider extends ImportedNamespaceAwareLocalScopeProvider {

	@Inject
	private IQualifiedNameProvider qualifiedNameProvider;

	@Override
	public List<ImportNormalizer> internalGetImportedNamespaceResolvers(final EObject context,
			final boolean ignoreCase) {
		final var result = super.internalGetImportedNamespaceResolvers(context, ignoreCase);
		if (context.eContainer() == null) {
			final CompilerInfo compilerInfo = getCompilerInfo(context);
			if (compilerInfo != null) {
				final String packageName = compilerInfo.getPackageName();
				if (packageName != null && !packageName.isEmpty()) {
					final QualifiedName name = getQualifiedNameConverter().toQualifiedName(packageName);
					if (name != null && !name.isEmpty()) {
						result.add(doCreateImportNormalizer(name, true, ignoreCase));
					}
				}
				result.addAll(super.internalGetImportedNamespaceResolvers(compilerInfo, ignoreCase));
			}
		}
		return result;
	}

	protected static CompilerInfo getCompilerInfo(final EObject context) {
		if (context.eResource() instanceof final LibraryElementXtextResource libraryElementXtextResource) {
			return getCompilerInfo(libraryElementXtextResource.getInternalLibraryElement());
		}
		return null;
	}

	protected static CompilerInfo getCompilerInfo(final LibraryElement libraryElement) {
		if (libraryElement != null) {
			return libraryElement.getCompilerInfo();
		}
		return null;
	}

	@Override
	protected ISelectable internalGetAllDescriptions(final Resource resource) {
		final Iterable<EObject> allContents = () -> Spliterators
				.iterator(new STAlgorithmResourceContentsSpliterator(resource));
		final Iterable<IEObjectDescription> allDescriptions = Scopes.scopedElementsFor(allContents,
				qualifiedNameProvider);
		return new MultimapBasedSelectable(allDescriptions);
	}

	@Override
	public boolean isRelativeImport() {
		return false;
	}

	protected static class STAlgorithmResourceContentsSpliterator implements Spliterator<EObject> {

		private final TreeIterator<EObject> delegate;

		public STAlgorithmResourceContentsSpliterator(final Resource resource) {
			this.delegate = EcoreUtil.getAllContents(resource, false);
		}

		@Override
		public boolean tryAdvance(final Consumer<? super EObject> action) {
			while (delegate.hasNext()) {
				final EObject eObject = delegate.next();
				if (filter(eObject)) {
					action.accept(eObject);
					return true;
				}
				delegate.prune();
			}
			return false;
		}

		@SuppressWarnings("static-method") // subclasses may override
		protected boolean filter(final EObject eObject) {
			// do not export anything inside of an algorithm, method, or FB network
			return !(eObject instanceof STAlgorithm || eObject instanceof STMethod || eObject instanceof FBNetwork);
		}

		@Override
		public Spliterator<EObject> trySplit() {
			return null;
		}

		@Override
		public long estimateSize() {
			return Long.MAX_VALUE;
		}

		@Override
		public int characteristics() {
			return Spliterator.DISTINCT | Spliterator.IMMUTABLE | Spliterator.NONNULL | Spliterator.ORDERED;
		}
	}
}
