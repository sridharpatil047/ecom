

# Scalable E-Commerce Backend with Spring Boot & Microservices

### 📢 Check out the demo video!
##### A clear walkthrough of the backend API flows for key use cases. 🚀
[Watch the video](https://www.youtube.com/watch?v=uUhbUfUKLZI)

--- 
## 🚀 Overview

This **high-performance e-commerce backend** is built using **Spring Boot**, designed to power seamless online shopping experiences with **secure transactions, scalable architecture, and efficient order processing**.

![high-level-design](./design-docs/high-level-design/high-level-design.drawio.svg)

With a well-structured **RESTful API**, it provides the core functionalities of any top-tier e-commerce platform:
- **🔐 Secure User Authentication** – Implements JWT-based authentication and role-based access control.
- **🛍️ Product Catalog Management** – Enables dynamic product listings with search and filtering capabilities.
- **🛒 Shopping Cart & Checkout** – Manages user carts, calculates totals, and processes orders efficiently.
- **📦 Order Processing & Tracking** – Ensures smooth order fulfillment with real-time tracking updates.

### ⚡ Why This Stands Out?
- **Scalable & Modular Design** – Built with a **clean architecture** following industry best practices.
- **Secure & Reliable** – Integrated with **OAuth2, JWT, and Spring Security** for data protection.
- **Database & Caching Optimization** – Uses **MySQL/PostgresSQL with Redis** for high-speed performance.
- **Microservices-Ready** – Designed to scale seamlessly with **Service Discovery (Netflix Eureka) and API Gateway (Spring Cloud Gateway)**, enabling efficient request routing and load balancing.
- **Advanced Search Capabilities** – Integrates **Elasticsearch** for lightning-fast search, supporting both **basic keyword searches** and **advanced filtering** like fuzzy search and full-text indexing.
- **Seamless Payment Integration** – Supports **Razorpay and Stripe** for secure and flexible payment processing, ensuring a smooth checkout experience.
- **Extensible & Maintainable** – Follows **robust design principles and reusable patterns**, allowing easy addition of new features without disrupting the existing system.

This isn't just another backend—it’s a **production-ready foundation** for any e-commerce application looking to scale and perform at the highest level. 🚀

---
## 🔥 Features List

### 🏠 User Management
- **🔑 Registration** – Users can create accounts via email or social logins.
- **🔐 Secure Login** – Users can authenticate using their credentials.
- **👤 Profile Management** – Edit and update user profile details.
- **🔄 Password Reset** – Secure password reset functionality via email.

### 🛍️ Product Catalog
- **📂 Category-Based Browsing** – Explore products across multiple categories.
- **📝 Detailed Product Pages** – View product images, descriptions, and specifications.
- **🔎 Powerful Search** – Find products instantly using keywords.

### 🛒 Cart & Checkout
- **➕ Add to Cart** – Easily add products to the cart.
- **📋 Cart Review** – View selected items, quantity, and total cost.
- **💳 Seamless Checkout** – Specify delivery details and choose a payment method.

### 📦 Order Management
- **✅ Order Confirmation** – Receive a confirmation message upon purchase.
- **📜 Order History** – Access past orders anytime.
- **🚚 Order Tracking** – Monitor delivery status in real time.

### 💰 Payment
- **🛠️ Multiple Payment Options** – Pay via credit/debit cards, UPI, or net banking.
- **🔒 Secure Transactions** – Encrypted and trusted payment processing.
- **📄 Payment Receipt** – Auto-generated receipts for completed payments.

### 🔐 Authentication & Security
- **🛡️ Secure Authentication** – Strong encryption ensures data privacy.
- **🕒 Session Management** – Users stay logged in for a controlled duration.


---

## 🔍 Low Level Design

### 👤 **User Service**
![UserService](./design-docs/low-level-design/class-diagram/UserService.svg.drawio.svg)

### 📦 **Product Service**
![ProductService](./design-docs/low-level-design/class-diagram/ProductService.drawio.svg)

### 🛒 **Cart Service**
![CartService](./design-docs/low-level-design/class-diagram/CartService.drawio.svg)

### 📑 **Order Service**
![OrderService](./design-docs/low-level-design/class-diagram/OrderService.drawio.svg)

### 💳 **Payment Service**
![PaymentService](./design-docs/low-level-design/class-diagram/PaymentService.drawio.svg)

### 📩 **Notification Service**
![NotificationService](./design-docs/low-level-design/class-diagram/NotificationService.drawio.svg)

---

## 🚀 Technologies Used

### 🌐 API Gateway - **Spring Cloud Gateway**
- **Spring Cloud Gateway** is an API Gateway that provides *routing, authentication, and monitoring* capabilities for microservices.
- 🛣️ **Routes API requests** to appropriate backend microservices.
- 🔐 Implements **security policies** like OAuth 2.0 authentication.
- 📊 Handles **rate limiting, logging, and analytics**.

### ⚖️ Load Balancer - **Spring Cloud LoadBalancer**
- **Spring Cloud LoadBalancer** is a *client-side load balancing solution* that distributes requests across multiple service instances.
- 🏗️ Prevents **overloading** of a single service instance.
- 💪 Enhances **fault tolerance** by redistributing traffic when an instance fails.

### 🔍 Service Discovery - **Netflix Eureka**
- **Netflix Eureka** is a **service registry** that helps microservices dynamically locate each other.
- 📝 **Registers and discovers** microservices in a distributed system.
- 📈 Ensures services can **scale up or down** dynamically.

### 📩 Distributed Messaging - **Apache Kafka**
- **Apache Kafka** is a *distributed message broker* used for **event-driven applications**.
- 🔄 Enables **asynchronous communication** between microservices.
- ⚡ Processes **real-time data streams**, such as payment status updates.

### 🚀 Global Cache - **Redis**
- **Redis** is an *in-memory key-value store* used for **caching and session management**.
- ⚡ **Boosts API speed** by caching frequently accessed data.
- 📉 Reduces **database load** by minimizing repetitive queries.

### 🔐 Authentication & Authorization - **OpenID Connect & OAuth 2.0**
- **OAuth 2.0** is a **secure authorization framework**, while **OpenID Connect** adds an authentication layer.
- 🔑 OAuth 2.0 allows **password-free logins** (e.g., *"Login with Google"*).
- 👤 OpenID Connect **verifies user identity**.

### 🗄️ SQL Database - **MySQL**
- **MySQL** is a **relational database** used for structured data storage.
- 📦 Stores **users, orders, transactions, and products**.
- 🔄 Supports **ACID transactions** for **data consistency**.

### 🔎 NoSQL Database - **Elasticsearch**
- **Elasticsearch** is a **high-performance NoSQL search engine**.
- ⚡ **Powers fast search** for products, logs, and analytics.
- 🔍 Provides **advanced filtering, ranking, and autocomplete**.

### 💳 Payment Gateway - **Razorpay & Stripe**
- **Razorpay & Stripe** securely process online transactions.
- 🏦 **Supports payments via** *credit cards, UPI, and wallets*.
- 🛡️ Provides **fraud detection** and **transaction verification**.

### 📧 SMTP Server - **Gmail**
- **SMTP (Simple Mail Transfer Protocol)** is used for sending emails.
- 📩 Sends **order confirmations, password resets**, and other notifications.
- 🔄 Implements **transactional emails via SMTP relay**.



---

## 🏗️ High-Level Design

This project follows a **robust and scalable microservices architecture** deployed on **AWS**, ensuring **high availability, security, and fault tolerance**. The system is built to handle **millions of requests per second**, with **efficient load balancing**, **service discovery**, and **real-time data processing**.

With **Spring Boot, Netflix Eureka, Kafka, and Redis**, this architecture guarantees **seamless performance, rapid data retrieval, and a smooth user experience**. Below is an overview of how different AWS components interact to build this highly efficient e-commerce backend.

### 🏢 Architecture on AWS

This architecture is designed for **high availability, scalability, and fault tolerance** using AWS services across two **Availability Zones (AZs)**.

![aws-architecture-diagram](./design-docs/high-level-design/aws-architecture-diagram.drawio.svg)


#### 🔄 User Request Handling
- 🌎 Web or mobile clients send requests to **Route 53 (DNS)**, which resolves to the **Elastic Load Balancer (ELB)**.
- ⚖️ The **ELB** distributes traffic across multiple **API Gateways** in different **Availability Zones**.

#### 🔗 API Gateway & Microservices
- 🛡️ The **API Gateway** securely handles incoming requests and routes them to appropriate **microservices** running on **EC2 instances** inside a **private subnet**.
- 🧩 **Netflix Eureka (Service Discovery)** enables seamless **microservices communication**.

#### 🗄️ Data Layer Operations
- 📦 Microservices interact with databases like **MySQL, Elasticsearch, Redis, and Kafka**, deployed in **private subnets**.
- 🔄 **Sync replication** ensures **data consistency** between **Availability Zones**.

#### 🌍 Outbound Internet Access for Private Subnet
- 🚀 Microservices in the **private subnet** require internet access for **updates and external APIs**.
- 🌐 A **NAT Gateway** in the **public subnet** allows private subnet instances to make **outbound requests** securely.

#### 🔥 High Availability & Fault Tolerance
- ✅ **Multi-AZ Deployment** ensures **redundancy and failover protection**.
- 🔒 **Security Groups & Route Tables** efficiently manage **traffic rules and security policies**.

---

## 🏗️ AWS Components

### 🌐 **Route 53**
- 📌 AWS’s **Domain Name System (DNS)** service.
- 📡 Routes users to the appropriate AWS service (e.g., ELB, S3, EC2).

### 🚪 **Internet Gateway**
- 🌍 A network component that allows **resources inside the VPC** to access the **public internet**.

### ⚖️ **Elastic Load Balancer (ELB)**
- 🔄 Distributes incoming traffic among **multiple instances** to ensure **fault tolerance & scalability**.
- 🏢 Supports **multiple Availability Zones** for **high availability**.

### 📍 **Route Table**
- 🛣️ Defines rules for **network traffic** within a **VPC**.
- 🔄 Determines how **subnets communicate** with each other and external services.

### 🏠 **Public Subnet**
- 🌍 **Accessible from the internet** via an **Internet Gateway**.
- 🛠️ Typically contains **API Gateways, Load Balancers, and NAT Gateways**.

### 🔒 **Private Subnet**
- 🚫 No direct internet access for **better security**.
- 🔗 Contains **microservices, databases, and caching layers**.
- 🌐 Can communicate externally via a **NAT Gateway**.

### 🌐 **NAT Gateway**
- 🔄 Allows **outbound internet traffic** from private subnet instances.
- 🚫 Prevents **inbound connections**, ensuring **security**.

### 🖥️ **EC2 (Elastic Compute Cloud)**
- 🏗️ Virtual machines that host **microservices and backend applications**.
- 🚀 **Auto-scaled** based on demand for **cost efficiency**.

### 🔐 **Security Groups**
- 🛡️ Acts as a **firewall** for EC2 instances.
- 🚦 Controls **inbound and outbound traffic**.

### 📍 **Region**
- 🌍 A **geographical area** where AWS data centers exist (*e.g., us-east-1, ap-south-1*).

### 🏢 **Availability Zone (AZ)**
- 🏗️ A **data center** inside a **Region**.
- 🔄 Provides **high availability** by distributing workloads across multiple AZs.

### 🌐 **VPC (Virtual Private Cloud)**
- 🔒 A **logically isolated network** within AWS.
- 🏗️ Helps in **network segmentation and security**.

### 🛢️ **RDS (Relational Database Service)**
- 💾 Managed **MySQL, PostgreSQL, SQL Server**, etc.
- 🔄 Provides **automated backups, scaling, and multi-AZ deployment**.

### ☁️ **Managed Infra / Elastic Beanstalk**
- 🚀 AWS **PaaS** that automatically **deploys and scales applications**.
- 🔧 Reduces **operational overhead** and simplifies **infrastructure management**.
- 

## 🔄 Flow Diagrams

### 🔑 **Login Flow**
![login-flow](./design-docs/high-level-design/data-flows/01-login-flow.drawio.svg)

### 🛠️ **Admin Flow**
![admin-flow](./design-docs/high-level-design/data-flows/02-admin-flow.drawio.svg)


### 🆕 **User SignUp Flow**
![user-sign-up-flow](./design-docs/high-level-design/data-flows/03-user-sign-up-flow.drawio.svg)


### 🏠 **User Profile Management Flow**
![user-profile-management-flow](./design-docs/high-level-design/data-flows/04-user-profile-management-flow.drawio.svg)


### 🛒 **Product Purchase Flow**
![product-purchase-flow](./design-docs/high-level-design/data-flows/05-product-purchase-flow.drawio.svg)

## 🚀 Future Enhancements

The journey doesn’t stop here! We have some **thrilling upgrades** planned to make this e-commerce backend even more **scalable, resilient, and efficient**:

- 🔄 **Saga Pattern** – To ensure **data consistency** across multiple microservices in distributed transactions.
- ⚡ **Circuit Breaker Pattern** – Enhancing fault tolerance to **gracefully handle failures** without affecting the entire system.
- 🔗 **gRPC for Inter-Service Communication** – Moving beyond REST for **high-performance, low-latency** service-to-service communication.

## 🌍 Open Source Vision

I want to take this **to the next level**—by making it **open source**! If you're interested in contributing, **reach out to me** and I'll provide a **setup guide** to help you get started. Let's build something that can empower businesses to own their backend while keeping full control over their **brand identity and customer experience**.

Unlike platforms like **Shopify, Dukaan, Amazon, Flipkart, or Alibaba**, which lock businesses into their ecosystems, this project aims to provide:  
✅ **Complete Backend Ownership** – Businesses can have their own Backend without relying on third-party platforms.  
✅ **Scalability & Customization** – Extend the system to match specific needs without limitations.  
✅ **Cost Efficiency** – No hidden fees or commission cuts—control your revenue streams.

## 🎯 The USP – Why This Project Can Be a Game Changer

If taken seriously and built to production scale, this **state-of-the-art backend** can:  
🚀 **Empower businesses** to create their own e-commerce platforms without vendor lock-in.  
💡 **Compete with big players** by providing a modular, scalable, and highly customizable solution.  
🔗 **Enable multi-tenant support** for companies to launch their own marketplaces like Amazon or Flipkart.  
🔒 **Offer enterprise-grade security** with OAuth2, JWT, and role-based access control.  
⚙️ **Leverage AI/ML-driven analytics** to optimize customer experience and boost conversions.

This is just the beginning—**let’s revolutionize e-commerce together!** 🚀💡

---

## 💼 Looking for a Skilled Backend Engineer?

This project showcases my expertise in **Spring Boot**, **Microservices Architecture**, **Distributed Systems**, and **Scalable API Design**. I have designed this backend with **high availability, fault tolerance, and best security practices** in mind.

🚀 I am actively looking for exciting opportunities in **backend development**, **microservices**, and **cloud-based architectures**. If you’re a recruiter or a hiring manager, let’s connect!

📩 **Contact Me**: [sridharpatil047@gmail.com](mailto:sridharpatil047@gmail.com)  
🔗 **LinkedIn**: [linkedin.com/in/sridhar-patil-047](https://www.linkedin.com/in/sridhar-patil-047)  
🌟 If you liked my work, don’t forget to ⭐ **star** this repo!  
