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

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author: waleed samy <waleedsamy634@gmail.com>
 */
@Entity
@Table(name = "order_line")
@XmlRootElement
@NamedQueries({
		@NamedQuery(name = "OrderLine.findAll", query = "SELECT o FROM OrderLine o"),
		@NamedQuery(name = "OrderLine.findById", query = "SELECT o FROM OrderLine o WHERE o.id = :id"),
		@NamedQuery(name = "OrderLine.findByCreatedAt", query = "SELECT o FROM OrderLine o WHERE o.createdAt = :createdAt"),
		@NamedQuery(name = "OrderLine.findByUpdatedAt", query = "SELECT o FROM OrderLine o WHERE o.updatedAt = :updatedAt"),
		@NamedQuery(name = "OrderLine.findByQuantity", query = "SELECT o FROM OrderLine o WHERE o.quantity = :quantity") })
public class OrderLine implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@XmlTransient
	@Column(name = "id")
	private Long id;
	@Column(name = "created_at")
	@XmlTransient
	@Temporal(TemporalType.DATE)
	private Date createdAt;
	@XmlTransient
	@Column(name = "updated_at")
	@Temporal(TemporalType.DATE)
	private Date updatedAt;
	@Column(name = "quantity")
	private Integer quantity;
	@JoinColumn(name = "product_id", referencedColumnName = "code")
	@ManyToOne(optional = false)
	private Product productId;
	@JoinColumn(name = "sales_order", referencedColumnName = "order_number")
	@ManyToOne
	private SalesOrder salesOrder;

	public OrderLine() {
	}

	public OrderLine(Long id) {
		this.id = id;
	}

	public OrderLine(Product product, Integer quantity) {
		this.productId = product;
		this.quantity = quantity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Product getProductId() {
		return productId;
	}

	public void setProductId(Product productId) {
		this.productId = productId;
	}

	public SalesOrder getSalesOrder() {
		return salesOrder;
	}

	public void setSalesOrder(SalesOrder salesOrder) {
		this.salesOrder = salesOrder;
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
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof OrderLine)) {
			return false;
		}
		OrderLine other = (OrderLine) object;
		if ((this.id == null && other.id != null)
				|| (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.dev.backend.model.OrderLine[ id=" + id + " ]";
	}

}
