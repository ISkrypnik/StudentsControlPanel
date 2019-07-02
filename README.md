# Students Control Pane

Here is an app that allows user to create/reade/update/delete
student entities.

#### In this project you can find:
- Java,
- SWT,
- Hibernate,
- PostgreSQL,
- Lombok,
- Slf4j,
- Logback.

#### Description

Student object contains four fields:
- Id,
- First name,
- Last name,
- Group number.

Entity creates through the input forms. User should type all
fields except id, it generates automatically.

To save entity user should fill all available input fields and 
press **save** button.

To delete entity it should be selected by mouse press in the
table and then should be pressed **delete** button.

To update entity user should select it in the table by mouse
click and press **edit** button. After editing existing values in
input forms save button have to be pressed to save changes.

#### Start up
To start application from idea you have to create table with
name "students".
Postgre credentials should be changed to yours in persistence.xml
file. Also make sure that you use your data base locally, else
change its url in same file.
In build gradle you can find two different dependencies for SWT
library: for windows and for linux. Comment/uncomment needed
according to your system.