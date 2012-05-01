/*
 * Created on Aug 3, 2005
 */
package com.upulgodage.util.ejb.finder;

import java.util.Collection;
import java.util.Map;

import com.upulgodage.util.ejb.finder.clause.OrderClause;
import com.upulgodage.util.ejb.finder.clause.WhereClause;
import com.upulgodage.util.ejb.finder.exception.ClauseException;


/**
 * @author Upul Godage <uigodage@yahoo.com>
 */
public interface FinderHelper {
	public FinderParam createFinderParam(String fromClause, Collection whereClauses, Collection orderClauses, Map paramMap);

	public WhereClause newEqualClause(String attributeName, Object value) throws ClauseException;
	public WhereClause newNotEqualClause(String attributeName, Object value) throws ClauseException;

	public WhereClause newGreaterThanClause(String attributeName, Object value) throws ClauseException;
	public WhereClause newGreaterThanOrEqualClause(String attributeName, Object value) throws ClauseException;
	public WhereClause newLessThanClause(String attributeName, Object value) throws ClauseException;
	public WhereClause newLessThanOrEqualClause(String attributeName, Object value) throws ClauseException;

	public WhereClause newEqualBeanClause(String prefix, Object bean) throws ClauseException;

	public WhereClause newNotClause(WhereClause whereCriterion) throws ClauseException;

	public WhereClause newNullClause(String attributeName) throws ClauseException;
	public WhereClause newNotNullClause(String attributeName) throws ClauseException;

	public WhereClause newInClause(String attributeName, Collection values) throws ClauseException;
	public WhereClause newNotInClause(String attributeName, Collection values) throws ClauseException;

	public WhereClause newLikeClause(String attributeName, Object value) throws ClauseException;
	public WhereClause newNotLikeClause(String attributeName, Object value) throws ClauseException;

	public WhereClause newLikeClause(String attributeName, Object value, String escapeChar) throws ClauseException;
	public WhereClause newNotLikeClause(String attributeName, Object value, String escapeChar) throws ClauseException;

	public WhereClause newBetweenClause(String attributeName, Object low, Object high) throws ClauseException;
	public WhereClause newNotBetweenClause(String attributeName, Object low, Object high) throws ClauseException;

	public WhereClause newOrClause(Collection clauses) throws ClauseException;
	public WhereClause newAndClause(Collection clauses) throws ClauseException;

	public WhereClause newCustomClause(String query, Collection args) throws ClauseException;

	public OrderClause newAscOrderClause(String field) throws ClauseException;
	public OrderClause newDescOrderClause(String field) throws ClauseException;
}

//!end
