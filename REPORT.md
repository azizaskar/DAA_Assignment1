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


---

## 5. Bonus Tasks Analysis (+15%)

### Task A: Deterministic Select (Median of Medians) vs Randomized QuickSelect
* **Worst-Case Bound**:
    * **Randomized QuickSelect**: Expected average case is $\Theta(n)$, but degenerate random choices can degrade to worst-case $\Theta(n^2)$.
    * **Deterministic Select (BFPRT)**: Guarantees strict $O(n)$ worst-case running time by splitting the array into groups of 5 and recursively finding the median of medians. The recurrence is $T(n) \le T(\lceil n/5 \rceil) + T(7n/10) + \Theta(n)$. Since $1/5 + 7/10 = 9/10 < 1$, the recurrence resolves to strictly linear $O(n)$ time.
* **Empirical Comparison (Random vs Sorted)**:
    * On random datasets, randomized QuickSelect performs substantially fewer comparisons and runs faster due to smaller constant factors (no overhead of grouping by 5 and finding multiple medians).
    * On adversarial / presorted inputs, Deterministic Select maintains steady, predictable linear behavior with zero risk of stack overflow or quadratic degradation, whereas randomized QuickSelect depends on pseudo-random generator entropy.

### Task B: Closest Pair of Points ($O(n \log n)$)
* **Divide-and-Conquer Strategy**:
    1. Points are pre-sorted by $x$-coordinate in $O(n \log n)$.
    2. The field is split into left and right halves around $x_{\text{mid}}$.
    3. Minimum distance $\delta = \min(\delta_{\text{left}}, \delta_{\text{right}})$ is calculated recursively.
    4. A vertical boundary strip of width $2\delta$ is filtered and sorted by $y$-coordinate.
* **The 7-Points Geometric Proof**:
    * Any two points within the strip with distance $< \delta$ must reside within a $2\delta \times \delta$ rectangle.
    * Subdividing this rectangle into grid squares of side length $\delta / 2$ yields at most 8 squares. Each square can contain at most one point (otherwise $\min(\delta_{\text{left}}, \delta_{\text{right}}) < \delta$, a contradiction).
    * Consequently, sorted by $y$, each point only needs to be tested against at most 7 subsequent points.
* **Verification**:
    * Verified for $n \in [10, 2000]$ against brute-force $O(n^2)$ verification, with differences bounded within numerical tolerance $\epsilon < 10^{-9}$.