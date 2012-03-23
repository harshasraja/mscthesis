/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.thesis.api.solution4.dao;

import harsha.api.connection.CloudConnector;

/**
 *
 * @author jcrada
 */
public class MetadataDAO extends BaseDAO {

    public MetadataDAO() throws Exception{
        super(CloudConnector.getMetadataConnection(), null);
    }
    
    public MetadataDAO(ValidationHandler handler) throws Exception{
        super(CloudConnector.getMetadataConnection(), handler);
    }
}
