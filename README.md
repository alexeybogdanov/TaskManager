# TaskManager

Due to lack of time I tried to keep it as simple as possible. The app can be 'runned' from main method, the tests are added for verifying base functoinality. 

Max size and customer approach can be changed in 'config.properties' file.


With Task Manager we refer to a software
component that is designed for handling multiple
processes inside an operating system. Each process
is identified by 2 fields, a unique unmodifiable
identifier (PID), and a priority (low, medium, high).
The process is immutable, it is generated with a
priority and will die with this priority – each process
has a kill() method that will destroy it

* Add a process
* List running processes
* Kill/KillGroup/KillAll

## Base approach
The default behaviour is that we can
accept new processes till when there is capacity
inside the Task Manager, otherwise we won’t accept
any new process

## FIFO
A different customer wants a different behaviour:
he’s asking to accept all new processes through the
add() method, killing and removing from the TM list
the oldest one (First-In, First-Out) when the max size
is reached

## Priority Based
A new customer is asking something different again,
every call to the add() method, when the max size is
reached, should result into an evaluation: if the new
process passed in the add() call has a higher priority
compared to any of the existing one, we remove the
lowest priority that is the oldest, otherwise we skip it

### List running processes
The task manager offers the possibility to list() all the
running processes, sorting them by time of creation
(implicitly we can consider it the time in which has
been added to the TM), priority or id.

### Model one or more methods capable of

* killing a specific process
* killing all processes with a specific priority
* killing all running processes
