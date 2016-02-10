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
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author: waleed samy <waleedsamy634@gmail.com>
 */
@Entity
@Table(name = "customer")
@XmlRootElement
@NamedQueries({
		@NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c"),
		@NamedQuery(name = Customer.FindbyCode, query = "SELECT c FROM Customer c WHERE c.code = :code"),
		@NamedQuery(name = "Customer.findByAddress", query = "SELECT c FROM Customer c WHERE c.address = :address"),
		@NamedQuery(name = "Customer.findByCreatedAt", query = "SELECT c FROM Customer c WHERE c.createdAt = :createdAt"),
		@NamedQuery(name = "Customer.findByCreditLimit", query = "SELECT c FROM Customer c WHERE c.creditLimit = :creditLimit"),
		@NamedQuery(name = "Customer.findByCurrentLimit", query = "SELECT c FROM Customer c WHERE c.currentLimit = :currentLimit"),
		@NamedQuery(name = "Customer.findByUpdatedAt", query = "SELECT c FROM Customer c WHERE c.updatedAt = :updatedAt"),
		@NamedQuery(name = Customer.FindbyName, query = "SELECT c FROM Customer c WHERE c.name = :name"),
		@NamedQuery(name = "Customer.findByPhone1", query = "SELECT c FROM Customer c WHERE c.phone1 = :phone1"),
		@NamedQuery(name = "Customer.findByPhone2", query = "SELECT c FROM Customer c WHERE c.phone2 = :phone2") })
public class Customer implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String FindbyName = "Customer.findByName";
	public static final String FindbyCode = "Customer.findByCode";

	@Id
	@Basic(optional = false)
	@Column(name = "code")
	private String code;
	@Column(name = "address")
	private String address;
	@Column(name = "created_at")
	@Temporal(TemporalType.DATE)
	private Date createdAt;
	@Column(name = "credit_limit")
	private BigInteger creditLimit;
	@Column(name = "currentLimit")
	private BigInteger currentLimit;
	@Column(name = "updated_at")
	@Temporal(TemporalType.DATE)
	private Date updatedAt;
	@Column(name = "name")
	private String name;
	@Column(name = "phone1")
	private String phone1;
	@Column(name = "phone2")
	private String phone2;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "customerId")
	private List<SalesOrder> salesOrderList;

	public Customer() {
	}

	public Customer(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public BigInteger getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(BigInteger creditLimit) {
		this.creditLimit = creditLimit;
	}

	public BigInteger getCurrentLimit() {
		return currentLimit;
	}

	public void setCurrentLimit(BigInteger currentLimit) {
		this.currentLimit = currentLimit;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	@XmlTransient
	@JsonIgnore
	public List<SalesOrder> getSalesOrderList() {
		return salesOrderList;
	}

	public void setSalesOrderList(List<SalesOrder> salesOrderList) {
		this.salesOrderList = salesOrderList;
	}

	@PrePersist
	private void preInsert() {
		createdAt = new Date();
	}

	@PreUpdate
	private void preUpdate() {
		updatedAt = new Date();
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (code != null ? code.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof Customer)) {
			return false;
		}
		Customer other = (Customer) object;
		if ((this.code == null && other.code != null)
				|| (this.code != null && !this.code.equals(other.code))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.dev.backend.model.Customer[ code=" + code + " ]";
	}

}
