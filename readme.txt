
Getting the source:

	git clone https://simbo1905@github.com/simbo1905/ZkToDo2.git
	
Ensure that it will create the database tables as necessary:

	hibernate.hbm2ddl.auto=update file src/main/webapp/WEB-INF/classes/zktodo2.properties

Building and running it locally (requires a PostgreSQL database host/db/user/passwd):

	mvn -Djetty.port=8080 -DDATABASE_URL=postgres://foodToGoUser:foodToGoPassword@localhost/testdb package jetty:run

Running locally with Heroku dev tools

	foreman start

Pushing to Heroku Cloud: 

  heroku create --stack cedar
  git push heroku master
  heroku open

Remember

	set hibernate.hbm2ddl.auto=validate file src/main/webapp/WEB-INF/classes/zktodo2.properties 
	then mvn package as it seems to drop the data whenever you push when it is set to update

