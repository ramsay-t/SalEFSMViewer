import scala.collection.mutable
import scala.collection.mutable._

class SalBlock(label: String, params: List[String], startState: String) {
  override def toString: String = {
    var ps = ""
    for(p <- params) if(ps.equals("")) { ps += p } else { ps += "," + p }
    startState + " -- " + label + "(" + ps + ") --> "
  }
}

// "Singleton" version with static methods
object SalBlock {
  def parseOne(lines: Array[String]): (SalBlock, Array[String]) = {
    var leftover = lines;
    var l: String = ""
    var p: MutableList[String] = new MutableList[String]()
    var s: String = ""

    while(leftover.length > 0 && ((l.equals("")||s.equals("")))) {
      var rx = "ip_([0-9]+)_" + l + "_[0-9]+ = (.*)"
      if(leftover.head.startsWith("label = ")) {
        l = leftover.head.substring(8)
        //println("Label: " + l)
      } else if(leftover.head.matches(rx)) {
        var pp = leftover.head.substring(leftover.head.indexOf("=")+2)
        if(pp.startsWith("String_")) pp = pp.substring(7)
        //println("Param: " + pp)
        p += pp
      } else if(leftover.head.startsWith("cfstate = ")) {
        s = leftover.head.substring(10)
      }
      leftover = leftover.tail

    }
    if(leftover.length > 0) {
      (new SalBlock(l, p.toList, s), leftover)
    } else {
      (null, leftover)
    }
  }

  def parseCounterexample(salres: String): List[SalBlock] = {
    var lines = salres.split("\n")

    var blocks = mutable.MutableList[SalBlock]()

    while(lines.length > 0) {
      var (block,newlines) = SalBlock.parseOne(lines)
      lines = newlines
      if(block != null) blocks += block
    }

    blocks.toList
  }

}
