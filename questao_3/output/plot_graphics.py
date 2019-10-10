import matplotlib.pyplot as plt
import csv
import statistics
import numpy as np

print('PLOTTING GRAPHS, WAIT A LITTLE BIT...')

## READING PROCESS DATA ##

proc_time_data_map = {}
proc_mem_data_map = {}

with open('procCreationTime.csv') as csvfile:
    plots = csv.reader(csvfile, delimiter=',')
    is_first = True
    for row in plots:
        if is_first:
            is_first = False
            continue
        # print(row)
        if not int(row[0]) in proc_time_data_map.keys():
            proc_time_data_map[int(row[0])] = []
            proc_mem_data_map[int(row[0])] = []
        proc_time_data_map[int(row[0])].append(float(row[1].lstrip()))
        proc_mem_data_map[int(row[0])].append(int(row[2].lstrip()))

with open('procCreationTimeSmall.csv') as csvfile:
    plots = csv.reader(csvfile, delimiter=',')
    is_first = True
    for row in plots:
        if is_first:
            is_first = False
            continue
        # print(row)
        if not int(row[0]) in proc_time_data_map.keys():
            proc_time_data_map[int(row[0])] = []
            proc_mem_data_map[int(row[0])] = []
        proc_time_data_map[int(row[0])].append(float(row[1].lstrip()))
        proc_mem_data_map[int(row[0])].append(int(row[2].lstrip()))

## READING THREADS DATA ##

thread_time_data_map = {}
thread_mem_data_map = {}

with open('threadCreationTime.csv') as csvfile:
    plots = csv.reader(csvfile, delimiter=',')
    is_first = True
    for row in plots:
        if is_first:
            is_first = False
            continue
        # print(row)
        if not int(row[0]) in thread_time_data_map.keys():
            thread_time_data_map[int(row[0])] = []
            thread_mem_data_map[int(row[0])] = []
        thread_time_data_map[int(row[0])].append(float(row[1].lstrip()))
        thread_mem_data_map[int(row[0])].append(int(row[2].lstrip()))

with open('threadCreationTimeSmall.csv') as csvfile:
    plots = csv.reader(csvfile, delimiter=',')
    is_first = True
    for row in plots:
        if is_first:
            is_first = False
            continue
        # print(row)
        if not int(row[0]) in thread_time_data_map.keys():
            thread_time_data_map[int(row[0])] = []
            thread_mem_data_map[int(row[0])] = []
        thread_time_data_map[int(row[0])].append(float(row[1].lstrip()))
        thread_mem_data_map[int(row[0])].append(int(row[2].lstrip()))

## DATA DISPLAY ##

keys = list(proc_time_data_map.keys())
keys.sort()

mean_proc_time_list = []
mean_proc_mem_list = []

for key in keys:
    mean_proc_time_list.append(statistics.mean(proc_time_data_map[key]))
    mean_proc_mem_list.append(statistics.mean(proc_mem_data_map[key]))

mean_thread_time_list = []
mean_thread_mem_list = []

for key in keys:
    mean_thread_time_list.append(statistics.mean(thread_time_data_map[key]))
    mean_thread_mem_list.append(statistics.mean(thread_mem_data_map[key]))

## RECT GRAPHS ##

plt.plot(keys, mean_proc_time_list, label='Processos')
plt.plot(keys, mean_thread_time_list, label='Threads')
plt.xlabel('Número de instâncias')
plt.ylabel('Tempo (s)')
plt.title('Tempo total médio de criação')
plt.legend()
plt.savefig('rect_proc_x_thread_time.png', bbox_inches='tight')
plt.clf()

plt.plot(keys, mean_proc_mem_list, label='Processos')
plt.plot(keys, mean_thread_mem_list, label='Threads')
plt.xlabel('Número de instâncias')
plt.ylabel('Memória Utilizada (KB)')
plt.title('Quantidade total média de memória utilizada')
plt.legend()
plt.savefig('rect_proc_x_thread_mem.png', bbox_inches='tight')
plt.clf()

## PER-THREAD AND PER-PROCESS DATA HISTOGRAMS

thread_hist_mem = [statistics.mean(np.array(thread_mem_data_map[key]) / key) for key in keys]
thread_hist_time = [statistics.mean(np.array(thread_time_data_map[key]) / key) for key in keys]
proc_hist_mem = [statistics.mean(np.array(proc_mem_data_map[key]) / key) for key in keys]
proc_hist_time = [statistics.mean(np.array(proc_time_data_map[key]) / key) for key in keys]

print(thread_hist_mem)
print(thread_hist_time)
print(proc_hist_mem)
print(proc_hist_time)

fig, axs = plt.subplots(1, 2, sharey=True, tight_layout=True)
# We can set the number of bins with the `bins` kwarg
axs[0].hist(thread_hist_mem, bins=20)
axs[1].hist(proc_hist_mem, bins=20)
plt.savefig('hist_mem.png', bbox_inches='tight')
plt.clf()

fig, axs = plt.subplots(1, 2, sharey=True, tight_layout=True)
# We can set the number of bins with the `bins` kwarg
axs[0].hist(thread_hist_time, bins=20)
axs[1].hist(proc_hist_time, bins=20)
plt.savefig('hist_time.png', bbox_inches='tight')
plt.clf()

## BOXPLOT GRAPHS ##

thread_box_mem = [thread_mem_data_map[key] for key in keys]
thread_box_time = [thread_time_data_map[key] for key in keys]
proc_box_mem = [proc_mem_data_map[key] for key in keys]
proc_box_time = [proc_time_data_map[key] for key in keys]

## 10 - 50 INSTANCES

## MEMORY

fig, ax = plt.subplots(nrows=1, ncols=2, figsize=(18, 8))
bp0 = ax[0].boxplot(thread_box_mem[:5])
bp1 = ax[1].boxplot(proc_box_mem[:5])
ax[0].set_xticklabels(keys[:5])
ax[1].set_xticklabels(keys[:5])
ax[0].set_title('Boxplots da quantidade de memória utilizada por Threads')
ax[1].set_title('Boxplots da quantidade de memória utilizada por Processos')
# Setting the scale to facilitate the data comparisson
# ax[0].set_ylim(top=ax[1].get_ylim()[1])
# ax[1].set_ylim(bottom=ax[0].get_ylim()[0])
for axe in ax:
    axe.yaxis.grid(True)
    axe.set_xlabel('Número de instâncias')
    axe.set_ylabel('Memória Utilizada (KB)')
plt.savefig('boxplot_10_50_mem.png', bbox_inches='tight')
plt.clf()

## TIME

fig, ax = plt.subplots(nrows=1, ncols=2, figsize=(18, 8))
bp0 = ax[0].boxplot(thread_box_time[:5])
bp1 = ax[1].boxplot(proc_box_time[:5])
ax[0].set_xticklabels(keys[:5])
ax[1].set_xticklabels(keys[:5])
ax[0].set_title('Boxplots do tempo de criação de Threads')
ax[1].set_title('Boxplots do tempo de criação de Processos')
# Setting the scale to facilitate the data comparisson
# ax[0].set_ylim(top=ax[1].get_ylim()[1])
# ax[1].set_ylim(bottom=ax[0].get_ylim()[0])
for axe in ax:
    axe.yaxis.grid(True)
    axe.set_xlabel('Número de instâncias')
    axe.set_ylabel('Tempo (s)')
plt.savefig('boxplot_10_50_time.png', bbox_inches='tight')
plt.clf()

## 60 - 100 INSTANCES

## MEMORY

fig, ax = plt.subplots(nrows=1, ncols=2, figsize=(18, 8))
bp0 = ax[0].boxplot(thread_box_mem[5:10])
bp1 = ax[1].boxplot(proc_box_mem[5:10])
ax[0].set_xticklabels(keys[5:10])
ax[1].set_xticklabels(keys[5:10])
ax[0].set_title('Boxplots da quantidade de memória utilizada por Threads')
ax[1].set_title('Boxplots da quantidade de memória utilizada por Processos')

# Setting the scale to facilitate the data comparisson
# ax[0].set_ylim(top=ax[1].get_ylim()[1])
# ax[1].set_ylim(bottom=ax[0].get_ylim()[0])
for axe in ax:
    axe.yaxis.grid(True)
    axe.set_xlabel('Número de instâncias')
    axe.set_ylabel('Memória Utilizada (KB)')
plt.savefig('boxplot_60_100_mem.png', bbox_inches='tight')
plt.clf()

## TIME

fig, ax = plt.subplots(nrows=1, ncols=2, figsize=(18, 8))
bp0 = ax[0].boxplot(thread_box_time[5:10])
bp1 = ax[1].boxplot(proc_box_time[5:10])
ax[0].set_xticklabels(keys[5:10])
ax[1].set_xticklabels(keys[5:10])
ax[0].set_title('Boxplots do tempo de criação de Threads')
ax[1].set_title('Boxplots do tempo de criação de Processos')
# Setting the scale to facilitate the data comparisson
# ax[0].set_ylim(top=ax[1].get_ylim()[1])
# ax[1].set_ylim(bottom=ax[0].get_ylim()[0])
for axe in ax:
    axe.yaxis.grid(True)
    axe.set_xlabel('Número de instâncias')
    axe.set_ylabel('Tempo (s)')
plt.savefig('boxplot_60_100_time.png', bbox_inches='tight')
plt.clf()

## 1K - 5K INSTANCES

## MEMORY

fig, ax = plt.subplots(nrows=1, ncols=2, figsize=(18, 8))
bp0 = ax[0].boxplot(thread_box_mem[10:15])
bp1 = ax[1].boxplot(proc_box_mem[10:15])
ax[0].set_xticklabels(keys[10:15])
ax[1].set_xticklabels(keys[10:15])
ax[0].set_title('Boxplots da quantidade de memória utilizada por Threads')
ax[1].set_title('Boxplots da quantidade de memória utilizada por Processos')
# Setting the scale to facilitate the data comparisson
# ax[0].set_ylim(top=ax[1].get_ylim()[1])
# ax[1].set_ylim(bottom=ax[0].get_ylim()[0])
for axe in ax:
    axe.yaxis.grid(True)
    axe.set_xlabel('Número de instâncias')
    axe.set_ylabel('Memória Utilizada (KB)')
plt.savefig('boxplot_1K_5K_mem.png', bbox_inches='tight')
plt.clf()

## TIME

fig, ax = plt.subplots(nrows=1, ncols=2, figsize=(18, 8))
bp0 = ax[0].boxplot(thread_box_time[10:15])
bp1 = ax[1].boxplot(proc_box_time[10:15])
ax[0].set_xticklabels(keys[10:15])
ax[1].set_xticklabels(keys[10:15])
# Setting the scale to facilitate the data comparisson
# ax[0].set_ylim(top=ax[1].get_ylim()[1])
# ax[1].set_ylim(bottom=ax[0].get_ylim()[0])
ax[0].set_title('Boxplots do tempo de criação de Threads')
ax[1].set_title('Boxplots do tempo de criação de Processos')
for axe in ax:
    axe.yaxis.grid(True)
    axe.set_xlabel('Número de instâncias')
    axe.set_ylabel('Tempo (s)')
plt.savefig('boxplot_1K_5K_time.png', bbox_inches='tight')
plt.clf()

## 6K - 10K INSTANCES

## MEMORY

fig, ax = plt.subplots(nrows=1, ncols=2, figsize=(18, 8))
bp0 = ax[0].boxplot(thread_box_mem[15:])
bp1 = ax[1].boxplot(proc_box_mem[15:])
ax[0].set_xticklabels(keys[15:])
ax[1].set_xticklabels(keys[15:])
ax[0].set_title('Boxplots da quantidade de memória utilizada por Threads')
ax[1].set_title('Boxplots da quantidade de memória utilizada por Processos')
# Setting the scale to facilitate the data comparisson
# ax[0].set_ylim(top=ax[1].get_ylim()[1])
# ax[1].set_ylim(bottom=ax[0].get_ylim()[0])
for axe in ax:
    axe.yaxis.grid(True)
    axe.set_xlabel('Número de instâncias')
    axe.set_ylabel('Memória Utilizada (KB)')
plt.savefig('boxplot_6K_10K_mem.png', bbox_inches='tight')
plt.clf()

## TIME

fig, ax = plt.subplots(nrows=1, ncols=2, figsize=(18, 8))
bp0 = ax[0].boxplot(thread_box_time[15:])
bp1 = ax[1].boxplot(proc_box_time[15:])
ax[0].set_xticklabels(keys[15:])
ax[1].set_xticklabels(keys[15:])
ax[0].set_title('Boxplots do tempo de criação de Threads')
ax[1].set_title('Boxplots do tempo de criação de Processos')
# Setting the scale to facilitate the data comparisson
# ax[0].set_ylim(top=ax[1].get_ylim()[1])
# ax[1].set_ylim(bottom=ax[0].get_ylim()[0])
for axe in ax:
    axe.yaxis.grid(True)
    axe.set_xlabel('Número de instâncias')
    axe.set_ylabel('Tempo (s)')
plt.savefig('boxplot_6K_10K_time.png', bbox_inches='tight')
plt.clf()


## SEPARATED MEMORY BOXPLOTS - 1K to 10K

for i in range(10):
    fig, ax = plt.subplots(nrows=1, ncols=2, figsize=(18, 8))
    bp0 = ax[0].boxplot(thread_box_mem[i + 10])
    bp1 = ax[1].boxplot(proc_box_mem[i + 10])
    ax[0].set_xticklabels(keys[i + 10: i + 11])
    ax[1].set_xticklabels(keys[i + 10: i + 11])
    ax[0].set_title('Boxplot da quantidade de memória utilizada por Threads')
    ax[1].set_title('Boxplot da quantidade de memória utilizada por Processos')
    # Setting the scale to facilitate the data comparisson
    # ax[0].set_ylim(top=ax[1].get_ylim()[1])
    # ax[1].set_ylim(bottom=ax[0].get_ylim()[0])
    for axe in ax:
        axe.yaxis.grid(True)
        axe.set_xlabel('Número de instâncias')
        axe.set_ylabel('Memória Utilizada (KB)')
    plt.savefig('boxplot_' + str(i + 1) + 'K_mem.png', bbox_inches='tight')
    plt.clf()

## INCREASE PERCENTAGE GRAPH ##

mem_increase_perc = []
time_increase_perc = []

column_width = 0.20

for i in range(len(keys)):
    mem_increase_perc.append(round((mean_proc_mem_list[i] * 100.0) / mean_thread_mem_list[i], 2))
    time_increase_perc.append(round((mean_proc_time_list[i] * 100.0) / mean_thread_time_list[i], 2))

fig, ax = plt.subplots(figsize=(20, 9))
rects1 = ax.bar(np.array(range(0, len(keys))) - column_width / 2, np.array(mem_increase_perc), column_width, label='Memória (KB)')
rects2 = ax.bar(np.array(range(0, len(keys))) + column_width / 2, np.array(time_increase_perc), column_width, label='Tempo (s)')
ax.set_ylabel('Taxa de variação (%)')
ax.set_xlabel('Número de instâncias')
ax.set_title('Taxa de variação de tempo e memória de processos em relação à threads')
ax.set_xticks(np.array(range(0, len(keys))))
ax.set_xticklabels(keys)
ax.legend(loc='upper left', fontsize='x-large')

def autolabel(rects):
    """Attach a text label above each bar in *rects*, displaying its height."""
    for rect in rects:
        height = rect.get_height()
        ax.annotate('{}'.format(height),
                    xy=(rect.get_x() + rect.get_width() / 2, height),
                    xytext=(0, 3),  # 3 points vertical offset
                    textcoords="offset points",
                    ha='center', va='bottom')


autolabel(rects1)
autolabel(rects2)

fig.tight_layout()

plt.savefig('bar_increase_perc.png', bbox_inches='tight')
plt.clf()

print('mean memory increase: ' + str(statistics.mean(mem_increase_perc)) + '%', 'mean time increase: ' + str(statistics.mean(time_increase_perc)) + '%')

print('All graphs were plotted with success.')