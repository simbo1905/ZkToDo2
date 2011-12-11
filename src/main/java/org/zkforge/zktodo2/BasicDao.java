/*
 * Copyright 2002-2008 the original author or authors:
 * http://loom.extrema-sistemas.com/
 * http://sourceforge.net/projects/loom/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.zkforge.zktodo2;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

/**
 * Common persistence methods
 * @author icoloma
 */
@Repository
public class BasicDao {

	private EntityManager entityManager;

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/**
	 * Retrieves a non-paged query. Use with care, this method could potentially
	 * instantiate large amounts of data.
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> find(String queryString, Object... params) {
		Query query = entityManager.createQuery(queryString);
		setParameters(query, params);
		return query.getResultList();
	}

	/**
	 * @return a single object that satisfies the query.
	 * @throws NoResultException
	 *             if there is no result
	 * @throws NonUniqueResultException
	 *             if more than one result
	 * @throws IllegalStateException
	 *             if called for a Java Persistence query language UPDATE or
	 *             DELETE statement
	 */
	public Object findSingle(String queryString, Object... params) {
		Query query = entityManager.createQuery(queryString);
		setParameters(query, params);
		return query.getSingleResult();
	}

	/**
	 * Sets all the parameters of a query
	 */
	private void setParameters(Query query, Object... params) {
		if (params != null) {
			for (int i = 0; i < params.length; i++)
				query.setParameter(i + 1, params[i]);
		}
	}

	public <T> T merge(T entity) {
		return entityManager.merge(entity);
	}

	public void persist(Object entity) {
		entityManager.persist(entity);
	}

	/**
	 * Removes a persistent instance
	 * 
	 * @param <T>
	 *            The persistent class
	 * @param clazz
	 *            The persistent class
	 * @param id
	 *            the primary key to remove
	 * @return the removed instance
	 */
	public <T> T remove(Class<T> clazz, Serializable id)
			throws EntityNotFoundException {
		T instance = find(clazz, id);
		entityManager.remove(instance);
		return instance;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> findAll(Class<T> clazz) {
		return (List<T>) find("from " + clazz.getName());
	}

	public <T> T find(Class<T> clazz, Serializable id)
			throws EntityNotFoundException {
		T result = entityManager.find(clazz, id);
		if (result == null) {
			throw new EntityNotFoundException(clazz, id);
		}
		return result;
	}

	public void flush() {
		entityManager.flush();
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void refresh(Object entity) {
		entityManager.refresh(entity);
	}

	/**
	 * Retrieves a non-paged query
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> findNamedQuery(final String namedQuery, Object... params) {
		Query query = entityManager.createNamedQuery(namedQuery);
		setParameters(query, params);
		return query.getResultList();
	}

	/**
	 * @return a single object that satisfies the named query.
	 * @throws NoResultException
	 *             if there is no result
	 * @throws NonUniqueResultException
	 *             if more than one result
	 * @throws IllegalStateException
	 *             if called for a Java Persistence query language UPDATE or
	 *             DELETE statement
	 */
	public Object findNamedQuerySingle(String namedQuery, Object... params) {
		Query query = entityManager.createNamedQuery(namedQuery);
		setParameters(query, params);
		return query.getSingleResult();
	}

	public int bulkUpdate(String queryString, Object... params) {
		Query query = entityManager.createQuery(queryString);
		setParameters(query, params);
		return query.executeUpdate();
	}
}
