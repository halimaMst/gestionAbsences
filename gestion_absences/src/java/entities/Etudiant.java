package entities;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "etudiants")
public class Etudiant extends User {

    private String prenom;
    private String classe;

    @OneToMany(mappedBy = "etudiant")
    private List<Absence> absences;

    public Etudiant() {
    }

    public Etudiant(String nom, String prenom, String classe, String email, String motDePasse) {
        super(nom, email, motDePasse);
        this.prenom = prenom;
        this.classe = classe;
    }

    // Getters & Setters
    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public List<Absence> getAbsences() {
        return absences;
    }

    public void setAbsences(List<Absence> absences) {
        this.absences = absences;
    }
}
