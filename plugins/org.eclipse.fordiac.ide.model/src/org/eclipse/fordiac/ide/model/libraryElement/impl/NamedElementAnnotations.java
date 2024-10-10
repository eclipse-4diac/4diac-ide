/*******************************************************************************
 * Copyright (c) 2023, 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.model.libraryElement.impl;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.fordiac.ide.model.IdentifierVerifier;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.util.LibraryElementValidator;

public final class NamedElementAnnotations {
	static final String QUALIFIED_NAME_DELIMITER = "."; //$NON-NLS-1$

	/**
	 * Do not call directly! Use {@link INamedElement#getQualifiedName()} instead.
	 *
	 * Must be accessible from derived models.
	 */
	public static String getQualifiedName(final INamedElement element) {
		final INamedElement namedContainer = getNamedContainer(element);
		if (namedContainer != null && namedContainer.eContainer() != null) {
			return namedContainer.getQualifiedName() + QUALIFIED_NAME_DELIMITER + element.getName();
		}
		return element.getName();
	}

	static INamedElement getNamedContainer(EObject object) {
		while (object != null) {
			object = object.eContainer();
			if (object instanceof final INamedElement element) {
				return element;
			}
		}
		return null;
	}

	static String removeQualifiedNamePrefix(final String s, final String prefix) {
		if (s.startsWith(prefix + QUALIFIED_NAME_DELIMITER)) {
			return s.substring(prefix.length() + QUALIFIED_NAME_DELIMITER.length());
		}
		return s;
	}

	/**
	 * Do not call directly! Use {@link INamedElement#findBySimpleName(String)}
	 * instead.
	 *
	 * Must be accessible from derived models.
	 */
	public static Stream<INamedElement> findBySimpleName(final INamedElement root, final String name) {
		return StreamSupport.stream(new NamedContentsSpliterator(root, name), false);
	}

	/**
	 * Do not call directly! Use {@link INamedElement#findByQualifiedName(String)}
	 * instead.
	 *
	 * Must be accessible from derived models.
	 */
	public static Stream<INamedElement> findByQualifiedName(final INamedElement root, final String qualifiedName) {
		final int separator = qualifiedName.indexOf('.');
		if (separator >= 0) {
			final String head = qualifiedName.substring(0, separator);
			final String tail = qualifiedName.substring(separator + 1);
			return root.findBySimpleName(head).flatMap(element -> element.findByQualifiedName(tail));
		}
		return root.findBySimpleName(qualifiedName);
	}

	public static boolean validateName(final INamedElement element, final DiagnosticChain diagnostics,
			final Map<Object, Object> context) {
		if (element.getName() != null && !element.getName().isEmpty()) {
			if (element.getName().contains("__")) { //$NON-NLS-1$
				if (diagnostics != null) {
					diagnostics.add(new BasicDiagnostic(Diagnostic.WARNING, LibraryElementValidator.DIAGNOSTIC_SOURCE,
							LibraryElementValidator.INAMED_ELEMENT__VALIDATE_NAME,
							MessageFormat.format(Messages.IdentifierVerifier_NameConsecutiveUnderscore,
									element.getName()),
							FordiacMarkerHelper.getDiagnosticData(element,
									LibraryElementPackage.Literals.INAMED_ELEMENT__NAME, element.getName())));
				}
			} else if (element.getName().endsWith("_")) { //$NON-NLS-1$
				if (diagnostics != null) {
					diagnostics.add(new BasicDiagnostic(Diagnostic.WARNING, LibraryElementValidator.DIAGNOSTIC_SOURCE,
							LibraryElementValidator.INAMED_ELEMENT__VALIDATE_NAME,
							MessageFormat.format(Messages.IdentifierVerifier_NameTrailingUnderscore, element.getName()),
							FordiacMarkerHelper.getDiagnosticData(element,
									LibraryElementPackage.Literals.INAMED_ELEMENT__NAME)));
				}
			} else {
				final Optional<String> errorMessage = IdentifierVerifier.verifyIdentifier(element.getName(), element);
				if (errorMessage.isPresent()) {
					if (diagnostics != null) {
						diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR, LibraryElementValidator.DIAGNOSTIC_SOURCE,
								LibraryElementValidator.INAMED_ELEMENT__VALIDATE_NAME, errorMessage.get(),
								FordiacMarkerHelper.getDiagnosticData(element,
										LibraryElementPackage.Literals.INAMED_ELEMENT__NAME)));
					}
					return false;
				}
			}
		}
		return true;
	}

	public static boolean validateDuplicateName(final INamedElement element, final DiagnosticChain diagnostics,
			final Map<Object, Object> context, final String key) {
		final Map<String, INamedElement> namedContents = getNamedContents(context, key);
		// update siblings map
		// - if the map does not contain the name -> put name and element into the map
		// (so we get the element and add a diagnostic on it when we actually have a
		// duplicate)
		// - if the map does contain the name -> put the null element into the map
		// (so we do not add a diagnostic on it twice but still retain the key in the
		// map for more duplicates)
		final INamedElement duplicate = putConditional(namedContents, element.getQualifiedName(), element,
				NullNamedElement.INSTANCE);
		if (duplicate != null) { // we have a collision
			// add diagnostics
			if (diagnostics != null) {
				// add diagnostic for sibling (only once)
				if (!(duplicate instanceof NullNamedElement)) {
					diagnostics.add(createDuplicateNameDiagnostic(duplicate));
				}
				// add diagnostic for current element
				diagnostics.add(createDuplicateNameDiagnostic(element));
			}
			return false;
		}
		return true;
	}

	private static Diagnostic createDuplicateNameDiagnostic(final INamedElement element) {
		return new BasicDiagnostic(Diagnostic.ERROR, LibraryElementValidator.DIAGNOSTIC_SOURCE,
				LibraryElementValidator.INAMED_ELEMENT__VALIDATE_NAME,
				MessageFormat.format(Messages.InterfaceElementAnnotations_DuplicateName, element.getName()),
				FordiacMarkerHelper.getDiagnosticData(element, LibraryElementPackage.Literals.INAMED_ELEMENT__NAME,
						element.getQualifiedName()));
	}

	private static <K, V> V putConditional(final Map<K, V> map, final K key, final V valueIfAbsent,
			final V valueIfPresent) {
		V v = map.get(key);
		if (v == null) {
			v = map.put(key, valueIfAbsent);
		} else {
			v = map.put(key, valueIfPresent);
		}
		return v;
	}

	@SuppressWarnings("unchecked")
	private static Map<String, INamedElement> getNamedContents(final Map<Object, Object> context, final String key) {
		return (Map<String, INamedElement>) context.computeIfAbsent(key, k -> new HashMap<>());
	}

	private static class NullNamedElement extends EObjectImpl implements INamedElement {
		private static final NullNamedElement INSTANCE = new NullNamedElement();

		@Override
		public String getName() {
			return null;
		}

		@Override
		public void setName(final String value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public String getComment() {
			return null;
		}

		@Override
		public void setComment(final String value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public String getQualifiedName() {
			return null;
		}

		@Override
		public Stream<INamedElement> findBySimpleName(final String name) {
			return Stream.empty();
		}

		@Override
		public Stream<INamedElement> findByQualifiedName(final String name) {
			return Stream.empty();
		}

		@Override
		public boolean validateName(final DiagnosticChain diagnostics, final Map<Object, Object> context) {
			return false;
		}
	}

	private static class NamedContentsSpliterator implements Spliterator<INamedElement> {

		private final String name;
		private final TreeIterator<EObject> contents;

		private NamedContentsSpliterator(final INamedElement root, final String name) {
			this.name = name;
			contents = root.eAllContents();
		}

		@Override
		public boolean tryAdvance(final Consumer<? super INamedElement> action) {
			while (contents.hasNext()) {
				final EObject element = contents.next();
				if (element instanceof final INamedElement namedElement) {
					if (Objects.equals(namedElement.getName(), name)) {
						action.accept(namedElement);
						return true;
					}
					contents.prune();
				}
			}
			return false;
		}

		@Override
		public Spliterator<INamedElement> trySplit() {
			return null; // cannot split
		}

		@Override
		public long estimateSize() {
			return Long.MAX_VALUE;
		}

		@Override
		public int characteristics() {
			return Spliterator.DISTINCT | Spliterator.NONNULL | Spliterator.ORDERED;
		}
	}

	private NamedElementAnnotations() {
		throw new UnsupportedOperationException("Helper class must not be instantiated"); //$NON-NLS-1$
	}
}
