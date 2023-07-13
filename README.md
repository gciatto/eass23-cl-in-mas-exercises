## Exercises about computational logic in with 2P-Kt

Part of the lecture "On the role of computational logic in MAS: practice with 2P-Kt 4 hours" 

### Useful resources

- Lecture Slides: https://github.com/gciatto/eass23-cl-in-mas
- 2P-Kt Documentation: https://github.com/tuProlog/2p-kt-presentation/
- Kotlin Documentation: https://kotlinlang.org/docs/home.html
  - Sequences: https://kotlinlang.org/docs/sequences.html

### Order of exercises

* Exercise 1 in `./exercise-1` is about terms
    - __goal__: practice with the creation and usage of terms in 2P-Kt

* Exercise 2 in `./exercise-2` is about the Herbrand universe
    - __goal__: write a lazy algorithm for computing the Herbrand universe corresponding to some given set
    of functors and their corresponding arities
    - advanced, **optional**

* Exercise 3 in `./exercise-3` is about Horn clauses
    - __goal__: practice with the creation and usage of clauses in 2P-Kt

* Exercise 4 in `./exercise-4` is about the manipulation of both terms and clauses
    - __goal__: write an algorithm aimed at analysing any given theory of logic rules and facts, in order to
    detect the sets of functors, predicate names, and variables names therein contained 
    - advanced, **optional**

* Exercise 5 in `./exercise-5` is about substitutions
    - __goal__: practice with the creation and usage of substitutions in 2P-Kt

* Exercise 6 in `./exercise-6` is about unification
    - __goal__: practice with the exploitation of unification in 2P-Kt 

* Exercise 7 in `./exercise-7` is about clauses retrieval from theories, via unification
    - __goal__: practice with the exploitation of unification as a means to query theories

* Exercise 8 in `./exercise-8` is about the manipulation of logic theories
    - __goal__: write an algorithm aimed at relationalising any logic theory in propositional form 
    - advanced, **optional**

* Exercise 9 in `./exercise-9` is about resolution
    - __goal__: exploit 2P-Kt functionalities to implement pure logic solvers, leveraging on various proof-tree 
    exploration strategies
    - advanced

* Exercise 10 in `./exercise-10` is about resolution with side effects
    - __goal__: extend the logic solver from exercise 9 to support Prolog-like meta predicates such as:
      + `not(P)` which fails if predicate `P` is true
      + `assert(C)` which adds clause `C` to the solver's knowledge base
      + `retract(C)` which removes clause `C` from the solver's knowledge base
      + `is(X, E)` which assigns to variable `X` the value attained by evaluating expression `E`
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

4. Inspect and understand the provided unit tests
    - each single line has a meaning: ask for help if a line/test/suite is unclear

5. Address the `TODO` and re-run the tests

6. When all tests pass, the exercise is done
