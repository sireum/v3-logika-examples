package org.sireum.logika.examples

import org.scalatest.FreeSpec
import java.io.File

import ammonite.ops._

class RegressionTest extends FreeSpec {
  val sireum: Path = {
    val homeEnv = System.getenv("SIREUM_HOME")
    require(homeEnv != null, "The SIREUM_HOME environment variable is not defined.")
    val homeDir = new File(homeEnv)
    val sireumBat = new File(homeDir, "sireum.bat")
    val sireum = new File(homeDir, "sireum")
    if (sireumBat.canExecute) Path(sireumBat)
    else if (sireum.canExecute) Path(sireum)
    else {
      require(requirement = false, "Could not find Sireum executable.")
      null
    }
  }

  val basePath: Path = {
    val path = new File(getClass.getResource("").toURI).getCanonicalPath
    require(path.contains("/target"), "Failed to detect base directory.")
    Path(new File(path.substring(0, path.indexOf("/target")))) / 'src
  }

  "Truth Table" - manual(basePath / 'truthtable)

  "Propositional Logic" - manual(basePath / 'propositional)

  "Predicate Logic" - manual(basePath / 'predicate)

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
