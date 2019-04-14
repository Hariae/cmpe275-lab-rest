package jbr.springmvc.dao;

import java.util.List;

import jbr.springmvc.model.CollaborationEntity;


public interface CollaborationDao {

	public Integer addCollaboration(CollaborationEntity collaboration);
	
	public CollaborationEntity getCollaboration(CollaborationEntity collaboration);
	
	public void removeCollaboration(CollaborationEntity collaboration);
	
	public List<CollaborationEntity> getEmployeeCollaborations(Integer employeeId);
	
	public List<Integer> getCollaborators(Integer employeeId);
	 
}
