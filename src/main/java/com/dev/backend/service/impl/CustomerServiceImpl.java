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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.backend.exceptions.DataNotFoundException;
import com.dev.backend.model.Customer;
import com.dev.backend.service.CustomerService;
import com.dev.backend.service.dao.hibnerateDaoService;

@Service("customerService")
@Transactional
public class CustomerServiceImpl implements CustomerService {
	private static final Logger LOG = Logger
			.getLogger(CustomerServiceImpl.class.getName());
	@Autowired
	private hibnerateDaoService baseDAO;

	@Override
	public List<Customer> findAllCustomers() {
		try {
			return baseDAO.findAll(Customer.class);
		} catch (DataNotFoundException e) {
			LOG.log(Level.WARNING, "No Customers Found");
		}
		return new ArrayList<>();
	}

	@Override
	public Customer findById(Long id) {
		try {
			return baseDAO.findById(Customer.class, id);
		} catch (DataNotFoundException e) {
			LOG.log(Level.WARNING, "No Customer Found For ID " + id);
		}
		return null;
	}

	@Override
	public Customer createCustomer(Customer customer) {
		return baseDAO.create(customer);
	}

	@Override
	public Customer updateCustomer(Customer customer) {
		return baseDAO.update(customer);
	}

	@Override
	public List<Customer> findByName(String name) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", name);
		try {
			return baseDAO
					.findObjectWithNamedQuery(Customer.FindbyName, params);
		} catch (DataNotFoundException e) {
			LOG.log(Level.WARNING, "No Customer Found With Name " + name);
		}
		return new ArrayList<>();
	}

	@Override
	public void delete(Long id) {
		baseDAO.delete(findById(id));
	}

}
