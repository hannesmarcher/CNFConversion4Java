# CNFConversion4Java

This project converts propositional logic formulas into its respective CNF(Conjunctive-Normal-Form) - see https://en.wikipedia.org/wiki/Conjunctive_normal_form.

## Usage

Simply specify the desired formula in `Main.java` (variable `formula`) and execute it.

### Operators

The default operators are: `&` (logical and), `|` (logical or), `-` (logical not), `~` (logical implies), `=` (logical equivalence), `^` (logical xor).

Besides these default operators, one can specify custom operators in `config.OperatorRepresentations`.
For example, if one would like to use `∧`-symbol instead of `&`-symbol, then this class is the way to go.

### Allowed Characters

In essence any character is allowed, except operators (including default operators, such as `&`, and custom operators, such as `∧`), backslashes, and simple brackets.

## Conversion Approach

A propositional logic formula is converted by applying the following rules:
* `logic equivalence`, e.g. `A EQUIV B` results in `(A AND B) OR (NOT A AND NOT B)`
* `double negation eliminiation`, e.g. `NOT NOT A` results in `A`
* `De Morgan's laws`, e.g. `NOT(A AND B)` results in `NOT A OR NOT B`
* `distributive law`, e.g. `(A1 AND A2) OR (B1 AND B2)` results in `(A1 OR B1) AND (A1 OR B2) AND (A2 OR B1) AND (A2 OR B2)`

## Limitations:

There exist three major limitations:
* There is no precedence between operators, e.g. there are no guarantees what the outcome of `(a ∧ b ∨ c)` is. For example, the outcome might be `((a ∧ b) ∨ c)`, `(a ∧ (b ∨ c))`, or something different. Thus, brackets are crucial!
* The conversion to CNF is done recursively, thus memory might explode, especially for very large formulas and formulas with a lot of equivalence and xor operators (due to transformation rules of `logic equivalence`)
* In the current implementation trivial clauses are not removed.
