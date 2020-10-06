<template>
  <v-container fluid class="mt-15 pl-15 pr-15">
    <!-- 카테고리 -->
    <v-card color="basil">
      <v-tabs v-model="tab" background-color="transparent" color="basil" class="basil--text" grow>
        <!-- <v-tabs-slider></v-tabs-slider> -->
        <v-tab href="#tab-1" class="tab-text">대기 중인 기부요청</v-tab>
        <v-tab href="#tab-2" class="tab-text">영상 관리</v-tab>
      </v-tabs>
    </v-card>

    <v-tabs-items v-model="tab">
      <v-tab-item class="justify-content-left p-3" value="tab-1">
        <v-row>
          <div class="col-12 col-sm-6 col-md-3" v-for="(donation, index) in waitList" :key="index">
            <v-card max-width="400" style="overflow:hidden;">
              <div style="height:150px;width:100%;">
                <img class="donation-img white--text" :src="getImg(donation.img)" />
              </div>
              <v-card-title class="pb-0 mb-1 donation-title">{{ donation.title }}</v-card-title>
              <v-card-text class="text--primary pb-0">
                <div class="mb-3">{{ donation.receiver }}</div>
                <v-col class="d-flex justify-content-end pt-0">
                  <v-btn class="green-mbtn px-2" data-toggle="modal" data-target="#waitDetail" @click="goDoWaitDetail(donation)">
                    상세보기
                  </v-btn>
                </v-col>
              </v-card-text>
            </v-card>
          </div>
        </v-row>
        <!-- 페이지네이션 -->
        <v-row class="justify-content-center mt-8">
          <v-pagination v-model="page" :length="length" :page="page" :total-visible="totalVisible"></v-pagination>
        </v-row>
      </v-tab-item>
      <v-tab-item class="row justify-content-center p-3" value="tab-2">
        <!-- 유튜브 크롤링 -->
        <div class="container">
          <v-btn class="green-mbtn" style="float:right;" v-if="!crawlingFlag" @click="crawlingFlag = true">크롤링</v-btn>
          <v-btn class="green-mbtn" style="float:right;" v-if="crawlingFlag" @click="crawlingFlag = false">크롤링 취소</v-btn>
        </div>
        <div
          v-if="crawlingFlag"
          fluid
          class="col-12 col-md-6 col-lg-6 col-sm-6 p-5"
          style="margin:50px auto;text-align:center;border:2px solid lightgray; border-radius:8px;"
        >
          <p style="font-size:1.2rem;">YouTube에서 영상을 가져옵니다.</p>
          <v-text-field v-model="word" label="검색어" id="id"></v-text-field>
          <v-select :items="items" label="연령대" dense outlined v-model="age"></v-select>
          <v-btn class="green-mbtn" @click="crawling">크롤링</v-btn>
        </div>
        <div class="col-12"></div>
        <!-- 크롤링 리스트 -->
        <div class="col-10 col-xl-3 col-lg-3 col-md-3">
          <v-select :items="items" label="연령대" dense outlined v-model="vage"></v-select>
        </div>
        <div class="col-12"></div>
        <v-row>
          <div class="col-12 col-sm-6 col-md-3" v-for="(video, index) in videos" :key="index">
            <v-hover>
              <template v-slot:default="{ hover }">
                <v-card max-width="400" style="overflow:hidden;">
                  <!-- <v-card-title class="pb-0 mb-1 youtube-title">{{ video.title }}</v-card-title> -->
                  <div hover style="height:250px;width:100%;">
                    <img class="youtube-img white--text" style="background-size:contain" :src="video.thumbnail" @click="overlay = true" />
                  </div>
                  <v-fade-transition>
                    <v-overlay v-if="hover" absolute color="black" class="d-flex justify-content-between" style="padding: 20px;">
                      <p style="color:white;font-size:1rem;">{{ video.title }}</p>
                      <div class="d-flex justify-content-center">
                        <v-btn class="mr-5" @click="(tempVid = video.videoId), (view = true)">보기</v-btn>
                        <v-btn @click="(tempId = video.youtubeid), (del = true)">삭제</v-btn>
                      </div>
                    </v-overlay>
                  </v-fade-transition>
                </v-card>
              </template>
            </v-hover>
          </div>
        </v-row>
        <div class="col-12"></div>
        <!-- 페이지네이션 -->
        <v-row class="justify-content-center mt-8">
          <v-pagination v-model="vpage" :length="vlength" :page="vpage" :total-visible="totalVisible"></v-pagination>
        </v-row>
        <!-- 비디오 보기 -->
        <v-dialog dark v-model="view" max-width="800">
          <v-card>
            <v-card-actions>
              <v-spacer></v-spacer>
              <LazyYoutubeVideo :src="makeUrl(tempVid)" style="width:100%;height:100%;" />
              <v-icon @click="view = false" large class="ml-5 mr-3">mdi-close</v-icon>
            </v-card-actions>
          </v-card>
        </v-dialog>

        <v-dialog v-model="del" max-width="400">
          <v-card>
            <v-card-title class="headline">정말 삭제하시겠습니까?</v-card-title>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn text @click="videoDelete(tempId), (del = false)" color="#DC143C">확인</v-btn>
              <v-btn text @click="del = false">취소</v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-tab-item>
    </v-tabs-items>
    <WaitDetailComp :wait="this.goWaitDetail" :waitImages="this.goWaitImages" />
  </v-container>
</template>

<script>
import http from '@/util/http-common.js';
import WaitDetailComp from '@/components/admin/WaitDetailComp.vue';
import LazyYoutubeVideo from "vue-lazy-youtube-video";

export default {
  name: 'AdminPage',
  components: {
    WaitDetailComp,
    LazyYoutubeVideo,
  },
  data() {
    return {
      tab: null,
      waitList: [],
      goWaitDetail: [],
      goWaitImages: [],
      word: '',
      age: 0,
      items: [1, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100],
      length: 0,
      page: 1,
      totalVisible: 10,
      crawlingFlag: false,
      videos: [],
      overlay: false,
      del: false,
      view: false,
      tempId: 0,
      tempVid: '',
      vage: 0,
      vpage: 1,
      vlength: 0,
    };
  },
  created() {
    this.init();
    this.getWaitCnt();
    this.getVideoCnt();
  },
  methods: {
    goDoWaitDetail(dona) {
      this.goWaitDetail = dona;
      this.goWaitImages = dona.img.substring(0, dona.img.length - 1).split('|');
    },
    init() {
      http
        .get('/donation/getWaitList/0')
        .then((res) => {
          this.waitList = res.data;
        })
        .catch((err) => {
          console.log(err);
        });
      http
        .get('/crawling/getVideosP/0')
        .then((res) => {
          this.videos = res.data.content;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getDetail(donationid) {
      scroll(0, 0);
      this.$router.push({
        name: 'DonationRequestDetail',
        params: { ID: donationid },
      });
    },
    getImg(img) {
      img = img.split('|')[0];
      return '../../../contents/' + img;
    },
    crawling() {
      if (this.age == 0 || this.word == '') {
        alert('크롤링 정보를 입력하세요.');
      } else {
        http
          .post(`/crawling/youtube?search=${this.word}&age=${this.age}`)
          .then((res) => {
            this.log = res.data;
            this.word = '';
            this.age = 0;
            this.getVideo();
          })
          .catch((err) => {
            console.log(err);
          });
      }
    },
    getWaitCnt() {
      http
        .get('/donation/getWaitListCnt')
        .then((res) => {
          if (res.data % 8 == 0) {
            this.length = Math.floor(res.data / 8);
          } else {
            this.length = Math.floor(res.data / 8) + 1;
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getVideo() {
      if(this.vage == 0) {
        http
        .get(`/crawling/getVideosP/${this.vpage - 1}`)
        .then((res) => {
          this.videos = res.data.content;
        })
        .catch((err) => {
          console.log(err);
        });
      } else {
        http
        .get(`/crawling/getVideosByAgeP/${this.vage}/${this.vpage - 1}`)
        .then((res) => {
          this.videos = res.data;
        })
        .catch((err) => {
          console.log(err);
        });
      }
    },
    makeUrl(url) {
      return 'https://www.youtube.com/embed/' + url;
    },
    videoDelete(id) {
      http
        .delete('/crawling/deleteVideo/' + id)
        .then((res) => {
          console.log(res.data);
          this.vpage = 1;
          if(this.vage == 0) {
            this.getVideoCnt();
          } else {
            this.getVideoByAgeCnt(this.vage);
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    stopVideo() {
      document.querySelector("iframe").src = '';
    },
    getVideoCnt() {
      http
        .get("/crawling/getVideoCnt")
        .then(res => {
          if (res.data % 8 == 0) {
            this.vlength = Math.floor(res.data / 8);
          } else {
            this.vlength = Math.floor(res.data / 8) + 1;
          }
        })
        .catch(err => {
          console.log(err);
        })
    },
    getVideoByAgeCnt(age) {
      http
        .get("/crawling/getVideoByAgeCnt/" + age)
        .then(res => {
          if (res.data % 8 == 0) {
            this.vlength = Math.floor(res.data / 8);
          } else {
            this.vlength = Math.floor(res.data / 8) + 1;
          }
          this.getVideo();
        })
        .catch(err => {
          console.log(err);
        })
    },
  },
  watch: {
    page(page) {
      page = page - 1;
      http
        .get(`/donation/getWaitList/${page}`)
        .then((res) => {
          this.waitList = res.data;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    vpage: function() {
      this.getVideo();
    },
    view: function() {
      if(this.view == false) {
        this.stopVideo();
      }
    },
    vage: function() {
      this.vpage = 1;
      this.getVideoByAgeCnt(this.vage);
    },
  },
};
</script>
<style scoped>
.donation-img {
  height: 150px !important;
  width: 100% !important;
}

.donation-title {
  display: block;
  height: 40px;
  /* align-items: baseline; */
  text-overflow: ellipsis;
  overflow: hidden;
  line-height: 1.8rem;
  white-space: nowrap;
  text-align: left;
}
.basil {
  background-color: #fffbe6 !important;
}
.basil--text {
  color: #356859 !important;
}
.tab-text {
  font-weight: bold;
  text-decoration: none;
}
.youtube-img {
  height: 250px !important;
  width: 100% !important;
  /* object-fit: cover; */
  transform: scale(1);
  transition: all 0.3s ease-in-out;
}
</style>
