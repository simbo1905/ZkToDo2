/*
ZK.forge is distributed under Lesser GPL Version see also http://www.gnu.org/licenses/lgpl.html
 */
package org.zkforge.zktodo2;

import java.util.List;

/**
 * The service is a singleton so stateless. This class wraps holds state for 
 * the user and delegates actions through to the statless service. 
 * @author simbo
 */
public class ZkToDoModelImpl implements ZkToDoModel {
	protected ReminderService reminderService; 

	/* (non-Javadoc)
	 * @see org.zkforge.zktodo2.ZkToDoModel#deleteEvent(org.zkforge.zktodo2.Reminder)
	 */
	public void deleteEvent(Reminder reminder) throws EntityNotFoundException {
		reminderService.delete(reminder);
	}

	/* (non-Javadoc)
	 * @see org.zkforge.zktodo2.ZkToDoModel#findAll()
	 */
	public List<Reminder> findAll() {
		return reminderService.findAll();
	}

	/* (non-Javadoc)
	 * @see org.zkforge.zktodo2.ZkToDoModel#mergeEvent(org.zkforge.zktodo2.Reminder)
	 */
	public void mergeEvent(Reminder reminder) throws EntityNotFoundException {
		reminderService.merge(reminder);
	}

	/* (non-Javadoc)
	 * @see org.zkforge.zktodo2.ZkToDoModel#persistEvent(org.zkforge.zktodo2.Reminder)
	 */
	public void persistEvent(Reminder reminder) {
		reminderService.persist(reminder);
	}

	public ReminderService getReminderService() {
		return reminderService;
	}

	public void setReminderService(ReminderService reminderService) {
		this.reminderService = reminderService;
	}
	
	protected Reminder selectedReminder;

	//used by selectedItem="@{controller.selectedReminder}" and others
	/* (non-Javadoc)
	 * @see org.zkforge.zktodo2.ZkToDoModel#getSelectedReminder()
	 */
	public Reminder getSelectedReminder() {
		return this.selectedReminder;
	}
	
	//used by selectedItem="@{controller.selectedReminder}" and others
	/* (non-Javadoc)
	 * @see org.zkforge.zktodo2.ZkToDoModel#setSelectedReminder(org.zkforge.zktodo2.Reminder)
	 */
	public void setSelectedReminder(Reminder reminder) {
		this.selectedReminder = reminder;
	}
	
	//used by model="@{controller.reminders}"
	/* (non-Javadoc)
	 * @see org.zkforge.zktodo2.ZkToDoModel#getReminders()
	 */
	public List<Reminder> getReminders() {
		return this.findAll();
	}
}
