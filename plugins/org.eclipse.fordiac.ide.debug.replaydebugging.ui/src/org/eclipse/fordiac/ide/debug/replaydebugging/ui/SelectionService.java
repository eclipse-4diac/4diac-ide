/*******************************************************************************
 * Copyright (c) 2026 Jose Cabral
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Jose Cabral
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.debug.replaydebugging.ui;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.deployment.debug.ui.annotation.WatchValueAnnotation;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;

public class SelectionService implements ISelectionListener {

	private static String SYSTEM_EDITOR_PAGE_ID = "org.eclipse.fordiac.ide.editors.SystemEditor"; //$NON-NLS-1$

	private SelectionService() {
		// singleton
	}

	private static final SelectionService INSTANCE = new SelectionService();
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	public static final String PROPERTY_SELECTION = "replayDebuggingSelection"; //$NON-NLS-1$

	private final List<String> lastSelectedDatapoints = new ArrayList<>();

	public static SelectionService getDefault() {
		return INSTANCE;
	}

	public void install(final IWorkbenchPage page) {
		page.addSelectionListener(this);
	}

	public void uninstall(final IWorkbenchPage page) {
		page.removeSelectionListener(this);
	}

	@Override
	public void selectionChanged(final IWorkbenchPart part, final ISelection selection) {
		if (!part.getSite().getId().equals(SYSTEM_EDITOR_PAGE_ID)
				|| !(selection instanceof final IStructuredSelection sel)) {
			return; // accept only selections in the system editor
		}

		final var it = sel.iterator();
		lastSelectedDatapoints.clear();
		while (it.hasNext()) {
			final var element = it.next();
			if (!(element instanceof final EditPart ep)) {
				continue;
			}

			switch (ep.getModel()) {
			case final IInterfaceElement interf ->
				lastSelectedDatapoints.add(removeDeviceAndResourceNamesIfPresent(interf));
			case final FBNetworkElement fb -> {
				// find all interfaces and variables from a FB block
				// omit non interface elements, or interface from subApps
				final var fbBlockIt = fb.eAllContents();
				while (fbBlockIt.hasNext()) {
					final EObject obj = fbBlockIt.next();
					if (!(obj instanceof final IInterfaceElement interfaceElement)) {
						continue;
					}
					lastSelectedDatapoints.add(removeDeviceAndResourceNamesIfPresent(interfaceElement));
				}
				if (fb.getType() instanceof BasicFBType) {
					// add also the ECC current state
					lastSelectedDatapoints.add(removeDeviceAndResourceNamesIfPresent(fb) + ".!ECC");
				}
			}
			case final org.eclipse.fordiac.ide.model.libraryElement.Connection conn -> {
				lastSelectedDatapoints.add(conn.getSource().getQualifiedName());
				lastSelectedDatapoints.add(conn.getDestination().getQualifiedName());
				// add also the connection data
				lastSelectedDatapoints.add(removeDeviceAndResourceNamesIfPresent(conn.getSource()) + "!"
						+ removeDeviceAndResourceNamesIfPresent(conn.getDestination()));
			}
			case final WatchValueAnnotation watch -> lastSelectedDatapoints.add(watch.getElement().getQualifiedName());
			default -> {
				// ignore other types of elements
			}
			}
		}

		pcs.firePropertyChange(PROPERTY_SELECTION, null, null);

	}

	private static String removeDeviceAndResourceNamesIfPresent(final IInterfaceElement element) {
		if (element.getQualifiedName()
				.startsWith(element.getBlockFBNetworkElement().getResource().getQualifiedName())) {
			return element.getQualifiedName()
					.substring(element.getBlockFBNetworkElement().getResource().getQualifiedName().length() + 1); // extra
																													// dot
		}
		return element.getQualifiedName();
	}

	private static String removeDeviceAndResourceNamesIfPresent(final FBNetworkElement element) {
		if (element.getQualifiedName().startsWith(element.getResource().getQualifiedName())) {
			return element.getQualifiedName().substring(element.getResource().getQualifiedName().length() + 1); // extra
																												// dot
		}
		return element.getQualifiedName();
	}

	public List<String> getSelectedElements() {
		return lastSelectedDatapoints;
	}

	public void addPropertyChangeListener(final PropertyChangeListener l) {
		pcs.addPropertyChangeListener(l);
	}

	public void removePropertyChangeListener(final PropertyChangeListener l) {
		pcs.removePropertyChangeListener(l);
	}
}
