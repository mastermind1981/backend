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

import com.dev.backend.model.OrderLine;
import com.dev.backend.model.Product;
import com.dev.backend.model.SalesOrder;

/**
 * @author waleed samy
 *
 */
public interface SalesOrderService {
	
	public List<SalesOrder> findAllSalesOrders();
	
	public SalesOrder findByOrderNumber(String orderNumber) ;

	public SalesOrder createSalesOrder(SalesOrder salesOrder);
	
	public SalesOrder updateSalesOrder(SalesOrder salesOrder);

	public void deleteSalesOrder(String orderNumber);
}
