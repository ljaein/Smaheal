import sys
import cv2

def main(argv):
    if cv2.VideoCapture(0).isOpened():
        cv2.destroyAllWindows()
    

if __name__ == "__main__":
    main(sys.argv)