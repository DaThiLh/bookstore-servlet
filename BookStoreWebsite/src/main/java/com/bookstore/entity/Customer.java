package com.bookstore.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "customer", catalog = "bookstoredb",
    uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@NamedQueries({
  @NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c ORDER BY c.registerDate DESC"),
  @NamedQuery(name = "Customer.findByEmail", query = "SELECT c FROM Customer c WHERE c.email = :email"),
  @NamedQuery(name = "Customer.countAll", query = "SELECT COUNT(c.email) FROM Customer c"),
  @NamedQuery(name = "Customer.checkLogin", query = "SELECT c FROM Customer c WHERE c.email = :email AND c.password = :password")
})
public class Customer implements java.io.Serializable {

  private Integer customerId;
  private String email;
  private String fullname;
  private String address;
  private String city;
  private String country;
  private String phone;
  private String zipcode;
  private String password;
  private Date registerDate;
  private Set<Review> reviews = new HashSet<Review>(0);
  private Set<BookOrder> bookOrders = new HashSet<BookOrder>(0);

  public Customer() {}

  public Customer(String email, String fullname, String address, String city, String country,
      String phone, String zipcode, String password, Date registerDate) {
    this.email = email;
    this.fullname = fullname;
    this.address = address;
    this.city = city;
    this.country = country;
    this.phone = phone;
    this.zipcode = zipcode;
    this.password = password;
    this.registerDate = registerDate;
  }

  public Customer(String email, String fullname, String address, String city, String country,
      String phone, String zipcode, String password, Date registerDate, Set<Review> reviews,
      Set<BookOrder> bookOrders) {
    this.email = email;
    this.fullname = fullname;
    this.address = address;
    this.city = city;
    this.country = country;
    this.phone = phone;
    this.zipcode = zipcode;
    this.password = password;
    this.registerDate = registerDate;
    this.reviews = reviews;
    this.bookOrders = bookOrders;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "customer_id", unique = true, nullable = false)
  public Integer getCustomerId() {
    return this.customerId;
  }

  public void setCustomerId(Integer customerId) {
    this.customerId = customerId;
  }

  @Column(name = "email", unique = true, nullable = false, length = 64)
  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Column(name = "fullname", nullable = false, length = 30)
  public String getFullname() {
    return this.fullname;
  }

  public void setFullname(String fullname) {
    this.fullname = fullname;
  }

  @Column(name = "address", nullable = false, length = 128)
  public String getAddress() {
    return this.address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  @Column(name = "city", nullable = false, length = 32)
  public String getCity() {
    return this.city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  @Column(name = "country", nullable = false, length = 64)
  public String getCountry() {
    return this.country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  @Column(name = "phone", nullable = false, length = 15)
  public String getPhone() {
    return this.phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  @Column(name = "zipcode", nullable = false, length = 24)
  public String getZipcode() {
    return this.zipcode;
  }

  public void setZipcode(String zipcode) {
    this.zipcode = zipcode;
  }

  @Column(name = "password", nullable = false, length = 16)
  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "register_date", nullable = false, length = 19)
  public Date getRegisterDate() {
    return this.registerDate;
  }

  public void setRegisterDate(Date registerDate) {
    this.registerDate = registerDate;
  }

  /**
   * Important NOTE: Do not change the FetchType to EAGER. Keep it LAZY.
   * Explain: The list customer feature will load all Customer objects 
   * with associated Review objects
   * This affects the application's performance
   */
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
  public Set<Review> getReviews() {
    return this.reviews;
  }

  public void setReviews(Set<Review> reviews) {
    this.reviews = reviews;
  }

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
  public Set<BookOrder> getBookOrders() {
    return this.bookOrders;
  }

  public void setBookOrders(Set<BookOrder> bookOrders) {
    this.bookOrders = bookOrders;
  }

  @Override
  public int hashCode() {
    return Objects.hash(address, bookOrders, city, country, customerId, email, fullname, password,
        phone, registerDate, reviews, zipcode);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Customer other = (Customer) obj;
    return Objects.equals(address, other.address) && Objects.equals(bookOrders, other.bookOrders)
        && Objects.equals(city, other.city) && Objects.equals(country, other.country)
        && Objects.equals(customerId, other.customerId) && Objects.equals(email, other.email)
        && Objects.equals(fullname, other.fullname) && Objects.equals(password, other.password)
        && Objects.equals(phone, other.phone) && Objects.equals(registerDate, other.registerDate)
        && Objects.equals(reviews, other.reviews) && Objects.equals(zipcode, other.zipcode);
  }

}
