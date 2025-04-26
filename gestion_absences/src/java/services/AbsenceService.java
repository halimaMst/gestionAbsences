/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;
import dao.AbsenceDao;
import entities.Absence;
import entities.AbsenceId;
import java.util.List;
/**
 *
 * @author adhmin
 */
public class AbsenceService {
    private final AbsenceDao ad;

    public AbsenceService() {
        this.ad = new AbsenceDao();
    }

    public boolean create(Absence o) {
        return ad.create(o);
    }

    public boolean update(Absence o) {
        return ad.update(o);
    }

    public boolean delete(Absence o) {
        return ad.delete(o);
    }
    public List<Absence> findAll() {
        return ad.findAll();
    }
     public Absence findById(AbsenceId id) {
        return ad.findById(id);
    }
}
