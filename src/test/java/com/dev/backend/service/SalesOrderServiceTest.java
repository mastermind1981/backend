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
package com.dev.backend.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.dev.backend.exceptions.DataNotFoundException;
import com.dev.backend.model.Customer;
import com.dev.backend.model.SalesOrder;
import com.dev.backend.service.dao.hibnerateDaoService;
import com.github.javafaker.Faker;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseSetups;

/**
 * @author waleed samy
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring-config.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		TransactionalTestExecutionListener.class,
		DbUnitTestExecutionListener.class })
@DatabaseSetups({ @DatabaseSetup(value = "customers-entries.xml"),
		@DatabaseSetup(value = "product-entries.xml"),
		@DatabaseSetup(value = "sales-orders-entries.xml"),
		@DatabaseSetup(value = "order-lines-entries.xml") })
public class SalesOrderServiceTest {

	private static Faker faker = new Faker();
	private static final String rubbish = faker.letterify(" so");

	@Autowired
	private hibnerateDaoService baseDAO;

	@Test
	public void findAllSalesOrdersTest() throws DataNotFoundException {
		List<SalesOrder> searchResults = baseDAO.findAll(SalesOrder.class);
		assertThat(searchResults, hasSize(5));
	}

	@Test
	@Ignore
	public void findByOrderNumberTest() {

	}

	@Test
	public void createSalesOrderTest() {

	}

	@Test
	@Ignore
	public void updateSalesOrderTest() {

	}

	@Test
	@Ignore
	public void deleteSalesOrderTest() {

	}
}