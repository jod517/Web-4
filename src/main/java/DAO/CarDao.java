package DAO;

import model.Car;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.DBHelper;

import java.util.List;

public class CarDao {

    private SessionFactory sessionFactory;

    public CarDao() {
        this.sessionFactory = DBHelper.getSessionFactory();
    }



    public List<Car> getAllCars() {
        String hql = "FROM Car";
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<Car> AllCars = session.createQuery(hql).list();
        transaction.commit();
        session.close();
        return AllCars;
    }
}
