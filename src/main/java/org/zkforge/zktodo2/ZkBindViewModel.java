package org.zkforge.zktodo2;

import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;

public class ZkBindViewModel  {

	ZkToDoModel toDoModel = null;
	
	public Reminder getSelectedReminder() {
		return toDoModel.getSelectedReminder();
	}

	public void setSelectedReminder(Reminder reminder) {
		toDoModel.setSelectedReminder(reminder);
	}

	public void setToDoModel(ZkToDoModel toDoModel) {
		this.toDoModel = toDoModel;
	}

	public ListModel<Reminder> getReminders() {
		return new ListModelList<Reminder>(toDoModel.getReminders());
	}

}
