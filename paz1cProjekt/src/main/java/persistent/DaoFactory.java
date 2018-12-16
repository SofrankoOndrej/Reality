package persistent;

import org.springframework.jdbc.core.JdbcTemplate;
import com.mysql.cj.jdbc.MysqlDataSource;

public enum DaoFactory {
	// nazov instancie
	INSTANCE;

	private JdbcTemplate jdbcTemplate;
	private UserDao userDao;
	private MapLayerDao mapLayerDao;
	private TileDao tileDao;
	private PropertyDao propertyDao;
	private AddressDao addressDao;

	
	public AddressDao getAddressDao() {
		if (addressDao == null)
			addressDao = new MysqlAddressDao(getJdbcTemplate());
		return addressDao;
	}
	
	public PropertyDao getPropertyDao() {
		if (propertyDao == null)
			propertyDao = new MysqlPropertyDao(getJdbcTemplate());
		return propertyDao;
	}
	
	public UserDao getUserDao() {
		if (userDao == null)
			userDao = new MysqlUserDao(getJdbcTemplate());
		return userDao;
	}

	public MapLayerDao getMapLayerDao() {
		if (mapLayerDao == null) {
			mapLayerDao = new MysqlMapLayerDao(getJdbcTemplate());
		}
		return mapLayerDao;
	}
	
	public TileDao getTileDao() {
		if(tileDao == null) {
			tileDao = new MysqlTileDao(getJdbcTemplate());
		}
		return tileDao;
	}
	
	private JdbcTemplate getJdbcTemplate() {
		if (jdbcTemplate == null) {
			MysqlDataSource dataSource = new MysqlDataSource();
			dataSource.setUser("ondro");
			dataSource.setPassword("ondro.123");
			dataSource.setDatabaseName("project_scheme");
			dataSource.setUrl("jdbc:mysql://localhost/project_scheme?serverTimezone=Europe/Bratislava");
			jdbcTemplate = new JdbcTemplate(dataSource);
		}
		return jdbcTemplate;
	}

	

}
