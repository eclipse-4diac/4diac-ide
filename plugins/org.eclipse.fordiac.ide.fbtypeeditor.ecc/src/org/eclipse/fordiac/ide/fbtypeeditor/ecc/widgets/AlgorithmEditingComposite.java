/*******************************************************************************
 * Copyright (c) 2018 TU Wien/ACIN
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Peter Gsellmann
 *   - extraction from
 *     org.eclipse.fordiac.ide.fbtypeeditor.ecc.properties.AlgorithmGroup
 *   Martin Melik-Merkumians
 *   	- made several methods abstract and moved implementation to respective
 *   sub-classes, also removed non-shareable methods to specialized sub-classes
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.widgets;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.Activator;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.IAlgorithmEditor;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.IAlgorithmEditorCreator;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.Messages;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.ChangeAlgorithmTextCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.properties.AbstractECSection;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.TextAlgorithm;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

public abstract class AlgorithmEditingComposite {

	protected Group algorithmGroup;
	protected CLabel languageLabel;
	protected Combo languageCombo;
	protected CLabel commentLabel;
	protected Text commentText;
	protected Composite codeEditors;
	protected StackLayout stack;
	private Map<String, IAlgorithmEditor> editors = new HashMap<>();
	protected IAlgorithmEditor currentAlgEditor;
	private CommandStack commandStack;
	protected Algorithm currentAlgorithm;

	protected boolean blockUpdates = false;

	protected final IDocumentListener listener = new IDocumentListener() {
		@Override
		public void documentChanged(final DocumentEvent event) {
			if ((getAlgorithm() != null) && (null != currentAlgEditor) && currentAlgEditor.isDocumentValid()) {
				executeCommand(new ChangeAlgorithmTextCommand((TextAlgorithm) getAlgorithm(),
						currentAlgEditor.getAlgorithmText()));
			}
		}

		@Override
		public void documentAboutToBeChanged(final DocumentEvent event) {
			// no action required
		}
	};

	public AlgorithmEditingComposite() {
		stack = new StackLayout();
	}

	protected Algorithm getAlgorithm() {
		return currentAlgorithm;
	}

	protected void executeCommand(Command cmd) {
		if (null != currentAlgorithm && getCommandStack() != null) {
			blockUpdates = true;
			getCommandStack().execute(cmd);
			blockUpdates = false;
		}
	}

	public void initialize(BasicFBType basicFBType, CommandStack commandStack) {
		this.setCommandStack(commandStack);
		loadEditors(basicFBType);
	}

	public void loadEditors(BaseFBType basicFBType) {
		editors.clear();
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint point = registry.getExtensionPoint("org.eclipse.fordiac.ide.fbtypeeditor.ecc.algorithmEditor"); //$NON-NLS-1$
		IExtension[] extensions = point.getExtensions();
		for (IExtension extension : extensions) {
			IConfigurationElement[] elements = extension.getConfigurationElements();
			for (IConfigurationElement element : elements) {
				Object obj = null;
				try {
					obj = element.createExecutableExtension("class"); //$NON-NLS-1$
				} catch (Exception e) {
					Activator.getDefault().logError(e.getMessage(), e);
				}
				if (obj instanceof IAlgorithmEditorCreator) {
					IAlgorithmEditor editor = ((IAlgorithmEditorCreator) obj).createAlgorithmEditor(codeEditors,
							basicFBType);
					String lang = element.getAttribute("language"); //$NON-NLS-1$
					editors.put(lang, editor);
				}
			}
		}
	}

	public void setAlgorithm(Algorithm algorithm) {
		if (!blockUpdates) {
			// set commandStack to null so that an update will not lead to a changed type
			CommandStack commandStackBuffer = getCommandStack();
			setCommandStack(null);
			if (this.currentAlgorithm != algorithm) {
				currentAlgorithm = algorithm;
				if (null != currentAlgorithm) {
					initializeEditor();
					enableAllFields();
					updateAlgFields();
				} else {
					algorithmGroup.setText(Messages.ECAlgorithmGroup_Title);
					commentText.setText(""); //$NON-NLS-1$
					languageCombo.select(0);
					stack.topControl = null;
					codeEditors.layout();
					disableAllFields();
				}
			} else if (null != currentAlgorithm) {
				// update the content of the algorithm only
				updateAlgFields();
			}
			setCommandStack(commandStackBuffer);
		}
	}

	protected abstract void enableAllFields();

	protected abstract void disableAllFields();

	protected abstract void updateAlgFields();

	private void initializeEditor() {
		if (null != currentAlgEditor) {
			currentAlgEditor.removeDocumentListener(listener);
		}
		String algType = getAlgorithmTypeString(getAlgorithm());
		currentAlgEditor = editors.get(algType);
		if (null != currentAlgEditor) {
			stack.topControl = currentAlgEditor.getControl();
			currentAlgEditor.addDocumentListener(listener);
		}
		codeEditors.layout();
	}

	protected static String getAlgorithmTypeString(Algorithm algorithm) {
		if (algorithm instanceof STAlgorithm) {
			return "ST"; //$NON-NLS-1$
		}
		if (algorithm instanceof TextAlgorithm) {
			return "AnyText"; //$NON-NLS-1$
		}
		return "AnyText"; // per default return any text and show it as generic text //$NON-NLS-1$
	}

	protected void fillLanguageDropDown() {
		languageCombo.removeAll();
		for (String alg : AbstractECSection.getLanguages()) {
			languageCombo.add(alg);
		}
	}

	protected abstract Command getChangeAlgorithmTypeCommand(BaseFBType fbType, Algorithm oldAlgorithm,
			String algorithmType);

	public CommandStack getCommandStack() {
		return commandStack;
	}

	public void setCommandStack(CommandStack commandStack) {
		this.commandStack = commandStack;
	}
}