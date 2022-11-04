# TaskManager
The aim of the project is to create a console (non-GUI) application for managing tasks.<br/>

## TaskManager has following features:
- loading data from a tasks.csv file separated by "," at application startup,
- listing all available tasks,
- adding a new task
- removing a task
- saving data to file and exit from the application

## Data validation in TaskManager:
- removeTask: the entered numeric value is validated if is non-negative and not bigger than number of available tasks.

## To improve:
#### > listTask: 
- sorting by data
- red font in tasks with previous date
- yellow font in tasks with current date
- green font in tasks with future date

#### > addTask:
- check if a string is a date value
- check if task is due
- check if a string is a boolean value 

#### > removeTask:
- add confirmation to remove task

#### > csv file:
- change csv delimiter from "," to "|"
 
## Sample tasks.csv file:
Simple task - very important, 2020-03-09, true<br/>
Second task not so important, 2020-05-10, false<br/>
Throw away trash, 2020-03-09, false<br/>

## IDE:
IntelliJ IDEA 2022.2.2
