
#### This is the code for the "ZkToDo2" sample with ZK, JPA & Spring

	http://www.slideshare.net/simbo1905/zk-mvvm-spring-jpa-on-two-paas-clouds-10610874
	http://www.slideshare.net/simbo1905/design-patterns-in-zk-java-mvvm-as-modelviewbinder 

#### See it running on the cloud (the apps may shutdown on idle and so may be slow on first request): 

	http://zktd2-zkdemo.rhcloud.com/ (OpenShift PaaS Cloud)
	http://glowing-light-1070.herokuapp.com/ (Heroku PaaS Cloud)

#### Getting the source:

	git clone https://simbo1905@github.com/simbo1905/ZkToDo2.git
	
#### Building and running with default settings a PostgreSQL database host/db/user/passwd:

	mvn -Djetty.port=8080 -DDATABASE_URL=postgres://user:password@localhost/db package jetty:run

within the source code there are config settings for running on mysql db, postgresql db and hsqldb see 
the commandline.build.and.run.txt for more details on switching to MySQL database.  

#### Note

	"hibernate.hbm2ddl.auto=update" in file src/main/webapp/WEB-INF/classes/postgresql.zktodo2.properties may or may not create the required database tables depending on your database setup. Of course that setting is a bit lazy you can have hibernate generate the sql ddl script and use the local tools to manage your schema for a real app.

#### More documentation at: 

	./commandline.build.and.run.txt
	./eclipse.indigo.build.and.debug.txt
	./opensshift.build.and.run.txt
	./heroku.build.and.run.txt
	./jboss.build.and.run.txt


