## Exercises about computational logic in with 2P-Kt

Part of the lecture "On the role of computational logic in MAS: practice with 2P-Kt 4 hours" 

### Useful resources

- Lecture Slides: https://github.com/gciatto/eass23-cl-in-mas
- 2P-Kt Documentation: https://github.com/tuProlog/2p-kt-presentation/
- Kotlin Documentation: https://kotlinlang.org/docs/home.html
  - Sequences: https://kotlinlang.org/docs/sequences.html

### Order of exercises

1. Exercise 1 in `./exercise-1` is about terms
    - 101 for terms' creation and usage in 2P-Kt

2. Exercise 2 in `./exercise-2` is about the Herbrand universe
   - advanced, **optional**

3. Exercise 3 in `./exercise-3` is about Horn clauses
   - 101 for clauses' creation and usage in 2P-Kt

4. Exercise 4 in `./exercise-4` is about the manipulation of both terms and clauses
   - advanced, **optional**

5. Exercise 5 in `./exercise-5` is about the manipulation of logic theories
   - advanced, **optional**

### Workflow

1. Listen to the teacher's explanation

2. Meanwhile, import the current folder as a project into your preferred IDE
   - if the IDE is IntelliJ or Eclipse, please import the project as **a Gradle project**

3. Look for specific TODOs in the source code directory of each exercise
   - exercises consist of unit test which can be individually run
   - to run a test you can either
     * press the "Play" button
     * run tests from the command line:
       ```bash
       ./gradlew exercise-N:test
       ```

5. Inspect and understand the provided unit tests
    - each single line has a meaning: ask for help if a line/test/suite is unclear

6. Address the `TODO` and re-run the tests

7. When all tests pass, the exercise is done
