public class TestRoutine implements Runnable{
    private int type;
    private CountDownLatch cdl;

    public TestRoutine(int type, CountDownLatch cdl) {
        this.type = type;
        this.cdl = cdl;
    }

    public void routineA() {
        this.cdl.countDown();
        System.out.println("latch = " + this.cdl.getLatch());
    }

    public void routineB() throws InterruptedException {
        System.out.println("Iniciando o await");
        this.cdl.await();
        System.out.println("Fim do await");
    }

    @Override
    public void run() {
        if (this.type == 0) {
            try {
                this.routineB();
            } catch (InterruptedException e){
                System.out.println("Algo errado :.(");
            }
        }
        if (this.type == 1) this.routineA();
    }
}
