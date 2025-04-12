/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import dao.AbsenceDao;
import dao.EnseignantDao;
import dao.EtudiantDao;
import dao.SeanceCoursDao;
import entities.*;
import util.HibernateUtil;

import java.sql.Time;
import java.util.Date;
import java.util.List;

/**
 *
 * @author adhmin
 */
public class Test {

    public static void main(String[] args) {
        // Initialiser la session Hibernate
        HibernateUtil.getSessionFactory();

        // Initialiser les DAO
        EnseignantDao enseignantDao = new EnseignantDao();
        EtudiantDao etudiantDao = new EtudiantDao();
        SeanceCoursDao seanceCoursDao = new SeanceCoursDao();
        AbsenceDao absenceDao = new AbsenceDao();

        // Créer un enseignant
        Enseignant enseignant = new Enseignant("Zahid Dupont", "Zahid@example.com", "password");
        enseignantDao.create(enseignant);

        // Créer des étudiants
        Etudiant etudiant1 = new Etudiant("Moustine", "Halima", "Classe A");
        Etudiant etudiant2 = new Etudiant("ammari", "Taha", "Classe B");
        etudiantDao.create(etudiant1);
        etudiantDao.create(etudiant2);

        // Créer une séance de cours
        SeanceCours seanceCours = new SeanceCours(new Date(), new Time(10, 0, 0), "Mathématiques", enseignant);
        seanceCoursDao.create(seanceCours);

        // Enregistrer des absences
        Absence absence1 = new Absence(etudiant1, seanceCours, true);
        Absence absence2 = new Absence(etudiant2, seanceCours, false);
        absenceDao.create(absence1);
        absenceDao.create(absence2);

        // Afficher toutes les absences
        System.out.println("\n Toutes les absences :");
        for (Absence a : absenceDao.findAll()) {
            System.out.println(a);
        }

        // Afficher les absences d'un étudiant spécifique (par ID)
        System.out.println("\n Absences de l'étudiant Jean Martin :");
        for (Absence a : absenceDao.findAll()) {
            if (a.getEtudiant().getId() == etudiant1.getId()) {
                System.out.println(a);
            }
        }

        // Afficher les séances d'un enseignant spécifique (par ID)
        System.out.println("\n Séances de l'enseignant Professeur Dupont :");
        for (SeanceCours seance : seanceCoursDao.findAll()) {
            if (seance.getEnseignant().getId() == enseignant.getId()) {
                System.out.println("Séance ID: " + seance.getId() + ", Date: " + seance.getDate() + ", Matière: " + seance.getMatiere());
            }
        }

    }
}
