import sys
import cv2
import time
import numpy as np   
from keras.preprocessing.image import img_to_array
from keras.models import load_model

def main(argv):
    # Face detection XML load and trained model loading
    # 경로 확인
    face_detection = cv2.CascadeClassifier('./files/haarcascade_frontalface_default.xml')
    emotion_classifier = load_model('./files/emotion_model.hdf5', compile=False)
    # face_detection = cv2.CascadeClassifier(argv[1])
    # emotion_classifier = load_model(argv[2], compile=False)
    EMOTIONS = ["Angry" ,"Disgusting","Fearful", "Happy", "Sad", "Surpring", "Neutral"]

    # Video capture using webcam
    camera = cv2.VideoCapture(-1)

    while True:
        # Capture image from camera
        ret, frame = camera.read()
        
        # Convert color to gray scale
        gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
        
        # Face detection in frame
        faces = face_detection.detectMultiScale(gray,
                                                scaleFactor=1.1,
                                                minNeighbors=5,
                                                minSize=(30,30))
        
        # Create empty image
        canvas = np.zeros((250, 300, 3), dtype="uint8")
        
        # Perform emotion recognition only when face is detected
        if len(faces) > 0:
            # For the largest image
            face = sorted(faces, reverse=True, key=lambda x: (x[2] - x[0]) * (x[3] - x[1]))[0]
            (fX, fY, fW, fH) = face
            # Resize the image to 48x48 for neural network
            roi = gray[fY:fY + fH, fX:fX + fW]
            roi = cv2.resize(roi, (48, 48))
            roi = roi.astype("float") / 255.0
            roi = img_to_array(roi)
            roi = np.expand_dims(roi, axis=0)
            
            # Emotion predict
            preds = emotion_classifier.predict(roi)[0]
            emotion_probability = np.max(preds)
            label = EMOTIONS[preds.argmax()]
            
            # Assign labeling
            # cv2.putText(frame, label, (fX, fY - 10), cv2.FONT_HERSHEY_SIMPLEX, 0.45, (0, 0, 255), 2)
            # cv2.rectangle(frame, (fX, fY), (fX + fW, fY + fH), (0, 0, 255), 2)
    
            # Label printing
            for (i, (emotion, prob)) in enumerate(zip(EMOTIONS, preds)):
                text = "{}: {:.2f}%".format(emotion, prob * 100)    
                w = int(prob * 300)
                cv2.rectangle(canvas, (7, (i * 35) + 5), (w, (i * 35) + 35), (0, 0, 255), -1)
                cv2.putText(canvas, text, (10, (i * 35) + 23), cv2.FONT_HERSHEY_SIMPLEX, 0.45, (255, 255, 255), 2)

            # c or smile to capture
            if cv2.waitKey(33) & 0xFF == ord('c') or preds[3] > 0.6:
                now = int(round(time.time() * 1000))
                filename = str(now) + ".png"
                cv2.imwrite("C:/image/" + filename, frame)
                # 경로 확인
                cv2.imwrite("./frontend/public/images/" + filename, frame)
                print(filename)
                print(round(emotion_probability * 100))
                break


        # Open two windows
        # Display image ("Emotion Recognition")
        # Display probabilities of emotion
        cv2.imshow('Emotion Recognition', frame)
        cv2.imshow("Probabilities", canvas)
        
        # q to quit
        if cv2.waitKey(33) & 0xFF == ord('q') or cv2.waitKey(33) == 27:
            break

    # Clear program and close windows
    camera.release()
    cv2.destroyAllWindows()
    

if __name__ == "__main__":
    cv2.destroyAllWindows()
    main(sys.argv)
