
package org.zkforge.zktodo2.ui;

import java.util.Date;
import java.util.List;

import org.zkforge.zktodo2.Model;
import org.zkforge.zktodo2.Reminder;
import org.zkforge.zktodo2.ReminderService;
import org.zkoss.bind.Converter;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

/**
 * This class demonstrates "Supervising Presenter" pattern. 
 * 
 * {@link http://martinfowler.com/eaaDev/SupervisingPresenter.html}
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class) // wire with Spring
public class Controller {

	// wired components
	@Wire Textbox name;
	@Wire Intbox priority;
	@Wire Datebox date;
	@Wire Listbox list;

	// wired property 	
	@WireVariable Model model = null; 
	
	public void setModel(Model model) {
		this.model = model;
	}

	public Model getModel() {
		return model;
	}
	
	// wired property
	@WireVariable ReminderService reminderService = null;
	
	public ReminderService getReminderService() {
		return reminderService;
	}

	public void setReminderService(ReminderService reminderService) {
		this.reminderService = reminderService;
	}

	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view){
		Selectors.wireComponents(view, this, false);
		reload();
	}

	/**
	 * @param e The event is unused but is here to indicated that the have access to the screen context
	 */
	@Command
	public void create(@ContextParam(ContextType.TRIGGER_EVENT) Event e) {
		Date dateValue = date.getValue();
		Integer priorityValue = priority.getValue();
		String nameValue = name.getValue();
		if( dateValue != null && priorityValue != null && nameValue != null ){
			Reminder reminder = new Reminder(nameValue, priorityValue, dateValue);
			this.reminderService.persist(reminder);
			this.model.setSelectedReminder(reminder);
			reload();
		}
		return;
	}

	protected void reload() {
		List<Reminder> reminders = this.reminderService.findAll();
		this.model.getReminders().clear();
		this.model.getReminders().addAll(reminders);
	}

	/**
	 * @param e The event is unused but is here to indicated that the have access to the screen context
	 */
	@Command
	public void save(@ContextParam(ContextType.TRIGGER_EVENT) Event e) {
		Reminder selectedReminder = this.model.getSelectedReminder();
		if( selectedReminder != null ){
			try {
				this.reminderService.persist(selectedReminder);
			} catch (Exception exception){
				// not implemented
			}
			reload();
		}
	}

	/**
	 * @param e The event is unused but is here to indicated that the have access to the screen context
	 */
	@Command
	public void delete(@ContextParam(ContextType.TRIGGER_EVENT) Event e) {
		Reminder selectedReminder = this.model.getSelectedReminder();
		if( null != selectedReminder ){
			try {
				this.reminderService.delete(selectedReminder);
			} catch (Exception exception ){
				// not implemented
			}
			this.model.setSelectedReminder(null);
			reload();
		}
	}

}
