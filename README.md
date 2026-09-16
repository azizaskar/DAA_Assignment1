# Fast Sorting & Selection Engine (DAA Assignment 1)

## Overview
Implementation and empirical analysis of Divide-and-Conquer algorithms:
* **MergeSort**: Reusable buffer and insertion sort cutoff (<= 15).
* **QuickSort**: Random pivot, 3-way partitioning, and bounded recursion depth.
* **QuickSelect**: Order-statistic selection using 3-way partitioning.

## Build and Run

### Prerequisites
* Java JDK 17+ (or JDK 25)
* Maven 3.8+
* Python 3 with `pandas` and `matplotlib` (for plots)

### Run Tests
```bash
mvn clean test
mvn clean compile
mvn exec:java -Dexec.mainClass="daa.Main"

### Generate Plots
```bash
py plot.py
