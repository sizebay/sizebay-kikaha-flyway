package sizebay.kikaha.flywaydb;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.sql.DataSource;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import org.flywaydb.core.Flyway;

import com.typesafe.config.Config;

@Slf4j
@Singleton
@ToString( exclude = { "dataSource", "config" } )
public class FlywayDBProducer {

	@Inject
	Config config;

	@Inject
	DataSource dataSource;

	boolean baselineOnMigrate;
	String baselineVersion;
	String sqlMigrationSuffix;
	String sqlMigrationPrefix;
	String sqlMigrationSeparator;

	@PostConstruct
	public void loadConfiguration() {
		log.debug( "Loading FlywayDB configuration" );
		final Config flywayConf = config.getConfig( "server.flyway" );
		baselineOnMigrate = flywayConf.getBoolean( "baseline-on-migrate" );
		baselineVersion = flywayConf.getString( "baseline-version" );
		sqlMigrationSuffix = flywayConf.getString( "sql-migration-suffix" );
		sqlMigrationPrefix = flywayConf.getString( "sql-migration-prefix" );
		sqlMigrationSeparator = flywayConf.getString( "sql-migration-separator" );
	}

	@Produces
	public Flyway produceAFlywayInstance() {
		log.debug( "Creating Flyway instance for " + dataSource );

		final Flyway flyway = new Flyway();
		flyway.setDataSource( dataSource );
		flyway.setBaselineOnMigrate( baselineOnMigrate );
		flyway.setBaselineVersionAsString( baselineVersion );
		flyway.setSqlMigrationSuffix( sqlMigrationSuffix );
		flyway.setSqlMigrationPrefix( sqlMigrationPrefix );
		flyway.setSqlMigrationSeparator( sqlMigrationSeparator );

		log.debug( "Created flyway configured with: " + this );

		return flyway;
	}
}
