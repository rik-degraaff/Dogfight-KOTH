#!/usr/bin/env python

import sys

log = open("dumb_log.txt", "w")
while 1:
    # read in a line, log it
    line = sys.stdin.readline()
    log.write(line)
    if line.startswith("NEW TURN"):
        # each turn, read in four plane states, log them
        log.write(sys.stdin.readline())
        log.write(sys.stdin.readline())
        log.write(sys.stdin.readline())
        log.write(sys.stdin.readline())
        # two dumb moves
        print "N 0 0"
        sys.stdout.flush()
        print "N 0 0"
        sys.stdout.flush()
        # log those dumb moves
        log.write("N 0 0\n")
        log.write("N 0 0\n")
    log.flush()
