
# Fido User Guide
Fido is a CLI tasks tracking application designed for people to keep track of their upcoming
todos, events and deadlines. For experienced CLI users, they can enter their
tasks faster compared to GUI applications
* [Quick Start](#quick-start)
* [Features](#features)
  * [list](#1-listing-all-the-tasks-list-)
  * [todo](#2-add-a-todo-todo)
  * [deadline](#3-add-a-deadline-deadline)
  * [event](#4-add-a-event-event)
  * [mark](#5-mark-a-task-mark)
  * [unmark](#6-unmark-a-task-unmark)
  * [delete](#7-delete-a-task-delete)
  * [find](#8-find-tasks-find-)
  * [bye](#9-exit-the-program-bye)
* [Saving the data](#saving-the-data)
* [Editing the data](#editing-the-data)
* [Known issues](#known-issues)

## Quick Start
* Ensure you have Java 11 or above installed in your Computer.
* Download the latest fido.jar from the github release.
* Open a command terminal, create a new folder to put the jar file inside,<br>
  change directory to the created new folder using `cd` or `dir` and use <br>
  `java -jar fido.jar` to run the application
* Refer to the features below for details of each command
## Features

### 1. Listing all the tasks: list 
#### **_SYNOPSIS_**
```dtd
list
```
#### **_DESCRIPTION_**
Lists the tasks tracked by the application
#### **_USAGE_**
Example of usage: <br>
```dtd
list
```
Expected output: <br>

```dtd
------------------------------------
1. [T][ ] task1
2. [E][ ] add more tasks (from: 6pm, to: 89)

------------------------------------
```
<br>

### 2. Add a ToDo: todo
#### **_SYNOPSIS_**
```dtd    
todo [description_of_task]
```
#### **_DESCRIPTION_**
Adds a todo to the list of tasks tracked by Fido.
A todo should only consist of a task description.
#### **_USAGE_**
Example of usage: <br>
```dtd
todo attend a concert
```
Expected output: <br>
```dtd
------------------------------------
added: [T][ ] attend a concert
Now you have 1 tasks in the list
------------------------------------
``` 
<br>

### 3. Add a Deadline: deadline
#### **_SYNOPSIS_**
```dtd
deadline [description_of_task] /by [time]
```
#### **_DESCRIPTION_**
Adds a deadline to the list of tasks tracked by Fido.
<br>A deadline should only consist of a task description and a time to finish the task by.
#### **_OPTIONS_**
* `/by [time]`<br>
  Specifies the time to complete the task by. The format can be in any String format.

#### **_USAGE_**
Example of usage: <br>
```dtd
deadline finish cs2113 tutorial /by 9'oclock
```
Expected output: <br>
```dtd
------------------------------------
added: [D][ ] finish cs2113 tutorial (by: 9'oclock)
Now you have 1 tasks in the list
------------------------------------
```
<br>

### 4. Add a Event: event
#### **_SYNOPSIS_**
```dtd    
event [description_of_task] /from [start_time] /to [end_time]
```
#### **_DESCRIPTION_**
Adds an event to the list of tasks tracked by Fido.
<br>A event should only consist of a task description, the start time of a task and the end time of the task.
#### **_OPTIONS_**
* `/from [start_time]`<br>
  Specifies the start time the event starts. The format can be in any String format.
* `/to [end_time]`<br>
    Specifies the end time the event ends. The format can be in any String format.

#### **_USAGE_**
Example of usage: <br>
```dtd
event attend lecture /from 4pm /to 6pm
```
Expected output: <br>
```dtd
------------------------------------
added: [E][ ] attend lecture (from: 4pm, to: 6pm)
Now you have 1 tasks in the list
------------------------------------
```
<br>

### 5. Mark a task: mark
#### **_SYNOPSIS_**
```dtd    
mark [index_of_task]
```
#### **_DESCRIPTION_**
Marks a tasks as completed in the list of tasks tracked by Fido. <br>
The index of the task to mark should be derived from the output of the `list` command
<br> and must be a positive integer.
#### **_USAGE_**
Example: Original list of tasks using `list` command:
```dtd
------------------------------------
1. [E][ ] attend lecture (from: 4pm, to: 6pm)

------------------------------------
```
Example of usage: <br>
```dtd
mark 1
```
Expected output: <br>
```dtd
------------------------------------
uwu  marked the task as done: [E][X] attend lecture (from: 4pm, to: 6pm)
------------------------------------
``` 
Updated list of tasks using `list` command:
```dtd
------------------------------------
1. [E][X] attend lecture (from: 4pm, to: 6pm)

------------------------------------
```
<br>

### 6. Unmark a task: unmark
#### **_SYNOPSIS_**
```dtd    
unmark [index_of_task]
```
#### **_DESCRIPTION_**
Marks a tasks as incomplete in the list of tasks tracked by Fido. <br>
The index of the task to mark should be derived from the output of the `list` command
<br> and must be a positive integer.
#### **_USAGE_**
Example: Original list of tasks using `list` command:
```dtd
------------------------------------
1. [E][X] attend lecture (from: 4pm, to: 6pm)
2. [T][ ] cook food
3. [D][X] eat cooked food (by: 3pm)

------------------------------------
```
Example of usage: <br>
```dtd
mark 3
```
Expected output: <br>
```dtd
------------------------------------
:( marked the task as not done yet: [D][ ] eat cooked food (by: 3pm)
------------------------------------
``` 
Updated list of tasks using `list` command:
```dtd
------------------------------------
1. [E][X] attend lecture (from: 4pm, to: 6pm)
2. [T][ ] cook food
3. [D][ ] eat cooked food (by: 3pm)

------------------------------------
```
<br>

### 7. Delete a task: delete
#### **_SYNOPSIS_**
```dtd    
delete [index_of_task]
```
#### **_DESCRIPTION_**
Deletes a task from the list of tasks tracked by Fido. <br>
The index of the task to mark should be derived from the output of the `list` command
<br> and must be a positive integer.
#### **_USAGE_**
Example: Original list of tasks using `list` command:
```dtd
------------------------------------
1. [E][X] attend lecture (from: 4pm, to: 6pm)
2. [T][ ] cook food
3. [D][ ] eat cooked food (by: 3pm)

------------------------------------
```
Example of usage: <br>
```dtd
delete 2
```
Expected output: <br>
```dtd
------------------------------------
I have deleted: 
[T][ ] cook food
Now you have 2 tasks in the list
------------------------------------
``` 
Updated list of tasks using `list` command:
```dtd
------------------------------------
1. [E][X] attend lecture (from: 4pm, to: 6pm)
2. [D][ ] eat cooked food (by: 3pm)

------------------------------------
```
<br>

### 8. Find tasks: find 
#### **_SYNOPSIS_**
```dtd    
find [keywords_to_search_for]
```
#### **_DESCRIPTION_**
Finds the tasks containing `[keywords_to_search_for]` **in the task description** from list of tasks tracked by Fido, <br>
and outputs it to the user. <br>
The index of the task to mark should be derived from the output of the `list` command
#### **_USAGE_**
Example:
Original list of tasks using `list` command:

```dtd
------------------------------------
1. [T][ ] todo one
2. [T][X] todo 2
3. [D][ ] deadline 1 (by: 9pm)
4. [D][X] deadline two (by: 10pm)
5. [E][ ] event1 (from: 8am, to: 9pm)
6. [E][ ] get two concert tickets (from: 7am, to: 9pm)

------------------------------------
```
Example of usage: <br>
```dtd
find two
```
Expected output: <br>
```dtd
------------------------------------
Here are the matching tasks: 
1. [D][X] deadline two (by: 10pm)
2. [E][ ] get two concert tickets (from: 7am, to: 9pm)

------------------------------------
``` 

### 9. Exit the program: bye
#### **_SYNOPSIS_**
```dtd
bye
```
#### **_DESCRIPTION_**
Exits the fido application
#### **_USAGE_**
Example of usage: <br>
```dtd
bye
```
Expected output: <br>
```dtd
------------------------------------
Bye. Hope to see you again soon!
------------------------------------
```
<br>

## Saving the data
All task data input by the user is automatically saved by the app following any changes to <br>
the data in memory in the file `./data/tasklist.txt`. Ensure that the file is not corrupted. If the program exits on start up with the <br>
following line being displayed:
```dtd
error regarding file IO
```
delete the data file and restart the application. This will remove all data stored in the data file <br>
and allow for a clean start. Please also ensure that the user you are using has permission to the <br>
directory that the `fido.jar` file is in.

If the file is corrupted, the current data file will be discarded and a new file will be created

## Editing the data
It is highly not recommended to edit the data file as wrongly editing the file will lead to file corruption <br>
which will result in the user having to delete the file, losing all the data

## Known issues
* it is known that adding \` in any of the task fields will lead to an error in the file parsing <br>
  This is because the \` symbol is used as a file delimiter when saving the tasks in file format <br>
  As such, the user should avoid using \` in the task fields