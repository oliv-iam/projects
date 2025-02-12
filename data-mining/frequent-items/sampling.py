#!/usr/bin/env python3
import sys

def sampling(ssize: int, dsize: int, delta: float, thres: float, datapath: str) -> tuple[float, list[tuple[int, float]]]:
    pass

def main():
    if len(sys.argv) != 6:
        sys.exit("incorrect arguments")
    ssize = int(sys.argv[1])
    dsize = int(sys.argv[2])
    delta = float(sys.argv[3])
    thres = float(sys.argv[4])
    datapath = str(sys.argv[5])
    print(sampling(ssize, dsize, delta, thres, datapath))

if __name__ == "__main__":
    main()
