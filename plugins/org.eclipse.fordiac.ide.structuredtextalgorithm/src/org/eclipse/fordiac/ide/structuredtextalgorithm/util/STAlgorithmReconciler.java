/**
 * Copyright (c) 2022 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 */
package org.eclipse.fordiac.ide.structuredtextalgorithm.util;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.STMethod;
import org.eclipse.fordiac.ide.model.libraryElement.STSourceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SourceElement;
import org.eclipse.fordiac.ide.structuredtextcore.util.STCorePartition;
import org.eclipse.fordiac.ide.structuredtextcore.util.STCoreReconciler;

public class STAlgorithmReconciler implements STCoreReconciler {

	@Override
	public void reconcile(final LibraryElement dest, final Optional<? extends STCorePartition> source) {
		if (dest instanceof final BaseFBType baseFBType && source.isPresent()
				&& source.get() instanceof final STAlgorithmPartition algorithmSource) {
			reconcile(baseFBType, algorithmSource);
		}
	}

	private static void reconcile(final BaseFBType dest, final STAlgorithmPartition source) {
		// check duplicates in source or dest (very bad)
		if (STCoreReconciler.hasDuplicates(source.getSourceElements().stream())) {
			return; // don't even try to attempt this or risk screwing dest up
		}
		if (STCoreReconciler
				.hasDuplicates(dest.getSourceElements().stream().filter(STSourceElement.class::isInstance))) {
			// dest is broken -> remove all ST source elements
			dest.getSourceElements().removeIf(STSourceElement.class::isInstance);
		} else {
			// remove from dest if not in source
			dest.getSourceElements().removeIf(destElem -> destElem instanceof STSourceElement
					&& findCandidate(source.getSourceElements(), destElem).isEmpty());
		}
		// add or merge/move according to source
		IntStream.range(0, source.getSourceElements().size()).forEach(index -> {
			final STSourceElement sourceElem = source.getSourceElements().get(index);
			final Optional<SourceElement> candidate = findCandidate(dest.getSourceElements(), sourceElem);
			if (candidate.isPresent() && merge(candidate.get(), sourceElem)) {
				// move to position (dest must contain at least index algs since we insert as we
				// go)
				dest.getSourceElements().move(index, candidate.get());
			} else {
				candidate.ifPresent(dest.getSourceElements()::remove); // remove candidate (if exists)
				dest.getSourceElements().add(index, sourceElem); // insert at position
			}
		});
	}

	private static boolean matches(final SourceElement dest, final SourceElement source) {
		return switch (dest) {
		case final STAlgorithm destAlg when source instanceof final STAlgorithm srcAlg -> matches(destAlg, srcAlg);
		case final STMethod destMethod when source instanceof final STMethod srcMethod ->
			matches(destMethod, srcMethod);
		default -> false;
		};
	}

	private static boolean merge(final SourceElement dest, final STSourceElement source) {
		return switch (dest) {
		case final STAlgorithm destAlg when source instanceof final STAlgorithm srcAlg -> merge(destAlg, srcAlg);
		case final STMethod destMethod when source instanceof final STMethod srcMethod -> merge(destMethod, srcMethod);
		default -> false;
		};
	}

	private static boolean matches(final STAlgorithm dest, final STAlgorithm src) {
		return Objects.equals(dest.getName(), src.getName());
	}

	private static boolean merge(final STAlgorithm dest, final STAlgorithm source) {
		dest.setComment(source.getComment());
		dest.setText(source.getText());
		return true;
	}

	private static boolean matches(final STMethod dest, final STMethod src) {
		return Objects.equals(dest.getName(), src.getName());
	}

	private static boolean merge(final STMethod dest, final STMethod source) {
		dest.setComment(source.getComment());
		dest.setText(source.getText());
		ECollections.setEList(dest.getInputParameters(),
				source.getInputParameters().stream().map(EcoreUtil::copy).toList());
		ECollections.setEList(dest.getOutputParameters(),
				source.getOutputParameters().stream().map(EcoreUtil::copy).toList());
		ECollections.setEList(dest.getInOutParameters(),
				source.getInOutParameters().stream().map(EcoreUtil::copy).toList());
		dest.setReturnType(source.getReturnType());
		return true;
	}

	private static <T extends SourceElement> Optional<T> findCandidate(final List<T> list,
			final SourceElement element) {
		return list.stream().filter(candidate -> matches(candidate, element)).findFirst();
	}
}
