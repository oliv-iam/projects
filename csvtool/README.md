#### Final Project Description

##### About: 
My project is intended as a convenient way for viewing and manipulating data. It reads from a CSV file, displays the data in a readable format, and gives the user options for filtering and sorting depending on specification. When they have finished, it exports the result to another CSV. 

##### Data: 
My program can use any data generally in a table format (with named columns and some rows).

##### Data Structures: 
I used a priority queue and a binary search tree for my alternative versions. For each, they are used for storing the data rows in a useful order. Currently I am using them for sorting rows in the table.  

##### Instructions: 
Run the program using the following command: 

```java Main <input> <output>```

An example, using an included CSV as input:

 ```java Main memtrace.csv memtrace-out.csv```

After reading from the CSV file, the program gives the user options for its next action. It should mostly be self-explanatory from then on.  Note that user input does not to be wrapped in quotations and that when asked to give values separated by commas, for example, the program expects the format ```value1,value2,value3``` (no spaces or quotations.) The program ends and generates a csv to the output directory when the user selects ```done```. 

Another note: the viewer opens vim as a subprocess, so that portion of the program might not work well outside of a Unix shell. It should still generate the visualization as a file, however, which could be viewed separately. 

Choose the data structure (priority queue vs. binary search tree) by commenting out one of the lines at the top of Main.java: (this code selects priority queue)
```
static String DATA_STRUCTURE = "priority-queue";
// static String DATA_STRUCTURE = "binary-search-tree";
```

##### Results: 
I found that the version using a priority queue was significantly more efficient. When I timed the sorting operation for the example run above, sort took 4848728 ns for the priority queue and 8139900 ns for the BST (BST implementation took ~68% longer to run).

I was surprised, since I expected them to run in around similar time due to the larger number of operations required for sorting with the priority queue. It does make sense however, due to the difference in time complexity for necessary operations (add and remove run in O(logn) for priority queue, and in O(n) for an unbalanced binary search tree, meaning in the worst case sorting is O(nlogn) for priority queue and O(n^2) for BST). I also implemented the priority queue using an array, and the BST using nodes, which likely contributed to the difference in time. 

##### Future: 
I have a few ideas for improving and extending my program, for example:
- Support parsing and different file formats
- Possibly increase efficiency by reading the file straight to the data structure, instead of going through a 2D array
- Additional operations, such as filtering rows using a given condition, counting matches, etc. This would make better use of my data structure implementations, since they would be used for more than sorting (and wouldn't need to be reloaded with each use).

##### Acknowledgments: 
I used the documentation for libraries I used and looked at some code examples as well (I don't have specific examples or websites to provide). 