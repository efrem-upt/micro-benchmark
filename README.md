# micro-benchmark
Java program that measures the CPU Time of a computer by performing several tests and calculating a score. Small project made during my second year of Computer Engineering studies at Polytechnic University of Timișoara. Technologies used:
* [Java 17](https://www.oracle.com/java/technologies/javase-downloads.html)
* [JavaFX 18](https://openjfx.io/openjfx-docs/) (as GUI)
* [Maven](https://maven.apache.org/) (build tool)
* [Google Firebase](https://firebase.google.com/) (as Database, for calculation of user score)

## Running the program

Run the program by double-clicking the jar file or executing the following Terminal command: 

```bash
  java -jar "Dragos's Micro-Benchmark App.jar"
```
The user shall be presented with the following screen: 

![Welcome Screen](https://lh6.googleusercontent.com/Asti9OPZNnhLzxXAMnhYajbtDoC8Yw4-jv8GSQThh59y-ERGFnx9UIGcEz59eCOf8bxUZ_KxfdIncA=w1920-h932)

Pressing on the `Start Benchmark` button will start the benchmark and do five tests: `N Queens Problem (Backtracking)`, `Fast Fourier Transform`, `Factorization Algorithm`, `Large Matrix Dot Product`, `Large Matrix Tensor Product`. More tests can be added by following the same procedures as the first five.

When the tests are completed, the user's general peformance (`CPU Time Average measured for all tasks`) are sent to a Google Firebase database. This is in order to calculate how "good" the PC is based on other PC's performances that ran the same program.

*Note: Averages are geometric. This also includes `CPU Time Average measured for all tasks`. This one is calculated using the `Weighted Geometric Average`. Each test has been given the following weights:*
 - `N Queens Problem (Backtracking)`: 0.05% per test, 100 tests runned;
 - `Fast Fourier Transform`: 0.25% per test, 100 tests runned;
 - `Factorization Algorithm`: 0.3% per test, 100 tests runned;
 - `Large Matrix Dot Product`: 0.5% per test, 50 tests runned;
 - `Large Matrix Tensor Product`: 0.3% per test, 50 tests runned;

*This is so the score can be a weighted score. Being weighted, `CPU Time Average measured for al tasks`'s meaning is based on the meaning of the weights. They have been chosen such that more demanding tests are worth more than easier tests.*

*Note 2: For the CPU Time calculation the getThreadCpuTime method from the java.lang.management.ThreadMXBean class has been used. It measures CPU Time on a given thread by a certain id, so every time the test is run, it had to be put in its thread, and save the CPU Times calculated for each iteration in a list to be manipulated later statistically. Basically, several synchronization operations between threads had to be performed to achieve all the functionalities of the application.*
## Features

- Calculates three versions of CPU Time for each test: `CPU Time Average`, `CPU Time Max`, `CPU Time Total`. Data is presented in a table at the end of the process.
- Integrated with Google Firebase for keeping track of users overall score via `CPU Time Average measured for all tasks`.
- Shows a score of how good the PC is doing against other computers that ran the program (via Firebase array fetching and position of user in the array).
- Optional internet connection. Internet connection is only required for scoring purposes. The score will not be shown for users with no internet connection. However, the main results of the application are available.

## Demo

![hippo](https://lh5.googleusercontent.com/pl3ta4wo3onqBXS5D2P9UMVrzGnIpE8A7Z_uH_ZAZNb6yRORlF-Sda4622WIYpNnk8l2nAqVvUwpMg=w1920-h932-rw)
## Screenshots

![Result Screen 1](https://lh5.googleusercontent.com/wUsXjko7vWB3FDuBFzgumhQrlUG5gWIwRsFZuIipZaa789FgE_PwH1qbVzveGuXPLBvkvVzeh9Ppqw=w1920-h230)
![Result Screen 2](https://lh4.googleusercontent.com/AH3sKdhp2kP9NHDogyWGwkRRgU0BJhdXqOaAiVpF9tmzPwTk5fVdiMIeIYXyOLrQ7Zj5LwylskC6LQ=w1920-h230)


## License

[GNU GPLv3](https://choosealicense.com/licenses/gpl-3.0/)

