IMAGE_SIZE=224
ARCHITECTURE="mobilenet_0.50_${IMAGE_SIZE}"
python -m scripts.retrain   --bottleneck_dir=tf_files/bottlenecks --how_many_training_steps=1000 --model_dir=tf_files/models/ --summaries_dir=tf_files/training_summaries/"${ARCHITECTURE}"   --output_graph=tf_files/retrained_graph.pb   --output_labels=tf_files/retrained_labels.txt --eval_step_interval=30 --train_batch_size=5000 --architecture="${ARCHITECTURE}" --image_dir=tf_files/photos --testing_percentage=20 --random_brightness=0 --random_scale=0 --random_crop=0

cp tf_files/retrained_labels.txt ../app/src/main/assets/retrained_labels.txt
cp tf_files/retrained_graph.pb ../app/src/main/assets/retrained_graph.pb