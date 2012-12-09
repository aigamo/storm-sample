package net.aigamo_web.storm.model;

import java.io.Serializable;
import java.util.Date;


public class AnalyticObject implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String operation;

	private String name;

	private Date date;

	private String blogEntryNo;

	public AnalyticObject(String opeartion, String name, Date date,
			String blogEntryNo) {
		this.operation = opeartion;
		this.name = name;
		this.date = date;
		this.blogEntryNo = blogEntryNo;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getBlogEntryNo() {
		return blogEntryNo;
	}

	public void setBlogEntryNo(String blogEntryNo) {
		this.blogEntryNo = blogEntryNo;
	}

}
