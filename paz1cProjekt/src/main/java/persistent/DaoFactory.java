package persistent;

import org.springframework.jdbc.core.JdbcTemplate;

import com.mysql.cj.jdbc.MysqlDataSource;

public enum DaoFactory {
	INSTANCE;
	
	private JdbcTemplate jdbcTemplate;
	private UserDao userDao;
	//private WorkshopDao workshopDao;
	
	public UserDao getUserDao() {
		if (userDao == null)
			userDao = new MysqlUserDao(getJdbcTemplate());
		return userDao;
	}
	
//	public WorkshopDao getWorkshopDao() {
//		if (workshopDao == null) {
//			workshopDao = new MysqlWorkshopDao(getJdbcTemplate());
//		}
//		return workshopDao;
//	}
	
	private JdbcTemplate getJdbcTemplate() {
		if (jdbcTemplate == null) {
			MysqlDataSource dataSource = new MysqlDataSource();
			dataSource.setUser("ondro");
			dataSource.setPassword("ondro.123");
//			dataSource.setDatabaseName("registracia_itat");
			dataSource.setUrl("jdbc:mysql://localhost/project_scheme?serverTimezone=Europe/Bratislava");
			jdbcTemplate = new JdbcTemplate(dataSource);
		}
		return jdbcTemplate;
	}
}

