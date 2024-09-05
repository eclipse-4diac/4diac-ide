/*******************************************************************************
 * Copyright (c) 2018, 2024 fortiss GmbH, Johannes Kepler University
 *                          Martin Erich Jobst
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.util;

import java.text.MessageFormat;
import java.util.Optional;
import java.util.function.Function;

import org.eclipse.core.runtime.Platform;
import org.eclipse.fordiac.ide.deployment.Messages;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.model.eval.value.Value;
import org.eclipse.fordiac.ide.model.eval.variable.VariableOperations;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public interface DeploymentHelper {

	String MGR_ID = "MGR_ID"; //$NON-NLS-1$

	static String getVariableValue(final VarDeclaration varDecl) throws DeploymentException {
		if (hasInitalValue(varDecl)) {
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

	static Function<VarDeclaration, String> getVariableValueRetargetable(final VarDeclaration varDecl,
			final SubApp subapp) throws DeploymentException {
		if (hasTypeOrInstanceInitialValue(subapp, varDecl)) {
			try {
				final Value value = VariableOperations.newVariable(varDecl).getValue();
				return destination -> VariableOperations.newVariable(destination, value).toString(false);
			} catch (final Exception e) {
				if (forceDeployement()) {
					return unused -> varDecl.getValue().getValue();
				}
				throw new DeploymentException(MessageFormat.format(Messages.DeploymentHelper_VariableValueError,
						varDecl.getValue().getValue(), varDecl.getQualifiedName(), e.getMessage()), e);
			}
		}
		return null;
	}

	static boolean hasTypeInitialValue(final SubApp subApp, final VarDeclaration varDec) {
		if (subApp.isTyped()) {
			final Optional<VarDeclaration> dec = subApp.getType().getInterfaceList().getInputVars().stream()
					.filter(v -> v.getName().contentEquals(varDec.getName())).findAny();
			if (dec.isPresent()) {
				return DeploymentHelper.hasInitalValue(dec.get());
			}
		}
		return false;
	}

	static boolean hasTypeOrInstanceInitialValue(final SubApp subApp, final VarDeclaration varDec) {
		return hasInitalValue(varDec) || hasTypeInitialValue(subApp, varDec);
	}

	private static boolean hasInitalValue(final VarDeclaration varDecl) {
		return varDecl.getValue() != null && varDecl.getValue().getValue() != null
				&& !varDecl.getValue().getValue().isEmpty();
	}

	static String getMgrID(final Device dev) throws DeploymentException {
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

	static String getMgrIDSafe(final Device dev) {
		try {
			return getMgrID(dev);
		} catch (final DeploymentException e) {
			FordiacLogHelper.logWarning(e.getMessage(), e);
		}
		return ""; //$NON-NLS-1$
	}

	static boolean forceDeployement() {
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

	static class ForceDeploymentHelper {
		private ForceDeploymentHelper() {
		}

		private static Boolean forceDeployment = null;
	}
}
