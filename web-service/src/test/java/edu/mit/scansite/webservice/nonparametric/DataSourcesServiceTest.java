package edu.mit.scansite.webservice.nonparametric;

import edu.mit.scansite.server.ServiceLocator;
import edu.mit.scansite.server.dataaccess.DaoFactory;
import edu.mit.scansite.shared.DataAccessException;
import edu.mit.scansite.shared.transferobjects.DataSource;
import edu.mit.scansite.webservice.otherservices.DataSourcesService;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 3/14/2017.
 *
 */
public class DataSourcesServiceTest {
    private DataSourcesService service;
    private List<String> dataSourceNames;

    @Before
    public void setup() {
        service = new DataSourcesService();

        dataSourceNames = new ArrayList<>();
        dataSourceNames.add("swissprot");
        dataSourceNames.add("yeast");
        dataSourceNames.add("swissprotorthology");
        dataSourceNames.add("homologene");
        dataSourceNames.add("loctree");
        //you may want to add further ones if you use the full scaled database
    }


    @Test
    public void dataSourcesServiceTest() {
        edu.mit.scansite.webservice.transferobjects.DataSource[] dataSources = service.getDatasources();
        int matches = 0;

        for (edu.mit.scansite.webservice.transferobjects.DataSource dataSource : dataSources) {
            for (String dataSourceName : dataSourceNames) {
                if (dataSource.getShortName().equals(dataSourceName)) {
                    matches++;
                }
            }
        }
        assert (matches == dataSourceNames.size());
    }


    @Test
    public void retriebeDataSourcesTest() throws DataAccessException {
        DaoFactory daoFac = ServiceLocator.getDaoFactory();
        List<edu.mit.scansite.shared.transferobjects.DataSource> dataSources = service.retrieveDataSources(daoFac);

        int matches = 0;
        for (DataSource dataSource : dataSources) {
            for (String dataSourceName : dataSourceNames) {
                if (dataSource.getShortName().equals(dataSourceName)) {
                    matches++;
                }
            }
        }
        assert (matches == dataSourceNames.size());
    }
}
