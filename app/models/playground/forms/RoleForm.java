package models.playground.forms;

import models.playground.Playground;
import models.playground.Role;
import play.data.validation.Constraints.Pattern;
import play.data.validation.Constraints.Required;

public class RoleForm {
	
	public Long id;
	
	@Required
	public String name;
	
	@Required
	@Pattern(value="^[0-9]+$")
	public String beginAge;
	
	@Required
	@Pattern(value="^[0-9]+$")
	public String endAge;
	
	public Long playgroundId;
	
	public void submit(){
		Role role = Role.create();
		
		id = role.id;
		
		update();
	}

	public void update() {
		Role.initialize(id, name, Long.parseLong(beginAge), Long.parseLong(endAge));
		
		Role.addPlayground(id, playgroundId);		
		
		Playground.addRole(playgroundId, id);
	}

}
