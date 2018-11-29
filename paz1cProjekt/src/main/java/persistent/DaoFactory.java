package persistent;

import org.springframework.jdbc.core.JdbcTemplate;
import com.mysql.cj.jdbc.MysqlDataSource;

public enum DaoFactory {
	INSTANCE;

	private JdbcTemplate jdbcTemplate;
	private UserDao userDao;
	private MapLayerDao mapLayerDao;

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
