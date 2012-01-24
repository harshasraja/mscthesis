package harsha.thesis.api.common.handlers;

import harsha.thesis.api.annotation.PrimaryKey;
import harsha.thesis.api.connection.Connection;
import harsha.thesis.api.solution0.entity.BaseEntity;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;

import me.prettyprint.cassandra.model.BasicColumnDefinition;
import me.prettyprint.cassandra.model.BasicColumnFamilyDefinition;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.ThriftCfDef;
import me.prettyprint.hector.api.ddl.ColumnFamilyDefinition;
import me.prettyprint.hector.api.ddl.ColumnIndexType;
import me.prettyprint.hector.api.ddl.ComparatorType;

public class ColumnFamilyCreationHandler {
	
	protected Logger logger = Logger.getLogger(this.getClass().getName());
	
	protected Connection connection = null;
	
	public ColumnFamilyCreationHandler(Connection connection){
		this.connection = connection;
	}
	
	private ColumnFamilyCreationHandler(){
		
	}
	
	private String getPrimaryKeyFieldForEntity(BaseEntity entity){
		String primaryKey = null;
		Annotation [] a1 = entity.getClass().getDeclaredAnnotations();
		for (Annotation annotation : a1) {
			//System.out.println(annotation);
			if (annotation instanceof PrimaryKey) {
				primaryKey = ((PrimaryKey) annotation).primaryKey();
			}
		}
		return primaryKey;
	}
	
	public void createColumFamily(BaseEntity entity){
		
		String primaryKey = getPrimaryKeyFieldForEntity(entity);
		
		
		
		BasicColumnFamilyDefinition columnFamilyDefinition = new BasicColumnFamilyDefinition();
		columnFamilyDefinition.setKeyspaceName(connection.getKeySpace());
		columnFamilyDefinition.setName(entity.getColumnFamilyRepresentation());
		columnFamilyDefinition.setComparatorType(ComparatorType.UTF8TYPE);
		
		//me.prettyprint.cassandra.model.BasicColumnFamilyDefinition cannot be cast to me.prettyprint.cassandra.service.ThriftCfDef
		
		ColumnFamilyDefinition cfDef = new ThriftCfDef(columnFamilyDefinition);
		
		//columnFamilyDefinition = new BasicColumnFamilyDefinition(cfDef);
		
		BasicColumnDefinition columnDefinition = null;
		
		columnDefinition = new BasicColumnDefinition();
		columnDefinition.setName(StringSerializer.get().toByteBuffer(primaryKey));
		columnDefinition.setIndexName(entity.getColumnFamilyRepresentation()+"_"+primaryKey);
	    columnDefinition.setIndexType(ColumnIndexType.valueOf("KEYS"));
	    columnDefinition.setValidationClass("UTF8Type");
	    cfDef.addColumnDefinition(columnDefinition);
	    
	    Method [] methods = entity.getClass().getDeclaredMethods();
		for (Method method : methods) {
			if (!method.getName().contains("set"+primaryKey) && 
					!method.getName().contains("get"+primaryKey) && 
					!method.getName().contains("ColumnFamilyRepresentation") &&
					!method.getName().contains("KeyForUpdate") &&
					method.getName().contains("get")) {
				
				columnDefinition = new BasicColumnDefinition();
				columnDefinition.setName(StringSerializer.get().toByteBuffer(method.getName().substring(3,method.getName().length())));
				//columnDefinition.setValidationClass("UTF8Type");
				columnDefinition.setIndexName(entity.getColumnFamilyRepresentation()+"_"+method.getName().substring(3,method.getName().length()));
				columnDefinition.setIndexType(ColumnIndexType.valueOf("KEYS"));
			    columnDefinition.setValidationClass("UTF8Type");
			    cfDef.addColumnDefinition(columnDefinition);
			}
		}
		
		connection.getCluster().addColumnFamily(cfDef);
	    
	}

}
