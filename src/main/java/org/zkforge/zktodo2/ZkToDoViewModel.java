package org.zkforge.zktodo2;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.cdi.context.DesktopScoped;
import org.zkoss.zul.ListModelList;

@DesktopScoped
@Named("toDoViewModel3")
public class ZkToDoViewModel implements Serializable {

	private static final long serialVersionUID = -1053875875930621700L;

	public ZkToDoViewModel() {
		java.lang.System.out.println(">>> ZkToDoViewModel <<< ");
	}
	
	@EJB
	protected transient BasicDao reminderService;

	protected ListModelList<Reminder> reminders = new ListModelList<Reminder>();
	
	public ListModelList<Reminder> getReminders() {
		List<Reminder> rs = this.reminderService.findAll(Reminder.class);
		this.reminders.clear();
		this.reminders.addAll(rs);
		return this.reminders;
	}

	protected Reminder selectedReminder = new Reminder();

	public Reminder getSelectedReminder() {
		return this.selectedReminder;
	}
	
	@NotifyChange
	public void setSelectedReminder(Reminder reminder) {
		this.selectedReminder = reminder;
	}
	
	@Command
	@NotifyChange({"reminders","selectedReminder"})
	public void delete() {
		if( this.selectedReminder.getId() != null ){
			try {
				this.reminderService.delete(selectedReminder);
				this.selectedReminder = new Reminder();
			} catch (EntityNotFoundException e) {
				// no doubt someone else deleted it at the same time. nothing to do. 
			}
		}
	}

	@Command
	@NotifyChange({"reminders","selectedReminder"})
	public void save() {
		if( this.selectedReminder.getId() != null ){
				this.reminderService.merge(selectedReminder);
		} else {
			this.reminderService.persist(this.selectedReminder);
		}
	}
	
	@Command
	@NotifyChange({"reminders","selectedReminder"})
	public void create() {
		this.selectedReminder = new Reminder();
	}

}
