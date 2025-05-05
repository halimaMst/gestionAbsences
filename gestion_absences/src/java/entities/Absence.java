package entities;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Absences")
public class Absence {

    @EmbeddedId
    private AbsenceId id;

    @ManyToOne
    @JoinColumn(name = "seance_cours_id", referencedColumnName = "id", insertable = false, updatable = false)
    private SeanceCours seanceCours;

    @ManyToOne
    @JoinColumn(name = "etudiant_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Etudiant etudiant;
    
    @Column(name = "justifiee")
    private boolean justifiee;
    
    @Column(name = "justification")
    private String justification;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "date_justification")
    private Date dateJustification;

    public Absence() {
    }

    public Absence(SeanceCours seanceCours, Etudiant etudiant) {
        this.seanceCours = seanceCours;
        this.etudiant = etudiant;
        this.justifiee = false;
        this.justification = null;
        this.dateJustification = null;
    }

    public AbsenceId getId() {
        return id;
    }

    public void setId(AbsenceId id) {
        this.id = id;
    }

    public SeanceCours getSeanceCours() {
        return seanceCours;
    }

    public void setSeanceCours(SeanceCours seanceCours) {
        this.seanceCours = seanceCours;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }
    
    public boolean isJustifiee() {
        return justifiee;
    }
    
    public void setJustifiee(boolean justifiee) {
        this.justifiee = justifiee;
    }
    
    public String getJustification() {
        return justification;
    }
    
    public void setJustification(String justification) {
        this.justification = justification;
    }
    
    public Date getDateJustification() {
        return dateJustification;
    }
    
    public void setDateJustification(Date dateJustification) {
        this.dateJustification = dateJustification;
    }

    @Override
    public String toString() {
        return "Absence{" + "id=" + id + ", seanceCours=" + seanceCours + ", etudiant=" + etudiant + 
               ", justifiee=" + justifiee + ", justification=" + justification + '}';
    }
}