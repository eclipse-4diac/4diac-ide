/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.deployment.debug.watch;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.fordiac.ide.deployment.debug.DeploymentDebugDevice;
import org.eclipse.fordiac.ide.deployment.debug.IDeploymentDebugElement;
import org.eclipse.fordiac.ide.model.eval.EvaluatorException;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.ITypedElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public interface IWatch extends IVariable, IDeploymentDebugElement {

	/**
	 * {@inheritDoc}
	 */
	@Override
	String getName();

	/**
	 * {@inheritDoc}
	 */
	@Override
	DeploymentDebugDevice getDebugTarget();

	/**
	 * Get the qualified name of the watch
	 *
	 * @return The qualified name
	 */
	String getQualifiedName();

	/**
	 * Get the resource this watch belongs to
	 *
	 * @return The resource
	 */
	Resource getResource();

	/**
	 * Get the watched element
	 *
	 * @return The element
	 */
	ITypedElement getWatchedElement();

	/**
	 * Add the watch in target
	 *
	 * @throws DebugException if an exception occurred during installation
	 */
	void addWatch() throws DebugException;

	/**
	 * Remove the watch from target
	 *
	 * @throws DebugException if an exception occurred during installation
	 */
	void removeWatch() throws DebugException;

	/**
	 * Update the watch with the given data
	 *
	 * @param watchData The watch data
	 */
	void updateValue(DeploymentDebugWatchData watchData);

	/**
	 * Check if the watch was recently updated
	 *
	 * @return true if alive, false otherwise
	 */
	boolean isAlive();

	/**
	 * Create a new watch for an element
	 *
	 * @param name        The name of the watch
	 * @param element     The element
	 * @param debugTarget The debug target
	 * @return The created watch
	 * @throws EvaluatorException            if there is a problem creating the
	 *                                       watch
	 * @throws UnsupportedOperationException if the element is not supported
	 */
	static IWatch watchFor(final String name, final INamedElement element, final DeploymentDebugDevice debugTarget)
			throws EvaluatorException, UnsupportedOperationException {
		return switch (element) {
		case final Event event when DeploymentDebugWatchUtils.isSubAppInterfaceElement(event) ->
			new SubAppEventWatch(name, event, debugTarget);
		case final Event event -> new EventWatch(name, event, debugTarget);
		case final VarDeclaration varDeclaration when DeploymentDebugWatchUtils
				.isSubAppInterfaceElement(varDeclaration) ->
			new SubAppVarDeclarationWatch(name, varDeclaration, debugTarget);
		case final VarDeclaration varDeclaration -> new VarDeclarationWatch(name, varDeclaration, debugTarget);
		case final AdapterDeclaration adapterDeclaration ->
			new AdapterDeclarationWatch(name, adapterDeclaration, debugTarget);
		case final FBNetworkElement networkElement -> new FBNetworkElementWatch(name, networkElement, debugTarget);
		default -> throw new UnsupportedOperationException("Unsupported element: " + element.eClass().getName()); //$NON-NLS-1$
		};
	}

	/**
	 * Create a new watch for an element
	 *
	 * @param name                 The name of the watch
	 * @param element              The element
	 * @param resource             The resource of the element
	 * @param resourceRelativeName The resource-relative name of the element
	 * @param debugTarget          The debug target
	 * @return The created watch
	 * @throws EvaluatorException            if there is a problem creating the
	 *                                       watch
	 * @throws UnsupportedOperationException if the element is not supported
	 */
	static IWatch watchFor(final String name, final INamedElement element, final Resource resource,
			final String resourceRelativeName, final DeploymentDebugDevice debugTarget)
			throws EvaluatorException, UnsupportedOperationException {
		return switch (element) {
		case final IInterfaceElement interfaceElement when DeploymentDebugWatchUtils
				.isSubAppInterfaceElement(interfaceElement) ->
			throw new UnsupportedOperationException("Unsupported element: " + element.eClass().getName()); //$NON-NLS-1$
		case final Event event -> new EventWatch(name, event, resource, resourceRelativeName, debugTarget);
		case final VarDeclaration varDeclaration ->
			new VarDeclarationWatch(name, varDeclaration, resource, resourceRelativeName, debugTarget);
		case final AdapterDeclaration adapterDeclaration ->
			new AdapterDeclarationWatch(name, adapterDeclaration, resource, resourceRelativeName, debugTarget);
		case final FBNetworkElement networkElement ->
			new FBNetworkElementWatch(name, networkElement, resource, resourceRelativeName, debugTarget);
		default -> throw new UnsupportedOperationException("Unsupported element: " + element.eClass().getName()); //$NON-NLS-1$
		};
	}
}
