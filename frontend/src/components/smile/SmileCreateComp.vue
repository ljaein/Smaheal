<template>
  <div class="container" style="height:1920px;">
    <!-- stepper -->
    <v-row>
      <v-col cols="12">
        <v-stepper v-model="e1">
          <v-stepper-header>
            <v-stepper-step :complete="e1 > 1" step="1">step 1</v-stepper-step>
            <v-divider></v-divider>
            <v-stepper-step :complete="e1 > 2" step="2">step 2</v-stepper-step>
            <v-divider></v-divider>
            <v-stepper-step step="3">step 3</v-stepper-step>
          </v-stepper-header>

          <v-stepper-items>
            <v-stepper-content step="1">
              <v-card class="mb-8" color="grey lighten-1" height="400px">
                <p>먼저 스마힐 사용법에 대해 알아볼까요?</p>
                <p>웃음을 기부하는 방법은 크게 3가지가 있어요.</p>
                <ol>
                  <li>재밌는 사진을 보면서 웃는다.</li>
                  <li>재밌는 동영상을 보면서 웃는다.</li>
                  <li>셀카모드로 직접 웃는 사진을 캡쳐한다.</li>
                </ol>
              </v-card>
              <v-btn color="primary" @click="e1 = 2">
                Continue
              </v-btn>
            </v-stepper-content>

            <v-stepper-content step="2">
              <v-card class="mb-8" color="grey lighten-1" height="400px">
                <p>사진과 동영상을 보고 웃음을 기부하는 방법은 웃으면 자동으로 캡쳐가 됩니다.</p>
                <p>셀카모드는 얼굴을 보면서 직접 캡쳐하여 웃음을 인증 받은 후에 기부가 진행됩니다.</p>
                <p>카메라와 너무 멀리 떨어져 있으면 캡쳐가 안될 수 있어요.</p>
              </v-card>
              <v-btn color="primary" @click="e1 = 3">
                Continue
              </v-btn>
              <v-btn text @click="e1 = 1">Back</v-btn>
            </v-stepper-content>

            <v-stepper-content step="3">
              <v-card class="mb-8" color="grey lighten-1" height="400px">
                <p>웃음 기부 전에 웃음왕에 사진이 쓰일지에 대한 여부를 선택하고 기부를 하게 됩니다.</p>
                <p>어렵지 않죠?</p>
                <p>그러면 웃음 기부를 시작해볼까요?</p>
              </v-card>
              <v-btn color="primary" @click="stepEnd">
                Complete
              </v-btn>
              <v-btn text @click="e1 = 2">Back</v-btn>
            </v-stepper-content>
          </v-stepper-items>
        </v-stepper>
      </v-col>
    </v-row>

    <v-row style="height:300px"></v-row>

    <!-- 캡쳐 종류 선택 -->
    <v-row style="height:700px;" class="d-flex justify-content-center pt-15">
      <v-col cols="10">
        <v-card>
          <v-tabs v-model="tab" background-color="#fffbe3" color="basil" class="basil--text" centered icons-and-text>
            <v-tabs-slider></v-tabs-slider>
            <v-tab class="tab-text" href="#tab-contents" @click="someContents()">
              Contents
              <v-icon>mdi-animation</v-icon>
            </v-tab>

            <v-tab class="tab-text" href="#tab-self" @click="selfieContents()">
              Selfie
              <v-icon>mdi-account-box</v-icon>
            </v-tab>

            <v-tab class="tab-text" href="#tab-file" @click="selfieContents()">
              Upload
              <v-icon>mdi-file-upload</v-icon>
            </v-tab>
          </v-tabs>

          <v-tabs-items v-model="tab">
            <!-- 콘텐츠 -->
            <v-tab-item v-if="!contentsFlag" id="tab-contents">
              <div class="container" style="height:400px;text-align:center">
                <v-btn color="#356859" class="col-2" style="top:170px;color:white;" @click="(contentsFlag = true), cameraOn()">start</v-btn>
              </div>
            </v-tab-item>
            <v-tab-item v-if="contentsFlag" id="tab-contents">
              <v-row align="center" class="container m-0">
                <v-item-group v-model="window" class="shrink" mandatory tag="v-flex">
                  <!-- 사진 -->
                  <v-item v-slot:default="{ active, toggle }" class="mb-5">
                    <div>
                      <v-btn :input-value="active" icon @click="toggle">
                        <v-icon>mdi-image-multiple</v-icon>
                      </v-btn>
                    </div>
                  </v-item>
                  <!-- 영상 -->
                  <v-item v-slot:default="{ active, toggle }">
                    <div>
                      <v-btn :input-value="active" icon @click="toggle">
                        <v-icon>mdi-video</v-icon>
                      </v-btn>
                    </div>
                  </v-item>
                </v-item-group>

                <!-- 컨텐츠 내용 -->
                <v-col>
                  <v-window v-model="window" class="elevation-1" vertical>
                    <!-- 사진 -->
                    <v-window-item>
                      <v-card flat>
                        <v-card-text>
                          <div class="container" style="height:400px;text-align:center">
                            <v-carousel style="height:100%;">
                              <v-carousel-item reverse-transition="fade-transition" transition="fade-transition">
                                <img
                                  src="https://s-i.huffpost.com/gen/3948866/thumbs/o-PEPE-THE-FROG-570.jpg?3"
                                  style="max-width:100%;width:400px;height:65%;"
                                  alt=""
                                />
                              </v-carousel-item>
                              <v-carousel-item reverse-transition="fade-transition" transition="fade-transition">
                                <img
                                  src="https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQaqQZ3o8bafo4ngTZFIM2vxkSXTBcSr_osfQ&usqp=CAU"
                                  style="max-width:100%;width:400px;height:65%;"
                                  alt=""
                                />
                              </v-carousel-item>
                              <v-carousel-item reverse-transition="fade-transition" transition="fade-transition">
                                <img
                                  src="https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcTCdSE06g-wWMbwV39-LgNCI23dcrefSkhosA&usqp=CAU"
                                  style="max-width:100%;width:400px;height:65%;"
                                  alt=""
                                />
                              </v-carousel-item>
                            </v-carousel>
                          </div>
                        </v-card-text>
                      </v-card>
                    </v-window-item>
                    <!-- 영상 -->
                    <v-window-item>
                      <v-card flat>
                        <v-card-text>
                          <LazyYoutubeVideo :src="makeUrl(videos[1].videoId)" style="width:100%;height:100%;" />
                        </v-card-text>
                      </v-card>
                    </v-window-item>
                  </v-window>
                </v-col>
              </v-row>
            </v-tab-item>

            <!-- 셀카 -->
            <v-tab-item v-if="!selfFlag" id="tab-self">
              <div class="container" style="height:400px;text-align:center">
                <v-btn color="#356859" class="col-2" style="top:170px;color:white;" @click="selfieInit()">start</v-btn>
              </div>
            </v-tab-item>
            <v-tab-item v-if="selfFlag" id="tab-self">
              <div class="container" style="height:400px;text-align:center">
                <video src="" autoplay class="feed" width="100%" height="100%" id="webcam" @click="init(), (camOn = true)"></video>
              </div>
              <v-row class="d-flex justify-content-center">
                <v-btn color="#356859" class="m-2 col-md-2 col-sm-4 col-10" style="color:white;" v-if="startFlag" @click="selfieStart()"
                  >start</v-btn
                >
                <v-btn color="#356859" class="m-2 col-md-2 col-sm-4 col-10" style="color:white;" v-if="endFlag" @click="selfieStop()"
                  >stop</v-btn
                >
                <v-btn color="#356859" class="m-2 col-md-2 col-sm-4 col-10" style="color:white;" v-if="endFlag" @click="selfieCap()"
                  >capture</v-btn
                >
                <v-btn color="#356859" class="m-2 col-md-2 col-sm-4 col-10" style="color:white;" v-if="checkFlag" @click="check()"
                  >check</v-btn
                >
              </v-row>

              <div v-if="!startFlag">
                <div class="container" style="height:250px;text-align:center">
                  <canvas height="200%" width="200%"></canvas>
                  <img id="myImage" />
                  <!-- {{ selfieCapture }} -->
                </div>
                <div v-if="selCapFlag">
                  <!-- 사진 사용 여부 -->
                  <v-row>
                    <v-switch style="margin:0 auto" v-model="kingFlag" inset :label="`사진 허용 `"></v-switch>
                  </v-row>
                  <!-- 한줄 코멘트 -->
                  <v-row class="justify-content-center">
                    <v-col cols="6">
                      <v-text-field v-model="comment" :rules="commentRules" :counter="20" label="한 줄 메세지"></v-text-field>
                    </v-col>
                  </v-row>
                  <!-- 기부하기 -->
                  <v-row>
                    <v-btn style="margin:0 auto 50px auto;color:white;" class="col-3" color="#356859" @click="donationSelfie"
                      >기부하기</v-btn
                    >
                  </v-row>
                </div>
              </div>
            </v-tab-item>

            <!-- 파일 업로드 -->
            <v-tab-item v-if="!fileFlag" id="tab-file">
              <div class="container" style="height:400px;text-align:center">
                <v-btn color="#356859" class="col-2" style="top:170px;color:white;" @click="uploadInit()">start</v-btn>
              </div>
            </v-tab-item>
            <v-tab-item v-if="fileFlag" id="tab-file">
              <div class="container" style="height:250px;text-align:center">
                <canvas height="200%" width="200%"></canvas>
                <img id="myImage" />
                <!-- {{ selfieCapture }} -->
              </div>

              <div v-if="inputFile != ''" style="margin:0 auto;">
                <img :src="preImg(inputFile)" style="max-width:100%;width:400px;height:65%;" />
              </div>

              <div class="col-lg-6 col-md-6 col-sm-8 col-10" style="margin:0 auto;">
                파일 업로드
                <v-file-input
                  ref="file"
                  accept="image/png, image/jpeg, image/bmp"
                  v-model="inputFile"
                  color="#356859"
                  counter
                  prepend-icon
                  outlined
                >
                  <template v-slot:selection="{ text }">
                    <v-chip color="#356859" dark label small>{{ text }}</v-chip>
                  </template>
                </v-file-input>
              </div>
              <div>
                <!-- 사진 사용 여부 -->
                <v-row>
                  <v-switch style="margin:0 auto" v-model="kingFlag" inset :label="`사진 허용 `"></v-switch>
                </v-row>
                <!-- 한줄 코멘트 -->
                <v-row class="justify-content-center">
                  <v-col cols="6">
                    <v-text-field v-model="comment" :rules="commentRules" :counter="20" label="한 줄 메세지"></v-text-field>
                  </v-col>
                </v-row>
                <!-- 기부하기 -->
                <v-row>
                  <v-btn style="margin:0 auto 50px auto;color:white;" class="col-3" color="#356859" @click="donationSelfie">기부하기</v-btn>
                </v-row>
              </div>
            </v-tab-item>
          </v-tabs-items>
        </v-card>
      </v-col>
    </v-row>
    <div v-if="autoCapFlag">
      <v-row>
        <v-card style="margin:0 auto;">
          <img :src="getImg(autoCapture[0])" alt="캡쳐된 이미지" />
          <!-- <v-img src="../../../public/images/1600678282188.png" alt="autoCapture"></v-img> -->
        </v-card>
      </v-row>
      <v-row class="justify-content-center">
        <v-btn
          class="col-lg-2 col-md-2 col-sm-5 col-5 mx-10 my-5"
          color="#356859"
          style="color:white;"
          @click="(contentsFlag = false), (autoCapFlag = false), stepEnd()"
          >다시찍기</v-btn
        >
        <v-btn class="col-lg-2 col-md-2 col-sm-5 col-5 mx-10 my-5" color="#356859" style="color:white;" @click="selectFlag = true"
          >사진확정</v-btn
        >
      </v-row>
    </div>

    <div v-if="autoCapFlag">
      <!-- 사진 사용 여부 -->
      <v-row class="col-4 p-0 mt-5" style="margin:0 auto;">
        <v-switch v-model="kingFlag" inset label="사진 허용" style="margin:0 auto;"></v-switch>
      </v-row>
      <!-- 한줄 코멘트 -->
      <v-row class="justify-content-center">
        <v-col cols="6">
          <v-text-field v-model="comment" :rules="commentRules" :counter="20" label="한 줄 메세지"></v-text-field>
        </v-col>
      </v-row>
      <!-- 기부하기 -->
      <v-row class="col-4 mb-10" style="margin:0 auto;">
        <v-btn v-if="selectFlag" color="#356859" @click="donationContents" style="margin:0 auto;color:white;">기부하기</v-btn>
      </v-row>
    </div>

    <!-- 로딩 오버레이 -->
    <v-overlay :value="overlay">
      <v-progress-circular v-if="(value > 0) & (value < 100)" :rotate="-90" :size="100" :width="15" :value="value" color="white">
        {{ value }}
      </v-progress-circular>
    </v-overlay>

    <!-- 캡쳐 알림 -->
    <v-snackbar v-model="captureFlag" top right :timeout="3000" color="error">
      캡쳐되었습니다.
      <template v-slot:action="{ attrs }">
        <v-btn text v-bind="attrs" @click="captureFlag = false">
          Close
        </v-btn>
      </template>
    </v-snackbar>

    <!-- 기부 알림 -->
    <v-snackbar v-model="donationFlag" top right :timeout="3000" color="error">
      기부되었습니다.
      <template v-slot:action="{ attrs }">
        <v-btn text v-bind="attrs" @click="donationFlag = false">
          Close
        </v-btn>
      </template>
    </v-snackbar>

    <!-- 로딩 오버레이 -->
    <v-overlay :value="donationFlag">
      <v-progress-circular :size="70" width="6" indeterminate color="amber" style="margin-left:50%"></v-progress-circular>
    </v-overlay>
  </div>
</template>

<script>
import http from '@/util/http-common.js';
import { mapGetters, mapState } from 'vuex';
import LazyYoutubeVideo from 'vue-lazy-youtube-video';
// npm install --save vue-lazy-youtube-video

export default {
  components: {
    LazyYoutubeVideo,
  },
  created() {
    scroll(0, 0);
    this.cameraOff();
    this.donationid = this.$route.params.ID;
    this.uid = this.getUserID;
    this.getAge();
  },
  data() {
    return {
      donationid: 0,
      uid: '',
      uage: '',
      e1: 1,
      log: '',
      value: 0,
      comment: '',
      interval: {},
      length: 2,
      window: 0,
      tab: null,
      overlay: false,
      contentsFlag: false,
      selfFlag: false,
      captureFlag: false,
      autoCapFlag: false,
      kingFlag: false,
      camOn: false,
      startFlag: true,
      endFlag: false,
      checkFlag: false,
      selCapFlag: false,
      selectFlag: false,
      donationFlag: false,
      fileFlag: false,
      commentRules: [(v) => v.length <= 20 || '20자 이내로 써주세요.'],
      autoCapture: [],
      selfieCapture: [],
      videos: [],
      inputFile: [],
    };
  },
  methods: {
    getAge() {
      this.uage = new Date().getFullYear() - this.getUserBirth.substr(0, 4);
      if (this.uage < 10) {
        this.uage = 1;
      } else {
        this.uage = this.uage - (this.uage % 10);
      }
      this.getVideos();
    },
    getVideos() {
      http
        .get(`/crawling/getVideosByAge/${this.uage}`)
        .then((res) => {
          this.videos = res.data;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    playing() {
      console.log('we are watching!!!');
    },
    check() {
      var myImage = document.querySelector('canvas').toDataURL();
      //btoa
      http
        .post(`/smile/smileCheck`, myImage)
        .then((res) => {
          this.selfieCapture = res.data;
          if (this.selfieCapture == 'findFail') {
            alert('얼굴 인식 못함');
          } else {
            this.selCapFlag = true;
            alert('얼굴 인식 성공');
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    capture() {
      const picture = document.querySelector('canvas');
      const ctx = picture.getContext('2d');
      ctx.imageSmoothingEnabled = true;
      ctx.imageSmoothingQuality = 'high';
      ctx.drawImage(document.getElementById('webcam'), 0, 0, picture.width, picture.height);
    },
    stop() {
      if (this.camOn) {
        if (document.getElementById('webcam') != null) {
          const stream = document.getElementById('webcam').srcObject;
          const tracks = stream.getTracks();
          tracks.forEach(function(track) {
            track.stop();
          });

          document.getElementById('webcam').srcObject = null;
        }
        this.camOn = false;
      }
    },
    init() {
      console.log(navigator)
      if ('mediaDevices' in navigator && 'getUserMedia' in navigator.mediaDevices) {
        navigator.mediaDevices.getUserMedia({ video: true }).then((stream) => {
          const videoPlayer = document.getElementById('webcam');
          videoPlayer.srcObject = stream;
          videoPlayer.play();
        });
      } else {
        alert('cannot get Media');
      }
    },
    stepEnd() {
      scroll(0, 920);
    },
    cameraOn() {
      this.loading();
      http
        .get('/smile/cameraOn')
        .then((res) => {
          if (res.data[0] != 'cancel') {
            this.captureFlag = true;
            this.autoCapFlag = true;
            this.autoCapture = res.data;
            scroll(0, 1700);
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    cameraOff() {
      http
        .get('/smile/cameraOff')
        .then((res) => {
          this.log = res.data;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getImg(img) {
      return '../../../images/' + img;
    },
    makeUrl(url) {
      return 'https://www.youtube.com/embed/' + url;
    },
    loading() {
      this.overlay = true;
      this.interval = setInterval(() => {
        if (this.value === 100) {
          this.overlay = false;
          clearInterval(this.interval);
          return;
        }
        this.value += 10;
      }, 300);
    },
    donationSelfie() {
      if (this.selfieCapture == 'findFail') {
        alert('사진 다시 찍어주세요');
      } else {
        if (this.selfieCapture[2] < 30) {
          alert('웃음 지수가 너무 낮습니다.');
        } else {
          http
            .post('/smile/regist', {
              user_id: this.uid,
              donationid: this.donationid,
              photo: this.selfieCapture[0],
              smileper: this.selfieCapture[2],
              comment: this.comment,
              agreement: this.kingFlag ? 1 : 0,
            })
            .then((res) => {
              this.stop();
              this.log = res.data;
              this.donationFlag = true;
              let x = this;
              setTimeout(function() {
                x.$router.push('/donationlist');
              }, 1500);
            })
            .catch((err) => {
              console.log(err);
            });
        }
      }
    },
    donationContents() {
      http
        .post('/smile/regist', {
          user_id: this.uid,
          donationid: this.donationid,
          photo: this.autoCapture[0],
          smileper: this.autoCapture[1],
          comment: this.comment,
          agreement: this.kingFlag ? 1 : 0,
        })
        .then((res) => {
          this.log = res.data;
          this.donationFlag = true;
          let x = this;
          setTimeout(function() {
            x.$router.push('/donationlist');
          }, 1500);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    selfieInit() {
      this.selfFlag = true;
      this.checkFlag = false;
      this.selCapFlag = false;
      this.endFlag = false;
    },
    selfieStart() {
      this.init();
      this.camOn = true;
      this.startFlag = false;
      this.endFlag = true;
    },
    selfieStop() {
      this.stop();
      this.startFlag = true;
      this.endFlag = false;
      this.checkFlag = false;
      this.selCapFlag = false;
      this.selfieCapture = [];
    },
    selfieCap() {
      this.capture();
      this.selfieCapture = [];
      this.checkFlag = true;
    },
    selfieContents() {
      this.autoCapFlag = false;
      this.startFlag = true;
      this.contentsFlag = false;
      this.checkFlag = false;
      this.selCapFlag = false;
      this.endFlag = false;
      this.selfFlag = false;
      this.value = 0;
      this.overlay = false;
      this.fileFlag = false;
      this.selfieCapture = [];
      this.stop();
      this.cameraOff();
    },
    someContents() {
      this.selfFlag = false;
      this.value = 0;
      this.overlay = false;
      this.fileFlag = false;
      this.selfieCapture = [];
      this.stop();
    },
    uploadInit() {
      this.selfFlag = false;
      this.contentsFlag = false;
      this.fileFlag = true;
      this.stop();
    },
    preImg(img) {
      if (img != null) {
        return URL.createObjectURL(img);
      }
    },
  },
  computed: {
    ...mapGetters(['getUserID', 'getUserBirth']),
    ...mapState({
      userID: (state) => `${state.user.getUserID}`,
      userBirth: (state) => `${state.user.getUserBirth}`,
    }),
  },
  watch: {
    inputFile: function() {
      console.log(this.inputFile);
    },
  },
};
</script>

<style scoped>
.camera {
  width: 100vw;
  height: 100vh;
  padding: 25px;
  box-sizing: border-box;
}
.feed {
  display: block;
  width: 100%;
  max-width: 1280px;

  margin: 0 auto;

  background-color: #171717;
  box-shadow: 6px 6px 12px 0px rgba(0, 0, 0, 0.35);
}
.basil--text {
  color: #356859 !important;
}
.tab-text {
  font-weight: bold;
  text-decoration: none;
}
</style>
