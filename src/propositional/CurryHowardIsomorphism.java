import java.util.function.Function;

final class And<T1, T2> {
  final T1 e1;
  final T2 e2;

  And(T1 e1, T2 e2) {
    this.e1 = e1;
    this.e2 = e2;
  }
}

abstract class Or<L, R> {
}

final class Left<L, R> extends Or<L, R> {
  final L l;

  Left(L l) {
    this.l = l;
  }
}

final class Right<L, R> extends Or<L, R> {
  final R r;

  Right(R r) {
    this.r = r;
  }
}

final class Implies<T1, T2> {
  final Function<T1, T2> f;

  Implies(Function<T1, T2> f) {
    this.f = f;
  }
}

public class CurryHowardIsomorphism {

  // ∧i: P, Q ⊢ P ∧ Q
  <P, Q> And<P, Q> andi(P arg1, Q arg2) {
    return new And<>(arg1, arg2);
  }

  // ^e1: P ^ Q ⊢ P
  <P, Q> P ande1(And<P, Q> arg1) {
    return arg1.e1;
  }

  // ^e1: P ^ Q ⊢ Q
  <P, Q> Q ande2(And<P, Q> arg1) {
    return arg1.e2;
  }

  // ∨i1: P ⊢ P ∨ Q
  <P, Q> Or<P, Q> ori1(P arg1) {
    return new Left<>(arg1);
  }

  // ∨i2: Q ⊢ P ∨ Q
  <P, Q> Or<P, Q> ori2(Q arg1) {
    return new Right<>(arg1);
  }

  // ve: P ∨ Q, { P assume ... R }, { Q assume ... R} ⊢ R
  <P, Q, R> R ore(Or<P, Q> arg1, Function<P, R> arg2, Function<Q, R> arg3) {
    return (arg1 instanceof Left<?, ?>) ?
        arg2.apply(((Left<P, Q>) arg1).l) :
        arg3.apply(((Right<P, Q>) arg1).r);
  }

  // →i: { P assume ... Q } ⊢ P → Q
  <P, Q> Implies<P, Q> impliesi(Function<P, Q> arg1) {
    return new Implies<>(arg1);
  }

  // →e: P → Q, P ⊢ Q
  <P, Q> Q impliese(Implies<P, Q> arg1, P arg2) {
    return arg1.f.apply(arg2);
  }

  <p, q> And<q, p> andExample(And<p, q> premise) { // p ∧ q  ⊢  q ∧ p  {
    And<p, q> x1 = premise;                        //   1. p ∧ q    premise
    p x2 = ande1(x1);                              //   2. p        ∧e1 1
    q x3 = ande2(x1);                              //   3. q        ∧e2 1
    And<q, p> x4 = andi(x3, x2);                   //   4. q ∧ p    ∧i 3 2
    return x4;                                     //
  }                                                // }

  <p, q, r> Or<Or<p, q>, r> orExample(q premise1) { // q  ⊢  (p ∨ q) ∨ r  {
    q x1 = premise1;                                //   1. q              premise
    Or<p, q> x2 = ori2(x1);                         //   2. p ∨ q          ∨i2 1
    Or<Or<p, q>, r> x3 = ori1(x2);                  //   3. (p ∨ q) ∨ r    ∨i1 2
    return x3;                                      //
  }                                                 // }

  <p, q, r> Implies<q, r> example3(p premise1,
                                   Implies<And<q, p>, r> premise2) { // p, q ∧ p → r  ⊢  q → r  {
    p x1 = premise1;                                                 //   1. p            premise
    Implies<And<q, p>, r> x2 = premise2;                             //   2. q ∧ p → r    premise
    Function<q, r> x3 =                                              //   3. {
        (q x4) -> {                                                  //        4. q       assume
          And<q, p> x5 = andi(x4, x1);                               //        5. q ∧ p   ∧i 4 1
          r x6 = impliese(x2, x5);                                   //        6. r       →e 2 5
          return x6;                                                 //
        };                                                           //      }
    Implies<q, r> x7 = impliesi(x3);                                 //   7. q → r        →i 3
    return x7;                                                       //
  }                                                                  // }

  <p, q, r> Implies<Or<p, q>, r> example4(Implies<p, r> premise1,
                                          Implies<q, r> premise2) { // p → r, q → r  ⊢  p ∨ q → r  {
    Implies<p, r> x1 = premise1;                                    //   1. p → r          premise
    Implies<q, r> x2 = premise2;                                    //   2. q → r          premise
    Function<Or<p, q>, r> x3 =                                      //   3. {
        (Or<p, q> x4) -> {                                          //        4. p ∨ q     assume
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
    Implies<Or<p, q>, r> x12 = impliesi(x3);                        //  12. p ∨ q → r      →i 3
    return x12;                                                     //
  }                                                                 // }
}
