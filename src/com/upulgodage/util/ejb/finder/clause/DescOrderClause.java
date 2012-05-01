/*
 * Created on Apr 25, 2005
 */
package com.upulgodage.util.ejb.finder.clause;

/**
 * @author Upul Godage <uigodage@yahoo.com>
 */
public class DescOrderClause extends OrderClause {
    public DescOrderClause(String field) {
        expression = field  + " DESC";
    }
}

//!end
