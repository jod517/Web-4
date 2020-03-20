package DAO;

import model.Car;
import model.DailyReport;
import service.DailyReportService;
import util.DayReportBuffer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

public class DailyReportDao {

    private Session session;

    public DailyReportDao(Session session) {
        this.session = session;
    }

    public List<DailyReport> getAllDailyReport() {
        Transaction transaction = session.beginTransaction();
        List<DailyReport> dailyReports = session.createQuery("FROM DailyReport").list();
        transaction.commit();
        session.close();
        return dailyReports;
    }


    public void updateReport(Long earnings, Long soldCars) {
        List<DailyReport> list = DailyReportService.getInstance().getAllDailyReports();
        Transaction transaction = null;
        try {
            if (list.size() != 0) {
                DailyReport dailyReport = list.get(list.size() - 1);
                dailyReport.setEarnings(dailyReport.getEarnings() + earnings);
                dailyReport.setSoldCars(dailyReport.getSoldCars() + soldCars);
                transaction = session.beginTransaction();
                session.update(dailyReport);
                transaction.commit();
            } else {
                transaction = session.beginTransaction();
                session.save(new DailyReport(earnings, soldCars));
                transaction.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }

    }
}
