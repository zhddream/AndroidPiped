package com.zhd.admin.piped;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * 使用管道流进行线程之间的通信
 */
public class PipedActivity extends AppCompatActivity {
    Product Product;//生产线程
    Consumer Consumer;//消费线程
    PipedInputStream pipedInputStream;
    PipedOutputStream pipedOutputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);
        pipedOutputStream = new PipedOutputStream();
        pipedInputStream = new PipedInputStream();

        Product = new Product(pipedOutputStream, new User("张三 ", "123456"));//发送线程
        Consumer = new Consumer(pipedInputStream);//接受线程
        try {
            pipedOutputStream.connect(pipedInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.click:
                Product.start();
                Consumer.start();
                break;
            default:
                break;
        }
    }

    /**
     * 生产线程
     */
    public class Product extends Thread {
        private PipedOutputStream outputStream;
        private User user;

        public Product(PipedOutputStream outputStream, User user) {
            this.outputStream = outputStream;
            this.user = user;
        }

        @Override
        public void run() {
            try {
                outputStream.write(StreamUtil.object2ByteArray(user));
                outputStream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public class Consumer extends Thread {
        private PipedInputStream inputStream;


        public Consumer(PipedInputStream inputStream) {
            this.inputStream = inputStream;
        }

        @Override
        public void run() {
            try {
                final byte[] bytes = new byte[1024];
                inputStream.read(bytes);
                inputStream.close();
                final User user = (User) StreamUtil.byteArray2Object(bytes);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(PipedActivity.this, "消费者线程接收到数据" + user.getName(), Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
