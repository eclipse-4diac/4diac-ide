/*******************************************************************************
 * Copyright (c) 2018, 2025 fortiss GmbH, Johannes Kepler University
 *                          Martin Erich Jobst, MSR Software GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *   Martin Jobst - adopt new ST editor for values
 *                - rework initial value handling
 *                - add connection source suffix for delegate connections
 *   Tim Grocki - deploy initial values of FB types with ForteTypeOverride
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.util;

import java.text.MessageFormat;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.Platform;
import org.eclipse.fordiac.ide.deployment.Messages;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.interactors.ForteTypeNameCreator;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.GenericTypes;
import org.eclipse.fordiac.ide.model.eval.value.Value;
import org.eclipse.fordiac.ide.model.eval.value.ValueOperations;
import org.eclipse.fordiac.ide.model.eval.variable.VariableOperations;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableFB;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public final class DeploymentHelper {

	public static final String MGR_ID = "MGR_ID"; //$NON-NLS-1$

	private static final String NEGATED_CONNECTION_SUFFIX = ".NOT"; //$NON-NLS-1$

	public static String getVariableValue(final VarDeclaration varDecl) throws DeploymentException {
		if (VariableOperations.hasDeclaredInitialValue(varDecl)) {
			try {
				return VariableOperations.newVariable(varDecl).toString(false);
			} catch (final Exception e) {
				if (forceDeployement()) {
					return varDecl.getValue().getValue();
				}
				throw new DeploymentException(MessageFormat.format(Messages.DeploymentHelper_VariableValueError,
						varDecl.getValue().getValue(), varDecl.getQualifiedName(), e.getMessage()), e);
			}
		}
		return null;
	}

	public static String getVariableValue(final VarDeclaration source, final VarDeclaration destination,
			final boolean negate) throws DeploymentException {
		// the FORTE type of an overridden FB type does not know the IDE type's initial
		// values, so these have to be deployed as well
		final boolean typeOverridden = hasDeployTypeOverride(destination.getBlockFBNetworkElement());
		// deploy if source is different from destination (e.g., subapp connection)
		// or when there is an initial value on the source
		if (source != destination || VariableOperations.hasDeclaredInitialValue(source)
				|| (typeOverridden && VariableOperations.hasInheritedInitialValue(source))) {
			try {
				// get source value
				Value value = VariableOperations.newVariable(source).getValue();
				// negate?
				if (negate) {
					value = ValueOperations.bitwiseNot(value);
				}
				// deploy only if
				// - destination is generic
				// - designated value is not equal with initial value of destination type
				// - or the destination FB type is deployed as another FORTE type
				if (!typeOverridden && !GenericTypes.isAnyType(destination.getType())
						&& equalsTypeValue(value, destination)) {
					return null;
				}
				// convert to destination string
				return VariableOperations.newVariable(destination, value).toString(false);
			} catch (final Exception e) {
				final String sourceValue = source.getValueString();
				if (forceDeployement() && !sourceValue.isEmpty()) {
					return sourceValue;
				}
				throw new DeploymentException(MessageFormat.format(Messages.DeploymentHelper_VariableValueError,
						sourceValue, destination.getQualifiedName(), e.getMessage()), e);
			}
		}
		return null;
	}

	/**
	 * Checks whether the FB's type sets
	 * {@code eclipse4diac::core::ForteTypeOverride}, so that the FB is deployed as
	 * another FORTE type.
	 *
	 * @param fb the FB, may be {@code null}
	 * @return {@code true} if the FB's type has a non-empty override or one that
	 *         cannot be resolved; {@code false} for {@code null} and configurable
	 *         FBs
	 */
	private static boolean hasDeployTypeOverride(final FBNetworkElement fb) {
		if (fb == null || fb instanceof ConfigurableFB) {
			return false; // configurable FBs ignore the override
		}
		try {
			return ForteTypeNameCreator.getDeployTypeOverride(fb.getType()) != null;
		} catch (final DeploymentException e) {
			// an invalid override is reported when the type name is resolved
			return true;
		}
	}

	private static boolean equalsTypeValue(final Value value, final VarDeclaration variable) {
		final VarDeclaration typeVariable = variable.findInTypeInterface();
		if (typeVariable == null || GenericTypes.isAnyType(typeVariable.getType())) {
			return false;
		}
		final Value destinationTypeValue = VariableOperations.newVariable(typeVariable).getValue();
		return ValueOperations.equals(value, destinationTypeValue);
	}

	public static String getSourceSuffix(final Connection conn) {
		if (conn.isNegated()) {
			return NEGATED_CONNECTION_SUFFIX;
		}
		return ""; //$NON-NLS-1$
	}

	public static void addSourceSuffix(final StringBuilder suffix, final Connection conn) {
		toggleNegatedSourceSuffix(suffix, conn);
	}

	public static void removeSourceSuffix(final StringBuilder suffix, final Connection conn) {
		toggleNegatedSourceSuffix(suffix, conn);
	}

	private static void toggleNegatedSourceSuffix(final StringBuilder suffix, final Connection conn) {
		if (conn.isNegated()) {
			if (suffix.indexOf(NEGATED_CONNECTION_SUFFIX) == 0) {
				suffix.delete(0, NEGATED_CONNECTION_SUFFIX.length());
			} else {
				suffix.insert(0, NEGATED_CONNECTION_SUFFIX);
			}
		}
	}

	public static String getMgrID(final Device dev) throws DeploymentException {
		for (final VarDeclaration varDecl : dev.getVarDeclarations()) {
			if (MGR_ID.equalsIgnoreCase(varDecl.getName())) {
				final String val = DeploymentHelper.getVariableValue(varDecl);
				if (null != val) {
					return val;
				}
			}
		}
		return ""; //$NON-NLS-1$
	}

	public static String getMgrIDSafe(final Device dev) {
		try {
			return getMgrID(dev);
		} catch (final DeploymentException e) {
			ILog.get().warn(e.getMessage(), e);
		}
		return ""; //$NON-NLS-1$
	}

	private static boolean forceDeployement() {
		if (ForceDeploymentHelper.forceDeployment == null) {
			ForceDeploymentHelper.forceDeployment = Boolean.FALSE;
			final String[] args = Platform.getCommandLineArgs();
			for (final String arg : args) {
				if ("-forceDeployment".equals(arg)) { //$NON-NLS-1$
					ForceDeploymentHelper.forceDeployment = Boolean.TRUE;
					break;
				}
			}
		}
		return ForceDeploymentHelper.forceDeployment.booleanValue();
	}

	private static class ForceDeploymentHelper {
		private ForceDeploymentHelper() {
		}

		private static Boolean forceDeployment = null;
	}

	private DeploymentHelper() {
		throw new UnsupportedOperationException();
	}
}
