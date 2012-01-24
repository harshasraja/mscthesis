package harsha.thesis.api.solution2.dao;

import harsha.thesis.api.solution2.entity.Metadata;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class MetadataDAO extends BaseDAO {

	public MetadataDAO(String driverClassName, String connectionString) throws Exception {
		super(driverClassName, connectionString);
		
	}
	
	public List<Metadata> getMetadata(String type) throws Exception{
		
		return null;
		//return getMetadataFromString(super.read(type, "-1"));
	}
	
	
	private List<Metadata> getMetadataFromString(String metadataStringRepresentation) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException{
		Method [] methods = Metadata.class.getDeclaredMethods();
		StringTokenizer st = new StringTokenizer(metadataStringRepresentation, "{");
		List<String> tempMeta = new LinkedList<String>();
		List<String> tempMeta1 = new LinkedList<String>();
		List<Metadata> tempdta = new LinkedList<Metadata>();
		while(st.hasMoreElements()){
			String str = (String)st.nextElement();
			if ( str.contains("{") ||
					str.contains("};")){
				str.replace("{", "");
				str.replace("}", "");
			}
			tempMeta.add(str);
		}
		for (String string : tempMeta) {
			StringTokenizer st1 = new StringTokenizer(string,";");
			Metadata data = Metadata.class.newInstance();
			while (st1.hasMoreElements()) {
				String str = (String) st1.nextElement();
				//tempMeta1.add(str);
				st = new StringTokenizer(str,":");
				try{
					String columnName = (String)st.nextElement();
					String value = (String)st.nextElement();
					if (null != value &&
							"}".equals(value)) {
						value="";
					}
					for (Method method : methods) {
						if (method.getName().contains(columnName) && 
								method.getName().equals("set"+columnName)) {
							method.invoke(data, value);
						}
					}
				} catch (NoSuchElementException e) {
					logger.debug(e.getMessage(), e);
				}

			}
			tempdta.add(data);
		}
		return tempdta;
	}
}
