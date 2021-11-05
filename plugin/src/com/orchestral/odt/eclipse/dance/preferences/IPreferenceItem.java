package com.orchestral.odt.eclipse.dance.preferences;


public interface IPreferenceItem<T> {
	
	String getName();
	
	T getValue();
	
	boolean isEnabled();
	
	IPreferenceItem<?> getParent();
	
	T getDefaultValue();
}
