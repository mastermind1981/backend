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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.dev.backend.model.Customer;
import com.dev.backend.service.CustomerService;

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

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.build();
	}

	@Test
	public void findAllCustomersShouldReturnEntries() throws Exception {

		Customer first = new Customer("123", "john smith");
		Customer second = new Customer("345", "john smith the second");

		when(customerServiceMock.findAllCustomers()).thenReturn(
				Arrays.asList(first, second));

		mockMvc.perform(get("/customer/"))
				.andExpect(status().isOk())
				.andExpect(
						content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].code", is("123")))
				.andExpect(jsonPath("$[0].name", is("john smith")))
				.andExpect(jsonPath("$[1].code", is("345")))
				.andExpect(jsonPath("$[1].name", is("john smith the second")));

		verify(customerServiceMock, times(1)).findAllCustomers();
		verifyNoMoreInteractions(customerServiceMock);
	}

	@Test
	@Ignore
	public void findOneCustomersShouldReturnEntries() throws Exception {
	}

	@Test
	@Ignore
	public void deleteCustomersShouldReturnEntries() throws Exception {
	}

	@Test
	@Ignore
	public void createCustomersShouldReturnEntries() throws Exception {
	}

	@Test
	@Ignore
	public void updateCustomersShouldReturnEntries() throws Exception {
	}

}
