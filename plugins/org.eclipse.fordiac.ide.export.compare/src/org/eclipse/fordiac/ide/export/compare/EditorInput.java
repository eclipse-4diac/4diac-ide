/*******************************************************************************
 * Copyright (c) 2009  Profactor GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Thomas Strasser
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.compare;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.CompareEditorInput;
import org.eclipse.compare.structuremergeviewer.Differencer;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;

/**
 * The Class EditorInput.
 */
class EditorInput extends CompareEditorInput {

	private final File original;
	private final File generated;

	/**
	 * Instantiates a new editor input.
	 * 
	 * @param configuration the configuration
	 * @param original the original
	 * @param generated the generated
	 */
	public EditorInput(CompareConfiguration configuration, File original,
			File generated) {
		super(configuration);
		this.original = original;
		this.generated = generated;
	}

	@Override
	protected Object prepareInput(IProgressMonitor monitor)
			throws InvocationTargetException, InterruptedException {
		Differencer d = new Differencer();
		Object diff = d.findDifferences(false, new NullProgressMonitor(), null,
				null, new Input(generated), new Input(original));

		return diff;
	}
}
