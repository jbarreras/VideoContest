package utilities.repository;

import play.db.jpa.JPA;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@MappedSuperclass
public abstract class Entity {

    private int id = 0;

    public static List findByColumn2(Class pClass, String columna, String filter) {
        List list = null;
        try {
            return JPA.em().createQuery("FROM " + pClass.getName() + " WHERE " + columna + "='" + filter + "'").getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List findAll(Class pClass) {
        List list = null;
        try {
            return JPA.em().createQuery("FROM " + pClass.getName()).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List findByColumn(Class pClass, String filter) {
        List list = null;
        try {
            return JPA.em().createQuery("FROM " + pClass.getName().substring(pClass.getName().lastIndexOf(".") + 1, pClass.getName().length()) + " WHERE " + filter).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @SuppressWarnings("JpaQlInspection")
    public static Object findById(Class pClass, Long id) {
        Object entity = null;
        try {
            entity = JPA.em().createQuery("FROM " + pClass.getName().toString().substring(pClass.getName().toString().lastIndexOf(".") + 1, pClass.getName().toString().length()) + " WHERE id= " + id).getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entity;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void save() {
        try {
            JPA.em().persist(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {
        try {
            JPA.em().merge(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete() {
        try {
            JPA.em().remove(JPA.em().merge(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}