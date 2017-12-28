package com.bridgelabz.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table
public class Notes implements Serializable {

	@Id
	@GeneratedValue
	private int id;

	private String description;

	private String title;

	private Date creationDate;

	private Date modificationDate;

	private String color;

	private boolean isPin;

	private boolean isTrash;
	
	private boolean isArchive;

	private String reminderDate;

	@Lob
	@Column(name = "notePicture", columnDefinition = "LONGBLOB")
	private String notePicture;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "User_Id")
	private User user;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "collaborator", joinColumns = @JoinColumn(name = "noteId"), inverseJoinColumns = @JoinColumn(name = "userId"))
	private List<User> userId = new LinkedList<User>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getModificationDate() {
		return modificationDate;
	}

	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public boolean isPin() {
		return isPin;
	}

	public void setPin(boolean isPin) {
		this.isPin = isPin;
	}

	public boolean isTrash() {
		return isTrash;
	}

	public void setTrash(boolean isTrash) {
		this.isTrash = isTrash;
	}

	public boolean isArchive() {
		return isArchive;
	}

	public void setArchive(boolean isArchive) {
		this.isArchive = isArchive;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getReminderDate() {
		return reminderDate;
	}

	public void setReminderDate(String reminderDate) {
		this.reminderDate = reminderDate;
	}

	public String getNotePicture() {
		return notePicture;
	}

	public void setNotePicture(String notePicture) {
		this.notePicture = notePicture;
	}

	public List<User> getUserId() {
		return userId;
	}

	public void setUserId(List<User> userId) {
		this.userId = userId;
	}


	
	
	
}
