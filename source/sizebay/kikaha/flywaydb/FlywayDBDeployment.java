package sizebay.kikaha.flywaydb;

import javax.inject.Inject;
import javax.inject.Singleton;

import kikaha.core.api.DeploymentContext;
import kikaha.core.api.DeploymentListener;
import lombok.extern.slf4j.Slf4j;

import org.flywaydb.core.Flyway;

@Slf4j
@Singleton
public class FlywayDBDeployment implements DeploymentListener {

	@Inject
	Flyway flyway;

	@Override
	public void onDeploy( DeploymentContext context ) {
		log.info( "Starting database migration" );
		flyway.migrate();
		log.info( "Migration done!" );
	}

	@Override
	public void onUndeploy( DeploymentContext context ) {
		log.debug( "Nothing to undeploy for Flyway" );
	}
}
