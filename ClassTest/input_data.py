# -*- coding: utf-8 -*-

# 定義使用的套件
import tensorflow as tf
import numpy as np
import os

#%% 讀取資料

def get_files(file_dir):

    wfj = []           #0華盛頓富士
    label_wfj = []
    afj = []           #1美國富士
    label_afj = []
    jfj = []           #2日本蜜富士
    label_jfj = []
    nfj = []           #3紐西蘭玫瑰富士
    label_nfj = []
    # 用檔名判斷並將圖片放到相對應的分類籃
    for file in os.listdir(file_dir):
        name = file.split(sep='_')
        if name[0] == 'WFJ':
            wfj.append(file_dir + file)
            label_wfj.append(0)
        elif name[0] == 'AFJ':
            afj.append(file_dir + file)
            label_afj.append(1)
        elif name[0] == 'JFJ':
            jfj.append(file_dir + file)
            label_jfj.append(2)
        else:
            nfj.append(file_dir + file)
            label_nfj.append(3)
    print("There are %d Washington FuJi Apple\nThere are %d American FuJi Apple\nThere are %d Japan FuJi Apple\nThere are %d New Zealand FuJi Apple" % (len(wfj), len(afj), len(jfj), len(nfj)))

    # 打亂圖片順序
    image_list = np.hstack((wfj, afj, jfj, nfj))
    label_list = np.hstack((label_wfj, label_afj, label_jfj, label_nfj))
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

   
    input_queue = tf.train.slice_input_producer([image, label])
    
    label = input_queue[1]
    image_contents = tf.read_file(input_queue[0])
    image = tf.image.decode_jpeg(image_contents, channels=3)
    
    
    #將圖片進行裁剪
    image = tf.image.resize_image_with_crop_or_pad(image, image_W, image_H)    
    
    image = tf.image.resize_images(image, [image_H, image_W], method=tf.image.ResizeMethod.NEAREST_NEIGHBOR)
    
    image = tf.image.per_image_standardization(image)
    
    image_batch, label_batch = tf.train.batch([image, label],
                                                batch_size= batch_size,
                                                num_threads= 64, 
                                                capacity = capacity)
    
    label_batch = tf.reshape(label_batch, [batch_size])
    image_batch = tf.cast(image_batch, tf.float32)
    
    return image_batch, label_batch

#%% TEST
# training時註解
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



