package com.wcs.compare.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class AreaEntity {

	/**
	 * Primitive type, is not null
	 */
	@Id
	private long id;
	
	/**
	 * Left horizontal orientation comparison
	 */
	@Lob
	@Column(length = 96000)
	private String left;
	
	/**
	 * Right horizontal orientation comparison
	 */
	@Lob
	@Column(length = 96000)
	private String right;

	/**
	 * Empty constructor
	 */
	public AreaEntity() {

	}

	/**
	 * Constructor new object instances full.
	 * 
	 * @param id of the object
	 * @param left horizontal orientation sent through the request.
	 * @param right horizontal orientation sent through the request.
	 */
	public AreaEntity(long id, String left, String right) {
		this.id = id;
		this.left = left;
		this.right = right;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLeft() {
		return left;
	}

	public void setLeft(String left) {
		this.left = left;
	}

	public String getRight() {
		return right;
	}

	public void setRight(String right) {
		this.right = right;
	}
	
	/**
	 * 
	 * auto-generated hashCode()
	 *
	 * @author <a href="mailto:wellington.cs@hotmail.com">Wellington Silva</a>.
	 * @return int
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((left == null) ? 0 : left.hashCode());
		result = prime * result + ((right == null) ? 0 : right.hashCode());
		return result;
	}

	/**
	 * 
	 * auto-generated equals()
	 *
	 * @author <a href="mailto:wellington.cs@hotmail.com">Wellington Silva</a>.
	 * @param obj
	 * @return boolean
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AreaEntity other = (AreaEntity) obj;
		if (id != other.id)
			return false;
		if (left == null) {
			if (other.left != null)
				return false;
		} else if (!left.equals(other.left))
			return false;
		if (right == null) {
			if (other.right != null)
				return false;
		} else if (!right.equals(other.right))
			return false;
		return true;
	}

	/**
	 * 
	 * Formatting the toString()
	 *
	 * @author <a href="mailto:wellington.cs@hotmail.com">Wellington Silva</a>.
	 * @return String
	 */
	@Override
	public String toString() {
		return "Area [id=" + getId() + ", left=" + getLeft() + ", right=" + getRight() + "]";
	}
}