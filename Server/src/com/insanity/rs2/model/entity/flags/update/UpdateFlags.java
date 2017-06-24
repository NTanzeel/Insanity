package com.insanity.rs2.model.entity.flags.update;

import java.util.BitSet;

/**
 * Holds update flags.
 * @author Naqash Tanzeel
 *
 */
public class UpdateFlags {
	
	/**
	 * The bitset (flag data).
	 */
	private BitSet flags = new BitSet();
	
	/**
	 * Checks if an update required.
	 * @return <code>true</code> if 1 or more flags are set,
	 * <code>false</code> if not.
	 */
	public boolean isUpdateRequired() {
		return !flags.isEmpty();
	}
	
	/**
	 * Flags (sets to true) a flag.
	 * @param flag The flag to flag.
	 */
	public void flag(UpdateFlag flag) {
		flags.set(flag.ordinal(), true);
	}
	
	/**
	 * Sets a flag.
	 * @param flag The flag.
	 * @param value The value.
	 */
	public void set(UpdateFlag flag, boolean value) {
		flags.set(flag.ordinal(), value);
	}
	
	/**
	 * Gets the value of a flag.
	 * @param flag The flag to get the value of.
	 * @return The flag value.
	 */
	public boolean get(UpdateFlag flag) {
		return flags.get(flag.ordinal());
	}
	
	/**
	 * Resest all update flags.
	 */
	public void reset() {
		flags.clear();
	}

}
