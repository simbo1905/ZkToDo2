/*
ZK.forge is distributed under Lesser GPL Version see also http://www.gnu.org/licenses/lgpl.html
 */
package org.zkforge.zktodo2;

import static java.lang.System.out;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 * This class demonstrates "Model-View-Presenter" pattern as the Composer is 
 * doing all the explicit work of updating the UI. 
 * 
 * see http://martinfowler.com/eaaDev/uiArchs.html
 * 
 * also known as Supervising Controller and Passive View. 
 */
public class ZkToDoControllerV1 extends GenericForwardComposer<Window> implements
		ListitemRenderer<Reminder> {

	private static final long serialVersionUID = -3486059156312322420L;

	public ZkToDoControllerV1() {
	}

	protected ReminderService reminderService;

	public ReminderService getReminderService() {
		return reminderService;
	}

	public void setReminderService(ReminderService reminderService) {
		this.reminderService = reminderService;
	}

	protected Reminder selectedReminder = null;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		listModelList = new ListModelList();
		List<Reminder> reminders = reminderService.findAll();
		listModelList.addAll(reminders);
		list.setModel(listModelList);
		list.setItemRenderer(this);
		list.addEventListener("onSelect", new EventListener(){
			public void onEvent(Event e)
					throws Exception {
				int index = list.getSelectedIndex();
				selectedReminder = (Reminder) listModelList.get(index);
				date.setValue(selectedReminder.getDate());
				priority.setValue(selectedReminder.getPriority());
				name.setValue(selectedReminder.getName());
				return;
			}});
	}

	protected Textbox name;
	protected Intbox priority;
	protected Datebox date;
	protected Listbox list;
	protected ListModelList<Reminder> listModelList;

	public void onClick$add(Event e) {
		Date dateValue = date.getValue();
		Integer priorityValue = priority.getValue();
		String nameValue = name.getValue();
		if( dateValue != null && priorityValue != null && nameValue != null ){
			Reminder reminder = new Reminder();
			reminder.setDate(date.getValue());
			reminder.setName(name.getValue());
			reminder.setPriority(priority.getValue());
			this.reminderService.persist(reminder);
			List<Reminder> reminders = this.reminderService.findAll();
			this.listModelList.clear();
			this.listModelList.addAll(reminders);
			this.selectedReminder = reminder;
		}
	}

	public void onClick$update(Event e) {
		if( selectedReminder != null ){
			selectedReminder.setDate(date.getValue());
			selectedReminder.setPriority(priority.getValue());
			selectedReminder.setName(name.getValue());
			try {
				this.reminderService.merge(selectedReminder);
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
			List<Reminder> reminders = reminderService.findAll();
			listModelList.clear();
			listModelList.addAll(reminders);
			
		}
	}

	public void onClick$delete(Event e) {
		if( null != selectedReminder ){
			int index = listModelList.indexOf(selectedReminder);
			try {
				this.reminderService.delete(selectedReminder);
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

	protected SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");
	
	@Override
	public void render(Listitem listItem, Reminder reminder, int index) throws Exception {
		new Listcell(reminder.getName()).setParent(listItem);
		new Listcell(reminder.getPriority()+"").setParent(listItem);
		new Listcell(dateFormat.format(reminder.getDate())).setParent(listItem);
	}

}
