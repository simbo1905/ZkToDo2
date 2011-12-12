
This is the code for the "ZkToDo2" sample with ZK, JPA & Spring documented on dzone.com: 

	http://java.dzone.com/articles/test-driving-mvvm-pattern-zk-0

(Note: The article does not cover the new ZK6 "ZK Bind" MVVM but the sourcecode *does*.)

See it running on the cloud (the apps may shutdown on idle so very slow on first request): 

	http://zktd2-zkdemo.rhcloud.com/zktodo_d.zul (Heroku PaaS Cloud)
	http://glowing-light-1070.herokuapp.com/zktodo_d.zul (OpenShift PaaS Cloud)

Getting the source:

	git clone https://simbo1905@github.com/simbo1905/ZkToDo2.git
	
Building and running it locally requires a PostgreSQL database host/db/user/passwd:

	mvn -Djetty.port=8080 -DDATABASE_URL=postgres://foodToGoUser:foodToGoPassword@localhost/testdb package jetty:run

Note

	"hibernate.hbm2ddl.auto=update" in file src/main/webapp/WEB-INF/classes/postgresql.zktodo2.properties 
	may or may not create the required database table. Of course that setting is a bit lazy you can have 
	hibernate generate the sql ddl script and use the local/heroku tools to manage your schema 
	for a real app.
	
	within the source code there are config settings for running on mysql db, postgresql db and hsqldb

More documentation at: 

	./commandline.build.and.run.txt
	./eclipse.indigo.build.and.debug.txt
	./opensshift.build.and.run.txt
	./heroku.build.and.run.txt
