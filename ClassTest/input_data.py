# -*- coding: utf-8 -*-

import tensorflow as tf
import numpy as np
import os
import math

#%%
train_dir = '/data/train/'

def get_files(file_dir, ratio):
    wfj = []           #0華盛頓富士
    label_wfj = []
    afj = []           #1美國富士
    label_afj = []
    jfj = []           #2日本蜜富士
    label_jfj = []
    nfj = []           #3紐西蘭玫瑰富士
    label_nfj = []
    cfj = []           #智利富士
    label_cfj = []
    # 用檔名判斷並將圖片放到相對應的分類籃
    for file in os.listdir(file_dir):
        name = file.split(sep='_')
        if name[1] == 'WFJ':
            wfj.append(file_dir + file)
            label_wfj.append(1)
        elif name[1] == 'AFJ':
            afj.append(file_dir + file)
            label_afj.append(2)
        elif name[1] == 'JFJ':
            jfj.append(file_dir + file)
            label_jfj.append(3)
        elif name[1] == 'NFJ':
            nfj.append(file_dir + file)
            label_nfj.append(4)
        else:
            cfj.append(file_dir + file)
            label_cfj.append(5)
    print("There are %d Washington FuJi Apple\nThere are %d American FuJi Apple\nThere are %d Japan FuJi Apple\nThere are %d New Zealand FuJi Apple\nThere are %d New Chile FuJi Apple" % (len(wfj), len(afj), len(jfj), len(nfj), len(cfj)))
    
    # 總共幾張訓練圖
    train =[]
    for file in os.listdir(file_dir):
        train.append(file_dir + file)
    print('There are %d train apples\n' %(len(train)))
    

    # 打亂圖片順序
    image_list = np.hstack((wfj, afj, jfj, nfj))
    label_list = np.hstack((label_wfj, label_afj, label_jfj, label_nfj))

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





