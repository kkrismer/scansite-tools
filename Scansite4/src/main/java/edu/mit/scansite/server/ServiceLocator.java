package edu.mit.scansite.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import edu.mit.scansite.shared.DatabaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mit.scansite.server.dataaccess.DaoFactory;
import edu.mit.scansite.server.dataaccess.databaseconnector.DbConnector;
import edu.mit.scansite.shared.DataAccessException;

/**
 * @author Thomas Bernwinkler
 * @author Konstantin Krismer
 * @author Tobieh
 */
final public class ServiceLocator {
	private static final String CFG_DIR = "cfg/";
	private static final String DB_ACCESS_FILENAME             = "DatabaseAccess.properties";
	private static final String DB_CONSTANTS_FILENAME          = "DatabaseConstants.properties";
	private static final String DOMAIN_LOCATOR_ACCESS_FILENAME = "DomainLocator.properties";
	private static final String WEB_SERVICE_ACCESS_FILENAME    = "ScansiteDataAccess.properties";
	private static final String UPDATER_CONSTANTS_FILENAME     = "UpdaterConstants.xml";
	private static final String UPDATER_CONSTANTS_DTD_FILENAME = "UpdaterConstants.dtd";

	private static ServiceLocator instance;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private Properties dbAccessProperties = null;
	private Properties dbConstantsProperties = null;
	private Properties webServiceProperties = null;
	private Properties domainLocatorConfigProperties = null;
//	private DbConnector connector;

	private ServiceLocator(){}

	private static void ensureInstance() {
	    if (instance == null) {
	        instance = new ServiceLocator();
        }
    }

	public static Properties getDbAccessProperties() throws DataAccessException {
	    ensureInstance();
		if (instance.dbAccessProperties == null) {
			instance.dbAccessProperties = instance.initPropertiesFile(DB_ACCESS_FILENAME);
		}
		return instance.dbAccessProperties;
	}

	public static Properties getDbConstantsProperties() throws DataAccessException {
	    ensureInstance();
		if (instance.dbConstantsProperties == null) {
			instance.dbConstantsProperties = instance.initPropertiesFile(DB_CONSTANTS_FILENAME);
		}
		return instance.dbConstantsProperties;
	}

	public static Properties getDomainLocatorConfigProperties() throws DataAccessException {
	    ensureInstance();
		if (instance.domainLocatorConfigProperties == null) {
			instance.domainLocatorConfigProperties = instance.initPropertiesFile(DOMAIN_LOCATOR_ACCESS_FILENAME);
		}
		return instance.domainLocatorConfigProperties;
	}

	// START SCANSITE WEB SERVICE SPECIFIC

	private static Properties getConfigFilePathProperties() throws DataAccessException {
	    ensureInstance();
	    if (instance.webServiceProperties == null) {
	        instance.webServiceProperties = instance.initPropertiesFile(WEB_SERVICE_ACCESS_FILENAME);
        }
        return instance.webServiceProperties;
    }

    public static Properties getSvcDbAccessProperties() throws DataAccessException {
	    ensureInstance();
        if (instance.dbAccessProperties == null) {
            String directory = getConfigFilePathProperties().getProperty("SCANSITE_CFG_PATH");
            directory += CFG_DIR;
            instance.dbAccessProperties = instance.initPropertiesFile(directory, DB_ACCESS_FILENAME);
        }
        return instance.dbAccessProperties;
    }

    public static Properties getSvcDbConstantsFile() throws DataAccessException {
		ensureInstance();
        if (instance.dbConstantsProperties == null) {
            String directory = getConfigFilePathProperties().getProperty("SCANSITE_CFG_PATH");
            directory += CFG_DIR;
            instance.dbConstantsProperties = instance.initPropertiesFile(directory, DB_CONSTANTS_FILENAME);
        }
        return instance.dbConstantsProperties;
    }

    // END SCANSITE WEB SERVICE SPECIFIC

	public static InputStream getUpdaterConstantsFileProperties() throws DataAccessException {
		return instance.initConfigFileStream(UPDATER_CONSTANTS_FILENAME);
	}

	public static InputStream getUpdaterConstantsDTDFileProperties()
			throws DataAccessException {
		return instance.initConfigFileStream(UPDATER_CONSTANTS_DTD_FILENAME);
	}

    private InputStream initConfigFileStream(String file)
            throws DataAccessException {
        try {
            return Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream(CFG_DIR + file);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }


//	public static  DbConnector getConnector() throws DatabaseException {
//		ensureInstance();
//        if (instance.connector == null) {
//            instance.connector = DbConnector.getInstance();
//            (getDbAccessProperties());
//        }
//        return instance.connector;
//	}


//	public static DbConnector getWebServiceConnector() throws DatabaseException {
//		ensureInstance();
//        if (instance.connector == null) {
//            instance.connector = new DbConnector(getSvcDbAccessProperties());
//        }
//        return instance.connector;
//	}

	/**
	 * @return A DaoFactory.
	 * @throws DataAccessException Exception for missing configuration file
	 */
	public static DaoFactory getDaoFactory() throws DataAccessException {
		try {
			Properties accConfig = getDbAccessProperties();
			Properties constConfig = getDbConstantsProperties();
			return new DaoFactory(accConfig, constConfig);
		} catch (Exception e) {
            String path = new File(DB_ACCESS_FILENAME).getAbsolutePath();
            throw new DataAccessException("Configuration-files can not be accessed at '"
                    + path.substring(0, path.lastIndexOf('/')) + "/'!\n"	+ e.getMessage());
		}
	}

	/**
     * @return A DaoFactory for the Scansite Web Service
     * @throws DataAccessException Exception for missing configuration file
     */
	public static DaoFactory getSvcDaoFactory() throws DataAccessException {
        try {
            Properties accConfig = getSvcDbAccessProperties();
            Properties constConfig = getSvcDbConstantsFile();
            return new DaoFactory(accConfig, constConfig);
        } catch (Exception e) {
            String path = new File(DB_ACCESS_FILENAME).getAbsolutePath();
            throw new DataAccessException("Configuration-files can not be accessed at '"
                    + path.substring(0, path.lastIndexOf('/')) + "/'!\n"	+ e.getMessage());
        }
    }


	private Properties initPropertiesFile(String file) throws DataAccessException {
        return initPropertiesFile(CFG_DIR, file);
    }

	private Properties initPropertiesFile(String directory, String file) throws DataAccessException {
		Properties p = new Properties();
		InputStream fin;
		try {
			fin = Thread.currentThread().getContextClassLoader().getResourceAsStream(directory + file);
			if (fin == null) { // Web Service uses sources outside the classpath
			    fin = new FileInputStream(directory + file);
			}
			p.load(fin);
			fin.close();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DataAccessException(e.getMessage(), e);
		}
		return p;
	}

//	private InputStream initConfigFileStream(String file)
//			throws DataAccessException {
//		InputStream fin;
//		try {
//			fin = Thread.currentThread().getContextClassLoader()
//					.getResourceAsStream(CFG_DIR + file);
//			if (fin == null) { // try with backslashes
//				fin = Thread.currentThread().getContextClassLoader()
//						.getResourceAsStream(BACKSLASH_DIR + file);
//				logger.info("config resource file retrieval: used backslash-containing path");
//			} else {
//				logger.info("config resource file retrieval: used slash-containing path");
//			}
//			return fin;
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//			throw new DataAccessException(e.getMessage(), e);
//		}
//	}
}
