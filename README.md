# Persistence
Attempting to increase the bounds on integer multiplicative persistence.

Inspired to create this video by https://youtu.be/Wim9WJeDTHQ

Process does not have sources, as I thought of it on my own, but may exist elsewhere in literature.

Written in java, which is not be the most efficient language for this.

"We" is used regardless of plurality or lack thereof of the author(s).

# Usage
This is a java file, which can be compiled with

javac Pers.java

and run with

java Pers <md>

Where md is the maximum number of digits of the 2nd to last element in the desired longest chain. See below for why that is what I use.

Currently, I've had this run successfully for 250 as an md setting, which found no longer sequences than found in previous work

These are small, distinct starting numbers for 11 steps that it found.

277777788888899
27777789999999999


# Approach

Any number after the first in the chain must be of the form 2^a3^b5^c7^d, or 0.

The goal is to find highly persistent numbers in this form, and then we can find a predecessor.

We use dynamic programming to store results for all smaller numbers of this form, so that every time it is recomputed for a new one, we only have to do the persistence once.

Note however, that if both c and a are positive, then the number is a multiple of 10, and has persistence 1.

We thus don't need to store for values of that form.

Therefore, we only need to store O(n^3) different numbers, those of form 2^a3^b7^d or 3^b5^c7^d, of which there are O(n^3).




I am currently unsure about the complexity of the program, but memory should be around O(n^4), and time should be O(n^3 * (multiplication complexity)),


Please let me know if anything seems incorrect.