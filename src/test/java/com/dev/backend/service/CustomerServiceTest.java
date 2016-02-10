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

import java.util.List;

import org.junit.Assert;
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
import com.dev.backend.service.dao.hibnerateDaoService;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring-config.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		TransactionalTestExecutionListener.class,
		DbUnitTestExecutionListener.class })
@DatabaseSetup("customers-entries.xml")
public class CustomerServiceTest {

	@Autowired
	private hibnerateDaoService baseDAO;

	@Test
	public void findAllCustomersTest() throws DataNotFoundException {
		List<Customer> searchResults = baseDAO.findAll(Customer.class);
		Assert.assertEquals(searchResults.size(), 5);
	}

	@Test
	@Ignore
	public void findByCodeTest() {

	}

	@Test
	@Ignore
	public void deleteTest() {

	}

	@Test
	@Ignore
	public void createCustomerTest() {

	}

	@Test
	@Ignore
	public void updateCustomerTest() {

	}

}
