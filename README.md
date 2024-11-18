# Supermarket Management Application

An interactive application that helps you manage supermarkets, products and optimized solutions
based on products similarity using Spring Shell.

---

## Description
This application lets you manage products, supermarkets and shelf configurations
efficiently.  
It counts with interactive commands through _Spring Shell_ to compute tasks like:  
- **Products and Characteristics Management:**  
  - Add characteristics to help building an optimal distribution for your supermarkets.  
  - Add products and customize them with characteristics and restrictions.
- **Supermarkets management:**  
  - Add supermarkets in order to create different environments and create infinite possibilities.  
- **Catalogues Management:**  
  - Add catalogues with different products to manage various inventories.
- **Shelves Management:**
  - Add shelves with different size and restrictions.
- **Similarities Management:**
  - Add or modify similarities between products manually or by characteristics.
- **Solution Management**
  - Generate, query or modify optimal distributions by similarity.
  
---

## Functionalities

Thanks to the interactive shell implemented by _Spring Shell_, you can check all the commands
and how they work while you interact.

- #### Use 'help' to check all the commands or 'command' --help to check a specific command.
- #### Add or log in a User with 'Admin' or 'User' privileges to start interacting.
- #### Add or select a supermarket before starting managing you supermarket functionalities.
- #### Once you have finished, you can write 'stop' to fully stop your program.

---

## Requisites

- Java 21+
- OS compatible with JVM

---

## Compiling

1. Use command **./gradlew bootJar** to compile the project.  
2. Execute the app with **java -jar build/libs/prop12-1-0.0.1-SNAPSHOT**
   1. In order to change the jar file name, you can go to  
   **settings.gradle.kts** and change **rootProject.name** value.


---

## Use

In the following section we'll show some commands you can use to help you start using the application.

```
  - Add a characteristic:
    
      add-char Organic
    
  - Add a product:
  
      add-product Apple
    
  - Add/remove a characteristic to a product:
    
    · Add
        mod-prod Apple Organic
    · Remove
        mod-prod Apple Organic -r
  
  - Modify similarity between products to 0.9 (1.0 - 0.0)
    
      mod-sim Apple Bannana 0.90
      
  - Add a supermarket:
  
      add-super Consum
      
  - Add a shelf of 40 spaces:
  
      add-shelf Candies 40
      
  - Add a restriction to shelf space 10:
  
      add-rest Candies Haribo 10
      
  - Add a catalogue:
  
      add-cat Sweets
      
  - Add/remove a product to a catalogue:
  
    · Add
        mod-cat Sweets Phoskitos
    · Remove
        mod-prod Sweets Phoskitos -r
        
  - Generate a distribution:
  
      generate-solution Solution1 Candies Sweets algorithm heuristic
        · Algorithm: (Backtracking/HillClimbing/Greedy)
        
        · Heuristic: (1 - Similarity by characteristics / 2 - Modified similarities)   
```

---

## Credits

Created by group 12.1:

- Odei Garcia Miralles
- Marc Rams Estrada
- Victor Ramirez Arimaha
- Marc Esteve Rodriguez
