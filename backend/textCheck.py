import sys
from konlpy.tag import Mecab
from tensorflow.keras.preprocessing.text import Tokenizer
from tensorflow.keras.preprocessing.sequence import pad_sequences
from tensorflow.keras.models import load_model


def main(argv):
    loaded_model = load_model('./files/sentiment_model.hdf5',compile=False)
    mecab = Mecab()
    tokenizer = Tokenizer()
    stopwords = ['도', '는', '다', '의', '가', '이', '은', '한', '에', '하', '고', '을', '를', '인', '듯', '과', '와', '네', '들', '듯', '지', '임', '게']
    new_sentence = mecab.morphs(argv[0]) # 토큰화
    new_sentence = [word for word in new_sentence if not word in stopwords] # 불용어 제거
    encoded = tokenizer.texts_to_sequences([new_sentence]) # 정수 인코딩
    pad_new = pad_sequences(encoded, maxlen = 80) # 패딩
    score = float(loaded_model.predict(pad_new)) # 예측
    if(score > 0.5):
        print("{:.2f}% 확률로 긍정 메세지입니다.".format(score * 100))
    else:
        print("{:.2f}% 확률로 부정 메세지입니다.".format((1 - score) * 100))

if __name__ == "__main__":
    main(sys.argv)