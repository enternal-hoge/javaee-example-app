package eternal.hoge.javaee.simple.example.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import eternal.hoge.javaee.simple.example.model.User;

public class UserDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	public void save(User user) {
		entityManager.persist(user);
	}
	
	public User findById(Long userId) {
		return entityManager.find(User.class, userId);
	}
	
	public User login(User user) {
		List<User> result = entityManager
					.createQuery("from User "
								+ "where email = :email "
								+ "and password = :pass", User.class)
					.setParameter("email", user.getEmail())
					.setParameter("pass", user.getPassword())
					.setMaxResults(1)
					.getResultList();
		
		if(result.isEmpty()) {
			return null;
		}
		
		return result.get(0);
	}
	
}
