package modules;

import com.google.inject.AbstractModule;

import play.Configuration;
import play.Environment;
import services.*;
import services.auth.Authenticator;
import services.auth.SigaiAuthenticator;

public class ServiceModule extends AbstractModule {

    private final Environment environment;
    private final Configuration configuration;

    public ServiceModule(Environment environment, Configuration configuration) {
        this.environment   = environment;
        this.configuration = configuration;
    }

    @SuppressWarnings("unchecked")
	protected void configure() {
    	String scheduleClassName = configuration.getString("service.schedule", "services.MockScheduleService");

    	try {
            Class<IScheduleService> bindingClass = (Class<IScheduleService>)
            		environment.classLoader().loadClass(scheduleClassName);
            bind(IScheduleService.class).to(bindingClass);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    	
    	bind(IClock.class).to(RealClock.class);
    	bind(IPrivacyService.class).to(PrivacyService.class);
    	bind(Authenticator.class).to(SigaiAuthenticator.class);
    }
}