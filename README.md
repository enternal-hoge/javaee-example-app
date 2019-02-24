# javaee-example-app
A simple application of JavaEE with JPA, CDI and JSF

**This is just my Learning** not production leady quality.


### Build

```
mvn clean package
```

### Run

Run on **Redhat JBoss EAP 7.2**.

copy war file into <JBOSS_HOME>/standard/deployments or WEB GUI Console.

### Notes

this application use h2 database as persistence store into JBoss EAP.

initial test data persist following class and annotations.

To add some user and notes to the database, uncomment the following annotations
```Java
package eternal.hoge.javaee.simple.example.bean.startup;

@Singleton
@Startup
public class StartupBean {
  ...
}
```
