cd ORM
mvn package
mvn install:install-file -Dfile=target/NotBadORM-0.0.1-SNAPSHOT.jar -DgroupId=com.revature -DartifactId=NotBadORM -Dversion=0.0.1 -Dpackaging=jar -DgeneratePom=true
cd ..
cp ORM/target/NotBadORM-0.0.1-SNAPSHOT.jar WebApp/src/main/resources
cd WebApp
mvn package