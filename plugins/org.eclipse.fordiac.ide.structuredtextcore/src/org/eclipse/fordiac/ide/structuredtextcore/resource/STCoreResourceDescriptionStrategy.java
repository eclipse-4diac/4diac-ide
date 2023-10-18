/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextcore.resource;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.data.AnyDerivedType;
import org.eclipse.fordiac.ide.model.eval.value.ValueOperations;
import org.eclipse.fordiac.ide.model.libraryElement.ICallable;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStandardFunction;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.util.STCoreUtil;
import org.eclipse.fordiac.ide.structuredtextcore.util.STCoreRegionString;
import org.eclipse.fordiac.ide.structuredtextcore.util.STCoreRegionStringCollectors;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.EObjectDescription;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.impl.DefaultResourceDescriptionStrategy;
import org.eclipse.xtext.util.IAcceptor;

import com.google.common.collect.ImmutableMap;

public class STCoreResourceDescriptionStrategy extends DefaultResourceDescriptionStrategy {

	private final static Logger LOG = Logger.getLogger(STCoreResourceDescriptionStrategy.class);

	public static final String CONTAINER_ECLASS_NAME = STCoreResourceDescriptionStrategy.class.getName()
			+ ".containerEClassName"; //$NON-NLS-1$
	public static final String DISPLAY_STRING = STCoreResourceDescriptionStrategy.class.getName() + ".DISPLAY_STRING"; //$NON-NLS-1$
	public static final String PARAMETER_PROPOSAL = STCoreResourceDescriptionStrategy.class.getName()
			+ ".PARAMETER_PROPOSAL"; //$NON-NLS-1$
	public static final String PARAMETER_PROPOSAL_REGIONS = STCoreResourceDescriptionStrategy.class.getName()
			+ ".PARAMETER_PROPOSAL_REGIONS"; //$NON-NLS-1$

	@Override
	public boolean createEObjectDescriptions(final EObject eObject, final IAcceptor<IEObjectDescription> acceptor) {
		if (getQualifiedNameProvider() == null) {
			return false;
		}
		try {
			final QualifiedName qualifiedName = getQualifiedNameProvider().getFullyQualifiedName(eObject);
			if (qualifiedName != null) {
				final ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
				fillUserData(eObject, builder);
				acceptor.accept(EObjectDescription.create(qualifiedName, eObject, builder.build()));
			}
		} catch (final Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return true;
	}

	@SuppressWarnings("static-method") // subclasses may override
	protected void fillUserData(final EObject eObject, final ImmutableMap.Builder<String, String> builder) {
		final EObject container = eObject.eContainer();
		if (container != null) {
			builder.put(CONTAINER_ECLASS_NAME, container.eClass().getName());
		}
		if (eObject instanceof final ICallable callable) {
			builder.put(DISPLAY_STRING, getCallableDisplayString(callable));
			final STCoreRegionString regionString = getCallableParameterProposal(callable);
			builder.put(PARAMETER_PROPOSAL, regionString.toString());
			builder.put(PARAMETER_PROPOSAL_REGIONS, regionString.getRegions().toString());
		}
	}

	public static String getCallableDisplayString(final ICallable callable) {
		return callable.getName()
				+ Stream.of(callable.getInputParameters().stream().map(param -> getCallableDisplayString(param, "")), //$NON-NLS-1$
						callable.getOutputParameters().stream().map(param -> getCallableDisplayString(param, "&")), //$NON-NLS-1$
						callable.getInOutParameters().stream().map(param -> getCallableDisplayString(param, "&&"))) //$NON-NLS-1$
						.flatMap(Function.identity())
						.collect(Collectors.joining(", ", "(", STCoreUtil.isCallableVarargs(callable) ? " ...)" : ")")) //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				+ (callable.getReturnType() != null ? callable.getReturnType().getName() : ""); //$NON-NLS-1$
	}

	protected static String getCallableDisplayString(final INamedElement parameter, final String typePrefix) {
		final INamedElement type = STCoreUtil.getFeatureType(parameter);
		if (type != null) {
			return parameter.getName() + ": " + typePrefix + type.getName(); //$NON-NLS-1$
		}
		return parameter.getName();
	}

	public static STCoreRegionString getCallableParameterProposal(final ICallable callable) {
		if (callable instanceof final STStandardFunction standardFunction && standardFunction.isVarargs()) {
			return new STCoreRegionString("()"); //$NON-NLS-1$
		}
		return Stream.of(callable.getInputParameters(), callable.getInOutParameters(), callable.getOutputParameters())
				.flatMap(Collection::stream).map(parameter -> getCallableParameterProposal(callable, parameter))
				.collect(STCoreRegionStringCollectors.joining(", ", "(", ")")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	protected static STCoreRegionString getCallableParameterProposal(final ICallable callable,
			final INamedElement parameter) {
		if (callable instanceof STStandardFunction) { // non-formal call for standard functions
			return new STCoreRegionString(getCallableParameterDefaultValue(callable, parameter), true);
		}
		return new STCoreRegionString(parameter.getName())
				.append(callable.getOutputParameters().contains(parameter) ? " => " : " := ") //$NON-NLS-1$ //$NON-NLS-2$
				.append(getCallableParameterDefaultValue(callable, parameter), true);
	}

	protected static String getCallableParameterDefaultValue(final ICallable callable, final INamedElement parameter) {
		if (callable.getInOutParameters().contains(parameter) || callable.getOutputParameters().contains(parameter)) {
			return "VAR"; //$NON-NLS-1$
		}
		if (parameter instanceof final VarDeclaration varDeclaration) {
			return getCallableParameterTypeDefaultValue(varDeclaration.getType());
		}
		if (parameter instanceof final STVarDeclaration stVarDeclaration) {
			return getCallableParameterTypeDefaultValue(stVarDeclaration.getType());
		}
		return ""; //$NON-NLS-1$
	}

	protected static String getCallableParameterTypeDefaultValue(final INamedElement type) {
		if (type instanceof AnyDerivedType) {
			return "VAR"; //$NON-NLS-1$
		}
		try {
			return ValueOperations.defaultValue(type).toString();
		} catch (final Exception e) {
			return ""; //$NON-NLS-1$
		}
	}
}
