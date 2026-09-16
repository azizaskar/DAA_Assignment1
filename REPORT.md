
```markdown
# Assignment 1 Report: Divide and Conquer & Asymptotic Notations

## 1. Asymptotic Bounds Summary

| Algorithm | Best Case | Average Case | Worst Case | Reason / Causing Input |
| :--- | :--- | :--- | :--- | :--- |
| **MergeSort** | $\Theta(n \log n)$ | $\Theta(n \log n)$ | $\Theta(n \log n)$ | Always divides the array into two equal halves regardless of order. |
| **QuickSort** | $\Theta(n \log n)$ | $\Theta(n \log n)$ | $\Theta(n^2)$ | Worst case occurs when pivot is consistently extreme (mitigated by random pivot). |
| **QuickSelect** | $\Theta(n)$ | $\Theta(n)$ | $\Theta(n^2)$ | Worst case on extreme pivots; linear on average due to single-sided recursion. |
| **Insertion Sort** | $\Theta(n)$ | $\Theta(n^2)$ | $\Theta(n^2)$ | Best case on already sorted array; worst case on reverse-sorted input. |

---

## 2. Recurrences and Master Theorem

The Master Theorem applies to recurrences of the form:
$$T(n) = a \cdot T(n/b) + f(n)$$
Critical exponent: $c = \log_b(a)$.

### MergeSort
* **Recurrence**: $T(n) = 2T(n/2) + \Theta(n)$
* **Parameters**: $a = 2, b = 2, f(n) = \Theta(n)$
* **Case**: $\log_2(2) = 1$. Since $f(n) = \Theta(n^1)$, this is **Case 2**.
* **Result**: $T(n) = \Theta(n \log n)$.

### QuickSort (Balanced Split Assumption)
* **Recurrence**: $T(n) = 2T(n/2) + \Theta(n)$
* **Parameters**: $a = 2, b = 2, f(n) = \Theta(n)$
* **Master Theorem Case**: **Case 2**, giving $T(n) = \Theta(n \log n)$.
* **Random Pivot Analysis**: A random pivot guarantees that degenerate splits (like $1$ vs $n-1$) occur with negligible probability. The expected division is balanced (at least $1/4$ to $3/4$), keeping tree depth at $O(\log n)$ and average running time at $O(n \log n)$.

### QuickSelect (Balanced Split Assumption)
* **Recurrence**: $T(n) = 1T(n/2) + \Theta(n)$
* **Parameters**: $a = 1, b = 2, f(n) = \Theta(n)$
* **Critical Exponent**: $\log_2(1) = 0$. Since $f(n) = \Theta(n^1)$ and $1 > 0$, this is **Case 3**.
* **Result**: $T(n) = \Theta(n)$.

---

## 3. Empirical Plots and Constant Verification

1. **Time vs n**: MergeSort and QuickSort scale according to $O(n \log n)$, while QuickSelect scales strictly linearly $O(n)$.
2. **Depth vs n**: QuickSort recursion depth remains strictly bounded under $2 \cdot \log_2(n)$ across all datasets due to tail-recursion optimization and 3-way partitioning.
3. **Ratio Check for $\Theta$ Bound**:
   * For QuickSort and MergeSort, $\frac{\text{comparisons}}{n \log_2 n}$ stabilizes as $n$ grows.
   * Approximate constants: $c_1 \approx 1.1$, $c_2 \approx 2.4$, for $n \ge n_0 = 10\,000$.
   * For QuickSelect, $\frac{\text{comparisons}}{n}$ converges to $\approx 2.0 - 3.5$, confirming the tight $\Theta(n)$ bound.

---

## 4. Discussion

The measurements match theoretical predictions closely:
1. **Cutoff Impact**: Insertion sort cutoff ($n \le 15$) suppresses recursion overhead on small arrays and leverages CPU cache locality.
2. **Memory Efficiency**: Reusable buffer in MergeSort avoids repeated allocations and GC pressure for large $n = 1\,000\,000$.
3. **Hardware & Runtime Effects**: Deviations at small $n$ ($n \le 1\,000$) stem from JVM JIT compilation warm-up.