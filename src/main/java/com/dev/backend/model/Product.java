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
@Table(name = "product")
@XmlRootElement
@NamedQueries({
		@NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p"),
		@NamedQuery(name = Product.FindbyCode, query = "SELECT p FROM Product p WHERE p.code = :code"),
		@NamedQuery(name = "Product.findByCreatedAt", query = "SELECT p FROM Product p WHERE p.createdAt = :createdAt"),
		@NamedQuery(name = "Product.findByDescription", query = "SELECT p FROM Product p WHERE p.description = :description"),
		@NamedQuery(name = "Product.findByUpdatedAt", query = "SELECT p FROM Product p WHERE p.updatedAt = :updatedAt"),
		@NamedQuery(name = "Product.findByPrice", query = "SELECT p FROM Product p WHERE p.price = :price"),
		@NamedQuery(name = "Product.findByQuantity", query = "SELECT p FROM Product p WHERE p.quantity = :quantity") })
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String FindbyCode = "Product.findByCode";

	@Id
	@Basic(optional = false)
	@Column(name = "code")
	private String code;
	@Column(name = "created_at")
	@Temporal(TemporalType.DATE)
	private Date createdAt;
	@Column(name = "description")
	private String description;
	@Column(name = "updated_at")
	@Temporal(TemporalType.DATE)
	private Date updatedAt;
	@Column(name = "price")
	private Double price = 0.0;
	@Column(name = "quantity")
	private Integer quantity;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "productId")
	private List<OrderLine> orderLineList;

	public Product() {
	}

	public Product(String code, Double price, Integer quantity) {
		this.code = code;
		this.price = price;
		this.quantity = quantity;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@XmlTransient
	@JsonIgnore
	public List<OrderLine> getOrderLineList() {
		return orderLineList;
	}

	public void setOrderLineList(List<OrderLine> orderLineList) {
		this.orderLineList = orderLineList;
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
		if (!(object instanceof Product)) {
			return false;
		}
		Product other = (Product) object;
		if ((this.code == null && other.code != null)
				|| (this.code != null && !this.code.equals(other.code))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.dev.backend.model.Product[ code=" + code + " ]";
	}

}
