package dao;

import java.util.List;

import models.Device;
import models.User;

public interface DeviceDAO extends BaseDAO<Device, Integer> {
	public List<Device> findAllByUser(User user);
	public boolean existsCode(String code);
}
