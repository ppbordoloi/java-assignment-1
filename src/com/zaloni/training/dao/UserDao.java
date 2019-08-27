package com.zaloni.training.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.zaloni.training.conf.DbConnection;
import com.zaloni.training.entity.User;

public class UserDao {
    public void save(User user) {
        Session sessionObj = DbConnection.getSession();
        sessionObj.beginTransaction();
        sessionObj.save(user);
        sessionObj.getTransaction().commit();
    }
    
    public boolean isEmailExists(String email) {
        Session sessionObj = DbConnection.getSession();
        sessionObj.beginTransaction();
        Query query = sessionObj.createQuery("SELECT COUNT(u.id) FROM User u WHERE u.email = :email ");
        query.setParameter("email", email);
        Long c = (Long) query.uniqueResult();
        int count = c.intValue();
        sessionObj.getTransaction().commit();
        return count > 0;
    }
    
    public boolean isValidCredential(String email, String password) {
        Session sessionObj = DbConnection.getSession();
        sessionObj.beginTransaction();
        Query query = sessionObj.createQuery("SELECT COUNT(u.id) FROM User u WHERE u.email = :email AND u.password = :password");
        query.setParameter("email", email);
        query.setParameter("password", password);
        Long c = (Long) query.uniqueResult();
        int count = c.intValue();
        sessionObj.getTransaction().commit();
        return count > 0;
    }
    
    @SuppressWarnings("unchecked")
    public List<User> getUsers(String searchString) {
        Session sessionObj = DbConnection.getSession();
        sessionObj.beginTransaction();

        Criteria crit = sessionObj.createCriteria(User.class);
        if (searchString != null && !searchString.isEmpty()) {
            crit.add(Restrictions.or(Restrictions.like("username", searchString + "%", MatchMode.ANYWHERE),Restrictions.like("id", Integer.valueOf(searchString))));
        }
        List<User> results = crit.list();
        sessionObj.getTransaction().commit();
        return results;
    }
    
    public void deleteUser(int id) {
        Session sessionObj = DbConnection.getSession();
        sessionObj.beginTransaction();
        User user = (User) sessionObj.load(User.class, id);
        sessionObj.delete(user);
        sessionObj.getTransaction().commit();
    }

    @SuppressWarnings("unchecked")
    public User getUser(int id) {
        Session sessionObj = DbConnection.getSession();
        sessionObj.beginTransaction();
        Criteria crit = sessionObj.createCriteria(User.class);
        crit.add(Restrictions.eq("id", id));
        List<User> results = crit.list();
        sessionObj.getTransaction().commit();
        return results.size() > 0 ? results.get(0) : null;
    }

    @SuppressWarnings("unchecked")
    public User getUserByEmail(String email) {
        Session sessionObj = DbConnection.getSession();
        sessionObj.beginTransaction();
        Criteria crit = sessionObj.createCriteria(User.class);
        crit.add(Restrictions.eq("email", email));
        List<User> results = crit.list();
        sessionObj.getTransaction().commit();
        return results.size() > 0 ? results.get(0) : null;
    }
}
