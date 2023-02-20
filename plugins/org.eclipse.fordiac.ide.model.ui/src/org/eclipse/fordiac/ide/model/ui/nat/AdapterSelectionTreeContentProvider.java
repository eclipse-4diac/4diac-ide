package org.eclipse.fordiac.ide.model.ui.nat;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.eclipse.fordiac.ide.model.typelibrary.AdapterTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.ui.Messages;

public class AdapterSelectionTreeContentProvider extends TypeSelectionTreeContentProvider {

	@Override
	protected Object[] createTree(HashMap<String, List<String>> inputElement) {
		final TypeNode adapters = new TypeNode(Messages.DataTypeDropdown_Adapter_Types);
		
		TypeLibrary typeLib = TypeLibraryManager.INSTANCE.getTypeLibrary(getCurrentProject());
		List<AdapterTypeEntry> adapterTypes = typeLib.getAdapterTypesSorted();
		
		inputElement.forEach((key, listValue) -> {
			listValue.forEach(typeName -> {
				Optional<AdapterTypeEntry> type = adapterTypes.stream()
						.filter(dataType -> dataType.getTypeName().equals(typeName))
						.findFirst();
				if (type.isPresent()) {
					final TypeNode newNode = new TypeNode(type.get().getTypeName(), type.get().getType());
					adapters.addChild(newNode);
				}
			});
		});
		
		if (adapters.getChildren().isEmpty()) {
			return adapters.getChildren().toArray();
		} 

		return new TypeNode[] {adapters};
	}
}
