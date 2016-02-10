/**
 * Copyright 2016, owale.
 *
 * Licensed under GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007; you may
 * not use this file except in compliance with the License. You may obtain a
 * copy of the License at
 *
 * http://www.gnu.org/licenses/gpl-3.0.en.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 * code is a task implementation for crossover https://crossover.com
 *
 * @author: waleed samy <waleedsamy634@gmail.com>
 *
 */
package com.dev.backend.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.dev.backend.model.Customer;
import com.dev.backend.service.CustomerService;
import com.dev.backend.service.impl.CustomerServiceImpl;
import com.github.javafaker.Faker;
import com.google.gson.Gson;

/**
 * @author waleed samy
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring-config.xml" })
@WebAppConfiguration
public class CustomerControllerTest {

	MockMvc mockMvc;

	@Autowired
	WebApplicationContext webApplicationContext;

	@Mock
	CustomerService customerServiceMock;

	@InjectMocks
	@Autowired
	CustomerController customerController;

	private static Faker faker = new Faker();
	private static Gson gson = new Gson();

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.build();
	}

	@Test
	public void findAllCustomersShouldReturnEntries() throws Exception {

		String code1 = faker.code().isbn10();
		String name1 = faker.name().name();
		String code2 = faker.code().isbn10();
		String name2 = faker.name().name();
		Customer first = new Customer(code1, name1);
		Customer second = new Customer(code2, name2);

		when(customerServiceMock.findAllCustomers()).thenReturn(
				Arrays.asList(first, second));

		mockMvc.perform(get("/customer/"))
				// .andDo(printResponse)
				.andExpect(status().isOk())
				.andExpect(
						content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].code", is(code1)))
				.andExpect(jsonPath("$[0].name", is(name1)))
				.andExpect(jsonPath("$[1].code", is(code2)))
				.andExpect(jsonPath("$[1].name", is(name2)));

		verify(customerServiceMock, times(1)).findAllCustomers();
		verifyNoMoreInteractions(customerServiceMock);
	}

	@Test
	public void findOneCustomersShouldReturnEntries() throws Exception {
		String code1 = faker.code().isbn10();
		String name1 = faker.name().name();

		Customer first = new Customer(code1, name1);

		when(customerServiceMock.findByCode(code1)).thenReturn(first);

		mockMvc.perform(get("/customer/" + code1))
				// .andDo(printResponse)
				.andExpect(status().isOk())
				.andExpect(
						content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$code", is(code1)))
				.andExpect(jsonPath("$name", is(name1)));

		verify(customerServiceMock, times(1)).findByCode(code1);
		verifyNoMoreInteractions(customerServiceMock);

	}

	@Test
	public void deleteCustomersShouldReturnEntries() throws Exception {
		String code = faker.code().isbn10();

		CustomerService customerService = new CustomerServiceImpl();
		CustomerService spy = Mockito.spy(customerService);
		Mockito.doNothing().when(spy).delete(code);

		mockMvc.perform(delete("/customer/" + code))
		// .andDo(printResponse))
				.andExpect(status().isOk());

		verify(customerServiceMock, times(1)).delete(code);
		verifyNoMoreInteractions(customerServiceMock);

	}

	@Test
	public void createCustomersShouldReturnEntries() throws Exception {

		String code1 = faker.code().isbn10();
		String name1 = faker.name().name();
		Customer first = new Customer(code1, name1);

		when(customerServiceMock.createCustomer(first)).thenReturn(first);

		mockMvc.perform(
				MockMvcRequestBuilders
						.post(("/customer/"))
						.accept(MediaType.APPLICATION_JSON_VALUE)
						.header("Content-Type",
								MediaType.APPLICATION_JSON_VALUE)
						.content(gson.toJson(first)))
				// .andDo(printResponse)
				.andExpect(status().isOk())
				.andExpect(
						content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.name", is(name1)))
				.andExpect(jsonPath("$.code", is(code1)));

		verify(customerServiceMock, times(1)).createCustomer(first);
		verifyNoMoreInteractions(customerServiceMock);
	}

	@Test
	public void updateCustomersShouldReturnEntries() throws Exception {

		String code1 = faker.code().isbn10();
		String name1 = faker.name().name();
		String address = faker.address().secondaryAddress();
		String phone1 = faker.phoneNumber().phoneNumber();
		String creditLimit = faker.numerify("100");

		Customer first = new Customer(code1, name1);
		first.setAddress(address);
		first.setPhone1(phone1);
		first.setCreditLimit(Double.valueOf(creditLimit));

		when(customerServiceMock.updateCustomer(first)).thenReturn(first);

		mockMvc.perform(
				MockMvcRequestBuilders
						.post(("/customer/" + code1))
						.accept(MediaType.APPLICATION_JSON_VALUE)
						.header("Content-Type",
								MediaType.APPLICATION_JSON_VALUE)
						.content(gson.toJson(first)))
				// .andDo(printResponse)
				.andExpect(status().isOk())
				.andExpect(
						content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.name", is(name1)))
				.andExpect(jsonPath("$.code", is(code1)))
				.andExpect(
						jsonPath("$.creditLimit",
								is(Double.valueOf(creditLimit))))
				.andExpect(jsonPath("$.address", is(address)))
				.andExpect(jsonPath("$.phone1", is(phone1)));

		verify(customerServiceMock, times(1)).updateCustomer(first);
		verifyNoMoreInteractions(customerServiceMock);

	}

	/**
	 * Print reposnse handler
	 */
	ResultHandler printResponse = new ResultHandler() {
		@Override
		public void handle(MvcResult result) throws Exception {
			System.out.println(result.getResponse().getContentAsString());
		}
	};

}
