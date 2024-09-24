/*******************************************************************************
 * Copyright (c) 2008, 2024 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 * 							Johannes Kepler University, Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Ingo Hegny, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *
 *   Peter Gsellmann
 *     - incorporating simple fb
 *
 *   Daniel Lindhuber, Bianca Wiesmayr
 *     - cleanup
 *   Patrick Aigner
 *     - change dialog integration
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.editors;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.fordiac.ide.fbtypeeditor.Messages;
import org.eclipse.fordiac.ide.model.commands.change.AbstractChangeInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.commands.create.CreateInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteInterfaceCommand;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.search.dialog.FBTypeEntryDataHandler;
import org.eclipse.fordiac.ide.model.search.dialog.FBTypeUpdateDialog;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.typeeditor.AbstractTypeEditor;
import org.eclipse.fordiac.ide.typeeditor.ITypeEditorPage;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.fordiac.ide.ui.contentoutline.MultiPageEditorContentOutlinePage;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CommandStackEvent;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.INavigationLocation;
import org.eclipse.ui.INavigationLocationProvider;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.ide.IGotoMarker;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

public class FBTypeEditor extends AbstractTypeEditor implements IGotoMarker, INavigationLocationProvider {

	private IContentOutlinePage contentOutline = null;

	private static final int DEFAULT_BUTTON_INDEX = 0; // Save Button
	private static final int CANCEL_BUTTON_INDEX = 1;
	private int interfaceChanges = 0; // number of interface changes happend since the last save

	@Override
	public void doSave(final IProgressMonitor monitor) {
		if ((null != getTypeEntry()) && (checkTypeSaveAble())) {
			performPresaveHooks();
			if (interfaceChanges != 0) {
				createSaveDialog(monitor);
			} else {
				doSaveInternal(monitor);
			}
		}
	}

	private void createSaveDialog(final IProgressMonitor monitor) {
		final String[] labels = { Messages.FBTypeEditor_AlteringButton_SaveAndUpdate, // Messages.StructAlteringButton_SaveAs,
				SWT.getMessage("SWT_Cancel") }; //$NON-NLS-1$

		final FBTypeUpdateDialog<TypeEntry> fbSaveDialog = new FBTypeUpdateDialog<>(null,
				Messages.FBTypeEditor_ViewingComposite_Headline, null, "", //$NON-NLS-1$
				MessageDialog.NONE, labels, DEFAULT_BUTTON_INDEX, new FBTypeEntryDataHandler(getTypeEntry()));

		// Depending on the button clicked:
		switch (fbSaveDialog.open()) {
		case DEFAULT_BUTTON_INDEX:
			doSaveInternal(monitor);
			break;
		case CANCEL_BUTTON_INDEX:
			MessageDialog.openInformation(null, Messages.FBTypeEditor_ViewingComposite_Headline,
					Messages.WarningDialog_FBNotSaved);
			break;
		default:
			break;
		}
	}

	private void performPresaveHooks() {
		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		final IConfigurationElement[] config = registry
				.getConfigurationElementsFor("org.eclipse.fordiac.ide.fbtypeeditor.fBTEditorValidation"); //$NON-NLS-1$

		for (final IConfigurationElement e : config) {
			try {
				final Object o = e.createExecutableExtension("class"); //$NON-NLS-1$
				if (o instanceof final IFBTValidation fbtValidation) {
					fbtValidation.invokeValidation(getType());
				}
			} catch (final CoreException ex) {
				FordiacLogHelper.logError(ex.getMessage(), ex);
			}
		}
	}

	protected boolean checkTypeSaveAble() {
		if (getType() instanceof final CompositeFBType cFBType) {
			// only allow to save composite FBs if all contained FBs have types
			for (final FBNetworkElement fb : cFBType.getFBNetwork().getNetworkElements()) {
				if (null == fb.getTypeEntry()) {
					MessageDialog.openInformation(getSite().getShell(),
							Messages.FBTypeEditor_CompositeContainsFunctionBlockWithoutType,
							MessageFormat.format(Messages.FBTypeEditor_CheckTypeSaveAble, fb.getName()));
					return false;
				}
			}
		}
		return true;
	}

	private void doSaveInternal(final IProgressMonitor monitor) {

		final WorkspaceModifyOperation operation = new WorkspaceModifyOperation(getTypeEntry().getFile().getParent()) {
			@Override
			protected void execute(final IProgressMonitor monitor)
					throws CoreException, InvocationTargetException, InterruptedException {
				// allow each editor to save back changes before saving to file
				getEditorPages().stream().filter(IEditorPart::isDirty)
						.forEach(editorPart -> SafeRunner.run(() -> editorPart.doSave(monitor)));
				getTypeEntry().save(getType(), monitor);
			}
		};
		try {
			operation.run(monitor);
		} catch (final InvocationTargetException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		} catch (final InterruptedException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
			Thread.currentThread().interrupt();
		}

		getCommandStack().markSaveLocation();
		interfaceChanges = 0;
	}

	@Override
	public void doSaveAs() {
		// TODO implement a save as new type method
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public <T> T getAdapter(final Class<T> adapter) {
		if (adapter == IContentOutlinePage.class) {
			return adapter.cast(getOutlinePage());
		}
		if (adapter == FBType.class) {
			return adapter.cast(getType());
		}
		if (adapter == IGotoMarker.class) {
			return adapter.cast(this);
		}
		return super.getAdapter(adapter);
	}

	protected IContentOutlinePage getOutlinePage() {
		if (null == contentOutline) {
			contentOutline = new MultiPageEditorContentOutlinePage(this, new FBTypeContentOutline(getType()));
		}
		return contentOutline;
	}

	@Override
	public void stackChanged(final CommandStackEvent event) {
		super.stackChanged(event);

		if (isInterfaceChangeCommand(event.getCommand())) {
			switch (event.getDetail()) {
			case CommandStack.POST_EXECUTE, CommandStack.POST_REDO:
				interfaceChanges++;
				break;
			case CommandStack.POST_UNDO:
				interfaceChanges--;
				break;
			default:
				break;
			}
		}
	}

	@Override
	public String getContributorId() {
		return "property.contributor.fb"; //$NON-NLS-1$
	}

	@Override
	public void gotoMarker(final IMarker marker) {
		int i = 0;
		for (final ITypeEditorPage editorPart : getEditorPages()) {
			if (editorPart.isMarkerTarget(marker)) {
				setActivePage(i);
				editorPart.gotoMarker(marker);
				break;
			}
			i++;
		}
	}

	@Override
	public INavigationLocation createEmptyNavigationLocation() {
		return null;
	}

	@Override
	public INavigationLocation createNavigationLocation() {
		return (getType() != null) ? new FBTypeNavigationLocation(this) : null;
	}

	private boolean isInterfaceChangeCommand(final Command cmd) {
		if (cmd instanceof final CompoundCommand compoundCmd) {
			for (final Command childCmd : compoundCmd.getCommands()) {
				if (isInterfaceChangeCommand(childCmd)) {
					// for compound commands it is sufficient to know that at least one of the
					// children is an interface command of our type
					return true;
				}
			}
			return false;
		}

		// we need to check not only for the four commands but also if the element is
		// an interface element of the FBtype and not of any child (e.g., pin of a
		// subapp in a typed subapp)
		final FBType fbType = getType();
		return ((cmd instanceof final CreateInterfaceElementCommand createIFCmd
				&& fbType.getInterfaceList().equals(createIFCmd.getTargetInterfaceList()))
				|| (cmd instanceof final DeleteInterfaceCommand delIFCmd
						&& fbType.getInterfaceList().equals(delIFCmd.getParent()))
				|| (cmd instanceof final AbstractChangeInterfaceElementCommand changeIFCmd
						&& fbType.getInterfaceList().equals(changeIFCmd.getInterfaceElement().eContainer()))
				|| (cmd instanceof final ChangeNameCommand chgNameCmd
						&& fbType.getInterfaceList().equals(chgNameCmd.getElement().eContainer())));
	}

	@Override
	public LibraryElement getEditedElement() {
		return getType();
	}

	@Override
	protected FBType getType() {
		return (FBType) super.getType();
	}

}
