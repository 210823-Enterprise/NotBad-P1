# !Bad ORM Framework

## Project Description
Something like: A java based ORM for simplifying connecting to and from an SQL database without the need for SQL or connection management. 

## Technologies Used

* PostgreSQL - version 42.2.12  
* Java - version 8.0  
* Apache commons - version 2.1  
* JUnit

## Features

List of features ready and TODOs for future development  
* Easy to use and straightforward user API.  
* No need for SQL, HQL, or any databse specific language.  
* Straightforward and simple Annotation based for ease of use. 
* etc...

To-do list: [`for future iterations`]
* Mapping of join columns inside of entities.    
* Implement of aggregate functions.  
* Allow ORM to build table based on Annotations in Entities.  
* etc...

## Getting Started  
Currently project must be included as local dependency. to do so:
```shell
  git clone https://github.com/210517-Enterprise/*your-repo*_p1.git
  cd *your-repo*_p1
  mvn install
```
Next, place the following inside your project pom.xml file:
```XML
  <dependency>
    <groupId>com.revature</groupId>
    <artifactId>*your-repo*_p1</artifactId>
    <version>1.0-SNAPSHOT</version>
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
   - #### @Table(name = "table_name)  
      - Indicates that this class is associated with table 'table_name'  
   - #### @Column(name = "column_name)  
      - Indicates that the Annotated field is a column in the table with the name 'column_name'  
   - #### @Setter(name = "column_name")  
      - Indicates that the anotated method is a setter for 'column_name'.  
   - #### @Getter(name = "column_name")  
      - Indicates that the anotated method is a getter for 'column_name'.  
   - #### @PrimaryKey(name = "column_name") 
      - Indicates that the annotated field is the primary key for the table.
   - #### @SerialKey(name = "column_name") 
      - Indicates that the annotated field is a serial key.

  ### User API  
  - #### 'public static Configuration getInstance()'
     - Returns a static instance of the configuration class.
  - #### `public void addAnnotatedClass(final Class<?> annotatedClass)`  
     - Registers a class to be added to the ORM and database. Must be properly annotated with the class marked as @Entity, and at least 1 variable marked with @Id, and another with @Column.  Throws 'IllegalStateException' if the configuration has been finalized, or the provided class is not properly annotated.
  - #### 'public void finalizeConfig()'
     - Marks a configuration as complete. Creates tables in the database for all registered class (if needed), and prevents adding of additional classes to the configuration.
  - #### `public static ORM getInstance()`  
     - returns the singleton instance of the class. This is the starting point to calling any of the below methods.  
  - #### `public boolean addObjectToDb(final Object object)`  
     - Saves an object to the database. Fails if the object already exists. If the primary key is set to auto generate, automatically sets the object's id to the generated id.
  - #### `public <T> T getObjectFromDb(final Class<T> clazz, final String columnName, final Object value)`  
     - Retrieves and object from the database using the specified column name. If more then 1 column matches the provided value, one object is arbitrarily returned. 
        - @param clazz The object's class.
	      - @param columnName The column name to check for retrieving the value
	      - @param value The value at the column
  - #### `public boolean removeObjectFromDB(final Object obj)`  
     - Removes the given object from the database.  
  - #### `public boolean addObjectToDB(final Object obj)`  
     - Adds the given object to the database.  
  - #### `public Optional<List<Object>> getListObjectFromDB(final Class <?> clazz, final String columns, final String conditions)`  
  - #### `public Optional<List<Object>> getListObjectFromDB(final Class <?> clazz, final String columns, final String conditions,final String operators)`  
  - #### `public Optional<List<Object>> getListObjectFromDB(final Class<?> clazz)`  
     - Gets a list of all objects in the database which match the included search criteria  
        - columns - comma seperated list of columns to search by.  
        - conditions - coma seperated list the values the columns should match to.  
        - operators - comma seperated list of operators to apply to columns (AND/OR) in order that they should be applied.  
  - #### `public void beginCommit()`  
     - begin databse commit.  
  - #### `public void Rollback()`  
     - Rollback to previous commit.  
  - #### `public void Rollback(final String name)`  
     - Rollback to previous commit with given name.  
  - #### `public void setSavepoint(final String name)`  
     - Set a savepoint with the given name.  
  - #### `public void ReleaseSavepoint(final String name)`  
     - Release the savepoint with the given name.  
  - #### `public void enableAutoCommit()`  
     - Enable auto commits on the database.  
  - #### `public void setTransaction()`  
     - Start a transaction block.  
  - #### `public void addAllFromDBToCache(final Class<?> clazz)`  
     - Adds all objects currently in the databse of the given clas type to the cache.  



## License

This project uses the following license: [GNU Public License 3.0](https://www.gnu.org/licenses/gpl-3.0.en.html).
