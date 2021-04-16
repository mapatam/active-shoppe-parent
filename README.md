# active-shoppe-parent
<h1>Momentum Active Days store. Notes:</h1>

<p>

1.  Customer data is pre-populated to the database. 
2.  Customers’ active days points initial totals are already listed in the store database.
3.  Products api Endpoints are to serve when listing all products in store Or purchasing a product.
4.  Store api endpoint is to serve when listing all the stores available
5.  /actuator/** is enabled with 6 endpoints (health, info, env, loggers, configprops, prometheus) exposed to monitor the app.
</p>
<p>

H2 Database \
--driver="org.h2.Driver" \
--url="jdbc:h2:mem:momentum" \
--username="admin" \
--password="admin" 

</p>



