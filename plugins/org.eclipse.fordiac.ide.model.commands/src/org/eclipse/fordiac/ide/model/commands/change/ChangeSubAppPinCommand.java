package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;

public class ChangeSubAppPinCommand extends AbstractUpdateFBNElementCommand {
	private final StructuredType type;

	public ChangeSubAppPinCommand(final FBNetworkElement oldElement, final StructuredType type) {
		super(oldElement);
		this.type = type;

	}

	@Override
	protected void createNewFB() {
		newElement = EcoreUtil.copy(oldElement);
		if (newElement instanceof final SubApp subApp) {
			final EList<IInterfaceElement> otherIn = subApp.getInterface().getAllInterfaceElements();
			otherIn.stream().filter(in -> in.getName().equals(type.getName())).forEach(s -> {
				s.setType(type);
			});
		}

	}
}
