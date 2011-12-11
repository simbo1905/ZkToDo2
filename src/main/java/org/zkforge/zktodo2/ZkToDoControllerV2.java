/*
ZK.forge is distributed under Lesser GPL Version see also http://www.gnu.org/licenses/lgpl.html
 */
package org.zkforge.zktodo2;

import static java.lang.System.out;

import java.util.Date;
import java.util.List;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;


/**
 * This class is more inline with true Model-View-Controller as the Composer
 * class is not actively updating the view; it manipulates models that the 
 * view is observing. 
 * 
 * @author simon
 */
public class ZkToDoControllerV2 extends GenericForwardComposer<Window> {

	private static final long serialVersionUID = 2560535692993939331L;
	protected Textbox name;
	protected Intbox priority;
	protected Datebox date;
	protected Listbox list;

	public ZkToDoControllerV2(){}
	
	protected ZkToDoModel toDoModel = null; 
	
	public ZkToDoModel getToDoModel() {
		return toDoModel;
	}

	public void setToDoModel(ZkToDoModel toDoModel) {
		this.toDoModel = toDoModel;
	}

	public void onClick$add(Event e) {
		Date dateValue = date.getValue();
		Integer priorityValue = priority.getValue();
		String nameValue = name.getValue();
		if( dateValue != null && priorityValue != null && nameValue != null ){
			Reminder reminder = new Reminder();
			reminder.setDate(date.getValue());
			reminder.setName(name.getValue());
			reminder.setPriority(priority.getValue());
			this.toDoModel.persistEvent(reminder);
			List<Reminder> reminders = this.toDoModel.findAll();
			ListModel<Reminder> listModel = this.list.getModel();
			ListModelList<Reminder> listModelList = (ListModelList<Reminder>)listModel;
			listModelList.clear();
			listModelList.addAll(reminders);
		}
		return;
	}

	public void onClick$update(Event e) {
		Reminder selectedReminder = this.toDoModel.getSelectedReminder();
		if( selectedReminder != null ){
			ListModelList<Object> listModelList = (ListModelList<Object>) this.list.getModel();
			try {
				this.toDoModel.mergeEvent(selectedReminder);
			} catch (EntityNotFoundException exception){
				int index = list.getSelectedIndex();
				listModelList.remove(index);
				alert("Reminder "+selectedReminder.getName()+" has been deleted by another user.");
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
			List<Reminder> reminders = toDoModel.findAll();
			listModelList.clear();
			listModelList.addAll(reminders);
		}
	}

	public void onClick$delete(Event e) {
		Reminder selectedReminder = this.toDoModel.getSelectedReminder();
		if( null != selectedReminder ){
			ListModel<Reminder> listModel = this.list.getModel();
			ListModelList<Reminder> listModelList = (ListModelList<Reminder>)listModel;
			int index = listModelList.indexOf(selectedReminder);
			try {
				this.toDoModel.deleteEvent(selectedReminder);
			} catch (EntityNotFoundException exception ){
				out.println("This is harmless as someone else has already deleted this item.");
			}
			listModelList.remove(selectedReminder);
			if( index >= listModelList.size() ){
				index = listModelList.size() - 1;
			} 
			if( listModelList.size() > 0 ){
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
}
