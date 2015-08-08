package hijack.dockerservice;

import hijack.dockerservice.model.Template;
import hijack.dockerservice.health.TemplateHealthCheck;
import hijack.dockerservice.resources.*;
import hijack.dockerservice.util.RemoteDockerService;
import hijack.dockerservice.util.SvnInfo;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

public class DockerServiceMainApplication extends Application<DockerServiceMainConfiguration> {
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

        //SvnInfo.init(configuration.getSvnUrl(), configuration.getSvnUser(), configuration.getSvnPwd());
        RemoteDockerService.setDockerServerUrl(configuration.getDockerServerUrl());

        final Template template = configuration.buildTemplate();
        environment.healthChecks().register("template", new TemplateHealthCheck(template));

        environment.jersey().register(new JobResource());
        environment.jersey().register(new SvnInfoResource());
        environment.jersey().register(new ComponentResource());
        environment.jersey().register(new HomeResource("home"));
        environment.jersey().register(new SolrResource(configuration));
        environment.jersey().register(new UploadFileResource());
        environment.jersey().register(new RegisterResource());
    }
}
