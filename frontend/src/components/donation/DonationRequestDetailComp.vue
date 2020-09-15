<template>
  <v-container fluid class="mt-5 px-0">
    <!-- 카테고리 -->
    <v-row>
      <v-col cols="12" class="pb-0 ml-5">
        <h3>
            카테고리 부분
          <!-- {{ donation.title }} -->
        </h3>
      </v-col>
    </v-row>
    <!-- 제목 -->
    <v-row>
      <v-col cols="12" class="pt-0 ml-5">
        <h1>
          {{ donation.title }}
        </h1>
      </v-col>
    </v-row>
    <!-- 사진 carousel -->
    <v-row justify="center" style="margin-bottom:100px;">
      <v-col cols="12" class="px-0">
        <v-carousel width="100%">
          <v-carousel-item :src="photo" reverse-transition="fade-transition" transition="fade-transition"></v-carousel-item>
        </v-carousel>
      </v-col>
    </v-row>
    <!-- 내용 -->
    <v-row class="d-flex justify-content-center">
      <v-tabs
        background-color="yellow accent-1"
        class="pt-0 pr-0 col-md-10 col-sm-12"
        color="black"
        style="position:sticky;top:100px;z-index:5"
      >
        <v-tab>
          <a class="nav-link" href=".section1" @click="scrollSpy" style="color:black">상세내용</a>
        </v-tab>
        <v-tab>
          <a class="nav-link" href="#section2" @click="scrollSpy" style="color:black">주소 & 지도</a>
        </v-tab>
        <v-tab>
          <a class="nav-link" href="#section3" @click="scrollSpy" style="color:black">응원메세지</a>
        </v-tab>
      </v-tabs>
      <v-col cols="8" md="7">
        <body data-spy="scroll" data-target=".navbar" data-offset="50">
          <div class="section1 container-fluid" style="padding-top:70px;padding-bottom:70px">
            <h3>상세내용</h3>
            {{ donation.content }}
          </div>
          <div id="section2" class="container-fluid" style="padding-top:70px;padding-bottom:70px">
            <h3>주소 & 지도</h3>
            {{ donation.address }}
          </div>
          <div id="section3" class="container-fluid" style="padding-top:70px;padding-bottom:70px">
            <h3>응원메세지</h3>
            <p>
              화이팅
            </p>
            <p>
              힘내세요
            </p>
            <p>
              화이팅
            </p>
            <p>
              힘내세요
            </p>
            <p>
              화이팅
            </p>
            <p>
              힘내세요
            </p>
            <!-- 메세지 입력 -->
            <v-text-field type="text" :counter=50 prepend-icon="mdi-comment" name="cheerup" label="응원의 메세지" v-model="cheerup"></v-text-field>
            <v-btn class="mt-2" style="float:right" @click="msgSubmit">submit</v-btn>
          </div>
        </body>
      </v-col>
      <!-- 플로팅 메뉴 -->
      <v-col cols="4" md="3">
        <v-card class="mx-auto" max-width="344" height="auto" outlined elevation="3" style="position:sticky;top:200px;z-index:5">
          <v-list-item three-line>
            <v-list-item-content>
              <div class="overline mb-4">To. {{ donation.receiver }}</div>
              <v-list-item-title class="headline mb-1">30%</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
          <br />
          <v-slider v-model="slider" :thumb-size="24" thumb-label="always" readonly>
            <template v-slot:thumb-label="{ value }">
              {{ satisfactionEmojis[Math.min(Math.floor(value / 8), 7)] }}
            </template>
          </v-slider>

          <div class="p-3">
            <p>현재: {{ donation.nowcnt }}</p>
            <p>목표: {{ donation.maxcnt }}</p>
            <p>{{ donation.edate }} 까지</p>
            <p>D-{{ this.Dday }}</p>
          </div>
          <div style="text-align:center" class="mb-5">
            <v-btn rounded color="primary" dark @click="goDonation">웃음기부</v-btn>
          </div>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import http from '@/util/http-common.js';

export default {
  name: 'DonationRequestDetailComp',

  data() {
    return {
      donation: [],
      pid: 0,
      title: '',
      photo: '',
      satisfactionEmojis: ['😐', '🙂', '😊', '😁', '😁', '😄', '😄', '😍'],
      slider: 30,
      Dday: 0,
      cheerup: '',
    };
  },

  created() {
    this.pid = this.$route.params.ID;
    this.init();
  },

  methods: {
    init() {
      http
        .get('/donation/detail/' + this.pid)
        .then((res) => {
          this.donation = res.data;
          this.calDay();
          this.photoInsert();
        })
        .catch((err) => {
          console.log(err);
        });
    },
    calDay() {
      var now = new Date();
      var endDate = this.donation.edate.split('-');
      var newEnd = new Date(endDate[0], Number(endDate[1]) - 1, endDate[2]);
      var gap = Math.ceil((newEnd.getTime() - now.getTime()) / 1000 / 60 / 60 / 24);
      this.Dday = gap;
    },
    photoInsert() {
      this.photo = this.donation.img;
    },
    scrollSpy(evt) {
      evt.preventDefault();
      const href = evt.target.getAttribute('href');
      var location = document.querySelector(href).offsetTop;
      window.scrollTo({ top: location + 790, behavior: 'smooth' });
    },
    msgSubmit() {
        // console.log(this.cheerup)
        // 응원 메세지 저장 axios
    },
    goDonation() {
        alert("기부하러 가기 구현");
    }
  },
  watch: {},
};
</script>
