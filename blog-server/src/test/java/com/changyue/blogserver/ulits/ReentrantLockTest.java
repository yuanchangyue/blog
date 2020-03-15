package com.changyue.blogserver.ulits;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author : 袁阊越
 * @description :
 * @date : 2020-03-12 13:52
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ReentrantLockTest {

    static ReentrantLock lock = new ReentrantLock();

    @Test
    public void reentrantLockTest() {
        new Thread(ReentrantLockTest::test, "线程1").start();
        new Thread(ReentrantLockTest::test, "线程2").start();
    }

    public static void test() {
        lock.lock();
        System.out.println(Thread.currentThread().getName() + "lock");
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + "unlock");
            lock.unlock();
        }
    }

}

