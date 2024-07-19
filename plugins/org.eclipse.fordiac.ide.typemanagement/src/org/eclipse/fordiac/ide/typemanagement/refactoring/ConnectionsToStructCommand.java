package org.eclipse.fordiac.ide.typemanagement.refactoring;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.commands.change.UpdateFBTypeCommand;
import org.eclipse.fordiac.ide.model.commands.create.StructDataConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteConnectionCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.search.types.BlockTypeInstanceSearch;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringCore;
import org.eclipse.ltk.internal.ui.refactoring.RefactoringUIMessages;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

/**
 * Replaces multiple Data Connections between two Function Blocks with a new
 * Struct Connection. Conflicts with branching Connections get resolved by
 * placing Struct (De-)Multiplexers.
 */
public class ConnectionsToStructCommand extends Command {
	private final FBType sourceType;
	private final FBType destinationType;
	private final DataType structType;
	private final String sourceVarName;
	private final String destinationVarName;
	private final Map<String, String> replacableConMap;
	private final boolean conflictResolution;

	Map<FBNetworkElement, FBNetworkElement> structConnectionMap;
	Map<FBNetworkElement, FBNetworkElement> demuxMap;
	Map<FBNetworkElement, FBNetworkElement> muxMap;

	private CompoundCommand editFBsCommand;
	// save
	private CompoundCommand updateCommands;
	private CompoundCommand connectStructCommand;
	private CompoundCommand destinationConnectionEditCommand;
	private CompoundCommand sourceConnectionEditCommand;
	private long sourceModify;
	private long destinationModify;

	ReplaceVarsWithStructChange sourceC2sChange;
	Change sourceUndo;
	ReplaceVarsWithStructChange destinationC2sChange;
	Change destinationUndo;

	public ConnectionsToStructCommand(final FBType sourceType, final FBType destinationType, final DataType structType,
			final String sourceVarName, final String destinationVarName, final Map<String, String> conMap) {
		this(sourceType, destinationType, structType, sourceVarName, destinationVarName, conMap, true);
	}

	public ConnectionsToStructCommand(final FBType sourceType, final FBType destinationType, final DataType structType,
			final String sourceVarName, final String destinationVarName, final Map<String, String> conMap,
			final boolean conflictResolution) {
		this.sourceType = Objects.requireNonNull(sourceType);
		this.destinationType = Objects.requireNonNull(destinationType);
		this.replacableConMap = Objects.requireNonNull(conMap);
		this.structType = Objects.requireNonNull(structType);
		this.sourceVarName = Objects.requireNonNull(sourceVarName);
		this.destinationVarName = Objects.requireNonNull(destinationVarName);
		this.conflictResolution = Objects.requireNonNull(conflictResolution);
	}

	@Override
	public boolean canUndo() {
		return sourceType.getTypeEntry().getLastModificationTimestamp() == sourceModify
				&& destinationType.getTypeEntry().getLastModificationTimestamp() == destinationModify;
	}

	@Override
	public boolean canRedo() {
		return sourceType.getTypeEntry().getLastModificationTimestamp() == sourceModify
				&& destinationType.getTypeEntry().getLastModificationTimestamp() == destinationModify;
	}

	@Override
	public void undo() {
		if (conflictResolution) {
			destinationConnectionEditCommand.undo();
			sourceConnectionEditCommand.undo();
		}
		connectStructCommand.undo();
		updateCommands.undo();
//		editFBsCommand.undo();
		try {
			sourceUndo.perform(new NullProgressMonitor());
			destinationUndo.perform(new NullProgressMonitor());
		} catch (final CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		saveFBs();
	}

	@Override
	public void redo() {
//		editFBsCommand.redo();
		try {
			sourceUndo = sourceC2sChange.perform(new NullProgressMonitor());
			destinationUndo = destinationC2sChange.perform(new NullProgressMonitor());
		} catch (final CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		saveFBs();
		updateCommands.redo();
		connectStructCommand.redo();
		if (conflictResolution) {
			sourceConnectionEditCommand.redo();
			destinationConnectionEditCommand.redo();
		}
	}

	@Override
	public boolean canExecute() {
		// TODO: implement
		return super.canExecute();
	}

	@Override
	public void execute() {
		editFBTypes();
		updateFBs();
		connectStruct();
		if (conflictResolution) {
			conflictResolution();
		}
	}

	private void saveFBs() {
		try {
			sourceType.getTypeEntry().save(sourceType);
			sourceModify = sourceType.getTypeEntry().getLastModificationTimestamp();
			destinationType.getTypeEntry().save(destinationType);
			destinationModify = destinationType.getTypeEntry().getLastModificationTimestamp();
		} catch (final CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void editFBTypes() {
		try {
			sourceC2sChange = new ReplaceVarsWithStructChange(EcoreUtil.getURI(sourceType), FBType.class,
					replacableConMap.keySet(), structType, sourceVarName, sourceType.getInterfaceList(), false, 0);
			destinationC2sChange = new ReplaceVarsWithStructChange(EcoreUtil.getURI(destinationType), FBType.class,
					new HashSet<>(replacableConMap.values()), structType, destinationVarName,
					destinationType.getInterfaceList(), true, 0);

			RefactoringCore.getUndoManager().aboutToPerformChange(sourceC2sChange);
			sourceUndo = sourceC2sChange.perform(new NullProgressMonitor());
			RefactoringCore.getUndoManager().changePerformed(sourceC2sChange, true);
			RefactoringCore.getUndoManager().addUndo(RefactoringUIMessages.RenameResourceHandler_title, sourceUndo);

			RefactoringCore.getUndoManager().aboutToPerformChange(destinationC2sChange);
			destinationUndo = destinationC2sChange.perform(new NullProgressMonitor());
			RefactoringCore.getUndoManager().changePerformed(destinationC2sChange, true);
			RefactoringCore.getUndoManager().addUndo(RefactoringUIMessages.RenameResourceHandler_title,
					destinationUndo);
		} catch (final CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		editFBsCommand = new CompoundCommand();
//		editFBsCommand.add(new ReplaceVarsWithStructCommand(replacableConMap.keySet(), structType, sourceVarName,
//				sourceType.getInterfaceList(), false, 0));
//		editFBsCommand.add(new ReplaceVarsWithStructCommand(new HashSet<>(replacableConMap.values()), structType,
//				destinationVarName, destinationType.getInterfaceList(), true, 0));
		editFBsCommand.execute();
		saveFBs();
	}

	private void updateFBs() {
		// Update all instances of edited FBTypes
		updateCommands = new CompoundCommand();
		getElementsOfType(sourceType).stream().forEach(fbnelem -> {
			final UpdateFBTypeCommand update = new UpdateFBTypeCommand(fbnelem);
			updateCommands.add(update);
		});

		getElementsOfType(destinationType).stream().forEach(fbnelem -> {
			final UpdateFBTypeCommand update = new UpdateFBTypeCommand(fbnelem);
			updateCommands.add(update);
		});
		updateCommands.execute();
	}

	private void connectStruct() {
		connectStructCommand = new CompoundCommand();

		// Create map between correct connected FBs and create MUX
		getElementsOfType(destinationType).stream().forEach(instance -> {
			instance.getInterface().getErrorMarker().stream().flatMap(err -> err.getInputConnections().stream());

			// Collect all correct connections
			final List<Connection> cons = instance.getInterface().getErrorMarker().stream()
					.flatMap(err -> err.getInputConnections().stream())
					.filter(con -> replacableConMap.containsKey(con.getSource().getName())
							&& replacableConMap.get(con.getSource().getName()).equals(con.getDestination().getName())
							&& con.getSourceElement().getType().getName().equals(sourceType.getName()))
					.toList();

			// Check if all connections between 2 instances are correct -> store in map
			if (cons.stream()
					.map((Function<? super Connection, ? extends FBNetworkElement>) Connection::getSourceElement)
					.distinct().count() == 1 && cons.size() == replacableConMap.size()) {

				final FBNetworkElement source = instance.getInterface().getErrorMarker().stream()
						.flatMap(err -> err.getInputConnections().stream())
						.filter(con -> replacableConMap.containsValue(con.getDestination().getName())).findFirst().get()
						.getSourceElement();
				final StructDataConnectionCreateCommand structCon = new StructDataConnectionCreateCommand(
						instance.getFbNetwork());
				structCon.setDestination(instance.getInput(destinationVarName));
				structCon.setSource(source.getOutput(sourceVarName));
				connectStructCommand.add(structCon);
				instance.getInterface().getErrorMarker().stream().flatMap(err -> err.getInputConnections().stream())
						.filter(con -> replacableConMap.containsValue(con.getDestination().getName()))
						.forEach(con -> connectStructCommand.add(new DeleteConnectionCommand(con)));
			}
		});
		connectStructCommand.execute();
	}

	private void conflictResolution() {
		destinationConnectionEditCommand = new CompoundCommand();
		sourceConnectionEditCommand = new CompoundCommand();
		editConnections(sourceType, sourceConnectionEditCommand, true);
		editConnections(destinationType, destinationConnectionEditCommand, false);
	}

	private void editConnections(final FBType type, final CompoundCommand cmd, final boolean isSource) {
		final Stream<ErrorMarkerInterface> errormarkers = getElementsOfType(type).stream()
				.map(FBNetworkElement::getInterface).map(InterfaceList::getErrorMarker).flatMap(EList::stream);
		final Stream<Connection> connections;
		final Function<Connection, String> connectToVar;

		if (isSource) {
			connections = errormarkers.filter(err -> replacableConMap.containsKey(err.getName()))
					.map(ErrorMarkerInterface::getOutputConnections).flatMap(EList::stream);
			connectToVar = t -> t.getSource().getName();
		} else {
			connections = errormarkers.filter(err -> replacableConMap.containsValue(err.getName()))
					.map(ErrorMarkerInterface::getInputConnections).flatMap(EList::stream);
			connectToVar = t -> replacableConMap.entrySet().stream()
					.filter(entry -> entry.getValue().equals(t.getDestination().getName())).findAny().get().getKey();
		}

		connections.forEach(con -> cmd.add(new RepairBrokenConnectionCommand(con, isSource, (StructuredType) structType,
				connectToVar.apply(con))));
		cmd.execute();
	}

	private static List<FBNetworkElement> getElementsOfType(final FBType type) {
		return new BlockTypeInstanceSearch(type.getTypeEntry()).performSearch().stream()
				.map(FBNetworkElement.class::cast).toList();
	}

	public boolean isPossible() {
		final IEditorPart sourceEditor = getEditor(sourceType.getTypeEntry());
		final IEditorPart destinationEditor = getEditor(sourceType.getTypeEntry());
		if (sourceEditor != null && sourceEditor.isDirty()
				|| destinationEditor != null && destinationEditor.isDirty()) {
			return false;
		}
		return true;
	}

	// TODO: copied from AbstractLiveSearchContext
	private static IEditorPart getEditor(final TypeEntry typeEntry) {
		final IWorkbench workbench = PlatformUI.getWorkbench();

		return Display.getDefault().syncCall(() -> {
			final IWorkbenchWindow activeWorkbenchWindow = workbench.getActiveWorkbenchWindow();

			if (activeWorkbenchWindow == null) {
				final IWorkbenchWindow[] workbenchWindows = workbench.getWorkbenchWindows();
				if (workbench.getWorkbenchWindows().length > 0 && workbenchWindows[0].getActivePage() != null) {
					final IWorkbenchPage activePage = workbenchWindows[0].getActivePage();
					for (final IEditorReference ref : activePage.getEditorReferences()) {
						try {
							final IEditorInput editorInput = ref.getEditorInput();
							if (editorInput instanceof final FileEditorInput fileInput
									&& fileInput.getFile().equals(typeEntry.getFile())) {
								return ref.getEditor(true);
							}
						} catch (final PartInitException e) {
							FordiacLogHelper.logWarning("Could not open Editor for type entry in search", e); //$NON-NLS-1$
						}
					}
				}

				return null;
			}

			return activeWorkbenchWindow.getActivePage().findEditor(new FileEditorInput(typeEntry.getFile()));
		});
	}
}
