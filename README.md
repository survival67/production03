1. download template  
2. Link to preconfigured start.spring.io  
3. Unarchive, open folder in terminal and in IDE  
4. run -> exception  
5. comment openai deps in pom.xml -> run -> no exception  

Start DB via Docker Desktop (Windows)  
1. Install Docker Desktop (if not installed)  
2. Open project folder in terminal  
3. Run database: Containers and volumes will be created automatically

Configure DB Access in java app  
port: "5433:5432"  
application.properties:
```
spring.application.name=production03
spring.datasource.url=jdbc:postgresql://localhost:5432/mydatabase?options=-c%20TimeZone=UTC
spring.datasource.username=myuser
spring.datasource.password=secret
debug=true
spring.jpa.properties.hibernate.jdbc.time_zone=UTC
```

Work with DB client (optional)  
You may use:  
- Eclipse → DBeaver plugin  
- Docker Desktop GUI → open container logs  

Init DB  
File: sql_schema/init.sql  

Run app  
Open project in Eclipse → Run as Spring Boot App  
Open browser → http://localhost:8080/sql

Execute SQL queries  
Use INSERT / UPDATE / DELETE / SELECT  
After each INSERT / UPDATE / DELETE the updated table is shown automatically.

Tables structure:
Products (uuid id, name, serial_number, category)  
Components (serial id, name, description, product_id → products.id)  
Details (serial id, name, material, component_id → components.id)

Example UUID values:
11111111-1111-1111-1111-111111111111  
22222222-2222-2222-2222-222222222222  
550e8400-e29b-41d4-a716-446655440000  

Example queries:
INSERT INTO products (id, name, serial_number, category)
VALUES ('11111111-1111-1111-1111-111111111111','Test','SN-1','Category');

INSERT INTO components (name, description, product_id)
VALUES ('Comp','Desc','11111111-1111-1111-1111-111111111111');

INSERT INTO details (name, material, component_id)
VALUES ('Detail','Steel',1);