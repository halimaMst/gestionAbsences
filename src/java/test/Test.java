package test;

import dao.AbsenceDAO;
import dao.EtudiantDAO;
import dao.SeanceCoursDAO;
import dao.UserDAO;
import entities.Absence;
import entities.Etudiant;
import entities.SeanceCours;
import entities.User;
import util.HibernateUtil;

import java.util.Date;
import java.sql.Time;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        HibernateUtil.getSessionFactory();

        // Initialiser les DAO
        UserDAO userDao = new UserDAO();
        EtudiantDAO etudiantDao = new EtudiantDAO();
        SeanceCoursDAO seanceCoursDao = new SeanceCoursDAO();
        AbsenceDAO absenceDao = new AbsenceDAO();

        // Créer un utilisateur (enseignant)
        User enseignant = new User("Professeur Zahid", "Zahid@example.com", "password");
        userDao.create(enseignant);

        // Créer des étudiants
        Etudiant etudiant1 = new Etudiant("Moustine", "Halima", "L1 Info");
        Etudiant etudiant2 = new Etudiant("Ammari", "Taha", "L1 Info");
        etudiantDao.create(etudiant1);
        etudiantDao.create(etudiant2);

        // Créer une séance de cours
        Date date = new Date(125, 3, 15); 
        Time heure = new Time(10, 0, 0); 
        SeanceCours seance = new SeanceCours(date, heure, "Java", enseignant);
        seanceCoursDao.create(seance);

        // Enregistrer des absences
        Absence absence1 = new Absence(etudiant1, seance, true); 
        Absence absence2 = new Absence(etudiant2, seance, false); 
        absenceDao.create(absence1);
        absenceDao.create(absence2);

        // Afficher toutes les absences
        System.out.println("\nToutes les absences :");
        for (Absence a : absenceDao.findAll()) {
            System.out.println(a.getEtudiant().getNom() + " est absent(e) le " + a.getSeance().getDate() + ", justifiée : " + a.isJustifiee());
        }

        // Afficher les absences d'un étudiant spécifique
        System.out.println("\nAbsences de " + etudiant1.getNom() + " :");
        List<Absence> absencesEtudiant1 = absenceDao.findByEtudiant(etudiant1);
        for (Absence a : absencesEtudiant1) {
            System.out.println("Le " + a.getSeance().getDate() + ", justifiée : " + a.isJustifiee());
        }

        // Afficher les séances d'un enseignant spécifique
        System.out.println("\nSéances de " + enseignant.getNom() + " :");
        List<SeanceCours> seancesEnseignant = seanceCoursDao.findByEnseignant(enseignant);
        for (SeanceCours s : seancesEnseignant) {
            System.out.println("Le " + s.getDate() + " à " + s.getHeure() + ", matière : " + s.getMatiere());
        }
    }
}
