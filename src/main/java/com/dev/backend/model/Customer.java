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

package com.dev.backend.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity
@Table(name = "customer")
@NamedQueries({
		@NamedQuery(name = Customer.FindbyName, query = "from Customer s where s.name LIKE :name"),
		@NamedQuery(name = Customer.FindbyCode, query = "from Customer s where s.code = :code") })
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Customer implements Serializable {
	public static final String FindbyName = "Customer.FindbyName";
	public static final String FindbyCode = "Customer.FindbyCode";

	private static final long serialVersionUID = 1L;
	
	private String code;
	private String name;
	private String address;
	private String phone1;
	private String phone2;
	private Long creditLimit = 0L;
	private Long currentLimit = 0L;
	private Date creationDate = new Date();
	private Date modificationDate = new Date();

	@OneToMany
	@JoinColumn(name = "customer_sales_orders")
	private List<SalesOrder> salesOrders;

	public Customer() {
	}

	public Customer(String code, String name) {
		this.code = code;
		this.name = name;
	}

	@Id
	@Column(name = "code", unique = true, nullable = false)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "name")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column
	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	@Column
	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "created_at", length = 7)
	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "updated_at", length = 7)
	public Date getModificationDate() {
		return this.modificationDate;
	}

	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}

	@Column(name = "credit_limit")
	public Long getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(Long creditLimit) {
		this.creditLimit = creditLimit;
	}

	@Column(name = "currentLimit")
	public Long getCurrentLimit() {
		return currentLimit;
	}

	public void setCurrentLimit(Long currentLimit) {
		this.currentLimit = currentLimit;
	}
}
