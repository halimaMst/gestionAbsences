package services;

import dao.SeanceCoursDao;
import entities.SeanceCours;
import java.util.List;

public class SeanceCoursService implements IService<SeanceCours> {

    private final SeanceCoursDao scd;

    public SeanceCoursService() {
        this.scd = new SeanceCoursDao();
    }

    @Override
    public boolean create(SeanceCours o) {
        return scd.create(o);
    }

    @Override
    public boolean update(SeanceCours o) {
        return scd.update(o);
    }

    @Override
    public boolean delete(SeanceCours o) {
        return scd.delete(o);
    }

    @Override
    public List<SeanceCours> findAll() {
        return scd.findAll();
    }

    @Override
    public SeanceCours findById(int id) {
        return scd.findById(id);
    }

}
