package com.google.code.qualitas.engines.api.core;

/**
 * The Interface ProcessBundle.
 */
public interface ProcessBundle {

	/**
	 * Every uploaded archive which user want to enhance should have qualitas
	 * descriptor.
	 */
	String QUALITAS_DESCRIPTOR_NAME = "qualitas.xml";

	/**
	 * Sets the bundle.
	 * 
	 * @param bundle
	 *            the contents of bundle
	 */
	void setBundle(byte[] bundle);

	/**
	 * Builds the bundle.
	 * 
	 * @return the byte[]
	 */
	byte[] buildBundle();

	/**
	 * Sets the main process name.
	 * 
	 * @param mainProcessName
	 *            the new main process name
	 */
	void setMainProcessName(String mainProcessName);

	/**
	 * Gets the main process name.
	 * 
	 * @return the main process name
	 */
	String getMainProcessName();

	/**
	 * Gets the qualitas descriptor.
	 * 
	 * @return the qualitas descriptor
	 */
	Entry getQualitasDescriptor();

	/**
	 * Checks if is instrumentable. Returns true if scenario bundle contains
	 * qualitas descriptor.
	 * 
	 * @return true, if is instrumentable
	 */
	boolean isInstrumentable();

	/**
	 * Gets entry by name.
	 * 
	 * @param name
	 *            the name
	 * @return the entry
	 */
	Entry getEntry(String name);

	/**
	 * Adds given entry.
	 * 
	 * @param entry
	 *            the entry
	 */
	void addEntry(Entry entry);

	/**
	 * Removes given entry.
	 * 
	 * @param entryName
	 *            the entry name
	 */
	void removeEntry(String entryName);

	/**
	 * Renames existing entry.
	 * 
	 * @param oldName
	 *            the old name
	 * @param newName
	 *            the new name
	 */
	void renameEntry(String oldName, String newName);

	/**
	 * Releases and removes all allocated resources..
	 */
	void cleanUp();
}