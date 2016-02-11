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

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "sales_order")
@XmlRootElement
@NamedQueries({
		@NamedQuery(name = "SalesOrder.findAll", query = "SELECT s FROM SalesOrder s"),
		@NamedQuery(name = "SalesOrder.findByOrderNumber", query = "SELECT s FROM SalesOrder s WHERE s.orderNumber = :orderNumber"),
		@NamedQuery(name = "SalesOrder.findByCreatedAt", query = "SELECT s FROM SalesOrder s WHERE s.createdAt = :createdAt"),
		@NamedQuery(name = "SalesOrder.findByUpdatedAt", query = "SELECT s FROM SalesOrder s WHERE s.updatedAt = :updatedAt"),
		@NamedQuery(name = "SalesOrder.findByTotalPrice", query = "SELECT s FROM SalesOrder s WHERE s.totalPrice = :totalPrice") })
public class SalesOrder implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Basic(optional = false)
	@Column(name = "order_number")
	private String orderNumber;
	@Column(name = "created_at")
	@XmlTransient
	@Temporal(TemporalType.DATE)
	private Date createdAt;
	@Column(name = "updated_at")
	@Temporal(TemporalType.DATE)
	@XmlTransient
	private Date updatedAt;
	@Column(name = "total_price")
	private Double totalPrice = 0.0;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "salesOrder", orphanRemoval = true)
	private List<OrderLine> orderLineList;
	@JoinColumn(name = "customer_id", referencedColumnName = "code")
	@ManyToOne(optional = false)
	private Customer customerId;

	public SalesOrder() {
	}

	public SalesOrder(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public SalesOrder(String orderNumber, Customer customerId,
			Double totalPrice, List<OrderLine> orderLineList) {
		this.orderNumber = orderNumber;
		this.customerId = customerId;
		this.totalPrice = totalPrice;
		this.orderLineList = orderLineList;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	@XmlTransient
	@JsonIgnore
	public List<OrderLine> getOrderLineList() {
		return orderLineList;
	}

	public void setOrderLineList(List<OrderLine> orderLineList) {
		this.orderLineList = orderLineList;
	}

	public Customer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Customer customerId) {
		this.customerId = customerId;
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
		hash += (orderNumber != null ? orderNumber.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof SalesOrder)) {
			return false;
		}
		SalesOrder other = (SalesOrder) object;
		if ((this.orderNumber == null && other.orderNumber != null)
				|| (this.orderNumber != null && !this.orderNumber
						.equals(other.orderNumber))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.dev.backend.model.SalesOrder[ orderNumber=" + orderNumber
				+ " ]";
	}

}
