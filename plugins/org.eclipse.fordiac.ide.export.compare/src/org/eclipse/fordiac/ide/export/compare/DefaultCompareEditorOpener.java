/*******************************************************************************
 * Copyright (c) 2009 - 2016 Profactor GmbH, fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Thomas Strasser, Alois Zoitl, Martin Jobst
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.compare;

import java.io.File;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.CompareUI;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.fordiac.ide.export.ICompareEditorOpener;

/**
 * Implements the CompareEditorOpener extension point.
 * 
 * @author gebenh
 */
public class DefaultCompareEditorOpener implements ICompareEditorOpener {

	private String name;
	private String title;
	private File original;
	private File newlyGenerated;

	/**
	 * Constructor.
	 */
	public DefaultCompareEditorOpener() {
		// empty Constructor
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fodiac.ide.export.util.ICompareEditorOpener#hasDifferences()
	 */
	@Override
	public boolean hasDifferences() {
		EditorInput input = createInput();

		try {
			input.run(new NullProgressMonitor());
		} catch (Exception e) {
			Activator.getDefault().logError(e.getMessage(), e);
			return false;
		}
		return input.getCompareResult() != null;
	}

	private EditorInput createInput() {
		CompareConfiguration compareConfig = new CompareConfiguration();
		compareConfig.setLeftEditable(true);
		compareConfig.setLeftLabel(Messages.DefaultCompareEditorOpener_NEWLY_Exported + name);
		compareConfig.setRightEditable(false);
		compareConfig.setRightLabel(Messages.DefaultCompareEditorOpener_ORIGINAL_FILE + name);
		EditorInput input = new EditorInput(compareConfig, original, newlyGenerated);
		input.setTitle(title);
		return input;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fodiac.ide.export.util.ICompareEditorOpener#openCompareEditor()
	 */
	@Override
	public void openCompareEditor() {
		EditorInput input = createInput();
		// run the merge editor
		CompareUI.openCompareEditor(input);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fodiac.ide.export.util.ICompareEditorOpener#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fodiac.ide.export.util.ICompareEditorOpener#setTitle(java.lang.String)
	 */
	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fodiac.ide.export.util.ICompareEditorOpener#setNewFile(java.io.File)
	 */
	@Override
	public void setNewFile(File newFile) {
		this.newlyGenerated = newFile;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fodiac.ide.export.util.ICompareEditorOpener#setOriginalFile(java.io.File)
	 */
	@Override
	public void setOriginalFile(File original) {
		this.original = original;
	}

}
