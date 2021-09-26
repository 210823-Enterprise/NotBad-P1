# !Bad ORM Framework

## Project Description
Something like: A not-too-shabby (or at least not totally awful) minimalist Java-based ORM framework for simplifying your persistence layer without need for SQL or connection management.

## Technologies Used

* PostgreSQL - version 42.2.12
* Java - version 8.0
* Apache commons - version 2.1
* Log4J

## Features

List of features: 
* Easy to use and straightforward user API.  
* No need for SQL, HQL, or any databse specific language.  
* Straightforward and simple annotations for ease of use. 

To-do list: [`for future iterations`]
* Mapping of join columns inside of entities.    
* Loading objects to cache from the API.

## Getting Started  
Currently project must be included as local dependency. to do so:
```shell
  git clone https://github.com/210517-Enterprise/NotBad-P1.git
  cd NotBad-p1
  mvn install
```
Next, place the following inside your project pom.xml file:
```XML
  <dependency>
    <groupId>com.revature</groupId>
    <artifactId>NotBadORM</artifactId>
    <version>0.0.1</version>
  </dependency>

```

Finally, inside your project structure you need a application.proprties file. 
 (typically located src/main/resources/)
 ``` 
  url=path/to/database
  admin-usr=username/of/database
  admin-pw=password/of/database  
  ```
  
## Usage  
  ### Annotating classes  
  All classes which represent objects in database must be annotated.
   - #### @Entity(tableName = "table_name")
      - Indicates that the class is to be used as the table name.
   - #### @Table(tableName = "table_name")  
      - Indicates that this class is associated with table 'table_name'  
   - #### @Id(columnName = "column_name")
      - Indicates that the annotated field represents a primary key for the table.
   - #### @Column(columnName = "column_name")  
      - Indicates that the annotated field is a column in the table with the name 'column_name'  
   - #### @JoinColumn(columnName = "column_name")
      - Indicates that the anootated field is a join column.

  ### User API  
  - #### `public static Configuration getInstance()`
     - Returns a static instance of the configuration class.
  - #### `public void addAnnotatedClass(final Class<?> annotatedClass)`  
     - Registers a class to be added to the ORM and database. Must be properly annotated with the class marked as @Entity, and at least 1 variable marked with @Id, and another with @Column.  Throws 'IllegalStateException' if the configuration has been finalized, or the provided class is not properly annotated.
  - #### `public void finalizeConfig()`
     - Marks a configuration as complete. Creates tables in the database for all registered class (if needed), and prevents adding of additional classes to the configuration.
  - #### `boolean generateTable(final MetaModel<?> model)`
     - Creates a table for the specified model is none exists.
  - #### `public static ORM getInstance()`
     - Returns the singleton instance of the class. This is the starting point to calling any of the below methods.  
  - #### `public boolean addObjectToDb(final Object object)`
     - Saves an object to the database. Fails if the object already exists. If the primary key is set to auto generate, automatically sets the object's id to the generated id.
  - #### `public <T> T getObjectFromDb(final Class<T> clazz, final String columnName, final Object value)`
     - Retrieves an object from the database using the specified column name. If more then 1 column matches the provided value, one object is arbitrarily returned. 
        - @param clazz : The object's class.
        - @param columnName : The column name to check for retrieving the value.
        - @param value : The value at the column.
  - #### `public <T> List<T> getAllObjectsFromDb(final Class<T> clazz)`  
     - Returns all objects in the database of the specified class.  
  - #### `public boolean updateObjectInDb(final Object object)`  
     - Updates an existing entry in the database using the provided object. Fails if the provided object does not already exist in the database.
  - #### `public boolean removeObjectFromDb(final Object object)`
     - Removes provided object from the database, assuming an object in the database has a matching primary key.
  - #### `public void startTransation()`  
     - Starts a transaction. Disables auto-commit and logs database queries.
  - #### `public void rollback()`  
     - Removes all recorded queries.
  - #### `public void abort()`  
     - Removes all recorded queries and closes the transaction. Re-enables auto-commit.  
  - #### `public void commit()`  
     - Commits all recorded queries and closes the transaction. Re-enables auto-commit. 



## License

This project uses the following license: [GNU Public License 3.0](https://www.gnu.org/licenses/gpl-3.0.en.html).
