package org.eclipse.fordiac.ide.model.dataexport;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.stream.XMLStreamException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.ResourceType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public class ResourceTypeExporter extends AbstractTypeExporter {

	private final ResourceType resourceType;

	public ResourceTypeExporter(final ResourceType type) {
		super(type);
		this.resourceType = type;
	}

	@Override
	protected String getRootTag() {
		return LibraryElementTags.RESOURCETYPE_ELEMENT;
	}

	@Override
	protected void createXMLEntries() throws XMLStreamException {
		getWriter().writeDTD(LINE_END
				+ "<!DOCTYPE ResourceType SYSTEM \"http://www.holobloc.com/xml/LibraryElement.dtd\">" + LINE_END);
		super.createXMLEntries();
	}

	@Override
	protected void createTypeSpecificXMLEntries() throws XMLStreamException {

		for (final VarDeclaration varDecl : resourceType.getVarDeclaration()) {
			addVarDeclaration(varDecl);
		}

		exportFBNetwork();

		final CompilerInfo compilerInfo = resourceType.getCompilerInfo();
		if (compilerInfo != null) {
			addCompilerInfo(compilerInfo);
		}
	}

	private void exportFBNetwork() throws XMLStreamException {
		final FBNetwork network = resourceType.getFBNetwork();
		if (network != null && !network.getNetworkElements().isEmpty()) {
			new FBNetworkExporter(this).createFBNetworkElement(network);
		}
	}

	public void export(final IFile file, final IProgressMonitor monitor) throws CoreException {
		final IProgressMonitor progressMonitor = monitor != null ? monitor : new NullProgressMonitor();
		try (InputStream content = getFileContent()) {
			if (file.exists()) {
				file.setContents(content, true, true, progressMonitor);
			} else {
				file.create(content, true, progressMonitor);
			}
		} catch (final IOException e) {
			FordiacLogHelper.logError("Error during resource type export", e);
			throw new CoreException(Status.error("Error exporting ResourceType", e));
		}
	}

	@Override
	public ResourceType getType() {
		return resourceType;
	}
}