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

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dev.backend.model.Customer;
import com.dev.backend.model.OrderLine;
import com.dev.backend.model.Product;
import com.dev.backend.model.SalesOrder;
import com.dev.backend.model.Status;
import com.dev.backend.service.SalesOrderService;
import com.google.gson.Gson;

/**
 * @author waleed samy
 *
 */
@Controller
@RequestMapping(value = "/order", produces = MediaType.APPLICATION_JSON_VALUE)
public class SalesOrderController {

	@Autowired
	SalesOrderService salesOrderService;

	static final Logger LOG = Logger.getLogger(SalesOrderController.class);
	Gson gson = new Gson();
	

	@RequestMapping(value = { "/", "/list" }, method = RequestMethod.GET)
	public @ResponseBody List<SalesOrder> getAllSalesOrders() {
		return salesOrderService.findAllSalesOrders();
	}

	@RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody SalesOrder addSalesOrder(@RequestBody SalesOrder order) {
		LOG.info(gson.toJson(order));
		return salesOrderService.createSalesOrder(order);
	}

	@RequestMapping(value = "/{orderNumber}", method = RequestMethod.GET)
	public @ResponseBody SalesOrder getSalesOrder(
			@PathVariable("orderNumber") String orderNumber) {
		return salesOrderService.findByOrderNumber(orderNumber);
	}

	@RequestMapping(value = "/{orderNumber}", method = RequestMethod.DELETE)
	public @ResponseBody Status deleteSalesOrder(
			@PathVariable("orderNumber") String orderNumber) {
		salesOrderService.deleteSalesOrder(orderNumber);
		return new Status(1, "done");
	}
}
