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
package org.eclipse.fordiac.ide.structuredtextcore.resource;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.data.AnyDerivedType;
import org.eclipse.fordiac.ide.model.eval.value.ValueOperations;
import org.eclipse.fordiac.ide.model.libraryElement.ICallable;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.ITypedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.ErrorTypeEntry;
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

import com.google.common.collect.ForwardingMap;

/**
 * Resource description strategy for ST core and derivatives.
 * <p>
 * This class overrides the default Xtext resource description strategy to adapt
 * it to the specific requirements of the ST core language and its derivatives:
 * <p>
 * <ul>
 * <li>provide additional information in the index for code completion,
 * <li>provide a signature hash and type information to detect changes in
 * exported descriptions,
 * <li>filter reference descriptions to immutable built-in or error placeholder
 * types.
 * </ul>
 */
public class STCoreResourceDescriptionStrategy extends DefaultResourceDescriptionStrategy {

	private static final Logger LOG = Logger.getLogger(STCoreResourceDescriptionStrategy.class);

	public static final String TYPE_URI = STCoreResourceDescriptionStrategy.class.getName() + ".TYPE_URI"; //$NON-NLS-1$
	public static final String SIGNATURE_HASH = STCoreResourceDescriptionStrategy.class.getName() + ".SIGNATURE_HASH"; //$NON-NLS-1$
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
				acceptor.accept(EObjectDescription.create(qualifiedName, eObject, createLazyUserData(eObject)));
			}
		} catch (final Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return true;
	}

	protected Map<String, String> createLazyUserData(final EObject eObject) {
		return new ForwardingMap<>() {
			private Map<String, String> delegate;

			@Override
			protected Map<String, String> delegate() {
				if (delegate == null) {
					final Map<String, String> userData = new HashMap<>();
					fillUserData(eObject, userData);
					delegate = Map.copyOf(userData);
				}
				return delegate;
			}
		};
	}

	@SuppressWarnings("static-method") // subclasses may override
	protected void fillUserData(final EObject eObject, final Map<String, String> userData) {
		final EObject container = eObject.eContainer();
		if (container != null) {
			userData.put(CONTAINER_ECLASS_NAME, container.eClass().getName());
		}
		if (eObject instanceof final ICallable callable) {
			userData.put(SIGNATURE_HASH, computeSignatureHash(callable));
			userData.put(DISPLAY_STRING, getCallableDisplayString(callable));
			final STCoreRegionString regionString = getCallableParameterProposal(callable);
			userData.put(PARAMETER_PROPOSAL, regionString.toString());
			userData.put(PARAMETER_PROPOSAL_REGIONS, regionString.getRegions().toString());
		} else if (eObject instanceof final ITypedElement typedElement && typedElement.getType() != null) {
			userData.put(TYPE_URI, EcoreUtil.getURI(typedElement.getType()).toString());
			userData.put(DISPLAY_STRING, getTypedElementDisplayString(typedElement));
		}
	}

	public static String computeSignatureHash(final ICallable callable) {
		final SignatureHashBuilder builder = new SignatureHashBuilder();
		Stream.of(callable.getInputParameters(), callable.getOutputParameters(), callable.getInOutParameters())
				.flatMap(Collection::stream).forEachOrdered(builder::appendParameter);
		if (callable.getReturnType() != null) {
			builder.appendType(callable.getReturnType());
		}
		return builder.hash();
	}

	public static int computeParameterHash(final INamedElement parameter) {
		if (parameter instanceof final ITypedElement typedElement) {
			return Objects.hash(typedElement.getName(), typedElement.getType());
		}
		return parameter.getName().hashCode();
	}

	public static String getCallableDisplayString(final ICallable callable) {
		return callable.getName()
				+ Stream.of(callable.getInputParameters().stream().map(param -> getCallableDisplayString(param, "")), //$NON-NLS-1$
						callable.getOutputParameters().stream().map(param -> getCallableDisplayString(param, "&")), //$NON-NLS-1$
						callable.getInOutParameters().stream().map(param -> getCallableDisplayString(param, "&&"))) //$NON-NLS-1$
						.flatMap(Function.identity())
						.collect(Collectors.joining(", ", "(", STCoreUtil.isCallableVarargs(callable) ? " ...)" : ")")) //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				+ (callable.getReturnType() != null ? " : " + callable.getReturnType().getName() : ""); //$NON-NLS-1$ //$NON-NLS-2$
	}

	protected static String getCallableDisplayString(final INamedElement parameter, final String typePrefix) {
		final INamedElement type = STCoreUtil.getFeatureType(parameter);
		if (type != null) {
			return parameter.getName() + " : " + typePrefix + type.getName(); //$NON-NLS-1$
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

	public static String getTypedElementDisplayString(final ITypedElement typedElement) {
		return typedElement.getName() + " : " + typedElement.getTypeName(); //$NON-NLS-1$
	}

	@Override
	protected boolean isResolvedAndExternal(final EObject from, final EObject to) {
		if (to instanceof final LibraryElement libraryElement && !to.eIsProxy()
				&& (libraryElement.getTypeEntry() == null || libraryElement.getTypeEntry() instanceof ErrorTypeEntry)) {
			return false;
		}
		return super.isResolvedAndExternal(from, to);
	}

	public static class SignatureHashBuilder {

		private final MessageDigest digest;
		private final StringBuilder builder;

		public SignatureHashBuilder() {
			digest = createDigest();
			builder = new StringBuilder();
		}

		protected static MessageDigest createDigest() {
			try {
				return MessageDigest.getInstance("MD5"); //$NON-NLS-1$
			} catch (final NoSuchAlgorithmException e) {
				LOG.error("Error creating message digest", e); //$NON-NLS-1$
				return null;
			}
		}

		protected void append(final String s) {
			if (digest != null) {
				digest.update(s.getBytes(StandardCharsets.UTF_8));
			}
			builder.append(s);
		}

		public SignatureHashBuilder appendType(final INamedElement type) {
			if (type != null) {
				append(EcoreUtil.getURI(type).toString());
			}
			return this;
		}

		public SignatureHashBuilder appendParameter(final INamedElement parameter) {
			append(parameter.getName());
			if (parameter instanceof final ITypedElement typedElement) {
				appendType(typedElement.getType());
			}
			return this;
		}

		public String hash() {
			if (digest != null) {
				try {
					return new BigInteger(digest.digest()).toString(16);
				} catch (final Exception e) {
					LOG.error("Error hashing signature", e); //$NON-NLS-1$
				}
			}
			return builder.toString();
		}

		@Override
		public String toString() {
			return builder.toString();
		}
	}
}
