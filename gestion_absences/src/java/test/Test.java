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
import util.HibernateUtil;
import java.sql.Time;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Test {

    public static void main(String[] args) throws Exception {
        // Initialiser la session Hibernate
        HibernateUtil.getSessionFactory();

        // Initialiser les DAO
        UserDao userDao = new UserDao();
        EnseignantDao enseignantDao = new EnseignantDao();
        EtudiantDao etudiantDao = new EtudiantDao();
        SeanceCoursDao seanceCoursDao = new SeanceCoursDao();
        AbsenceDao absenceDao = new AbsenceDao();

        // Créer 2 enseignants
        Enseignant profZahid = new Enseignant("Zahid", "zahid@example.com", "prof123");
        Enseignant profAhmed = new Enseignant("Ahmed", "ahmed@example.com", "prof456");
        enseignantDao.create(profZahid);
        enseignantDao.create(profAhmed);

        // Créer 2 étudiants
        Etudiant asmaa = new Etudiant("Ammari", "Asmaa", "L3 Info", "asmaa@example.com", "etud123");
        Etudiant taha = new Etudiant("Taha", "Hasraoui", "M1 Info", "taha@example.com", "etud789");
        etudiantDao.create(asmaa);
        etudiantDao.create(taha);

        // Créer 2 séances de cours
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
                profAhmed
        );
        seanceCoursDao.create(seanceJava);
        seanceCoursDao.create(seanceBD);

        // Créer 2 absences
        Absence absence1 = new Absence(seanceJava, asmaa);
        absence1.setId(new AbsenceId(seanceJava.getId(), asmaa.getId()));
        absence1.setJustifiee(false);

        Absence absence2 = new Absence(seanceBD, taha);
        absence2.setId(new AbsenceId(seanceBD.getId(), taha.getId()));
        absence2.setJustifiee(true);
        absence2.setJustification("Certificat médical");
        absence2.setDateJustification(sdf.parse("2025-05-18"));

        absenceDao.create(absence1);
        absenceDao.create(absence2);

        // Afficher toutes les absences
        System.out.println("\nToutes les absences:");
        for (Absence a : absenceDao.findAll()) {
            System.out.println(a);
        }

        // Afficher la liste de tous les étudiants
        System.out.println("\nListe de tous les étudiants:");
        for (Etudiant e : etudiantDao.findAll()) {
            System.out.println(e.getNom() + " " + e.getPrenom() + " - " + e.getClasse());
        }

        // Afficher la liste de tous les enseignants
        System.out.println("\nListe de tous les enseignants:");
        for (Enseignant e : enseignantDao.findAll()) {
            System.out.println(e.getNom() + " - " + e.getEmail());
        }

        // Statistiques des absences
        System.out.println("\nStatistiques des absences:");
        int totalAbsences = absenceDao.findAll().size();
        int absencesJustifiees = 0;
        int absencesNonJustifiees = 0;
        for (Absence a : absenceDao.findAll()) {
            if (a.isJustifiee()) absencesJustifiees++;
            else absencesNonJustifiees++;
        }
        System.out.println("Total des absences: " + totalAbsences);
        System.out.println("Absences justifiées: " + absencesJustifiees + " (" + (absencesJustifiees * 100 / totalAbsences) + "%)");
        System.out.println("Absences non justifiées: " + absencesNonJustifiees + " (" + (absencesNonJustifiees * 100 / totalAbsences) + "%)");
    }
}
