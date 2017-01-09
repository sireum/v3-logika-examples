/*
 Copyright (c) 2017, Robby, Kansas State University
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions are met:

 1. Redistributions of source code must retain the above copyright notice, this
    list of conditions and the following disclaimer.
 2. Redistributions in binary form must reproduce the above copyright notice,
    this list of conditions and the following disclaimer in the documentation
    and/or other materials provided with the distribution.

 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.sireum.logika.test.examples

import org.scalatest.FreeSpec

import ammonite.ops._
import org.sireum.logika.test._

class RegressionTest extends FreeSpec {

  val basePath: Path = rootPath / 'src

  "Truth Table" - manual(basePath / 'truthtable)

  "Propositional Logic" - {
    val path = basePath / 'propositional

    manual(path)

    "And" - manual(path / 'and)
    "Or" - manual(path / 'or)
    "Implies" - manual(path / 'implies)
    "Negation" - manual(path / 'negation)
  }

  "Predicate Logic" - {
    val path = basePath / 'predicate

    "Universal" - manual(path / 'universal)
    "Existential" - manual(path / 'existential)
  }

  "Programming Logic" - {
    "Manual" - manual(basePath / 'programming / 'manual)
    "Auto" - auto(basePath / 'programming / 'auto)
    "SymExe" - symexe(basePath / 'programming / 'symexe)
  }

  def manual(path: Path): Unit = {
    for (f <- path.toIO.listFiles(_.getName.endsWith(".logika"))) {
      f.getName in {
        try %%(sireum, 'logika, f.getName, SIREUM_SKIP_BUILD = "true")(path) catch {
          case ShelloutException(e) => fail(e.err.string)
        }
      }
    }
  }

  def auto(path: Path): Unit = {
    for (f <- path.toIO.listFiles(_.getName.endsWith(".logika"))) {
      f.getName in {
        try %%(sireum, 'logika, "-a", f.getName, SIREUM_SKIP_BUILD = "true")(path) catch {
          case ShelloutException(e) => fail(e.err.string)
        }
      }
    }
  }

  def symexe(path: Path): Unit = {
    for (f <- path.toIO.listFiles(_.getName.endsWith(".logika"))) {
      f.getName in {
        try %%(sireum, 'logika, "-x", f.getName, SIREUM_SKIP_BUILD = "true")(path) catch {
          case ShelloutException(e) => fail(e.err.string)
        }
      }
    }
  }
}
