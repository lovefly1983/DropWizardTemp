package hijack.dockerservice;

import hijack.dockerservice.DAO.ImageDAO;
import hijack.dockerservice.model.Template;
import hijack.dockerservice.health.TemplateHealthCheck;
import hijack.dockerservice.resources.*;
import hijack.dockerservice.util.RemoteDockerService;
import hijack.dockerservice.util.SvnInfo;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DockerServiceMainApplication extends Application<DockerServiceMainConfiguration> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DockerServiceMainApplication.class);

    public static void main(String[] args) throws Exception {
        new DockerServiceMainApplication().run(args);
    }

    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void initialize(Bootstrap<DockerServiceMainConfiguration> bootstrap) {
        /*
        bootstrap.addCommand(new RenderCommand());
        bootstrap.addBundle(new AssetsBundle());
        bootstrap.addBundle(new MigrationsBundle<HelloWorldConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(HelloWorldConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });
        bootstrap.addBundle(new ViewBundle());
        */
        bootstrap.addBundle(new ViewBundle());
        bootstrap.addBundle(new AssetsBundle("/assets/", "/assets/"));
    }

    @Override
    public void run(DockerServiceMainConfiguration configuration, Environment environment) {

        LOGGER.info("Run the app...");
        //SvnInfo.init(configuration.getSvnUrl(), configuration.getSvnUser(), configuration.getSvnPwd());
        RemoteDockerService.setDockerServerUrl(configuration.getDockerServerUrl());

        final Template template = configuration.buildTemplate();
        environment.healthChecks().register("template", new TemplateHealthCheck(template));

        // Resources
        environment.jersey().register(new HomeResource("home"));
        environment.jersey().register(new SolrResource(configuration));
        environment.jersey().register(new RegisterResource());

        final DBIFactory factory = new DBIFactory();
        final DBI jdbi;
        try {
            jdbi = factory.build(environment, configuration.getDataSourceFactory(), "mysql");
            final ImageDAO dao = jdbi.onDemand(ImageDAO.class);
            environment.jersey().register(new UploadFileResource(configuration, dao));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
