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
import com.dev.backend.model.Product;
import com.dev.backend.service.ProductService;
import com.dev.backend.service.dao.hibnerateDaoService;

@Service("productService")
@Transactional
public class ProductServiceImpl implements ProductService {
	private static final Logger LOG = Logger.getLogger(ProductServiceImpl.class
			.getName());
	@Autowired
	private hibnerateDaoService baseDAO;

	@Override
	public List<Product> findAllProducts() {
		try {
			return baseDAO.findAll(Product.class);
		} catch (DataNotFoundException e) {
			LOG.log(Level.WARNING, "No products Found");
		}
		return new ArrayList<>();
	}

	@Override
	public Product createProduct(Product product) {
		Product existed = findByCode(product.getCode());
		if (existed != null) {
			return baseDAO.update(product);
		}
		return baseDAO.create(product);
	}

	@Override
	public Product updateProduct(Product product) {
		return baseDAO.update(product);
	}

	@Override
	public Product findByCode(String code) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("code", code);
		try {
			return baseDAO.findObjectWithNamedQuery(Product.FindbyCode, params);
		} catch (DataNotFoundException e) {
			LOG.log(Level.WARNING, "No Product Found With Code " + code);
		}
		return null;
	}

	@Override
	public void delete(String code) {
		Product product = findByCode(code);
		if (product != null) {
			baseDAO.delete(findByCode(code));
		}
	}

}