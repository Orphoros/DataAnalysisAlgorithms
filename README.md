# Data Analysis Algorithms

> Demonstration of various data analysis algorithms on several datasets
>
> In association with [SarenDev](https://github.com/SarenDev).

## About

This repo contains a demonstration of several dataset analysis methods for various tasks.

The application demonstrates the following tasks:

- Find a specific repair case using:
  - AVL search
  - Binary search
  - Sorted linear search
  - Unsorted linear search
- Find the most common incident type using:
  - Sorted linear search
  - Unsorted linear search
- Plan an efficient route from one location to another using Dijkstra's algorithm
- Plan an efficient route from one location through all other locations using Prim's algorithm

## Project structure:

The `src` folder have the following main sub-folders:
- [Cases](./src/nl/cds/cases/): Holds the various problems to solve
- [Data](./src/nl/cds/data/): Holds the main [DataViewer](./src/nl/cds/data/DataViewer.java) Java file that uses the cases to visualize them
- [Model](./src/nl/cds/model/): Holds custom data structures and records
- [Tools](./src/nl/cds/tools/): Holds the QuickSort algorithm and a data reader class

---

This project was made as a part of a university project.