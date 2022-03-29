package com.beck;

import com.beck.entity.Department;
import com.beck.entity.Detail;
import com.beck.entity.Employee;
import com.beck.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class App {
    public static void main(String[] args) {
        Detail beckDetails = new Detail("Kadamjay", "+11111", "beck@gmail.com");
        Detail danDetails = new Detail("Moscow", "+2222", "dan@gmail.com");
        Detail uriDetails = new Detail("Aydarken", "+33333", "uri@gmail.com");
        Detail kanatDetails = new Detail("Bishkek", "+4444", "kanat@gmail.com");

        Employee beck = new Employee("Beck", "Ege", 43, 1000, beckDetails);
        Employee daniel = new Employee("Daniel", "Ege", 8, 500, danDetails);
        Employee uri = new Employee("Urunsa", "Baeva", 37, 2000, uriDetails);
        Employee kanat = new Employee("Kanat", "Subanov", 25, 1500, kanatDetails);
        kanatDetails.setEmployee(kanat);

        Department beckDep = new Department("Computer", 2000, 1000);
        beckDep.addEmployeeToDepartment(beck);
        beckDep.addEmployeeToDepartment(daniel);
        beckDep.addEmployeeToDepartment(uri);
        beckDep.addEmployeeToDepartment(kanat);

//        createTableDepartment();
//        saveDepartment(beckDep);

//        save(kanat);

//        createTable();
//        save(beck);
//        save(daniel);
//        save(uri);

//        getAllEmployees();
//        update(4, "Urunsa", "Baeva", "Home", 38,5000);
//        dropTable();

//        deleteById(2);
//        deleteAllData();


        HibernateUtil.shutDown();


    }


    public static void createTableDepartment() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String create_tableDep = "create table if not exists departments" +
                "(id integer not null primary key, " +
                "name varchar(50) not null," +
                "maxSalary integer not null," +
                "minSalary int not null)";
        Query query = session.createSQLQuery(create_tableDep);
        query.executeUpdate();
        transaction.commit();
        session.close();
        System.out.println("Table department successfully created");
    }

    public static int saveDepartment(Department d) {
        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(d);
            session.getTransaction().commit();
            session.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("Added successfully " + d);
        return d.getId();
    }

    public static int save(Detail d) {
        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(d);
            session.getTransaction().commit();
            session.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("Added successfully " + d);
        return d.getId();
    }

    public static void createTable() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            String create_table = "create table if not exists employees" +
                    "(id integer not null primary key, " +
                    "name varchar(50) not null, lastname varchar(50) not null, " +
                    "department varchar(50) not null, age integer not null," +
                    "salary int not null)";
            Query query = session.createSQLQuery(create_table);
            query.executeUpdate();
            transaction.commit();
            session.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Table successfully created");
    }

    public static int save(Employee e) {
        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(e);
            session.getTransaction().commit();
            session.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("Added successfully " + e);
        return e.getId();
    }

    public static void deleteById(int id) {
        Employee employee = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            employee = session.get(Employee.class, id);
            session.delete(employee);
            session.getTransaction().commit();
            session.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("Successfully deleted" + employee);
    }

    public static List<Employee> getAllEmployees() {
        List<Employee> employees = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            employees = session.createQuery("from Employee").list();
            session.getTransaction().commit();
            session.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("Finded " + employees.size() + employees);
        return employees;
    }

//    public static void update(int id, String name, String lastname, String department, int age, int salary) {
//        Session session = HibernateUtil.getSessionFactory().openSession();
//        session.beginTransaction();
//        Employee e = session.get(Employee.class, id);
//        e.setName(name);
//        e.setLastName(lastname);
//        e.setDepartment(department);
//        e.setAge(age);
//        e.setSalary(salary);
//        session.getTransaction().commit();
//        session.close();
//        System.out.println("Successfully updated");
//    }

    public static void dropTable() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            String drop_table = "Drop table if exists employees";
            Query query = session.createSQLQuery(drop_table).addEntity(Employee.class);
            query.executeUpdate();
            session.getTransaction().commit();
            session.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("Table employees successfully deleted");
    }

    public static void deleteAllData() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("delete from Employee");
            query.executeUpdate();
            session.getTransaction().commit();
            session.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("Successfully deleted all data from Employee");
    }


}
