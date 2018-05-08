# -*- coding: utf-8 -*-
"""
Created on Wed Apr 18 00:27:08 2018

@author: william
"""
""" 
reference:
https://blog.csdn.net/qq_16137569/article/details/72802387
https://www.youtube.com/watch?v=G1X-qJaCfII
https://github.com/kevin28520/My-TensorFlow-tutorials/tree/master/01%20cats%20vs%20dogs/new_version
"""
"""
#%%

import tensorflow as tf
import numpy as np
import os
import math

#%%

# you need to change this to your data directory
train_dir = 'data//train//'

def get_files(file_dir, ratio):
    '''
    Args:
        file_dir: file directory
    Returns:
        list of images and labels
    '''
    notsweets = []
    label_notsweets = []
    sweets = []
    label_sweets = []
    for file in os.listdir(file_dir):
        name = file.split(sep='.')
        if name[0]=='notsweet':
            notsweets.append(file_dir + file)
            label_notsweets.append(0)
        else:
            sweets.append(file_dir + file)
            label_sweets.append(1)
    print('There are %d notsweets\nThere are %d sweets' %(len(notsweets), len(sweets)))
    
    image_list = np.hstack((notsweets, sweets))
    label_list = np.hstack((label_notsweets, label_sweets))
    
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
    '''
    Args:
        image: list type
        label: list type
        image_W: image width
        image_H: image height
        batch_size: batch size
        capacity: the maximum elements in queue
    Returns:
        image_batch: 4D tensor [batch_size, width, height, 3], dtype=tf.float32
        label_batch: 1D tensor [batch_size], dtype=tf.int32
    '''
    
    image = tf.cast(image, tf.string)
    label = tf.cast(label, tf.int32)

    # make an input queue
    input_queue = tf.train.slice_input_producer([image, label])
    
    label = input_queue[1]
    image_contents = tf.read_file(input_queue[0])
    image = tf.image.decode_jpeg(image_contents, channels=3)
    
    ######################################
    # data argumentation should go to here
    ######################################
    #將圖片進行裁剪
    image = tf.image.resize_image_with_crop_or_pad(image, image_W, image_H)    
    
    image = tf.image.resize_images(image, [image_H, image_W], method=tf.image.ResizeMethod.NEAREST_NEIGHBOR)
    
    # 如果要看到正常圖片，请註釋108行（标准化）和 130行（image_batch = tf.cast(image_batch, tf.float32)）
    # 训练时，不要注释掉！
    image = tf.image.per_image_standardization(image)
    
    image_batch, label_batch = tf.train.batch([image, label],
                                                batch_size= batch_size,
                                                num_threads= 64, 
                                                capacity = capacity)
    
    label_batch = tf.reshape(label_batch, [batch_size])
    image_batch = tf.cast(image_batch, tf.float32)
    
    return image_batch, label_batch

"""
#%% TEST
# To test the generated batches of images
# When training the model, DO comment the following codes

'''
import matplotlib.pyplot as plt

BATCH_SIZE = 2
CAPACITY = 256
IMG_W = 208
IMG_H = 208

train_dir = 'data//train//'
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



'''















#%%
# 定義使用的套件
import tensorflow as tf
import numpy as np
import os

#%%
# 讀取資料
def get_files(file_dir):

    notsweets = []
    label_notsweets = []
    sweets = []
    label_sweets = []
    # 用檔名判斷並將圖片放到相對應的分類籃
    for file in os.listdir(file_dir):
        name = file.split(sep='_')
        if name[0] == 'notsweet':
            notsweets.append(file_dir + file)
            label_notsweets.append(0)
        else:
            sweets.append(file_dir + file)
            label_sweets.append(1)
    print("There are %d notsweets\nThere are %d sweets" % (len(notsweets), len(sweets)))

    # 打亂圖片順序
    image_list = np.hstack((notsweets, sweets))
    label_list = np.hstack((label_notsweets, label_sweets))
    temp = np.array([image_list, label_list])
    temp = temp.transpose()
    np.random.shuffle(temp)

    image_list = list(temp[:, 0])
    label_list = list(temp[:, 1])
    label_list = [int(i) for i in label_list]

    return image_list, label_list

#%%
# 產生相同大小的批次，分批進行訓練
def get_batch(image, label, image_W, image_H, batch_size, capacity):
    # image, label: 用來獲取上方return的image_list和label_list
    # image_W, image_H: 每批圖片的長、寬
    # batch_size: 每一批有多少張圖片
    # capacity: 每個queue最大的容量

    # 用tf.cast函數轉換格式
    image = tf.cast(image, tf.string)
    label = tf.cast(label, tf.int32)

    # make an input queue
    input_queue = tf.train.slice_input_producer([image, label])
    
    label = input_queue[1]
    image_contents = tf.read_file(input_queue[0])
    image = tf.image.decode_jpeg(image_contents, channels=3)
    
    ######################################
    # data argumentation should go to here
    ######################################
    #將圖片進行裁剪
    image = tf.image.resize_image_with_crop_or_pad(image, image_W, image_H)    
    
    image = tf.image.resize_images(image, [image_H, image_W], method=tf.image.ResizeMethod.NEAREST_NEIGHBOR)
    
    # 如果要看到正常圖片，请註解108行（标准化）和 130行（image_batch = tf.cast(image_batch, tf.float32)）
    # 訓練時記得拿掉
    image = tf.image.per_image_standardization(image)
    
    image_batch, label_batch = tf.train.batch([image, label],
                                                batch_size= batch_size,
                                                num_threads= 64, 
                                                capacity = capacity)
    
    label_batch = tf.reshape(label_batch, [batch_size])
    image_batch = tf.cast(image_batch, tf.float32)
    
    return image_batch, label_batch

#%%
# TEST
"""
import matplotlib.pyplot as plt

BATCH_SIZE = 2
CAPACITY = 256
IMG_W = 208
IMG_H = 208

train_dir = 'data\\train\\'
image_list, label_list = get_files(train_dir)
image_batch, label_batch = get_batch(image_list, label_list, IMG_W, IMG_H, BATCH_SIZE, CAPACITY)

with tf.Session() as sess:
    i = 0
    coord = tf.train.Coordinator()
    threads = tf.train.start_queue_runners(coord=coord)
    
    try:
        while not coord.should_stop() and i < 1:
            img, label = sess.run([image_batch, label_batch])

            for j in np.arange(BATCH_SIZE):
                print("label: %d" % label[j])
                plt.imshow(img[j, :, :, :])
                plt.show()
            i += 1
    except tf.errors.OutOfRangeError:
        print("done!")
    finally:
        coord.request_stop()
    coord.join(threads)
    """


