package entities;

import java.sql.Time;
import java.util.Date;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "seances_cours")
public class SeanceCours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Temporal(TemporalType.DATE)
    private Date date;

    private Time heure;
    private String matiere;

    @ManyToOne
    private User enseignant;

    @OneToMany(mappedBy = "seance")
    private List<Absence> absences; // Ajout de la collection

    public SeanceCours() {
    }

    public SeanceCours(Date date, Time heure, String matiere, User enseignant) {
        this.date = date;
        this.heure = heure;
        this.matiere = matiere;
        this.enseignant = enseignant;
    }

    // Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getHeure() {
        return heure;
    }

    public void setHeure(Time heure) {
        this.heure = heure;
    }

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public User getEnseignant() {
        return enseignant;
    }

    public void setEnseignant(User enseignant) {
        this.enseignant = enseignant;
    }

    public List<Absence> getAbsences() {
        return absences;
    }

    public void setAbsences(List<Absence> absences) {
        this.absences = absences;
    }
}
