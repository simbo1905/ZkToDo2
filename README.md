
#### This is the code for the "ZkToDo2" sample application with ZK, JPA & Spring

This is the sourcecode project from the white paper [Implementing event-driven GUI patterns using the ZK Java AJAX framework](http://www.ibm.com/developerworks/websphere/zones/portal/proddoc/zkjavaajax/). 

Tested against Java8, ZK 8.0.0, Spring 4.0.9.RELEASE and Hibernate 4.3.10.Final

#### See it in the clouds

Running on the [OpenShift PaaS Cloud](http://zktd2-zkdemo.rhcloud.com/ "OpenShift PaaS Cloud") 

Running on the [Heroku PaaS Cloud](http://glowing-light-1070.herokuapp.com/ "Heroku PaaS Cloud") 

#### Getting the source:

	git clone https://simbo1905@github.com/simbo1905/ZkToDo2.git
	
#### Building and running with default settings of a PostgreSQL database:

	mvn -Djetty.port=8080 -DDATABASE_URL=postgres://user:password@localhost:port/db package jetty:run

within the source code there are config settings for running on mysql db, postgresql db and derby see 
the commandline.build.and.run.txt for more details on switching to MySQL database. See `opensshift.build.and.run.txt` for the create table command.  

#### Note

The line

	"hibernate.hbm2ddl.auto=update" 

in file 

	src/main/webapp/WEB-INF/classes/postgresql.zktodo2.properties 

may or may not create the required database table depending on your database setup. Of course that setting is a bit lazy you can have hibernate generate the sql ddl script and use the local tools to manage your schema for a real app.

#### More documentation at: 

	./commandline.build.and.run.txt
	./eclipse.indigo.build.and.debug.txt
	./opensshift.build.and.run.txt
	./heroku.build.and.run.txt
	./cloudfoundry.build.and.run.txt

