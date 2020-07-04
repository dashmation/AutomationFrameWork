package com.qa.test.pojo;

public class PostsPojo {

	private int id;
	private String title;
	private String author;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/*
	 * Return Name Of The Author
	 */

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	PostsPojo() {

	}

	public PostsPojo(int id, String title, String author) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
	}
}
