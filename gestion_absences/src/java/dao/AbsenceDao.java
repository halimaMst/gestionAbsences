/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entities.Absence;
import entities.AbsenceId;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

/**
 *
 * @author adhmin
 */
public class AbsenceDao extends AbstractDao<Absence> {

    public AbsenceDao() {
        super(Absence.class);
    }
public Absence findById(AbsenceId id) {
        Session session = null;
        Transaction tx = null;
        Absence absence = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            absence = (Absence) session.get(Absence.class, id);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return absence;
    }
}
