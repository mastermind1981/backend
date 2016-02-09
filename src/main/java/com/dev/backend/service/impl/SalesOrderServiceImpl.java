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
package com.dev.backend.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.backend.exceptions.DataNotFoundException;
import com.dev.backend.model.SalesOrder;
import com.dev.backend.service.SalesOrderService;
import com.dev.backend.service.dao.hibnerateDaoService;

/**
 * @author waleed samy
 *
 */
@Service("salesOrderService")
@Transactional
public class SalesOrderServiceImpl implements SalesOrderService {

	private static final Logger LOG = Logger
			.getLogger(SalesOrderServiceImpl.class.getName());
	@Autowired
	private hibnerateDaoService baseDAO;


	@Override
	public List<SalesOrder> findAllSalesOrders() {
		try {
			return baseDAO.findAll(SalesOrder.class);
		} catch (DataNotFoundException e) {
			LOG.log(Level.WARNING, "No SalesOrder Found");
		}
		return new ArrayList<>();
	}

	@Override
	public SalesOrder updateSalesOrder(SalesOrder salesOrder) {
		return baseDAO.update(salesOrder);
	}

	@Override
	public void deleteSalesOrder(String orderNumber) {
		baseDAO.delete(findByOrderNumber(orderNumber));
	}

	@Override
	public SalesOrder findByOrderNumber(String orderNumber) {
		try {
			return baseDAO.findById(SalesOrder.class, orderNumber);
		} catch (DataNotFoundException e) {
			LOG.log(Level.WARNING, "No SalesOrder Found For ID " + orderNumber);
		}
		return null;
	}

	@Override
	public SalesOrder createSalesOrder(SalesOrder salesOrder) {
		SalesOrder existed = findByOrderNumber(salesOrder.getOrderNumber());
		if (existed != null) {
			return baseDAO.update(salesOrder);
		}
		return baseDAO.create(salesOrder);
	}

}
