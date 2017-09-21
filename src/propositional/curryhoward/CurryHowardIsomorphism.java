package curryhoward;

import java.util.function.Function;

final class and<P, Q> {
  final P e1;
  final Q e2;

  and(P e1, Q e2) {
    this.e1 = e1;
    this.e2 = e2;
  }
}

abstract class or<P, Q> {
}

final class left<P, Q> extends or<P, Q> {
  final P p;

  left(P q) {
    this.p = q;
  }
}

final class right<P, Q> extends or<P, Q> {
  final Q r;

  right(Q r) {
    this.r = r;
  }
}

final class implies<P, Q> {
  final Function<P, Q> f;

  implies(Function<P, Q> f) {
    this.f = f;
  }
}

public class CurryHowardIsomorphism {

  // ∧i: P, Q ⊢ P ∧ Q
  <P, Q> and<P, Q> andi(P arg1, Q arg2) {
    return new and<>(arg1, arg2);
  }

  // ^e1: P ^ Q ⊢ P
  <P, Q> P ande1(and<P, Q> arg1) {
    return arg1.e1;
  }

  // ^e1: P ^ Q ⊢ Q
  <P, Q> Q ande2(and<P, Q> arg1) {
    return arg1.e2;
  }

  // ∨i1: P ⊢ P ∨ Q
  <P, Q> or<P, Q> ori1(P arg1) {
    return new left<>(arg1);
  }

  // ∨i2: Q ⊢ P ∨ Q
  <P, Q> or<P, Q> ori2(Q arg1) {
    return new right<>(arg1);
  }

  // ve: P ∨ Q, { P assume ... R }, { Q assume ... R} ⊢ R
  <P, Q, R> R ore(or<P, Q> arg1, Function<P, R> arg2, Function<Q, R> arg3) {
    if (arg1 instanceof left<?, ?>)
      return arg2.apply(((left<P, Q>) arg1).p);
    else
      return arg3.apply(((right<P, Q>) arg1).r);
  }

  // →i: { P assume ... Q } ⊢ P → Q
  <P, Q> implies<P, Q> impliesi(Function<P, Q> arg1) {
    return new implies<>(arg1);
  }

  // →e: P → Q, P ⊢ Q
  <P, Q> Q impliese(implies<P, Q> arg1, P arg2) {
    return arg1.f.apply(arg2);
  }

  <p, q> and<q, p> andExample(and<p, q> premise) { // p ∧ q  ⊢  q ∧ p  {
    and<p, q> x1 = premise;                        //   1. p ∧ q    premise
    p x2 = ande1(x1);                              //   2. p        ∧e1 1
    q x3 = ande2(x1);                              //   3. q        ∧e2 1
    and<q, p> x4 = andi(x3, x2);                   //   4. q ∧ p    ∧i 3 2
    return x4;                                     //
  }                                                // }

  <p, q, r> or<or<p, q>, r> orExample(q premise1) { // q  ⊢  (p ∨ q) ∨ r  {
    q x1 = premise1;                                //   1. q              premise
    or<p, q> x2 = ori2(x1);                         //   2. p ∨ q          ∨i2 1
    or<or<p, q>, r> x3 = ori1(x2);                  //   3. (p ∨ q) ∨ r    ∨i1 2
    return x3;                                      //
  }                                                 // }

  <p, q, r> implies<q, r> example3(p premise1,
                                   implies<and<q, p>, r> premise2) { // p, q ∧ p → r  ⊢  q → r  {
    p x1 = premise1;                                                 //   1. p            premise
    implies<and<q, p>, r> x2 = premise2;                             //   2. q ∧ p → r    premise
    Function<q, r> x3 =                                              //   3. {
        (q x4) -> {                                                  //        4. q       assume
          and<q, p> x5 = andi(x4, x1);                               //        5. q ∧ p   ∧i 4 1
          r x6 = impliese(x2, x5);                                   //        6. r       →e 2 5
          return x6;                                                 //
        };                                                           //      }
    implies<q, r> x7 = impliesi(x3);                                 //   7. q → r        →i 3
    return x7;                                                       //
  }                                                                  // }

  <p, q, r> implies<or<p, q>, r> example4(implies<p, r> premise1,
                                          implies<q, r> premise2) { // p → r, q → r  ⊢  p ∨ q → r  {
    implies<p, r> x1 = premise1;                                    //   1. p → r          premise
    implies<q, r> x2 = premise2;                                    //   2. q → r          premise
    Function<or<p, q>, r> x3 =                                      //   3. {
        (or<p, q> x4) -> {                                          //        4. p ∨ q     assume
          Function<p, r> x5 =                                       //        5. {
              (p x6) -> {                                           //             6. p    assume
                r x7 = impliese(x1, x6);                            //             7. r    →e 1 6
                return x7;                                          //
              };                                                    //           }
          Function<q, r> x8 =                                       //        8. {
              (q x9) -> {                                           //             9. q    assume
                r x10 = impliese(x2, x9);                           //            10. r    →e 2 9
                return x10;                                         //
              };                                                    //           }
          r x11 = ore(x4, x5, x8);                                  //       11. r         ∨e 4 5 8
          return x11;                                               //
        };                                                          //      }
    implies<or<p, q>, r> x12 = impliesi(x3);                        //  12. p ∨ q → r      →i 3
    return x12;                                                     //
  }                                                                 // }
}
