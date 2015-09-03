package modules;

import com.google.inject.AbstractModule;

import dao.*;
import dao.ebean.*;

public class DAOModule extends AbstractModule {
    protected void configure() {
        bind(AccessLevelDAO.class).to(AccessLevelEbeanDAO.class);
        bind(AccessTypeClassifierDAO.class).to(AccessTypeClassifierEbeanDAO.class);
        bind(AccessTypeDAO.class).to(AccessTypeEbeanDAO.class);
        bind(ActionDAO.class).to(ActionEbeanDAO.class);
        bind(CommunicationTypeDAO.class).to(CommunicationTypeEbeanDAO.class);
        bind(DeviceCommunicationDAO.class).to(DeviceCommunicationEbeanDAO.class);
        bind(DeviceDAO.class).to(DeviceEbeanDAO.class);
        bind(DeviceTypeDAO.class).to(DeviceTypeEbeanDAO.class);
        bind(EnvironmentDAO.class).to(EnvironmentEbeanDAO.class);
        bind(EnvironmentFrequencyLevelDAO.class).to(EnvironmentFrequencyLevelEbeanDAO.class);
        bind(EnvironmentTypeDAO.class).to(EnvironmentTypeEbeanDAO.class);
        bind(FrequencyLevelDAO.class).to(FrequencyLevelEbeanDAO.class);
        bind(FunctionalityDAO.class).to(FunctionalityEbeanDAO.class);
        bind(LocalizationTypeDAO.class).to(LocalizationTypeEbeanDAO.class);
        bind(LogEventDAO.class).to(LogEventEbeanDAO.class);
        bind(UserCredentialDAO.class).to(UserCredentialEbeanDAO.class);
        bind(UserDAO.class).to(UserEbeanDAO.class);
        bind(UserEnvironmentDAO.class).to(UserEnvironmentEbeanDAO.class);
        bind(UserProfileEnvironmentDAO.class).to(UserProfileEnvironmentEbeanDAO.class);
        bind(UserTypeDAO.class).to(UserTypeEbeanDAO.class);
    }
}