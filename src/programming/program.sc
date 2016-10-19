// a Logika program always start with importing org.sireum.logika._
import org.sireum.logika._

// All variable have to be declared and typed
// B is a boolean type
// Z is arbitrary-precision integer type
// ZS is a sequence (dynamic array) of Z

// Read-only variable declarations (val)
val a: B = true
var b: B = a & !a | a // note: & is logical-and (not short-circuit), ! is negation, | is logical-or (inclusive)
val x: Z = 100
val y: Z = x * -2 + x / 2 - 10 // note: / is integer division

// Read/write variable declarations (var)
var z: Z = y * -1
z = z + 1

var s: ZS = ZS() // ZS() is empty sequence literal
s = ZS(1, 2, 3)
s = s :+ 4 // append
s = 0 +: s // prepend
s(0) = -1 // sequence update (assign -1 to s at index 0)
s(0) = s(0) + 1 // sequence lookup and update

// assumption
assume(s(0) >= 0)

// assertion
assert(s(0) >= 0)

// conditional
if (x > 0) {
  z = 10
} else {
  z = 11
}

// while-loop
while (z > 0) {
  print("z is: ") // print to console
  println(z, ".") // print z. to console with newline
  z = z - 1
}

// function
def f(n: Z): Z = {
  val r: Z = n + 1
  return r
}

// helper function
@helper def h(n: Z): Z = {
  val r: Z = z + n
  return r
}

// procedure
def g(n: Z): Unit = {
  z = h(n) // function call
}
