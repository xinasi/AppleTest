import os
from os import walk
from os.path import join
train_path = '/home/xinasi/hub/data/'
test_path = '/home/xinasi/tensorflow/test/'

test_list = []
count = 0
for root, dirs, files in walk(train_path):
    for i in files:
        count += 1
        print('The ' + str(count) + ' Times Start')
        full_train_path = join(root, i)
        os.system('mv ' + full_train_path + ' ' + test_path + i)
        print('Get: ' + i)
        
        os.chdir("/home/xinasi/hub/")
        os.system('rm -rf model && rm -rf retrain_logs && rm -rf ~/tensorflow/model && rm -rf ~/tensorflow/retrain_logs && mkdir model && mkdir retrain_logs && rm -rf /tmp/_retrain_checkpoint.* && rm -rf /tmp/tfhub_modules/ && rm /tmp/checkpoint')
        os.system('bazel-bin/examples/image_retraining/retrain \
                  --bottleneck_dir=./model/bottlenecks \
                  --model_dir=./model/inception \
                  --tfhub_module https://tfhub.dev/google/imagenet/inception_resnet_v2/feature_vector/1 \
                  --output_graph=./model/retrained_graph.pb \
                  --output_labels=./model/retrained_labels.txt \
                  --how_many_training_steps=4000 \
                  --image_dir ./data/ \
                  --summaries_dir ./retrain_logs')
        print('Training Successfully')
        os.system('cp -r /home/xinasi/hub/model /home/xinasi/tensorflow/model')
        os.system('cp -r /home/xinasi/hub/retrain_logs /home/xinasi/tensorflow/retrain_logs')
        print('Model Copy Successfully')

        os.chdir("/home/xinasi/")
        os.system('python tensorflow/tensorflow/examples/label_image/label_image.py \
                  --input_layer="Placeholder" \
                  --output_layer="final_result" \
                  --labels=tensorflow/model/retrained_labels.txt \
                  --graph=tensorflow/model/retrained_graph.pb \
                  --image=tensorflow/test/' + i)

        for j in os.listdir(test_path):
            test_list.append(j)
        for k in test_list:
            os.system('mv ' + test_path + k + ' ' + full_train_path)
            print('Release: ' + k)
        test_list.clear()
        if not os.listdir(test_path):
            print('test file cleared')
            print('The ' + str(count) + ' Times End')

