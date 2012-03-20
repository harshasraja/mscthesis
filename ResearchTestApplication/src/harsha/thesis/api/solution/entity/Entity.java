package harsha.thesis.api.solution.entity;

import harsha.thesis.api.annotation.PrimaryKey;
import java.util.List;
import org.apache.log4j.Logger;

@PrimaryKey(primaryKey = "Id")
public abstract class Entity implements Cloneable {

    protected static final Logger logger = Logger.getLogger(Entity.class);
    protected String id;
    protected String keyForUpdate;
    protected String columnFamily;
    protected List<Metadata> metadata;

    public Entity() {
    }

    public Entity(String columnFamily) {
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

    public void setMetadata(List<Metadata> metadata) {
        this.metadata = metadata;
    }

    public List<Metadata> getMetadata() {
        return this.metadata;
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
