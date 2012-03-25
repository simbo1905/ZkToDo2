package org.zkforge.zktodo2;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Named("reminderService")
public class ReminderService {
	@Inject
	@Named("basicDao")
	protected BasicDao basicDao;

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<Reminder> findAll(){
		List<?> events = this.basicDao.findAll(Reminder.class);
		return(List<Reminder>) events;
	}
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void persist(Reminder reminder){
		this.basicDao.persist(reminder);
	}

	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void delete(Reminder reminder) throws EntityNotFoundException {
		this.basicDao.remove(Reminder.class, reminder.getId());
	}

	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void merge(Reminder reminder) throws EntityNotFoundException {
		this.basicDao.merge(reminder);
	}
}
