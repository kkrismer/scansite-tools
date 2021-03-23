package edu.mit.scansite.webservice.otherservices;

import edu.mit.scansite.server.ServiceLocator;
import edu.mit.scansite.server.dataaccess.DaoFactory;
import edu.mit.scansite.server.dataaccess.DataSourceDao;
import edu.mit.scansite.shared.DataAccessException;
import edu.mit.scansite.webservice.WebService;
import edu.mit.scansite.webservice.exception.ScansiteWebServiceException;
import edu.mit.scansite.webservice.transferobjects.DataSource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * @author Tobieh
 * @author Thomas Bernwinkler
 * */

@Path("/datasources")
public class DataSourcesService extends WebService {
    /**
     * @return An object containing all available datasources/databases (each of which has a name and a nickname).
     * @throws ScansiteWebServiceException Unable to access database
     */
    @GET
    @Produces({MediaType.APPLICATION_XML})
    public DataSource[] getDatasources() {
        try {
            DaoFactory factory = ServiceLocator.getDaoFactory();
            List<edu.mit.scansite.shared.transferobjects.DataSource> ds = retrieveDataSources(factory);
            if (ds != null && ds.size() > 0) {
                DataSource[] dataSources = new DataSource[ds.size()];
                for (int i = 0; i < ds.size(); ++i) {
                    dataSources[i] = new DataSource(ds.get(i).getDisplayName(), ds.get(i).getShortName(), ds.get(i).getVersion(), ds.get(i).getDescription(),
                            factory.getProteinDao().getProteinCount(ds.get(i)));
                }
                return dataSources;
            }
            return new DataSource[]{};
        } catch (DataAccessException e) {
            throw new ScansiteWebServiceException("Server can not access database. " + TXT_TRY_LATER);
        }
    }

    public List<edu.mit.scansite.shared.transferobjects.DataSource> retrieveDataSources(DaoFactory daoFac) throws DataAccessException {
            final boolean primaryDataSourceOnly = true;
            DataSourceDao dsDao = daoFac.getDataSourceDao();
            return dsDao.getAll(primaryDataSourceOnly);
    }
}
