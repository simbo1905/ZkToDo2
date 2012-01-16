package org.zkforge.zktodo2;

import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.validator.AbstractValidator;

public class ReminderValidator extends AbstractValidator {

	public void validate(ValidationContext ctx) {
		String name = (String)ctx.getProperties("name")[0].getValue();
		Integer priority = (Integer)ctx.getProperties("priority")[0].getValue();
		
		if(name == null || "".equals(name))
			this.addInvalidMessage(ctx, "name", "You must enter a name");		
		
		if(ctx.getProperties("date")[0].getValue() == null)
			this.addInvalidMessage(ctx, "date", "You must specify a date");
		
		if(priority == null || priority < 1 || priority > 10)
			this.addInvalidMessage(ctx, "priority", "You must give a priority > 0 && < 10");
	}
}
