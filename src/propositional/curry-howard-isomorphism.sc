type ^[P, Q] = (P, Q)                                                  // ∧
type V[P, Q] = Either[P, Q]                                            // ∨
type ->[P, Q] = P => Q                                                 // →

def andi[P, Q](p: P, q: Q): P ^ Q = (p, q)                             // ∧i-rule

def ande1[P, Q](pq: P ^ Q): P = pq._1                                  // ∧e1-rule

def ande2[P, Q](pq: P ^ Q): Q = pq._2                                  // ∧e2-rule

def ori1[P, Q](p: P): P V Q = Left[P, Q](p)                            // ∨i1-rule

def ori2[P, Q](q: Q): P V Q = Right[P, Q](q)                           // ∨i2-rule

def ore[P, Q, R](pORq: P V Q, pDEDUCEr: P => R, qDEDUCEr: Q => R): R = // ∨e-rule
  pORq match {
    case Left(p) => pDEDUCEr(p)
    case Right(q) => qDEDUCEr(q)
  }

def implyi[P, Q](pDEDUCEq: P => Q): P -> Q =                           // →i rule
    (p) => pDEDUCEq(p)

def implye[P, Q](pIMPLYq: P -> Q, p: P): Q =                           // →e rule
  pIMPLYq(p)

def and_example[p, q](premise1: p ^ q): q ^ p = // p ∧ q  ⊢  q ∧ p
{                                               // {
  val x1: p ^ q    = premise1                   //   1. p ∧ q    premise
  val x2: p        = ande1(x1)                  //   2. p        ∧e1 1
  val x3: q        = ande2(x1)                  //   3. q        ∧e2 1
  val x4: q ^ p    = andi(x3, x2)               //   4. q ∧ p    ∧i 3 2
  /* return */ x4                               //
}                                               // }

def or_example[p, q, r](premise1: q): (p V q) V r = // q  ⊢  (p ∨ q) ∨ r
{                                                   // {
  val x1: q            = premise1                   //   1. q              premise
  val x2: p V q        = ori2[p, q](x1)             //   2. p ∨ q          ∨i2 1
  val x3: p V q V r    = ori1[p V q, r](x2)         //   3. (p ∨ q) ∨ r    ∨i1 2
  x3                                                //
}                                                   // }

def example1[p, q, r](premise1: p ^ q -> r, premise2: p -> q, premise3: p): r = // p ∧ q → r, p → q, p  ⊢  r
{                                                                               // {
  val x1: p ^ q -> r    = premise1                                              //   1. p ∧ q → r    premise
  val x2: p -> q        = premise2                                              //   2. p → q        premise
  val x3: p             = premise3                                              //   3. p            premise
  val x4: q             = implye(x2, x3)                                        //   4. q            →e 2 3
  val x5: p ^ q         = andi(x3, x4)                                          //   5. p ∧ q        ∧i 3 4
  val x6: r             = implye(x1, x5)                                        //   6. r            →e 1 5
  x6                                                                            //
}                                                                               // }

def example2[p, q, r](premise1: p V q -> r, premise2: q): r = // p ∨ q → r, q  ⊢  r
{                                                             // {
  val x1: p V q -> r    = premise1                            //   1. p ∨ q → r    premise
  val x2: q             = premise2                            //   2. q            premise
  val x3: p V q         = ori2(x2)                            //   3. p ∨ q        ∨i2 2
  val x4: r             = implye(x1, x3)                      //   4. r            →e 1 3
  x4                                                          //
}                                                             // }

def example3[p, q, r](premise1: p, premise2: q ^ p -> r): q -> r = // p, q ∧ p → r  ⊢  q → r
{                                                                  // {
  val x1: p             = premise1                                 //   1. p            premise
  val x2: q ^ p -> r    = premise2                                 //   2. q ∧ p → r    premise
  val x3 = {                                                       //   3. {
    (x4: q) =>                                                     //        4. q       assume
    val x5: q ^ p       = andi(x4, x1)                             //        5. q ∧ p   ∧i 4 1
    val x6: r           = implye(x2, x5)                           //        6. r       →e 2 5
    x6                                                             //
  }                                                                //      }
  val x7: q -> r        = implyi(x3)                               //   7. q → r        →i 3
  x7                                                               //
}                                                                  // }

def example4[p, q, r](premise1: p -> r, premise2: q -> r): p V q -> r = // p → r, q → r  ⊢  p ∨ q → r
{                                                                       // {
  val x1: p -> r               = premise1                               //   1. p → r          premise
  val x2: q -> r               = premise2                               //   2. q → r          premise
  val x3 = {                                                            //   3. {
    (x4: p V q) =>                                                      //        4. p ∨ q     assume
    val x5 = {                                                          //        5. {
      (x6: p) =>                                                        //             6. p    assume
      val x7: r                = implye(x1, x6)                         //             7. r    →e 1 6
      x7                                                                //
    }                                                                   //           }
    val x8 = {                                                          //        8. {
      (x9: q) =>                                                        //             9. q    assume
      val x10: r               = implye(x2, x9)                         //            10. r    →e 2 9
      x10                                                               //
    }                                                                   //           }
    val x11: r                 = ore[p, q, r](x4, x5, x8)               //       11. r         ∨e 4 5 8
    x11                                                                 //
  }                                                                     //      }
  val x12: p V q -> r          = implyi(x3)                             //  12. p ∨ q → r      →i 3
  x12                                                                   //
}                                                                       // }

def example5[p, q](premise1: q): p -> (q ^ p) = // q  ⊢  p → q ∧ p
{                                               // {
  val x1: q               = premise1            //   1. q             premise
  val x2 = {                                    //   2. {
    (x3: p) =>                                  //        3. p        assume
    val x4: q ^ p         = andi(x1, x3)        //        4. q ∧ p    ∧i 1 3
    x4                                          //
  }                                             //      }
  val x5: p -> (q ^ p)    = implyi(x2)          //   5. p → q ∧ p     →i 2
  x5                                            //
}                                               // }

def example6[p, q, r](premise1: p -> (q -> r)): (p -> q) -> (p -> r) = // p → q → r  ⊢ (p → q) → (p → r)
{                                                                      // {
  val x1: p -> (q -> r)            = premise1                          //   1. p → q → r            premise
  val x2 = {                                                           //   2. {
    (x3: p -> q) =>                                                    //        3. p → q           assume
      val x4 = {                                                       //        4. {
        (x5: p) =>                                                     //             5. p          assume
        val x6: q -> r             = implye(x1, x5)                    //             6. q → r      →e 1 5
        val x7: q                  = implye(x3, x5)                    //             7. q          →e 3 5
        val x8: r                  = implye(x6, x7)                    //             8. r          →e 6 7
        x8                                                             //
      }                                                                //           }
      val x9: p -> r               = implyi(x4)                        //        9. p → r           →i 4
      x9                                                               //
    }                                                                  //      }
  val x10: (p -> q) -> (p -> r)    = implyi(x2)                        //  10. (p → q) → (p → r)    →i 2
  x10                                                                  //
}                                                                      // }
