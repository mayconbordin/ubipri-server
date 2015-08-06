package dao.ebean;

import java.util.List;

import com.avaje.ebean.Ebean;

import dao.DeviceDAO;
import models.Device;
import models.User;

public class DeviceEbeanDAO extends BaseEbeanDAO<Device, Integer> implements DeviceDAO {
	public List<Device> findAllByUser(User user) {
		List<Device> devices = Ebean.find(Device.class)
			 .fetch("deviceType")
		     .where().eq("user", user)
		     .findList();
		
		return devices;
	}
	
	public boolean existsCode(String code) {
		int count = Ebean.find(getPersistentClass())
			.where().eq("code", code)
			.findRowCount();
		
		return (count != 0);
	}
}
