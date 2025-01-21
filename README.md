# Simulating Multilevel Feedback Queue Scheduling

This project simulates a multilevel feedback queue (MLFQ) scheduling algorithm and reports performance metrics on randomly generated workloads. The assignment is divided into two main parts: a workload generator and a simulator that applies the scheduling algorithm.

## Project Overview

The goal of this project is to simulate a multilevel feedback queue scheduling algorithm with multiple queues, each implementing different scheduling algorithms such as Round Robin (RR), Shortest-Remaining-Time First (SRTF), and First-Come-First-Served (FCFS). Additionally, a workload generator creates random processes with various CPU and I/O burst times, which the simulator uses for scheduling.

## Part 1: Workload Generator

The workload generator generates a random set of processes and saves them to a file in the specified format. Each process consists of a PID, arrival time, and a series of CPU bursts and I/O bursts.

### File Format

Each line in the generated file represents a process, with its attributes as follows:

PID Arrival Time CPU Burst IO Burst CPU Burst IO Burst CPU Burst

### Workload Generator Parameters

- **Number of processes**: Integer value that specifies how many processes to generate.
- **Max arrival time**: Specifies the maximum possible arrival time for processes (randomly chosen within the range [0, Max arrival time]).
- **Max No. of CPU Bursts**: Specifies the maximum number of CPU bursts for each process (randomly generated between 1 and Max No. of CPU Bursts).
- **[Min IO, Max IO]**: Range for I/O burst duration, randomly chosen.
- **[Min CPU, Max CPU]**: Range for CPU burst duration, randomly chosen.

## Part 2: Simulator

The simulator reads the generated workload file and simulates a multilevel feedback queue scheduling algorithm with four different queues:

### Queue Structure

- **Queue #1**: Round Robin (RR) with time quantum q1.
- **Queue #2**: Round Robin (RR) with time quantum q2.
- **Queue #3**: Shortest-Remaining-Time First (SRTF).
- **Queue #4**: First-Come-First-Served (FCFS).

The user can specify values for q1, q2, and α (used to predict the duration of the next CPU burst in the SRTF algorithm).

### Queue Transition Rules

1. A new process enters Queue #1. If it does not finish its CPU burst within 10 time-quanta, it is moved to Queue #2.
2. Queue #2 processes can receive at most 10 time-quanta for their CPU burst. If they do not finish, they are moved to Queue #3.
3. Queue #3 uses the Shortest-Remaining-Time First (SRTF) algorithm for scheduling. If a process is preempted 3 times by other processes entering Queue #3, it is moved to Queue #4, where it is scheduled using the FCFS algorithm.
4. Queue #1 has the highest priority, followed by Queue #2, Queue #3, and Queue #4.

### Scheduling Flow

1. Queue #1 is processed first; if it's empty, Queue #2 is processed.
2. Processes from Queue #2 preempt processes in Queue #3.
3. Processes in Queue #3 can be preempted by new processes in Queue #1.
4. After each CPU burst, processes move to I/O, and then return to the queue from which the last I/O burst was made.

### User Interaction

The user can pause the simulation at any time and view the processes in each queue, the currently running process, and processes performing I/O.

### Outputs

- **Gantt Chart**: Displaying the execution timeline of processes.
- **CPU Utilization**: Calculated from the simulation.
- **Average Waiting Time**: Computed for all processes.

## Program Flow

### 1. Workload Generation
- The user specifies the parameters (e.g., number of processes, max arrival time, burst ranges).
- The generator produces a file with randomly generated processes based on these parameters.

### 2. Simulation
- The user loads the generated file or uses an existing file as input.
- The simulation follows the multilevel feedback queue rules for scheduling.
- The program reports:
  - Processes in each queue at any given time.
  - CPU utilization.
  - Gantt chart of the process execution.
  - Average waiting time.

### 3. Viewing the Output
After the simulation ends, the following are generated:
- **Gantt Chart**: A visual representation of the process scheduling.
- **CPU Utilization**: The percentage of time the CPU is actively processing tasks.
- **Average Waiting Time**: The average time a process spends in the queue before its execution.
