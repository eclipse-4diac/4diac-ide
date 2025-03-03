/*******************************************************************************
 * Copyright (c) 2012, 2023 itemis AG (http://www.itemis.eu) and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.preferences;

import java.io.IOException;
import java.util.Objects;

import org.eclipse.core.commands.common.EventManager;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.NodeChangeEvent;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.jface.preference.IPersistentPreferenceStore;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.util.SafeRunnable;
import org.osgi.service.prefs.BackingStoreException;

/**
 * Mainly copied from {@link org.eclipse.ui.preferences.ScopedPreferenceStore}.
 * It fixes the memory leak described in
 * <a href="https://bugs.eclipse.org/bugs/show_bug.cgi?id=239033">these</a>
 * <a href="https://bugs.eclipse.org/bugs/show_bug.cgi?id=362199">bugs</a>.
 *
 * The FixedScopedPreferenceStore is an IPreferenceStore that uses the scopes
 * provided in org.eclipse.core.runtime.preferences.
 * <p>
 * A {@link FixedScopedPreferenceStore} does the lookup of a preference based on
 * it's search scopes and sets the value of the preference based on its store
 * scope.
 * </p>
 * <p>
 * The default scope is always included in the search scopes when searching for
 * preference values.
 * </p>
 *
 * copied class
 * org.eclipse.xtext.ui.editor.preferences.FixedScopedPreferenceStore
 *
 * @see org.eclipse.ui.preferences.ScopedPreferenceStore
 * @since 2.3
 */
public class FixedScopedPreferenceStore extends EventManager implements IPersistentPreferenceStore {

	/**
	 * The storeContext is the context where values will stored with the setValue
	 * methods. If there are no searchContexts this will be the search context.
	 * (along with the "default" context)
	 */
	private final IScopeContext storeContext;

	/**
	 * The searchContext is the array of contexts that will be used by the get
	 * methods for searching for values.
	 */
	private IScopeContext[] searchContexts;

	/**
	 * A boolean to indicate the property changes should not be propagated.
	 */
	protected boolean silentRunning = false;

	/**
	 * The listener on the IEclipsePreferences. This is used to forward updates to
	 * the property change listeners on the preference store.
	 */
	IEclipsePreferences.IPreferenceChangeListener preferencesListener;

	/**
	 * The listener on the IEclipsePreferences that the {@link #preferencesListener}
	 * is registered to new preference nodes in the underlying store.
	 */
	private IEclipsePreferences.INodeChangeListener nodeChangeListener;

	/**
	 * The default context is the context where getDefault and setDefault methods
	 * will search. This context is also used in the search.
	 */
	private final IScopeContext defaultContext = DefaultScope.INSTANCE;

	/**
	 * The nodeQualifer is the string used to look up the node in the contexts.
	 */
	String nodeQualifier;

	/**
	 * The defaultQualifier is the string used to look up the default node.
	 */
	String defaultQualifier;

	/**
	 * Boolean value indicating whether or not this store has changes to be saved.
	 */
	private boolean dirty;

	/**
	 * Create a new instance of the receiver. Store the values in context in the
	 * node looked up by qualifier.
	 *
	 * @param context              the scope to store to
	 * @param qualifier            the qualifier used to look up the preference node
	 * @param defaultQualifierPath the qualifier used when looking up the defaults
	 */
	public FixedScopedPreferenceStore(final IScopeContext context, final String qualifier,
			final String defaultQualifierPath) {
		this(context, qualifier);
		this.defaultQualifier = defaultQualifierPath;
	}

	/**
	 * Create a new instance of the receiver. Store the values in context in the
	 * node looked up by qualifier.
	 *
	 * @param context   the scope to store to
	 * @param qualifier the qualifer used to look up the preference node
	 */
	public FixedScopedPreferenceStore(final IScopeContext context, final String qualifier) {
		storeContext = context;
		this.nodeQualifier = qualifier;
		this.defaultQualifier = qualifier;
	}

	// Fix is here and in disposeNodeChangeListener. Look for callees accordingly.
	/**
	 * Initialize the node change listener.
	 */
	private void initializeNodeChangeListener() {
		if (nodeChangeListener == null) {
			nodeChangeListener = new IEclipsePreferences.INodeChangeListener() {
				/*
				 * (non-Javadoc)
				 *
				 * @see
				 * org.eclipse.core.runtime.preferences.IEclipsePreferences.INodeChangeListener#
				 * added(org.eclipse.core.runtime.preferences.IEclipsePreferences.
				 * NodeChangeEvent)
				 */
				@Override
				public void added(final NodeChangeEvent event) {
					if (nodeQualifier.equals(event.getChild().name()) && isListenerAttached()) {
						getStorePreferences().addPreferenceChangeListener(preferencesListener);
					}
				}

				/*
				 * (non-Javadoc)
				 *
				 * @see
				 * org.eclipse.core.runtime.preferences.IEclipsePreferences.INodeChangeListener#
				 * removed(org.eclipse.core.runtime.preferences.IEclipsePreferences.
				 * NodeChangeEvent)
				 */
				@Override
				public void removed(final NodeChangeEvent event) {
					// Do nothing as there are no events from removed node
				}
			};
			((IEclipsePreferences) getStorePreferences().parent()).addNodeChangeListener(nodeChangeListener);
		}
	}

	/**
	 * Dispose the node change listener.
	 */
	private void disposeNodeChangeListener() {
		if (nodeChangeListener != null) {
			final IEclipsePreferences preferences = getStorePreferences();
			if (preferences == null) {
				return;
			}
			final IEclipsePreferences parent = (IEclipsePreferences) preferences.parent();
			if (parent == null) {
				return;
			}
			parent.removeNodeChangeListener(nodeChangeListener);
			nodeChangeListener = null;
		}
	}

	/**
	 * Initialize the preferences listener.
	 */
	private void initializePreferencesListener() {
		if (preferencesListener == null) {
			preferencesListener = event -> {

				if (silentRunning) {
					return;
				}

				Object oldValue = event.getOldValue();
				Object newValue = event.getNewValue();
				final String key = event.getKey();
				if (newValue == null) {
					newValue = getDefault(key, oldValue);
				} else if (oldValue == null) {
					oldValue = getDefault(key, newValue);
				}
				firePropertyChangeEvent(event.getKey(), oldValue, newValue);
			};
			getStorePreferences().addPreferenceChangeListener(preferencesListener);
		}

	}

	/**
	 * Does its best at determining the default value for the given key. Checks the
	 * given object's type and then looks in the list of defaults to see if a value
	 * exists. If not or if there is a problem converting the value, the default
	 * default value for that type is returned.
	 *
	 * @param key the key to search
	 * @param obj the object who default we are looking for
	 * @return Object or <code>null</code>
	 */
	Object getDefault(final String key, final Object obj) {
		final IEclipsePreferences defaults = getDefaultPreferences();
		if (obj instanceof String) {
			return defaults.get(key, STRING_DEFAULT_DEFAULT);
		}
		if (obj instanceof Integer) {
			return Integer.valueOf(defaults.getInt(key, INT_DEFAULT_DEFAULT));
		}
		if (obj instanceof Double) {
			return Double.valueOf(defaults.getDouble(key, DOUBLE_DEFAULT_DEFAULT));
		} else if (obj instanceof Float) {
			return Float.valueOf(defaults.getFloat(key, FLOAT_DEFAULT_DEFAULT));
		} else if (obj instanceof Long) {
			return Long.valueOf(defaults.getLong(key, LONG_DEFAULT_DEFAULT));
		} else if (obj instanceof Boolean) {
			return defaults.getBoolean(key, BOOLEAN_DEFAULT_DEFAULT) ? Boolean.TRUE : Boolean.FALSE;
		} else {
			return null;
		}
	}

	/**
	 * Return the IEclipsePreferences node associated with this store.
	 *
	 * @return the preference node for this store
	 */
	IEclipsePreferences getStorePreferences() {
		return storeContext.getNode(nodeQualifier);
	}

	/**
	 * Return the default IEclipsePreferences for this store.
	 *
	 * @return this store's default preference node
	 */
	private IEclipsePreferences getDefaultPreferences() {
		return defaultContext.getNode(defaultQualifier);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.jface.preference.IPreferenceStore#addPropertyChangeListener(org.
	 * eclipse.jface.util.IPropertyChangeListener)
	 */
	@Override
	public void addPropertyChangeListener(final IPropertyChangeListener listener) {
		// Create the preference listeners if they do not exist
		initializeNodeChangeListener();
		initializePreferencesListener();
		addListenerObject(listener);
	}

	/**
	 * Return the preference path to search preferences on. This is the list of
	 * preference nodes based on the scope contexts for this store. If there are no
	 * search contexts set, then return this store's context.
	 * <p>
	 * Whether or not the default context should be included in the resulting list
	 * is specified by the <code>includeDefault</code> parameter.
	 * </p>
	 *
	 * @param includeDefault <code>true</code> if the default context should be
	 *                       included and <code>false</code> otherwise
	 * @return IEclipsePreferences[]
	 * @since 3.4 public, was added in 3.1 as private method
	 */
	public IEclipsePreferences[] getPreferenceNodes(final boolean includeDefault) {
		// if the user didn't specify a search order, then return the scope that
		// this store was created on. (and optionally the default)
		if (searchContexts == null) {
			if (includeDefault) {
				return new IEclipsePreferences[] { getStorePreferences(), getDefaultPreferences() };
			}
			return new IEclipsePreferences[] { getStorePreferences() };
		}
		// otherwise the user specified a search order so return the appropriate
		// nodes based on it
		int length = searchContexts.length;
		if (includeDefault) {
			length++;
		}
		final IEclipsePreferences[] preferences = new IEclipsePreferences[length];
		for (int i = 0; i < searchContexts.length; i++) {
			preferences[i] = searchContexts[i].getNode(nodeQualifier);
		}
		if (includeDefault) {
			preferences[length - 1] = getDefaultPreferences();
		}
		return preferences;
	}

	/**
	 * Set the search contexts to scopes. When searching for a value the seach will
	 * be done in the order of scope contexts and will not search the storeContext
	 * unless it is in this list.
	 * <p>
	 * If the given list is <code>null</code>, then clear this store's search
	 * contexts. This means that only this store's scope context and default scope
	 * will be used during preference value searching.
	 * </p>
	 * <p>
	 * The defaultContext will be added to the end of this list automatically and
	 * <em>MUST NOT</em> be included by the user.
	 * </p>
	 *
	 * @param scopes a list of scope contexts to use when searching, or
	 *               <code>null</code>
	 */
	public void setSearchContexts(final IScopeContext[] scopes) {
		if (scopes == null) {
			return;
		}
		this.searchContexts = scopes.clone();

		// Assert that the default was not included (we automatically add it to
		// the end)
		for (final IScopeContext scope : scopes) {
			if (scope.equals(defaultContext)) {
				Assert.isTrue(false, org.eclipse.ui.internal.WorkbenchMessages.ScopedPreferenceStore_DefaultAddedError);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.preference.IPreferenceStore#contains(java.lang.String)
	 */
	@Override
	public boolean contains(final String name) {
		if (name == null) {
			return false;
		}
		return (Platform.getPreferencesService().get(name, null, getPreferenceNodes(true))) != null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.jface.preference.IPreferenceStore#firePropertyChangeEvent(java.
	 * lang.String, java.lang.Object, java.lang.Object)
	 */
	@Override
	public void firePropertyChangeEvent(final String name, final Object oldValue, final Object newValue) {
		// important: create intermediate array to protect against listeners
		// being added/removed during the notification
		final Object[] list = getListeners();
		if (list.length == 0) {
			return;
		}
		final PropertyChangeEvent event = new PropertyChangeEvent(this, name, oldValue, newValue);
		for (final Object element : list) {
			final IPropertyChangeListener listener = (IPropertyChangeListener) element;
			SafeRunner.run(new SafeRunnable(JFaceResources.getString("PreferenceStore.changeError")) { //$NON-NLS-1$
				@Override
				public void run() {
					listener.propertyChange(event);
				}
			});
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.jface.preference.IPreferenceStore#getBoolean(java.lang.String)
	 */
	@Override
	public boolean getBoolean(final String name) {
		final String value = internalGet(name);
		return value == null ? BOOLEAN_DEFAULT_DEFAULT : Boolean.valueOf(value).booleanValue();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.jface.preference.IPreferenceStore#getDefaultBoolean(java.lang.
	 * String)
	 */
	@Override
	public boolean getDefaultBoolean(final String name) {
		return getDefaultPreferences().getBoolean(name, BOOLEAN_DEFAULT_DEFAULT);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.jface.preference.IPreferenceStore#getDefaultDouble(java.lang.
	 * String)
	 */
	@Override
	public double getDefaultDouble(final String name) {
		return getDefaultPreferences().getDouble(name, DOUBLE_DEFAULT_DEFAULT);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.preference.IPreferenceStore#getDefaultFloat(java.lang.
	 * String)
	 */
	@Override
	public float getDefaultFloat(final String name) {
		return getDefaultPreferences().getFloat(name, FLOAT_DEFAULT_DEFAULT);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.jface.preference.IPreferenceStore#getDefaultInt(java.lang.String)
	 */
	@Override
	public int getDefaultInt(final String name) {
		return getDefaultPreferences().getInt(name, INT_DEFAULT_DEFAULT);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.preference.IPreferenceStore#getDefaultLong(java.lang.
	 * String)
	 */
	@Override
	public long getDefaultLong(final String name) {
		return getDefaultPreferences().getLong(name, LONG_DEFAULT_DEFAULT);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.jface.preference.IPreferenceStore#getDefaultString(java.lang.
	 * String)
	 */
	@Override
	public String getDefaultString(final String name) {
		return getDefaultPreferences().get(name, STRING_DEFAULT_DEFAULT);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.jface.preference.IPreferenceStore#getDouble(java.lang.String)
	 */
	@Override
	public double getDouble(final String name) {
		final String value = internalGet(name);
		if (value == null) {
			return DOUBLE_DEFAULT_DEFAULT;
		}
		try {
			return Double.parseDouble(value);
		} catch (final NumberFormatException e) {
			return DOUBLE_DEFAULT_DEFAULT;
		}
	}

	/**
	 * Return the string value for the specified key. Look in the nodes which are
	 * specified by this object's list of search scopes. If the value does not exist
	 * then return <code>null</code>.
	 *
	 * @param key the key to search with
	 * @return String or <code>null</code> if the value does not exist.
	 */
	private String internalGet(final String key) {
		return Platform.getPreferencesService().get(key, null, getPreferenceNodes(true));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.preference.IPreferenceStore#getFloat(java.lang.String)
	 */
	@Override
	public float getFloat(final String name) {
		final String value = internalGet(name);
		if (value == null) {
			return FLOAT_DEFAULT_DEFAULT;
		}
		try {
			return Float.parseFloat(value);
		} catch (final NumberFormatException e) {
			return FLOAT_DEFAULT_DEFAULT;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.preference.IPreferenceStore#getInt(java.lang.String)
	 */
	@Override
	public int getInt(final String name) {
		final String value = internalGet(name);
		if (value == null) {
			return INT_DEFAULT_DEFAULT;
		}
		try {
			return Integer.parseInt(value);
		} catch (final NumberFormatException e) {
			return INT_DEFAULT_DEFAULT;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.preference.IPreferenceStore#getLong(java.lang.String)
	 */
	@Override
	public long getLong(final String name) {
		final String value = internalGet(name);
		if (value == null) {
			return LONG_DEFAULT_DEFAULT;
		}
		try {
			return Long.parseLong(value);
		} catch (final NumberFormatException e) {
			return LONG_DEFAULT_DEFAULT;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.jface.preference.IPreferenceStore#getString(java.lang.String)
	 */
	@Override
	public String getString(final String name) {
		final String value = internalGet(name);
		return value == null ? STRING_DEFAULT_DEFAULT : value;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.jface.preference.IPreferenceStore#isDefault(java.lang.String)
	 */
	@Override
	public boolean isDefault(final String name) {
		if (name == null) {
			return false;
		}
		return (Platform.getPreferencesService().get(name, null, getPreferenceNodes(false))) == null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.preference.IPreferenceStore#needsSaving()
	 */
	@Override
	public boolean needsSaving() {
		return dirty;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.preference.IPreferenceStore#putValue(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void putValue(final String name, final String value) {
		try {
			// Do not notify listeners
			silentRunning = true;
			getStorePreferences().put(name, value);
		} finally {
			// Be sure that an exception does not stop property updates
			silentRunning = false;
			dirty = true;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.jface.preference.IPreferenceStore#removePropertyChangeListener(
	 * org.eclipse.jface.util.IPropertyChangeListener)
	 */
	@Override
	public void removePropertyChangeListener(final IPropertyChangeListener listener) {
		removeListenerObject(listener);
		if (!isListenerAttached()) {
			disposePreferenceStoreListener();
			disposeNodeChangeListener();
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.jface.preference.IPreferenceStore#setDefault(java.lang.String,
	 * double)
	 */
	@Override
	public void setDefault(final String name, final double value) {
		getDefaultPreferences().putDouble(name, value);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.jface.preference.IPreferenceStore#setDefault(java.lang.String,
	 * float)
	 */
	@Override
	public void setDefault(final String name, final float value) {
		getDefaultPreferences().putFloat(name, value);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.jface.preference.IPreferenceStore#setDefault(java.lang.String,
	 * int)
	 */
	@Override
	public void setDefault(final String name, final int value) {
		getDefaultPreferences().putInt(name, value);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.jface.preference.IPreferenceStore#setDefault(java.lang.String,
	 * long)
	 */
	@Override
	public void setDefault(final String name, final long value) {
		getDefaultPreferences().putLong(name, value);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.jface.preference.IPreferenceStore#setDefault(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void setDefault(final String name, final String defaultObject) {
		getDefaultPreferences().put(name, defaultObject);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.jface.preference.IPreferenceStore#setDefault(java.lang.String,
	 * boolean)
	 */
	@Override
	public void setDefault(final String name, final boolean value) {
		getDefaultPreferences().putBoolean(name, value);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.jface.preference.IPreferenceStore#setToDefault(java.lang.String)
	 */
	@Override
	public void setToDefault(final String name) {

		final String oldValue = getString(name);
		final String defaultValue = getDefaultString(name);
		try {
			silentRunning = true;// Turn off updates from the store
			// removing a non-existing preference is a no-op so call the Core
			// API directly
			getStorePreferences().remove(name);
			if (!Objects.equals(oldValue, defaultValue)) {
				dirty = true;
				firePropertyChangeEvent(name, oldValue, defaultValue);
			}

		} finally {
			silentRunning = false;// Restart listening to preferences
		}

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.preference.IPreferenceStore#setValue(java.lang.String,
	 * double)
	 */
	@Override
	public void setValue(final String name, final double value) {
		final double oldValue = getDouble(name);
		if (oldValue == value) {
			return;
		}
		try {
			silentRunning = true;// Turn off updates from the store
			if (getDefaultDouble(name) == value) {
				getStorePreferences().remove(name);
			} else {
				getStorePreferences().putDouble(name, value);
			}
			dirty = true;
			firePropertyChangeEvent(name, Double.valueOf(oldValue), Double.valueOf(value));
		} finally {
			silentRunning = false;// Restart listening to preferences
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.preference.IPreferenceStore#setValue(java.lang.String,
	 * float)
	 */
	@Override
	public void setValue(final String name, final float value) {
		final float oldValue = getFloat(name);
		if (oldValue == value) {
			return;
		}
		try {
			silentRunning = true;// Turn off updates from the store
			if (getDefaultFloat(name) == value) {
				getStorePreferences().remove(name);
			} else {
				getStorePreferences().putFloat(name, value);
			}
			dirty = true;
			firePropertyChangeEvent(name, Float.valueOf(oldValue), Float.valueOf(value));
		} finally {
			silentRunning = false;// Restart listening to preferences
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.preference.IPreferenceStore#setValue(java.lang.String,
	 * int)
	 */
	@Override
	public void setValue(final String name, final int value) {
		final int oldValue = getInt(name);
		if (oldValue == value) {
			return;
		}
		try {
			silentRunning = true;// Turn off updates from the store
			if (getDefaultInt(name) == value) {
				getStorePreferences().remove(name);
			} else {
				getStorePreferences().putInt(name, value);
			}
			dirty = true;
			firePropertyChangeEvent(name, Integer.valueOf(oldValue), Integer.valueOf(value));
		} finally {
			silentRunning = false;// Restart listening to preferences
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.preference.IPreferenceStore#setValue(java.lang.String,
	 * long)
	 */
	@Override
	public void setValue(final String name, final long value) {
		final long oldValue = getLong(name);
		if (oldValue == value) {
			return;
		}
		try {
			silentRunning = true;// Turn off updates from the store
			if (getDefaultLong(name) == value) {
				getStorePreferences().remove(name);
			} else {
				getStorePreferences().putLong(name, value);
			}
			dirty = true;
			firePropertyChangeEvent(name, Long.valueOf(oldValue), Long.valueOf(value));
		} finally {
			silentRunning = false;// Restart listening to preferences
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.preference.IPreferenceStore#setValue(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void setValue(final String name, final String value) {
		// Do not turn on silent running here as Strings are propagated
		if (getDefaultString(name).equals(value)) {
			getStorePreferences().remove(name);
		} else {
			getStorePreferences().put(name, value);
		}
		dirty = true;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.preference.IPreferenceStore#setValue(java.lang.String,
	 * boolean)
	 */
	@Override
	public void setValue(final String name, final boolean value) {
		final boolean oldValue = getBoolean(name);
		if (oldValue == value) {
			return;
		}
		try {
			silentRunning = true;// Turn off updates from the store
			if (getDefaultBoolean(name) == value) {
				getStorePreferences().remove(name);
			} else {
				getStorePreferences().putBoolean(name, value);
			}
			dirty = true;
			firePropertyChangeEvent(name, oldValue ? Boolean.TRUE : Boolean.FALSE,
					value ? Boolean.TRUE : Boolean.FALSE);
		} finally {
			silentRunning = false;// Restart listening to preferences
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.preference.IPersistentPreferenceStore#save()
	 */
	@Override
	public void save() throws IOException {
		try {
			getStorePreferences().flush();
			dirty = false;
		} catch (final BackingStoreException e) {
			throw new IOException(e.getMessage());
		}

	}

	/**
	 * Dispose the preference store listener.
	 */
	private void disposePreferenceStoreListener() {

		final IEclipsePreferences root = (IEclipsePreferences) Platform.getPreferencesService().getRootNode()
				.node(Plugin.PLUGIN_PREFERENCE_SCOPE);
		try {
			if (!(root.nodeExists(nodeQualifier))) {
				return;
			}
		} catch (final BackingStoreException e) {
			return;// No need to report here as the node won't have the
			// listener
		}

		final IEclipsePreferences preferences = getStorePreferences();
		if (preferences == null) {
			return;
		}
		if (preferencesListener != null) {
			preferences.removePreferenceChangeListener(preferencesListener);
			preferencesListener = null;
		}
	}

}
