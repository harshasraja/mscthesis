/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.api.solutions.vh;

import harsha.api.Constraint;
import harsha.api.Entity;
import harsha.api.EntityManager;
import java.util.List;
import me.prettyprint.hector.api.beans.ColumnSlice;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.SliceQuery;
import org.apache.log4j.Logger;

/**
 *
 * @author harshasraja
 */
public class ValidationHandlerS2 extends MetadataAsText {

    protected static final Logger LOG = Logger.getLogger(ValidationHandlerS2.class);

    public ValidationHandlerS2(EntityManager em) {
        super(em);
    }

    @Override
    public String solution() {
        return "solution2";
    }

    @Override
    public List<Constraint> retrieveMetadata(Entity entity) throws Exception {
        SliceQuery<String, String, String> sliceQuery = em.getConnection().getSliceQuery();
        sliceQuery.setColumnFamily(em.columnFamily(entity.getClass()));
        sliceQuery.setRange("", "", false, 1);
//        LOG.warn("TODO:Check if the size of range is correct, clazz.methods.length");
        sliceQuery.setColumnNames(new String[]{"Metadata"});
        sliceQuery.setKey("-1");

        QueryResult<ColumnSlice<String, String>> result = sliceQuery.execute();
        ColumnSlice<String, String> columnSlice = result.get();
        List<HColumn<String, String>> columns = columnSlice.getColumns();
        String stringOfMetadata = null;
        for (HColumn<String, String> column : columns) {
            if ("Metadata".equals(column.getName())) {
                stringOfMetadata = column.getValue();
            }
        }

        return Constraint.Parse(stringOfMetadata);
    }

    
    
}
