package jbr.springmvc.dao;

import jbr.springmvc.model.CollaborationEntity;


public interface CollaborationDao {

	public Integer addCollaboration(CollaborationEntity collaboration);
	
	public CollaborationEntity getCollaboration(CollaborationEntity collaboration);
	 
}
