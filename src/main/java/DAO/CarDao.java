package DAO;

import model.Car;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import service.DailyReportService;
import util.DBHelper;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;

public class CarDao {


    private Session session;

    public CarDao(Session session) {
        this.session = session;
    }


    public List<Car> getAllCars() {
        Criteria criteria = session.createCriteria(Car.class);
        List<Car> result = (List<Car>) criteria.list();
        session.close();
        return result;
    }

    public boolean soldCar(Map<String, String> map) {
        Criteria criteria = session.createCriteria(Car.class);
        List<Car> carsToSell = (List<Car>) criteria
                .add(Restrictions.allEq(map))
                .list();
        if (carsToSell.size() != 0) {
            Transaction trx = null;
            try {
                Car car = carsToSell.get(0);
                trx = session.beginTransaction();
                session.delete(car);
                trx.commit();
                DailyReportService.getInstance().updateReport(car.getPrice(), 1L);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                if (trx != null) {
                    trx.rollback();
                }
                session.close();
            }
        }
        session.close();
        return false;

    }

    public List<Car> getCarByBrand (String brand) {
        session.beginTransaction();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Car> criteriaQuery = criteriaBuilder.createQuery(Car.class);
        Root<Car> carRoot = criteriaQuery.from(Car.class);
        criteriaQuery.select(carRoot).where(criteriaBuilder.equal(carRoot.get("brand"),brand));
        Query<Car> query= session.createQuery(criteriaQuery);
        List<Car> results = query.getResultList();
        session.getTransaction().commit();
        session.close();
        return results;
    }

    public void addCar(Car car) {
        session.beginTransaction();
        session.save(car);
        session.getTransaction().commit();
        session.close();
    }
}
