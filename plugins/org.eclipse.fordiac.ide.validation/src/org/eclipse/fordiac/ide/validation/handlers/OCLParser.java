/*******************************************************************************
 * Copyright (c) 2020 Sandor Bacsi
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sandor Bacsi - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.validation.handlers;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.validation.Activator;
import org.eclipse.ocl.OCLInput;
import org.eclipse.ocl.ParserException;
import org.eclipse.ocl.ecore.Constraint;
import org.osgi.framework.Bundle;

public class OCLParser {
	private static final String CONSTRAINT_DIRECTORY = "constraints"; //$NON-NLS-1$
	private static final String CONSTRAINT_FILE_FBTYPE = "ECC.ocl"; //$NON-NLS-1$
	private static final String CONSTRAINT_FILE_APP = "FB.ocl"; //$NON-NLS-1$

	private OCLParser() {
	}

	public static List<Constraint> loadOCLConstraints(INamedElement element) {
		InputStream in = null;
		String constraintFile;
		List<Constraint> constraints = new ArrayList<>();
		Bundle bundle = Activator.getDefault().getBundle();
		constraintFile = getOCLFile(element);
		try {
			URL url = FileLocator.find(bundle, new Path(CONSTRAINT_DIRECTORY + IPath.SEPARATOR + constraintFile));
			url = FileLocator.toFileURL(url);
			in = url.openStream();
			OCLInput document = new OCLInput(in);
			constraints.addAll(Activator.getDefault().getOclInstance().parse(document));

		} catch (ParserException | IOException e) {
			Activator.getDefault().logError(e.getMessage(), e);
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException ioe) {
					Activator.getDefault().logError(ioe.getMessage(), ioe);
				}
		}
		return constraints;
	}

	private static String getOCLFile(INamedElement element) {
		if (element instanceof FBType) {
			return CONSTRAINT_FILE_FBTYPE;
		}

		if (element instanceof Application || element instanceof SubApp) {
			return CONSTRAINT_FILE_APP;
		}
		return null;
	}
}
