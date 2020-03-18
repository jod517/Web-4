package service;

import DAO.CarDao;
import model.Car;
import org.hibernate.SessionFactory;
import util.DBHelper;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CarService {

    private static CarService carService;

    private SessionFactory sessionFactory;

    private CarService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static CarService getInstance() {
        if (carService == null) {
            carService = new CarService(DBHelper.getSessionFactory());
        }
        return carService;
    }


    public List<Car> getAllCars() {
        List<Car> list = null;
        CarDao dao = new CarDao( sessionFactory.openSession());
        list = dao.getAllCars();
        return list;
    }

    public int getNumberBrand(String brand) {
        List<Car> list = null;
        int number = 0;
        CarDao dao = new CarDao( sessionFactory.openSession());
        list = dao.getCarByBrand(brand);
        if (list != null) {
            number = list.size();
        }
        return number;
    }

    public void addCar(Car car) {
        CarDao dao = new CarDao(sessionFactory.openSession());
        dao.addCar(car);
    }

    public boolean soldCar(Map<String, String> map) {
        return new CarDao(sessionFactory.openSession()).soldCar(map);
    }
}
