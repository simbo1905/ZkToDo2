
Builda and running it locally (requires a PostgreSQL database):

	mvn -Djetty.port=8080 -DDATABASE_URL=postgres://foodToGoUser:foodToGoPassword@localhost/testdb package jetty:run

Getting the source:

	git clone https://simbo1905@github.com/simbo1905/ZkToDo2.git
	
Pushing to Heroku Cloud: 

  heroku create --stack cedar
  git push heroku master
  heroku open
