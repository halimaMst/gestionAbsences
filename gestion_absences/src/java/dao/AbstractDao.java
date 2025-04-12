/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

/**
 *
 * @author adhmin
 */
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import util.HibernateUtil;
import java.util.List;

public abstract class AbstractDao<T> implements IDao<T> {

    private final Class<T> entityClass;

    public AbstractDao(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public boolean create(final T o) {
        return executeTransaction(new HibernateOperation<T>() {
            @Override
            public void execute(Session session) {
                session.save(o);
            }
        });
    }

    public boolean delete(final T o) {
        return executeTransaction(new HibernateOperation<T>() {
            @Override
            public void execute(Session session) {
                session.delete(o);
            }
        });
    }

    public boolean update(final T o) {
        return executeTransaction(new HibernateOperation<T>() {
            @Override
            public void execute(Session session) {
                session.update(o);
            }
        });
    }

    public List<T> findAll() {
        Session session = null;
        Transaction tx = null;
        List<T> list = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            list = session.createQuery("FROM " + entityClass.getSimpleName())
                    .list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return list;
    }

    public T findById(int id) {
        Session session = null;
        Transaction tx = null;
        T entity = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            entity = (T) session.get(entityClass, id);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    private boolean executeTransaction(HibernateOperation<T> operation) {
        Session session = null;
        Transaction tx = null;
        boolean status = false;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            operation.execute(session);
            tx.commit();
            status = true;
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return status;
    }

    private interface HibernateOperation<T> {

        void execute(Session session);
    }
}
