# -*- coding: utf-8 -*-
import os
import numpy as np
import tensorflow as tf
import input_data
import model

# 路徑
train_dir = 'data//train//'
test_dir = 'data//test//sweet//'
train_logs_dir = 'logs//./train//./'
val_logs_dir = 'logs//./val//./'

N_CLASSES = 2
IMG_W = 208
IMG_H = 208
RATIO = 0.2
BATCH_SIZE = 15
CAPACITY = 256
MAX_STEP = 100
learning_rate = 0.001


def training():

    train, train_label, val, val_label = input_data.get_files(train_dir, RATIO)
    train_batch, train_label_batch = input_data.get_batch(train,
                                                  train_label,
                                                  IMG_W,
                                                  IMG_H,
                                                  BATCH_SIZE,
                                                  CAPACITY)
    val_batch, val_label_batch = input_data.get_batch(val,
                                                  val_label,
                                                  IMG_W,
                                                  IMG_H,
                                                  BATCH_SIZE,
                                                  CAPACITY)

    logits = model.inference(train_batch, BATCH_SIZE, N_CLASSES)
    loss = model.losses(logits, train_label_batch)
    train_op = model.trainning(loss, learning_rate)
    acc = model.evaluation(logits, train_label_batch)

    x = tf.placeholder(tf.float32, shape=[BATCH_SIZE, IMG_W, IMG_H, 3])
    y_ = tf.placeholder(tf.int16, shape=[BATCH_SIZE])
    

    with tf.Session() as sess:
        saver = tf.train.Saver()
        sess.run(tf.global_variables_initializer())
        coord = tf.train.Coordinator()
        threads = tf.train.start_queue_runners(sess= sess, coord=coord)

        summary_op = tf.summary.merge_all()
        train_writer = tf.summary.FileWriter(train_logs_dir, sess.graph)
        val_writer = tf.summary.FileWriter(val_logs_dir, sess.graph)

        try:
            for step in np.arange(MAX_STEP):
                if coord.should_stop():
                        break
                tra_images,tra_labels = sess.run([train_batch, train_label_batch])
                _, tra_loss, tra_acc = sess.run([train_op, loss, acc],
                                                feed_dict={x:tra_images, y_:tra_labels})
                if step % 5 == 0:
                    print('Step %d, train loss = %.2f, train accuracy = %.2f%%' %(step, tra_loss, tra_acc*100.0))
                    summary_str = sess.run(summary_op)
                    train_writer.add_summary(summary_str, step)

                if step % 25 == 0 or (step + 1) == MAX_STEP:
                    val_images, val_labels = sess.run([val_batch, val_label_batch])
                    val_loss, val_acc = sess.run([loss, acc],
                                                 feed_dict={x:val_images, y_:val_labels})
                    print('**  Step %d, val loss = %.2f, val accuracy = %.2f%%  **' %(step, val_loss, val_acc*100.0))
                    summary_str = sess.run(summary_op)
                    val_writer.add_summary(summary_str, step)

                if step % 35 == 0 or (step + 1) == MAX_STEP:
                    checkpoint_path = os.path.join(train_logs_dir, 'model.ckpt')
                    saver.save(sess, checkpoint_path, global_step=step)

        except tf.errors.OutOfRangeError:
            print('Done training -- epoch limit reached')
        finally:
            coord.request_stop()
        coord.join(threads)

#%%
# 測試圖片準確度，訓練時註解
def get_one_image(file_dir):
    
    from PIL import Image
    import matplotlib.pyplot as plt
    import os
    test =[]
    for file in os.listdir(file_dir):
        test.append(file_dir + file)
    print('There are %d test apples\n' %(len(test)))

    n = len(test)
    ind = np.random.randint(0, n)
    print(ind+1) #位在資料夾第幾張
    img_test = test[ind]
    img_name = img_test.split('//',2) 
    print(img_name[2]) #只有檔案名稱
    #print (img_test) #整個檔案路徑
    image = Image.open(img_test)
    plt.imshow(image)
    image = image.resize([208, 208])
    image = np.array(image)
    return image

def test_one_image():
   
    test_dir = 'data//test//sweet//'
    logs_train_dir = 'logs//./train//./'
    test_image = get_one_image(test_dir)
    

    with tf.Graph().as_default():
        BATCH_SIZE = 1
        N_CLASSES = 2

        image = tf.cast(test_image, tf.float32)
        image = tf.image.per_image_standardization(image)
        image = tf.reshape(image, [1, 208, 208, 3])
        logit = model.inference(image, BATCH_SIZE, N_CLASSES)

        logit = tf.nn.softmax(logit)

        x = tf.placeholder(tf.float32, shape=[208, 208, 3])

        saver = tf.train.Saver()

        with tf.Session() as sess:

            print("Reading checkpoints...")
            ckpt = tf.train.get_checkpoint_state(logs_train_dir)
            if ckpt and ckpt.model_checkpoint_path:
                global_step = ckpt.model_checkpoint_path.split('/')[-1].split('-')[-1]
                saver.restore(sess, ckpt.model_checkpoint_path)
                print('Loading success, global_step is %s' % global_step)
            else:
                print('No checkpoint file found')

            prediction = sess.run(logit, feed_dict={x: test_image})
            max_index = np.argmax(prediction)
           
      
            print('It is Sweet with possibility %.6f' %prediction[:, 0])
            print('It is NotSweet with possibility %.6f' %prediction[:, 1])






