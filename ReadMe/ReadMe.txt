EDIT: 
updated to actually display the output in case of the response being O(n) or O(1) which it seemed to not do unless it was nested for loops.
(took only one if statement)


This Big O notation solver was written by 
Faraz Daneshpour and Jacob Nelson

-It can be run with the runme.bat or with (java -jar bigO.jar)

-It will read all texts files in the same directory, and if they are formatted properly it will attempt to find the bigO notation.


Note:
It has been tested to work with 1 for loop, but has been written to handle (theoretically) "infinitely" nested for loops (no recursion used).  Keep in mind this is in java, and there are possibilities of memory leaks.  

I haven't thoroughly tested the infinitely nested for loops but it did pretty well against what I tested it with.  

By the way this is the version you said that if anyone was able to do, you'd give them an A in the class. 