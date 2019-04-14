package jbr.springmvc.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="COLLABORATION")
public class CollaborationEntity {
	
	@Id
	@GeneratedValue
	private Integer Collab_Id;
	
	private Integer Collab_1;
	
	private Integer Collab_2;

	public Integer getCollab_Id() {
		return Collab_Id;
	}
	
	public Integer getCollab_1() {
		return Collab_1;
	}

	public void setCollab_1(Integer collab_1) {
		Collab_1 = collab_1;
	}

	public Integer getCollab_2() {
		return Collab_2;
	}

	public void setCollab_2(Integer collab_2) {
		Collab_2 = collab_2;
	}
	
	
}
