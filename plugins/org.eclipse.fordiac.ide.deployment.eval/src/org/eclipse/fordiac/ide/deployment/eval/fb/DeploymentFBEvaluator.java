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
package org.eclipse.fordiac.ide.deployment.eval.fb;

import java.text.MessageFormat;

import org.eclipse.fordiac.ide.deployment.data.FBDeploymentData;
import org.eclipse.fordiac.ide.deployment.devResponse.Watches;
import org.eclipse.fordiac.ide.deployment.eval.Messages;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.model.eval.Evaluator;
import org.eclipse.fordiac.ide.model.eval.EvaluatorException;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class DeploymentFBEvaluator<T extends FBType> extends DeploymentFBNetworkElementEvaluator<T, FB> {

	private FBDeploymentData deploymentData;

	public DeploymentFBEvaluator(final T type, final Variable<?> context, final Iterable<Variable<?>> variables,
			final Evaluator parent) {
		super(type, LibraryElementFactory.eINSTANCE.createFB(), context, variables, parent);
	}

	@Override
	protected void deployInstance() throws DeploymentException {
		deploymentData = new FBDeploymentData("", getInstance()); //$NON-NLS-1$
		getSharedState().deploy(deploymentData);
	}

	@Override
	protected void deleteInstance() throws DeploymentException {
		getSharedState().deleteFB(deploymentData);
		deploymentData = null;
	}

	@Override
	protected void addWatch(final IInterfaceElement element) throws EvaluatorException {
		try {
			getSharedState().addWatch(getResourceRelativeName(element));
		} catch (final DeploymentException e) {
			throw new EvaluatorException(
					MessageFormat.format(Messages.DeploymentFBEvaluator_CannotAddWatch, element.getQualifiedName()), e,
					this);
		}
	}

	@Override
	protected void removeWatch(final IInterfaceElement element) throws EvaluatorException {
		try {
			getSharedState().removeWatch(getResourceRelativeName(element));
		} catch (final DeploymentException e) {
			throw new EvaluatorException(
					MessageFormat.format(Messages.DeploymentFBEvaluator_CannotRemoveWatch, element.getQualifiedName()),
					e, this);
		}
	}

	@Override
	protected void updateWatch(final Event event, final Watches watches) throws EvaluatorException {
		updateWatch(event, parseWatchValue(event, getWatchValue(watches, event)));
	}

	@Override
	protected void updateWatch(final VarDeclaration varDeclaration, final Watches watches) throws EvaluatorException {
		updateWatch(varDeclaration, parseWatchValue(varDeclaration, getWatchValue(watches, varDeclaration)));
	}

	@Override
	protected boolean triggerEvent(final Event event) throws EvaluatorException {
		try {
			getSharedState().triggerEvent(getResourceRelativeName(event));
		} catch (final DeploymentException e) {
			throw new EvaluatorException(
					MessageFormat.format(Messages.DeploymentFBEvaluator_CannotTriggerEvent, event.getQualifiedName()),
					e, this);
		}
		return true;
	}

	@Override
	protected void writeVariable(final VarDeclaration varDeclaration) throws EvaluatorException {
		final Variable<?> variable = getVariables().get(varDeclaration.getName());
		try {
			getSharedState().writeFBParameter(variable.toString(false), deploymentData, varDeclaration);
		} catch (final DeploymentException e) {
			throw new EvaluatorException(MessageFormat.format(Messages.DeploymentFBEvaluator_CannotWriteValue,
					varDeclaration.getQualifiedName()), e, this);
		}
	}

	protected FBDeploymentData getDeploymentData() {
		return deploymentData;
	}
}
