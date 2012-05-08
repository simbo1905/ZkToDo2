package org.zkforge.zktodo2;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import org.springframework.context.annotation.Scope;

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
