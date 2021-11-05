package com.orchestral.odt.eclipse.dance.preferences;


public abstract class PreferenceItemAdapter<T> implements IPreferenceItem<T> {
	
	private final String name;
	private final T defaultValue;
	private final IPreferenceItem<?> parent;
	
	public PreferenceItemAdapter() {
		this(null, null);
	}
	
	public PreferenceItemAdapter(String name, T defaultValue) {
		this(name, null, defaultValue);
	}
	
	public PreferenceItemAdapter(String name, IPreferenceItem<?> parent, T defaultValue) {
		this.name = name;
		this.parent = parent;
		this.defaultValue = defaultValue;
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public IPreferenceItem<?> getParent() {
		return parent;
	}

	@Override
	public boolean isEnabled() {
		if (this.parent != null) {
			return this.parent.isEnabled() && getValue() != null;
		}
		return this.getValue() != null;
	}

	@Override
	public T getDefaultValue() {
		return defaultValue;
	}
}
