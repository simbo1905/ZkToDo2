
#### This is the code for the "ZkToDo2" sample application with ZK, JPA & Spring

[Here](http://www.slideshare.net/simbo1905/zk-mvvm-spring-jpa-on-two-paas-clouds-10610874) is the presentation given to the 2011 London ZK Users group. 

[Here](http://www.slideshare.net/simbo1905/design-patterns-in-zk-java-mvvm-as-modelviewbinder) is the presentation of the idea of ZK MVVM given to the 2010 London ZK Users group.  

#### See it in the clouds

Running on the [OpenShift PaaS Cloud](http://zktd2-zkdemo.rhcloud.com/ "OpenShift PaaS Cloud") 

Running on the [Heroku PaaS Cloud](http://glowing-light-1070.herokuapp.com/ "Heroku PaaS Cloud") 

Running on the [Cloudfoundry PaaS Cloud](http://zktodo2.cloudfoundry.com/ "Cloudfoundry PaaS Cloud") 

#### Getting the source:

	git clone https://simbo1905@github.com/simbo1905/ZkToDo2.git
	
#### Building and running with default settings a PostgreSQL database host/db/user/passwd:

	mvn -Djetty.port=8080 -DDATABASE_URL=postgres://user:password@localhost/db package jetty:run

within the source code there are config settings for running on mysql db, postgresql db and hsqldb see 
the commandline.build.and.run.txt for more details on switching to MySQL database.  

#### Note

The line

	"hibernate.hbm2ddl.auto=update" 

in file 

	src/main/webapp/WEB-INF/classes/postgresql.zktodo2.properties 

may or may not create the required database tables depending on your database setup. Of course that setting is a bit lazy you can have hibernate generate the sql ddl script and use the local tools to manage your schema for a real app.

#### More documentation at: 

	./commandline.build.and.run.txt
	./eclipse.indigo.build.and.debug.txt
	./opensshift.build.and.run.txt
	./heroku.build.and.run.txt
	./cloudfoundry.build.and.run.txt


