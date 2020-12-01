Project Name
Django-This is project for github django user validation

Prerequiste
1. Java environment
2. Maven
3. Eclipse

Installation
Download the zip or clone the GIT rpeository
Unzip the file(if downloaded)
Open Eclipse
File-->Import -->Existing Maven Repository
Select the project django
Project is ready to run .Execute the following commands 
Run mvn clean
Run mvn compile
Run mvn test 

Usage
For any dependency or plugins that have been used in project please refer pom.xml
Right click on project and choose run as configurations
In configuration choose arguments -->vm arguments and add this parameter -Dbrowser=Chrome
This vm argument will be set as parameter in jenkins also
URL for the endpoint are mentioned in portal.properties file

Results
Logs are generated in folder Logs folder in root directory
Reports are generated in properties -->Reports folder






