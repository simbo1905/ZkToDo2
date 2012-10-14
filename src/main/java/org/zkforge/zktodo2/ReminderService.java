package org.zkforge.zktodo2;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
/**
 * A trivial transactional business service. In a real app a service may use 
 * many repositories (DAOs) and may use many helper services.  
 */
@Named("reminderService")
public class ReminderService {
	@Inject
	protected ReminderRepository reminderRepository;

	@Transactional(readOnly=true)
	public List<Reminder> findAll(){
		return this.reminderRepository.findAll();
	}
	
	@Transactional(readOnly=false,propagation = Propagation.REQUIRED)
	public void persist(Reminder reminder){
		this.reminderRepository.save(reminder);
	}

	@Transactional(readOnly=false,propagation = Propagation.REQUIRED)
	public void delete(Reminder reminder)  {
		this.reminderRepository.delete(reminder);
	}


}
