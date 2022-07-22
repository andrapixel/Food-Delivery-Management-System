# Food-Delivery-Management-System
A Java Swing application the simulates a food delivery management system for a catering company,

The system has three types of users that log in using a username and a password: <b>administrator</b>, <b>employee</b> and <b>client</b>. 

The administrator can:
* import the initial set of products which will populate the menu from a .csv file
* manage the products from the menu (add/delete/modify a product, or create new products composed of already existing base products -> used the <i>Composite Design Pattern</i> for modelling the classes MenuItem, BaseProduct and CompositeProduct)
* generate reports about the performed orders (done using <i>lambda expressions</i> and <i>stream processing</i>; also the main cause of my `pain`)

The client can:
* view the list of products from the menu (displayed in a JTable)
* search for products based on one or multiple criteria (keyword, rating, number of calories/protein/fats/sodium, price) - also done through <i>lambda expressions</i> and <i>stream processing</i>
* create an order consisting of several products - for each order the date and time is persisted and a bill listing the ordered products and total price is generated

The employee is notified each time a new order is performed by a client so that he/she can prepare the delivery of the ordered dishes (done by using the <i>Observer Design Pattern</i>).

The menu items, performed orders and user information are persisted using serialization so as to be available at future system executions by means of deserialization. (had a dozen of breakdowns while trying to get this to work haha)
