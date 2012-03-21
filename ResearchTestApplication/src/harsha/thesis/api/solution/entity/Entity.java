package harsha.thesis.api.solution.entity;

import harsha.thesis.api.annotation.Column;
import harsha.thesis.api.annotation.PrimaryKey;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public abstract class Entity implements Cloneable {

    protected static final Logger LOG = Logger.getLogger(Entity.class);
    protected String keyForUpdate;

    @Column(columnName = "Metadata")
    protected String metadata;

    public Entity() {
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public String getKeyForUpdate() {
        return this.keyForUpdate;
    }

    public void setKeyForUpdate(String keyForUpdate) {
        this.keyForUpdate = keyForUpdate;
    }

    public boolean isNull() {
        String id = GetValue(GetPrimaryKey(this.getClass()), this);
        return id == null || id.isEmpty();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return (Object) super.clone();
    }

    @Override
    public String toString() {
        String result = GetName(this.getClass()) + "[";
        result += "PK=" + GetPrimaryKey(this.getClass());

//        for (Field field : this.getClass().getFields()) {
        for (String column : GetAllColumnsFor(this.getClass())) {
            result += ", " + column + "=" + GetValue(column, this);
        }

        result += "]";
        return result;
    }

    public static String GetPrimaryKey(Class<? extends Entity> clazz) {
        PrimaryKey key = clazz.getAnnotation(PrimaryKey.class);
        return key.primaryKey();
    }

    public static String GetName(Class<? extends Entity> clazz) {
        return clazz.getSimpleName();
    }

    public static String GetValue(String column, Entity entity) {
        String result = null;
        try {
            Method getter = entity.getClass().getMethod("get" + column);
            result = (String) getter.invoke(entity);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return result;
    }

    public static void SetValue(String column, String value, Entity entity) {
        try {
            Method setter = entity.getClass().getMethod("set" + column, String.class);
            setter.invoke(entity, value);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static List<String> GetAllColumnsFor(Class<? extends Entity> clazz) {
        List<String> result = new ArrayList<String>();
        Class parent = clazz;
        do {
            System.out.println(parent.getSimpleName());
            for (Field field : parent.getDeclaredFields()) {
                Column column = field.getAnnotation(Column.class);
                if (column != null) {
                    result.add(column.columnName());
                }
            }
            parent = parent.getSuperclass();
        } while (Entity.class.isAssignableFrom(parent));
        return result;
    }

    public static void main(String[] args) {
        Entity[] entities = {new Student(), new Course(), new Enrolment(), new Constraint()};
        for (Entity entity : entities) {
            System.out.println("Entity: " + entity);
        }

        Student student = new Student();
        student.setFirstName("Jane");
        student.setLastName("Smith");
        student.setAge("28");
        student.setEmail("jane.smith@email.com");
        student.setStudentId("JS28");
        System.out.println(student);


        for (String column : GetAllColumnsFor(Student.class)) {
            SetValue(column, "'" + column + "'", student);
            System.out.println(column);
        }

//        System.out.println("Methods:");
//        for (Method method : Student.class.getDeclaredMethods()) {
//            System.out.println(method.toString());
//        }
//
//        System.out.println("Fields");
//        for (Field field : Student.class.getFields()) {
//            System.out.println(field.toString());
//        }

        System.out.println("***");
        System.out.println(Student.class.getSuperclass());


    }
}
