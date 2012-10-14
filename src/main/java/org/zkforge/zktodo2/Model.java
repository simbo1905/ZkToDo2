package org.zkforge.zktodo2;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import org.springframework.context.annotation.Scope;

/**
 * This class is used by the SupervisingController example. The Model is 
 * observed by the binder which updates the screen. The Controller mediates 
 * the system services and pushes entities into the Model. The Model therefore 
 * holds the users conversational state with the system. 
 * 
 * {@see supervisingcontroller.zul}
 * {@see org.zkforge.zktodo2.ui.Controller}
 */
@Scope("desktop")
@Named("model")
public class Model {
	
	public Model(){
		//noop
	}
	
	protected Reminder selectedReminder = new Reminder();

	public Reminder getSelectedReminder() {
		return this.selectedReminder;
	}
	
	public void setSelectedReminder(Reminder reminder) {
		this.selectedReminder = reminder;
	}

	private List<Reminder> reminders = new ArrayList<Reminder>();
	
	public List<Reminder> getReminders() {
		return this.reminders;
	}
}
