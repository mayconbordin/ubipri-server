package forms;

import play.data.validation.Constraints;

import java.util.ArrayList;
import java.util.List;

public class UserDeviceForm {
	@Constraints.Required
    @Constraints.MaxLength(50)
    private String code;
    
    @Constraints.Required
    @Constraints.MaxLength(100)
    private String name;
    
    @Constraints.Required
    @Constraints.MaxLength(100)
    private String deviceType;

	private List<String> functionalities = new ArrayList<>();

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public List<String> getFunctionalities() {
		return functionalities;
	}

	public void setFunctionalities(List<String> functionalities) {
		this.functionalities = functionalities;
	}

	public void addFunctionality(String functionality) {
		functionalities.add(functionality);
	}
}
