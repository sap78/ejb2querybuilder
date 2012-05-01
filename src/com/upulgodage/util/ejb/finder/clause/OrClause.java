/*
 * Created on Apr 26, 2005
 */
package com.upulgodage.util.ejb.finder.clause;

import java.util.Collection;

import com.upulgodage.util.ejb.finder.exception.ClauseException;

/**
 * @author Upul Godage <uigodage@yahoo.com>
 */
public class OrClause extends JoinClause {
	public OrClause(Collection criterions) throws ClauseException {
		super(criterions, JoinClause.JOIN_WITH_OR);
	}
}

//!end

