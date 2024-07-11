package org.eclipse.fordiac.ide.typemanagement.refactoring;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.fordiac.ide.model.commands.change.ChangeStructCommand;
import org.eclipse.fordiac.ide.model.commands.change.ReconnectDataConnectionCommand;
import org.eclipse.fordiac.ide.model.commands.change.UpdateFBTypeCommand;
import org.eclipse.fordiac.ide.model.commands.create.FBCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.StructDataConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteConnectionCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.search.types.BlockTypeInstanceSearch;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
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
	private CompoundCommand muxCreateCommand;
	private CompoundCommand destinationConnectionEditCommand;
	private CompoundCommand sourceConnectionEditCommand;
	private long sourceModify;
	private long destinationModify;

	public ConnectionsToStructCommand(final FBType sourceType, final FBType destinationType, final DataType structType,
			final String sourceVarName, final String destinationVarName, final Map<String, String> conMap) {
		this(sourceType, destinationType, structType, sourceVarName, destinationVarName, conMap, true);
	}

	public ConnectionsToStructCommand(final FBType sourceType, final FBType destinationType, final DataType structType,
			final String sourceVarName, final String destinationVarName, final Map<String, String> conMap,
			final boolean conflictResolution) {
		this.sourceType = sourceType;
		this.destinationType = destinationType;
		this.replacableConMap = conMap;
		this.structType = structType;
		this.sourceVarName = sourceVarName;
		this.destinationVarName = destinationVarName;
		this.conflictResolution = conflictResolution;
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
		sourceConnectionEditCommand.undo();
		destinationConnectionEditCommand.undo();
		muxCreateCommand.undo();
		updateCommands.undo();
		editFBsCommand.undo();
		saveFBs();
	}

	@Override
	public void redo() {
		editFBsCommand.redo();
		saveFBs();
		updateCommands.redo();
		muxCreateCommand.redo();
		destinationConnectionEditCommand.redo();
		sourceConnectionEditCommand.redo();
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
		createMUX();
		reconnectCon();
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

		editFBsCommand = new CompoundCommand();
		editFBsCommand.add(new ReplaceVarsWithStructCommand(replacableConMap.keySet(), structType, sourceVarName,
				sourceType.getInterfaceList(), false, 0));
		editFBsCommand.add(new ReplaceVarsWithStructCommand(new HashSet<>(replacableConMap.values()), structType,
				destinationVarName, destinationType.getInterfaceList(), true, 0));
		editFBsCommand.execute();
		saveFBs();
	}

	private void updateFBs() {
		// Update all instances of edited FBTypes
		updateCommands = new CompoundCommand();
		getElementsOfType(sourceType).stream().forEach(fbnelem -> updateCommands.add(new UpdateFBTypeCommand(fbnelem)));
		getElementsOfType(destinationType).stream()
				.forEach(fbnelem -> updateCommands.add(new UpdateFBTypeCommand(fbnelem)));
		updateCommands.execute();
	}

	private void createMUX() {
		muxCreateCommand = new CompoundCommand();

		structConnectionMap = new HashMap<>();
		demuxMap = new HashMap<>();
		muxMap = new HashMap<>();

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
					.distinct().count() == 1 && cons.stream().count() == replacableConMap.size()) {
				structConnectionMap.put(instance,
						instance.getInterface().getErrorMarker().stream()
								.flatMap(err -> err.getInputConnections().stream())
								.filter(con -> replacableConMap.containsValue(con.getDestination().getName()))
								.findFirst().get().getSourceElement());
				// Create MUX if selected in wizard
			} else if (conflictResolution) {
				final Position pos = LibraryElementFactory.eINSTANCE.createPosition();
				pos.setX((int) instance.getPosition().getX() - 1000);
				pos.setY((int) instance.getPosition().getY());
				final FBCreateCommand muxcreate = new FBCreateCommand(
						instance.getTypeLibrary().getFBTypeEntry("STRUCT_MUX"), instance.getFbNetwork(), pos);
				muxCreateCommand.add(muxcreate);
				muxcreate.execute();
				final ChangeStructCommand changeStruct = new ChangeStructCommand(
						(StructManipulator) muxcreate.getElement(), structType);
				muxCreateCommand.add(changeStruct);
				changeStruct.execute();
				muxMap.put(instance, changeStruct.getNewElement());
			}
		});

		// Create DEMUX if selected in wizard if any connections can not be replaced
		// with Struct
		if (conflictResolution) {
			getElementsOfType(sourceType).stream().forEach(instance -> {
				if (instance.getInterface().getErrorMarker().stream()
						.flatMap(err -> err.getOutputConnections().stream())
						.filter(con -> !(structConnectionMap.containsKey(con.getDestinationElement())
								&& structConnectionMap.get(con.getDestinationElement()).equals(instance)
								&& replacableConMap.containsKey(con.getSource().getName()) && replacableConMap
										.get(con.getSource().getName()).equals(con.getDestination().getName())))
						.count() != 0) {
					final Position pos = LibraryElementFactory.eINSTANCE.createPosition();
					pos.setX((int) instance.getPosition().getX() + 1000);
					pos.setY((int) instance.getPosition().getY());
					final FBCreateCommand muxcreate = new FBCreateCommand(
							instance.getTypeLibrary().getFBTypeEntry("STRUCT_DEMUX"), instance.getFbNetwork(), pos);
					muxCreateCommand.add(muxcreate);
					muxcreate.execute();
					final ChangeStructCommand changeStruct = new ChangeStructCommand(
							(StructManipulator) muxcreate.getElement(), structType);
					muxCreateCommand.add(changeStruct);
					changeStruct.execute();
					demuxMap.put(instance, changeStruct.getNewElement());
				}
			});
		}
	}

	private void reconnectCon() {
		destinationConnectionEditCommand = new CompoundCommand();
		sourceConnectionEditCommand = new CompoundCommand();

		// Edit destination connections
		getElementsOfType(destinationType).stream().forEach(instance -> {
			if (structConnectionMap.containsKey(instance)) {
				final StructDataConnectionCreateCommand structCon = new StructDataConnectionCreateCommand(
						instance.getFbNetwork());
				// TODO: getInput if input is altered during creation (due to vars with same
				// name)
				structCon.setDestination(instance.getInput(destinationVarName));
				structCon.setSource(structConnectionMap.get(instance).getOutput(sourceVarName));
				destinationConnectionEditCommand.add(structCon);
				instance.getInterface().getErrorMarker().stream().flatMap(err -> err.getInputConnections().stream())
						.filter(con -> replacableConMap.containsValue(con.getDestination().getName()))
						.forEach(con -> destinationConnectionEditCommand.add(new DeleteConnectionCommand(con)));
				// Connect MUX if selected in Wizard
			} else if (conflictResolution) {

				final StructDataConnectionCreateCommand structCon = new StructDataConnectionCreateCommand(
						instance.getFbNetwork());
				structCon.setDestination(instance.getInput(destinationVarName));
				structCon.setSource(muxMap.get(instance).getOutput("OUT"));
				destinationConnectionEditCommand.add(structCon);
				instance.getInterface().getErrorMarker().stream().flatMap(err -> err.getInputConnections().stream())
						.filter(con -> replacableConMap.containsValue(con.getDestination().getName())).forEach(con -> {
							final String var = replacableConMap.entrySet().stream()
									.filter(entry -> entry.getValue().equals(con.getDestination().getName())).findAny()
									.get().getKey();
							destinationConnectionEditCommand.add(new ReconnectDataConnectionCommand(con, false,
									muxMap.get(instance).getInput(var), instance.getFbNetwork()));
						});
			}
		});
		destinationConnectionEditCommand.execute();

		// Edit source connections
		// Connect DEMUX if selected in Wizard
		if (conflictResolution) {
			getElementsOfType(sourceType).stream().forEach(instance -> {
				if (instance.getInterface().getErrorMarker().stream()
						.flatMap(err -> err.getOutputConnections().stream())
						.filter(con -> replacableConMap.containsKey(con.getSource().getName())).count() != 0) {
					final StructDataConnectionCreateCommand structCon = new StructDataConnectionCreateCommand(
							instance.getFbNetwork());
					structCon.setDestination(demuxMap.get(instance).getInput("IN"));
					structCon.setSource(instance.getOutput(sourceVarName));
					// sourceConnectionEditCommand.add(structCon);
					instance.getInterface().getErrorMarker().stream()
							.flatMap(err -> err.getOutputConnections().stream())
							.filter(con -> replacableConMap.containsKey(con.getSource().getName()))
							.forEach(con -> new RepairBrokenConnectionCommand(con, (StructuredType) structType,
									con.getSource().getName()).execute());
//							.forEach(con -> sourceConnectionEditCommand.add(new ReconnectDataConnectionCommand(con,
//									true, demuxMap.get(instance).getOutput(con.getSource().getName()),
//									instance.getFbNetwork())));
				}
			});
		}
		sourceConnectionEditCommand.execute();
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
