package entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AbsenceId implements Serializable {

    @Column(name = "seance_cours_id")
    private int seanceCoursId;

    @Column(name = "etudiant_id")
    private int etudiantId;

    public AbsenceId() {
    }

    public AbsenceId(int seanceCoursId, int etudiantId) {
        this.seanceCoursId = seanceCoursId;
        this.etudiantId = etudiantId;
    }

    public int getSeanceCoursId() {
        return seanceCoursId;
    }

    public void setSeanceCoursId(int seanceCoursId) {
        this.seanceCoursId = seanceCoursId;
    }

    public int getEtudiantId() {
        return etudiantId;
    }

    public void setEtudiantId(int etudiantId) {
        this.etudiantId = etudiantId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AbsenceId that = (AbsenceId) o;

        if (seanceCoursId != that.seanceCoursId) {
            return false;
        }
        return etudiantId == that.etudiantId;
    }

    @Override
    public int hashCode() {
        int result = seanceCoursId;
        result = 31 * result + etudiantId;
        return result;
    }
}
