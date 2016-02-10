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
package com.dev.backend.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.dev.backend.model.Customer;
import com.dev.backend.model.OrderLine;
import com.dev.backend.model.SalesOrder;
import com.dev.backend.service.SalesOrderService;
import com.google.gson.Gson;

/**
 * @author waleed samy
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring-config.xml" })
@WebAppConfiguration
public class SalesOrderControllerTest {
	MockMvc mockMvc;

	@Autowired
	WebApplicationContext webApplicationContext;

	@InjectMocks
	@Autowired
	SalesOrderController salesOrderController;

	@Mock
	SalesOrderService salesOrderServiceMock;

	Gson gson = new Gson();

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.build();
	}

	@Test
	public void findAllSalesOrdersShouldReturnEntries() throws Exception {
		SalesOrder first = new SalesOrder("123", 10.5, 200);
		List<OrderLine> orderLines = new ArrayList<OrderLine>();
		OrderLine order = new OrderLine(first, 10);
		orderLines.add(order);

		SalesOrder salesOrder = new SalesOrder("123456", new Customer("123",
				"john smith"), 1000.0, orderLines);

		when(salesOrderServiceMock.findAllSalesOrders()).thenReturn(
				Arrays.asList(salesOrder));

		mockMvc.perform(get("/order/"))
				.andExpect(status().isOk())
				.andExpect(
						content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].orderNumber", is("123456")))
				.andExpect(jsonPath("$[0].totalPrice", is(1000.0)))
				.andExpect(jsonPath("$[0].customer.code", is("123")))
				.andExpect(jsonPath("$[0].customer.name", is("john smith")))
				.andExpect(jsonPath("$.orderLines", hasSize(1)))
				.andExpect(jsonPath("$.orderLines[0].SalesOrder.code", is("123")))
				.andExpect(jsonPath("$.orderLines[0].SalesOrder.price", is(10.5)))
				.andExpect(jsonPath("$.orderLines[0].quantity", is(10)));
	}

	@Test
	public void findOneSalesOrderShouldReturnEntries() throws Exception {
		SalesOrder first = new SalesOrder("UYTG", 10.5, 200);
		List<OrderLine> orderLines = new ArrayList<OrderLine>();
		OrderLine order = new OrderLine(first, 10);
		orderLines.add(order);

		SalesOrder salesOrder = new SalesOrder("120125", new Customer("SRDRR",
				"john smith"), 1000.0, orderLines);

		when(salesOrderServiceMock.findByOrderNumber("120125")).thenReturn(
				salesOrder);

		mockMvc.perform(get("/order/120125"))
				.andExpect(status().isOk())
				.andExpect(
						content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.orderNumber", is("120125")))
				.andExpect(jsonPath("$.totalPrice", is(1000.0)))
				.andExpect(jsonPath("$.customer.name", is("john smith")))
				.andExpect(jsonPath("$.orderLines", hasSize(1)))
				.andExpect(jsonPath("$.orderLines[0].SalesOrder.code", is("UYTG")))
				.andExpect(jsonPath("$.orderLines[0].SalesOrder.price", is(10.5)))
				.andExpect(jsonPath("$.orderLines[0].quantity", is(10)));
	}

	@Test
	public void crearteSalesOrdersShouldReturn() throws Exception {
		
		SalesOrder salesOrder = new SalesOrder("120125", new Customer("SRDRR",
				"john smith"), 1000.0, new ArrayList<OrderLine>());
		
		when(salesOrderServiceMock.findByOrderNumber("120125")).thenReturn(
				salesOrder);
		System.out.println(gson.toJson(salesOrder));
		mockMvc.perform(
				MockMvcRequestBuilders.post(("/order/"))
						.accept(MediaType.APPLICATION_JSON_VALUE)
						.header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
						.content(gson.toJson(salesOrder)))
				.andExpect(status().isOk())
				.andExpect(
						content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.orderNumber", is("120125")))
				.andExpect(jsonPath("$.totalPrice", is(1000.0)))
				.andExpect(jsonPath("$.customer.name", is("john smith")))
				.andExpect(jsonPath("$.orderLines", hasSize(1)))
				.andExpect(jsonPath("$.orderLines[0].SalesOrder.code", is("UYTG")))
				.andExpect(jsonPath("$.orderLines[0].SalesOrder.price", is(10.5)))
				.andExpect(jsonPath("$.orderLines[0].quantity", is(10)));
	}

	@Test
	@Ignore
	public void deleteSalesOrderShouldReturnEntries() throws Exception {
	}

	@Test
	@Ignore
	public void updateSalesOrderShouldReturnEntries() throws Exception {
	}

}
