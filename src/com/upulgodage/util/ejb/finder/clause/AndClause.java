/*
 * Created on Apr 26, 2005
 */
package com.upulgodage.util.ejb.finder.clause;

import java.util.Collection;

import com.upulgodage.util.ejb.finder.exception.ClauseException;

/**
 * @author Upul Godage <uigodage@yahoo.com>
 */
public class AndClause extends JoinClause {
	public AndClause(Collection clauses) throws ClauseException {
		super(clauses, JoinClause.JOIN_WITH_AND);
	}
}

//!end

