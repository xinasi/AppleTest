# -*- coding: utf-8 -*-
"""
Created on Thu Apr 19 16:12:50 2018

@author: william
Youtube: https://www.youtube.com/channel/UCVCSn4qQXTDAtGWpWAe4Plw
"""
#%%
import os
import numpy as np
import tensorflow as tf
import input_data
import model

#%%

N_CLASSES = 4
IMG_W = 208  # resize the image, if the input image is too large, training will be very slow.
IMG_H = 208
BATCH_SIZE = 14
CAPACITY = 30
MAX_STEP = 600 # with current parameters, it is suggested to use MAX_STEP>10k
learning_rate = 0.0001 # with current parameters, it is suggested to use learning rate<0.0001


#%%
def run_training():
    
    # you need to change the directories to yours.
    train_dir = 'data//train//'
    logs_train_dir = 'logs//./train//./'
    
    train, train_label = input_data.get_files(train_dir)
    
    train_batch, train_label_batch = input_data.get_batch(train,
                                                          train_label,
                                                          IMG_W,
                                                          IMG_H,
                                                          BATCH_SIZE, 
                                                          CAPACITY)      
    train_logits = model.inference(train_batch, BATCH_SIZE, N_CLASSES)
    train_loss = model.losses(train_logits, train_label_batch)        
    train_op = model.trainning(train_loss, learning_rate)
    train__acc = model.evaluation(train_logits, train_label_batch)
       
    summary_op = tf.summary.merge_all()
    sess = tf.Session()
    train_writer = tf.summary.FileWriter(logs_train_dir, sess.graph)
    saver = tf.train.Saver()
    
    sess.run(tf.global_variables_initializer())
    coord = tf.train.Coordinator()
    threads = tf.train.start_queue_runners(sess=sess, coord=coord)
    
    try:
        for step in np.arange(MAX_STEP):
            if coord.should_stop():
                    break
            _, tra_loss, tra_acc = sess.run([train_op, train_loss, train__acc])
               
            if step % 50 == 0:
                print('Step %d, train loss = %.2f, train accuracy = %.2f%%' %(step, tra_loss, tra_acc))
                summary_str = sess.run(summary_op)
                train_writer.add_summary(summary_str, step)
            
            if step % 150 == 0 or (step + 1) == MAX_STEP:
                checkpoint_path = os.path.join(logs_train_dir, 'model.ckpt')
                saver.save(sess, checkpoint_path, global_step=step)
                
    except tf.errors.OutOfRangeError:
        print('Done training -- epoch limit reached')
    finally:
        coord.request_stop()
        
    coord.join(threads)
    sess.close()
    

#%% Evaluate one image
#when training, comment the following codes.

#Test one image

def get_one_image(file_dir):
    """
    Randomly pick one image from test data
    Return: ndarray
    """
    from PIL import Image
    import matplotlib.pyplot as plt
    test =[]
    for file in os.listdir(file_dir):
        test.append(file_dir + file)
    print('There are %d test pictures\n' %(len(test)))

    n = len(test)
    ind = np.random.randint(0, n)
    print(ind)
    img_test = test[ind]

    image = Image.open(img_test)
    plt.imshow(image)
    image = image.resize([208, 208])
    image = np.array(image)
    return image

def test_one_image():
    """
    Test one image with the saved models and parameters
    """
    test_dir = 'data//test//'
    logs_train_dir = 'logs//./train//./'
    test_image = get_one_image(test_dir)
    

    with tf.Graph().as_default():
        BATCH_SIZE = 1
        N_CLASSES = 4

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
            if max_index == 0:
                print('It is Washington FuJi Apple with possibility %.6f' %prediction[:, 0])
            elif max_index == 1:
                print('It is American FuJi Apple with possibility %.6f' %prediction[:, 1])
            elif max_index == 2:
                print('It is Japan FuJi Apple with possibility %.6f' %prediction[:, 2])
            else:
                print('It is New Zealand FuJi Apple with possibility %.6f' %prediction[:, 3])


if __name__ == '__main__':
    run_training()
#test_one_image()
