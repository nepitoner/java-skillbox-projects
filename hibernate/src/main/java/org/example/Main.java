package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();

        Session session = sessionFactory.openSession();

        String hql = "SELECT DISTINCT sb.studentId, sb.courseId " +
                "FROM Subscription sb " +
                "WHERE sb.studentId IN (" +
                "    SELECT st.id " +
                "    FROM Student st " +
                "    JOIN PurchaseList pl ON pl.studentName = st.name" +
                ") AND sb.courseId IN (" +
                "    SELECT cr.id " +
                "    FROM Course cr " +
                "    JOIN PurchaseList pl ON pl.courseName = cr.name)";

        List<Object[]> resList = session.createQuery(hql).list();

        for (Object[] result : resList) {
            Transaction tx = session.beginTransaction();
            PurchaseListKey purchaseListKey = new PurchaseListKey((int)result[0], (int)result[1]);
            LinkedPurchaseList linkedPurchaseList = new LinkedPurchaseList();
            linkedPurchaseList.setId(purchaseListKey);
            linkedPurchaseList.setStudentId(purchaseListKey.getStudentId());
            linkedPurchaseList.setCourseId(purchaseListKey.getCourseId());

            System.out.println(linkedPurchaseList.getStudentId() + " " + linkedPurchaseList.getCourseId());
            session.save(linkedPurchaseList);
            tx.commit();
        }
        sessionFactory.close();
    }
}