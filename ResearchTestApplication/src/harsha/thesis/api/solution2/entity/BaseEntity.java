package harsha.thesis.api.solution2.entity;

import harsha.thesis.api.solution2.entity.Metadata;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

public abstract class BaseEntity {

    protected Logger logger = Logger.getLogger(this.getClass().getName());
    protected String keyForUpdate;
    protected List<Metadata> metaData;
    protected String metadataStringRepresentation;

    public String getKeyForUpdate() {
        return keyForUpdate;
    }

    public abstract void setKeyForUpdate(String keyForUpdate);

    public abstract String getColumnFamilyRepresentation();

    public abstract boolean isNull();

    public String getMetadataStringRepresentation() {
        if (metaData != null
                && null == metadataStringRepresentation
                && "".equals(metadataStringRepresentation)
                && metadataStringRepresentation.length() <= 1) {
            for (Metadata mdata : metaData) {
                metadataStringRepresentation = metadataStringRepresentation + "|" + "ConstraintName:" + mdata.getConstraintName()
                        + ";KeySpace:" + mdata.getKeySpace()
                        + ";ConstraintType:" + mdata.getConstraintType()
                        + ";TableName:" + mdata.getTableName()
                        + ";RKeySpace:" + mdata.getRKeySpace()
                        + ";RConstraintName:" + mdata.getRConstraintName()
                        + ";RColumn:" + mdata.getRColumn()
                        + ";DeleteRule:" + mdata.getDeleteRule()
                        + "|;";
            }
        }
        return metadataStringRepresentation;
    }

    public List<Metadata> getMetaData() {
        return metaData;
    }

    public void setMetaData(List<Metadata> metaData) {
        this.metaData = metaData;
    }

    public void setMetadataStringRepresentation(String metadataStringRepresentation) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException {
        this.metadataStringRepresentation = metadataStringRepresentation;
        Method[] methods = Metadata.class.getDeclaredMethods();

        StringTokenizer st = new StringTokenizer(metadataStringRepresentation, "|");
        List<String> tempMeta = new LinkedList<String>();
        List<String> tempMeta1 = new LinkedList<String>();
        List<Metadata> tempdta = new LinkedList<Metadata>();
        while (st.hasMoreElements()) {
            String str = (String) st.nextElement();
            if (str.contains("|")
                    || str.contains(";")) {
                str.replace("|", "");
                str.replace(";", "");
            }

            //str.length()>1 to handle rouge ; elements
            if (null != str
                    && !"".equals(str.trim())
                    && str.length() > 1) {
                tempMeta.add(str);
            }
        }
        for (String string : tempMeta) {
            StringTokenizer st1 = new StringTokenizer(string, ";");
            Metadata data = Metadata.class.newInstance();
            while (st1.hasMoreElements()) {
                String str = (String) st1.nextElement();
                //tempMeta1.add(str);
                st = new StringTokenizer(str, ":");
                try {
                    String columnName = (String) st.nextElement();
                    String value = (String) st.nextElement();
                    if (null != value
                            && "|".equals(value)) {
                        value = "";
                    }
                    for (Method method : methods) {
                        if (method.getName().contains(columnName)
                                && method.getName().equals("set" + columnName)) {
                            method.invoke(data, value);
                        }
                    }
                } catch (NoSuchElementException e) {
                    logger.debug(e.getMessage(), e);
                }

            }
            tempdta.add(data);
        }
        this.metaData = tempdta;
    }
}
