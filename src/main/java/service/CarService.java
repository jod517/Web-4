package service;

import DAO.CarDao;
import model.Car;
import org.hibernate.SessionFactory;
import util.DBHelper;

import java.util.LinkedList;
import java.util.List;

public class CarService {

    private static CarService carService;

    private CarDao carDao;

    private CarService() {
        carDao = new CarDao();
    }

    public static CarService getInstance() {
        if (carService == null) {
            carService = new CarService();
        }
        return carService;
    }


    public List<Car> getAllCars() {
        List allCarList = new LinkedList();
        allCarList = carDao.getAllCars();
        return allCarList;
    }
}
