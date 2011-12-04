
Getting the source:

	git clone https://simbo1905@github.com/simbo1905/ZkToDo2.git
	
Ensure that it will create the database tables as necessary:

	set "hibernate.hbm2ddl.auto=update" in file src/main/webapp/WEB-INF/classes/zktodo2.properties

Building and running it locally requires a PostgreSQL database host/db/user/passwd:

	mvn -Djetty.port=8080 -DDATABASE_URL=postgres://foodToGoUser:foodToGoPassword@localhost/testdb package jetty:run

Running locally with Heroku dev tools requres that you let it know the DATABASE_URL in an environment variable

	export DATABASE_URL=postgres://foodToGoUser:foodToGoPassword@localhost/testdb
	foreman start

Pushing to Heroku Cloud: 

  heroku create --stack cedar
  git push heroku master
  heroku open

Remember

	setting "hibernate.hbm2ddl.auto=xxxx" in file src/main/webapp/WEB-INF/classes/zktodo2.properties 
	seems to give some issues. 'validate' does not seem to work on heroku and 'update' seems to drop 
	data locally. flipping between the two seems to get things working. 


