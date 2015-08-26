package forms;

import play.data.validation.Constraints;

public class UserLocationForm {
	@Constraints.Required
	public String deviceCode;
	
	@Constraints.Required
	public int environmentId;

	public boolean exiting = false;
	public boolean async = false;

	public UserLocationForm() { }

	public UserLocationForm(String deviceCode, int environmentId) {
		this.deviceCode = deviceCode;
		this.environmentId = environmentId;
	}

	public UserLocationForm(String deviceCode, int environmentId, boolean exiting) {
		this.deviceCode = deviceCode;
		this.environmentId = environmentId;
		this.exiting = exiting;
	}

	public String getDeviceCode() {
		return deviceCode;
	}
	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}
	public int getEnvironmentId() {
		return environmentId;
	}
	public void setEnvironmentId(int environmentId) {
		this.environmentId = environmentId;
	}
	public boolean isExiting() {
		return exiting;
	}
	public void setExiting(boolean exiting) {
		this.exiting = exiting;
	}
	public boolean isAsync() {
		return async;
	}
	public void setAsync(boolean async) {
		this.async = async;
	}
}