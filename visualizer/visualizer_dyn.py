# -*- coding: utf-8 -*-
"""
Created on Tue Jan 18 13:28:39 2022

@author: Florian
"""

import numpy as np
import matplotlib.pyplot as plt

f = open("-6870520713983956494_long.txt", "r")
text = f.read()
text = text.split("\n")

label_text = text[3]
labels = label_text.split(",")[1:]

# labels = ["coal", "copper", "lapis", "iron", "redstone", "diamond", "gold", "emerald", "amethyst"]
# colors = ["black", "orange", "blue", "grey", "red", "cyan", "yellow", "lime", "violet"]

xs = []
ys = []
for i in range(len(labels)):
    ys.append([])

for i in range(5):
    print(text[i])
for i in range(5, len(text)-1):
    # print(text[i])
    values = text[i].split(",")
    xs.append(int(values[0]))
    for v in range(len(labels)):
        ys[v].append(int(values[v+1]))


f = plt.figure(figsize=(9, 4), dpi=600)
plt.title("Minecraft 1.18 Block distribution")
plt.xlabel("height")
plt.ylabel("amount")


for i in range(len(labels)):
    if labels[i] in ["OBSIDIAN"]:
        index_max = np.argmax(ys[i])
        print("optimal height for " + labels[i] + ": " + str(index_max - 64))
        # plt.plot(xs, ys[i], label=labels[i], color=colors[i])
        plt.plot(xs, ys[i], label=labels[i])

plt.legend()
plt.show()