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
          <v-tabs v-model="tab" background-color="deep-purple accent-4" centered dark icons-and-text>
            <v-tabs-slider></v-tabs-slider>
            <v-tab href="#tab-picture" @click="(videoFlag = false), (selfFlag = false), (value = 0), (overlay = false), stop()">
              Picture
              <v-icon>mdi-image-multiple</v-icon>
            </v-tab>

            <v-tab href="#tab-video" @click="(pictureFlag = false), (selfFlag = false), (value = 0), (overlay = false), stop()">
              Video
              <v-icon>mdi-video</v-icon>
            </v-tab>

            <v-tab href="#tab-self" @click="(videoFlag = false), (pictureFlag = false), (value = 0), (overlay = false), stop()">
              Selfie
              <v-icon>mdi-account-box</v-icon>
            </v-tab>
          </v-tabs>

          <v-tabs-items v-model="tab">
            <!-- 사진 -->
            <v-tab-item v-if="!pictureFlag" id="tab-picture">
              <div class="container" style="height:500px;text-align:center;">
                <v-btn color="success" class="col-2" style="top:220px;" @click="(pictureFlag = true), cameraOn()">start</v-btn>
              </div>
            </v-tab-item>

            <v-tab-item v-if="pictureFlag" id="tab-picture">
              <div class="container" style="height:100%;text-align:center">
                <v-carousel>
                  <v-carousel-item reverse-transition="fade-transition" transition="fade-transition">
                    <img
                      src="https://s-i.huffpost.com/gen/3948866/thumbs/o-PEPE-THE-FROG-570.jpg?3"
                      style="max-width:100%;width:500px;height:90%;"
                      alt=""
                    />
                  </v-carousel-item>
                  <v-carousel-item reverse-transition="fade-transition" transition="fade-transition">
                    <img
                      src="https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQaqQZ3o8bafo4ngTZFIM2vxkSXTBcSr_osfQ&usqp=CAU"
                      style="max-width:100%;width:500px;height:90%;"
                      alt=""
                    />
                  </v-carousel-item>
                  <v-carousel-item reverse-transition="fade-transition" transition="fade-transition">
                    <img
                      src="https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcTCdSE06g-wWMbwV39-LgNCI23dcrefSkhosA&usqp=CAU"
                      style="max-width:100%;width:500px;height:90%;"
                      alt=""
                    />
                  </v-carousel-item>
                </v-carousel>
              </div>
              <div class="container" style="height:300px;text-align:center">
                <img
                  src="https://s-i.huffpost.com/gen/3948866/thumbs/o-PEPE-THE-FROG-570.jpg?3"
                  style="max-width:100%;width:300px;height:100%"
                  alt="캡쳐된 이미지 나올 자리"
                />
              </div>
              <!-- 사진 사용 여부 -->
              <v-row>
                <v-switch style="margin:0 auto" v-model="kingFlag" inset :label="`사진 허용 `"></v-switch>
              </v-row>
              <!-- 기부하기 -->
              <v-row>
                <v-btn style="margin:0 auto 50px auto;" class="col-3" color="success" @click="donation">기부하기</v-btn>
              </v-row>
            </v-tab-item>

            <!-- 동영상 -->
            <v-tab-item v-if="!videoFlag" id="tab-video">
              <div class="container" style="height:500px;text-align:center">
                <v-btn color="success" class="col-2" style="top:220px;" @click="(videoFlag = true), cameraOn()">start</v-btn>
              </div>
            </v-tab-item>
            <v-tab-item v-if="videoFlag" id="tab-video">
              <div class="container" style="height:500px;text-align:center">
                <video controls height="100%" width="100%">
                  <source src="../../../public/video/video1.mp4" type="video/mp4" />
                </video>
              </div>
              <div class="container" style="height:300px;text-align:center">
                <img
                  src="https://s-i.huffpost.com/gen/3948866/thumbs/o-PEPE-THE-FROG-570.jpg?3"
                  style="max-width:100%;width:300px;height:100%"
                  alt="캡쳐된 이미지 나올 자리"
                />
              </div>
              <!-- 사진 사용 여부 -->
              <v-row>
                <v-switch style="margin:0 auto" v-model="kingFlag" inset :label="`사진 허용 `"></v-switch>
              </v-row>
              <!-- 기부하기 -->
              <v-row>
                <v-btn style="margin:0 auto 50px auto;" class="col-3" color="success" @click="donation">기부하기</v-btn>
              </v-row>
            </v-tab-item>

            <!-- 셀카 -->
            <v-tab-item v-if="!selfFlag" id="tab-self">
              <div class="container" style="height:500px;text-align:center">
                <v-btn color="success" class="col-2" style="top:220px;" @click="selfFlag = true">start</v-btn>
              </div>
            </v-tab-item>
            <v-tab-item v-if="selfFlag" id="tab-self">
              <div class="container" style="height:500px;text-align:center">
                <video src="" autoplay class="feed" width="100%" height="100%" id="webcam" @click="init(), (camOn = true)"></video>
              </div>
              <v-row class="d-flex justify-content-center">
                <v-btn color="success" class="m-2 col-md-2 col-sm-4 col-10" @click="init(), (camOn = true)">start</v-btn>
                <v-btn color="success" class="m-2 col-md-2 col-sm-4 col-10" @click="stop">stop</v-btn>
                <v-btn color="success" class="m-2 col-md-2 col-sm-4 col-10" @click="capture">capture</v-btn>
                <v-btn color="success" class="m-2 col-md-2 col-sm-4 col-10" @click="check">check</v-btn>
              </v-row>
              <div class="container" style="height:250px;text-align:center">
                <canvas height="200%" width="200%"></canvas>
                <img id="myImage" />
                {{selfieCapture}}
              </div>
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
                <v-btn style="margin:0 auto 50px auto;" class="col-3" color="success" @click="donation">기부하기</v-btn>
              </v-row>
            </v-tab-item>
          </v-tabs-items>
        </v-card>
      </v-col>
    </v-row>

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
  </div>
</template>

<script>
import http from '@/util/http-common.js';
import { mapGetters, mapState } from 'vuex';

export default {
  created() {
    this.donationid = this.$route.params.ID;
    this.uid = this.getUserID;
  },
  data() {
    return {
      donationid: 0,
      uid: '',
      e1: 1,
      log: '',
      value: 0,
      comment:'',
      interval: {},
      tab: null,
      overlay: false,
      pictureFlag: false,
      videoFlag: false,
      selfFlag: false,
      captureFlag: false,
      kingFlag: false,
      camOn: false,
      commentRules:[
        v => v.length <= 20 || "ss"
      ],
      selfieCapture:[]
    };
  },
  methods: {
    check() {
      var myImage = document.querySelector('canvas').toDataURL();
      //btoa
      http.post(`/smile/smileCheck`, myImage)
      .then(res => {
        this.selfieCapture = res.data;
      })
      .catch(err => {
        console.log(err);
      })
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
      scroll(0, 970);
    },
    cameraOn() {
      this.loading();
      http
        .get('/smile/cameraOn')
        .then((res) => {
          this.captureFlag = true;
          this.log = res.data;
          scroll(0, 1500);
        })
        .catch((err) => {
          console.log(err);
        });
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
    donation() {
      // alert('기부 완료');
      if(this.selfieCapture[2] < 30) {
        alert("웃음 지수가 너무 낮습니다.");
      } else {
        http.post("/smile/regist", {
          user_id: this.uid,
          donationid: this.donationid,
          photo: this.selfieCapture[0],
          smileper: this.selfieCapture[2],
          comment: this.comment,
          agreement: this.kingFlag ? 1 : 0,
        })
        .then(res => {
          console.log(res.data)
        })
        .catch(err => {
          console.log(err);
        })
      }
    },
  },
  computed: {
    ...mapGetters(['getUserID']),
    ...mapState({
      userID: (state) => `${state.user.getUserID}`,
    }),
  },
};
</script>

<style>
html {
  scroll-behavior: smooth;
}
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
</style>
