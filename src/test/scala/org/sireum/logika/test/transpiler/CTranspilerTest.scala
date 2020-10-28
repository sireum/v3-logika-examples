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

package org.sireum.logika.test.transpiler

import java.io.File
import java.nio.file.Files

import ammonite.ops._
import org.scalatest.freespec.AnyFreeSpec

import org.sireum.logika.test._

class CTranspilerTest extends AnyFreeSpec {

  val basePath: Path = rootPath / "src" / "programming"

  val targetPath: Path = rootPath / "target" / "c"

  "Manual" - createTests((basePath / "manual").toIO)

  "Auto" - createTests((basePath / "auto").toIO)

  "SymExe" - createTests((basePath / "symexe").toIO)

  def createTests(d: File): Unit = {
    if (d.isFile) createTest(d)
    else for (f <- d.listFiles) {
      if (f.isFile) createTest(f)
      else if (f.isDirectory && !Files.isSymbolicLink(f.toPath)) createTests(f)
    }
  }

  def createTest(f: File): Unit = {
    if (!f.getName.endsWith(".logika")) return
    val p = Path(f)
    val (dir, rel) = {
      val r = p.relativeTo(basePath)
      val d = targetPath / r
      val n = f.getName
      (Path(new File(d.toIO.getParentFile, n.substring(0, n.length - 7))), r)
    }

    rm ! dir
    mkdir ! dir

    rel.last in {
      val r = %%(sireum, "logika", "--c", ".", "-x", f.getCanonicalPath, SIREUM_SKIP_BUILD="true")(dir)
      %%("cmake", ".")(dir)
      %%("make")(dir)
    }
  }
}
