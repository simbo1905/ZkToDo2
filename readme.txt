
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

	set "hibernate.hbm2ddl.auto=validate" file src/main/webapp/WEB-INF/classes/zktodo2.properties 
	if it seems that it drops the data whenever you restart.


