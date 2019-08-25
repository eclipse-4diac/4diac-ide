/*******************************************************************************
 * Copyright (c) 2018 TU Wien/ACIN, Johannes Kepler University
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Peter Gsellmann
 *   - extraction from
 *     org.eclipse.fordiac.ide.fbtypeeditor.ecc.properties.AlgorithmGroup
 *   Martin Melik-Merkumians
 *   	- made several methods abstract and moved implementation to respective
 *   sub-classes, also removed non-shareable methods to specialized sub-classes
 *   Alois Zoitl - harmonized code from algorithm group and simple alg editing
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
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.AbstractChangeAlgorithmTypeCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.ChangeAlgorithmTextCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.properties.AbstractECSection;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.TextAlgorithm;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

public abstract class AlgorithmEditingComposite {

	private CLabel languageLabel;
	private Combo languageCombo;
	private CLabel commentLabel;
	private Text commentText;
	private Composite codeEditors;
	private StackLayout stack;
	private Map<String, IAlgorithmEditor> editors = new HashMap<>();
	private IAlgorithmEditor currentAlgEditor;
	private CommandStack commandStack;
	private Algorithm currentAlgorithm;

	private boolean blockUpdates = false;

	private final IDocumentListener listener = new IDocumentListener() {
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
	
	public void createControls(final Composite parent, final FormToolkit toolkit) {
		Composite langAndComments = toolkit.createComposite(parent);
		langAndComments.setLayout(new GridLayout(4, false));
		langAndComments.setLayoutData(new GridData(GridData.FILL, 0, true, false));

		languageLabel = new CLabel(langAndComments, SWT.NONE);
		languageLabel.setBackground(parent.getBackground());
		languageLabel.setText(Messages.AlgorithmComposite_Language);
		languageCombo = new Combo(langAndComments, SWT.SINGLE | SWT.READ_ONLY);
		fillLanguageDropDown();
		languageCombo.addListener(SWT.Selection, event -> {
			AbstractChangeAlgorithmTypeCommand changeAlgorithmTypeCommand = getChangeAlgorithmTypeCommand(getFBType(), getAlgorithm(),
					languageCombo.getText());
			executeCommand(changeAlgorithmTypeCommand);
			setAlgorithm(changeAlgorithmTypeCommand.getNewAlgorithm());
		});

		commentLabel = new CLabel(langAndComments, SWT.NONE);
		commentLabel.setBackground(parent.getBackground());
		commentLabel.setText(Messages.AlgorithmComposite_Comment);
		commentText = toolkit.createText(langAndComments, "", SWT.SINGLE | SWT.BORDER); //$NON-NLS-1$
		commentText.setEditable(true);
		commentText.setEnabled(true);
		commentText.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false));
		commentText.addListener(SWT.Modify,
				e -> executeCommand(new ChangeCommentCommand(getAlgorithm(), commentText.getText())));

		GridData codeEditorsGridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		codeEditorsGridData.horizontalSpan = 1;
		codeEditorsGridData.minimumHeight = 250;
		codeEditors = new Group(parent, SWT.SHADOW_NONE);
		codeEditors.setBackground(toolkit.getColors().getBackground());
		codeEditors.setForeground(toolkit.getColors().getForeground());
		toolkit.adapt(codeEditors);
        
		codeEditors.setLayout(stack);
		codeEditors.setLayoutData(codeEditorsGridData);

		disableAllFields();
	}

	protected BaseFBType getFBType(){
		return (BaseFBType) currentAlgorithm.eContainer();
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

	public void initialize(BaseFBType fbType, CommandStack commandStack) {
		this.setCommandStack(commandStack);
		loadEditors(fbType);
	}

	private void loadEditors(BaseFBType basicFBType) {
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
				} else {
					stack.topControl = null;
					codeEditors.layout();
					disableAllFields();
				}
			} 
			updateAlgFields();
			setCommandStack(commandStackBuffer);
		}
	}

	protected void enableAllFields() {
		languageLabel.setEnabled(true);
		commentLabel.setEnabled(true);
		commentText.setEnabled(true);
		languageCombo.setEnabled(true);
	}

	protected void disableAllFields() {
		languageLabel.setEnabled(false);
		commentLabel.setEnabled(false);
		commentText.setEnabled(false);
		languageCombo.setEnabled(false);
	}

	protected void updateAlgFields(){
		Algorithm alg = getAlgorithm();
		commentText.setText((null != alg) ? alg.getComment() : ""); //$NON-NLS-1$
		languageCombo.select((null != alg) ? languageCombo.indexOf(getAlgorithmTypeString(getAlgorithm())) : 0 );
		if (alg instanceof TextAlgorithm) {
			currentAlgEditor.setAlgorithmText(((TextAlgorithm) alg).getText());
		} 
	}

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

	protected abstract AbstractChangeAlgorithmTypeCommand getChangeAlgorithmTypeCommand(BaseFBType fbType, Algorithm oldAlgorithm,
			String algorithmType);

	private CommandStack getCommandStack() {
		return commandStack;
	}

	private void setCommandStack(CommandStack commandStack) {
		this.commandStack = commandStack;
	}
}