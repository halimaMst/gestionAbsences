/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entities.Etudiant;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

/**
 *
 * @author adhmin
 */
public class EtudiantDao extends AbstractDao<Etudiant> {

    public EtudiantDao() {
        super(Etudiant.class);
    }
@Override
    public Etudiant findById(int id) {
        Session session = null;
        Transaction tx = null;
        Etudiant etudiant = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            etudiant = (Etudiant) session.get(Etudiant.class, id);

            if (etudiant != null) {
                // Forcer le chargement des absences (collection LAZY)
                Hibernate.initialize(etudiant.getAbsences());
            }

            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
                        e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return etudiant;
    }
}

