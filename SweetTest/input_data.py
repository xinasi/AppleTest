# -*- coding: utf-8 -*-

import tensorflow as tf
import numpy as np
import os
import math

#%%
train_dir = '/data/train/'

def get_files(file_dir, ratio):
    sweet = []           #0華盛頓富士
    label_sweet = []
    notsweet = []           #1美國富士
    label_notsweet = []
    # 用檔名判斷並將圖片放到相對應的分類籃
    for file in os.listdir(file_dir):
        name = file.split(sep='_')
        if name[2] == 'S':
            sweet.append(file_dir + file)
            label_sweet.append(0)
        else:
            notsweet.append(file_dir + file)
            label_notsweet.append(1)
    print("There are %d Sweet\nThere are %d NotSweet" % (len(sweet), len(notsweet)))
    
    # 總共幾張訓練圖
    train =[]
    for file in os.listdir(file_dir):
        train.append(file_dir + file)
    print('There are %d train apples\n' %(len(train)))
    

    # 打亂圖片順序
    image_list = np.hstack((sweet, notsweet))
    label_list = np.hstack((label_sweet, label_notsweet))

    temp = np.array([image_list, label_list])
    temp = temp.transpose()
    np.random.shuffle(temp)

    all_image_list = temp[:, 0]
    all_label_list = temp[:, 1]

    n_sample = len(all_label_list)
    n_val = math.ceil(n_sample*ratio) # number of validation samples
    n_train = n_sample - n_val # number of trainning samples

    tra_images = all_image_list[0:n_train]
    tra_labels = all_label_list[0:n_train]
    tra_labels = [int(float(i)) for i in tra_labels]
    val_images = all_image_list[n_train:-1]
    val_labels = all_label_list[n_train:-1]
    val_labels = [int(float(i)) for i in val_labels]

    return tra_images,tra_labels,val_images,val_labels

#%%
def get_batch(image, label, image_W, image_H, batch_size, capacity):
    

    image = tf.cast(image, tf.string)
    label = tf.cast(label, tf.int32)

    # make an input queue
    input_queue = tf.train.slice_input_producer([image, label])

    label = input_queue[1]
    image_contents = tf.read_file(input_queue[0])
    image = tf.image.decode_jpeg(image_contents, channels=3)
    
    #將圖片進行裁剪
    #image = tf.image.resize_image_with_crop_or_pad(image, image_W, image_H)    
    
    image = tf.image.resize_images(image, [image_H, image_W], method=tf.image.ResizeMethod.NEAREST_NEIGHBOR)
    
    #檢視圖片時註解
    image = tf.image.per_image_standardization(image)
    
    image_batch, label_batch = tf.train.batch([image, label],
                                                batch_size= batch_size,
                                                num_threads= 64, 
                                                capacity = capacity)
    
    label_batch = tf.reshape(label_batch, [batch_size])

    #檢視圖片時註解
    image_batch = tf.cast(image_batch, tf.float32)
    
    return image_batch, label_batch


#%%
# TEST
# 訓練時記得註解 
"""
import matplotlib.pyplot as plt
BATCH_SIZE = 2
CAPACITY = 256
IMG_W = 208
IMG_H = 208

train_dir = 'data\\train\\'
ratio = 0.2
tra_images, tra_labels, val_images, val_labels = get_files(train_dir, ratio)
tra_image_batch, tra_label_batch = get_batch(tra_images, tra_labels, IMG_W, IMG_H, BATCH_SIZE, CAPACITY)



with tf.Session() as sess:
    i = 0
    coord = tf.train.Coordinator()
    threads = tf.train.start_queue_runners(coord=coord)

    try:
        while not coord.should_stop() and i<1:

            img, label = sess.run([tra_image_batch, tra_label_batch])

            # just test one batch
            for j in np.arange(BATCH_SIZE):
                print('label: %d' %label[j])
                plt.imshow(img[j,:,:,:])
                plt.show()
            i+=1

    except tf.errors.OutOfRangeError:
        print('done!')
    finally:
        coord.request_stop()
    coord.join(threads)
"""





