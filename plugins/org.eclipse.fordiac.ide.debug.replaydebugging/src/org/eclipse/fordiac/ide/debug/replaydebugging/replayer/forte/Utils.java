package org.eclipse.fordiac.ide.debug.replaydebugging.replayer.forte;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.impl.CFBInstanceImpl;
import org.eclipse.fordiac.ide.model.libraryElement.impl.SubAppImpl;

public class Utils {

	private Utils() {
	}

//	private static final Set<String> SIFB_IN_FORTE_NOT_IN_IDE = Set.of("E_CYCLE");

	/**
	 * Collects all ports from the device and its resources, applications, and
	 * networks.
	 *
	 * @param device the device to collect ports from
	 * @return a map where keys are resource names and values are sets of port names
	 *         (qualified names)
	 */
	public static Set<String> collectAllValueHolders(final Resource resource) {
		return transformToNames(collectAllValueHolderElements(resource));
	}

	private static Set<IInterfaceElement> collectAllValueHolderElements(final Resource resource) {
		final Set<IInterfaceElement> result = new HashSet<>();
		collectAllValueHolderElements(resource, result);
		return result;
	}

	private static Set<String> transformToNames(final Set<IInterfaceElement> elements) {
		final Set<String> result = new HashSet<>();
		for (final var element : elements) {
			result.add(getWatchName(element));
		}
		return result;
	}

	private static String getDeviceResourcePrefix(final Resource resource) {
		return resource.getDevice().getName() + "." + resource.getName() + ".";
	}

	private static String getWatchName(final Resource resource, final String interfaceElementQualifiedName) {
		return getWatchName(getDeviceResourcePrefix(resource), interfaceElementQualifiedName);
	}

	private static String getWatchName(final String prefix, final String interfaceElementQualifiedName) {
		final String toAdd = interfaceElementQualifiedName;
		if (toAdd.startsWith(prefix)) {
			return toAdd.substring(prefix.length()); // remove the prefix if present
		}
		return toAdd;
	}

	private static String getWatchName(final Resource resource, final IInterfaceElement interfaceElement) {
		return getWatchName(resource, interfaceElement.getQualifiedName());
	}

	private static String getWatchName(final IInterfaceElement interfaceElement) {
		return getWatchName(interfaceElement.getBlockFBNetworkElement().getResource(), interfaceElement);
	}

	private static void collectAllValueHolderElements(final Resource resource, final Set<IInterfaceElement> result) {
		collectAllValueHolderElements(resource.getFBNetwork(), result);
	}

	private static void collectAllValueHolderElements(final FBNetwork network, final Set<IInterfaceElement> result) {
		if (network == null) {
			return; // no network, nothing to collect
		}

		// collect value holders from the current network
		final TreeIterator<EObject> it = network.eAllContents();
		while (it.hasNext()) {
			final EObject obj = it.next();
			if (obj instanceof final IInterfaceElement varDecl
					&& !(varDecl.getBlockFBNetworkElement() instanceof SubAppImpl)) {
				result.add(varDecl);
			}
		}

		// go deeper into network elements
		for (final FBNetworkElement networkElement : network.getNetworkElements()) {
			if (networkElement instanceof final CFBInstanceImpl composite) {
				collectAllValueHolderElements(composite.loadCFBNetwork(), result); // recursive call to collect ports
																					// from nested networks
			} else if (networkElement instanceof final SubAppImpl subApp) {
				var internalNetwork = subApp.loadSubAppNetwork();
				if (internalNetwork == null) {
					internalNetwork = ((SubApp) subApp.getOpposite()).loadSubAppNetwork();
				}
				collectAllValueHolderElements(internalNetwork, result); // recursive call to collect ports
			}
		}
	}

}