package harsha.thesis.api.solution.entity;

import harsha.thesis.api.annotation.PrimaryKey;
import org.apache.log4j.Logger;

@PrimaryKey(primaryKey = "Id")
public abstract class BaseEntity implements Cloneable {

    protected Logger logger = Logger.getLogger(this.getClass().getName());
    protected String id;
    protected String keyForUpdate;
    private String columnFamily;

    public BaseEntity() {
    }

    public BaseEntity(String columnFamily) {
        this.columnFamily = columnFamily;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getColumnFamily() {
        return columnFamily;
    }

    public void setColumnFamily(String columnFamily) {
        this.columnFamily = columnFamily;
    }

    public String getKeyForUpdate() {
        return this.keyForUpdate;
    }

    public void setKeyForUpdate(String keyForUpdate) {
        this.keyForUpdate = keyForUpdate;
    }

    public boolean isNull() {
        PrimaryKey k = this.getClass().getAnnotation(PrimaryKey.class);
        return k == null || k.primaryKey().isEmpty();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return (Object) super.clone();
    }
}
