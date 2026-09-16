import pandas as pd
import matplotlib.pyplot as plt
import numpy as np

# CSV файлын оқу
df = pd.read_csv("results.csv")

# 1. Time vs n
plt.figure(figsize=(10, 6))
for (alg, inp), group in df.groupby(['algorithm', 'input']):
    plt.plot(group['n'], group['time_ms'], marker='o', label=f"{alg} ({inp})")
plt.xscale('log')
plt.yscale('log')
plt.xlabel('n (log scale)')
plt.ylabel('Time (ms, log scale)')
plt.title('Time vs n')
plt.legend(bbox_to_anchor=(1.05, 1), loc='upper left')
plt.tight_layout()
plt.savefig('time_vs_n.png')
plt.close()

# 2. Depth vs n
plt.figure(figsize=(10, 6))
for (alg, inp), group in df.groupby(['algorithm', 'input']):
    plt.plot(group['n'], group['max_depth'], marker='o', label=f"{alg} ({inp})")
plt.xscale('log')
plt.xlabel('n (log scale)')
plt.ylabel('Max Recursion Depth')
plt.title('Max Depth vs n')
plt.legend(bbox_to_anchor=(1.05, 1), loc='upper left')
plt.tight_layout()
plt.savefig('depth_vs_n.png')
plt.close()

# 3. Ratio vs n
plt.figure(figsize=(10, 6))
for (alg, inp), group in df.groupby(['algorithm', 'input']):
    if alg == 'QuickSelect':
        ratio = group['comparisons'] / group['n']
    else:
        ratio = group['comparisons'] / (group['n'] * np.log2(group['n']))
    plt.plot(group['n'], ratio, marker='o', label=f"{alg} ({inp})")

plt.xscale('log')
plt.xlabel('n (log scale)')
plt.ylabel('Ratio')
plt.title('Ratio vs n (Checking Theta bound)')
plt.legend(bbox_to_anchor=(1.05, 1), loc='upper left')
plt.tight_layout()
plt.savefig('ratio_vs_n.png')
plt.close()

print("Plots generated successfully!")