package modules;

import com.google.inject.AbstractModule;

import play.Configuration;
import play.Environment;
import services.IScheduleService;
import services.ISigaiService;
import services.SigaiService;

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
    	
    	bind(ISigaiService.class).to(SigaiService.class);
    }
}