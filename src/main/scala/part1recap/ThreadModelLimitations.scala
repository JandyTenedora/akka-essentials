package part1recap

object ThreadModelLimitations {
  //Rants on limitations
  //Point 1: OO encapsulation is only valid in the SINGLE-THREADED MODEL

  class BankAccount(private var amount: Int){
    def toString = s"$amount"
    def withdraw(money: Int) = this.amount -= money
    def deposit(money: Int) = this.amount += money
    def getAmount = amount
  }

  val account = new BankAccount(2000)

  val depositThreads = (1 to 1000).map(_ => new Thread(() => account.deposit(1)))
  def main(args: Array[String]) = {

  }

}
