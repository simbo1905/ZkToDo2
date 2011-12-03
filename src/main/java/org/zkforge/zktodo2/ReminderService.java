package org.zkforge.zktodo2;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/*
ZK.forge is distributed under Lesser GPL Version see also http://www.gnu.org/licenses/lgpl.html
 */
public class ReminderService {
	protected BasicDao basicDao;

	public BasicDao getBasicDao() {
		return basicDao;
	}

	public void setBasicDao(BasicDao basicDao) {
		this.basicDao = basicDao;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<Reminder> findAll(){
		List<?> events = this.basicDao.findAll(Reminder.class);
		return(List<Reminder>) events;
	}
	
	@Transactional(readOnly=false,propagation = Propagation.REQUIRED)
	public void persist(Reminder reminder){
		this.basicDao.persist(reminder);
	}

	@Transactional(readOnly=false,propagation = Propagation.REQUIRED)
	public void delete(Reminder reminder) throws EntityNotFoundException {
		this.basicDao.remove(Reminder.class, reminder.getId());
	}

	@Transactional(readOnly=false,propagation = Propagation.REQUIRED)
	public void merge(Reminder reminder) throws EntityNotFoundException {
		this.basicDao.merge(reminder);
	}
}
