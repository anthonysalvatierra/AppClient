Description: A Rest API to manage shipments to a client and the products that have that shipment, with priority validations according to the client's membership.

Technologies: -Spring Boot. -Spring Data Jpa. -JDK 17. -Maven. -H2 Database.

To Consider: To deploy this project you must have the Java 17 version and in addition to having the JAVA_HOME environment variable registered in your operating system, you must also have Maven installed.

Steps to download:

Step 1: Download the .zip file from the repository or simply clone the project repository with “git clone” followed by the uri.

Step 2: Unzip the .zip file and from the terminal access the project directory to execute the command "mvnw package" which will download all the project dependencies.

Step 3: Once all the dependencies have been downloaded, the project's .jar file is created. To execute it, you must enter the target directory in the same project and execute the following command "java -jar" followed by the .jar file found in the directory. .

Step 4: Once the command is executed, the project will be deployed and you will be able to access the project endpoints through the local route "http://localhost:8080/swagger-ui/index.html" and test the project.
