import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.RepeatedTest;

class Test {

	@RepeatedTest(100)
	void test2() {
		CounterRoutine c = new CounterRoutine(0, 100000);
        Thread t1 = new Thread(c);
        Thread t2 = new Thread(c);
        t1.start();
        t2.start();
        try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
			fail();
		}
        assertEquals(200000, c.getCount());
	}
	
	@RepeatedTest(100)
	void test3() throws InterruptedException {
		CounterRoutine c = new CounterRoutine(0, 100000);
        Thread t1 = new Thread(c);
        Thread t2 = new Thread(c);
        Thread t3 = new Thread(c);
        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();
        assertEquals(300000, c.getCount());
	}
	
	@RepeatedTest(100)
	void test() {
		CounterRoutine c = new CounterRoutine(0, 100000);
        Thread t1 = new Thread(c);
        //Thread t2 = new Thread(c);
        //Thread t3 = new Thread(c);
        t1.start();
        //t2.start();
        //t3.start();
        try {
			t1.join();
			//t2.join();
			//t3.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
        assertEquals(100000, c.getCount());
	}

}
