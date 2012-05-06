
package org.zkforge.zktodo2.ui;

import static java.lang.System.out;

import java.util.Date;
import java.util.List;

import org.zkforge.zktodo2.EntityNotFoundException;
import org.zkforge.zktodo2.Model;
import org.zkforge.zktodo2.Reminder;
import org.zkoss.bind.Converter;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

/**
 * This class demonstrates "Supervising Presenter" pattern as the Composer is 
 * not doing all the explicit work of updating the UI. 
 * 
 * {@link http://martinfowler.com/eaaDev/SupervisingPresenter.html}
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class Controller {

	@Wire Textbox name;
	@Wire Intbox priority;
	@Wire Datebox date;
	@Wire Listbox list;

	public Controller(){
		// noop
	}
	
	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view){
	    Selectors.wireComponents(view, this, false);
	}
	
	@WireVariable Model model = null; 

	public void setModel(Model model) {
		this.model = model;
	}

	public Model getModel() {
		return model;
	}

	@Command
	@NotifyChange({"reminders","selectedReminder"})
	public void create() {
		Date dateValue = date.getValue();
		Integer priorityValue = priority.getValue();
		String nameValue = name.getValue();
		if( dateValue != null && priorityValue != null && nameValue != null ){
			Reminder reminder = new Reminder();
			reminder.setDate(date.getValue());
			reminder.setName(name.getValue());
			reminder.setPriority(priority.getValue());
			this.model.persistEvent(reminder);
			List<Reminder> reminders = this.model.findAll();
			ListModel<Reminder> listModel = this.list.getModel();
			ListModelList<Reminder> listModelList = (ListModelList<Reminder>)listModel;
			listModelList.clear();
			listModelList.addAll(reminders);
		}
		return;
	}

	@Command
	@NotifyChange({"reminders","selectedReminder"})
	public void save() {
		Reminder selectedReminder = this.model.getSelectedReminder();
		if( selectedReminder != null ){
			ListModelList<Object> listModelList = (ListModelList<Object>) this.list.getModel();
			try {
				this.model.mergeEvent(selectedReminder);
			} catch (EntityNotFoundException exception){
				int index = list.getSelectedIndex();
				listModelList.remove(index);
				if( listModelList.size() > 0 ){
					selectedReminder = (Reminder)listModelList.get(0);
					list.setSelectedIndex(0);
					name.setValue(selectedReminder.getName());
					date.setValue(selectedReminder.getDate());
					priority.setValue(selectedReminder.getPriority());
				} else {
					selectedReminder = null;
				}
			}
			List<Reminder> reminders = model.findAll();
			listModelList.clear();
			listModelList.addAll(reminders);
		}
	}

	@Command
	@NotifyChange({"reminders","selectedReminder"})
	public void delete(@BindingParam("e") Event e) {
		Reminder selectedReminder = this.model.getSelectedReminder();
		if( null != selectedReminder ){
			ListModel<Reminder> listModel = this.list.getModel();
			ListModelList<Reminder> listModelList = (ListModelList<Reminder>)listModel;
			int index = listModelList.indexOf(selectedReminder);
			try {
				this.model.deleteEvent(selectedReminder);
			} catch (EntityNotFoundException exception ){
				out.println("This is harmless as someone else has already deleted this item.");
			}
			listModelList.remove(selectedReminder);
			if( index >= listModelList.size() ){
				index = listModelList.size() - 1;
			} 
			if( listModelList.size() > 0 && index >= 0 ){
				selectedReminder = (Reminder)listModelList.get(index);
				list.setSelectedIndex(index);
				name.setValue(selectedReminder.getName());
				date.setValue(selectedReminder.getDate());
				priority.setValue(selectedReminder.getPriority());
			} else {
				selectedReminder = null;
			}
		}
	}
	
	Converter dateConverter = new TimestampConverter();

	public Converter getDateConverter() {
		return dateConverter;
	} 
}
