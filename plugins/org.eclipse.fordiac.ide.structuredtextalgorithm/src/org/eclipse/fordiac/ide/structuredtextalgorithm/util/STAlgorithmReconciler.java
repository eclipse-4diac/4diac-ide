/**
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
 */
package org.eclipse.fordiac.ide.structuredtextalgorithm.util;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ICallable;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.STMethod;
import org.eclipse.fordiac.ide.structuredtextcore.util.STCorePartition;
import org.eclipse.fordiac.ide.structuredtextcore.util.STCoreReconciler;

public class STAlgorithmReconciler implements STCoreReconciler {

	@Override
	public void reconcile(final LibraryElement dest, final Optional<? extends STCorePartition> source) {
		if (dest instanceof final BaseFBType baseFBType && source.isPresent()
				&& source.get() instanceof final STAlgorithmPartition algorithmSource) {
			reconcile(baseFBType.getCallables(), algorithmSource);
		}
	}

	public static void reconcile(final EList<ICallable> dest, final STAlgorithmPartition source) {
		// check duplicates in source or dest (very bad)
		if (checkDuplicates(source.getCallables())) {
			return; // don't even try to attempt this or risk screwing dest up
		}
		if (checkDuplicates(dest)) {
			dest.removeIf(STAlgorithmReconciler::isSTElement); // dest is broken
		}
		// remove from dest if not in source
		dest.removeIf(destAlg -> isSTElement(destAlg) && source.getCallables().stream()
				.noneMatch(sourceAlg -> Objects.equals(sourceAlg.getName(), destAlg.getName())));
		// add or merge/move according to source
		IntStream.range(0, source.getCallables().size()).forEach(index -> {
			final ICallable sourceAlg = source.getCallables().get(index);
			final Optional<ICallable> candidate = dest.stream().filter(STAlgorithmReconciler::isSTElement)
					.filter(destAlg -> Objects.equals(sourceAlg.getName(), destAlg.getName())).findFirst();
			if (candidate.isPresent() && merge(candidate.get(), sourceAlg)) {
				// move to position (dest must contain at least index algs since we insert as we
				// go)
				dest.move(index, candidate.get());
			} else {
				candidate.ifPresent(dest::remove); // remove candidate (if exists)
				dest.add(index, sourceAlg); // insert at position
			}
		});
	}

	protected static boolean merge(final ICallable dest, final ICallable source) {
		if (dest instanceof final STAlgorithm destAlg && source instanceof final STAlgorithm sourceAlg) {
			return merge(destAlg, sourceAlg);
		}
		if (dest instanceof final STMethod destMethod && source instanceof final STMethod sourceMethod) {
			return merge(destMethod, sourceMethod);
		}
		return false;
	}

	protected static boolean merge(final STAlgorithm dest, final STAlgorithm source) {
		dest.setComment(source.getComment());
		dest.setText(source.getText());
		return true;
	}

	protected static boolean merge(final STMethod dest, final STMethod source) {
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

	protected static boolean checkDuplicates(final EList<? extends ICallable> list) {
		return list.stream().filter(STAlgorithmReconciler::isSTElement).map(INamedElement::getName).distinct()
				.count() != list.stream().filter(STAlgorithmReconciler::isSTElement).count();
	}

	protected static boolean isSTElement(final ICallable callable) {
		return callable instanceof STAlgorithm || callable instanceof STMethod;
	}
}
