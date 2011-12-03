Installation:

Running it locally:

	mvn -Djetty.port=8080 package jetty:run

Getting the source:

	git clone https://simbo1905@github.com/simbo1905/ZkToDo2.git
	
Pushing to Heroku Cloud: 

  heroku create --stack cedar
  git push heroku master
  heroku open

Deploy To Tomcat:

1 Deploy this application to Tomcat by copying the zktodo2-X.X.war file to the directory   $TOMCAT_HOME/webapps/. Tomcat will handle the rest of work, including unzipping and deploying.
2 Start your Tomcat.
3 Open your browser, and visit http://localhost:8080/zktodo2-X.X/index.jsp (the port number depends on the configuration of your Tomcat).
4 The code is documented at http://www.zkoss.org/smalltalks/mvc4/


