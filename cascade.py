from sets import Set
import Queue
import random
import sys

q = Queue.Queue()

mapp = dict()
infl = []
graph = dict() #int->list
visited = []

index = 0
#read topic influence file
with open("topic_influence.txt") as tin:
    for line in tin:
        ws = line.split()
        mapp[ws[0]] = index
        infl.append(ws[1])
        index = index + 1

start = mapp[sys.argv[1]]

#read graph
with open("final_list.txt") as fin:
    for line in fin:
        words = line.split(" ")
        graph[mapp[words[0]]] = []
        for i in range(len(words)-1):
            graph[mapp[words[0]]].append(mapp[words[i+1].strip()])

for i in range(index):
    visited.append(0)

active = 0
#calculate number of activated nodes

q.put(start)
visited[start] = 1
while not q.empty():
    par = q.get()
    for child in graph[par]:
        if(visited[child]==0):
            visited[child] = 1
            rand = random.uniform(0.0,0.01)
            if(float(rand) <= float(infl[par])):
                active = active + 1
                q.put(child)

print active




