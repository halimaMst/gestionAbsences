package entities;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
public class Enseignant extends User {
    
    @OneToMany(mappedBy = "enseignant")
    private List<SeanceCours> seancesCours;
    
    public Enseignant() {
    }
    
    public Enseignant(String nom, String email, String motDePasse) {
        super(nom, email, motDePasse);
    }
    
    // Ajouter getters et setters pour seancesCours
    public List<SeanceCours> getSeancesCours() {
        return seancesCours;
    }
    
    public void setSeancesCours(List<SeanceCours> seancesCours) {
        this.seancesCours = seancesCours;
    }
}