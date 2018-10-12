import sys.process.Process
import java.io.File

import scala.collection.mutable

object SalRunner {

  def smc(workingdir: String, assertion: String, theorem: String): String =  {
    var as = "--assertion=" + assertion + "!" + theorem
    var cmd = Process(Seq("sal-smc",as), new File(workingdir))
    cmd.!!
  }


}
