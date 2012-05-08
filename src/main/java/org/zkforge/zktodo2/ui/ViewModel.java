package org.zkforge.zktodo2.ui;

import java.util.ArrayList;
import java.util.List;

import org.zkforge.zktodo2.Reminder;
import org.zkforge.zktodo2.ReminderService;
import org.zkoss.bind.Converter;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class) // wire with Spring
public class ViewModel  {
	
	// wired property
	@WireVariable ReminderService reminderService = null;
	
	public ReminderService getReminderService() {
		return reminderService;
	}

	public void setReminderService(ReminderService reminderService) {
		this.reminderService = reminderService;
	}

	@Init
	public void init(){
		Selectors.wireVariables((Component)null, this, Selectors.newVariableResolvers(getClass(), null));
	}

	protected List<Reminder> reminders = new ArrayList<Reminder>();
	
	public List<Reminder> getReminders() {
		List<Reminder> rs = this.reminderService.findAll();
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
			} catch (Exception e) {
				e.printStackTrace(); // hum. some else deleted this. should really send this up to the user and ask them to reload the page. 
			}
		}
	}

	@Command
	@NotifyChange({"reminders","selectedReminder"})
	public void save() {
		if( this.selectedReminder.getId() != null ){
			try {
				this.reminderService.persist(selectedReminder);
			} catch (Exception e) {
				e.printStackTrace(); // hum. some else deleted this. should really send this up to the user and ask them to reload the page. 
			}
		} else {
			this.reminderService.persist(this.selectedReminder);
		}
	}
	
	@Command
	@NotifyChange({"reminders","selectedReminder"})
	public void create() {
		this.selectedReminder = new Reminder();
	}
	
	Converter<?,?,?> dateConverter = new TimestampConverter();

	public Converter<?,?,?> getDateConverter() {
		return dateConverter;
	} 

}
