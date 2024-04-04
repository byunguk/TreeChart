# TreeChart

TreeChart shows size of the child views graphically. 
`ceateTreeChart` function has two parameter `children` and `parentView`.
`children` is an array of size of each child view.

This function divide input array into two parts and calls itself until `children` has only one item.
When it divides input array, it can divide by index or sum.
In this example, it divides input array by sum.


There are four test cases. If you tab any places, it will recrate the chart with next test set.


![TreeChart](https://github.com/byunguk/TreeChart/assets/16727541/dc9adb03-f745-474a-bc55-37519afcf195)
