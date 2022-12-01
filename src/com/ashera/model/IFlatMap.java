package com.ashera.model;

import java.util.Map;

public interface IFlatMap {

	/**
	 * Get the hierarchical map encapsulated in this object.
	 *
	 * @return
	 */
	Map<String, Object> getHierarchicalMap();

	/**
	 * Convert a hierarchical map into a plain map. Create a new instance of the plain map.
	 *
	 * @param documentMap
	 * @return
	 */
	Map<String, String> getPlainMap();

	/**
	 * Check if the path specified actually exists in the plain map.
	 *
	 * @param path
	 * @return
	 */
	boolean exists(String path);

	/**
	 * Get an element of the hierarchical map by a plain map path. This method simplifies the access to the information
	 * of the hierarchical map. If the path does not map any element in the hierarchical map, then return null. Under
	 * any type of error, it is returned a null value.
	 *
	 * @param path
	 * @return
	 */
	Object get(String path);

	/**
	 * Put a value in the hierarchical map by a plain map path. It is possible to put a List<Object> or Map<String,
	 * Object> values in the plain map; however, these values must be compatible with a PlainMap object (where every
	 * leave node is a String) and this is not validated by this method. Undefined behavior may happen if it is put a
	 * List or Map with an incompatible type.
	 *
	 * @param path
	 * @param value
	 * @throws PlainMapException
	 */
	void put(String path, Object value) throws PlainMapException;

	/**
	 * Clear the plain map.
	 */
	void clear();

	/**
	 * Remove an element (or elements) under the path specified. If the path does not match any element,
	 * the action is ignored (without throwing any exception).
	 *
	 * @param path
	 * @throws PlainMapException
	 */
	void remove(String path) throws PlainMapException;

	/**
	 * Add a root path to every element in the map.
	 *
	 * @param rootPath
	 */
	void addRootPath(String rootPath);

	/**
	 * Remove a root path reducing the hierarchy of the map.
	 *
	 * It clears the map and assign to the map the value at rootPath.
	 *
	 * @param rootPath
	 * @throws PlainMapException
	 */
	void removeRootPath(String rootPath) throws PlainMapException;

	/**
	 * Get the parent element name. If there is any problem or there is not one and only one element, then return null.
	 *
	 * @return
	 * @throws PlainMapException
	 */
	String getParent() throws PlainMapException;

	/**
	 * Extract the parent element. The map is modified after the extraction of the root element and this root element
	 * name is returned.
	 *
	 * @return
	 * @throws PlainMapException
	 */
	String extractParent() throws PlainMapException;

}