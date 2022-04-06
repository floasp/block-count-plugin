# -*- coding: utf-8 -*-
"""
Created on Tue Jan 18 13:28:39 2022

@author: Florian
"""

import numpy as np
import matplotlib.pyplot as plt

f = open("8215386904963541759_8000x8000.txt", "r")
text = f.read()
text = text.split("\n")

labels = ["coal", "copper", "lapis", "iron", "redstone", "diamond", "gold", "emerald", "amethyst"]
colors = ["black", "orange", "blue", "grey", "red", "cyan", "yellow", "lime", "violet"]

xs = []
ys = [[], [], [], [], [], [], [], [], []]

for i in range(4):
    print(text[i])
for i in range(4, len(text)-1):
    print(text[i])
    values = text[i].split(",")
    xs.append(int(values[0]))
    for v in range(9):
        ys[v].append(int(values[v+1]))


f = plt.figure(figsize=(9, 4), dpi=600)
plt.title("Minecraft 1.18 Ore distribution")
plt.xlabel("height")
plt.ylabel("amount")


for i in range(len(labels)):
    index_max = np.argmax(ys[i])
    print("optimal height for " + labels[i] + ": " + str(index_max - 64))
    plt.plot(xs, ys[i], label=labels[i], color=colors[i])

plt.legend()
plt.show()