/*
 * Created on Aug 3, 2005
 */
package com.upulgodage.util.ejb.finder;

/**
 * @author Upul Godage <uigodage@yahoo.com>
 */
public class FinderParam {
	Object[] args;
	String sql;

	public Object[] getArgs() {
		return args;
	}
	public void setArgs(Object[] args) {
		this.args = args;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
}

//!end
