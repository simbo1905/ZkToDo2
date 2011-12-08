
Getting the source:

	git clone https://simbo1905@github.com/simbo1905/ZkToDo2.git
	
Building and running it locally requires a PostgreSQL database host/db/user/passwd:

	mvn -Djetty.port=8080 -DDATABASE_URL=postgres://foodToGoUser:foodToGoPassword@localhost/testdb package jetty:run

Running locally with Heroku dev tools requres that you let it know the DATABASE_URL in an environment variable

	export DATABASE_URL=postgres://foodToGoUser:foodToGoPassword@localhost/testdb
	foreman start

Pushing to Heroku Cloud: 

	heroku create --stack cedar
	git push heroku master
	heroku open

Note

	"hibernate.hbm2ddl.auto=update" in file src/main/webapp/WEB-INF/classes/zktodo2.properties 
	may or may not create the required database table. lso note that setting it to 'validate' 
	is not something that Heroku db likes. O f course that setting is a bit lazy you can have 
	hibernate generate the sql ddl script and use the local/heroku tools to manage your schema 
	for a real app.

More documentation at: 

	./commandline.build.and.run.txt
	./eclipse.indigo.build.and.debug.txt
	
An article about the ZK, JPA & Spring code in this sample app is at:

	http://java.dzone.com/articles/test-driving-mvvm-pattern-zk-0




