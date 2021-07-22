# LuckyCalendar

## Running Code

### Locate JDK

First, you need to figure out where your installation of the Java Development
Kit (JDK) is located so you can run the java compiler from that directory or add
it to your PATH variable.

#### Windows

Typically, in Windows operating systems, the file is located in a directory like

`C:\Program Files\Java\jdk1.8.0_161\`

### Compiling Code

In a command prompt or terminal, move to the parent directory of this project,
i.e. LuckyCalendar/, and run the compiler located in the JDK path. 

For example, this is what it might look like:

#### Windows

```
"C:\Program Files\Java\jdk1.8.0_161\bin\javac.exe" -d Output -classpath Source Source\LuckyCalendar.java
```

#### Linux
Alternatively, in Linux, you might see command that looks more like these:
```
/usr/bin/javac -d Output -classpath Source Source/LuckyCalendar.java

javac -d Output -classpath Source Source/LuckyCalendar.java
```

#### Command Line Arguments

- We either run the the compilers with an absolute directory or from the PATH.
- The java compiler `javac` is located in the bin/ directory of the JDK
directory.
- The `-d Output` parameter selects the Ouptut directory for the destination of
all generated class files.
- The `-classpath Source` parameter selects the location of all sources files to
be used in compilation.
- The final `Source\LuckyCalendar.java` parameter indicates the relative path of
the file that we are looking to compile.

NOTE: All classes that are depended upon will be compiled automatically when
trying to compile the target file with the main function.

### Running Code

Much like compiling code, we use the JDK folder to run locate the java binary
for running the code.


#### Windows

```
"C:\Program Files\Java\jdk1.8.0_161\bin\java.exe" -cp "Output;Assets" LuckyCalendar
```

#### Linux
Alternatively, in Linux, you might see command that looks more like these:
```
/usr/bin/java -cp "Output;Assets" LuckyCalendar
```
```
java -cp "Output;Assets" LuckyCalendar
```

#### Command Line Arguments

- We either run the the compilers with an absolute directory or from the PATH.
- The java virtual machine runner `java` is located in the bin/ directory of the
JDK directory.
- The `-cp "Output;Assets"` parameter selects the location of all class files
and required assets to be used in program execution.
- The final `LuckyCalendar` parameter indicates the name of the class (which
contains a main() function) that is to be executed.

NOTE: Because we specify the Assets folder in the classpath during execution,
the classloader is able to locate the files within it, without knowing their
exact path in code.


## Testing Code

See above for information on how to find the JDK folder and how to compile code
generally. Tests are kept in separate directories so as to keep the final jar
for the project as lean as possible. Tests are group by individual classes or
inherited classes. They can all be run individually or collectively using the
special AllTests class (which will need updating for every new test class that
gets added).

### Windows

```
"C:\Program Files\Java\jdk1.8.0_161\bin\javac.exe" -d TestOutput -classpath "Source;TestSource" TestSource\*.java
```
```
"C:\Program Files\Java\jdk1.8.0_161\bin\java.exe" -cp "TestOutput" AllTets
```
or
```
"C:\Program Files\Java\jdk1.8.0_161\bin\java.exe" -cp "TestOutput" TimeIntervalTest
```

### Linux

```
"javac" -d TestOutput -classpath "Source;TestSource" TestSource/*.java
```
```
java" -cp "TestOutput" AllTets
```
or
```
"java" -cp "TestOutput" TimeIntervalTest
```
