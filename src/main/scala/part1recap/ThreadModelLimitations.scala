package part1recap

object ThreadModelLimitations {
  //Rants on limitations
  //Point 1: OO encapsulation is only valid in the SINGLE-THREADED MODEL

  class BankAccount(private var amount: Int){
    override def toString = s"$amount"
    def withdraw(money: Int) = synchronized {
      this.amount -= money
    }
    def deposit(money: Int) = synchronized {
      this.amount += money
    }
    def getAmount = amount
  }

  val account = new BankAccount(2000)
  /*
    first solution: synchronization
    other problems:
      -deadlock:
        situation where two or more threads making no progress, but not consuming any cpu cycles
      -livelock
        situation where multiple threads are still crunching numbers, but not making any progress
   */

  val depositThreads = (1 to 1000).map(_ => new Thread(() => account.deposit(1)))
  val withdrawThreads = (1 to 1000).map(_ => new Thread(() => account.withdraw(1)))

  var task: Runnable = () => println("I'll be executed on another thread")

  val runningThread: Thread = new Thread(() =>
  {
    while (true) {
      while (task == null) {
        runningThread.synchronized {
          println("[background]] waiting for a task")
          runningThread.wait()
        }
      }
      task.synchronized({
        println("[background I have a task!")
        task.run()
        task = null
      })
    }

  })

  def delegateToBackgroundthread(r: Runnable) = {
    if (task == null){
      task = r
      runningThread.synchronized{
        runningThread.notify()
      }
    }

  }
  def main(args: Array[String]) = {
    (depositThreads ++ withdrawThreads).foreach(_.start())
    println(account.getAmount)
  }

}
