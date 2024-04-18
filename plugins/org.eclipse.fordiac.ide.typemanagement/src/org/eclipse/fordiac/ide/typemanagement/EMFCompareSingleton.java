package org.eclipse.fordiac.ide.typemanagement;

import java.util.Objects;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.compare.EMFCompare;
import org.eclipse.emf.compare.ide.ui.internal.configuration.EMFCompareConfiguration;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;

public class EMFCompareSingleton {

	private static EMFCompare emfCompare;
	private static AdapterFactory adapterFactory;
	private static EMFCompareConfiguration emfCompareConfiguration;

	public static EMFCompare getEmfCompare() {
		if (Objects.isNull(emfCompare)) {
			EMFCompareSingleton.emfCompare = EMFCompare.builder().build();
		}
		return EMFCompareSingleton.emfCompare;
	}

	public static AdapterFactory getAdapterFactory() {
		if (Objects.isNull(adapterFactory)) {
			EMFCompareSingleton.adapterFactory = new ComposedAdapterFactory(
					ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		}
		return EMFCompareSingleton.adapterFactory;
	}

	public static EMFCompareConfiguration getEmfCompareConfiguration() {
		if (Objects.isNull(emfCompareConfiguration)) {
			EMFCompareSingleton.emfCompareConfiguration = new EMFCompareConfiguration(new CompareConfiguration());
		}
		return EMFCompareSingleton.emfCompareConfiguration;
	}

}
