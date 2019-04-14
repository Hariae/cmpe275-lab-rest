package jbr.springmvc.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jbr.springmvc.model.CollaborationEntity;
import jbr.springmvc.model.EmployeeEntity;

@Repository
@Transactional
public class CollaborationDaoImpl implements CollaborationDao{
	@Autowired
	DataSource datasource;
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@PersistenceContext
	private EntityManager manager;
	
	public Integer addCollaboration(CollaborationEntity collaboration) {
		// TODO Auto-generated method stub
		manager.persist(collaboration);
		return collaboration.getCollab_Id();
	}

	public CollaborationEntity getCollaboration(CollaborationEntity collaboration) {
		// TODO Auto-generated method stub
		
		List<CollaborationEntity> collaborations = manager.createQuery("Select a From CollaborationEntity a", CollaborationEntity.class).getResultList();
		CollaborationEntity result = null;
		for(int i=0;i<collaborations.size();i++) {
			if(collaborations.get(i).getCollab_1().equals(collaboration.getCollab_1()) && collaborations.get(i).getCollab_2().equals(collaboration.getCollab_2())) {
				result  = collaborations.get(i);
			}
		}
		
		
		return result;
	}

	public void removeCollaboration(CollaborationEntity collaboration) {
		// TODO Auto-generated method stub
		
		//CollaborationEntity collab = manager.find(CollaborationEntity.class, collaboration.getCollab_Id());
		manager.remove(collaboration);
		
	}

}
