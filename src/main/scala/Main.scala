import scala.collection.mutable

object Main {
  def main(args: Array[String]){

    var workingdir = "/Users/ramsay/Downloads"
    var assertion = "linkedin{2}"
    var theorem = "neverDetailed"

    if(args.length == 3) {
      workingdir = args(0)
      assertion = args(1)
      theorem = args(2)
    }
    var salres = SalRunner.smc(workingdir,assertion,theorem)

    if(salres.startsWith("Counterexample:")) {
      var blocks = SalBlock.parseCounterexample(salres)
      println("Counterexample:")
      for (b <- blocks) println(b)
    } else {
      println(salres)
    }
  }
}
