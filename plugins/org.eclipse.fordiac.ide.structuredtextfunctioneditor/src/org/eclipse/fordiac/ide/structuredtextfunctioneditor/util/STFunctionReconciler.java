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
package org.eclipse.fordiac.ide.structuredtextfunctioneditor.util;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ICallable;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.STFunction;
import org.eclipse.fordiac.ide.model.libraryElement.STFunctionBody;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.With;
import org.eclipse.fordiac.ide.model.typelibrary.EventTypeLibrary;
import org.eclipse.fordiac.ide.structuredtextcore.util.STCorePartition;
import org.eclipse.fordiac.ide.structuredtextcore.util.STCoreReconciler;

public class STFunctionReconciler implements STCoreReconciler {
	private static final String DEFAULT_INPUT_EVENT_NAME = "REQ"; //$NON-NLS-1$
	private static final String DEFAULT_OUTPUT_EVENT_NAME = "CNF"; //$NON-NLS-1$

	@Override
	public void reconcile(final LibraryElement dest, final Optional<? extends STCorePartition> source) {
		if (dest instanceof final FunctionFBType functionFbType && source.isPresent()
				&& source.get() instanceof final STFunctionPartition stFunctionPartition) {
			reconcile(functionFbType, stFunctionPartition);
		}
	}

	protected static void reconcile(final FunctionFBType dest, final STFunctionPartition source) {
		// check duplicates in source (very bad)
		if (checkDuplicates(source.getFunctions())) {
			return; // don't even try to attempt this or risk screwing dest up
		}
		// update package & imports
		CompilerInfo compilerInfo = dest.getCompilerInfo();
		if (compilerInfo == null) {
			compilerInfo = LibraryElementFactory.eINSTANCE.createCompilerInfo();
			dest.setCompilerInfo(compilerInfo);
		}
		compilerInfo.setPackageName(source.getPackageName());
		ECollections.setEList(compilerInfo.getImports(), source.getImports());
		// update body
		final STFunctionBody body = LibraryElementFactory.eINSTANCE.createSTFunctionBody();
		body.setText(source.getOriginalSource());
		dest.setBody(body);
		// reconcile type
		reconcileType(dest, source);
	}

	protected static void reconcileType(final FunctionFBType dest, final STFunctionPartition source) {
		findPrimaryFunction(dest, source).ifPresent(function -> reconcileType(dest, function));
	}

	protected static void reconcileType(final FunctionFBType dest, final STFunction source) {
		dest.setComment(source.getComment());
		reconcileInterface(dest.getInterfaceList(), source);
	}

	protected static void reconcileInterface(final InterfaceList interfaceList, final STFunction source) {
		ECollections.setEList(interfaceList.getEventInputs(), List.of(createEvent(DEFAULT_INPUT_EVENT_NAME, true)));
		ECollections.setEList(interfaceList.getEventOutputs(), List.of(createEvent(DEFAULT_OUTPUT_EVENT_NAME, false)));
		ECollections.setEList(interfaceList.getInputVars(),
				source.getInputParameters().stream().map(VarDeclaration.class::cast).map(EcoreUtil::copy).toList());
		ECollections.setEList(interfaceList.getOutputVars(),
				source.getOutputParameters().stream().map(VarDeclaration.class::cast).map(EcoreUtil::copy).toList());
		ECollections.setEList(interfaceList.getInOutVars(),
				source.getInOutParameters().stream().map(VarDeclaration.class::cast).map(EcoreUtil::copy).toList());
		if (source.getReturnType() != null) {
			final VarDeclaration returnVar = LibraryElementFactory.eINSTANCE.createVarDeclaration();
			returnVar.setName(""); //$NON-NLS-1$
			returnVar.setType(source.getReturnType());
			interfaceList.getOutputVars().add(0, returnVar);
		}
		addWiths(interfaceList.getEventInputs().get(0),
				Stream.concat(interfaceList.getInputVars().stream(), interfaceList.getInOutVars().stream()));
		addWiths(interfaceList.getEventOutputs().get(0),
				Stream.concat(interfaceList.getOutputVars().stream(), interfaceList.getOutMappedInOutVars().stream()));
	}

	protected static Event createEvent(final String name, final boolean input) {
		final Event event = LibraryElementFactory.eINSTANCE.createEvent();
		event.setName(name);
		event.setType(EventTypeLibrary.getInstance().getType(null));
		event.setIsInput(input);
		return event;
	}

	protected static void addWiths(final Event event, final Stream<VarDeclaration> withs) {
		withs.forEach(variable -> addWith(event, variable));
	}

	private static With addWith(final Event event, final VarDeclaration variable) {
		final With with = LibraryElementFactory.eINSTANCE.createWith();
		event.getWith().add(with);
		with.setVariables(variable);
		return with;
	}

	protected static Optional<STFunction> findPrimaryFunction(final FunctionFBType dest,
			final STFunctionPartition source) {
		return source.getFunctions().stream().filter(callable -> dest.getName().equals(callable.getName())).findFirst()
				.or(source.getFunctions().stream().filter(callable -> !callable.getName()
						.startsWith(STFunctionPartition.LOST_AND_FOUND_NAME))::findFirst);
	}

	protected static boolean checkDuplicates(final EList<? extends ICallable> list) {
		return list.stream().map(ICallable::getName).distinct().count() != list.size();
	}
}
