/*
 * Created on Aug 3, 2005
 */
package com.upulgodage.util.ejb.finder;


/**
 * @author Upul Godage <uigodage@yahoo.com>
 */
public abstract class FinderFactory {
	public static FinderHelper getFinderHelper() {
		return new JbossFinderHelper();
	}
}

//!end
