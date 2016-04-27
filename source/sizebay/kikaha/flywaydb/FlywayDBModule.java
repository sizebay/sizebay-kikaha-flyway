package sizebay.kikaha.flywaydb;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.undertow.Undertow;
import kikaha.core.DeploymentContext;
import kikaha.core.modules.Module;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import org.flywaydb.core.Flyway;

import java.io.IOException;

@Slf4j
@Singleton
public class FlywayDBModule implements Module {

	@Getter
	final String name = "flyway";

	@Inject
	Flyway flyway;

	@Override
	public void load(Undertow.Builder builder, DeploymentContext deploymentContext) throws IOException {
		log.info( "Starting database migration" );
		flyway.migrate();
		log.info( "Migration done!" );
	}
}
