/*
ZK.forge is distributed under Lesser GPL Version see also http://www.gnu.org/licenses/lgpl.html
 */
package org.zkforge.zktodo2;

import java.util.Date;
import java.util.List;

public class ZkToDoViewModel {

	public ZkToDoViewModel(){
		this.selectedReminder = new Reminder();
	}
	
	protected Reminder selectedReminder;

	public Reminder getSelectedReminder() {
		return selectedReminder;
	}
	
	public void setSelectedReminder(Reminder reminder) {
		this.selectedReminder = reminder;
		if( this.selectedReminder == null ){
			this.selectedReminder = new Reminder();
		}
	}
	
	protected ReminderService reminderService; 

	public void setReminderService(ReminderService reminderService) {
		this.reminderService = reminderService;
	}

	public List<Reminder> getReminders() {
		return this.reminderService.findAll();
	}
	
	public void delete() {
		if( this.selectedReminder.getId() != null ){
			try {
				this.reminderService.delete(selectedReminder);
				this.selectedReminder = new Reminder();
			} catch (EntityNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	public void update() {
		if( this.selectedReminder.getId() != null ){
			try {
				this.reminderService.merge(selectedReminder);
			} catch (EntityNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void add() {
		if( this.selectedReminder.getId() == null ){
			this.reminderService.persist(this.selectedReminder);
			this.selectedReminder = new Reminder();
		}
	}
	
	/**
	 * The following date method can be called by binder in response to
	 *  
	 *		<datebox id="date" 
	 *			onChange="@{toDoViewModel, converter='org.zkforge.zktodo2.binding.InputEventCommandConverter'}"/>
	 * 
	 * It therefore demonstrates that CommandBinder can use a converter to 
	 * pass state known by the View down to the ViewModel. 
	 * 
	 * As it is our View input components are bound to the properties of the 
	 * ViewModel.seletectedReminder detached JPA entity; so we don't need to be 
	 * passed such state. But for the sake of making an example this vaguely
	 * named method which does nothing shows how you would have components which
	 * are not bound to any Model pass data down into the ViewModel
	 */
	public void date(Date date ){
		System.out.println("The date has changed to "+date); // NOPMD by simbo on 12/10/11 12:07 PM
	}
}
