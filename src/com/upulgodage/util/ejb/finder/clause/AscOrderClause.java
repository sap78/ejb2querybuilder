/*
 * Created on Apr 25, 2005
 */
package com.upulgodage.util.ejb.finder.clause;

/**
 * @author Upul Godage <uigodage@yahoo.com>
 */
public class AscOrderClause extends OrderClause {
    public AscOrderClause(String field) {
        expression = field  + " ASC";
    }
}

//!end
