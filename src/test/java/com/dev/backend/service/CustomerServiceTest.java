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
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;

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
import com.github.javafaker.Faker;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring-config.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		TransactionalTestExecutionListener.class,
		DbUnitTestExecutionListener.class })
@DatabaseSetup("customers-entries.xml")
public class CustomerServiceTest {

	private static Faker faker = new Faker();
	private static final String rubbish = faker.letterify(" cs");

	@Autowired
	private hibnerateDaoService baseDAO;

	@Test
	public void findAllCustomersTest() throws DataNotFoundException {
		List<Customer> searchResults = baseDAO.findAll(Customer.class);
		assertThat(searchResults, hasSize(5));
	}

	@Test
	public void findByCodeTest() throws DataNotFoundException {
		Customer customer = baseDAO.findById(Customer.class, "006282146-1");
		assertThat(customer.getCode(), is("006282146-1"));
		assertThat(customer.getAddress(), is("Apt. 7211"));
		assertThat(customer.getPhone1(), is("1-406-550-3415 x59161"));
		assertThat(customer.getName(), is("ohnathan Rosenbaum"));
		assertThat(customer.getCreditLimit(), is(100.0));
	}

	@Test
	public void deleteTest() throws DataNotFoundException {
		baseDAO.delete(baseDAO.findById(Customer.class, "006282146-1"));
		List<Customer> searchResults = baseDAO.findAll(Customer.class);
		assertThat(searchResults, hasSize(4));
	}

	@Test
	public void createCustomerTest() throws DataNotFoundException {
		String code1 = faker.code().isbn10();
		String name1 = faker.name().name() + rubbish;
		String address = faker.address().secondaryAddress();
		String phone1 = faker.phoneNumber().phoneNumber();
		String creditLimit = faker.numerify("100");

		Customer customer = new Customer(code1, name1);
		customer.setAddress(address);
		customer.setPhone1(phone1);
		customer.setCreditLimit(Double.valueOf(creditLimit));

		Customer result = baseDAO.create(customer);

		assertThat(result,
				is(allOf(notNullValue(), instanceOf(Customer.class))));
		assertThat(baseDAO.findAll(Customer.class), hasSize(6));
		assertThat(baseDAO.findById(Customer.class, code1),
				instanceOf(Customer.class));

	}

	@Test
	public void updateCustomerTest() throws DataNotFoundException {
		String code1 = "006282146-3";
		String name1 = faker.name().name() + rubbish;
		String address = faker.address().secondaryAddress() + rubbish;
		String phone1 = faker.phoneNumber().phoneNumber();
		Double creditLimit = 1000.0;

		Customer customer = new Customer(code1, name1);
		customer.setAddress(address);
		customer.setPhone1(phone1);
		customer.setCreditLimit(creditLimit);

		Customer updated = baseDAO.update(customer);

		assertThat(updated.getName(), is(name1));
		assertThat(updated.getAddress(), is(address));
		assertThat(updated.getPhone1(), is(phone1));
		assertThat(updated.getCreditLimit(), is(creditLimit));

	}

}
