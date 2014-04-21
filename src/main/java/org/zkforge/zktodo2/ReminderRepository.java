package org.zkforge.zktodo2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Here we use spring-data for all the helper logic of working with queries. We 
 * don't provide an implementation of this class as the runtime behaviour is 
 * all provided by spring-data-jpa. 
 * 
 * We should really use a PagingAndSortingRepository but to keep the example simple
 * we will not. 
 * 
 * {@link http://gordondickens.com/wordpress/2011/08/02/adding-queries-to-spring-data-jpa/}
 * @author simbo
 *
 */
public interface ReminderRepository extends JpaRepository<Reminder, Long> {
	@Query("from Reminder where name = ?1")
	Reminder findByName(String name);
}
