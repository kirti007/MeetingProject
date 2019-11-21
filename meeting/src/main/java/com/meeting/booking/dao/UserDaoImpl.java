package com.meeting.booking.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import com.meeting.booking.pojo.*;

import com.meeting.booking.model.User;

@Transactional
@Repository
public class UserDaoImpl implements UserDao {
	@Autowired
	private EntityManager factory;

	@Override
	public User findById(int userId) {
		return factory.find(User.class, userId);
	}

	@Override
	public void save(User user) {
		factory.persist(user);
	}

	@Override
	public User getUser(String userNameOrEmail) {
		Query p = factory.createNativeQuery("select * from meetings.user", User.class);
		@SuppressWarnings("unchecked")
		List<User> users = p.getResultList();
		if (CollectionUtils.isEmpty(users))
			return null;
		return users.stream().filter(u -> u.getEmailId().equals(userNameOrEmail)).findAny().orElse(null);
	}

	@Override
	public void update(User user) {
		factory.persist(user);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUserId(String emailId) {

		Query q = factory.createNativeQuery("select * from user where email_id='" + emailId + "'", User.class);
		// ArrayList<User> li = (ArrayList<User>) q.getResultList();
		List<User> li = q.getResultList();
		System.out.println(li);
		return li;
	}

	@Override
	public void UpdatePasswordNew(UpdatePasswordNew updatePassword) {
		String emId = "'" + updatePassword.getUserNameOrEmail() + "'";
		String pass = "'" + updatePassword.getNewPassword() + "'";
		int id = +updatePassword.getId();
		Query q = factory.createNativeQuery(
				"update user set password=" + pass + "where email_id=" + emId + "and id=" + id + "", User.class);
		q.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllEmail() {
		Query q = factory.createNativeQuery("select email_id from user");

		List<String> li = q.getResultList();
		System.out.println(li);
		return li;
	}
}
