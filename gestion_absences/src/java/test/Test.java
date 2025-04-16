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
import dao.UserDao;
import entities.Absence;
import entities.AbsenceId;
import entities.Enseignant;
import entities.Etudiant;
import entities.SeanceCours;
import entities.User;
import util.HibernateUtil;
import java.sql.Time;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 *
 * @author adhmin
 */

public class Test {

    public static void main(String[] args) throws Exception {
        // Initialiser la session Hibernate
        HibernateUtil.getSessionFactory();

        // Initialiser les DAO
        UserDao userDao = new UserDao(UserDao.class);
        EnseignantDao enseignantDao = new EnseignantDao();
        EtudiantDao etudiantDao = new EtudiantDao();
        SeanceCoursDao seanceCoursDao = new SeanceCoursDao();
        AbsenceDao absenceDao = new AbsenceDao();

        // Créer le professeur Zahid
        Enseignant profZahid = new Enseignant("Zahid", "zahid@example.com", "prof123");
        enseignantDao.create(profZahid);

        // Créer les étudiants
        Etudiant asmaa = new Etudiant("Ammari", "Asmaa", "L3 Info", "asmaa@example.com", "etud123");
        Etudiant halima = new Etudiant("Halima", "Moustine", "L3 Info", "halima@example.com", "etud456");
        Etudiant taha = new Etudiant("Taha", "Hasraoui", "M1 Info", "taha@example.com", "etud789");

        etudiantDao.create(asmaa);
        etudiantDao.create(halima);
        etudiantDao.create(taha);

        // Créer des séances de cours
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = sdf.parse("2025-05-10");
        Date date2 = sdf.parse("2025-05-17");

        SeanceCours seanceJava = new SeanceCours(
                date1,
                Time.valueOf("09:00:00"),
                "Java Avancé",
                profZahid
        );

        SeanceCours seanceBD = new SeanceCours(
                date2,
                Time.valueOf("14:00:00"),
                "Base de données",
                profZahid
        );

        seanceCoursDao.create(seanceJava);
        seanceCoursDao.create(seanceBD);

        // Enregistrer des absences
        Absence absence1 = new Absence(seanceJava, asmaa);
        absence1.setId(new AbsenceId(seanceJava.getId(), asmaa.getId()));

        Absence absence2 = new Absence(seanceJava, halima);
        absence2.setId(new AbsenceId(seanceJava.getId(), halima.getId()));

        Absence absence3 = new Absence(seanceBD, taha);
        absence3.setId(new AbsenceId(seanceBD.getId(), taha.getId()));

        absenceDao.create(absence1);
        absenceDao.create(absence2);
        absenceDao.create(absence3);

        // Afficher toutes les absences
        System.out.println("\nToutes les absences:");
        for (Absence a : absenceDao.findAll()) {
            System.out.println(a);
        }

        // Afficher les absences d'un étudiant spécifique
        System.out.println("\nAbsences de l'étudiante Asmaa Ammari:");
        for (Absence a : absenceDao.findAll()) {
            if (a.getEtudiant().getId() == asmaa.getId()) {
                System.out.println("Date: " + a.getSeanceCours().getDate()
                        + ", Matière: " + a.getSeanceCours().getMatiere());
            }
        }

        // Afficher les séances du professeur Zahid
        System.out.println("\nSéances de cours du professeur Zahid:");
        for (SeanceCours s : seanceCoursDao.findAll()) {
            if (s.getEnseignant().getId() == profZahid.getId()) {
                System.out.println("Date: " + s.getDate()
                        + ", Heure: " + s.getHeure()
                        + ", Matière: " + s.getMatiere());
            }
        }

        // Afficher les absences pour la séance de Java
        System.out.println("\nAbsences pour la séance de Java Avancé:");
        for (Absence a : absenceDao.findAll()) {
            if (a.getSeanceCours().getId() == seanceJava.getId()) {
                System.out.println("Étudiant: " + a.getEtudiant().getNom()
                        + " " + a.getEtudiant().getPrenom());
            }
        }

        // Afficher la liste de tous les étudiants
        System.out.println("\nListe de tous les étudiants:");
        for (Etudiant e : etudiantDao.findAll()) {
            System.out.println(e.getNom() + " " + e.getPrenom() + " - " + e.getClasse());
        }
    }
}
