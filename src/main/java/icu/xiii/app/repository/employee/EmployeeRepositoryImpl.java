package icu.xiii.app.repository.employee;

import icu.xiii.app.config.HibernateUtil;
import icu.xiii.app.dto.employee.EmployeeDtoRequest;
import icu.xiii.app.entity.Employee;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class EmployeeRepositoryImpl implements EmployeeRepository {

    public void save(EmployeeDtoRequest request) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Employee employee = new Employee(request);
            session.persist(employee);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public Optional<List<Employee>> getAll() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            List<Employee> employees = session
                    .createQuery("FROM Employee", Employee.class)
                    .getResultList();
            transaction.commit();
            System.out.println(employees);
            return Optional.of(employees);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<Employee> getById(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Employee employee = session.find(Employee.class, id);
            transaction.commit();
            return Optional.ofNullable(employee);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public void update(Long id, EmployeeDtoRequest request) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Employee employee = new Employee(id, request);
            session.merge(employee);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public boolean deleteById(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Optional<Employee> employee = getById(id);
            if (employee.isEmpty()) {
                return false;
            }
            session.remove(employee.get());
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return false;
    }

    public Optional<Employee> getLastEntity() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            TypedQuery<Employee> query = session
                    .createQuery("FROM Employee ORDER BY id DESC", Employee.class);
            query.setMaxResults(1);
            Employee employee = query.getSingleResult();
            transaction.commit();
            return Optional.ofNullable(employee);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            return Optional.empty();
        }
    }
}
