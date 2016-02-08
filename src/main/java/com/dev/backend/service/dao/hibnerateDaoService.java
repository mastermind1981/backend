/**
 * Copyright 2016, owale.
 *
 * Licensed under GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.gnu.org/licenses/gpl-3.0.en.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * code is a task implementation for crossover https://crossover.com
 * @author: waleed samy <waleedsamy634@gmail.com>
 **/


package com.dev.backend.service.dao;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dev.backend.exceptions.*;

/**
 * hibnerate based DAO service depend on sessionFactory
 * 
 * @author waleed samy
 * 
 */
@Component("baseDAO")
@Transactional
public class hibnerateDaoService {
	private static final Logger LOG = Logger
			.getLogger(hibnerateDaoService.class.getName());
	@Autowired
	@Qualifier("sessionFactory")
	protected SessionFactory sessionFactory;
	
	private static Session currentSession;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	protected Session getSession() {
		if (currentSession == null) {
			currentSession = sessionFactory.openSession();
		}
		return currentSession;
	}

	/**
	 * Create a given entity in data store
	 * 
	 * @param <T>
	 *            Type of Entity
	 * @return <T> Created Entity
	 */
	public <T> T create(T t) {
		Transaction tx = getSession().beginTransaction();
		getSession().save(t);
		tx.commit();
		return t;
	}

	/**
	 * Updates an existing entity in the database.
	 * 
	 * @param <T>
	 *            Type of entity
	 * @param t
	 *            Entity to update
	 * @return Updated entity
	 */
	public <T> T update(T t) {
		Transaction tx = getSession().beginTransaction();
		getSession().update(t);
		tx.commit();
		return t;
	}

	/**
	 * delete an existing entity in the database.
	 * 
	 * @param <T>
	 *            Entity to be deleted
	 */
	public <T> void delete(T t) {
		Transaction tx = getSession().beginTransaction();
		getSession().delete(t);
		tx.commit();
	}

	/**
	 * Finds a given entity in the data store.
	 * 
	 * @param <T>
	 *            Type of entity
	 * @param type
	 *            Type of entity
	 * @param id
	 *            Unique identifier of the entity
	 * @return Entity matching the unique identifier
	 * @throws DataNotFoundException
	 *             If no match could be found
	 */
	public <T> T findById(Class<T> type, Object id)
			throws DataNotFoundException {
		if (id == null) {
			LOG.log(Level.SEVERE,
					"null is not a valid primary key for " + type.getName());
			throw new DataNotFoundException(
					"null is not a valid primary key for " + type.getName());
		}
		@SuppressWarnings("unchecked")
		T entity = (T) getSession().get(type, (Serializable) id);

		if (entity == null) {
			LOG.log(Level.SEVERE, type.getName() + " with ID " + id
					+ " not found");
			throw new DataNotFoundException(type.getName() + " with ID " + id
					+ " not found");
		}
		return entity;
	}

	/**
	 * Finds all the entities of a given type.
	 * 
	 * @param <T>
	 *            Type of entity
	 * @param type
	 *            Type of entity
	 * @return {@link List} of all entities of the given type
	 */
	public <T> List<T> findAll(Class<T> type) throws DataNotFoundException {
		@SuppressWarnings("unchecked")
		List<T> entities = (List<T>) getSession().createCriteria(type).list();

		if (entities == null) {
			throw new DataNotFoundException(type.getName());
		}
		return entities;
	}

	/**
	 * Finds a range of entities of a given type in a given range.
	 * 
	 * @param <T>
	 *            Type of entity
	 * @param type
	 *            Type of entity
	 * @param start
	 *            First entity to retrieve
	 * @param resultLimit
	 *            Number of entities to retrieve
	 * @return {@link List} of entities of the given type in the given range
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> List<T> findAll(Class<T> type, int start, int resultLimit) {
		return new LinkedList(getSession().createCriteria(type)
				.setFirstResult(start).setMaxResults(resultLimit).list());
	}

	/**
	 * Finds a {@link List} of entity returned by the given {@link Query}.
	 * 
	 * @param query
	 *            {@link Query} to execute
	 * @param parameters
	 *            Parameters of the query
	 * @return {@link List} of entities returned by the {@link Query}
	 */
	public <T> List<T> findWithNamedQuery(String query,
			Map<String, Object> parameters) {
		Query namedQuery = getSession().getNamedQuery(query);
		return findWithQuery(namedQuery, parameters);
	}

	/**
	 * Finds a {@link List} of entity returned by the given {@link Query}.
	 * 
	 * @param query
	 *            {@link Query} to execute
	 * @param parameters
	 *            Parameters of the query
	 * @return {@link List} of entities returned by the {@link Query}
	 */
	public <T> List<T> findWithNamedQuery(String query,
			Map<String, Object> parameters, int start, int resultLimit) {
		Query namedQuery = getSession().getNamedQuery(query);
		return findWithQuery(namedQuery, parameters, start, resultLimit);
	}

	/**
	 * Finds a {@link Object} of entity returned by the given {@link Query}.
	 * 
	 * @param query
	 *            {@link Query} to execute
	 * @param parameters
	 *            Parameters of the query
	 * @return {@link Object} of entity returned by the {@link Query}
	 * @throws DataNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public <T> T findObjectWithNamedQuery(String query,
			Map<String, Object> parameters) throws DataNotFoundException {
		Query namedQuery = getSession().getNamedQuery(query);
		return (T) findObjectWithQuery(namedQuery, parameters);
	}

	/**
	 * Finds a {@link List} of entity returned by the given {@link Query}.
	 * 
	 * @param query
	 *            {@link Query} to execute
	 * @param parameters
	 *            Parameters of the query
	 * @param start
	 *            First record of the result set
	 * @param resultLimit
	 *            Maximum number of results
	 * @return {@link List} of entities returned by the {@link Query}
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> findWithQuery(Query query, Map<String, Object> parameters) {
		Set<Entry<String, Object>> rawParameters = parameters.entrySet();
		setQueryParameters(query, rawParameters);
		return query.list();
	}

	/**
	 * Finds a {@link Object} of entity returned by the given {@link Query}.
	 * 
	 * @param query
	 *            {@link Query} to execute
	 * @param parameters
	 *            Parameters of the query
	 * @return {@link Object} of entities returned by the {@link Query}
	 * @throws DataNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public <T> T findObjectWithQuery(Query query, Map<String, Object> parameters)
			throws DataNotFoundException {
		Set<Entry<String, Object>> rawParameters = parameters.entrySet();
		setQueryParameters(query, rawParameters);
		List entities = query.list();
		if (!entities.isEmpty()) {
			if (entities.size() > 1) {
				LOG.log(Level.INFO,
						"More Objects Found With This Creatria . May Cause Probelm , Try To Get All Objects Instead");

			}
			return (T) entities.get(0);
		} else {
			LOG.log(Level.SEVERE, "No Object Found");
			throw new DataNotFoundException("No Object Found");
		}

	}

	/**
	 * Finds a {@link List} of entity returned by the given {@link Query}.
	 * 
	 * @param query
	 *            {@link Query} to execute
	 * @param parameters
	 *            Parameters of the query
	 * @param start
	 *            First record of the result set
	 * @param resultLimit
	 *            Maximum number of results
	 * @return {@link List} of entities returned by the {@link Query}
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> findWithQuery(Query query,
			Map<String, Object> parameters, int start, int resultLimit) {
		Set<Entry<String, Object>> rawParameters = parameters.entrySet();
		query.setFirstResult(start);
		query.setMaxResults(start + resultLimit);
		setQueryParameters(query, rawParameters);
		return query.list();
	}

	/**
	 * native delete SQL statement
	 * 
	 * @param deletequery
	 *            string of SQL Delete Command To Execute
	 */
	public void deleteNatively(String deletequery) {
		Transaction tx = getSession().beginTransaction();
		getSession().createSQLQuery(deletequery).executeUpdate();
		tx.commit();
	}

	private void setQueryParameters(Query query,
			Set<Entry<String, Object>> parameters) {
		for (Entry<String, Object> param : parameters) {
			query.setParameter(param.getKey(), param.getValue());
		}
	}
}