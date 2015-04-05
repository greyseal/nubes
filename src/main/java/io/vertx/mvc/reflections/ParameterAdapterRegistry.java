package io.vertx.mvc.reflections;

import io.vertx.core.MultiMap;

import java.util.HashMap;
import java.util.Map;

public class ParameterAdapterRegistry {
	
	private Map<Class<?>, ParameterAdapter<?>> adapters;
	private ParameterAdapter<Object> defaultParameterAdapter;
	
	public ParameterAdapterRegistry() {
		adapters = new HashMap<Class<?>, ParameterAdapter<?>>();
	}
	
	public ParameterAdapterRegistry(ParameterAdapter<Object> defaultParameterAdapter) {
		this();
		this.defaultParameterAdapter = defaultParameterAdapter;
	}
	
	public<T> void registerAdapter(Class<T> parameterClass, ParameterAdapter<T> adapter) {
		adapters.put(parameterClass, adapter);
	}
	
	@SuppressWarnings("unchecked")
	public<T> ParameterAdapter<T> getAdapter(Class<T> parameterClass) {
		return (ParameterAdapter<T>) adapters.get(parameterClass);
	}
	
	public ParameterAdapter<Object> getDefaultParameterAdapter() {
		return defaultParameterAdapter;
	}
	
	@SuppressWarnings("unchecked")
	public<T> T adaptParam(String value, Class<T> parameterClass) throws Exception {
		ParameterAdapter<T> adapter = getAdapter(parameterClass);
		if (adapter != null) {
			return adapter.adaptParam(value, parameterClass);
		} else if (defaultParameterAdapter != null) {
			return (T) defaultParameterAdapter.adaptParam(value, parameterClass);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public<T> T adaptParams(MultiMap values, Class<T> parameterClass) throws Exception {
		ParameterAdapter<T> adapter = getAdapter(parameterClass);
		if (adapter != null) {
			return adapter.adaptParams(values, parameterClass);
		} else if (defaultParameterAdapter != null) {
			return (T) defaultParameterAdapter.adaptParams(values, parameterClass);
		}
		return null;
	}
}
