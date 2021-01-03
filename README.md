# Cute IOC
Work Task
### 1. Declared supported annotation

  - Component
  - Bean
  - PostConstructor
  - Autowired
  - Qualifier

### 2. Load class
   - load all classes in package and sub package of start up class's package

### 3. Initialize bean, component

  - Create bean instance
    
### 4. Inject bean, invoke method
   
  - invoke postConstructor method

### 5. Create application context
  - ApplicationContextHolder
  - ApplicationContext
  
### 6. In the near future
  - Support http handle request for web application
  - Support more annotation
  - Support Bean scope: proxy, singleton, request
  - Support PreDestroy, StartUp annotation
  - etc
  
  # Installation
  maven
```
  <dependency>
      <groupId>me.geardao</groupId>
      <artifactId>cute-ioc</artifactId>
      <version>1.0</version>
  </dependency>
  
```
```
    public static void main(String[] args) {
        CuteIoc.run(Main.class);
    }
```
        