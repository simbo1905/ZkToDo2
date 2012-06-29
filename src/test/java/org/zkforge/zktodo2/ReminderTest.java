package org.zkforge.zktodo2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:dataSourceContext.xml","file:src/main/webapp/WEB-INF/spring/application-context.xml"})
@Transactional
public class ReminderTest {

	@Autowired
	protected DataSource dataSource;

	@Autowired
	protected ReminderRepository reminderRepository;

	@Autowired
	protected ReminderService reminderService;

	@Test
	public void testConfig() throws Exception {
		assertNotNull(dataSource);
		assertNotNull(reminderRepository);
		assertNotNull(reminderService);
	}

	@Test
	public void testReminderRespository() throws Exception {
		Calendar calender = Calendar.getInstance();
		calender.set(1975, 5, 19);
		Reminder reminder = new Reminder("My Birthday", 2, calender.getTime());
		reminderRepository.save(reminder);
		assertNotNull(reminder.getId());
		Reminder event2 = reminderRepository.findByName("My Birthday");
		assertEquals(reminder, event2);
		calender.set(1979, 4, 8);
		Reminder event3 = new Reminder("My Ladies Birthday", 1, calender.getTime());
		reminderRepository.save(event3);
		assertNotNull(event3.getId());
		Reminder event4 = reminderRepository.findByName("My Ladies Birthday");
		assertEquals(event3, event4);
		List<Reminder> reminders = reminderRepository.findAll();
		assertEquals(2, reminders.size());
		boolean foundMyDay = false;
		boolean foundHerDay = false;
		for( Reminder e : reminders ){
			if( e.getName().equals("My Birthday")){
				foundMyDay = true;
			} else if ( e.getName().equals("My Ladies Birthday") ){
				foundHerDay = true;
			}
		}
		assertTrue(foundMyDay);
		assertTrue(foundHerDay);
	}

	@Test
	public void testReminderService() throws Exception {
		Calendar calender = Calendar.getInstance();
		calender.set(1975, 5, 19);
		Reminder reminder = new Reminder("My Birthday 2", 3, calender.getTime());
		reminderService.persist(reminder);
		assertNotNull(reminder.getId());
		calender.set(1979, 4, 8);
		Reminder event2 = new Reminder("My Ladies Birthday 2", 4, calender.getTime());
		reminderService.persist(event2);
		assertNotNull(event2.getId());
		List<Reminder> reminders = reminderService.findAll();
		assertEquals(2, reminders.size());
		boolean foundMyDay = false;
		boolean foundHerDay = false;
		for( Reminder e : reminders ){
			if( e.getName().equals("My Birthday 2")){
				foundMyDay = true;
			} else if ( e.getName().equals("My Ladies Birthday 2") ){
				foundHerDay = true;
			}
		}
		assertTrue(foundMyDay);
		assertTrue(foundHerDay);
	}
}
