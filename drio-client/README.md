### Building Client (memcount.c)
Note: requires cmake version 3.7 or higher, DynamoRIO installed, and environmental variable `DYNAMORIO_HOME`

```
cd ..
mkdir build
cd build
cmake -DDynamoRIO_DIR="$DYNAMORIO_HOME/cmake" ../drio-client
make memcount
cd ..
```
Then to run: `$DYNAMORIO_HOME/bin64/drrun -c build/libmemcount.so -- <executable>`

### Sample Output
```
Client memcount is running
...
Instrumentation results:
  saw 213032 memory references
  number of reads: 146947
  number of writes: 66085
```