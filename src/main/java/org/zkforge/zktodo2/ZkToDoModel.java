/*
ZK.forge is distributed under Lesser GPL Version see also http://www.gnu.org/licenses/lgpl.html
 */
package org.zkforge.zktodo2;

import java.util.List;


/**
 * This is an Application Model class as it exposes resource via methods such 
 * as "findAll" yet it also store the uses selection with get/setSelectedItem.  
 * 
 * @author simon
 */
public interface ZkToDoModel {

	public abstract void deleteEvent(Reminder reminder)
			throws EntityNotFoundException;

	public abstract List<Reminder> findAll();

	public abstract void mergeEvent(Reminder reminder)
			throws EntityNotFoundException;

	public abstract void persistEvent(Reminder reminder);

	//used by selectedItem="@{controller.selectedReminder}" and others
	public abstract Reminder getSelectedReminder();

	//used by selectedItem="@{controller.selectedReminder}" and others
	public abstract void setSelectedReminder(Reminder reminder);

	//used by model="@{controller.reminders}"
	public abstract List<Reminder> getReminders();

}