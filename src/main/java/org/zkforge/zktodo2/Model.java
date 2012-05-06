package org.zkforge.zktodo2;

import java.util.List;

public class Model {
	
	protected ReminderService reminderService; 

	public void deleteEvent(Reminder reminder) throws EntityNotFoundException {
		reminderService.delete(reminder);
	}

	public List<Reminder> findAll() {
		return reminderService.findAll();
	}

	public void mergeEvent(Reminder reminder) throws EntityNotFoundException {
		reminderService.merge(reminder);
	}

	public void persistEvent(Reminder reminder) {
		reminderService.persist(reminder);
	}

	public ReminderService getReminderService() {
		return reminderService;
	}

	public void setReminderService(ReminderService reminderService) {
		this.reminderService = reminderService;
	}
	
	protected Reminder selectedReminder = new Reminder();

	public Reminder getSelectedReminder() {
		return this.selectedReminder;
	}
	
	public void setSelectedReminder(Reminder reminder) {
		this.selectedReminder = reminder;
	}
	
	public List<Reminder> getReminders() {
		return this.findAll();
	}
}
