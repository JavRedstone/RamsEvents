# RamsEvents Project [&copy; Javier 2023]

## General Structure

### DB: Postgresql database

### Frontend: Angular web application

- Angular CLI version 15.0.0
- Angular Fire 7.5.0
- Angular Material 15.0.3
- NGX Cookie Service 15.0.0
- Recommended IDE: Visual Studio Code, Version: 1.71.2

### Backend: Spring boot maven project

- Java: OpenJDK jdk-18.0.1.1
- Recommended IDE: Spring Tool Suite 4, Version: 4.15.1.RELEASE
- Server is running on port 8080



## Source code folder: `source_code`

- Frontend: `source_code/frontend`
- Backend: `source_code/backend`
- Database Scripts: `db_scripts`



## Installation Process

### Database Setup for the RamsEvents Project

- Download Postgresql in https://www.postgresql.org/download/
- Install Postgresql database.
- Open PgAdmin. It should be installed with Postgresql.
- Set the user name as `postgres` and password as `Jav123`.
- Set port as `5432`.
- Once PgAdmin is open, on the left panel, right click on the `Databases` label and click Create -> Database.
- Name the database `webdb`.
- Right click on webdb and click Create -> Schema.
- Name the schema `ramsevents`.
- Click on the schema and click the query tool on the top panel.
- In the main query tool panel, open the file `init.sql` in the folder named `db_scripts`. Run it to create the tables and sequences.
- Then, open the file `insert.sql` in the folder named `db_scripts`. Run it to insert the testing data into the tables.

*Your database setup is complete!*

### Deploying the RamsEvents Project

- Download the Apache Tomcat server from https://tomcat.apache.org/download-10.cgi#10.1.5.
- Unzip the downloaded zip file named `apache-tomcat-10.1.5.zip`.
- In the `build_files`, copy over the folder named `ramsevents` and the war file `ramseventsApi.war` into the `webapps` folder of the unzipped tomcat directory.
- Open the command prompt window.
- Navigate in the command prompt to the `bin` folder of the tomcat directory.
- Run the command `catalina.bat run` to start the tomcat server.
- Access the RamsEvents website at `localhost:8080/ramsevents`.
- Login and signup with your google account.

*You can try out the website now! Enjoy!*



## Credit

### References

- Material Design: https://material.angular.io/
- Material Icons: https://fonts.google.com/icons
- Firebase: https://firebase.google.com/
- PDFMake Tutorial Code: https://github.com/pandeysoni/pdfmake/blob/master/src/App.js [https://pandeysoni.medium.com/how-to-generate-pdf-file-using-pdfmake-module-660509799461]

### Libraries Used

*For all libraries used for the frontend code, please check the `package.json` file under the `source_code/frontend/` folder.*
*For all plugins used for the backend code, please check the `pom.xml` file under the `source_code/backend/ramsevents/` folder.*